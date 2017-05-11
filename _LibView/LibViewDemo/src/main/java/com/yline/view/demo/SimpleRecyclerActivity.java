package com.yline.view.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yline.base.BaseAppCompatActivity;
import com.yline.view.apply.SimpleRecycleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleRecyclerActivity extends BaseAppCompatActivity
{
	private Random random;

	private RecyclerView recyclerView;

	private List<String> data;

	private SimpleRecycleAdapter homeAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_recycler);

		recyclerView = (RecyclerView) findViewById(R.id.recycler);
		recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

		homeAdapter = new SimpleRecycleAdapter();
		recyclerView.setAdapter(homeAdapter);

		initData();
	}

	private void initData()
	{
		random = new Random();
		data = new ArrayList<>();
		for (int i = 0; i < 31; i++)
		{
			data.add((200 + random.nextInt(100)) + "");
		}

		homeAdapter.addAll(data);
	}

	/**
	 * 开启该CommonListActivity
	 *
	 * @param context
	 */
	public static void actionStart(Context context)
	{
		context.startActivity(new Intent(context, SimpleRecyclerActivity.class));
	}
}
