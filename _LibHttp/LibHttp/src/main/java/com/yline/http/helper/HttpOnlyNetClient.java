package com.yline.http.helper;

import com.yline.http.interceptor.OnlyNetInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class HttpOnlyNetClient
{
	private static OkHttpClient httpClient;

	public static OkHttpClient getInstance()
	{
		if (null == httpClient)
		{
			synchronized (HttpOnlyNetClient.class)
			{
				if (null == httpClient)
				{
					OkHttpClient.Builder builder = new OkHttpClient.Builder();

					// 设置超时
					builder.connectTimeout(10, TimeUnit.SECONDS)
							.readTimeout(10, TimeUnit.SECONDS)
							.writeTimeout(10, TimeUnit.SECONDS);

					// 添加拦截器；默认走网络，如果没有网，则走缓存
					builder.addInterceptor(new OnlyNetInterceptor());

					httpClient = builder.build();
				}
			}
		}
		return httpClient;
	}

	private HttpOnlyNetClient()
	{
	}
}
