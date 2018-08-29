package com.yline.utils.crypt;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES对称加密
 * 直接调用的api引入了Base64转码加密
 * 原因：若无转码，则生成的字符串会产生乱码，乱码的字符串获取的byte是错误的
 *
 * @author yline 2018/8/28 -- 18:05
 */
public class AESUtils {
	/* 算法 */
	private static final String ALGORITHM = "AES";
	
	/* 算法/模式/补码方式 */
	private static final String METHOD = "AES/CBC/PKCS5Padding";
	
	/* 使用CBC模式，需要一个向量iv，可增加加密算法的强度 */
	private static final String PARAMETER_SPEC = "1234567890123456";
	
	/**
	 * AES加密 + Base64转码加密
	 *
	 * @param sSrc 原始数据
	 * @param sKey 秘钥，要求16位
	 * @return AES - Base64 后的数组
	 */
	public static String encrypt(String sSrc, String sKey) {
		if (null == sSrc || null == sKey) {
			return null;
		}
		
		byte[] encryptedBytes = encryptInner(sSrc.getBytes(), sKey.getBytes()); // AES加密
		return (null == encryptedBytes ? null : Base64.encodeToString(encryptedBytes, Base64.DEFAULT)); // base64转码并加密
	}
	
	/**
	 * AES 加密
	 *
	 * @param srcBytes 原始数据（待加密的数据）
	 * @param keyBytes 秘钥，要求16位
	 * @return 加密后的byte数组
	 */
	private static byte[] encryptInner(byte[] srcBytes, byte[] keyBytes) {
		if (null == srcBytes || null == keyBytes || keyBytes.length != 16) {
			return null;
		}
		
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, ALGORITHM);
			
			Cipher cipher = Cipher.getInstance(METHOD);
			IvParameterSpec iv = new IvParameterSpec(PARAMETER_SPEC.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			
			return cipher.doFinal(srcBytes);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Base64转码解密 + AES 解密
	 *
	 * @param sSrc 原始数据（待解密的数据）
	 * @param sKey 秘钥，要求16位
	 * @return 解密后的byte数组
	 */
	public static String decrypt(String sSrc, String sKey) {
		if (null == sSrc || null == sKey) {
			return null;
		}
		
		byte[] baseBytes = Base64.decode(sSrc, Base64.DEFAULT); // base64转码并解密
		byte[] decryptedBytes = decryptInner(baseBytes, sKey.getBytes()); // AES解密
		return (null == decryptedBytes ? null : new String(decryptedBytes));
	}
	
	/**
	 * AES 解密
	 *
	 * @param srcBytes 原始数据（待解密的数据）
	 * @param keyBytes 秘钥，要求16位
	 * @return 解密后的byte数组
	 */
	private static byte[] decryptInner(byte[] srcBytes, byte[] keyBytes) {
		if (null == srcBytes || null == keyBytes || keyBytes.length != 16) {
			return null;
		}
		
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, ALGORITHM);
			Cipher cipher = Cipher.getInstance(METHOD);
			IvParameterSpec iv = new IvParameterSpec(PARAMETER_SPEC.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			
			return cipher.doFinal(srcBytes);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
