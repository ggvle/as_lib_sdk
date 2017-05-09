package com.demo.fragment;

import android.os.Bundle;
import android.view.View;

import com.demo.activity.LeakCanaryActivity;
import com.demo.application.MainApplication;
import com.yline.application.SDKManager;
import com.yline.log.LogFileUtil;
import com.yline.test.BaseTestFragment;

public class FunctionFragment extends BaseTestFragment
{
	@Override
	public void testStart(View view, Bundle savedInstanceState)
	{
		// 测试 LeakCanaryActivity(不能放入LibSDK中,否则失效)
		addButton("LeakCanary Activity", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LogFileUtil.v(MainApplication.TAG, "btn_leak_canary_activity");
				LeakCanaryActivity.actionStart(getContext());
			}
		});

		addButton("SDKManager", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LogFileUtil.v(MainApplication.TAG, "btn_baseApplication");
				SDKManager.toast("测试，toast");
			}
		});

		addButton("CrashHandler", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				throw new ArithmeticException("crashHandler test");
			}
		});
	}
}
