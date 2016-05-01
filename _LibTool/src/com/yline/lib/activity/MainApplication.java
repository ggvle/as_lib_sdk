package com.yline.lib.activity;

import android.app.Application;
import android.content.Context;

/**
 * simple introduction
 *
 * @author YLine 2016-5-1 -> 下午3:29:09
 * @version 
 */
public class MainApplication extends Application
{
    private static Context context;
    
    /**
     * @see android.app.Application#onCreate()
     */
    @Override
    public void onCreate()
    {
        super.onCreate();
        context = getApplicationContext();
    }
    
    public static Context getContext()
    {
        return context;
    }
}
