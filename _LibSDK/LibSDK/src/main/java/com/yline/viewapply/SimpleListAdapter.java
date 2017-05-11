package com.yline.viewapply;

import android.content.Context;
import android.view.ViewGroup;

import com.yline.common.CommonListAdapter;
import com.yline.common.ViewHolder;

/**
 * 简单的ListAdapter案例
 *
 * @author yline 2017/5/9 -- 16:06
 * @version 1.0.0
 */
public class SimpleListAdapter extends CommonListAdapter<String>
{
	public SimpleListAdapter(Context context)
	{
		super(context);
	}

	@Override
	protected int getItemRes(int position)
	{
		return android.R.layout.simple_list_item_1;
	}

	@Override
	protected void onBindViewHolder(ViewGroup parent, ViewHolder viewHolder, int position)
	{
		viewHolder.setText(android.R.id.text1, sList.get(position));
	}
}
