package com.yline.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class BaseFrameLayout extends FrameLayout
{
    public BaseFrameLayout(Context context)
    {
        super(context);
    }
    
    public BaseFrameLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    public BaseFrameLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }
    
}
