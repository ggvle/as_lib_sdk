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
	public static final String TAG = "libSDKDemo";

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
	protected SDKConfig initConfig()
	{
		SDKConfig sdkConfig = new SDKConfig();
		sdkConfig.setFileParentPath("_yline");
		sdkConfig.setLogFilePath("libSDKDemo"); // 默认开启日志,并写到文件中
		sdkConfig.setLogSystem(false);
		sdkConfig.setLogLib(true);
		return sdkConfig;
	}
}
