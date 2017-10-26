package com.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.lib.sdk.demo.R;
import com.yline.base.BaseAppCompatActivity;

/**
 * Created by yline on 2016/11/11.
 */
public class LeakCanaryActivity extends BaseAppCompatActivity
{
	public static void actionStart(Context context)
	{
		Intent intent = new Intent();
		intent.setClass(context, LeakCanaryActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leak_canary);

		findViewById(R.id.btn_async_task).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startAsyncTask();
			}
		});
	}

	@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
	private void startAsyncTask()
	{
		// This async task is an anonymous class and therefore has a hidden reference to the outer
		// class MainActivity. If the activity gets destroyed before the task finishes (e.g. rotation),
		// the activity instance will leak.
		new AsyncTask<Void, Void, Void>()
		{
			@Override
			protected Void doInBackground(Void... params)
			{
				// Do some slow work in background
				SystemClock.sleep(20000);
				return null;
			}
		}.execute();
	}
}
