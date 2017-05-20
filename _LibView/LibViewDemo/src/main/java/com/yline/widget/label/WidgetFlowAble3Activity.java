package com.yline.widget.label;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yline.inner.InnerConstant;
import com.yline.view.demo.R;

public class WidgetFlowAble3Activity extends AppCompatActivity
{
	private WidgetFlowAble clickFlowAble, selectFlowAble, pressFlowAble;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_widget_flow_able_three);

		clickFlowAble = new WidgetFlowAble(this, R.id.flow_label_drawable_one)
		{
			@Override
			protected int getItemResourceId()
			{
				return super.getItemResourceId();
			}
		};
		selectFlowAble = new WidgetFlowAble(this, R.id.flow_label_drawable_two)
		{
			@Override
			protected int getItemResourceId()
			{
				return R.layout.activity_label_item_flow_able_select;
			}
		};
		pressFlowAble = new WidgetFlowAble(this, R.id.flow_label_drawable_three)
		{
			@Override
			protected int getItemResourceId()
			{
				return R.layout.activity_label_item_flow_able_press;
			}
		};

		clickFlowAble.setDataList(InnerConstant.getSingerList());
		selectFlowAble.setDataList(InnerConstant.getSingerList());
		pressFlowAble.setDataList(InnerConstant.getSingerList());
	}
	
	public static void actionStart(Context context)
	{
		context.startActivity(new Intent(context, WidgetFlowAble3Activity.class));
	}
}
