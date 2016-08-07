package com.yline.base;

import com.yline.application.BaseApplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class BaseTextView extends TextView
{
    public BaseTextView(Context context)
    {
        this(context, null);
    }
    
    public BaseTextView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }
    
    public BaseTextView(Context context, AttributeSet attrs, int defStyleAttr)
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
