package com.yline.view.view.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yline.base.BaseAppCompatActivity;

public class ViewCircleActivity extends BaseAppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_circle);
		
		ImageView circleView = (ImageView) findViewById(R.id.view_circle);
		Glide.with(this).load(InnerConstant.getUrlRec()).error(R.drawable.global_load_failed).into(circleView);
	}
	
	public static void actionStart(Context context)
	{
		context.startActivity(new Intent(context, ViewCircleActivity.class));
	}
}
