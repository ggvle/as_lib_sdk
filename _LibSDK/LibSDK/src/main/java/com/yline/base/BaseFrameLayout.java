package com.yline.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.yline.application.BaseApplication;
import com.yline.log.LogFileUtil;

public class BaseFrameLayout extends FrameLayout {
    public BaseFrameLayout(Context context) {
        this(context, null);
    }

    public BaseFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        BaseApplication.addViewForRecord(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LogFileUtil.m("finishInflate:" + getClass().getSimpleName());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BaseApplication.removeViewForRecord(this);
    }
}
