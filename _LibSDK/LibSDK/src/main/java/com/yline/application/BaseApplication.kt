package com.yline.application

import android.app.Activity
import android.app.Application
import android.app.Service
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment

/**
 * 后期所有的方法调用,可以采取xUtils一样,集成到x里面
 * 1, Log to File
 * 2, Log location
 * 3, 异常错误抛出记录
 * 4, Activity管理
 * 5, Application标配Handler、Application
 *
 * @author YLine 2016-5-25 - 上午7:32:23
 */
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        SDKManager.init(this, null)
    }

    companion object {
        /**
         * 更改资源的时候,才需要做一步操作,引用不需要
         * 原子操作
         *
         * @return 全局handler
         */
        val handler: Handler
            get() = SDKManager.getHandler()

        /**
         * 吐司
         *
         * @param content 显示数据
         */
        fun toast(content: String) {
            SDKManager.toast(content)
        }

        /* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 重复提供方法 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% */

        /**
         * @return 当前application, 因为onCreate为应用入口, 因此不用担心为null
         */
        fun getApplication(): Application {
            return SDKManager.getApplication()
        }

        fun addActivity(activity: Activity) {
            SDKManager.addActivity(activity)
        }

        fun removeActivity(activity: Activity) {
            SDKManager.removeActivity(activity)
        }

        fun finishActivity() {
            SDKManager.finishActivity()
        }

        fun addFragmentForRecord(fragment: Fragment) {
            SDKManager.addFragmentForRecord(fragment)
        }

        fun removeFragmentForRecord(fragment: Fragment) {
            SDKManager.removeFragmentForRecord(fragment)
        }

        fun addFragmentForRecordFew(fragment: android.app.Fragment) {
            SDKManager.addFragmentForRecordFew(fragment)
        }

        fun removeFragmentForRecordFew(fragment: android.app.Fragment) {
            SDKManager.removeFragmentForRecordFew(fragment)
        }

        fun addViewForRecord(view: View) {
            SDKManager.addViewForRecord(view)
        }

        fun removeViewForRecord(view: View) {
            SDKManager.removeViewForRecord(view)
        }

        fun addServiceForRecord(service: Service) {
            SDKManager.addServiceForRecord(service)
        }

        fun removeServiceForRecord(service: Service) {
            SDKManager.removeServiceForRecord(service)
        }
    }
}
