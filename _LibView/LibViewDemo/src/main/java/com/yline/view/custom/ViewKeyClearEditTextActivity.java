package com.yline.view.custom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yline.base.BaseAppCompatActivity;
import com.yline.view.demo.R;

public class ViewKeyClearEditTextActivity extends BaseAppCompatActivity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_key_clear_edit_text);

		ViewKeyClearEditText etViewKeyClear = (ViewKeyClearEditText) findViewById(R.id.view_key_clear);
	}

	public static void actionStart(Context context)
	{
		context.startActivity(new Intent(context, ViewKeyClearEditTextActivity.class));
	}
}
