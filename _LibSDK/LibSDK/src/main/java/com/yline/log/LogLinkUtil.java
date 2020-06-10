package com.yline.log;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import java.util.HashMap;
import java.util.Locale;

/**
 * 日志工具，将日志，统一集中输出。
 * 适合: 集中日志输出的情况
 *
 * created on 2020-06-09 -- 22:17
 * @author yline
 */
public class LogLinkUtil {
    private static HashMap<String, LogInfo> keyMap = new HashMap<>();
    private static final String TAG = "default";

    static {
        keyMap.put(TAG, new LogInfo(TAG, 123456));
    }

    private static LogHandler mHandler;

    public static void debug(String... infoArray) {
        debugInner(TAG, false, infoArray);
    }

    public static void debugEnd(String... infoArray) {
        debugInner(TAG, true, infoArray);
    }

    public static void debugTag(String tag, String[] infoArray) {
        debugInner(tag, false, infoArray);
    }

    public static void debugTagEnd(String tag, String[] infoArray) {
        debugInner(tag, true, infoArray);
    }

    private synchronized static void debugInner(String tag, boolean isEnd, String... infoArray) {
        String location = genLocation();

        LogInfo logInfo = keyMap.get(tag);
        if (null == logInfo) {
            logInfo = new LogInfo(tag, (int) System.currentTimeMillis() % Integer.MAX_VALUE);
            keyMap.put(tag, logInfo);
        }

        logInfo.location = location;
        logInfo.infoArray = infoArray;

        int arg1 = isEnd ? LogHandler.END : LogHandler.APPEND;
        getHandler().obtainMessage(logInfo.what, arg1, -1, logInfo).sendToTarget();
    }

    private static class LogHandler extends Handler {
        private static final int DELAY_TIME = 3000;

        private static final int APPEND = 1;
        private static final int END = 2;
        private static final int AUTO_END = 3;

        // key = msg.what, builder 为 内容
        private SparseArray<StringBuilder> builderArray = new SparseArray<>();

        private LogHandler(Looper looper) {
            super(looper);
        }

        @Override public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            final int arg1 = msg.arg1;
            final int what = msg.what;
            final LogInfo logInfo = (LogInfo) msg.obj;

            LogUtil.v("arg = " + msg.arg1 + ", loginfo is " + (null == logInfo ? "null" : "full"));

            // 如果为空, 则代表异常了
            if (null == logInfo) {
                return;
            }

            // 自动的清空下, 不校验内容, 因为很可能没有内容
            if (arg1 != AUTO_END && (null == logInfo.location || null == logInfo.infoArray)) {
                return;
            }

            StringBuilder sBuilder = builderArray.get(what);
            if (null == sBuilder) {
                sBuilder = new StringBuilder();
                builderArray.put(what, sBuilder);
            }

            if (arg1 == APPEND) {
                if (logInfo.startTime == 0) {
                    logInfo.debugStart(sBuilder);

                    // 发送一个自动延时的消息
                    Message message = Message.obtain(this);
                    message.what = what;
                    message.arg1 = AUTO_END;
                    message.obj = logInfo;
                    sendMessageDelayed(message, 2000 + DELAY_TIME);  // 第一次 2000ms + DELAY_TIME
                } else {
                    logInfo.debug(sBuilder);
                }
            } else if (arg1 == END) {
                removeMessages(what);   // 清空延时信息

                logInfo.debug(sBuilder);

                String result = sBuilder.toString();
                sBuilder.delete(0, sBuilder.length());

                Log.v("xxx",
                        "--------------------------------------" + logInfo.tag + " - " + what);
                Log.v("xxx", result);
                Log.v("xxx",
                        "--------------------------------------" + logInfo.tag + " - " + what);

                logInfo.reset();
            } else if (arg1 == AUTO_END) {
                // 已经结束了
                if (System.currentTimeMillis() - logInfo.tempTime > DELAY_TIME) {
                    removeMessages(what);   // 清空延时信息

                    String result = sBuilder.toString();
                    sBuilder.delete(0, sBuilder.length());

                    Log.v("xxx",
                            "--------------------------------------" + logInfo.tag + " - " + what);
                    Log.v("xxx", result);
                    Log.v("xxx",
                            "--------------------------------------" + logInfo.tag + " - " + what);

                    logInfo.reset();
                } else {
                    Message newMsg = Message.obtain(msg);

                    // 重复利用当前的msg, 再次发送一个延时消息
                    sendMessageDelayed(newMsg,
                            DELAY_TIME - (System.currentTimeMillis() - logInfo.tempTime));
                }
            }
        }
    }

    private static class LogInfo {
        private final int what;
        private final String tag;

        private String location;
        private String[] infoArray;

        private long startTime;
        private long tempTime;

        private LogInfo(String tag, int what) {
            this.what = what;
            this.tag = tag;

            this.startTime = 0;
            this.tempTime = 0;
        }

        private void debugStart(StringBuilder sBuilder) {
            startTime = System.currentTimeMillis();

            sBuilder.append("xxx-yline").append('\n');  // 开始的标记 + 换行

            sBuilder.append("0 - ").append(location).append("; "); // 位置信息

            // 内容
            if (null != infoArray && infoArray.length > 0) {
                for (int i = 0; i < infoArray.length; i++) {
                    sBuilder.append(i);
                    sBuilder.append(" = ");
                    sBuilder.append(infoArray[i]);

                    if (i != infoArray.length - 1) {
                        sBuilder.append(", ");
                    }
                }
                sBuilder.append("; ");
            }

            sBuilder.append("cost = ").append(startTime)
                    .append('-').append(System.currentTimeMillis() - startTime)
                    .append('\n');

            tempTime = System.currentTimeMillis();

            // 清空打印的信息
            location = null;
            infoArray = null;
        }

        private void debug(StringBuilder sBuilder) {
            sBuilder.append("1 - ").append(location).append("; ");  // 位置信息

            // 内容
            if (null != infoArray && infoArray.length > 0) {
                for (int i = 0; i < infoArray.length; i++) {
                    sBuilder.append(i);
                    sBuilder.append(" = ");
                    sBuilder.append(infoArray[i]);

                    if (i != infoArray.length - 1) {
                        sBuilder.append(", ");
                    }
                }
                sBuilder.append("; ");
            }

            sBuilder.append("cost = ").append(System.currentTimeMillis() - tempTime)
                    .append('-').append(System.currentTimeMillis() - startTime)
                    .append('\n');

            tempTime = System.currentTimeMillis();

            // 清空打印的信息
            location = null;
            infoArray = null;
        }

        private void reset() {
            startTime = 0;
            tempTime = 0;

            location = null;
            infoArray = null;
        }
    }

    private static String genLocation() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String clazzName = caller.getClassName();
        clazzName = clazzName.substring(clazzName.lastIndexOf('.') + 1);

        return String.format(Locale.CHINA, "%s.%s(L:%d)", clazzName, caller.getMethodName(),
                caller.getLineNumber());
    }

    private synchronized static Handler getHandler() {
        if (null == mHandler) {
            HandlerThread handlerThread = new HandlerThread("YlineLogThread");
            handlerThread.start();

            mHandler = new LogHandler(handlerThread.getLooper());
        }
        return mHandler;
    }
}
