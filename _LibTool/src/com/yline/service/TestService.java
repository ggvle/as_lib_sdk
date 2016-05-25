package com.yline.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.yline.log.LogFileUtil;

/**
 * simple introduction
 * PS:服务想要开启,必须先注册<不注册不会报错,也不会运行>
 *
 * @author YLine 2016-5-22 -> 上午11:05:39
 * @version 
 */
public class TestService extends Service
{
    
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    
    /**
     * 服务只会被创建一次
     * 第一次创建：onCreate() --> onStartCommand() --> onStart() 顺序执行
     * 多次创建：onStartCommand() --> onStart() 顺序执行
     */
    @Override
    public void onCreate()
    {
        super.onCreate();
        LogFileUtil.v(com.yline.service.User.TAG_SERVICE, "onCreate success");
    }
    
    /**
     * 每次调用都会执行
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (null == intent)
        {
            intent = new Intent();
        }
        LogFileUtil.v(com.yline.service.User.TAG_SERVICE, "onStartCommand success");
        return super.onStartCommand(intent, flags, startId);
    }
    
    /**
     * 每次调用都会执行
     */
    @Override
    public void onStart(Intent intent, int startId)
    {
        LogFileUtil.v(com.yline.service.User.TAG_SERVICE, "onStart success");
    };
    
    @Override
    public boolean onUnbind(Intent intent)
    {
        return super.onUnbind(intent);
    }
    
    @Override
    public void onDestroy()
    {
        LogFileUtil.v(com.yline.service.User.TAG_SERVICE, "onDestroyed");
    };
}
