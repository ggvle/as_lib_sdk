package com.yline.application

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Context
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.yline.log.CrashHandler
import com.yline.log.LogFileUtil
import com.yline.sdk.R
import com.yline.log.LogUtil

import java.util.ArrayList

/**
 * 全局配置, 要求初始化!!!
 *
 * @author yline 2017/4/17 -- 15:48
 * @version 1.0.0
 */
@SuppressLint("StaticFieldLeak")
object SDKManager {

    /**
     * handler 吐丝
     */
    private const val HANDLER_TOAST = -1

    /**
     * 应用心跳
     */
    private const val HANDLER_PALPITATION = -2

    /**
     * 应用心跳频率
     */
    private const val APPLICATION_TIME = 2 * 60 * 1000

    /**
     * Activity管理
     */
    private val mActivityList = ArrayList<Activity>()

    /**
     * Fragment记录(记录的只有String)
     */
    private val mFragmentList = ArrayList<String>()

    /**
     * View记录
     */
    private val mViewList = ArrayList<String>()

    /**
     * Service记录
     */
    private val mServiceList = ArrayList<String>()

    /**
     * @return 当前application, 因为onCreate为应用入口, 因此不用担心为null
     */
    private var application: Application? = null

    private var mSdkConfig: SDKConfig? = null

    /**
     * Toast 工具
     */
    private var mToast: Toast? = null
    private var mTvToast: TextView? = null

    // handler相关
    private var handler: Handler? = null

    fun init(application: Application, sdkConfig: SDKConfig?) {
        val innerSDKConfig = sdkConfig ?: SDKConfig()

        SDKManager.application = application  // 初始化全局变量
        SDKManager.mSdkConfig = innerSDKConfig

        // 异常崩溃日志
        CrashHandler.initConfig(application)

        // 打印日志工具
        LogUtil.init(innerSDKConfig)
        LogFileUtil.init(application, innerSDKConfig)

        // 设立一个程序入口的log
        LogUtil.v("应用启动 *** application start id = " + Thread.currentThread().id)
        LogUtil.v(sdkConfig.toString())
        LogUtil.v("应用启动 *** application start id = " + Thread.currentThread().id)

        getHandler().sendEmptyMessageDelayed(HANDLER_PALPITATION, APPLICATION_TIME.toLong())
    }

    fun getApplication(): Application {
        return if (null != SDKManager.application) {
            SDKManager.application!!
        } else {
            throw NullPointerException("application is null, 请初始化")
        }
    }

    /**
     * 更改资源的时候,才需要做一步操作,引用不需要
     * 原子操作
     *
     * @return 全局handler
     */
    fun getHandler(): Handler {
        if (null == handler) {
            synchronized(DefaultHandler::class.java) {
                if (null == handler) {
                    handler = DefaultHandler()
                }
            }
        }
        return handler!!
    }

    /**
     * 打印toast
     *
     * @param context 上下文
     * @param msg     内容
     */
    private fun showToast(context: Context?, msg: String) {
        if (null == mToast) {
            mToast = Toast(context)
            mToast?.duration = Toast.LENGTH_SHORT
        }

        if (null == mTvToast) {
            mTvToast = TextView(context)
            mTvToast?.setBackgroundResource(R.drawable.lib_bg_toast)
            mTvToast?.setTextColor(-0x1)
        }

        mTvToast?.text = msg
        mToast?.view = mTvToast
        mToast?.show()
    }

    /**
     * 吐司
     *
     * @param content 显示数据
     */
    fun toast(content: String) {
        getHandler().obtainMessage(HANDLER_TOAST, content).sendToTarget()
    }

    /**
     * @return 配置的SDKConfig，因为onCreate为应用入口, 因此不用担心为null
     */
    val sdkConfig: SDKConfig
        get() {
            if (null == mSdkConfig) {
                synchronized(SDKConfig::class.java) {
                    if (null == mSdkConfig) {
                        mSdkConfig = SDKConfig()
                    }
                }
            }
            return mSdkConfig!!
        }

    fun addActivity(activity: Activity) {
        LogUtil.v("addActivity:" + activity.javaClass.simpleName)
        mActivityList.add(activity)
    }

    fun removeActivity(activity: Activity) {
        LogUtil.v("removeActivity:" + activity.javaClass.simpleName)
        mActivityList.remove(activity)
    }

    fun finishActivity() {
        for (activity in mActivityList) {
            LogUtil.v("finishActivity:" + activity.javaClass.simpleName)
            activity.finish()
        }
    }

    fun addFragmentForRecord(fragment: Fragment) {
        LogUtil.v("addFragmentForRecord X:" + fragment.javaClass.simpleName)
        mFragmentList.add(fragment.javaClass.simpleName)
    }

    fun removeFragmentForRecord(fragment: Fragment) {
        LogUtil.v("removeFragmentForRecord X:" + fragment.javaClass.simpleName)
        mFragmentList.remove(fragment.javaClass.simpleName)
    }

    fun addFragmentForRecordFew(fragment: android.app.Fragment) {
        LogUtil.v("addFragmentForRecord Few:" + fragment.javaClass.simpleName)
        mFragmentList.add(fragment.javaClass.simpleName)
    }

    fun removeFragmentForRecordFew(fragment: android.app.Fragment) {
        LogUtil.v("removeFragmentForRecord Few:" + fragment.javaClass.simpleName)
        mFragmentList.remove(fragment.javaClass.simpleName)
    }

    fun addViewForRecord(view: View) {
        LogUtil.v("addViewForRecord:" + view.javaClass.simpleName)
        mViewList.add(view.javaClass.simpleName)
    }

    fun removeViewForRecord(view: View) {
        LogUtil.v("removeViewForRecord:" + view.javaClass.simpleName)
        mViewList.remove(view.javaClass.simpleName)
    }

    fun addServiceForRecord(service: Service) {
        LogUtil.v("addServiceForRecord:" + service.javaClass.simpleName)
        mServiceList.add(service.javaClass.simpleName)
    }

    fun removeServiceForRecord(service: Service) {
        LogUtil.v("removeServiceForRecord:" + service.javaClass.simpleName)
        mServiceList.remove(service.javaClass.simpleName)
    }

    private class DefaultHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                HANDLER_PALPITATION -> {
                    LogUtil.v("this time = " + System.currentTimeMillis() + ",this thread = " + Thread.currentThread().id)
                    getHandler().sendEmptyMessageDelayed(HANDLER_PALPITATION, APPLICATION_TIME.toLong())
                }
                HANDLER_TOAST -> showToast(application, msg.obj as String)
                else -> {
                }
            }
        }
    }
}
