package com.yline.inner;

import android.os.Bundle;
import android.view.View;

import com.yline.test.BaseTestFragment;
import com.yline.view.custom.ViewCircleActivity;

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
	}
}
