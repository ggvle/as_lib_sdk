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

import com.yline.base.BaseAppCompatActivity;
import com.yline.sdk.R;
import com.yline.utils.UIResizeUtil;
import com.yline.utils.UIScreenUtil;

public abstract class BaseTestActivity extends BaseAppCompatActivity implements ITestCallback
{
	protected LinearLayout linearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_base);
		linearLayout = (LinearLayout) findViewById(R.id.ll_base_content);
		
		testStart(this.getWindow().getDecorView(), savedInstanceState);
	}
	
	@Override
	public Button addButton(String content, View.OnClickListener listener)
	{
		Button button = new Button(this);
		button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		UIResizeUtil.build().setBottomMargin(20).commit(button);
		button.setText(content);
		button.setAllCaps(false);
		button.setOnClickListener(listener);
		linearLayout.addView(button);
		return button;
	}
	
	@Override
	public EditText addEditText(String hintContent)
	{
		EditText editText = new EditText(this);
		editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		editText.setHint(hintContent);
		editText.setAllCaps(false);
		linearLayout.addView(editText);
		return editText;
	}
	
	@Override
	public EditText addEditText(String hintContent, String content)
	{
		EditText editText = new EditText(this);
		editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		editText.setHint(hintContent);
		editText.setText(content);
		editText.setAllCaps(false);
		linearLayout.addView(editText);
		return editText;
	}
	
	@Override
	public EditText addEditNumber(String hintContent)
	{
		EditText editText = new EditText(this);
		editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		editText.setHint(hintContent);
		editText.setAllCaps(false);
		editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		linearLayout.addView(editText);
		return editText;
	}
	
	@Override
	public EditText addEditNumber(String hintContent, String content)
	{
		EditText editText = new EditText(this);
		editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		editText.setHint(hintContent);
		editText.setText(content);
		editText.setAllCaps(false);
		editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		linearLayout.addView(editText);
		return editText;
	}
	
	@Override
	public ImageView addImageView(int width, int height)
	{
		ImageView imageView = new ImageView(this);
		imageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(UIScreenUtil.dp2px(this, width), UIScreenUtil.dp2px(this, height)));
		this.linearLayout.addView(imageView);
		return imageView;
	}
	
	@Override
	public TextView addTextView(String initContent)
	{
		TextView textView = new TextView(this);
		textView.setLayoutParams(new android.view.ViewGroup.LayoutParams(-1, -2));
		textView.setHint(initContent);
		textView.setAllCaps(false);
		this.linearLayout.addView(textView);
		return textView;
	}
}
