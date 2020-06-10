package com.yline.log;

import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import com.yline.application.SDKConfig;
import com.yline.utils.FileSizeUtil;
import com.yline.utils.FileUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author yline 2017/3/10 -- 13:34
 * @version 1.0.0
 */
public final class LogFileUtil {
    /**
     * log trace 抛出的位置,两层,即:使用该工具的子类的位置
     */
    public static final int LOG_LOCATION_PARENT = 4;
    
	private static final String TAG = "LogFile"; // 文件夹名称
    
    /**
     * LogFileUtil 错误日志tag
     */
    private static final String TAG_ERROR = "LogFileUtil error -> ";
    /**
     * 写入文件编号,默认
     */
    private static final int START_COUNT = 0;

    // 信息格式
    /**
     * 写入文件最大编号
     */
    private static final int MAX_COUNT = 10;
    /**
     * 写入文件,每个文件大小2M
     */
    private static final int MAX_SIZE_OF_TXT = 2 * 1024 * 1024;

    /**
     * log trace 抛出的位置,两层,即:使用该工具的当前位置,作为默认
     */
    private static final int LOG_LOCATION_NOW = 3;
    /**
     * 写入文件,路径下保存的文件名称
     */
    private static final String LOG_FILE_TXT_NAME = "_log.txt";

    // 安全级别
    private static final String V = "V";
    private static final String D = "D";
    private static final String I = "I";
    private static final String W = "W";
    private static final String E = "E";

    // 总格式
    private static final String FORMAT = "%s.xxx->%s%s -> %s"; // 时间.xxx->定位 tag -> content
    private static final String FORMAT_TAG_TYPE = "%s(%s):%s/"; // 时间 + type内容
    private static final String FORMAT_TAG_LOCATION = "%s.%s(L:%d):"; // 类名、方法名、行数
    private static final String FORMAT_TAG_MSG = "xxx->%s->%s"; // tag、content

    /**
     * 写入文件,文件夹,路径
     */
    private static File logDirFile;
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

    public static void init(Context context, SDKConfig sdkConfig) {
        logDirFile = context.getExternalFilesDir(TAG);
        isSDKLog = sdkConfig.isSDKLog();
        isUtilLog = sdkConfig.isUtilLog();
        isUtilLogToFile = sdkConfig.isUtilLogToFile();
        isUtilLogLocation = sdkConfig.isUtilLogLocation();
        isUtilLogBySystem = sdkConfig.isUtilLogBySystem();
    }

    /**
     * 设置默认的标签
     *
     * @param content 内容
     */
    public static void m(String content) {
        print(V, LOG_LOCATION_NOW, TAG, content);
    }

    /**
     * 设置默认的标签
     *
     * @param content 内容
     */
    public static void v(String content) {
        print(V, LOG_LOCATION_NOW, TAG, content);
    }

    /**
     * @param tag     标签
     * @param content 内容
     */
    public static void v(String tag, String content) {
        print(V, LOG_LOCATION_NOW, tag, content);
    }

    /**
     * @param tag      标签
     * @param content  内容
     * @param location 定位位置
     */
    public static void v(String tag, String content, int location) {
        print(V, location, tag, content);
    }

    /**
     * @param tag     标签
     * @param content 内容
     */
    public static void i(String tag, String content) {
        print(I, LOG_LOCATION_NOW, tag, content);
    }

    /**
     * @param tag      标签
     * @param content  内容
     * @param location 定位位置
     */
    public static void i(String tag, String content, int location) {
        print(I, location, tag, content);
    }

	/**
	 * @param content 内容
	 */
	public static void e(String content) {
		print(E, LOG_LOCATION_NOW, TAG, content);
	}
    
    /**
     * @param tag     标签
     * @param content 内容
     */
    public static void e(String tag, String content) {
        print(E, LOG_LOCATION_NOW, tag, content);
    }

    /**
     * @param tag      标签
     * @param content  内容
     * @param location 定位位置
     */
    public static void e(String tag, String content, int location) {
        print(E, location, tag, content);
    }

