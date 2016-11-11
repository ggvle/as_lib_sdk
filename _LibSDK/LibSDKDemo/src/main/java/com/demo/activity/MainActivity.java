package com.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.demo.application.MainApplication;
import com.demo.common.CommonListActivity;
import com.demo.utils.FileUtilUser;
import com.demo.utils.LogFileUtilUser;
import com.demo.utils.LogUtilUser;
import com.demo.utils.SPUtilUser;
import com.lib.sdk.demo.R;
import com.yline.base.BaseActivity;
import com.yline.log.LogFileUtil;

public class MainActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.btn_baseapplication).setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				LogFileUtil.v(MainApplication.TAG, "btn_baseApplication");
				MainApplication.toast("测试，toast");
			}
		});

		findViewById(R.id.btn_crashHandler).setOnClickListener(new View.OnClickListener()
		{

			@SuppressWarnings("null")
			@Override
			public void onClick(View v)
			{
				Button btn = null;
				btn.setText("100");
			}
		});

		findViewById(R.id.btn_logutil).setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				new LogUtilUser().test();
			}
		});


		findViewById(R.id.btn_logFileUtil).setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				new LogFileUtilUser().test();
			}
		});

		findViewById(R.id.btn_fileUtil).setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				new FileUtilUser().test();
			}
		});

		findViewById(R.id.btn_spUtil).setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				new SPUtilUser().test(MainActivity.this);
			}
		});

		// 测试 LeakCanaryActivity(不能放入LibSDK中,否则失效)
		findViewById(R.id.btn_leak_canary_activity).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LogFileUtil.v(MainApplication.TAG, "btn_leak_canary_activity");
				LeakCanaryActivity.actionStart(MainActivity.this);
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
		findViewById(R.id.btn_execute).setOnClickListener(new View.OnClickListener()
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

		findViewById(R.id.btn_common_list_activity).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LogFileUtil.v(MainApplication.TAG, "btn_common_list_activity");
				CommonListActivity.actionStart(MainActivity.this);
			}
		});
	}
}
