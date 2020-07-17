package com.yline.log

import android.content.Context
import android.os.Process
import android.text.TextUtils

import com.yline.application.SDKConfig
import com.yline.utils.FileSizeUtil
import com.yline.utils.FileUtil

import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * @author yline 2017/3/10 -- 13:34
 * @version 1.0.0
 */
object LogFileUtil {
    /**
     * log trace 抛出的位置,两层,即:使用该工具的子类的位置
     */
    const val LOG_LOCATION_PARENT = 4

    private const val TAG = "LogFile" // 文件夹名称

    /**
     * LogFileUtil 错误日志tag
     */
    private const val TAG_ERROR = "LogFileUtil error -> "
    /**
     * 写入文件编号,默认
     */
    private const val START_COUNT = 0

    // 信息格式
    /**
     * 写入文件最大编号
     */
    private const val MAX_COUNT = 10
    /**
     * 写入文件,每个文件大小2M
     */
    private const val MAX_SIZE_OF_TXT = 2 * 1024 * 1024

    /**
     * log trace 抛出的位置,两层,即:使用该工具的当前位置,作为默认
     */
    private const val LOG_LOCATION_NOW = 3
    /**
     * 写入文件,路径下保存的文件名称
     */
    private const val LOG_FILE_TXT_NAME = "_log.txt"

    // 安全级别
    private const val V = "V"
    private const val D = "D"
    private const val I = "I"
    private const val W = "W"
    private const val E = "E"

    // 总格式
    private const val FORMAT = "%s.xxx->%s%s -> %s" // 时间.xxx->定位 tag -> content
    private const val FORMAT_TAG_TYPE = "%s(%s):%s/" // 时间 + type内容
    private const val FORMAT_TAG_LOCATION = "%s.%s(L:%d):" // 类名、方法名、行数
    private const val FORMAT_TAG_MSG = "xxx->%s->%s" // tag、content

    /**
     * 写入文件,文件夹,路径
     */
    private var logDirFile: File? = null
    /**
     * SDK日志内容是否输出
     */
    private var isSDKLog: Boolean = false
    /**
     * log 开关
     */
    private var isUtilLog: Boolean = false
    /**
     * 是否写到文件
     */
    private var isUtilLogToFile: Boolean = false
    /**
     * 是否定位
     */
    private var isUtilLogLocation: Boolean = false
    /**
     * 正常的LogCat失效时，使用sysOut
     */
    private var isUtilLogBySystem: Boolean = false

    fun init(context: Context, sdkConfig: SDKConfig) {
        logDirFile = context.getExternalFilesDir(TAG)
        isSDKLog = sdkConfig.isSDKLog
        isUtilLog = sdkConfig.isUtilLog
        isUtilLogToFile = sdkConfig.isUtilLogToFile
        isUtilLogLocation = sdkConfig.isUtilLogLocation
        isUtilLogBySystem = sdkConfig.isUtilLogBySystem
    }

    /**
     * 设置默认的标签
     *
     * @param content 内容
     */
    fun m(content: String) {
        print(V, LOG_LOCATION_NOW, TAG, content)
    }

    /**
     * 设置默认的标签
     *
     * @param content 内容
     */
    fun v(content: String) {
        print(V, LOG_LOCATION_NOW, TAG, content)
    }

    /**
     * @param tag     标签
     * @param content 内容
     */
    fun v(tag: String, content: String) {
        print(V, LOG_LOCATION_NOW, tag, content)
    }

    /**
     * @param tag      标签
     * @param content  内容
     * @param location 定位位置
     */
    fun v(tag: String, content: String, location: Int) {
        print(V, location, tag, content)
    }

    /**
     * @param tag     标签
     * @param content 内容
     */
    fun i(tag: String, content: String) {
        print(I, LOG_LOCATION_NOW, tag, content)
    }

    /**
     * @param tag      标签
     * @param content  内容
     * @param location 定位位置
     */
    fun i(tag: String, content: String, location: Int) {
        print(I, location, tag, content)
    }

    /**
     * @param content 内容
     */
    fun e(content: String) {
        print(E, LOG_LOCATION_NOW, TAG, content)
    }

