package com.demo.application;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.squareup.leakcanary.LeakCanary;
import com.yline.application.BaseApplication;
import com.yline.application.SDKConfig;

public class MainApplication extends BaseApplication
{
	@Override
	public void onCreate()
	{
		super.onCreate();

		MultiDex.install(this);
		initLeakCanary(this);
	}
	
	private void initLeakCanary(Application application)
	{
		if (LeakCanary.isInAnalyzerProcess(this))
		{
			return;
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
		{
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
					.detectAll() //
					.penaltyLog() //
					.penaltyDeath() //
					.build());
		}

		LeakCanary.install(application);
	}

	@Override
	public SDKConfig initConfig()
	{
		SDKConfig sdkConfig = new SDKConfig();
		sdkConfig.setSDKLog(true);
		sdkConfig.setUtilLogBySystem(true);
		return sdkConfig;
	}
}
