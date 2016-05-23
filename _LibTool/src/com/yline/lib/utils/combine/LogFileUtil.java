package com.yline.lib.utils.combine;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.text.TextUtils;

import com.yline.lib.activity.MainApplication;
import com.yline.lib.utils.FileUtil;

/**
 * 想要区分应用时,可以采用区分目录或修改"x"的方式
 * simple introduction
 *
 * @author YLine 2016-5-21 -> 下午6:50:25
 * @version
 */
public class LogFileUtil
{
    private static final String ERROR_LOG_FILE_UTIL = "error_LogFileUtil : ";
    
    private static final String LOG_FILE_PATH = MainApplication.FILE_PARENT_PATH + "LibToolLog" + File.separator;
    
    private static final int START_COUNT = 0; // 写入文件编号
    
    private static final int MAX_COUNT = 5; // 文件最大编号
    
    private static final int MAX_SIZE_OF_TXT = 2 * 1024 * 1024;
    
    private static final String LOG_FILE_TXT_NAME = "_yline_log.txt";
    
    // 三个开关
    private static final boolean isLog = true; // log 开关
    
    private static final boolean isToFile = true; // 是否写到文件
    
    private static final boolean isLogLocation = true; // 是否定位
    
    // 信息格式
    private static final String TAG_DEFAULT = "x->";
    
    private static final String TAG_DEFAULT_LOCATION = "x->%s.%s(L:%d): "; // tag 定位  默认格式
    
    private static final String MSG_DEFAULT = "%s -> %s"; // msg 默认格式
    
    private static final String TAG_FILE_DEFAULT = "x->%s: %s/"; // tag 文件默认格式<日期,级别>
    
    private static final String TAG_FILE_DEFAULT_LOCATION = "x->%s: %s/%s.%s(L:%d): "; // tag 文件定位默认格式
    
    private static final String MSG_FILE_DEFAULT = "%s %s -> %s";
    
    // 安全级别
    private static final String V = "V";
    
    private static final String D = "D";
    
    private static final String I = "I";
    
    private static final String W = "W";
    
    private static final String E = "E";
    
    public static void v(String tag, String content)
    {
        if (isLog)
        {
            android.util.Log.v(generateTag(), String.format(MSG_DEFAULT, tag, content));
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(V), tag, content));
        }
    }
    
    public static void v(String tag, String content, Throwable tr)
    {
        if (isLog)
        {
            android.util.Log.v(generateTag(), String.format(MSG_DEFAULT, tag, content), tr);
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(V), tag, content) + '\n'
                + android.util.Log.getStackTraceString(tr));
        }
    }
    
    public static void d(String tag, String content)
    {
        if (isLog)
        {
            android.util.Log.d(generateTag(), String.format(MSG_DEFAULT, tag, content));
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(D), tag, content));
        }
    }
    
    public static void d(String tag, String content, Throwable tr)
    {
        if (isLog)
        {
            android.util.Log.d(generateTag(), String.format(MSG_DEFAULT, tag, content), tr);
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(D), tag, content) + '\n'
                + android.util.Log.getStackTraceString(tr));
        }
    }
    
    public static void i(String tag, String content)
    {
        if (isLog)
        {
            android.util.Log.i(generateTag(), String.format(MSG_DEFAULT, tag, content));
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(I), tag, content));
        }
    }
    
    public static void i(String tag, String content, Throwable tr)
    {
        if (isLog)
        {
            android.util.Log.i(generateTag(), String.format(MSG_DEFAULT, tag, content), tr);
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(I), tag, content) + '\n'
                + android.util.Log.getStackTraceString(tr));
        }
    }
    
    public static void w(String tag, String content)
    {
        if (isLog)
        {
            android.util.Log.w(generateTag(), String.format(MSG_DEFAULT, tag, content));
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(W), tag, content));
        }
    }
    
    public static void w(String tag, String content, Throwable tr)
    {
        if (isLog)
        {
            android.util.Log.w(generateTag(), String.format(MSG_DEFAULT, tag, content), tr);
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(W), tag, content) + '\n'
                + android.util.Log.getStackTraceString(tr));
        }
    }
    
    public static void e(String tag, String content)
    {
        if (isLog)
        {
            android.util.Log.e(generateTag(), String.format(MSG_DEFAULT, tag, content));
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(E), tag, content));
        }
    }
    
    public static void e(String tag, String content, Throwable tr)
    {
        if (isLog)
        {
            android.util.Log.e(generateTag(), String.format(MSG_DEFAULT, tag, content), tr);
        }
        
        if (isToFile)
        {
            writeLogToFile(String.format(MSG_FILE_DEFAULT, generateFileTag(E), tag, content) + '\n'
                + android.util.Log.getStackTraceString(tr));
        }
    }
    
    private static String generateTag()
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
    
    private static String generateFileTag(String type)
    {
        // 日期 时间: 级别
        String time =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA).format(Long.valueOf(System.currentTimeMillis()));
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
    
    private synchronized static void writeLogToFile(String content)
    {
        String path = FileUtil.getPath();
        if (null == path)
        {
            android.util.Log.e(generateFileTag(E), ERROR_LOG_FILE_UTIL + "sdcard path is null");
            return;
        }
        
        File dirFile = FileUtil.createFileDir(path + LOG_FILE_PATH);
        if (null == dirFile)
        {
            android.util.Log.e(generateFileTag(E), ERROR_LOG_FILE_UTIL + "sdcard dirFile create failed");
            return;
        }
        
        File file = FileUtil.createFile(dirFile, START_COUNT + LOG_FILE_TXT_NAME);
        if (null == file)
        {
            android.util.Log.e(generateFileTag(E), ERROR_LOG_FILE_UTIL + "sdcard file create failed");
            return;
        }
        
        int size = FileUtil.getFileSize(file);
        if (-1 == size)
        {
            android.util.Log.e(generateFileTag(E), ERROR_LOG_FILE_UTIL + "sdcard getFileSize failed");
            return;
        }
        
        if (!FileUtil.writeToFile(file, content))
        {
            android.util.Log.e(generateFileTag(E), ERROR_LOG_FILE_UTIL + "FileUtil write failed");
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
                        android.util.Log.e(generateFileTag(E), ERROR_LOG_FILE_UTIL + "FileUtil deleteFile failed");
                        return;
                    }
                }
                else
                {
                    if (FileUtil.isExist(dirFile, count + LOG_FILE_TXT_NAME)
                        && !FileUtil.renameFile(dirFile, count + LOG_FILE_TXT_NAME, (count + 1) + LOG_FILE_TXT_NAME))
                    {
                        android.util.Log.e(generateFileTag(E), ERROR_LOG_FILE_UTIL + "FileUtil renameFile failed");
                        return;
                    }
                }
            }
        }
    }
}
