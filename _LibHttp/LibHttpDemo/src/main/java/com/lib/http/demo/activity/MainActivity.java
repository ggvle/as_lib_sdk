package com.lib.http.demo.activity;

import android.os.Bundle;
import android.view.View;

import com.lib.http.XHttpAdapter;
import com.lib.http.demo.XHttpUtil;
import com.lib.http.demo.bean.VNewsMultiplexBean;
import com.lib.http.demo.bean.VNewsSingleBean;
import com.lib.http.demo.bean.WNewsMultiplexBean;
import com.lib.http.interceptor.OnlyNetInterceptor;
import com.yline.test.BaseTestActivity;

public class MainActivity extends BaseTestActivity
{
	@Override
	protected void testStart(Bundle savedInstanceState)
	{
		addButton("Get default", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				XHttpUtil.doGet(new XHttpAdapter<VNewsSingleBean>()
				{
					@Override
					public void onSuccess(VNewsSingleBean vNewsSingleBean)
					{

					}
				});
			}
		});

		addButton("Get OnlyCacheInterceptor", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				XHttpUtil.doGet(new XHttpAdapter<VNewsSingleBean>()
				{
					@Override
					public void onSuccess(VNewsSingleBean vNewsSingleBean)
					{

					}
				}, new OnlyNetInterceptor());
			}
		});

		addButton("Post default", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				XHttpUtil.doPost(new XHttpAdapter<VNewsMultiplexBean>()
				{
					@Override
					public void onSuccess(VNewsMultiplexBean vNewsMultiplexBean)
					{

					}
				}, new WNewsMultiplexBean(0, 3));
			}
		});
	}
}
