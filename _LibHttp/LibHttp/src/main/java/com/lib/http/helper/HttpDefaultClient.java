package com.lib.http.helper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class HttpDefaultClient
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

					httpClient = builder.build();
				}
			}
		}
		return httpClient;
	}

	private HttpDefaultClient()
	{
	}
}
