package com.yline.base;

import android.appwidget.AppWidgetHostView;
import android.content.Context;

import com.yline.application.BaseApplication;
import com.yline.log.LogFileUtil;

public class BaseAppWidgetHostView extends AppWidgetHostView
{
	public BaseAppWidgetHostView(Context context)
	{
		this(context, android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public BaseAppWidgetHostView(Context context, int animationIn, int animationOut)
	{
		super(context, animationIn, animationOut);
		BaseApplication.addViewForRecord(this);
	}

	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		LogFileUtil.m("finishInflate:" + getClass().getSimpleName());
	}

	@Override
	protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		BaseApplication.removeViewForRecord(this);
	}
}
