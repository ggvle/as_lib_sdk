package com.yline.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.yline.application.BaseApplication;
import com.yline.log.LogFileUtil;

/**
 * @author yline 2016/11/9 -- 21:12
 * @version 1.0.0
 */
public class BaseRelativeLayout extends RelativeLayout {

    public BaseRelativeLayout(Context context) {
        this(context, null);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
