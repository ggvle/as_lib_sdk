package com.yline.view.apply;

import android.os.Bundle;
import android.view.View;

import com.yline.application.BaseApplication;
import com.yline.log.LogFileUtil;
import com.yline.test.BaseTestFragment;

public class ViewApplyFragment extends BaseTestFragment
{
	public static ViewApplyFragment newInstance()
	{
		return new ViewApplyFragment();
	}

	@Override
	public void testStart(View view, Bundle savedInstanceState)
	{
		// SimpleListActivity
		addButton("SimpleListAdapter", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LogFileUtil.v(BaseApplication.TAG, "CommonListAdapter");
				SimpleListActivity.actionStart(getContext());
			}
		});

		addButton("SimpleRecycleAdapter", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LogFileUtil.v(BaseApplication.TAG, "CommonRecyclerAdapter");
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

		addButton("SimpleHeadFootRecyclerActivity", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				SimpleHeadFootRecyclerActivity.actionStart(getContext());
			}
		});
	}
}
