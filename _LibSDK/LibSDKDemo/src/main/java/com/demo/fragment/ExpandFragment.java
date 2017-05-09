package com.demo.fragment;

import android.os.Bundle;
import android.view.View;

import com.demo.application.MainApplication;
import com.demo.common.CommonListActivity;
import com.demo.common.CommonRecyclerActivity;
import com.yline.log.LogFileUtil;
import com.yline.test.BaseTestFragment;

public class ExpandFragment extends BaseTestFragment
{
	@Override
	public void testStart(View view, Bundle savedInstanceState)
	{
		// CommonListActivity
		addButton("CommonListAdapter", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LogFileUtil.v(MainApplication.TAG, "CommonListAdapter");
				CommonListActivity.actionStart(getContext());
			}
		});

		addButton("CommonRecyclerAdapter", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LogFileUtil.v(MainApplication.TAG, "CommonRecyclerAdapter");
				CommonRecyclerActivity.actionStart(getContext());
			}
		});
	}
}





























