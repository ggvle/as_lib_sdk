package com.yline.test

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.yline.base.BaseAppCompatActivity
import com.yline.sdk.R
import com.yline.utils.UIResizeUtil
import com.yline.utils.UIScreenUtil

abstract class BaseTestActivity : BaseAppCompatActivity(), ITestCallback {
    protected lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_base)
        linearLayout = findViewById<View>(R.id.ll_base_content) as LinearLayout

        testStart(this.window.decorView, savedInstanceState)
    }

    override fun addButton(content: String, listener: View.OnClickListener): Button {
        val button = Button(this)
        button.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        UIResizeUtil.build().setBottomMargin(20).commit(button)
        button.text = content
        button.isAllCaps = false
        button.setOnClickListener(listener)
        linearLayout.addView(button)
        return button
    }

    override fun addEditText(hintContent: String): EditText {
        val editText = EditText(this)
        editText.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        editText.hint = hintContent
        editText.isAllCaps = false
        linearLayout.addView(editText)
        return editText
    }

    override fun addEditText(hintContent: String, content: String): EditText {
        val editText = EditText(this)
        editText.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        editText.hint = hintContent
        editText.setText(content)
        editText.isAllCaps = false
        linearLayout.addView(editText)
        return editText
    }

    override fun addEditNumber(hintContent: String): EditText {
        val editText = EditText(this)
        editText.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        editText.hint = hintContent
        editText.isAllCaps = false
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        linearLayout.addView(editText)
        return editText
    }

    override fun addEditNumber(hintContent: String, content: String): EditText {
        val editText = EditText(this)
        editText.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        editText.hint = hintContent
        editText.setText(content)
        editText.isAllCaps = false
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        linearLayout.addView(editText)
        return editText
    }

    override fun addImageView(width: Int, height: Int): ImageView {
        val imageView = ImageView(this)
        imageView.layoutParams = android.view.ViewGroup.LayoutParams(UIScreenUtil.dp2px(this, width.toFloat()), UIScreenUtil.dp2px(this, height.toFloat()))
        this.linearLayout.addView(imageView)
        return imageView
    }

    override fun addTextView(initContent: String): TextView {
        val textView = TextView(this)
        textView.layoutParams = android.view.ViewGroup.LayoutParams(-1, -2)
        textView.hint = initContent
        textView.isAllCaps = false
        this.linearLayout.addView(textView)
        return textView
    }
}
