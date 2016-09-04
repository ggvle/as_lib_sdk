package com.demo.application;

import com.yline.application.BaseApplication;
import com.yline.application.SDKConfig;

public class MainApplication extends BaseApplication
{
	public static final String TAG = "libSDKDemo";

	@Override
	protected SDKConfig initConfig()
	{
		SDKConfig sdkConfig = new SDKConfig();
		sdkConfig.setFileParentPath("_ylines");
		sdkConfig.setLogFilePath("libSDKDemo"); // 默认开启日志,并写到文件中
		return sdkConfig;
	}
}
