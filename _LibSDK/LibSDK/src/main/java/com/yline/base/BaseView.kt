package com.yline.base

import android.content.Context
import android.util.AttributeSet
import android.view.View

import com.yline.application.BaseApplication
import com.yline.log.LogUtil

class BaseView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

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
