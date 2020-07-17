package com.yline.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

import com.yline.application.BaseApplication
import com.yline.log.LogUtil

class BaseLinearLayout @SuppressLint("NewApi")
constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : LinearLayout(context, attrs, defStyleAttr) {

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null) : this(context, attrs, 0) {
    }

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
