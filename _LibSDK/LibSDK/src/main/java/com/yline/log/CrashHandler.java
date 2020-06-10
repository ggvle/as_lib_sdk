package com.yline.log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.yline.application.BaseApplication;
import com.yline.application.SDKManager;
import com.yline.utils.FileUtil;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author yline 2017/3/10 -- 13:34
 * @version 1.0.0
 */
public final class CrashHandler implements UncaughtExceptionHandler {
	private static final String TAG = "CrashHandler"; // 文件夹名称
	
	/**
	 * 文件后缀
	 */
	private static final String CRASH_TXT_FILE = "-CrashHandler.txt";
	
	/**
	 * 该文件初始化等是否debug
	 */
	private boolean isDebug = true;
	
	/**
	 * 保存文件的，文件夹
	 */
	private File crashDirFile;
	
	private Context mContext;
	
	// 系统默认的UncaughtException处理类
	private UncaughtExceptionHandler mDefaultHandler;
	
	@SuppressLint("StaticFieldLeak")
	private static CrashHandler crashHandler;
	
	private static CrashHandler getInstance() {
		if (null == crashHandler) {
			crashHandler = new CrashHandler();
		}
		return crashHandler;
	}
	
	private CrashHandler() {
	}
	
	public static void initConfig(Context context) {
		getInstance().init(context);
	}
	
	private void init(Context context) {
		isDebug = SDKManager.getSdkConfig().isSDKLog();
		crashDirFile = context.getExternalFilesDir(TAG);
		
		if (isDebug) {
			LogFileUtil.m("CrashHandler -> init start, crashDirFile -> " + (null == crashDirFile ? "null" : crashDirFile.getAbsolutePath()));
		}
		
		mContext = context;
		// 获取系统默认的UncaughtExceptionHandler
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 将该CrashHandler实例设置为默认异常处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
		
		if (isDebug) {
			LogFileUtil.m("CrashHandler -> init end");
		}
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (isDebug) {
			LogUtil.v(TAG + " uncaughtException dealing");
		}
		
		// 收集错误信息
		if (!handleException(ex)) {
			LogUtil.v(TAG + "uncaughtException exception is null");
		}
		
		if (ex instanceof UnsatisfiedLinkError) {
			BaseApplication.finishActivity();
		}
		
		mDefaultHandler.uncaughtException(thread, ex);
	}
	
	/**
	 * 处理此时的异常
	 *
	 * @param ex 异常信息
	 * @return 是否处理成功
	 */
	private boolean handleException(Throwable ex) {
		if (null == ex) {
			return false;
		}
		
		Map<String, String> infoMap = collectionDeviceInfo(mContext);
		String throwableString = calculateCrashInfo(infoMap, ex);
		writeThrowableToFile(throwableString);
		
		uploadException();
		
		return true;
	}
	
	/**
	 * 上传文件到服务器
	 */
	private void uploadException() {
		// TODO
	}
	
	/**
	 * 收集设备参数信息，并不会, 打印任何信息
	 */
	private Map<String, String> collectionDeviceInfo(Context context) {
		Map<String, String> deviceInfoMap = new HashMap<>();
		
		// 时间
		String crashTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA).format(System.currentTimeMillis());
		deviceInfoMap.put("crashTime", crashTime);
		
		// 包相关
		try {
			if (null != context) {
				PackageManager packageManager = context.getPackageManager();
				if (null != packageManager) {
					PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
					if (null != packageInfo) {
						String versionName = (null == packageInfo.versionName) ? "null" : packageInfo.versionName;
						String versionCode = String.valueOf(packageInfo.versionCode);
						deviceInfoMap.put("versionName", versionName);
						deviceInfoMap.put("versionCode", versionCode);
					}
				}
			}
		} catch (PackageManager.NameNotFoundException e) {
			// TODO
		}
		
		// 反射机制
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				deviceInfoMap.put(field.getName(), String.valueOf(field.get("")));
			} catch (IllegalAccessException e) {
				// TODO
			}
		}
		return deviceInfoMap;
	}
	
	private String calculateCrashInfo(Map<String, String> deviceInfoMap, Throwable ex) {
		// Key - Value
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<String, String> entry : deviceInfoMap.entrySet()) {
			stringBuilder.append(entry.getKey());
			stringBuilder.append(" -> ");
			stringBuilder.append(entry.getValue());
			stringBuilder.append('\r');
			stringBuilder.append('\n');
		}
		
		// throwable info
		String causeString = getThrowableInfo(ex);
		stringBuilder.append(causeString);
		
		return stringBuilder.toString();
	}
	
	private String getThrowableInfo(Throwable ex) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter); // 写入错误信息
		Throwable cause = ex.getCause();
		while (null != cause) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		return writer.toString();
	}
	
	/**
	 * 写日志入文件，打印日志
	 *
	 * @param content 日志内容
	 */
	private synchronized void writeThrowableToFile(String content) {
		// 路径名、文件名
		String crashTime = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS", Locale.CHINA).format(System.currentTimeMillis());
		File file = FileUtil.create(crashDirFile, crashTime + CRASH_TXT_FILE);
		if (null == file) {
			LogUtil.e(TAG + " sdcard file create failed");
			return;
		}
		
		// 写入日志
		if (!FileUtil.write(file, content)) {
			LogUtil.e(TAG + " write failed");
		}
	}
}
