package com.yline.base

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

import com.yline.application.BaseApplication
import com.yline.log.LogUtil

open class BaseFrameLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

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
