package com.demo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.yline.log.LogFileUtil;
import com.yline.test.BaseTestFragment;
import com.yline.utils.FileSizeUtil;
import com.yline.utils.FileUtil;
import com.yline.utils.IOUtil;
import com.yline.utils.LogUtil;
import com.yline.utils.SPUtil;
import com.yline.utils.TimeConvertUtil;
import com.yline.utils.crypt.AESUtils;
import com.yline.utils.crypt.DESUtils;
import com.yline.utils.crypt.HexUtils;
import com.yline.utils.crypt.MD5Utils;
import com.yline.utils.crypt.RSAUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;

public class UtilFragment extends BaseTestFragment {
	private static final String TAG = "UtilFragment";
	
	@Override
	public void testStart(View view, Bundle savedInstanceState) {
		addButton("TimeConvertUtil", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				long oldTime = 1490411992L * 1000;
				
				String result = TimeConvertUtil.stamp2FormatTime(oldTime);
				LogFileUtil.v("result = " + result);
			}
		});
		
		addButton("LogUtil", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				testLogUtil();
			}
		});
		
		addButton("LogFileUtil", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				testLogFileUtil();
			}
		});
		
		addButton("FileUtil", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				testFileUtil();
			}
		});
		
		addButton("SPUtil", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				testSPUtil(getContext());
			}
		});
		
		addButton("测试 IOUtil", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				File dir = getContext().getExternalFilesDir("test");
				File file = FileUtil.create(dir, "sample.txt");
				
				try {
					FileOutputStream fileOutputStream = new FileOutputStream(file, true);
					IOUtil.write(System.currentTimeMillis() + ";汉字;;", fileOutputStream);
					IOUtil.close(fileOutputStream);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		addButton("HexUtils", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				testHex();
			}
		});
		
		addButton("MD5Utils", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				testMD5();
			}
		});
		
		addButton("AESUtils", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String key = "1234567887654321";
				String encrypt = AESUtils.encrypt("yline", key);
				LogUtil.v("encrypt = " + encrypt);
				
				String decrypt = AESUtils.decrypt(encrypt, key);
				LogUtil.v("decrypt = " + decrypt);
			}
		});
		
		addButton("DESUtils", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String key = "12345678";
				String encrypt = DESUtils.encrypt("yline", key);
				LogUtil.v("encrypt = " + encrypt);
				
				String decrypt = DESUtils.decrypt(encrypt, key);
				LogUtil.v("decrypt = " + decrypt);
			}
		});
		
		addButton("RSAUtils", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				testRSA();
			}
		});
	}
	
	private void testRSA() {
		long startTime = System.currentTimeMillis();
		
		// 生成密钥对
		KeyPair keyPair = RSAUtils.createKeyPair();
		LogUtil.v("diffTime = " + (System.currentTimeMillis() - startTime));
		startTime = System.currentTimeMillis();
		
		// 私钥
		String privateKey = RSAUtils.getPrivateKey(keyPair);
		LogUtil.v("privateKey = " + privateKey);
		
		LogUtil.v("diffTime = " + (System.currentTimeMillis() - startTime));
		startTime = System.currentTimeMillis();
		
		// 公钥
		String publicKey = RSAUtils.getPublicKey(keyPair);
		LogUtil.v("publicKey = " + publicKey);
		
		LogUtil.v("diffTime = " + (System.currentTimeMillis() - startTime));
		startTime = System.currentTimeMillis();
		
		// 加密
		String encryptA = RSAUtils.encrypt("yline", publicKey);
		LogUtil.v("encryptA = " + encryptA);
		
		LogUtil.v("diffTime = " + (System.currentTimeMillis() - startTime));
		startTime = System.currentTimeMillis();
		
		// 解密
		String decryptA = RSAUtils.decrypt(encryptA, privateKey);
		LogUtil.v("decryptA = " + decryptA);
		
		LogUtil.v("diffTime = " + (System.currentTimeMillis() - startTime));
		startTime = System.currentTimeMillis();
		
		final String SRC = "yline";
		// 数字证书
		String signA = RSAUtils.sign(SRC, privateKey);
		LogUtil.v("signA = " + signA);
		
		LogUtil.v("diffTime = " + (System.currentTimeMillis() - startTime));
		startTime = System.currentTimeMillis();
		
		// 校验数字证书
		boolean isRight = RSAUtils.verifySign(SRC, publicKey, signA);
		LogUtil.v("isRight = " + isRight);
		
		LogUtil.v("diffTime = " + (System.currentTimeMillis() - startTime));
	}
	
	private void testHex() {
		String hexString = HexUtils.encodeHex("yline".getBytes());
		byte[] sourceBytes = HexUtils.decodeHex(hexString.toCharArray());
		LogUtil.v(new String(sourceBytes));
	}
	
	private void testMD5() {
		String encryptA = MD5Utils.encrypt("yline".getBytes());
		LogUtil.v("encryptA = " + encryptA);
		
		File fileB = FileUtil.getFileTop("xmind_show.jpg");
		String encryptB = MD5Utils.encrypt(fileB);
		LogUtil.v("encryptB = " + encryptB);
	}
	
	private void testSPUtil(Context context) {
		SPUtil.put(context, "null", null);
		String result = (String) SPUtil.get(context, "null", null);
		LogFileUtil.v(TAG, "put -> value = " + result);
		
		// 增加两条数据
		SPUtil.put(context, "username", "utilUsername");
		SPUtil.put(context, "password", "utilPassword");
		LogFileUtil.v(TAG, "put -> value - utilUsername");
		LogFileUtil.v(TAG, "put -> value - utilPassword");
		
		// 更新两条数据
		SPUtil.put(context, "username", "utilUpdateUsername");
		SPUtil.put(context, "password", "utilUpdatePassword");
		LogFileUtil.v(TAG, "put -> value - utilUpdateUsername");
		LogFileUtil.v(TAG, "put -> value - utilUpdatePassword");
		
		// 删除一条数据
		SPUtil.remove(context, "password");
		LogFileUtil.v(TAG, "remove -> key - password");
		
		// 获取两条数据
		String username = (String) SPUtil.get(context, "username", "");
		String password = (String) SPUtil.get(context, "password", "");
		LogFileUtil.v(TAG, "get -> key - username");
		LogFileUtil.v(TAG, "get -> key - password");
		LogFileUtil.i(TAG, "usrname = " + username + ",password = " + password);
	}
	
	private void testLogUtil() {
		LogUtil.v("test -> v");
		LogUtil.v("test -> v", LogUtil.LOG_LOCATION_PARENT);
		
		LogUtil.i("test -> i");
		LogUtil.i("test -> i", LogUtil.LOG_LOCATION_PARENT);
		
		LogUtil.e("test -> e");
		LogUtil.e("test -> e", LogUtil.LOG_LOCATION_PARENT);
		LogUtil.e("test -> e", new Exception("test -> e -> Exception"));
		LogUtil.e("test -> e", LogUtil.LOG_LOCATION_PARENT, new Exception("test -> e -> Exception"));
	}
	
	private void testFileUtil() {
		LogUtil.v(TAG + " -> FileUtil.getPath() = " + FileUtil.getPathTop());
		
		String pathRoot = FileUtil.getPathRoot();
		LogUtil.v(TAG + " -> pathRoot = " + pathRoot);
		
		long blockSize = FileSizeUtil.getFileBlockSize(FileUtil.getPathTop());
		long availableSize = FileSizeUtil.getFileAvailableSize(FileUtil.getPathTop());
		LogUtil.v(TAG + " -> blockSize = " + blockSize + ", availableSize = " + availableSize);
		LogUtil.v(TAG + " -> blockSize = " + FileSizeUtil.formatFileAutoSize(blockSize)
				+ ", availableSize = " + FileSizeUtil.formatFileAutoSize(availableSize));
	}
	
	private void testLogFileUtil() {
		LogFileUtil.m("m");
		
		LogFileUtil.v(TAG, "v");
		LogFileUtil.v(TAG, "v", LogFileUtil.LOG_LOCATION_PARENT);
		
		LogFileUtil.i(TAG, "i");
		LogFileUtil.i(TAG, "i", LogFileUtil.LOG_LOCATION_PARENT);
		
		LogFileUtil.e(TAG, "e");
		LogFileUtil.e(TAG, "e", LogFileUtil.LOG_LOCATION_PARENT);
	}
}
