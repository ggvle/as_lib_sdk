package com.yline.base;

import com.yline.application.BaseApplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

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
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        BaseApplication.removeViewForRecord(this);
    }
}
