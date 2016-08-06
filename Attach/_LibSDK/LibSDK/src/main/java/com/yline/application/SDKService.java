package com.yline.application;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.yline.log.LogFileUtil;

/**
 * 伴生Application的服务---计时服务
 *
 * @author YLine 2016/8/7 --> 2:11
 * @version 1.0
 */
public class SDKService extends Service
{
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
		LogFileUtil.m("AppService running in onCreate");
		mThread = new Thread(new SDKRunnable());
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		LogFileUtil.m("AppService running in onStartCommand");
		if (null == intent)
		{
			intent = new Intent();
		}
		
		if (null == mThread)
		{
			LogFileUtil.m("AppService new thread in onStartCommand");
			mThread = new Thread(new SDKRunnable());
		}
		
		if (!mThread.isAlive())
		{
			LogFileUtil.m("AppService thread start in onStartCommand");
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
	 *
	 * @param context
	 */
	public static void initAppService(Context context, Class<?> cls)
	{
		context.startService(new Intent(context, cls));
	}
}
