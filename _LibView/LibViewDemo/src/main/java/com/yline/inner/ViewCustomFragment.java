package com.yline.inner;

import android.os.Bundle;
import android.view.View;

import com.yline.test.BaseTestFragment;
import com.yline.view.custom.ProgressCircleActivity;
import com.yline.view.custom.SuperSwipeSimpleActivity;
import com.yline.view.custom.ViewCircleActivity;
import com.yline.view.custom.ViewKeyClearEditTextActivity;

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
		
		addButton("ViewCircleProgressBar", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ProgressCircleActivity.actionStart(getContext());
			}
		});

		addButton("SuperSwipeRefreshLayout Simple", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				SuperSwipeSimpleActivity.actionStart(getContext());
			}
		});
	}
}
