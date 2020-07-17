package com.yline.test

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.yline.base.BaseFragment
import com.yline.sdk.R
import com.yline.utils.UIResizeUtil
import com.yline.utils.UIScreenUtil

/**
 *
 * created on 2020-07-17 -- 16:08
 * @author yline
 */
abstract class BaseTestXFragment : BaseFragment(), ITestCallback {
    protected lateinit var linearLayout: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_test_base, container, false)
        this.linearLayout = view.findViewById<View>(R.id.ll_base_content) as LinearLayout
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testStart(view, savedInstanceState)
    }

    override fun addButton(content: String, listener: View.OnClickListener): Button {
        val button = Button(context)
        button.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        UIResizeUtil.build().setBottomMargin(20).commit(button)
        button.text = content
        button.isAllCaps = false
        button.setOnClickListener(listener)
        linearLayout.addView(button)
        return button
    }

    override fun addEditText(hintContent: String): EditText {
        val editText = EditText(context)
        editText.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        editText.hint = hintContent
        editText.isAllCaps = false
        linearLayout.addView(editText)
        return editText
    }

    override fun addEditText(hintContent: String, content: String): EditText {
        val editText = EditText(context)
        editText.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        editText.hint = hintContent
        editText.setText(content)
        editText.isAllCaps = false
        linearLayout.addView(editText)
        return editText
    }

    override fun addEditNumber(hintContent: String): EditText {
        val editText = EditText(context)
        editText.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        editText.hint = hintContent
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.isAllCaps = false
        linearLayout.addView(editText)
        return editText
    }

    override fun addEditNumber(hintContent: String, content: String): EditText {
        val editText = EditText(context)
        editText.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        editText.hint = hintContent
        editText.setText(content)
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.isAllCaps = false
        linearLayout.addView(editText)
        return editText
    }

    override fun addImageView(width: Int, height: Int): ImageView {
        val imageView = ImageView(this.context)
        imageView.layoutParams = android.view.ViewGroup.LayoutParams(UIScreenUtil.dp2px(context!!, width.toFloat()), UIScreenUtil.dp2px(context!!, height.toFloat()))
        this.linearLayout.addView(imageView)
        return imageView
    }

    override fun addTextView(initContent: String): TextView {
        val textView = TextView(this.context)
        textView.layoutParams = android.view.ViewGroup.LayoutParams(-1, -2)
        textView.hint = initContent
        textView.isAllCaps = false
        this.linearLayout.addView(textView)
        return textView
    }
}
