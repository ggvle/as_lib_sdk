package com.yline.base;

import android.appwidget.AppWidgetHostView;
import android.content.Context;

public class BaseAppWidgetHostView extends AppWidgetHostView
{
    public BaseAppWidgetHostView(Context context)
    {
        super(context);
    }
    
    public BaseAppWidgetHostView(Context context, int animationIn, int animationOut)
    {
        super(context, animationIn, animationOut);
    }
}
