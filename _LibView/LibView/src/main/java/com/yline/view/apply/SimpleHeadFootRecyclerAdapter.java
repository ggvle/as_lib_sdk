package com.yline.view.apply;

import com.yline.view.common.HeadFootRecyclerAdapter;
import com.yline.view.common.RecyclerViewHolder;

public class SimpleHeadFootRecyclerAdapter extends HeadFootRecyclerAdapter<String>
{
	@Override
	public int getItemRes()
	{
		return android.R.layout.simple_list_item_1;
	}

	@Override
	public void onBindViewHolder(RecyclerViewHolder holder, int position)
	{
		holder.setText(android.R.id.text1, sList.get(position));
	}
}
