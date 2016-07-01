package com.yline.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TabHost;

public class BaseTabHost extends TabHost
{
    public BaseTabHost(Context context)
    {
        super(context);
    }
    
    public BaseTabHost(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
}
