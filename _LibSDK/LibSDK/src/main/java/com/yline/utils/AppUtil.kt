package com.yline.utils

import android.content.Context
import android.content.pm.PackageManager.NameNotFoundException

import com.yline.log.LogUtil

/**
 * 跟App相关的辅助类
 *
 * @author YLine 2016年7月16日 下午10:08:21
 */
object AppUtil {

    /**
     * 获取应用程序名称
     *
     * @param context 上下文
     * @return null if exception; such as "Utils" if success
     */
    fun getAppName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            val labelRes = packageInfo.applicationInfo.labelRes
            return context.resources.getString(labelRes)
        } catch (e: NameNotFoundException) {
            LogUtil.e("AppUtils -> getAppName NameNotFoundException")
        }

        return null
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context 上下文
     * @return null if exception; such as "1.0" if success
     */
    fun getVersionName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionName
        } catch (e: NameNotFoundException) {
            LogUtil.e("AppUtils -> getVersionName NameNotFoundException")
        }

        return null
    }
}
