package com.yline.view.dialog.pop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.PopupWindow;

import com.yline.base.BaseAppCompatActivity;
import com.yline.log.LogFileUtil;
import com.yline.view.dialog.InnerConstant;
import com.yline.view.dialog.demo.R;
import com.yline.view.pop.WidgetDeleteMenu;
import com.yline.view.recycler.holder.RecyclerViewHolder;
import com.yline.view.recycler.simple.SimpleRecyclerAdapter;

public class DeleteMenuActivity extends BaseAppCompatActivity
{
	private SimpleRecyclerAdapter recyclerAdapter;

	public static void actionStart(Context context)
	{
		context.startActivity(new Intent(context, DeleteMenuActivity.class));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_menu);

		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerAdapter = new SimpleRecyclerAdapter()
		{
			@Override
			public void onBindViewHolder(final RecyclerViewHolder viewHolder, int position)
			{
				super.onBindViewHolder(viewHolder, position);
				
				WidgetDeleteMenu widgetDeleteMenu = new WidgetDeleteMenu(DeleteMenuActivity.this);
				widgetDeleteMenu.setOnWidgetListener(new WidgetDeleteMenu.OnWidgetListener()
				{
					@Override
					public void onDismiss(PopupWindow popupWindow)
					{
						LogFileUtil.v("popupWindow onDismiss");
					}

					@Override
					public void onOptionSelected(View view, int position, String content)
					{
						LogFileUtil.v("onOptionSelected position = " + position + ", content = " + content);
					}
				});
				widgetDeleteMenu.showAtLocation(InnerConstant.getSingerList(3), viewHolder.getItemView());
			}
		};
		recyclerView.setAdapter(recyclerAdapter);

		recyclerAdapter.setDataList(InnerConstant.getMvList(30));
	}
}
