package com.yline.utils.crypt

import kotlin.experimental.and

/**
 * 十六进制字符串 和 字符集字符串，相互转换
 *
 * @author yline 2018/8/28 -- 14:15
 */
object HexUtils {
    /**
     * 用于建立十六进制字符的输出的小写字符数组
     */
    private val DIGITS_LOWER = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
    /**
     * 用于建立十六进制字符的输出的大写字符数组
     */
    private val DIGITS_UPPER = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param data byte[]
     * @return 十六进制String
     */
    fun encodeHex(data: ByteArray): String {
        return String(encodeHexInner(data, DIGITS_LOWER))
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param data        byte[]
     * @param toLowerCase {true} 传换成小写格式 ， {false} 传换成大写格式
     * @return 十六进制String
     */
    fun encodeHex(data: ByteArray, toLowerCase: Boolean): String {
        return String(encodeHexInner(data, if (toLowerCase) DIGITS_LOWER else DIGITS_UPPER))
    }

    /**
     * 将十六进制字符数组转换为字节数组
     *
     * @param data 十六进制char[]
     * @return byte[]
     * @throws RuntimeException 如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常
     */
    fun decodeHex(data: CharArray): ByteArray {
        return decodeHexInner(data)
    }

    /**
     * 将字节数组转换为十六进制字符数组
     *
     * @param data     byte[]
     * @param toDigits 用于控制输出的char[]
     * @return 十六进制char[]
     */
    private fun encodeHexInner(data: ByteArray, toDigits: CharArray): CharArray {
        val length = data.size
        val out = CharArray(length shl 1)
        // two characters form the hex value.
        var i = 0
        var j = 0
        while (i < length) {
            val value = data[i].toInt().and(0xFF)

            out[j++] = toDigits[value.ushr(4)]
            out[j++] = toDigits[value.and(0x0F)]
            i++
        }
        return out
    }

    /**
     * 将十六进制字符数组转换为字节数组
     *
     * @param data 十六进制char[]
     * @return byte[]
     * @throws RuntimeException 如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常
     */
    private fun decodeHexInner(data: CharArray): ByteArray {
        val len = data.size
        if (len and 0x01 != 0) {
            throw RuntimeException("Odd number of characters.")
        }
        val out = ByteArray(len shr 1)
        // two characters form the hex value.
        var i = 0
        var j = 0
        while (j < len) {
            var f = toDigit(data[j], j) shl 4
            j++
            f = f or toDigit(data[j], j)
            j++
            out[i] = (f and 0xFF).toByte()
            i++
        }
        return out
    }

    /**
     * 将十六进制字符转换成一个整数
     *
     * @param ch    十六进制char
     * @param index 十六进制字符在字符数组中的位置
     * @return 一个整数
     * @throws RuntimeException 当ch不是一个合法的十六进制字符时，抛出运行时异常
     */
    private fun toDigit(ch: Char, index: Int): Int {
        val digit = Character.digit(ch, 16)
        if (digit == -1) {
            throw RuntimeException("Illegal hexadecimal character " + ch
                    + " at index " + index)
        }
        return digit
    }
}
