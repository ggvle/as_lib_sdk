package com.yline.view.apply;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.yline.base.BaseActivity;
import com.yline.inner.InnerConstant;
import com.yline.view.demo.R;

public class SimpleListActivity extends BaseActivity
{
	private ListView lvDemo;

	private SimpleListAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.global_list);

		lvDemo = (ListView) findViewById(R.id.list_view);

		listAdapter = new SimpleListAdapter(this);
		lvDemo.setAdapter(listAdapter);

		listAdapter.setDataList(InnerConstant.getMvList());
	}

	/**
	 * 开启该CommonListActivity
	 *
	 * @param context
	 */
	public static void actionStart(Context context)
	{
		context.startActivity(new Intent(context, SimpleListActivity.class));
	}
}
