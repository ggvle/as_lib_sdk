package com.yline.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * 软键盘
 */
object KeyBoardUtil {

    /**
     * 进入时,隐藏软键盘
     *
     * @param activity 上下文
     */
    fun hideKeyboard(activity: Activity) {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    /**
     * 打开 软键盘
     *
     * @param context   上下文
     * @param mEditText 输入框
     */
    fun openKeyboard(context: Context, mEditText: EditText) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (null != imm) {
            imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }

    /**
     * 关闭软键盘
     *
     * @param context   上下文
     * @param mEditText 输入框a
     */
    fun closeKeyboard(context: Context, mEditText: EditText) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }

    /**
     * 关闭软键盘
     *
     * @param activity 上下文
     * @return 是否关闭成功
     */
    fun closeKeyboard(activity: Activity): Boolean {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val topView = activity.window.peekDecorView()
        return if (null != topView && null != imm) {
            imm.hideSoftInputFromWindow(topView.windowToken, 0)
        } else false
    }
}
