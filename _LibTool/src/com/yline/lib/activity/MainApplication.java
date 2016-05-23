package com.yline.lib.activity;

import java.io.File;

import android.app.Application;
import android.content.Context;

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
    
    private static Context context;
    
    /**
     * @see android.app.Application#onCreate()
     */
    @Override
    public void onCreate()
    {
        super.onCreate();
        context = getApplicationContext();
        
        // 打下日志文件
        for (int i = 0; i < 10; i++)
        {
            LogFileUtil.v(TAG_APPLICATION, "*** id = " + Thread.currentThread().getId());
        }
    }
    
    public static Context getContext()
    {
        return context;
    }
}
