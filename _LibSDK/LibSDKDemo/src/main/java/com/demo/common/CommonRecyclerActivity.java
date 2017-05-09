package com.demo.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lib.sdk.demo.R;
import com.yline.base.BaseAppCompatActivity;
import com.yline.view.common.CommonRecyclerAdapter;
import com.yline.view.common.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.Random;

public class CommonRecyclerActivity extends BaseAppCompatActivity
{
	private Random random;

	private RecyclerView recyclerView;

	private ArrayList<Bean> data;

	private RecyclerView.ItemDecoration decor;

	private HomeAdapter homeAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_recycler);

		recyclerView = (RecyclerView) findViewById(R.id.recycler);
		homeAdapter = new HomeAdapter();
		recyclerView.setAdapter(homeAdapter);

		// 添加动画效果
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		initData();

		recyclerView.setLayoutManager(new GridLayoutManager(CommonRecyclerActivity.this, 4));
		decor = new DividerGridItemDecoration(CommonRecyclerActivity.this);
		recyclerView.addItemDecoration(decor, 0);
	}

	private void initData()
	{
		random = new Random();
		data = new ArrayList<>();
		for (int i = 'A'; i < 'z'; i++)
		{
			data.add(new Bean("" + (char) i, 200 + random.nextInt(100)));
		}
		homeAdapter.addAll(data);
	}

	private class Bean
	{
		public Bean(String content, int height)
		{
			this.content = content;
			this.height = height;
		}

		private String content;

		private int height;

		public String getContent()
		{
			return content;
		}

		public void setContent(String content)
		{
			this.content = content;
		}

		public int getHeight()
		{
			return height;
		}

		public void setHeight(int height)
		{
			this.height = height;
		}
	}

	private class HomeAdapter extends CommonRecyclerAdapter<Bean>
	{

		@Override
		public int getItemRes()
		{
			return R.layout.item_common_recycler;
		}

		@Override
		public void onBindViewHolder(RecyclerViewHolder viewHolder, int position)
		{
			viewHolder.setText(R.id.tv_num, sList.get(position).getContent());
		}
	}

	/**
	 * 开启该CommonListActivity
	 *
	 * @param context
	 */
	public static void actionStart(Context context)
	{
		context.startActivity(new Intent(context, CommonRecyclerActivity.class));
	}
}
