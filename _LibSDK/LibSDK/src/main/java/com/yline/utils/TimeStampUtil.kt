package com.yline.utils

import android.annotation.SuppressLint

import java.util.Calendar

/**
 * 通过时间戳的类
 *
 * @author YLine 2016年7月17日 上午12:43:06
 */
object TimeStampUtil {

    /**
     * 获取当前时间戳
     *
     * @return 1451823266000
     */
    val currentStamp: Long
        get() = System.currentTimeMillis()

    /**
     * 获取前时间戳与当前时间戳的差
     *
     * @param oldTime 前时间戳
     * @return 差(s)
     */
    fun getDiffStamp(oldTime: Long): Long {
        return currentStamp - oldTime
    }

    /**
     * 是否超时
     *
     * @param oldTime   旧的时间戳
     * @param limitTime 限制的时间(单位ms)
     * @return true(超时)
     */
    fun isStampTimeOut(oldTime: Long, limitTime: Long): Boolean {
        return getDiffStamp(oldTime) > limitTime
    }

    /**
     * 获取一个标准的时间
     *
     * @param time 1451828457000
     * @return 2016-01-03 21:40:57
     */
    @SuppressLint("SimpleDateFormat")
    fun getTimeStandard(time: Long): String {
        val date = java.util.Date(time)
        val simpleDateFormat = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return simpleDateFormat.format(date)
    }

    /**
     * 年份
     *
     * @param time 1451825885000
     * @return 2016    (2016/1/3 20:58:5)
     */
    fun getYear(time: Long): Int {
        val date = Calendar.getInstance()
        date.time = java.util.Date(time)
        return date.get(Calendar.YEAR)
    }

    /**
     * 月份
     *
     * @param time 1451825885000
     * @return 1    (2016/1/3 20:58:5)
     */
    fun getMonth(time: Long): Int {
        val date = Calendar.getInstance()
        date.time = java.util.Date(time)
        return date.get(Calendar.MONTH) + 1
    }

    /**
     * 日期
     *
     * @param time 1451825885000
     * @return 3    (2016/1/3 20:58:5)
     */
    fun getDay(time: Long): Int {
        val date = Calendar.getInstance()
        date.time = java.util.Date(time)
        return date.get(Calendar.DAY_OF_MONTH)
    }

    /**
     * 小时
     *
     * @param time 1451825885000
     * @return 20    (2016/1/3 20:58:5)
     */
    fun getHour(time: Long): Int {
        val date = Calendar.getInstance()
        date.time = java.util.Date(time)
        return date.get(Calendar.HOUR_OF_DAY)
    }

    /**
     * 分钟
     *
     * @param time 1451825885000
     * @return 58    (2016/1/3 20:58:5)
     */
    fun getMinute(time: Long): Int {
        val date = Calendar.getInstance()
        date.time = java.util.Date(time)
        return date.get(Calendar.MINUTE)
    }

    /**
     * 秒钟
     *
     * @param time such as 1451825885000
     * @return 5    (2016/1/3 20:58:5)
     */
    fun getSecond(time: Long): Int {
        val date = Calendar.getInstance()
        date.time = java.util.Date(time)
        return date.get(Calendar.SECOND)
    }

    /**
     * 礼拜几,英式计算,即Sunday算作 1
     *
     * @param time such as 1451825885000
     * @return 6
     */
    fun getDayOfWeekEnglish(time: Long): Int {
        val date = Calendar.getInstance()
        date.time = java.util.Date(time)
        return date.get(Calendar.DAY_OF_WEEK)
    }

    /**
     * 中文的礼拜几
     *
     * @param time 时间戳
     * @return 1 or 7
     */
    fun getDayOfWeek(time: Long): Int {
        val date = Calendar.getInstance()
        date.time = java.util.Date(time)
        val dayOfWeek = date.get(Calendar.DAY_OF_WEEK)
        return if (dayOfWeek == 1) {
            7
        } else {
            dayOfWeek - 1
        }
    }
}
