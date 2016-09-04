package com.yline.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yline.application.BaseApplication;

/**
 * @author yline 2016/9/4 --> 17:57
 * @version 1.0.0
 */
public class BaseAppCompatActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		BaseApplication.addAcitivity(this);
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		BaseApplication.removeActivity(this);
	}
}
