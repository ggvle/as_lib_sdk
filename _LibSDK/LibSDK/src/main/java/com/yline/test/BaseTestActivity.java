package com.yline.test;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lib.sdk.R;
import com.yline.base.BaseAppCompatActivity;
import com.yline.utils.UIResizeUtil;
import com.yline.utils.UIScreenUtil;

public abstract class BaseTestActivity extends BaseAppCompatActivity
{
	protected LinearLayout linearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_base);
		linearLayout = (LinearLayout) findViewById(R.id.ll_base_content);

		testStart(savedInstanceState);
	}

	protected abstract void testStart(Bundle savedInstanceState);

	protected void addButton(String content, View.OnClickListener listener)
	{
		Button button = new Button(this);
		button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		UIResizeUtil.build().setBottomMargin(20).commit(button);
		button.setText(content);
		button.setOnClickListener(listener);
		linearLayout.addView(button);
	}

	protected EditText addEditText(String hintContent)
	{
		EditText editText = new EditText(this);
		editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		editText.setHint(hintContent);
		linearLayout.addView(editText);
		return editText;
	}

	protected EditText addEditText(String hintContent, String content)
	{
		EditText editText = new EditText(this);
		editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		editText.setHint(hintContent);
		editText.setText(content);
		linearLayout.addView(editText);
		return editText;
	}

	protected EditText addEditNumber(String hintContent)
	{
		EditText editText = new EditText(this);
		editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		editText.setHint(hintContent);
		editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		linearLayout.addView(editText);
		return editText;
	}

	protected EditText addEditNumber(String hintContent, String content)
	{
		EditText editText = new EditText(this);
		editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		editText.setHint(hintContent);
		editText.setText(content);
		editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		linearLayout.addView(editText);
		return editText;
	}

	protected ImageView addImageView(int width, int height)
	{
		ImageView imageView = new ImageView(this);
		imageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(UIScreenUtil.dp2px(this, width), UIScreenUtil.dp2px(this, height)));
		this.linearLayout.addView(imageView);
		return imageView;
	}

	protected TextView addTextView(String initContent)
	{
		TextView textView = new TextView(this);
		textView.setLayoutParams(new android.view.ViewGroup.LayoutParams(-1, -2));
		textView.setHint(initContent);
		this.linearLayout.addView(textView);
		return textView;
	}
}
