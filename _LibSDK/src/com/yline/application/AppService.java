package com.yline.application;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.yline.log.LogFileUtil;

/**
 * 伴生Application的服务---计时服务
 * simple introduction
 *
 * @author YLine 2016-5-29 -> 上午9:00:42
 * @version
 */
public final class AppService extends Service
{
    public static final String TAG_APP_SERVICE = "app_service";
    
    private Thread mThread;
    
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        LogFileUtil.v(TAG_APP_SERVICE, "AppService running in onCreate");
        mThread = new Thread(new AppRunnable());
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        LogFileUtil.v(TAG_APP_SERVICE, "AppService running in onStartCommand");
        if (null == intent)
        {
            intent = new Intent();
        }
        
        if (null == mThread)
        {
            LogFileUtil.v(TAG_APP_SERVICE, "AppService new thread in onStartCommand");
            mThread = new Thread(new AppRunnable());
        }
        
        if (!mThread.isAlive())
        {
            LogFileUtil.v(TAG_APP_SERVICE, "AppService thread start in onStartCommand");
            mThread.start();
        }
        
        return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (null != mThread)
        {
            mThread = null;
        }
    }
    
    /**
     * 开启此Service
     * @param context
     */
    public static void initAppService(Context context)
    {
        context.startService(new Intent(context, AppService.class));
    }
}
