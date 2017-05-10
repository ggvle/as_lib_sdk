package com.yline.view.apply;

import com.yline.view.common.HeadFootRecyclerAdapter;
import com.yline.view.common.RecyclerViewHolder;

public class SimpleHeadFootRecyclerAdapter extends HeadFootRecyclerAdapter<String>
{

	@Override
	protected void setViewContent(RecyclerViewHolder viewHolder, int position)
	{
		viewHolder.setText(android.R.id.text1, sList.get(position));
	}

	@Override
	public int getItemRes()
	{
		return android.R.layout.simple_list_item_1;
	}
}
