package com.yline.http.helper;

import com.yline.http.interceptor.CacheAndNetInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class HttpCacheAndNetClient
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
					OkHttpClient.Builder builder = new OkHttpClient.Builder();

					// 设置超时
					builder.connectTimeout(10, TimeUnit.SECONDS)
							.readTimeout(10, TimeUnit.SECONDS)
							.writeTimeout(10, TimeUnit.SECONDS);

					// 添加拦截器；默认走网络，如果没有网，则走缓存
					builder.addInterceptor(new CacheAndNetInterceptor());

					httpClient = builder.build();
				}
			}
		}
		return httpClient;
	}

	private HttpCacheAndNetClient()
	{
	}
}
