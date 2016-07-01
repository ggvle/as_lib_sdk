package com.yline.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;

public class BaseGridLayout extends GridLayout
{
    public BaseGridLayout(Context context)
    {
        super(context);
    }
    
    public BaseGridLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    public BaseGridLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }
    
}
