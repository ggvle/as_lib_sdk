package com.yline.view.layout.demo.label;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.yline.base.BaseAppCompatActivity;
import com.yline.view.layout.demo.InnerConstant;
import com.yline.view.layout.demo.InnerShowActivity;
import com.yline.view.layout.demo.R;
import com.yline.view.layout.label.WidgetFlow;

public class WidgetFlowActivity extends BaseAppCompatActivity
{
	private WidgetFlow widgetFlow;
	
	private TabLayout tabLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_label_widget_flow);
		
		widgetFlow = new WidgetFlow(this, R.id.flow_label);
		widgetFlow.addDataAll(InnerConstant.getSingerList());
		
		tabLayout = (TabLayout) findViewById(R.id.tab_label_flow);
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
		{
			@Override
			public void onTabSelected(TabLayout.Tab tab)
			{
				switch (tab.getPosition())
				{
					case 0:
						widgetFlow.addData(InnerConstant.getRandom());
						break;
					case 1:
						int length = widgetFlow.getDataSize();
						widgetFlow.removeView(length - 1);
						break;
					case 2:
						InnerShowActivity.actionStart(WidgetFlowActivity.this, R.layout.activity_label_widget_flow_xml);
						break;
				}
			}
			
			@Override
			public void onTabUnselected(TabLayout.Tab tab)
			{
				
			}
			
			@Override
			public void onTabReselected(TabLayout.Tab tab)
			{
				
			}
		});
		
		tabLayout.addTab(tabLayout.newTab().setText("Add"));
		tabLayout.addTab(tabLayout.newTab().setText("Remove"));
		tabLayout.addTab(tabLayout.newTab().setText("Xml展示"));
	}
	
	public static void actionStart(Context context)
	{
		context.startActivity(new Intent(context, WidgetFlowActivity.class));
	}
}
