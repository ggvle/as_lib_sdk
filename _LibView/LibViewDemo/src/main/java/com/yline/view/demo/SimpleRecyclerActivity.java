package com.yline.view.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yline.base.BaseAppCompatActivity;
import com.yline.view.apply.SimpleRecycleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SimpleRecyclerActivity extends BaseAppCompatActivity
{
	private RecyclerView recyclerView;

	private List<String> data;

	private SimpleRecycleAdapter homeAdapter;

	private Random random = new Random();

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

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_click);
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
		{
			@Override
			public void onTabSelected(TabLayout.Tab tab)
			{
				switch (tab.getPosition())
				{
					case 0:
						homeAdapter.add("add-" + random.nextInt(50));
						break;
					case 1:
						homeAdapter.add(0, "add-" + random.nextInt(50));
						break;
					case 2:
						homeAdapter.addAll(Arrays.asList("add-" + random.nextInt(50), "add-" + random.nextInt(50), "add-" + random.nextInt(50)));
						break;
					case 3:
						homeAdapter.addAll(0, Arrays.asList("add-" + random.nextInt(50), "add-" + random.nextInt(50), "add-" + random.nextInt(50)));
						break;
					case 4:
						homeAdapter.remove(0);
						break;
					case 5:
						String object = homeAdapter.getItem(homeAdapter.size() - 1);
						homeAdapter.remove(object);
						break;
					case 6:
						String objectA = homeAdapter.getItem(homeAdapter.size() - 1);
						String objectB = homeAdapter.getItem(homeAdapter.size() - 2);
						String objectC = homeAdapter.getItem(homeAdapter.size() - 3);
						homeAdapter.removeAll(Arrays.asList(objectA, objectB, objectC));
						break;
					case 7:
						homeAdapter.clear();
						break;
					case 8:
						homeAdapter.update(0, "update-" + random.nextInt(50));
						break;
				}
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab)
			{

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab)
			{

			}
		});
		tabLayout.addTab(tabLayout.newTab().setText("末尾+1"));
		tabLayout.addTab(tabLayout.newTab().setText("首位+1"));
		tabLayout.addTab(tabLayout.newTab().setText("末尾+n"));
		tabLayout.addTab(tabLayout.newTab().setText("首位+n"));
		tabLayout.addTab(tabLayout.newTab().setText("首位-1"));
		tabLayout.addTab(tabLayout.newTab().setText("末尾-1"));
		tabLayout.addTab(tabLayout.newTab().setText("末尾-n"));
		tabLayout.addTab(tabLayout.newTab().setText("清除所有"));
		tabLayout.addTab(tabLayout.newTab().setText("首位更新"));
		tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
	}

	private void initData()
	{
		data = new ArrayList<>();
		for (int i = 0; i < 22; i++)
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
