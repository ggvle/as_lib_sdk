package com.yline.widget.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.yline.application.SDKManager;
import com.yline.base.BaseAppCompatActivity;
import com.yline.inner.InnerConstant;
import com.yline.view.demo.R;
import com.yline.widget.menu.secondary.WidgetSecondary;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SecondMenuActivity extends BaseAppCompatActivity
{
	private WidgetSecondary secondaryWidget;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second_menu);

		ViewGroup viewGroup = (ViewGroup) findViewById(R.id.activity_second_menu);

		secondaryWidget = new WidgetSecondary(this, viewGroup);
		secondaryWidget.setOnSecondaryCallback(new WidgetSecondary.OnSecondaryCallback()
		{
			@Override
			public void onSecondarySelected(String first, List<String> second, String title)
			{
				SDKManager.toast("first = " + first + ",second = " + second.toString());
			}
		});

		Map<String, List<String>> provinceMap = InnerConstant.getAreaMap();
		secondaryWidget.setDataMap(provinceMap, "吉林省", Arrays.asList("长春市", "吉林市"));
	}
	
	public static void actionStart(Context context)
	{
		context.startActivity(new Intent(context, SecondMenuActivity.class));
	}
}
