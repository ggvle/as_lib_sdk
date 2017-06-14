package com.yline.view.view.demo;

import android.os.Bundle;
import android.view.View;

import com.yline.test.BaseTestActivity;

public class MainActivity extends BaseTestActivity
{

	@Override
	public void testStart(View view, Bundle savedInstanceState)
	{
		addButton("圆形 ImageView", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ViewCircleActivity.actionStart(MainActivity.this);
			}
		});
	}
}
