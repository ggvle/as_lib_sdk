package com.lib.http;

import android.content.Context;

import com.lib.http.cache.CacheManager;
import com.lib.http.util.LogUtil;

import java.io.File;

public class XHttpConfig
{
	private XHttpConfig()
	{
	}

	public static XHttpConfig getInstance()
	{
		return HttpConfigHolder.sInstance;
	}

	private static class HttpConfigHolder
	{
		private static XHttpConfig sInstance = new XHttpConfig();
	}

	private Context context;

	private File cacheDir;

	private int cacheMaxSize = 128 * 1024 * 1024;

	private boolean isUtilLog = true;

	private boolean isUtilLogLocation = true;

	/* 这个类，必须被初始化；否则程序出错 */
	public XHttpConfig init(Context context)
	{
		this.context = context;
		if (null == cacheDir)
		{
			this.cacheDir = context.getExternalCacheDir();
		}
		LogUtil.init(isUtilLog, isUtilLogLocation);
		CacheManager.getInstance().init(cacheDir, cacheMaxSize);
		return this;
	}

	public XHttpConfig setCacheDir(File cacheDir)
	{
		this.cacheDir = cacheDir;
		return this;
	}

	public XHttpConfig setCacheMaxSize(int cacheMaxSize)
	{
		this.cacheMaxSize = cacheMaxSize;
		return this;
	}

	public void setUtilLog(boolean utilLog)
	{
		isUtilLog = utilLog;
	}

	public void setUtilLogLocation(boolean utilLogLocation)
	{
		isUtilLogLocation = utilLogLocation;
	}

	public Context getContext()
	{
		return context;
	}
}
