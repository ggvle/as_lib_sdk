package com.yline.log

import com.yline.application.SDKConfig

import java.util.Locale

/**
 * 该工具,默认给_LibSDK使用,因此tag,全免
 * simple introduction
 * 格式:x-{[类名.方法名(L:行数)]: }功能tag - 信息content
 *
 *
 * tag 功能 目的: 某一个功能模块的tag
 * content 具体信息 目的: "start"、"end"、"number" = number 等类似信息
 *
 *
 * 级别:
 * v 主流程信息
 * d 调试信息
 * i 主流程信息注意级别
 * w 警告级别
 * e 错误级别
 *
 *
 * {}这里统一加一个开关,设置为信息安全
 *
 * @author YLine 2016-5-1
 */
object LogUtil {
    /**
     * log trace 抛出的位置,两层,即:使用该工具的子类的位置
     */
    val LOG_LOCATION_PARENT = 3
    /**
     * tag 默认格式
     */
    private val TAG = "xxx->"
    /**
     * tag 定位  默认格式
     */
    private val TAG_DEFAULT_LOCATION = "$TAG%s.%s(L:%d): "
    /**
     * msg 默认格式
     */
    private val MSG_DEFAULT = "LogUtil -> %s"
    /**
     * log trace 抛出的位置,两层,即:使用该工具的当前位置,作为默认
     */
    private val LOG_LOCATION_NOW = 2
    /**
     * log 开关
     */
    private var isUtilLog = true
    /**
     * log 是否定位
     */
    private var isUtilLogLocation = true

    fun init(sdkConfig: SDKConfig) {
        isUtilLog = sdkConfig.isUtilLog
        isUtilLogLocation = sdkConfig.isUtilLogLocation
    }

    /**
     * @param content 内容
     */
    fun v(content: String) {
        if (isUtilLog) {
            android.util.Log.v(generateTag(LOG_LOCATION_NOW), String.format(MSG_DEFAULT, content))
        }
    }

    /**
     * @param content  内容
     * @param location 定位位置
     */
    fun v(content: String, location: Int) {
        if (isUtilLog) {
            android.util.Log.v(generateTag(location), String.format(MSG_DEFAULT, content))
        }
    }

    /**
     * @param content 内容
     */
    fun i(content: String) {
        if (isUtilLog) {
            android.util.Log.i(generateTag(LOG_LOCATION_NOW), String.format(MSG_DEFAULT, content))
        }
    }

    /**
     * @param content  内容
     * @param location 定位位置
     */
    fun i(content: String, location: Int) {
        if (isUtilLog) {
            android.util.Log.i(generateTag(location), String.format(MSG_DEFAULT, content))
        }
    }

    /**
     * @param content 内容
     */
    fun e(content: String) {
        if (isUtilLog) {
            android.util.Log.e(generateTag(LOG_LOCATION_NOW), String.format(MSG_DEFAULT, content))
        }
    }

    /**
     * @param content  内容
     * @param location 定位位置
     */
    fun e(content: String, location: Int) {
        if (isUtilLog) {
            android.util.Log.e(generateTag(location), String.format(MSG_DEFAULT, content))
        }
    }

    /**
     * @param content 内容
     * @param tr      错误信息
     */
    fun e(content: String, tr: Throwable) {
        if (isUtilLog) {
            android.util.Log.e(generateTag(LOG_LOCATION_NOW), String.format(MSG_DEFAULT, content), tr)
        }
    }

    /**
     * @param content  内容
     * @param location 定位位置
     * @param tr       错误信息
     */
    fun e(content: String, location: Int, tr: Throwable) {
        if (isUtilLog) {
            android.util.Log.e(generateTag(location), String.format(MSG_DEFAULT, content), tr)
        }
    }

    private fun generateTag(location: Int): String {
        if (isUtilLogLocation) {
            val caller = Throwable().stackTrace[location]
            var clazzName = caller.className
            clazzName = clazzName.substring(clazzName.lastIndexOf(".") + 1)

            return String.format(Locale.CHINA,
                    TAG_DEFAULT_LOCATION,
                    clazzName,
                    caller.methodName,
                    caller.lineNumber)
        } else {
            return TAG
        }
    }
}
