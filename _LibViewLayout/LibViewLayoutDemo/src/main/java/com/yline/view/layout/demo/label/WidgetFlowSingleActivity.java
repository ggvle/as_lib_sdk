package com.yline.view.layout.demo.label;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yline.base.BaseAppCompatActivity;
import com.yline.view.layout.demo.R;
import com.yline.view.layout.label.LabelView;

public class WidgetFlowSingleActivity extends BaseAppCompatActivity
{
	private LabelView labelClickView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_label_widget_flow_single);

		labelClickView = (LabelView) findViewById(R.id.label_click);
		labelClickView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				labelClickView.setChecked(!labelClickView.isChecked());
			}
		});
	}

	public static void actionStart(Context context)
	{
		context.startActivity(new Intent(context, WidgetFlowSingleActivity.class));
	}
}
