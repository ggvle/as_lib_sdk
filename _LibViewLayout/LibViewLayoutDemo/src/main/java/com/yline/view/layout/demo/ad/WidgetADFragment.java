package com.yline.view.layout.demo.ad;

import android.os.Bundle;
import android.view.View;

import com.yline.test.BaseTestFragment;

public class WidgetADFragment extends BaseTestFragment
{
	public static WidgetADFragment newInstance()
	{
		WidgetADFragment fragment = new WidgetADFragment();
		return fragment;
	}

	@Override
	public void testStart(View view, Bundle savedInstanceState)
	{
		addButton("AD", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				WidgetADActivity.actionStart(getContext());
			}
		});
	}
}