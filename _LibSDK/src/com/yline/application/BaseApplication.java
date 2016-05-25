package com.yline.application;

import java.io.File;

import android.app.Application;

import com.yline.log.LogFileUtil;

/**
 * simple introduction
 *
 * @author YLine 2016-5-25 -> 上午7:32:23
 * @version 
 */
public class BaseApplication extends Application
{
    public static final String FILE_PARENT_PATH = "_yline_lib" + File.separator; // 文件保存父路径
    
    public static final String FILE_LOG_PATH = "LibToolLog" + File.separator; // 文件保存日志,子路径
    
    public static final String TAG_BASE_APPLICATION = "base_Application"; // sdk jar 包主线程tag
    
    private static Application mApplication;
    
    public static Application getApplication()
    {
        return mApplication;
    }
    
    private static void setApplication(Application application)
    {
        BaseApplication.mApplication = application;
    }
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        
        setApplication(this); // 初始化全局变量
        
        // 设立一个程序入口的log
        for (int i = 0; i < 10; i++)
        {
            LogFileUtil.v(TAG_BASE_APPLICATION, "*** application start id = " + Thread.currentThread().getId());
        }
    }
}
