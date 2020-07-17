package com.yline.utils.crypt

import java.io.Closeable
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * 实现MD5加密
 *
 * @author yline 2018/8/28 -- 14:34
 */
object MD5Utils {
    /* 算法 */
    private val ALGORITHM = "MD5"

    /**
     * 字符串 加密成 32位十六进制字符串
     *
     * @param valueBytes such as "yline".getBytes()
     * @return such as "971C47E6457B026249A1927AF4B4A93F"
     */
    fun encrypt(valueBytes: ByteArray): String? {
        val digestBytes = encryptInner(valueBytes)
        digestBytes?.let {
            return HexUtils.encodeHex(it, false)
        }
        return null
    }

    /**
     * 字符串 加密成 32位十六进制字符串
     *
     * @param valueBytes  such as "yline".getBytes()
     * @param toLowerCase 是否小写输出
     * @return such as "971C47E6457B026249A1927AF4B4A93F"
     */
    fun encrypt(valueBytes: ByteArray, toLowerCase: Boolean): String? {
        val digestBytes = encryptInner(valueBytes)
        digestBytes?.let {
            return HexUtils.encodeHex(it, toLowerCase)
        }
        return null
    }

    /**
     * MD5 加密
     *
     * @param valueBytes 原始数组
     * @return 加密后16位数组
     */
    private fun encryptInner(valueBytes: ByteArray): ByteArray? {
        try {
            val digest = MessageDigest.getInstance(ALGORITHM)
            return digest.digest(valueBytes)
        } catch (neverHappened: NoSuchAlgorithmException) {
            return null
        }
    }

    /**
     * 文件 加密成 32位十六进制字符串
     *
     * @param file such as "sdcard/xmind_show.jpg"
     * @return such as "4B70C44EE2D7684BBBB8CDC18A971FF6"
     */
    fun encrypt(file: File): String? {
        var `in`: FileInputStream? = null
        var ch: FileChannel? = null
        try {
            `in` = FileInputStream(file)
            ch = `in`.channel

            val byteBuffer = ch!!.map(FileChannel.MapMode.READ_ONLY, 0, file.length())
            val digest = MessageDigest.getInstance(ALGORITHM)
            digest.update(byteBuffer)

            val encodeBytes = digest.digest()
            return HexUtils.encodeHex(encodeBytes, false)
        } catch (neverHappened: IOException) {
            return null
        } catch (neverHappened: NoSuchAlgorithmException) {
            return null
        } finally {
            closeQuietly(`in`)
            closeQuietly(ch)
        }
    }

    /**
     * 关闭IO流
     *
     * @param closeable IO流操作对象
     */
    private fun closeQuietly(closeable: Closeable?) {
        if (null != closeable) {
            try {
                closeable.close()
            } catch (ignored: Throwable) {
            }

        }
    }
}