    /**
     * @param tag     标签
     * @param content 内容
     */
    fun e(tag: String, content: String) {
        print(E, LOG_LOCATION_NOW, tag, content)
    }

    /**
     * @param tag      标签
     * @param content  内容
     * @param location 定位位置
     */
    fun e(tag: String, content: String, location: Int) {
        print(E, location, tag, content)
    }

    /**
     * @param tag     标签
     * @param content 内容
     * @param tr      错误信息
     */
    fun e(tag: String, content: String, tr: Throwable) {
        print(E, LOG_LOCATION_NOW, tag, content + "\n" + android.util.Log.getStackTraceString(tr))
    }

    private fun generateTagTime(type: String): String {
        var newType = type
        // 日期 时间: 级别
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA).format(System.currentTimeMillis())
        if (TextUtils.isEmpty(newType)) {
            newType = E
        }
        return String.format(FORMAT_TAG_TYPE, time, Process.myTid(), newType)
    }

    private fun generateTagLocation(location: Int): String {
        if (isUtilLogLocation) {
            val caller = Throwable().stackTrace[location]
            var clazzName = caller.className
            clazzName = clazzName.substring(clazzName.lastIndexOf(".") + 1)

            return String.format(Locale.CHINA, FORMAT_TAG_LOCATION, clazzName, caller.methodName, caller.lineNumber)
        } else {
            return ""
        }
    }

    /**
     * 统一打印日志
     */
    private fun print(type: String, location: Int, tag: String, content: String) {
        if (isUtilLog && isSDKLog) {
            if (isUtilLogBySystem) {
                println(String.format(FORMAT, "", generateTagLocation(location), tag, content))
            } else {
                when (type) {
                    V -> android.util.Log.v(generateTagLocation(location), String.format(FORMAT_TAG_MSG, tag, content))
                    D -> android.util.Log.d(generateTagLocation(location), String.format(FORMAT_TAG_MSG, tag, content))
                    I -> android.util.Log.i(generateTagLocation(location), String.format(FORMAT_TAG_MSG, tag, content))
                    W -> android.util.Log.w(generateTagLocation(location), String.format(FORMAT_TAG_MSG, tag, content))
                    E -> android.util.Log.e(generateTagLocation(location), String.format(FORMAT_TAG_MSG, tag, content))
                    else -> android.util.Log.v(generateTagLocation(location), String.format(FORMAT_TAG_MSG, tag, content))
                }
            }
        }

        if (isUtilLogToFile && isSDKLog) {
            writeLogToFile(String.format(FORMAT, generateTagTime(type), generateTagLocation(location), tag, content))
        }
    }

    /**
     * 写日志入文件
     *
     * @param content 日志内容
     */
    @Synchronized
    private fun writeLogToFile(content: String) {
        val dirFile = logDirFile
        val file = FileUtil.create(logDirFile, START_COUNT.toString() + LOG_FILE_TXT_NAME)
        if (null == file) {
            LogUtil.e(TAG_ERROR + "sdcard file create failed")
            return
        }

        if (!FileUtil.write(file, content)) {
            LogUtil.e(TAG_ERROR + "FileUtil write failed")
            return
        }

        val size = FileSizeUtil.getFileSize(file)
        if (size < 0) {
            LogUtil.e(TAG_ERROR + "sdcard getFileSize failed")
            return
        }

        // 分文件、限制文件个数
        if (size > MAX_SIZE_OF_TXT) {
            for (count in MAX_COUNT downTo START_COUNT) {
                if (count == MAX_COUNT) {
                    if (FileUtil.isExist(dirFile, count.toString() + LOG_FILE_TXT_NAME) && !FileUtil.delete(dirFile, MAX_COUNT.toString() + LOG_FILE_TXT_NAME)) {
                        LogUtil.e(TAG_ERROR + "FileUtil deleteFile failed")
                        return
                    }
                } else {
                    if (FileUtil.isExist(dirFile, count.toString() + LOG_FILE_TXT_NAME) && !FileUtil.rename(dirFile, count.toString() + LOG_FILE_TXT_NAME, (count + 1).toString() + LOG_FILE_TXT_NAME)) {
                        LogUtil.e(TAG_ERROR + "FileUtil renameFile failed")
                        return
                    }
                }
            }
        }
    }
}
