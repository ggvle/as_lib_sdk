package com.yline.view.custom;

import android.os.Bundle;
import android.view.View;

import com.yline.test.BaseTestFragment;

public class ViewCustomFragment extends BaseTestFragment
{
	public static ViewCustomFragment newInstance()
	{
		ViewCustomFragment fragment = new ViewCustomFragment();
		return fragment;
	}

	@Override
	public void testStart(View view, Bundle savedInstanceState)
	{
		addButton("ViewCircle", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ViewCircleActivity.actionStart(getContext());
			}
		});

		addButton("ViewKeyClearEditTextActivity", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ViewKeyClearEditTextActivity.actionStart(getContext());
			}
		});
	}
}
