package com.yline.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lib.sdk.R;
import com.yline.base.BaseFragment;
import com.yline.utils.third.UIResizeUtil;
import com.yline.utils.third.UIScreenUtil;

public abstract class BaseTestFragment extends BaseFragment
{
	protected LinearLayout linearLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_test_base, container, false);
		this.linearLayout = (LinearLayout) view.findViewById(R.id.ll_base_content);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		testStart();
	}

	protected abstract void testStart();

	protected Button addButton(String content, View.OnClickListener listener)
	{
		Button button = new Button(getContext());
		button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		UIResizeUtil.build().setBottomMargin(20).commit(button);
		button.setText(content);
		button.setOnClickListener(listener);
		linearLayout.addView(button);
		return button;
	}

	protected EditText addEditText(String hintContent)
	{
		EditText editText = new EditText(getContext());
		editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		editText.setHint(hintContent);
		linearLayout.addView(editText);
		return editText;
	}

	protected ImageView addImageView(int width, int height)
	{
		ImageView imageView = new ImageView(this.getContext());
		imageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(UIScreenUtil.dp2px(getContext(), width), UIScreenUtil.dp2px(getContext(), height)));
		this.linearLayout.addView(imageView);
		return imageView;
	}

	protected TextView addTextView(String initContent)
	{
		TextView textView = new TextView(this.getContext());
		textView.setLayoutParams(new android.view.ViewGroup.LayoutParams(-1, -2));
		textView.setHint(initContent);
		this.linearLayout.addView(textView);
		return textView;
	}
}
