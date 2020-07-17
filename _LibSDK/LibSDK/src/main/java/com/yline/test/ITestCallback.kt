package com.yline.test

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

/**
 * 测试类的 接口
 *
 * @author yline 2017/5/9 -- 15:48
 * @version 1.0.0
 */
interface ITestCallback {
    fun testStart(view: View, savedInstanceState: Bundle?)

    fun addButton(content: String, listener: View.OnClickListener): Button

    fun addEditText(hintContent: String): EditText

    fun addEditText(hintContent: String, content: String): EditText

    fun addEditNumber(hintContent: String): EditText

    fun addEditNumber(hintContent: String, content: String): EditText

    fun addImageView(width: Int, height: Int): ImageView

    fun addTextView(initContent: String): TextView
}
