package com.yline.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.yline.application.BaseApplication;
import com.yline.log.LogFileUtil;

public class BaseImageView extends ImageView
{
	public BaseImageView(Context context)
	{
		this(context, null);
	}

	public BaseImageView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public BaseImageView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
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
