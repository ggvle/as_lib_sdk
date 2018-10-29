package com.yline.utils.crypt;

import android.util.Base64;

import com.yline.utils.LogUtil;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * DES对称加密
 * 直接调用的api引入了Base64转码加密
 * 原因：若无转码，则生成的字符串会产生乱码，乱码的字符串获取的byte是错误的
 *
 * @author yline 2018/8/29 -- 9:28
 */
public class DESUtils {
	/* 算法 */
	private static final String ALGORITHM = "DES";
	
	/* 算法/模式/补码方式 */
	private static final String METHOD = "DES/CBC/PKCS5Padding";
	
	/* 使用CBC模式，需要一个向量iv，可增加加密算法的强度 | 限制长度为8 */
	private static final String PARAMETER_SPEC = "12345678";
	
	/**
	 * DES加密 + Base64转码加密
	 *
	 * @param sScr 原始数据
	 * @param sKey 秘钥
	 * @return DES - Base64 后的数组
	 */
	public static String encrypt(String sScr, String sKey) {
		if (null == sScr || null == sKey) {
			return null;
		}
		
		byte[] encryptBytes = encryptInner(sScr.getBytes(), sKey.getBytes(), PARAMETER_SPEC.getBytes(), METHOD); // DES加密
		
		return (null == encryptBytes ? null : Base64.encodeToString(encryptBytes, Base64.NO_WRAP)); // base64转码并加密
	}
	
	/**
	 * DES 加密
	 *
	 * @param srcBytes      原始数据（待加密的数据）
	 * @param keyBytes      秘钥，秘钥长度必须大于等于8
	 * @param parameterSpec 偏移量
	 * @param method        加密方式
	 * @return 加密后的byte数组
	 */
	public static byte[] encrypt(byte[] srcBytes, byte[] keyBytes, byte[] parameterSpec, String method) {
		return encryptInner(srcBytes, keyBytes, parameterSpec, method);
	}
	
	/**
	 * Base64转码解密 + DES 解密
	 *
	 * @param sSrc 原始数据（待解密的数据）
	 * @param sKey 秘钥，秘钥长度必须大于等于8
	 * @return 解密后的byte数组
	 */
	public static String decrypt(String sSrc, String sKey) {
		if (null == sSrc || null == sKey) {
			return null;
		}
		
		byte[] baseBytes = Base64.decode(sSrc, Base64.NO_WRAP); // Base64转码并解密
		LogUtil.v(Arrays.toString(baseBytes));
		byte[] decryptBytes = decryptInner(baseBytes, sKey.getBytes(), PARAMETER_SPEC.getBytes(), METHOD); // DES解密
		LogUtil.v(Arrays.toString(decryptBytes));
		
		return (null == decryptBytes ? null : new String(decryptBytes));
	}
	
	/**
	 * DES解密
	 *
	 * @param srcBytes      原始数据（待解密的数据）
	 * @param keyBytes      秘钥
	 * @param parameterSpec 偏移量
	 * @param method        解密方式
	 * @return 解密后的byte数组
	 */
	public static byte[] decrypt(byte[] srcBytes, byte[] keyBytes, byte[] parameterSpec, String method) {
		return decryptInner(srcBytes, keyBytes, parameterSpec, method);
	}
	
	/* -------------------------------- 内部实现 ----------------------------- */
	
	/**
	 * DES 加密
	 *
	 * @param srcBytes      原始数据（待加密的数据）
	 * @param keyBytes      秘钥，秘钥长度必须大于等于8
	 * @param parameterSpec 偏移量
	 * @param method        加密方式
	 * @return 加密后的byte数组
	 */
	private static byte[] encryptInner(byte[] srcBytes, byte[] keyBytes, byte[] parameterSpec, String method) {
		if (null == srcBytes || null == keyBytes || null == parameterSpec) {
			return null;
		}
		
		try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
			DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			
			Cipher cipher = Cipher.getInstance(method);
			IvParameterSpec iv = new IvParameterSpec(parameterSpec);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			
			return cipher.doFinal(srcBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * DES解密
	 *
	 * @param srcBytes      原始数据（待解密的数据）
	 * @param keyBytes      秘钥
	 * @param parameterSpec 偏移量
	 * @param method        解密方式
	 * @return 解密后的byte数组
	 */
	private static byte[] decryptInner(byte[] srcBytes, byte[] keyBytes, byte[] parameterSpec, String method) {
		if (null == srcBytes || null == keyBytes) {
			return null;
		}
		
		try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
			DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			
			Cipher cipher = Cipher.getInstance(method);
			IvParameterSpec iv = new IvParameterSpec(parameterSpec);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			
			return cipher.doFinal(srcBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
