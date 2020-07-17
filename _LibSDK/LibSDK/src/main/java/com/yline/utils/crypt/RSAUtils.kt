package com.yline.utils.crypt

import android.util.Base64

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.NoSuchAlgorithmException
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

import javax.crypto.Cipher

/**
 * RSA数字签名 + 加密解密
 *
 *
 * 直接调用的api引入了Base64转码加密
 * 原因：若无转码，则生成的字符串会产生乱码，乱码的字符串获取的byte是错误的
 *
 * @author yline 2018/8/29 -- 15:37
 */
object RSAUtils {
    /* 单次 RSA加密，明文最大大小 */
    private const val MAX_ENCRYPT_BLOCK = 117

    /* 单次 RSA解密，密文最大大小 */
    private const val MAX_DECRYPT_BLOCK = 128

    /* 加密算法RSA */
    private const val ALGORITHM = "RSA"

    /* 秘钥长度(公钥和私钥) */
    private const val KEY_SIZE = 1024

    /* 签名算法 */
    private const val SIGNATURE_ALGORITHM = "MD5withRSA"

    /* 算法/模式/补码方式 */
    private const val METHOD = "RSA/ECB/PKCS1Padding"

    /* 由于服务器和Android端，内部RSA引用内容不同，这种方式可以解决BadPaddingException问题 */
    private const val METHOD_NO_PADDING = "RSA/None/PKCS1Padding"

    /**
     * 耗时较长，测试约300ms左右
     * 生成秘钥对，公钥和私钥
     *
     * @return 秘钥对
     */
    fun createKeyPair(): KeyPair? {
        try {
            val keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM)
            keyPairGenerator.initialize(KEY_SIZE)
            return keyPairGenerator.generateKeyPair()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 获取私钥（经过Base64转码加密过）
     *
     * @param keyPair 秘钥对
     * @return 被Base64转码加密过的私钥
     */
    fun getPrivateKey(keyPair: KeyPair?): String? {
        if (null == keyPair) {
            return null
        }

        val privateKeyBytes = keyPair.private.encoded
        return if (null == privateKeyBytes) null else Base64.encodeToString(privateKeyBytes, Base64.NO_WRAP)
    }

    /**
     * 获取公钥（经过Base64转码加密过）
     *
     * @param keyPair 密钥对
     * @return 被Base64转码加密过的公钥
     */
    fun getPublicKey(keyPair: KeyPair?): String? {
        if (null == keyPair) {
            return null
        }

        val publicKeyBytes = keyPair.public.encoded
        return if (null == publicKeyBytes) null else Base64.encodeToString(publicKeyBytes, Base64.NO_WRAP)
    }

    /**
     * 使用私钥，对信息，生成数字签名
     *
     * @param src        数据
     * @param privateKey 私钥（被Base64加密过）
     * @return 生成的数字签名（被Base64加密过）
     */
    fun sign(src: String?, privateKey: String?): String? {
        if (null == src || null == privateKey) {
            return null
        }

        val privateKeyBytes = Base64.decode(privateKey, Base64.NO_WRAP)
        val signBytes = signInner(src.toByteArray(), privateKeyBytes, SIGNATURE_ALGORITHM)
        return if (null == signBytes) null else Base64.encodeToString(signBytes, Base64.NO_WRAP)
    }

    /**
     * 使用私钥，对信息，生成数字签名
     *
     * @param sourceBytes     数据
     * @param privateKeyBytes 私钥
     * @param signAlgorithm   签名方式
     * @return 生成的数字签名
     */
    fun sign(sourceBytes: ByteArray, privateKeyBytes: ByteArray, signAlgorithm: String): ByteArray? {
        return signInner(sourceBytes, privateKeyBytes, signAlgorithm)
    }

    /**
     * 校验数字签名
     *
     * @param src       数据
     * @param publicKey 公钥（被Base64加密过）
     * @param sign      数字签名（被Base64加密过）
     * @return true(校验成功)，false(校验失败)
     */
    fun verifySign(src: String?, publicKey: String?, sign: String?): Boolean {
        if (null == src || null == publicKey || null == sign) {
            return false
        }

        val publicKeyBytes = Base64.decode(publicKey, Base64.NO_WRAP)
        val signBytes = Base64.decode(sign, Base64.NO_WRAP)
        return verifySignInner(src.toByteArray(), publicKeyBytes, signBytes, SIGNATURE_ALGORITHM)
    }

    /**
     * 校验数字签名
     *
     * @param source         数据
     * @param publicKeyBytes 公钥
     * @param signBytes      数字签名
     * @param signAlgorithm  签名方式
     * @return true(校验成功)，false(校验失败)
     */
    fun verifySign(source: ByteArray, publicKeyBytes: ByteArray, signBytes: ByteArray, signAlgorithm: String): Boolean {
        return verifySignInner(source, publicKeyBytes, signBytes, signAlgorithm)
    }

    /**
     * 使用公钥进行 RSA加密 - Base64转码并加密
     *
     * @param src       等待加密的原始数据
     * @param publicKey 公钥（被Base64加密过）
     * @return 加密后的数据
     */
    fun encrypt(src: String?, publicKey: String?): String? {
        if (null == src || null == publicKey) {
            return null
        }

        val publicKeyBytes = Base64.decode(publicKey, Base64.NO_WRAP)
        val encryptedBytes = encryptInner(src.toByteArray(), publicKeyBytes, METHOD)
        return if (null == encryptedBytes) null else Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)
    }

