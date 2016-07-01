package com.yline.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class BaseViewGroup extends ViewGroup
{
    public BaseViewGroup(Context context)
    {
        super(context);
    }
    
    public BaseViewGroup(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    public BaseViewGroup(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        
    }
    
}
