package com.yline.utils

import android.util.SparseIntArray
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Gallery
import android.widget.LinearLayout
import android.widget.RelativeLayout

import com.yline.application.SDKManager
import com.yline.log.LogUtil

/**
 * 功能一、build() 调整 UI 视图大小, 选择那个方法依据的是 其对应的上一层框体
 * 1,采用连缀的写法
 * 2,默认自动适配传入的View的父布局
 * 3,使用Apply作为结束方法
 * 4,默认适配宽度,不适配高度【都是按照设计比例适配】
 *
 * @author yline 2017/2/9 -- 18:39
 * @version 1.0.0
 */
class UIResizeUtil private constructor() {
    private var appWidth = 0 // 宽度适配
    private var appHeight = 0    // 高度适配
    private var isWidthAdapter: Boolean = false

    private var isHeightAdapter: Boolean = false

    fun setIsWidthAdapter(isWidthAdapter: Boolean): UIResizeUtil {
        if (isWidthAdapter) {
            array.put(IS_WIDTH_ADAPTER, TRUE)
        } else {
            array.put(IS_WIDTH_ADAPTER, FALSE)
        }
        return this
    }

    fun setIsHeightAdapter(isHeightAdapter: Boolean): UIResizeUtil {
        if (isHeightAdapter) {
            array.put(IS_HEIGHT_ADAPTER, TRUE)
        } else {
            array.put(IS_HEIGHT_ADAPTER, FALSE)
        }
        return this
    }

    fun setWidth(value: Int): UIResizeUtil {
        array.put(WIDTH, value)
        return this
    }

    fun setHeight(value: Int): UIResizeUtil {
        array.put(HEIGHT, value)
        return this
    }

    fun setLeftMargin(value: Int): UIResizeUtil {
        array.put(LEFT_MARGIN, value)
        return this
    }

    fun setRightMargin(value: Int): UIResizeUtil {
        array.put(RIGHT_MARGIN, value)
        return this
    }

    fun setTopMargin(value: Int): UIResizeUtil {
        array.put(TOP_MARGIN, value)
        return this
    }

    fun setBottomMargin(value: Int): UIResizeUtil {
        array.put(BOTTOM_MARGIN, value)
        return this
    }

    /**
     * 实现 view 控制
     *
     * @param view 被控制的view
     */
    fun commit(view: View?) {
        if (null != view) {
            var param: ViewGroup.LayoutParams? = view.layoutParams

            // 配置 type 和 param
            val type: Int
            if (view.parent is FrameLayout) {
                type = FRAME_LAYOUT
                if (null == param) {
                    param = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                }
            } else if (view.parent is LinearLayout)
            // RadioGroup
            {
                type = LINEAR_LAYOUT
                if (null == param) {
                    param = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                }
            } else if (view.parent is RelativeLayout) {
                type = RELATIVE_LAYOUT
                if (null == param) {
                    param = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                }
            } else if (view.parent is Gallery.LayoutParams) {
                type = GALLERY_LAYOUT
                if (null == param) {
                    param = Gallery.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                }
            } else
            // ViewGroup, Gallery
            {
                type = OTHERS_LAYOUT
                if (null == param) {
                    param = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                }
            }

            apply(view, param, type)
        }
    }

    private fun apply(view: View, param: ViewGroup.LayoutParams, type: Int) {
        // 设置值
        for (i in 0 until array.size()) {
            when (array.keyAt(i)) {
                IS_WIDTH_ADAPTER -> isWidthAdapter = if (array.get(IS_WIDTH_ADAPTER) == TRUE) true else false
                IS_HEIGHT_ADAPTER -> isHeightAdapter = if (array.get(IS_HEIGHT_ADAPTER) == TRUE) true else false
                WIDTH -> setWidth(param, array.get(WIDTH))
                HEIGHT -> setHeight(param, array.get(HEIGHT))
                LEFT_MARGIN -> setLeftMargin(type, param, array.get(LEFT_MARGIN))
                RIGHT_MARGIN -> setRightMargin(type, param, array.get(RIGHT_MARGIN))
                TOP_MARGIN -> setTopMargin(type, param, array.get(TOP_MARGIN))
                BOTTOM_MARGIN -> setBottomMargin(type, param, array.get(BOTTOM_MARGIN))
                else -> {
                }
            }
        }

        view.layoutParams = param
    }

