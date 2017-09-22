package com.yline.log;

import android.content.Context;
import android.text.TextUtils;

import com.yline.application.SDKConfig;
import com.yline.utils.FileSizeUtil;
import com.yline.utils.FileUtil;
import com.yline.utils.LogUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author yline 2017/3/10 -- 13:34
 * @version 1.0.0
 */
public final class LogFileUtil
{
	/**
	 * log trace 抛出的位置,两层,即:使用该工具的子类的位置
	 */
	public static final int LOG_LOCATION_PARENT = 3;
	private static final String TAG = "LogFile";
	/**
	 * LogFileUtil 错误日志tag
	 */
	private static final String TAG_ERROR = "LogFileUtil error -> ";
	/**
	 * 写入文件编号,默认
	 */
	private static final int START_COUNT = 0;
	/**
	 * 写入文件最大编号
	 */
	private static final int MAX_COUNT = 10;
	/**
	 * 写入文件,每个文件大小2M
	 */
	private static final int MAX_SIZE_OF_TXT = 2 * 1024 * 1024;
	
	// 信息格式
	/**
	 * 写入文件,路径下保存的文件名称
	 */
	private static final String LOG_FILE_TXT_NAME = "_log.txt";
	/**
	 * 默认自带前缀
	 */
	private static final String TAG_DEFAULT = "xxx->";
	/**
	 * tag 定位  默认格式
	 */
	private static final String TAG_DEFAULT_LOCATION = TAG_DEFAULT + "%s.%s(L:%d): ";
	/**
	 * msg 默认格式
	 */
	private static final String MSG_DEFAULT = "%s -> %s";
	/**
	 * tag 文件默认格式<日期,级别>
	 */
	private static final String TAG_FILE_DEFAULT = TAG_DEFAULT + "%s: %s/";
	/**
	 * tag 文件定位默认格式
	 */
	private static final String TAG_FILE_DEFAULT_LOCATION = TAG_DEFAULT + "%s: %s/%s.%s(L:%d): ";
	
	// 找到位置
	/**
	 * msg 文件定位默认格式
	 */
	private static final String MSG_FILE_DEFAULT = "%s %s -> %s";
	/**
	 * log trace 抛出的位置,两层,即:使用该工具的当前位置,作为默认
	 */
	private static final int LOG_LOCATION_NOW = 2;
	// 安全级别
	private static final String V = "V";
	
	private static final String D = "D";
	
	private static final String I = "I";
	
	private static final String W = "W";
	
	private static final String E = "E";
	
	/**
	 * 写入文件,文件夹,路径
	 */
	private static String logDirPath;
	
	// 三个开关
	
	/**
	 * SDK日志内容是否输出
	 */
	private static boolean isSDKLog;
	
	/**
	 * log 开关
	 */
	private static boolean isUtilLog;
	
	/**
	 * 是否写到文件
	 */
	private static boolean isUtilLogToFile;
	
	/**
	 * 是否定位
	 */
	private static boolean isUtilLogLocation;
	
	/**
	 * 正常的LogCat失效时，使用sysOut
	 */
	private static boolean isUtilLogBySystem;
	
	public static void init(Context context, SDKConfig sdkConfig)
	{
		logDirPath = context.getExternalFilesDir(TAG).getAbsolutePath();
		isSDKLog = sdkConfig.isSDKLog();
		isUtilLog = sdkConfig.isUtilLog();
		isUtilLogToFile = sdkConfig.isUtilLogToFile();
		isUtilLogLocation = sdkConfig.isUtilLogLocation();
		isUtilLogBySystem = sdkConfig.isUtilLogBySystem();
	}
	
	/**
	 * 获取本地 打印日志地址
	 *
	 * @return 日志地址
	 */
	public static String getLogDirPath()
	{
		return logDirPath;
	}
	
