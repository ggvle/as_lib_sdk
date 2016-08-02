package com.yline.base;

import com.yline.application.BaseApplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class BaseEditText extends EditText
{
    public BaseEditText(Context context)
    {
        this(context, null);
    }
    
    public BaseEditText(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }
    
    public BaseEditText(Context context, AttributeSet attrs, int defStyleAttr)
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
