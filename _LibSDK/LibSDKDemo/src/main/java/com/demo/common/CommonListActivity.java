package com.demo.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.lib.sdk.demo.R;
import com.yline.base.BaseActivity;
import com.yline.view.apply.SimpleListAdapter;
import com.yline.view.common.CommonListAdapter;

import java.util.Arrays;

public class CommonListActivity extends BaseActivity
{
	private static final String TAG = "CommonListActivity";

	private ListView lvDemo;

	private CommonListAdapter listAdapter;

	private String[] tempArray = {"yline", "f21", "fatenliyer", "yui", "joe", "yline", "f21", "fatenliyer", "yui", "joe", "yline", "f21", "fatenliyer", "yui", "joe"};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_list);

		lvDemo = (ListView) findViewById(R.id.lv_demo);

		listAdapter = new SimpleListAdapter(this);
		lvDemo.setAdapter(listAdapter);

		listAdapter.setDataList(Arrays.asList(tempArray));
	}

	/**
	 * 开启该CommonListActivity
	 *
	 * @param context
	 */
	public static void actionStart(Context context)
	{
		context.startActivity(new Intent(context, CommonListActivity.class));
	}
}
