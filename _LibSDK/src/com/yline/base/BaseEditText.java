package com.yline.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class BaseEditText extends EditText
{
    public BaseEditText(Context context)
    {
        super(context);
    }
    
    public BaseEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    public BaseEditText(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }
    
}
