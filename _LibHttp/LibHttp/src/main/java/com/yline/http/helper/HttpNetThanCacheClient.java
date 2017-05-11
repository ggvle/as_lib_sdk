package com.yline.http.helper;

import com.yline.http.interceptor.NetThanCacheInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 提供,统一处理Http的Client;
 * 这个用来处理文字和不被清除的内存(排除图片+视频)
 *
 * @author yline 2017/2/28 -- 17:29
 * @version 1.0.0
 */
public class HttpNetThanCacheClient extends OkHttpClient
{
	private static OkHttpClient httpClient;

	public static OkHttpClient getInstance()
	{
		if (null == httpClient)
		{
			synchronized (HttpNetThanCacheClient.class)
			{
				if (null == httpClient)
				{
					Builder builder = new Builder();
					/*
					// 设置缓存
					String cacheDirStr = SDKManager.getApplication().getExternalCacheDir() + File.separator + DEFAULT_CACHE_PATH;
					File cacheDir = FileUtil.createDir(cacheDirStr);
					CacheCode cache = new CacheCode(cacheDir, DEFAULT_CACHE_SIZE);
					builder.cache(cache);
					*/
					// 设置超时
					builder.connectTimeout(10, TimeUnit.SECONDS)
							.readTimeout(10, TimeUnit.SECONDS)
							.writeTimeout(10, TimeUnit.SECONDS);

					// 添加拦截器；默认走网络，如果没有网，则走缓存
					builder.addInterceptor(new NetThanCacheInterceptor());

					httpClient = builder.build();
				}
			}
		}
		return httpClient;
	}

	private HttpNetThanCacheClient()
	{
	}
}
