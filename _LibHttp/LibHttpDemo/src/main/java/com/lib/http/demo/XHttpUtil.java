package com.lib.http.demo;

import com.lib.http.XHttpAdapter;
import com.lib.http.demo.bean.VNewsMultiplexBean;
import com.lib.http.demo.bean.VNewsSingleBean;
import com.lib.http.demo.bean.WNewsMultiplexBean;
import com.lib.http.helper.XTextHttp;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class XHttpUtil
{
	public static void doGet(XHttpAdapter<VNewsSingleBean> adapter)
	{
		String httpUrl = "http://120.92.35.211/wanghong/wh/index.php/Api/ApiNews/new_tui";
		new XTextHttp<VNewsSingleBean>(adapter).doGet(httpUrl, null, VNewsSingleBean.class);
	}

	public static void doGet(XHttpAdapter<VNewsSingleBean> adapter, final Interceptor interceptor)
	{
		String httpUrl = "http://120.92.35.211/wanghong/wh/index.php/Api/ApiNews/new_tui";
		new XTextHttp<VNewsSingleBean>(adapter)
		{
			@Override
			protected OkHttpClient getClient()
			{
				OkHttpClient defaultClient = super.getClient();

				OkHttpClient.Builder builder = defaultClient.newBuilder();
				builder.interceptors().clear();
				builder.addInterceptor(interceptor);

				return builder.build();
			}
		}.doGet(httpUrl, null, VNewsSingleBean.class);
	}

	public static void doPost(XHttpAdapter<VNewsMultiplexBean> adapter, WNewsMultiplexBean wNewsMultiplexBean)
	{
		String httpUrl = "http://120.92.35.211/wanghong/wh/index.php/Api/ApiNews/news";
		new XTextHttp<VNewsMultiplexBean>(adapter).doPost(httpUrl, wNewsMultiplexBean, VNewsMultiplexBean.class);
	}
}
