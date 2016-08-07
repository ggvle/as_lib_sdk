package com.demo.application;

import android.os.Message;

import com.yline.application.BaseApplication;
import com.yline.application.SDKConfig;

public class MainApplication extends BaseApplication
{
	public static final String TAG = "libSDKDemo";

	@Override
	protected void handlerDefault(Message msg)
	{

	}

	@Override
	protected SDKConfig initConfig()
	{
		SDKConfig sdkConfig = new SDKConfig();
		sdkConfig.setLogFilePath("libSDKDemo"); // 默认开启日志,并写到文件中
		sdkConfig.setCls(AppService.class); // 配置伴生服务
		return sdkConfig;
	}
}
