package com.yline.view.apply;

import com.yline.view.common.CommonRecyclerAdapter;
import com.yline.view.common.RecyclerViewHolder;

/**
 * 简单的RecycleAdapter案例
 *
 * @author yline 2017/5/9 -- 16:05
 * @version 1.0.0
 */
public class SimpleRecycleAdapter extends CommonRecyclerAdapter<String>
{
	@Override
	public void onBindViewHolder(RecyclerViewHolder viewHolder, int position)
	{
		viewHolder.setText(android.R.id.text1, sList.get(position));
	}

	@Override
	public int getItemRes()
	{
		return android.R.layout.simple_list_item_1;
	}
}