    /**
     * 使用公钥进行 RSA加密
     *
     * @param sourceBytes    等待加密的原始数据
     * @param publicKeyBytes 公钥
     * @param method         加密方式
     * @return 加密后的数据
     */
    fun encrypt(sourceBytes: ByteArray, publicKeyBytes: ByteArray, method: String): ByteArray? {
        return encryptInner(sourceBytes, publicKeyBytes, method)
    }

    /**
     * 使用私钥进行  Base64转码解密 - RSA解密
     *
     * @param src        原始数据，等待解密
     * @param privateKey 私钥（被Base64加密过）
     * @return 解密后的数据
     */
    fun decrypt(src: String?, privateKey: String?): String? {
        if (null == src || null == privateKey) {
            return null
        }

        val privateKeyBytes = Base64.decode(privateKey, Base64.NO_WRAP)
        val baseBytes = Base64.decode(src.toByteArray(), Base64.NO_WRAP)
        val decryptedBytes = decryptInner(baseBytes, privateKeyBytes, METHOD)

        return if (null == decryptedBytes) null else String(decryptedBytes)
    }

    /**
     * 使用私钥进行 RSA解密
     *
     * @param sourceBytes     原始数据，等待解密
     * @param privateKeyBytes 私钥
     * @param method          解密方式
     * @return 解密后的数据
     */
    fun decrypt(sourceBytes: ByteArray, privateKeyBytes: ByteArray, method: String): ByteArray? {
        return decryptInner(sourceBytes, privateKeyBytes, method)
    }

    /**
     * 使用私钥进行RSA加密
     *
     * @param sourceBytes     等待加密的原始数据
     * @param privateKeyBytes 私钥
     * @return 加密后的数据
     */
    fun encryptByPrivate(sourceBytes: ByteArray, privateKeyBytes: ByteArray): ByteArray? {
        return encryptInnerByPrivate(sourceBytes, privateKeyBytes, METHOD_NO_PADDING)
    }

    /**
     * 使用公钥进行 RSA解密
     *
     * @param sourceBytes    原始数据，等待解密
     * @param publicKeyBytes 公钥
     * @return 解密后的数据
     */
    fun decryptByPublic(sourceBytes: ByteArray, publicKeyBytes: ByteArray): ByteArray? {
        return decryptInnerByPublic(sourceBytes, publicKeyBytes, METHOD_NO_PADDING)
    }

    /* ----------------------------------- 内部实现api ----------------------------------------- */

