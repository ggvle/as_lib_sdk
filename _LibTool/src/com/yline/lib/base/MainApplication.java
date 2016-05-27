package com.yline.lib.base;

import java.io.File;

import android.os.Message;

import com.yline.application.BaseApplication;
import com.yline.application.BaseConfig;

/**
 * simple introduction
 *
 * @author YLine 2016-5-1 -> 下午3:29:09
 * @version 
 */
public class MainApplication extends BaseApplication
{
    
    @Override
    public void onCreate()
    {
        super.onCreate();
    }
    
    @Override
    protected void handlerDefault(Message msg)
    {
        
    }
    
    @Override
    protected BaseConfig initConfig()
    {
        BaseConfig baseConfig = new BaseConfig();
        baseConfig.setFileLogPath("libTool" + File.separator);
        baseConfig.setLogLocation(true);
        baseConfig.setTimerServiceOpen(false);
        return baseConfig;
    }
}