    /**
     * @param tag     标签
     * @param content 内容
     * @param tr      错误信息
     */
    public static void e(String tag, String content, Throwable tr) {
        print(E, LOG_LOCATION_NOW, tag, content + "\n" + android.util.Log.getStackTraceString(tr));
    }

    private static String generateTagTime(String type) {
        // 日期 时间: 级别
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA).format(System.currentTimeMillis());
        if (TextUtils.isEmpty(type)) {
            type = E;
        }
        return String.format(FORMAT_TAG_TYPE, time, Process.myTid(), type);
    }

    private static String generateTagLocation(int location) {
        if (isUtilLogLocation) {
            StackTraceElement caller = new Throwable().getStackTrace()[location];
            String clazzName = caller.getClassName();
            clazzName = clazzName.substring(clazzName.lastIndexOf(".") + 1);

            return String.format(Locale.CHINA, FORMAT_TAG_LOCATION, clazzName, caller.getMethodName(), caller.getLineNumber());
        } else {
            return "";
        }
    }

    /**
     * 统一打印日志
     */
    private static void print(String type, int location, String tag, String content) {
        if (isUtilLog && isSDKLog) {
            if (isUtilLogBySystem) {
                System.out.println(String.format(FORMAT, "", generateTagLocation(location), tag, content));
            } else {
                switch (type) {
                    case V:
                        android.util.Log.v(generateTagLocation(location), String.format(FORMAT_TAG_MSG, tag, content));
                        break;
                    case D:
                        android.util.Log.d(generateTagLocation(location), String.format(FORMAT_TAG_MSG, tag, content));
                        break;
                    case I:
                        android.util.Log.i(generateTagLocation(location), String.format(FORMAT_TAG_MSG, tag, content));
                        break;
                    case W:
                        android.util.Log.w(generateTagLocation(location), String.format(FORMAT_TAG_MSG, tag, content));
                        break;
                    case E:
                        android.util.Log.e(generateTagLocation(location), String.format(FORMAT_TAG_MSG, tag, content));
                        break;
                    default:
                        android.util.Log.v(generateTagLocation(location), String.format(FORMAT_TAG_MSG, tag, content));
                        break;
                }
            }
        }

        if (isUtilLogToFile && isSDKLog) {
            writeLogToFile(String.format(FORMAT, generateTagTime(type), generateTagLocation(location), tag, content));
        }
    }

    /**
     * 写日志入文件
     *
     * @param content 日志内容
     */
    private synchronized static void writeLogToFile(String content) {
        File dirFile = logDirFile;
        File file = FileUtil.create(logDirFile, START_COUNT + LOG_FILE_TXT_NAME);
        if (null == file) {
            LogUtil.e(TAG_ERROR + "sdcard file create failed");
            return;
        }

        if (!FileUtil.write(file, content)) {
            LogUtil.e(TAG_ERROR + "FileUtil write failed");
            return;
        }

        long size = FileSizeUtil.getFileSize(file);
        if (-1 == size) {
            LogUtil.e(TAG_ERROR + "sdcard getFileSize failed");
            return;
        }

        // 分文件、限制文件个数
        if (size > MAX_SIZE_OF_TXT) {
            for (int count = MAX_COUNT; count >= START_COUNT; count--) {
                if (count == MAX_COUNT) {
                    if (FileUtil.isExist(dirFile, count + LOG_FILE_TXT_NAME) && !FileUtil.delete(dirFile, MAX_COUNT + LOG_FILE_TXT_NAME)) {
                        LogUtil.e(TAG_ERROR + "FileUtil deleteFile failed");
                        return;
                    }
                } else {
                    if (FileUtil.isExist(dirFile, count + LOG_FILE_TXT_NAME) && !FileUtil.rename(dirFile, count + LOG_FILE_TXT_NAME, (count + 1) + LOG_FILE_TXT_NAME)) {
                        LogUtil.e(TAG_ERROR + "FileUtil renameFile failed");
                        return;
                    }
                }
            }
        }
    }
}
