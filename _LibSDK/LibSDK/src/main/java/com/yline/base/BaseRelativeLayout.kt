package com.yline.base

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

import com.yline.application.BaseApplication
import com.yline.log.LogUtil

/**
 * @author yline 2016/11/9 -- 21:12
 * @version 1.0.0
 */
class BaseRelativeLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    init {
        BaseApplication.addViewForRecord(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        LogUtil.v("finishInflate:" + javaClass.simpleName)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        BaseApplication.removeViewForRecord(this)
    }
}
