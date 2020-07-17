package com.demo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.yline.log.LogFileUtil;
import com.yline.test.BaseTestFragment;
import com.yline.utils.FileSizeUtil;
import com.yline.utils.FileUtil;
import com.yline.utils.IOUtil;
import com.yline.log.LogUtil;
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
				
				String result = TimeConvertUtil.INSTANCE.stamp2FormatTime(oldTime);
				LogFileUtil.INSTANCE.v("result = " + result);
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
				File file = FileUtil.INSTANCE.create(dir, "sample.txt");
				
				try {
					FileOutputStream fileOutputStream = new FileOutputStream(file, true);
					IOUtil.INSTANCE.write(System.currentTimeMillis() + ";汉字;;", fileOutputStream);
					IOUtil.INSTANCE.close(fileOutputStream);
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
				String encrypt = AESUtils.INSTANCE.encrypt("yline", key);
				LogUtil.INSTANCE.v("encrypt = " + encrypt);
				
				String decrypt = AESUtils.INSTANCE.decrypt(encrypt, key);
				LogUtil.INSTANCE.v("decrypt = " + decrypt);
			}
		});
		
		addButton("DESUtils", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String key = "12345678";
				String encrypt = DESUtils.INSTANCE.encrypt("yline", key);
				LogUtil.INSTANCE.v("encrypt = " + encrypt);
				
				String decrypt = DESUtils.INSTANCE.decrypt(encrypt, key);
				LogUtil.INSTANCE.v("decrypt = " + decrypt);
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
		KeyPair keyPair = RSAUtils.INSTANCE.createKeyPair();
		LogUtil.INSTANCE.v("diffTime = " + (System.currentTimeMillis() - startTime));
		startTime = System.currentTimeMillis();
		
		// 私钥
		String privateKey = RSAUtils.INSTANCE.getPrivateKey(keyPair);
		LogUtil.INSTANCE.v("privateKey = " + privateKey);
		
		LogUtil.INSTANCE.v("diffTime = " + (System.currentTimeMillis() - startTime));
		startTime = System.currentTimeMillis();
		
		// 公钥
		String publicKey = RSAUtils.INSTANCE.getPublicKey(keyPair);
		LogUtil.INSTANCE.v("publicKey = " + publicKey);
		
		LogUtil.INSTANCE.v("diffTime = " + (System.currentTimeMillis() - startTime));
		startTime = System.currentTimeMillis();
		
		// 加密
		String encryptA = RSAUtils.INSTANCE.encrypt("yline", publicKey);
		LogUtil.INSTANCE.v("encryptA = " + encryptA);
		
		LogUtil.INSTANCE.v("diffTime = " + (System.currentTimeMillis() - startTime));
		startTime = System.currentTimeMillis();
		
		// 解密
		String decryptA = RSAUtils.INSTANCE.decrypt(encryptA, privateKey);
		LogUtil.INSTANCE.v("decryptA = " + decryptA);
		
		LogUtil.INSTANCE.v("diffTime = " + (System.currentTimeMillis() - startTime));
		startTime = System.currentTimeMillis();
		
		final String SRC = "yline";
		// 数字证书
		String signA = RSAUtils.INSTANCE.sign(SRC, privateKey);
		LogUtil.INSTANCE.v("signA = " + signA);
		
		LogUtil.INSTANCE.v("diffTime = " + (System.currentTimeMillis() - startTime));
		startTime = System.currentTimeMillis();
		
		// 校验数字证书
		boolean isRight = RSAUtils.INSTANCE.verifySign(SRC, publicKey, signA);
		LogUtil.INSTANCE.v("isRight = " + isRight);
		
		LogUtil.INSTANCE.v("diffTime = " + (System.currentTimeMillis() - startTime));
	}
	
	private void testHex() {
		String hexString = HexUtils.INSTANCE.encodeHex("yline".getBytes());
		byte[] sourceBytes = HexUtils.INSTANCE.decodeHex(hexString.toCharArray());
		LogUtil.INSTANCE.v(new String(sourceBytes));
	}
	
	private void testMD5() {
		String encryptA = MD5Utils.INSTANCE.encrypt("yline".getBytes());
		LogUtil.INSTANCE.v("encryptA = " + encryptA);
		
		File fileB = FileUtil.INSTANCE.getFileTop("xmind_show.jpg");
		String encryptB = MD5Utils.INSTANCE.encrypt(fileB);
		LogUtil.INSTANCE.v("encryptB = " + encryptB);
	}
	
	private void testSPUtil(Context context) {
		SPUtil.INSTANCE.put(context, "null", null);
		String result = (String) SPUtil.INSTANCE.get(context, "null", null);
		LogFileUtil.INSTANCE.v(TAG, "put -> value = " + result);
		
		// 增加两条数据
		SPUtil.INSTANCE.put(context, "username", "utilUsername");
		SPUtil.INSTANCE.put(context, "password", "utilPassword");
		LogFileUtil.INSTANCE.v(TAG, "put -> value - utilUsername");
		LogFileUtil.INSTANCE.v(TAG, "put -> value - utilPassword");
		
		// 更新两条数据
		SPUtil.INSTANCE.put(context, "username", "utilUpdateUsername");
		SPUtil.INSTANCE.put(context, "password", "utilUpdatePassword");
		LogFileUtil.INSTANCE.v(TAG, "put -> value - utilUpdateUsername");
		LogFileUtil.INSTANCE.v(TAG, "put -> value - utilUpdatePassword");
		
		// 删除一条数据
		SPUtil.INSTANCE.remove(context, "password");
		LogFileUtil.INSTANCE.v(TAG, "remove -> key - password");
		
		// 获取两条数据
		String username = (String) SPUtil.INSTANCE.get(context, "username", "");
		String password = (String) SPUtil.INSTANCE.get(context, "password", "");
		LogFileUtil.INSTANCE.v(TAG, "get -> key - username");
		LogFileUtil.INSTANCE.v(TAG, "get -> key - password");
		LogFileUtil.INSTANCE.i(TAG, "usrname = " + username + ",password = " + password);
	}
	
	private void testLogUtil() {
		LogUtil.INSTANCE.v("test -> v");
		LogUtil.INSTANCE.v("test -> v", LogUtil.INSTANCE.getLOG_LOCATION_PARENT());
		
		LogUtil.INSTANCE.i("test -> i");
		LogUtil.INSTANCE.i("test -> i", LogUtil.INSTANCE.getLOG_LOCATION_PARENT());
		
		LogUtil.INSTANCE.e("test -> e");
		LogUtil.INSTANCE.e("test -> e", LogUtil.INSTANCE.getLOG_LOCATION_PARENT());
		LogUtil.INSTANCE.e("test -> e", new Exception("test -> e -> Exception"));
		LogUtil.INSTANCE.e("test -> e", LogUtil.INSTANCE.getLOG_LOCATION_PARENT(), new Exception("test -> e -> Exception"));
	}
	
	private void testFileUtil() {
		LogUtil.INSTANCE.v(TAG + " -> FileUtil.getPath() = " + FileUtil.INSTANCE.getPathTop());
		
		String pathRoot = FileUtil.INSTANCE.getPathRoot();
		LogUtil.INSTANCE.v(TAG + " -> pathRoot = " + pathRoot);
		
		long blockSize = FileSizeUtil.INSTANCE.getFileBlockSize(FileUtil.INSTANCE.getPathTop());
		long availableSize = FileSizeUtil.INSTANCE.getFileAvailableSize(FileUtil.INSTANCE.getPathTop());
		LogUtil.INSTANCE.v(TAG + " -> blockSize = " + blockSize + ", availableSize = " + availableSize);
		LogUtil.INSTANCE.v(TAG + " -> blockSize = " + FileSizeUtil.INSTANCE.formatFileAutoSize(blockSize)
				+ ", availableSize = " + FileSizeUtil.INSTANCE.formatFileAutoSize(availableSize));
	}
	
	private void testLogFileUtil() {
		LogFileUtil.INSTANCE.m("m");
		
		LogFileUtil.INSTANCE.v(TAG, "v");
		LogFileUtil.INSTANCE.v(TAG, "v", LogFileUtil.LOG_LOCATION_PARENT);
		
		LogFileUtil.INSTANCE.i(TAG, "i");
		LogFileUtil.INSTANCE.i(TAG, "i", LogFileUtil.LOG_LOCATION_PARENT);
		
		LogFileUtil.INSTANCE.e(TAG, "e");
		LogFileUtil.INSTANCE.e(TAG, "e", LogFileUtil.LOG_LOCATION_PARENT);
	}
}
