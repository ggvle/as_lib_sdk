package com.yline.widget.menu;

import android.os.Bundle;
import android.view.View;

import com.yline.test.BaseTestFragment;

public class WidgetMenuFragment extends BaseTestFragment
{
	public static WidgetMenuFragment newInstance()
	{
		Bundle args = new Bundle();
		WidgetMenuFragment fragment = new WidgetMenuFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void testStart(View view, Bundle savedInstanceState)
	{


		addButton("DropMenu 下拉列表", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				DropMenuActivity.actionStart(getContext());
			}
		});
	}
}
