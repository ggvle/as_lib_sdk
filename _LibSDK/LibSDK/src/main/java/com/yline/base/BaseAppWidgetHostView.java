package com.yline.base;

import com.yline.application.BaseApplication;

import android.appwidget.AppWidgetHostView;
import android.content.Context;

public class BaseAppWidgetHostView extends AppWidgetHostView
{
    public BaseAppWidgetHostView(Context context)
    {
        this(context, android.R.anim.fade_in, android.R.anim.fade_out);
    }
    
    public BaseAppWidgetHostView(Context context, int animationIn, int animationOut)
    {
        super(context, animationIn, animationOut);
        BaseApplication.addViewForRecord(this);
    }
    
    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        BaseApplication.removeViewForRecord(this);
    }
}