    /**
     * 使用私钥，对信息，生成数字签名
     *
     * @param sourceBytes     数据
     * @param privateKeyBytes 私钥
     * @param signAlgorithm   签名方式
     * @return 生成的数字签名
     */
    private fun signInner(sourceBytes: ByteArray?, privateKeyBytes: ByteArray?, signAlgorithm: String): ByteArray? {
        if (null == sourceBytes || null == privateKeyBytes) {
            return null
        }

        try {
            val keyFactory = KeyFactory.getInstance(ALGORITHM)
            val encodedKeySpec = PKCS8EncodedKeySpec(privateKeyBytes)
            val signPrivateKey = keyFactory.generatePrivate(encodedKeySpec)

            val signature = Signature.getInstance(signAlgorithm)
            signature.initSign(signPrivateKey)
            signature.update(sourceBytes)

            return signature.sign()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 校验数字签名
     *
     * @param source         数据
     * @param publicKeyBytes 公钥
     * @param signBytes      数字签名
     * @param signAlgorithm  签名方式
     * @return true(校验成功)，false(校验失败)
     */
    private fun verifySignInner(source: ByteArray?, publicKeyBytes: ByteArray?, signBytes: ByteArray?, signAlgorithm: String): Boolean {
        if (null == source || null == publicKeyBytes || null == signBytes) {
            return false
        }

        try {
            val keyFactory = KeyFactory.getInstance(ALGORITHM)
            val encodedKeySpec = X509EncodedKeySpec(publicKeyBytes)
            val signPublicKey = keyFactory.generatePublic(encodedKeySpec)

            val signature = Signature.getInstance(signAlgorithm)
            signature.initVerify(signPublicKey)
            signature.update(source)

            return signature.verify(signBytes)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 使用公钥进行 RSA加密
     *
     * @param sourceBytes    等待加密的原始数据
     * @param publicKeyBytes 公钥
     * @param method         加密方式
     * @return 加密后的数据
     */
    private fun encryptInner(sourceBytes: ByteArray?, publicKeyBytes: ByteArray?, method: String): ByteArray? {
        if (null == sourceBytes || null == publicKeyBytes) {
            return null
        }

        var baoStream: ByteArrayOutputStream? = null
        try {
            val keyFactory = KeyFactory.getInstance(ALGORITHM)
            val encodedKeySpec = X509EncodedKeySpec(publicKeyBytes)
            val newPublicKey = keyFactory.generatePublic(encodedKeySpec)

            val cipher = Cipher.getInstance(method)
            cipher.init(Cipher.ENCRYPT_MODE, newPublicKey)

            baoStream = ByteArrayOutputStream()
            var tempLength: Int
            var offset = 0
            val sourceLength = sourceBytes.size
            var cacheBytes: ByteArray

            while (sourceLength > offset) {
                if (sourceLength - offset >= MAX_ENCRYPT_BLOCK) {
                    tempLength = MAX_ENCRYPT_BLOCK
                } else {
                    tempLength = sourceLength - offset
                }

                cacheBytes = cipher.doFinal(sourceBytes, offset, tempLength)
                baoStream.write(cacheBytes, 0, cacheBytes.size)
                offset += tempLength
            }

            return baoStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            if (null != baoStream) {
                try {
                    baoStream.close()
                } catch (e: IOException) {
                    //
                }

            }
        }
    }

    /**
     * 使用私钥进行 RSA解密
     *
     * @param sourceBytes     原始数据，等待解密
     * @param privateKeyBytes 私钥
     * @param method          解密方式
     * @return 解密后的数据
     */
    private fun decryptInner(sourceBytes: ByteArray?, privateKeyBytes: ByteArray?, method: String): ByteArray? {
        if (null == sourceBytes || null == privateKeyBytes) {
            return null
        }

        var baoStream: ByteArrayOutputStream? = null
        try {
            val keyFactory = KeyFactory.getInstance(ALGORITHM)
            val encodedKeySpec = PKCS8EncodedKeySpec(privateKeyBytes)
            val newPrivateKey = keyFactory.generatePrivate(encodedKeySpec)

            val cipher = Cipher.getInstance(method)
            cipher.init(Cipher.DECRYPT_MODE, newPrivateKey)

            baoStream = ByteArrayOutputStream()
            var tempLength: Int
            var offset = 0
            val sourceLength = sourceBytes.size
            var cacheTemp: ByteArray

            while (sourceLength > offset) {
                if (sourceLength - offset >= MAX_DECRYPT_BLOCK) {
                    tempLength = MAX_DECRYPT_BLOCK
                } else {
                    tempLength = sourceLength - offset
                }

                cacheTemp = cipher.doFinal(sourceBytes, offset, tempLength)
                baoStream.write(cacheTemp, 0, cacheTemp.size)
                offset += tempLength
            }

            return baoStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            if (null != baoStream) {
                try {
                    baoStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 使用私钥进行RSA加密
     *
     * @param sourceBytes     等待加密的原始数据
     * @param privateKeyBytes 私钥
     * @param method          加密方式
     * @return 加密后的数据
     */
    private fun encryptInnerByPrivate(sourceBytes: ByteArray?, privateKeyBytes: ByteArray?, method: String): ByteArray? {
        if (null == sourceBytes || null == privateKeyBytes) {
            return null
        }

        var baoStream: ByteArrayOutputStream? = null
        try {
            val keyFactory = KeyFactory.getInstance(ALGORITHM)
            val pkcs8EncodedKeySpec = PKCS8EncodedKeySpec(privateKeyBytes)
            val newPrivateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec)

            val cipher = Cipher.getInstance(method)
            cipher.init(Cipher.ENCRYPT_MODE, newPrivateKey)

            baoStream = ByteArrayOutputStream()
            var tempLength: Int
            var offset = 0
            val sourceLength = sourceBytes.size
            var cacheBytes: ByteArray

            while (sourceLength > offset) {
                if (sourceLength - offset >= MAX_ENCRYPT_BLOCK) {
                    tempLength = MAX_ENCRYPT_BLOCK
                } else {
                    tempLength = sourceLength - offset
                }

                cacheBytes = cipher.doFinal(sourceBytes, offset, tempLength)
                baoStream.write(cacheBytes, 0, cacheBytes.size)
                offset += tempLength
            }

            return baoStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            if (null != baoStream) {
                try {
                    baoStream.close()
                } catch (e: IOException) {
                    //
                }

            }
        }
    }

    /**
     * 使用公钥进行 RSA解密
     *
     * @param sourceBytes    原始数据，等待解密
     * @param publicKeyBytes 公钥
     * @return 解密后的数据
     */
    private fun decryptInnerByPublic(sourceBytes: ByteArray?, publicKeyBytes: ByteArray?, method: String): ByteArray? {
        if (null == sourceBytes || null == publicKeyBytes) {
            return null
        }

        var baoStream: ByteArrayOutputStream? = null
        try {
            val keyFactory = KeyFactory.getInstance(ALGORITHM)
            val encodedKeySpec = X509EncodedKeySpec(publicKeyBytes)
            val newPublicKey = keyFactory.generatePublic(encodedKeySpec)

            val cipher = Cipher.getInstance(method)
            cipher.init(Cipher.DECRYPT_MODE, newPublicKey)

            baoStream = ByteArrayOutputStream()
            var tempLength: Int
            var offset = 0
            val sourceLength = sourceBytes.size
            var cacheTemp: ByteArray

            while (sourceLength > offset) {
                if (sourceLength - offset >= MAX_DECRYPT_BLOCK) {
                    tempLength = MAX_DECRYPT_BLOCK
                } else {
                    tempLength = sourceLength - offset
                }

                cacheTemp = cipher.doFinal(sourceBytes, offset, tempLength)
                baoStream.write(cacheTemp, 0, cacheTemp.size)
                offset += tempLength
            }

            return baoStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            if (null != baoStream) {
                try {
                    baoStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }
}
