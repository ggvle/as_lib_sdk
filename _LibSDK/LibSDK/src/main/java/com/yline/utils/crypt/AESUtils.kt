package com.yline.utils.crypt

import android.util.Base64

import java.security.NoSuchAlgorithmException

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * AES对称加密
 * 直接调用的api引入了Base64转码加密
 * 原因：若无转码，则生成的字符串会产生乱码，乱码的字符串获取的byte是错误的
 *
 * @author yline 2018/8/28 -- 18:05
 */
object AESUtils {
    /* 算法 */
    private const val ALGORITHM = "AES"

    /* 算法/模式/补码方式 */
    private const val METHOD = "AES/CBC/PKCS5Padding"

    /* 使用CBC模式，需要一个向量iv，可增加加密算法的强度 */
    private const val PARAMETER_SPEC = "1234567890123456"

    /**
     * AES加密 + Base64转码加密
     *
     * @param sSrc 原始数据
     * @param sKey 秘钥，要求16位
     * @return AES - Base64 后的数组
     */
    fun encrypt(sSrc: String?, sKey: String?): String? {
        if (null == sSrc || null == sKey) {
            return null
        }

        val encryptedBytes = encryptInner(sSrc.toByteArray(), sKey.toByteArray(), PARAMETER_SPEC.toByteArray(), METHOD) // AES加密
        return if (null == encryptedBytes) null else Base64.encodeToString(encryptedBytes, Base64.NO_WRAP) // base64转码并加密
    }

    /**
     * AES 加密
     *
     * @param srcBytes      原始数据（待加密的数据）
     * @param keyBytes      秘钥，要求16位
     * @return 加密后的byte数组
     */
    fun encrypt(srcBytes: ByteArray, keyBytes: ByteArray): ByteArray? {
        return encryptInner(srcBytes, keyBytes, PARAMETER_SPEC.toByteArray(), METHOD)
    }

    /**
     * AES 加密
     *
     * @param srcBytes      原始数据（待加密的数据）
     * @param keyBytes      秘钥，要求16位
     * @param parameterSpec 偏移量
     * @param method        加密方法
     * @return 加密后的byte数组
     */
    fun encrypt(srcBytes: ByteArray, keyBytes: ByteArray, parameterSpec: ByteArray, method: String): ByteArray? {
        return encryptInner(srcBytes, keyBytes, parameterSpec, method)
    }

    /**
     * Base64转码解密 + AES 解密
     *
     * @param sSrc 原始数据（待解密的数据）
     * @param sKey 秘钥，要求16位
     * @return 解密后的byte数组
     */
    fun decrypt(sSrc: String?, sKey: String?): String? {
        if (null == sSrc || null == sKey) {
            return null
        }

        val baseBytes = Base64.decode(sSrc, Base64.NO_WRAP) // base64转码并解密
        val decryptedBytes = decryptInner(baseBytes, sKey.toByteArray(), PARAMETER_SPEC.toByteArray(), METHOD) // AES解密
        return if (null == decryptedBytes) null else String(decryptedBytes)
    }

    /**
     * AES 解密
     *
     * @param srcBytes 原始数据（待解密的数据）
     * @param keyBytes 秘钥，要求16位
     * @return 解密后的byte数组
     */
    fun decrypt(srcBytes: ByteArray, keyBytes: ByteArray): ByteArray? {
        return decryptInner(srcBytes, keyBytes, PARAMETER_SPEC.toByteArray(), METHOD)
    }

    /**
     * AES 解密
     *
     * @param srcBytes      原始数据（待解密的数据）
     * @param keyBytes      秘钥，要求16位
     * @param parameterSpec 偏移量
     * @param method        解密方式
     * @return 解密后的byte数组
     */
    fun decrypt(srcBytes: ByteArray, keyBytes: ByteArray, parameterSpec: ByteArray, method: String): ByteArray? {
        return decryptInner(srcBytes, keyBytes, parameterSpec, method)
    }

    /**
     * 随机生成一个 AES 字符串
     *
     * @param length 16 * 8
     * @return 字符流
     */
    fun createAESKey(length: Int): ByteArray {
        return createAESKeyInner(length)
    }

    /* ---------------------------- 内部实现 ---------------------------- */

    /**
     * 随机生成一个 AES 字符串
     *
     * @param length 16 * 8
     * @return 字符流
     */
    private fun createAESKeyInner(length: Int): ByteArray {
        try {
            val generator = KeyGenerator.getInstance(ALGORITHM)
            generator.init(length)
            return generator.generateKey().encoded
        } catch (e: NoSuchAlgorithmException) {
            return "1234567887654321".toByteArray()
        }

    }

    /**
     * AES 加密
     *
     * @param srcBytes      原始数据（待加密的数据）
     * @param keyBytes      秘钥，要求16位
     * @param parameterSpec 偏移量
     * @param method        加密方法
     * @return 加密后的byte数组
     */
    private fun encryptInner(srcBytes: ByteArray?, keyBytes: ByteArray?, parameterSpec: ByteArray?, method: String): ByteArray? {
        if (null == srcBytes || null == keyBytes || keyBytes.size != 16 || null == parameterSpec) {
            return null
        }

        try {
            val skeySpec = SecretKeySpec(keyBytes, ALGORITHM)

            val cipher = Cipher.getInstance(method)
            val iv = IvParameterSpec(parameterSpec)
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)

            return cipher.doFinal(srcBytes)
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }

    }

    /**
     * AES 解密
     *
     * @param srcBytes      原始数据（待解密的数据）
     * @param keyBytes      秘钥，要求16位
     * @param parameterSpec 偏移量
     * @param method        解密方式
     * @return 解密后的byte数组
     */
    private fun decryptInner(srcBytes: ByteArray?, keyBytes: ByteArray?, parameterSpec: ByteArray?, method: String): ByteArray? {
        if (null == srcBytes || null == keyBytes || keyBytes.size != 16 || null == parameterSpec) {
            return null
        }

        try {
            val skeySpec = SecretKeySpec(keyBytes, ALGORITHM)
            val cipher = Cipher.getInstance(method)
            val iv = IvParameterSpec(parameterSpec)
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)

            return cipher.doFinal(srcBytes)
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }

    }
}
