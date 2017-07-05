package com.yline.view.layout.demo.ad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yline.application.SDKManager;
import com.yline.base.BaseAppCompatActivity;
import com.yline.log.LogFileUtil;
import com.yline.utils.UIScreenUtil;
import com.yline.view.layout.ad.WidgetAD;
import com.yline.view.layout.demo.InnerConstant;
import com.yline.view.layout.demo.R;

import java.util.List;

public class WidgetADActivity extends BaseAppCompatActivity
{
	private WidgetAD widgetAD;

	private ViewGroup viewGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_widget_ad);
		
		viewGroup = (ViewGroup) findViewById(R.id.activity_widget_ad);

		// 3 s 钟之后才开始操作
		SDKManager.getHandler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				final List<String> urlList = InnerConstant.getUrlRecList(5);

				widgetAD = new WidgetAD()
				{
					@Override
					protected int getViewPagerHeight()
					{
						return UIScreenUtil.dp2px(WidgetADActivity.this, 150);
					}
				};
				View adView = widgetAD.start(WidgetADActivity.this, urlList.size());
				widgetAD.setListener(new WidgetAD.OnPageListener()
				{
					@Override
					public void onPageClick(View v, int position)
					{

					}

					@Override
					public void onPageInstance(final ImageView imageView, int position)
					{
						LogFileUtil.v("InnerConstant.getUrlRec() = " + InnerConstant.getUrlRec());

						Glide.with(WidgetADActivity.this).load(urlList.get(position)).into(imageView);
					}
				});
				viewGroup.addView(adView);
			}
		}, 3000);
	}
	
	public static void actionStart(Context context)
	{
		context.startActivity(new Intent(context, WidgetADActivity.class));
	}
}
