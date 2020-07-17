package com.yline.utils

import java.util.Locale

/**
 * 时间单位转换,转换类
 *
 * @author YLine
 *
 *
 * 2016年8月3日 下午7:50:16
 */
object TimeConvertUtil {
    /**
     * @param time 毫秒  12223000
     * @return 3:23:43
     */
    fun ms2FormatMinute(time: Int): String {
        val seconds = time / 1000
        return if (seconds < 3600) {
            String.format(Locale.CHINA, "%02d:%02d", seconds / 60, seconds % 60)
        } else {
            String.format(Locale.CHINA, "%d:%02d:%02d", seconds / 3600, seconds % 3600 / 60, seconds % 60)
        }
    }

    /**
     * 15分钟内 刚刚
     * 60分钟内 多少分钟前
     * 昨天
     * 几天前
     * 日期
     * 几个小时前
     *
     * @param oldTime 时间戳，单位毫秒
     * @return 提示所示
     */
    fun stamp2FormatTime(oldTime: Long): String {
        val newTime = System.currentTimeMillis()
        val durationTime = ((newTime - oldTime) / 1000).toInt()
        if (durationTime < 900) {
            return "刚刚"
        } else if (durationTime < 3600) {
            return (durationTime / 60).toString() + "分钟前"
        } else
        // 昨天,多少天前,日期,小时
        {
            val oldYear = TimeStampUtil.getYear(oldTime)
            val oldMonth = TimeStampUtil.getMonth(oldTime)
            val oldDay = TimeStampUtil.getDay(oldTime)
            val durationYear = TimeStampUtil.getYear(newTime) - oldYear
            val durationMonth = TimeStampUtil.getMonth(newTime) - oldMonth
            val durationDay = TimeStampUtil.getDay(newTime) - oldDay
            return if (durationYear > 0 || durationMonth > 0 || durationDay > 9) {
                String.format(Locale.CHINA, "%d-%d-%d", oldYear, oldMonth, oldDay)
            } else if (durationDay == 0) {
                (durationTime / 3600).toString() + "小时前"
            } else if (durationDay == 1) {
                "昨天"
            } else {
                durationDay.toString() + "天前"
            }
        }
    }
}
