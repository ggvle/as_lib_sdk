package com.lib.http.demo.activity;

import android.app.Application;

import com.lib.http.XHttpConfig;
import com.yline.application.SDKConfig;
import com.yline.application.SDKManager;

public class IApplication extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();

		SDKManager.init(this, new SDKConfig());
		XHttpConfig.getInstance().init(this);
	}
}
