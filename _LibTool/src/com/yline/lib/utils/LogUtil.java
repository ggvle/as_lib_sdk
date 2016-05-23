package com.yline.lib.utils;

/**
 * simple introduction
 * 格式:x->{[类名.方法名(L:行数)]: }功能tag -> 信息content
 * 
 * tag 功能 目的: 某一个功能模块的tag
 * content 具体信息 目的: "start"、"end"、"number" = number 等类似信息
 *
 * 级别:
 * v 主流程信息
 * d 调试信息
 * i 主流程信息注意级别
 * w 警告级别
 * e 错误级别
 * 
 * {}这里统一加一个开关,设置为信息安全
 * 
 * @author YLine 2016-5-1
 * @version
 */
public class LogUtil
{
    private static final boolean isLog = true; // log 开关
    
    private static final boolean isLogLocation = true; // 是否定位
    
    private static final String TAG_DEFAULT = "x->"; // tag 默认格式
    
    private static final String TAG_DEFAULT_LOCATION = "x->%s.%s(L:%d): "; // tag 定位  默认格式
    
    private static final String MSG_DEFAULT = "%s -> %s"; // msg 默认格式
    
    private static String generateTag()
    {
        if (isLogLocation)
        {
            StackTraceElement caller = new Throwable().getStackTrace()[2];
            String clazzName = caller.getClassName();
            clazzName = clazzName.substring(clazzName.lastIndexOf(".") + 1);
            
            return String.format(TAG_DEFAULT_LOCATION, clazzName, caller.getMethodName(), caller.getLineNumber());
        }
        else
        {
            return TAG_DEFAULT;
        }
    }
    
    public static void v(String tag, String content)
    {
        if (isLog)
        {
            android.util.Log.v(generateTag(), String.format(MSG_DEFAULT, tag, content));
        }
    }
    
    public static void v(String tag, String content, Throwable tr)
    {
        if (isLog)
        {
            android.util.Log.v(generateTag(), String.format(MSG_DEFAULT, tag, content), tr);
        }
    }
    
    public static void d(String tag, String content)
    {
        if (isLog)
        {
            android.util.Log.d(generateTag(), String.format(MSG_DEFAULT, tag, content));
        }
    }
    
    public static void d(String tag, String content, Throwable tr)
    {
        if (isLog)
        {
            android.util.Log.d(generateTag(), String.format(MSG_DEFAULT, tag, content), tr);
        }
    }
    
    public static void i(String tag, String content)
    {
        if (isLog)
        {
            android.util.Log.i(generateTag(), String.format(MSG_DEFAULT, tag, content));
        }
    }
    
    public static void i(String tag, String content, Throwable tr)
    {
        if (isLog)
        {
            android.util.Log.i(generateTag(), String.format(MSG_DEFAULT, tag, content), tr);
        }
    }
    
    public static void w(String tag, String content)
    {
        if (isLog)
        {
            android.util.Log.w(generateTag(), String.format(MSG_DEFAULT, tag, content));
        }
    }
    
    public static void w(String tag, String content, Throwable tr)
    {
        if (isLog)
        {
            android.util.Log.w(generateTag(), String.format(MSG_DEFAULT, tag, content), tr);
        }
    }
    
    public static void e(String tag, String content)
    {
        if (isLog)
        {
            android.util.Log.e(generateTag(), String.format(MSG_DEFAULT, tag, content));
        }
    }
    
    public static void e(String tag, String content, Throwable tr)
    {
        if (isLog)
        {
            android.util.Log.e(generateTag(), String.format(MSG_DEFAULT, tag, content), tr);
        }
    }
}
