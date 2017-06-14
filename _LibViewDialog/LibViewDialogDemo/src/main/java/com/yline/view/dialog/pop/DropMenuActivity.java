package com.yline.view.dialog.pop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yline.base.BaseAppCompatActivity;
import com.yline.view.dialog.InnerConstant;
import com.yline.view.dialog.demo.R;
import com.yline.view.pop.WidgetDropMenu;

import java.util.ArrayList;
import java.util.List;

public class DropMenuActivity extends BaseAppCompatActivity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drop_menu);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_drop_menu);

		List<View> containerViewList = new ArrayList<>();
		containerViewList.add(addTextView("下拉1"));
		containerViewList.add(addTextView("下拉2"));
		containerViewList.add(addTextView("下拉3"));

		new WidgetDropMenu(this, tabLayout).start(InnerConstant.getSingerList(3), containerViewList);
	}
	
	public static void actionStart(Context context)
	{
		context.startActivity(new Intent(context, DropMenuActivity.class));
	}

	private View addTextView(String content)
	{
		TextView textView = new TextView(this);
		ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		textView.setLayoutParams(layoutParams);
		textView.setBackgroundResource(android.R.color.white);
		textView.setText(content);
		return textView;
	}
}
