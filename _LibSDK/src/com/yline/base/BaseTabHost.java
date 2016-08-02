package com.yline.base;

import com.yline.application.BaseApplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TabHost;

public class BaseTabHost extends TabHost
{
    public BaseTabHost(Context context)
    {
        this(context, null);
    }
    
    public BaseTabHost(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        BaseApplication.addViewForRecord(this);
    }
    
    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        BaseApplication.removeViewForRecord(this);
    }
}
