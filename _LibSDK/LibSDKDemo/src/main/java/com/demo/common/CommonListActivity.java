package com.demo.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.demo.application.MainApplication;
import com.lib.sdk.demo.R;
import com.yline.base.BaseActivity;
import com.yline.log.LogFileUtil;
import com.yline.view.common.CommonListAdapter;
import com.yline.view.common.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CommonListActivity extends BaseActivity
{
	private static final String TAG = "CommonListActivity";

	private ListView lvDemo;

	private ListAdapter listAdapter;

	private String[] tempArray = {"yline", "f21", "fatenliyer", "yui", "joe"};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_list);

		lvDemo = (ListView) findViewById(R.id.lv_demo);

		listAdapter = new ListAdapter(CommonListActivity.this);
		lvDemo.setAdapter(listAdapter);

		final EditText etId = (EditText) findViewById(R.id.et_update_id);

		findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LogFileUtil.v(TAG, "btn_update");
				List<DemoBean> tempList = new ArrayList<DemoBean>();

				String idStr = etId.getText().toString().trim();
				listAdapter.clear();

				if (!TextUtils.isEmpty(idStr))
				{
					int number = Integer.parseInt(idStr);

					for (int i = 0; i < number; i++)
					{
						tempList.add(new DemoBean(i, tempArray[i % tempArray.length]));
					}

					listAdapter.addAll(tempList);
				}
				else
				{
					MainApplication.toast("the number is null");
				}
			}
		});
	}

	private class DemoBean
	{
		/**
		 * id
		 */
		private int id;

		/**
		 * name
		 */
		private String name;

		public DemoBean(int id, String name)
		{
			this.id = id;
			this.name = name;
		}

		public int getId()
		{
			return id;
		}

		public void setId(int id)
		{
			this.id = id;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}
	}

	private class ListAdapter extends CommonListAdapter<DemoBean>
	{
		public ListAdapter(Context context)
		{
			super(context);
		}

		@Override
		protected int getItemRes(int position)
		{
			return R.layout.item_common_list;
		}

		@Override
		protected void onBindViewHolder(ViewGroup parent, ViewHolder viewHolder, int position)
		{
			viewHolder.setText(R.id.tv_id, sList.get(position).getId() + "");
			viewHolder.setText(R.id.tv_name, sList.get(position).getName());
		}
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
