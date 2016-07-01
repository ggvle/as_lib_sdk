package com.yline.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class BaseLinearLayout extends LinearLayout
{
    public BaseLinearLayout(Context context)
    {
        super(context);
    }
    
    public BaseLinearLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    public BaseLinearLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }
}
