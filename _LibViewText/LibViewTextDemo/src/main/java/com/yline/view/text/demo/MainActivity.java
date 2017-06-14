package com.yline.view.text.demo;

import android.os.Bundle;
import android.view.View;

import com.yline.test.BaseTestActivity;

public class MainActivity extends BaseTestActivity
{
	@Override
	public void testStart(View view, Bundle savedInstanceState)
	{
		addButton("EditText 一键删除", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ViewKeyClearEditTextActivity.actionStart(MainActivity.this);
			}
		});
	}
}
