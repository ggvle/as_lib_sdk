package com.demo.fragment;

import android.view.View;

import com.demo.activity.LeakCanaryActivity;
import com.demo.application.MainApplication;
import com.demo.common.CommonListActivity;
import com.demo.common.CommonRecyclerActivity;
import com.yline.log.LogFileUtil;
import com.yline.test.BaseTestFragment;

public class FunctionFragment extends BaseTestFragment
{
	@Override
	protected void testStart()
	{
		// 测试 LeakCanaryActivity(不能放入LibSDK中,否则失效)
		addButton("leakCanary Activity", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LogFileUtil.v(MainApplication.TAG, "btn_leak_canary_activity");
				LeakCanaryActivity.actionStart(getContext());
			}
		});

		final Runnable runnable = new Runnable()
		{
			@Override
			public void run()
			{
				for (int i = 0; i < 100000000; i++)
				{
					if (i % 10000000 == 0)
					{
						LogFileUtil.v("TestThread i = " + i);
					}
				}
			}
		};

		// 测试,线程池+getProjectFilePath
		addButton("Executor task", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LogFileUtil.v(MainApplication.TAG, "ProjectFilePath = " + MainApplication.getProjectFilePath() + ",btn_execute");
				// 调用两次就会执行两次,然后,点击Button两次,就会出现线程池的排队效果
				MainApplication.start(runnable, null);
				MainApplication.start(runnable, null);
			}
		});

		// CommonListActivity
		addButton("CommonListAdapter", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LogFileUtil.v(MainApplication.TAG, "btn_common_list_activity");
				CommonListActivity.actionStart(getContext());
			}
		});

		addButton("CommonRecyclerAdapter", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LogFileUtil.v(MainApplication.TAG, "btn_common_recycler_activity");
				CommonRecyclerActivity.actionStart(getContext());
			}
		});
	}
}
