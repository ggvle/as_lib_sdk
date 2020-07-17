package com.yline.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Display
import android.view.View
import android.view.WindowManager

import com.yline.log.LogUtil
import java.lang.reflect.InvocationTargetException

/**
 * 功能一、获得屏幕相关的辅助类
 * 功能二、单位转换
 */
object UIScreenUtil {

    /**
     * dp to px
     *
     * @param context 上下文
     * @param dpValue dp
     * @return px
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue,
                context.resources.displayMetrics).toInt()
    }

    /**
     * sp to px
     *
     * @param context 上下文
     * @param spValue sp
     * @return px
     */
    fun sp2px(context: Context, spValue: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue,
                context.resources.displayMetrics).toInt()
    }

    /**
     * px to dp
     *
     * @param context 上下文
     * @param pxValue px
     * @return dp
     */
    fun px2dp(context: Context, pxValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return pxValue / scale
    }

    /**
     * px to sp
     *
     * @param context 上下文
     * @param pxValue px
     * @return sp
     */
    fun px2sp(context: Context, pxValue: Float): Float {
        return pxValue / context.resources.displayMetrics.scaledDensity
    }

    /**
     * 获得屏幕宽度
     *
     * @param context 上下文
     * @return such as 720 if success
     */
    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm?.defaultDisplay?.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    /**
     * 获取当前屏幕的绝对宽度,(排除状态栏、底部栏、横竖屏等因素)
     *
     * @param context 上下文
     * @return such as 720 if success
     */
    fun getAbsoluteScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val displayMetrics = DisplayMetrics()
        var widthPixels = displayMetrics.widthPixels
        var heightPixels = displayMetrics.heightPixels

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
            try {
                widthPixels = Display::class.java.getMethod("getRawWidth").invoke(display) as Int
                heightPixels = Display::class.java.getMethod("getRawHeight").invoke(display) as Int
            } catch (e: IllegalAccessException) {
                LogUtil.e("ScreenUtil getAbsoluteScreenWidth<17 IllegalAccessException", e)
            } catch (e: IllegalArgumentException) {
                LogUtil.e("ScreenUtil getAbsoluteScreenWidth<17 IllegalArgumentException", e)
            } catch (e: InvocationTargetException) {
                LogUtil.e("ScreenUtil getAbsoluteScreenWidth<17 InvocationTargetException", e)
            } catch (e: NoSuchMethodException) {
                LogUtil.e("ScreenUtil getAbsoluteScreenWidth<17 NoSuchMethodException", e)
            }

        }

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                val realSize = Point()
                Display::class.java.getMethod("getRealSize", Point::class.java).invoke(display, realSize)
                widthPixels = realSize.x
                heightPixels = realSize.y
            } catch (e: IllegalAccessException) {
                LogUtil.e("ScreenUtil getAbsoluteScreenHeight<17 IllegalAccessException", e)
            } catch (e: NoSuchMethodException) {
                LogUtil.e("ScreenUtil getAbsoluteScreenHeight<17 NoSuchMethodException", e)
            } catch (e: InvocationTargetException) {
                LogUtil.e("ScreenUtil getAbsoluteScreenHeight<17 InvocationTargetException", e)
            }

        }

        return Math.min(widthPixels, heightPixels)
    }

    /**
     * 获取当前屏幕的绝对高度,(排除状态栏、底部栏、横竖屏等因素)
     *
     * @param context 上下文
     * @return such as 1280 if success
     */
    fun getAbsoluteScreenHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val displayMetrics = DisplayMetrics()
        var widthPixels = displayMetrics.widthPixels
        var heightPixels = displayMetrics.heightPixels

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
            try {
                widthPixels = Display::class.java.getMethod("getRawWidth").invoke(display) as Int
                heightPixels = Display::class.java.getMethod("getRawHeight").invoke(display) as Int
            } catch (e: IllegalAccessException) {
                LogUtil.e("ScreenUtil getAbsoluteScreenHeight<17 IllegalAccessException", e)
            } catch (e: NoSuchMethodException) {
                LogUtil.e("ScreenUtil getAbsoluteScreenHeight<17 NoSuchMethodException", e)
            } catch (e: InvocationTargetException) {
                LogUtil.e("ScreenUtil getAbsoluteScreenHeight<17 InvocationTargetException", e)
            }

        }

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                val realSize = Point()
                Display::class.java.getMethod("getRealSize", Point::class.java).invoke(display, realSize)
                widthPixels = realSize.x
                heightPixels = realSize.y
            } catch (e: IllegalAccessException) {
                LogUtil.e("ScreenUtil getAbsoluteScreenHeight>=17 IllegalAccessException", e)
            } catch (e: IllegalArgumentException) {
                LogUtil.e("ScreenUtil getAbsoluteScreenHeight>=17 IllegalArgumentException", e)
            } catch (e: InvocationTargetException) {
                LogUtil.e("ScreenUtil getAbsoluteScreenHeight>=17 InvocationTargetException", e)
            } catch (e: NoSuchMethodException) {
                LogUtil.e("ScreenUtil getAbsoluteScreenHeight>=17 NoSuchMethodException", e)
            }

        }

        return Math.max(widthPixels, heightPixels)
    }

    /**
     * 获得屏幕高度
     *
     * @param context 上下文
     * @return such as 1184 if success
     */
    fun getScreenHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm?.defaultDisplay?.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

    /**
     * 获得状态栏高度
     *
     * @param context 上下文
     * @return such as 50 if success
     */
    fun getStatusHeight(context: Context): Int {
        var statusHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = Integer.parseInt(clazz.getField("status_bar_height").get(`object`)!!.toString())
            statusHeight = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            LogUtil.e("ScreenUtil -> getStatusHeight Exception", e)
        }

        return statusHeight
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity 上下文
     * @return bitmap of screen
     */
    fun snapShotWithStatusBar(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        var bp: Bitmap? = null
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height)
        view.destroyDrawingCache()
        return bp
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity 上下文
     * @return bitmap of screen without status bar
     */
    fun snapShotWithoutStatusBar(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top

        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        var bp: Bitmap? = null
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight)
        view.destroyDrawingCache()
        return bp
    }
}
