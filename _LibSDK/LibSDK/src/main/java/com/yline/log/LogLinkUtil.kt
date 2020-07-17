package com.yline.log

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.util.Log
import android.util.SparseArray
import java.util.HashMap
import java.util.Locale

/**
 * 日志工具，将日志，统一集中输出。
 * 适合: 集中日志输出的情况
 *
 * created on 2020-06-09 -- 22:17
 * @author yline
 */
object LogLinkUtil {
    private val keyMap = HashMap<String, LogInfo>()
    private val TAG = "default"

    init {
        keyMap[TAG] = LogInfo(TAG, 123456)
    }

    private var mHandler: LogHandler? = null

    fun debug(vararg infoArray: String) {
        debugInner(TAG, false, *infoArray)
    }

    fun debugEnd(vararg infoArray: String) {
        debugInner(TAG, true, *infoArray)
    }

    fun debugTag(
            tag: String,
            vararg infoArray: String
    ) {
        debugInner(tag, false, *infoArray)
    }

    fun debugTagEnd(
            tag: String,
            vararg infoArray: String
    ) {
        debugInner(tag, true, *infoArray)
    }

    @Synchronized
    private fun debugInner(
            tag: String,
            isEnd: Boolean,
            vararg infoArray: String
    ) {
        val location = genLocation()

        var logInfo: LogInfo? = keyMap[tag]
        if (null == logInfo) {
            logInfo = LogInfo(tag, System.currentTimeMillis().toInt() % Integer.MAX_VALUE)
            keyMap[tag] = logInfo
        }

        logInfo.location = location
        logInfo.infoArray = arrayOf(*infoArray)

        val arg1 = if (isEnd) LogHandler.END else LogHandler.APPEND
        getHandler().obtainMessage(logInfo.what, arg1, -1, logInfo)
                .sendToTarget()
    }

    private fun genLocation(): String {
        val caller = Throwable().stackTrace[2]
        var clazzName = caller.className
        clazzName = clazzName.substring(clazzName.lastIndexOf('.') + 1)

        return String.format(
                Locale.CHINA, "%s.%s(L:%d)", clazzName, caller.methodName,
                caller.lineNumber
        )
    }

    @Synchronized
    private fun getHandler(): Handler {
        if (null == mHandler) {
            val handlerThread = HandlerThread("YlineLogThread")
            handlerThread.start()

            mHandler = LogHandler(handlerThread.looper)
        }
        return mHandler!!
    }

    class LogInfo constructor(
            val tag: String,
            val what: Int
    ) {

        var location: String? = null
        var infoArray: Array<String>? = null

        var startTime: Long = 0
        var tempTime: Long = 0

        init {
            this.startTime = 0
            this.tempTime = 0
        }

        fun debugStart(sBuilder: StringBuilder) {
            startTime = System.currentTimeMillis()

            sBuilder.append("xxx-yline")
                    .append('\n')  // 开始的标记 + 换行

            sBuilder.append("0 - ")
                    .append(location)
                    .append("; ") // 位置信息

            // 内容
            if (null != infoArray && infoArray!!.size > 0) {
                for (i in infoArray!!.indices) {
                    sBuilder.append(i)
                    sBuilder.append(" = ")
                    sBuilder.append(infoArray!![i])

                    if (i != infoArray!!.size - 1) {
                        sBuilder.append(", ")
                    }
                }
                sBuilder.append("; ")
            }

            sBuilder.append("cost = ")
                    .append(startTime)
                    .append('-')
                    .append(System.currentTimeMillis() - startTime)
                    .append('\n')

            tempTime = System.currentTimeMillis()

            // 清空打印的信息
            location = null
            infoArray = null
        }

        fun debug(sBuilder: StringBuilder) {
            sBuilder.append("1 - ")
                    .append(location)
                    .append("; ")  // 位置信息

            // 内容
            if (null != infoArray && infoArray!!.size > 0) {
                for (i in infoArray!!.indices) {
                    sBuilder.append(i)
                    sBuilder.append(" = ")
                    sBuilder.append(infoArray!![i])

                    if (i != infoArray!!.size - 1) {
                        sBuilder.append(", ")
                    }
                }
                sBuilder.append("; ")
            }

            sBuilder.append("cost = ")
                    .append(System.currentTimeMillis() - tempTime)
                    .append('-')
                    .append(System.currentTimeMillis() - startTime)
                    .append('\n')

            tempTime = System.currentTimeMillis()

            // 清空打印的信息
            location = null
            infoArray = null
        }

        fun reset() {
            startTime = 0
            tempTime = 0

            location = null
            infoArray = null
        }
    }

    class LogHandler(looper: Looper) : Handler(looper) {
        companion object {
            const val DELAY_TIME = 3000

            const val APPEND = 1
            const val END = 2
            const val AUTO_END = 3
        }

        // key = msg.what, builder 为 内容
        private val builderArray = SparseArray<StringBuilder>()

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            val arg1 = msg.arg1
            val what = msg.what
            val logInfo = msg.obj as LogInfo

            LogUtil.v(
                    "arg = " + msg.arg1 + ", loginfo is " + if (null == logInfo) "null" else "full"
            )

            // 如果为空, 则代表异常了
            if (null == logInfo) {
                return
            }

            // 自动的清空下, 不校验内容, 因为很可能没有内容
            if (arg1 != AUTO_END && (null == logInfo.location || null == logInfo.infoArray)) {
                return
            }

            var sBuilder: StringBuilder? = builderArray.get(what)
            if (null == sBuilder) {
                sBuilder = StringBuilder()
                builderArray.put(what, sBuilder)
            }

            if (arg1 == APPEND) {
                if (logInfo.startTime == 0L) {
                    logInfo.debugStart(sBuilder)

                    // 发送一个自动延时的消息
                    val message = Message.obtain(this)
                    message.what = what
                    message.arg1 = AUTO_END
                    message.obj = logInfo
                    sendMessageDelayed(
                            message, (2000 + DELAY_TIME).toLong()
                    )  // 第一次 2000ms + DELAY_TIME
                } else {
                    logInfo.debug(sBuilder)
                }
            } else if (arg1 == END) {
                removeMessages(what)   // 清空延时信息

                logInfo.debug(sBuilder)

                val result = sBuilder.toString()
                sBuilder.delete(0, sBuilder.length)

                LogUtil.v("--------------------------------------" + logInfo.tag + " - " + what)
                LogUtil.v(result)
                LogUtil.v("--------------------------------------" + logInfo.tag + " - " + what)

                logInfo.reset()
            } else if (arg1 == AUTO_END) {
                // 已经结束了
                if (System.currentTimeMillis() - logInfo.tempTime > DELAY_TIME) {
                    removeMessages(what)   // 清空延时信息

                    val result = sBuilder.toString()
                    sBuilder.delete(0, sBuilder.length)

                    LogUtil.v("--------------------------------------" + logInfo.tag + " - " + what)
                    LogUtil.v(result)
                    LogUtil.v("--------------------------------------" + logInfo.tag + " - " + what)

                    logInfo.reset()
                } else {
                    val newMsg = Message.obtain(msg)

                    // 重复利用当前的msg, 再次发送一个延时消息
                    sendMessageDelayed(
                            newMsg,
                            DELAY_TIME - (System.currentTimeMillis() - logInfo.tempTime)
                    )
                }
            }
        }
    }
}


