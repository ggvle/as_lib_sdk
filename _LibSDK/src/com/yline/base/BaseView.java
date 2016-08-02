package com.yline.base;

import com.yline.application.BaseApplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class BaseView extends View
{
    public BaseView(Context context)
    {
        this(context, null);
    }
    
    public BaseView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }
    
    public BaseView(Context context, AttributeSet attrs, int defStyleAttr)
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
