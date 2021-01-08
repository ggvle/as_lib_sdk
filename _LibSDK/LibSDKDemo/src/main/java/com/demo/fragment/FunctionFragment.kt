package com.demo.fragment

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Process
import android.view.View
import com.demo.activity.LeakCanaryActivity
import com.yline.application.SDKManager
import com.yline.application.SDKManager.toast
import com.yline.log.LogUtil
import com.yline.test.BaseTestXFragment
import com.yline.utils.perf.MonitorUtil

class FunctionFragment : BaseTestXFragment() {

    override fun testStart(view: View, savedInstanceState: Bundle?) { // 测试 LeakCanaryActivity(不能放入LibSDK中,否则失效)
        addButton("LeakCanary Activity", View.OnClickListener {
            LogUtil.v("btn_leak_canary_activity")
            LeakCanaryActivity.actionStart(context)
        })

        addButton("SDKManager", View.OnClickListener {
            LogUtil.v("btn_baseApplication")
            toast("测试，toast")
        })

        addButton("CrashHandler", View.OnClickListener { throw ArithmeticException("crashHandler test") })
        addButton("MonitorUtil", View.OnClickListener { testMonitor() })
    }

    private fun testMonitor() {
        val result = MonitorUtil.getPidMemorySize(Process.myPid(), context)
        LogUtil.v("result = $result")
    }


}