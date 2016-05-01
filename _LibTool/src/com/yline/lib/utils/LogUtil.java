package com.yline.lib.utils;

import android.util.Log;

/**
 * 第一部分: x->     相同部分       目的: 采用该工具打印出的相同部分
 * 第二部分: tag     tag       目的: 定位,类名、方法名、行数; 
 * 在一定程度上已经违背了安全规范,因此在出版本的时候,需要log去掉或者去掉generate
 * 第三部分: tag     功能               目的: 某一个功能模块的tag
 * 第四部分: content 具体信息       目的: "start"、"end"、"number" = number 等类似信息
 * 
 * simple introduction
 *
 * @author YLine 2016-5-1
 * @version
 */
public class LogUtil
{
    private static final boolean isDebug = true; // log 开关
                                                 
    private LogUtil()
    {
        
    }
    
    private static String generateTag()
    {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String clazzName = caller.getClassName();
        clazzName = clazzName.substring(clazzName.lastIndexOf(".") + 1);
        // 类名、方法名、行数
        tag = String.format(tag, clazzName, caller.getMethodName(), caller.getLineNumber());
        return "x->" + tag;
    }
    
    public static void v(String tag, String content)
    {
        if (!isDebug)
        {
            return;
        }
        Log.v(generateTag(), tag + " -> " + content);
    }
    
    public static void v(String tag, String content, Throwable tr)
    {
        if (!isDebug)
        {
            return;
        }
        Log.v(generateTag(), tag + " -> " + content, tr);
    }
    
    public static void d(String tag, String content)
    {
        if (!isDebug)
        {
            return;
        }
        Log.d(generateTag(), tag + " -> " + content);
    }
    
    public static void d(String tag, String content, Throwable tr)
    {
        if (!isDebug)
        {
            return;
        }
        Log.d(generateTag(), tag + " -> " + content, tr);
    }
    
    public static void i(String tag, String content)
    {
        if (!isDebug)
        {
            return;
        }
        Log.i(generateTag(), tag + " -> " + content);
    }
    
    public static void i(String tag, String content, Throwable tr)
    {
        if (!isDebug)
        {
            return;
        }
        Log.i(generateTag(), tag + " -> " + content, tr);
    }
    
    public static void w(String tag, String content)
    {
        if (!isDebug)
        {
            return;
        }
        Log.w(generateTag(), tag + " -> " + content);
    }
    
    public static void w(String tag, String content, Throwable tr)
    {
        if (!isDebug)
        {
            return;
        }
        Log.w(generateTag(), tag + " -> " + content, tr);
    }
    
    public static void e(String tag, String content)
    {
        if (!isDebug)
        {
            return;
        }
        Log.v(generateTag(), tag + " -> " + content);
    }
    
    public static void e(String tag, String content, Throwable tr)
    {
        if (!isDebug)
        {
            return;
        }
        Log.v(generateTag(), tag + " -> " + content, tr);
    }
}
