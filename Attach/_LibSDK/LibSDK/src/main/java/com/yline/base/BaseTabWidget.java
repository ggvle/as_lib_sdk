package com.yline.base;

import com.yline.application.BaseApplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TabWidget;

public class BaseTabWidget extends TabWidget
{
    
    public BaseTabWidget(Context context)
    {
        this(context, null);
    }
    
    public BaseTabWidget(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }
    
    public BaseTabWidget(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        BaseApplication.addViewForRecord(this);
    }
    
    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        BaseApplication.removeViewForRecord(this);
    }
    
}
