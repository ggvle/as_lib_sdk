package com.yline.log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.text.TextUtils;

import com.yline.application.BaseApplication;
import com.yline.utils.FileUtil;

/**
 * 想要区分应用时,可以采用区分目录或修改"x"的方式
 * simple introduction
 *
 * @author YLine 2016-5-21 -> 下午6:50:25
 * @version
 */
public final class LogFileUtil
{
    private static final String ERROR_LOG_FILE_UTIL = "error_LogFileUtil : "; // 错误日志tag
    
    private static final String LOG_FILE_PATH =
        BaseApplication.FILE_PARENT_PATH + BaseApplication.getBaseConfig().getFileLogPath(); // 日志保存地址
    
    private static final int START_COUNT = 0; // 写入文件编号
    
    private static final int MAX_COUNT = 5; // 文件最大编号
    
    private static final int MAX_SIZE_OF_TXT = 512 * 1024; // 每个文件大小
    
    private static final String LOG_FILE_TXT_NAME = "_yline_log.txt"; // 路径下保存的文件名称
    
    // 三个开关
    private static final boolean isLog = BaseApplication.getBaseConfig().isLog(); // log 开关
    
    private static final boolean isToFile = BaseApplication.getBaseConfig().isLogToFile(); // 是否写到文件
    
    private static final boolean isLogLocation = BaseApplication.getBaseConfig().isLogLocation(); // 是否定位
    
    // 信息格式
    private static final String TAG_DEFAULT = "x->";
    
    private static final String TAG_DEFAULT_LOCATION = "x->%s.%s(L:%d): "; // tag 定位  默认格式
    
    private static final String MSG_DEFAULT = "%s -> %s"; // msg 默认格式
    
    private static final String TAG_FILE_DEFAULT = "x->%s: %s/"; // tag 文件默认格式<日期,级别>
    
    private static final String TAG_FILE_DEFAULT_LOCATION = "x->%s: %s/%s.%s(L:%d): "; // tag 文件定位默认格式
    
    private static final String MSG_FILE_DEFAULT = "%s %s -> %s";
    
    // 安全级别
    private static final String V = "V"; // verbose 详细
    
    private static final String D = "D"; // debug 调试
    
    private static final String I = "I"; // info 通告
    
    private static final String W = "W"; // warm 警告
    
    private static final String E = "E"; // error 错误
    
    private static final String M = "M"; // main 父工程 非报错定位
    
    private static final String MC1 = "MC1"; // 父工程,父类 第一层 非报错定位
    
    // 找到日志位置
    private static final int LOCATION_USER = 2; // 普通位置
    
    private static final int LOCATION_USER_PARENT1 = 3; // 父类中用来子类位置
    
