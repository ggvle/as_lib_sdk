package com.yline.lib.base;

import java.io.File;

import android.app.Application;

import com.yline.lib.utils.combine.LogFileUtil;

/**
 * simple introduction
 *
 * @author YLine 2016-5-1 -> 下午3:29:09
 * @version 
 */
public class MainApplication extends Application
{
    public static final String FILE_PARENT_PATH = "_yline_lib" + File.separator;
    
    public static final String TAG_APPLICATION = "application";
    
    private static Application mApplication;
    
    public static Application getApplication()
    {
        return mApplication;
    }

    public static void setApplication(Application application)
    {
        MainApplication.mApplication = application;
    }

    /**
     * @see android.app.Application#onCreate()
     */
    @Override
    public void onCreate()
    {
        super.onCreate();
        setApplication(this);
        
        // 打下日志文件
        for (int i = 0; i < 10; i++)
        {
            LogFileUtil.v(TAG_APPLICATION, "*** id = " + Thread.currentThread().getId());
        }
    }
}
