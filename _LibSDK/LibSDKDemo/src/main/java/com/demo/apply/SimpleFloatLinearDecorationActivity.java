package com.demo.apply;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lib.sdk.demo.R;
import com.yline.base.BaseAppCompatActivity;
import com.yline.utils.UIScreenUtil;
import com.yline.view.apply.SimpleFloatItemDecoration;
import com.yline.view.apply.SimpleHeadFootRecyclerAdapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SimpleFloatLinearDecorationActivity extends BaseAppCompatActivity
{

	private SimpleHeadFootRecyclerAdapter recyclerAdapter;

	private SimpleFloatItemDecoration floatItemDecoration;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_decoration);

		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_decoration);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		floatItemDecoration = new SimpleFloatItemDecoration(this)
		{
			@Override
			protected int getHeadNumber()
			{
				return 2;
			}

			@Override
			protected int getFootNumber()
			{
				return 2;
			}

			@Override
			protected int getDivideResourceId()
			{
				return R.drawable.widget_recycler_divider_black_normal;
			}
		};
		recyclerView.addItemDecoration(floatItemDecoration);

		recyclerAdapter = new SimpleHeadFootRecyclerAdapter();
		recyclerView.setAdapter(recyclerAdapter);

		View viewA = new View(this);
		viewA.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIScreenUtil.dp2px(this, 10)));
		viewA.setBackgroundColor(Color.GREEN);
		recyclerAdapter.addHeadView(viewA);

		View viewB = new View(this);
		viewB.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIScreenUtil.dp2px(this, 10)));
		viewB.setBackgroundColor(Color.RED);
		recyclerAdapter.addHeadView(viewB);

		View viewC = new View(this);
		viewC.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIScreenUtil.dp2px(this, 10)));
		viewC.setBackgroundColor(Color.GREEN);
		recyclerAdapter.addFootView(viewC);

		View viewD = new View(this);
		viewD.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIScreenUtil.dp2px(this, 10)));
		viewD.setBackgroundColor(Color.RED);
		recyclerAdapter.addFootView(viewD);

		recyclerAdapter.addAll(Arrays.asList("yline", "Simple", "English", "fatenliyer", "sin", "cos", "baby", "piano", "tree", "sky", "the world"));
		Map<Integer, String> map = new HashMap<>();
		map.put(0, "A");
		map.put(4, "B");
		floatItemDecoration.setKeys(map);
	}

	public static void actionStart(Context context)
	{
		context.startActivity(new Intent(context, SimpleFloatLinearDecorationActivity.class));
	}
}
