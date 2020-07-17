package com.yline.log

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

import com.yline.application.BaseApplication
import com.yline.application.SDKManager
import com.yline.utils.FileUtil

import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.Thread.UncaughtExceptionHandler
import java.text.SimpleDateFormat
import java.util.HashMap
import java.util.Locale

/**
 * @author yline 2017/3/10 -- 13:34
 * @version 1.0.0
 */
class CrashHandler private constructor() : UncaughtExceptionHandler {

    /**
     * 该文件初始化等是否debug
     */
    private var isDebug = true

    /**
     * 保存文件的，文件夹
     */
    private var crashDirFile: File? = null

    private var mContext: Context? = null

    // 系统默认的UncaughtException处理类
    private var mDefaultHandler: UncaughtExceptionHandler? = null

    private fun init(context: Context) {
        isDebug = SDKManager.sdkConfig.isSDKLog
        crashDirFile = context.getExternalFilesDir(TAG)

        if (isDebug) {
            LogFileUtil.m("CrashHandler -> init start, crashDirFile -> " + if (null == crashDirFile) "null" else crashDirFile!!.absolutePath)
        }

        mContext = context
        // 获取系统默认的UncaughtExceptionHandler
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        // 将该CrashHandler实例设置为默认异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this)

        if (isDebug) {
            LogFileUtil.m("CrashHandler -> init end")
        }
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (isDebug) {
            LogUtil.v("$TAG uncaughtException dealing")
        }

        // 收集错误信息
        if (!handleException(ex)) {
            LogUtil.v(TAG + "uncaughtException exception is null")
        }

        if (ex is UnsatisfiedLinkError) {
            BaseApplication.finishActivity()
        }

        mDefaultHandler!!.uncaughtException(thread, ex)
    }

    /**
     * 处理此时的异常
     *
     * @param ex 异常信息
     * @return 是否处理成功
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (null == ex) {
            return false
        }

        val infoMap = collectionDeviceInfo(mContext)
        val throwableString = calculateCrashInfo(infoMap, ex)
        writeThrowableToFile(throwableString)

        uploadException()

        return true
    }

    /**
     * 上传文件到服务器
     */
    private fun uploadException() {
        // TODO
    }

    /**
     * 收集设备参数信息，并不会, 打印任何信息
     */
    private fun collectionDeviceInfo(context: Context?): Map<String, String> {
        val deviceInfoMap = HashMap<String, String>()

        // 时间
        val crashTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA).format(System.currentTimeMillis())
        deviceInfoMap["crashTime"] = crashTime

        // 包相关
        try {
            if (null != context) {
                val packageManager = context.packageManager
                if (null != packageManager) {
                    val packageInfo = packageManager.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
                    if (null != packageInfo) {
                        val versionName = if (null == packageInfo.versionName) "null" else packageInfo.versionName
                        val versionCode = packageInfo.versionCode.toString()
                        deviceInfoMap["versionName"] = versionName
                        deviceInfoMap["versionCode"] = versionCode
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            // TODO
        }

        // 反射机制
        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                deviceInfoMap[field.name] = field.get("").toString()
            } catch (e: IllegalAccessException) {
                // TODO
            }

        }
        return deviceInfoMap
    }

    private fun calculateCrashInfo(deviceInfoMap: Map<String, String>, ex: Throwable): String {
        // Key - Value
        val stringBuilder = StringBuilder()
        for ((key, value) in deviceInfoMap) {
            stringBuilder.append(key)
            stringBuilder.append(" -> ")
            stringBuilder.append(value)
            stringBuilder.append('\r')
            stringBuilder.append('\n')
        }

        // throwable info
        val causeString = getThrowableInfo(ex)
        stringBuilder.append(causeString)

        return stringBuilder.toString()
    }

    private fun getThrowableInfo(ex: Throwable): String {
        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter) // 写入错误信息
        var cause: Throwable? = ex.cause
        while (null != cause) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        return writer.toString()
    }

    /**
     * 写日志入文件，打印日志
     *
     * @param content 日志内容
     */
    @Synchronized
    private fun writeThrowableToFile(content: String) {
        // 路径名、文件名
        val crashTime = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS", Locale.CHINA).format(System.currentTimeMillis())
        val file = FileUtil.create(crashDirFile, crashTime + CRASH_TXT_FILE)
        if (null == file) {
            LogUtil.e("$TAG sdcard file create failed")
            return
        }

        // 写入日志
        if (!FileUtil.write(file, content)) {
            LogUtil.e("$TAG write failed")
        }
    }

    companion object {
        private const val TAG = "CrashHandler" // 文件夹名称

        /**
         * 文件后缀
         */
        private const val CRASH_TXT_FILE = "-CrashHandler.txt"

        @SuppressLint("StaticFieldLeak")
        private var crashHandler: CrashHandler? = null

        private val instance: CrashHandler
            get() {
                if (null == crashHandler) {
                    crashHandler = CrashHandler()
                }
                return crashHandler!!
            }

        fun initConfig(context: Context) {
            instance.init(context)
        }
    }
}