    private fun setWidth(param: ViewGroup.LayoutParams, value: Int) {
        var value = value
        if (isWidthAdapter) {
            value = value * getAppWidth() / designWidth
        }
        param.width = value
    }

    private fun setHeight(param: ViewGroup.LayoutParams, value: Int) {
        var value = value
        if (isHeightAdapter) {
            value = value * getAppHeight() / designHeight
        }
        param.height = value
    }

    private fun setLeftMargin(type: Int, param: ViewGroup.LayoutParams, value: Int) {
        var value = value
        if (isWidthAdapter) {
            value = value * getAppWidth() / designWidth
        }

        when (type) {
            FRAME_LAYOUT -> (param as FrameLayout.LayoutParams).leftMargin = value
            LINEAR_LAYOUT -> (param as LinearLayout.LayoutParams).leftMargin = value
            RELATIVE_LAYOUT -> (param as RelativeLayout.LayoutParams).leftMargin = value
            else -> {
            }
        }
    }

    private fun setRightMargin(type: Int, param: ViewGroup.LayoutParams, value: Int) {
        var value = value
        if (isWidthAdapter) {
            value = value * getAppWidth() / designWidth
        }

        when (type) {
            FRAME_LAYOUT -> (param as FrameLayout.LayoutParams).rightMargin = value
            LINEAR_LAYOUT -> (param as LinearLayout.LayoutParams).rightMargin = value
            RELATIVE_LAYOUT -> (param as RelativeLayout.LayoutParams).rightMargin = value
            else -> {
            }
        }
    }

    private fun setTopMargin(type: Int, param: ViewGroup.LayoutParams, value: Int) {
        var value = value
        if (isHeightAdapter) {
            value = value * getAppHeight() / designHeight
        }

        when (type) {
            FRAME_LAYOUT -> (param as FrameLayout.LayoutParams).topMargin = value
            LINEAR_LAYOUT -> (param as LinearLayout.LayoutParams).topMargin = value
            RELATIVE_LAYOUT -> (param as RelativeLayout.LayoutParams).topMargin = value
            else -> {
            }
        }
    }

    private fun setBottomMargin(type: Int, param: ViewGroup.LayoutParams, value: Int) {
        var value = value
        if (isHeightAdapter) {
            value = value * getAppHeight() / designHeight
        }

        when (type) {
            FRAME_LAYOUT -> (param as FrameLayout.LayoutParams).bottomMargin = value
            LINEAR_LAYOUT -> (param as LinearLayout.LayoutParams).bottomMargin = value
            RELATIVE_LAYOUT -> (param as RelativeLayout.LayoutParams).bottomMargin = value
            else -> {
            }
        }
    }

    /**
     * 获取屏幕宽度,策略为先从缓存中获取
     *
     * @return 屏幕宽度
     */
    fun getAppWidth(): Int {
        if (appWidth == 0) {
            appWidth = UIScreenUtil.getAbsoluteScreenWidth(SDKManager.getApplication())
            LogUtil.v("UIResizeUtils -> getAppWidth width = $appWidth")
        }
        return appWidth
    }

    fun getAppHeight(): Int {
        if (0 == appHeight) {
            appHeight = UIScreenUtil.getAbsoluteScreenHeight(SDKManager.getApplication())
            LogUtil.v("UIResizeUtils -> getAppHeight height = $appHeight")
        }
        return appHeight
    }

    private object UIResizeHold {
        internal val sInstance = UIResizeUtil()
    }

    companion object {
        // 常量 boolean 值
        private const val TRUE = 1

        private const val FALSE = 0

        // key of array
        private const val WIDTH = 0

        private const val HEIGHT = 1

        private const val LEFT_MARGIN = 2

        private const val RIGHT_MARGIN = 3

        private const val TOP_MARGIN = 4

        private const val BOTTOM_MARGIN = 5

        private const val IS_WIDTH_ADAPTER = 6

        private const val IS_HEIGHT_ADAPTER = 7

        // type of parent view
        private const val LINEAR_LAYOUT = 10

        private const val FRAME_LAYOUT = 11

        private const val RELATIVE_LAYOUT = 12

        private const val GALLERY_LAYOUT = 13

        private const val OTHERS_LAYOUT = 14

        // 设计图 宽度和高度
        const val designWidth = 720 // 设计图宽度

        const val designHeight = 1080 // 设计图高度
        private val array = SparseIntArray()

        fun build(): UIResizeUtil {
            val util = UIResizeHold.sInstance
            array.clear()
            return util
        }
    }
}
