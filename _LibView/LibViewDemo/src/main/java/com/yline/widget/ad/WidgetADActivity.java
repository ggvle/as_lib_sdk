package com.yline.widget.ad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yline.base.BaseAppCompatActivity;
import com.yline.inner.InnerConstant;
import com.yline.view.demo.R;

public class WidgetADActivity extends BaseAppCompatActivity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_widget_ad);
		
		ViewGroup viewGroup = (ViewGroup) findViewById(R.id.activity_widget_ad);
		
		WidgetAD widgetAD = new WidgetAD();
		View adView = widgetAD.start(this, 3);
		widgetAD.setListener(new WidgetAD.OnPageListener()
		{
			@Override
			public void onPageClick(View v, int position)
			{
				
			}
			
			@Override
			public void onPageInstance(ImageView imageView, int position)
			{
				Glide.with(WidgetADActivity.this).load(InnerConstant.getUrlRec()).into(imageView);
			}
		});
		
		viewGroup.addView(adView);
	}
	
	public static void actionStart(Context context)
	{
		context.startActivity(new Intent(context, WidgetADActivity.class));
	}
}