	/**
	 * 设置默认的标签
	 *
	 * @param content 内容
	 */
	public static void m(String content)
	{
		if (isUtilLog && isSDKLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(LOG_LOCATION_NOW) + String.format(MSG_DEFAULT, TAG, content));
			}
			else
			{
				android.util.Log.v(generateTag(LOG_LOCATION_NOW), String.format(MSG_DEFAULT, TAG, content));
			}
		}
		
		if (isUtilLogToFile && isSDKLog)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(V, LOG_LOCATION_NOW), TAG, content));
		}
	}
	
	/**
	 * @param tag     标签
	 * @param content 内容
	 */
	public static void m(String tag, String content)
	{
		if (isUtilLog && isSDKLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(LOG_LOCATION_NOW) + String.format(MSG_DEFAULT, tag, content));
			}
			else
			{
				android.util.Log.v(generateTag(LOG_LOCATION_NOW), String.format(MSG_DEFAULT, tag, content));
			}
		}
		
		if (isUtilLogToFile && isSDKLog)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(V, LOG_LOCATION_NOW), tag, content));
		}
	}
	
	/**
	 * @param tag     标签
	 * @param content 内容
	 * @param tr      错误信息
	 */
	public static void m(String tag, String content, Throwable tr)
	{
		if (isUtilLog && isSDKLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(LOG_LOCATION_NOW) + String.format(MSG_DEFAULT, tag, content) + android.util.Log.getStackTraceString(tr));
			}
			else
			{
				android.util.Log.e(generateTag(LOG_LOCATION_NOW), String.format(MSG_DEFAULT, tag, content), tr);
			}
		}
		
		if (isUtilLogToFile && isSDKLog)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(E, LOG_LOCATION_NOW), tag, content) + '\n' + android.util.Log.getStackTraceString(tr));
		}
	}
	
	/**
	 * 设置默认的标签
	 *
	 * @param content 内容
	 */
	public static void v(String content)
	{
		if (isUtilLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(LOG_LOCATION_NOW) + String.format(MSG_DEFAULT, TAG, content));
			}
			else
			{
				android.util.Log.v(generateTag(LOG_LOCATION_NOW), String.format(MSG_DEFAULT, TAG, content));
			}
		}
		
		if (isUtilLogToFile)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(V, LOG_LOCATION_NOW), TAG, content));
		}
	}
	
	/**
	 * @param tag     标签
	 * @param content 内容
	 */
	public static void v(String tag, String content)
	{
		if (isUtilLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(LOG_LOCATION_NOW) + String.format(MSG_DEFAULT, tag, content));
			}
			else
			{
				android.util.Log.v(generateTag(LOG_LOCATION_NOW), String.format(MSG_DEFAULT, tag, content));
			}
		}
		
		if (isUtilLogToFile)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(V, LOG_LOCATION_NOW), tag, content));
		}
	}
	
	/**
	 * @param tag      标签
	 * @param content  内容
	 * @param location 定位位置
	 */
	public static void v(String tag, String content, int location)
	{
		if (isUtilLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(location) + String.format(MSG_DEFAULT, tag, content));
			}
			else
			{
				android.util.Log.v(generateTag(location), String.format(MSG_DEFAULT, tag, content));
			}
		}
		
		if (isUtilLogToFile)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(V, location), tag, content));
		}
	}
	
	/**
	 * @param tag     标签
	 * @param content 内容
	 */
	public static void d(String tag, String content)
	{
		if (isUtilLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(LOG_LOCATION_NOW) + String.format(MSG_DEFAULT, tag, content));
			}
			else
			{
				android.util.Log.d(generateTag(LOG_LOCATION_NOW), String.format(MSG_DEFAULT, tag, content));
			}
		}
		
		if (isUtilLogToFile)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(D, LOG_LOCATION_NOW), tag, content));
		}
	}
	
	/**
	 * @param tag      标签
	 * @param content  内容
	 * @param location 定位位置
	 */
	public static void d(String tag, String content, int location)
	{
		if (isUtilLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(location) + String.format(MSG_DEFAULT, tag, content));
			}
			else
			{
				android.util.Log.d(generateTag(location), String.format(MSG_DEFAULT, tag, content));
			}
		}
		
		if (isUtilLogToFile)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(D, location), tag, content));
		}
	}
	
	/**
	 * @param tag     标签
	 * @param content 内容
	 */
	public static void i(String tag, String content)
	{
		if (isUtilLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(LOG_LOCATION_NOW) + String.format(MSG_DEFAULT, tag, content));
			}
			else
			{
				android.util.Log.i(generateTag(LOG_LOCATION_NOW), String.format(MSG_DEFAULT, tag, content));
			}
		}
		
		if (isUtilLogToFile)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(I, LOG_LOCATION_NOW), tag, content));
		}
	}
	
	/**
	 * @param tag      标签
	 * @param content  内容
	 * @param location 定位位置
	 */
	public static void i(String tag, String content, int location)
	{
		if (isUtilLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(location) + String.format(MSG_DEFAULT, tag, content));
			}
			else
			{
				android.util.Log.i(generateTag(location), String.format(MSG_DEFAULT, tag, content));
			}
		}
		
		if (isUtilLogToFile)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(I, location), tag, content));
		}
	}
	
	/**
	 * @param tag     标签
	 * @param content 内容
	 * @param tr      错误信息
	 */
	public static void i(String tag, String content, Throwable tr)
	{
		if (isUtilLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(LOG_LOCATION_NOW) + String.format(MSG_DEFAULT, tag, content) + android.util.Log.getStackTraceString(tr));
			}
			else
			{
				android.util.Log.i(generateTag(LOG_LOCATION_NOW), String.format(MSG_DEFAULT, tag, content), tr);
			}
		}
		
		if (isUtilLogToFile)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(I, LOG_LOCATION_NOW), tag, content) + '\n' + android.util.Log.getStackTraceString(tr));
		}
	}
	
	/**
	 * @param tag      标签
	 * @param content  内容
	 * @param location 定位位置
	 * @param tr       错误信息
	 */
	public static void i(String tag, String content, int location, Throwable tr)
	{
		if (isUtilLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(location) + String.format(MSG_DEFAULT, tag, content) + android.util.Log.getStackTraceString(tr));
			}
			else
			{
				android.util.Log.i(generateTag(location), String.format(MSG_DEFAULT, tag, content), tr);
			}
		}
		
		if (isUtilLogToFile)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(I, location), tag, content) + '\n' + android.util.Log.getStackTraceString(tr));
		}
	}
	
	/**
	 * @param tag     标签
	 * @param content 内容
	 */
	public static void w(String tag, String content)
	{
		if (isUtilLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(LOG_LOCATION_NOW) + String.format(MSG_DEFAULT, tag, content));
			}
			else
			{
				android.util.Log.w(generateTag(LOG_LOCATION_NOW), String.format(MSG_DEFAULT, tag, content));
			}
		}
		
		if (isUtilLogToFile)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(W, LOG_LOCATION_NOW), tag, content));
		}
	}
	
	/**
	 * @param tag      标签
	 * @param content  内容
	 * @param location 定位位置
	 */
	public static void w(String tag, String content, int location)
	{
		if (isUtilLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(location) + String.format(MSG_DEFAULT, tag, content));
			}
			else
			{
				android.util.Log.w(generateTag(location), String.format(MSG_DEFAULT, tag, content));
			}
		}
		
		if (isUtilLogToFile)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(W, location), tag, content));
		}
	}
	
	/**
	 * @param tag     标签
	 * @param content 内容
	 */
	public static void e(String tag, String content)
	{
		if (isUtilLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(LOG_LOCATION_NOW) + String.format(MSG_DEFAULT, tag, content));
			}
			else
			{
				android.util.Log.e(generateTag(LOG_LOCATION_NOW), String.format(MSG_DEFAULT, tag, content));
			}
		}
		
		if (isUtilLogToFile)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(E, LOG_LOCATION_NOW), tag, content));
		}
	}
	
	/**
	 * @param tag      标签
	 * @param content  内容
	 * @param location 定位位置
	 */
	public static void e(String tag, String content, int location)
	{
		if (isUtilLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(location) + String.format(MSG_DEFAULT, tag, content));
			}
			else
			{
				android.util.Log.e(generateTag(location), String.format(MSG_DEFAULT, tag, content));
			}
		}
		
		if (isUtilLogToFile)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(E, location), tag, content));
		}
	}
	
	/**
	 * @param tag     标签
	 * @param content 内容
	 * @param tr      错误信息
	 */
	public static void e(String tag, String content, Throwable tr)
	{
		if (isUtilLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(LOG_LOCATION_NOW) + String.format(MSG_DEFAULT, tag, content) + android.util.Log.getStackTraceString(tr));
			}
			else
			{
				android.util.Log.e(generateTag(LOG_LOCATION_NOW), String.format(MSG_DEFAULT, tag, content), tr);
			}
		}
		
		if (isUtilLogToFile)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(E, LOG_LOCATION_NOW), tag, content) + '\n' + android.util.Log.getStackTraceString(tr));
		}
	}
	
	/**
	 * @param tag      标签
	 * @param content  内容
	 * @param location 定位位置
	 * @param tr       错误信息
	 */
	public static void e(String tag, String content, int location, Throwable tr)
	{
		if (isUtilLog)
		{
			if (isUtilLogBySystem)
			{
				System.out.println(generateTag(location) + String.format(MSG_DEFAULT, tag, content) + android.util.Log.getStackTraceString(tr));
			}
			else
			{
				android.util.Log.e(generateTag(location), String.format(MSG_DEFAULT, tag, content), tr);
			}
		}
		
		if (isUtilLogToFile)
		{
			writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(E, location), tag, content) + '\n' + android.util.Log.getStackTraceString(tr));
		}
	}
	
	/**
	 * 拼接日志tag,该tag专为打在eclipse,DDms上准备
	 *
	 * @param location 定位的级别
	 * @return 拼接而成的头部
	 */
	private static String generateTag(int location)
	{
		if (isUtilLogLocation)
		{
			StackTraceElement caller = new Throwable().getStackTrace()[location];
			String clazzName = caller.getClassName();
			clazzName = clazzName.substring(clazzName.lastIndexOf(".") + 1);
			
			return String.format(Locale.CHINA, TAG_DEFAULT_LOCATION, clazzName, caller.getMethodName(), caller.getLineNumber());
		}
		else
		{
			return TAG_DEFAULT;
		}
	}
	
	/**
	 * 拼接 日志tag,该tag专为写入file中准备
	 *
	 * @param type 数据类型
	 * @return 拼接而成的头部
	 */
	private static String generateFileTag(String type, int location)
	{
		// 日期 时间: 级别
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA).format(Long.valueOf(System.currentTimeMillis()));
		if (TextUtils.isEmpty(type))
		{
			type = E;
		}
		
		if (isUtilLogLocation)
		{
			StackTraceElement caller = new Throwable().getStackTrace()[location];
			String clazzName = caller.getClassName();
			clazzName = clazzName.substring(clazzName.lastIndexOf(".") + 1);
			
			return String.format(Locale.CHINA, TAG_FILE_DEFAULT_LOCATION, time, type, clazzName, caller.getMethodName(), caller.getLineNumber());
		}
		else
		{
			return String.format(TAG_FILE_DEFAULT, time, type);
		}
	}
	
	/**
	 * 写日志入文件
	 *
	 * @param content 日志内容
	 */
	private synchronized static void writeLogToFile(String content)
	{
		String path = logDirPath;
		if (null == path)
		{
			LogUtil.e(TAG_ERROR + "sdcard path is null");
			return;
		}
		
		File dirFile = FileUtil.createDir(path + File.separator);
		if (null == dirFile)
		{
			LogUtil.e(TAG_ERROR + "sdcard dirFile create failed path = " + path + logDirPath);
			return;
		}
		
		File file = FileUtil.create(dirFile, START_COUNT + LOG_FILE_TXT_NAME);
		if (null == file)
		{
			LogUtil.e(TAG_ERROR + "sdcard file create failed");
			return;
		}
		
		if (!FileUtil.write(file, content))
		{
			LogUtil.e(TAG_ERROR + "FileUtil write failed");
			return;
		}
		
		long size = FileSizeUtil.getFileSize(file);
		if (-1 == size)
		{
			LogUtil.e(TAG_ERROR + "sdcard getFileSize failed");
			return;
		}
		
		// 分文件、限制文件个数
		if (size > MAX_SIZE_OF_TXT)
		{
			for (int count = MAX_COUNT; count >= START_COUNT; count--)
			{
				if (count == MAX_COUNT)
				{
					if (FileUtil.isExist(dirFile, count + LOG_FILE_TXT_NAME) && !FileUtil.delete(dirFile, MAX_COUNT + LOG_FILE_TXT_NAME))
					{
						LogUtil.e(TAG_ERROR + "FileUtil deleteFile failed");
						return;
					}
				}
				else
				{
					if (FileUtil.isExist(dirFile, count + LOG_FILE_TXT_NAME) && !FileUtil.rename(dirFile, count + LOG_FILE_TXT_NAME, (count + 1) + LOG_FILE_TXT_NAME))
					{
						LogUtil.e(TAG_ERROR + "FileUtil renameFile failed");
						return;
					}
				}
			}
		}
	}
}
