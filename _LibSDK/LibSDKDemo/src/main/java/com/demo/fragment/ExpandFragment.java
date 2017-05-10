package com.demo.fragment;

import android.os.Bundle;
import android.view.View;

import com.demo.application.MainApplication;
import com.demo.apply.SimpleFloatLinearDecorationActivity;
import com.demo.apply.SimpleGridDecorationActivity;
import com.demo.apply.SimpleLinearDecorationActivity;
import com.demo.apply.SimpleListActivity;
import com.demo.apply.SimpleRecyclerActivity;
import com.yline.log.LogFileUtil;
import com.yline.test.BaseTestFragment;

public class ExpandFragment extends BaseTestFragment
{
	@Override
	public void testStart(View view, Bundle savedInstanceState)
	{
		// SimpleListActivity
		addButton("SimpleListAdapter", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LogFileUtil.v(MainApplication.TAG, "CommonListAdapter");
				SimpleListActivity.actionStart(getContext());
			}
		});

		addButton("SimpleRecycleAdapter", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LogFileUtil.v(MainApplication.TAG, "CommonRecyclerAdapter");
				SimpleRecyclerActivity.actionStart(getContext());
			}
		});

		addButton("SimpleGridItemDecoration", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				SimpleGridDecorationActivity.actionStart(getContext());
			}
		});

		addButton("SimpleLinearItemDecoration", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				SimpleLinearDecorationActivity.actionStart(getContext());
			}
		});

		addButton("SimpleFloatItemDecoration", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				SimpleFloatLinearDecorationActivity.actionStart(getContext());
			}
		});
	}
}





