    /**
     * main 父工程 非报错定位
     * @param tag
     * @param content
     */
    public static void m(String tag, String content)
    {
        if (isLog)
        {
            android.util.Log.v(generateTag(LOCATION_USER), String.format(MSG_DEFAULT, tag, content));
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(LOCATION_USER, M), tag, content));
        }
    }
    
    /**
     * main 父工程 非报错定位
     * @param tag
     * @param content
     * @param tr
     */
    public static void m(String tag, String content, Throwable tr)
    {
        if (isLog)
        {
            android.util.Log.v(generateTag(LOCATION_USER), String.format(MSG_DEFAULT, tag, content));
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(LOCATION_USER, M), tag, content) + '\n'
                + android.util.Log.getStackTraceString(tr));
        }
    }
    
    /**
     * main 父类 第一层 非报错定位
     * @param tag
     * @param content
     */
    public static void mC1(String tag, String content)
    {
        if (isLog)
        {
            android.util.Log.v(generateTag(LOCATION_USER_PARENT1), String.format(MSG_DEFAULT, tag, content));
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(LOCATION_USER_PARENT1, MC1), tag, content));
        }
    }
    
    /**
     * main 父类 第一层 非报错定位
     * @param tag
     * @param content
     * @param tr
     */
    public static void mC1(String tag, String content, Throwable tr)
    {
        if (isLog)
        {
            android.util.Log.v(generateTag(LOCATION_USER_PARENT1), String.format(MSG_DEFAULT, tag, content));
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(LOCATION_USER_PARENT1, MC1), tag, content) + '\n'
                + android.util.Log.getStackTraceString(tr));
        }
    }
    
    public static void v(String tag, String content)
    {
        if (isLog)
        {
            android.util.Log.v(generateTag(LOCATION_USER), String.format(MSG_DEFAULT, tag, content));
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(LOCATION_USER, V), tag, content));
        }
    }
    
    public static void v(String tag, String content, Throwable tr)
    {
        if (isLog)
        {
            android.util.Log.v(generateTag(LOCATION_USER), String.format(MSG_DEFAULT, tag, content), tr);
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(LOCATION_USER, V), tag, content) + '\n'
                + android.util.Log.getStackTraceString(tr));
        }
    }
    
    public static void d(String tag, String content)
    {
        if (isLog)
        {
            android.util.Log.d(generateTag(LOCATION_USER), String.format(MSG_DEFAULT, tag, content));
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(LOCATION_USER, D), tag, content));
        }
    }
    
    public static void d(String tag, String content, Throwable tr)
    {
        if (isLog)
        {
            android.util.Log.d(generateTag(LOCATION_USER), String.format(MSG_DEFAULT, tag, content), tr);
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(LOCATION_USER, D), tag, content) + '\n'
                + android.util.Log.getStackTraceString(tr));
        }
    }
    
    public static void i(String tag, String content)
    {
        if (isLog)
        {
            android.util.Log.i(generateTag(LOCATION_USER), String.format(MSG_DEFAULT, tag, content));
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(LOCATION_USER, I), tag, content));
        }
    }
    
    public static void i(String tag, String content, Throwable tr)
    {
        if (isLog)
        {
            android.util.Log.i(generateTag(LOCATION_USER), String.format(MSG_DEFAULT, tag, content), tr);
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(LOCATION_USER, I), tag, content) + '\n'
                + android.util.Log.getStackTraceString(tr));
        }
    }
    
    public static void w(String tag, String content)
    {
        if (isLog)
        {
            android.util.Log.w(generateTag(LOCATION_USER), String.format(MSG_DEFAULT, tag, content));
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(LOCATION_USER, W), tag, content));
        }
    }
    
    public static void w(String tag, String content, Throwable tr)
    {
        if (isLog)
        {
            android.util.Log.w(generateTag(LOCATION_USER), String.format(MSG_DEFAULT, tag, content), tr);
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(LOCATION_USER, W), tag, content) + '\n'
                + android.util.Log.getStackTraceString(tr));
        }
    }
    
    public static void e(String tag, String content)
    {
        if (isLog)
        {
            android.util.Log.e(generateTag(LOCATION_USER), String.format(MSG_DEFAULT, tag, content));
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(LOCATION_USER, E), tag, content));
        }
    }
    
    public static void e(String tag, String content, Throwable tr)
    {
        if (isLog)
        {
            android.util.Log.e(generateTag(LOCATION_USER), String.format(MSG_DEFAULT, tag, content), tr);
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(LOCATION_USER, E), tag, content) + '\n'
                + android.util.Log.getStackTraceString(tr));
        }
    }
    
    /**
     * 拼接日志tag,该tag专为打在eclipse,DDms上准备
     * @return
     */
    private static String generateTag(int location)
    {
        if (isLogLocation)
        {
            StackTraceElement caller = new Throwable().getStackTrace()[2];
            String clazzName = caller.getClassName();
            clazzName = clazzName.substring(clazzName.lastIndexOf(".") + 1);
            
            return String.format(Locale.CHINA,
                TAG_DEFAULT_LOCATION,
                clazzName,
                caller.getMethodName(),
                caller.getLineNumber());
        }
        else
        {
            return TAG_DEFAULT;
        }
    }
    
    /**
     * 拼接 日志tag,该tag专为写入file中准备
     * @param type
     * @return
     */
    private static String generateFileTag(int location, String type)
    {
        // 日期 时间: 级别
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA)
            .format(Long.valueOf(System.currentTimeMillis()));
        if (TextUtils.isEmpty(type))
        {
            type = E;
        }
        
        if (isLogLocation)
        {
            StackTraceElement caller = new Throwable().getStackTrace()[2];
            String clazzName = caller.getClassName();
            clazzName = clazzName.substring(clazzName.lastIndexOf(".") + 1);
            
            return String.format(Locale.CHINA,
                TAG_FILE_DEFAULT_LOCATION,
                time,
                type,
                clazzName,
                caller.getMethodName(),
                caller.getLineNumber());
        }
        else
        {
            return String.format(TAG_FILE_DEFAULT, time, type);
        }
    }
    
    /**
     * 写日志入文件
     * @param content   日志内容
     */
    private synchronized static void writeLogToFile(String content)
    {
        String path = FileUtil.getPath();
        if (null == path)
        {
            android.util.Log.e(generateFileTag(LOCATION_USER, E), ERROR_LOG_FILE_UTIL + "sdcard path is null");
            return;
        }
        
        File dirFile = FileUtil.createFileDir(path + LOG_FILE_PATH);
        if (null == dirFile)
        {
            android.util.Log.e(generateFileTag(LOCATION_USER, E), ERROR_LOG_FILE_UTIL + "sdcard dirFile create failed");
            return;
        }
        
        File file = FileUtil.createFile(dirFile, START_COUNT + LOG_FILE_TXT_NAME);
        if (null == file)
        {
            android.util.Log.e(generateFileTag(LOCATION_USER, E), ERROR_LOG_FILE_UTIL + "sdcard file create failed");
            return;
        }
        
        int size = FileUtil.getFileSize(file);
        if (-1 == size)
        {
            android.util.Log.e(generateFileTag(LOCATION_USER, E), ERROR_LOG_FILE_UTIL + "sdcard getFileSize failed");
            return;
        }
        
        if (!FileUtil.writeToFile(file, content))
        {
            android.util.Log.e(generateFileTag(LOCATION_USER, E), ERROR_LOG_FILE_UTIL + "FileUtil write failed");
            return;
        }
        
        // 分文件、限制文件个数
        if (size > MAX_SIZE_OF_TXT)
        {
            for (int count = MAX_COUNT; count >= START_COUNT; count--)
            {
                if (count == MAX_COUNT)
                {
                    if (FileUtil.isExist(dirFile, count + LOG_FILE_TXT_NAME)
                        && !FileUtil.deleteFile(dirFile, MAX_COUNT + LOG_FILE_TXT_NAME))
                    {
                        android.util.Log.e(generateFileTag(LOCATION_USER, E), ERROR_LOG_FILE_UTIL + "FileUtil deleteFile failed");
                        return;
                    }
                }
                else
                {
                    if (FileUtil.isExist(dirFile, count + LOG_FILE_TXT_NAME)
                        && !FileUtil.renameFile(dirFile, count + LOG_FILE_TXT_NAME, (count + 1) + LOG_FILE_TXT_NAME))
                    {
                        android.util.Log.e(generateFileTag(LOCATION_USER, E), ERROR_LOG_FILE_UTIL + "FileUtil renameFile failed");
                        return;
                    }
                }
            }
        }
    }
}