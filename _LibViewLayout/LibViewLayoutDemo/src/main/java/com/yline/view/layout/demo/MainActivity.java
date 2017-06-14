package com.yline.view.layout.demo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.yline.base.BaseAppCompatActivity;
import com.yline.base.BaseFragment;
import com.yline.view.layout.demo.ad.WidgetADFragment;
import com.yline.view.layout.demo.label.WidgetLabelFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseAppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final List<BaseFragment> fragmentList = new ArrayList<>();
		final List<String> titleList = new ArrayList<>();

		fragmentList.add(WidgetLabelFragment.newInstance());
		titleList.add("Label");

		fragmentList.add(WidgetADFragment.newInstance());
		titleList.add("AD 广告");

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
		ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

		viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
		{
			@Override
			public Fragment getItem(int position)
			{
				return fragmentList.get(position);
			}

			@Override
			public int getCount()
			{
				return fragmentList.size();
			}

			@Override
			public CharSequence getPageTitle(int position)
			{
				return titleList.get(position);
			}
		});
		tabLayout.setupWithViewPager(viewPager);
	}
}
