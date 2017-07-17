package com.demo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.demo.fragment.FunctionFragment;
import com.demo.fragment.UtilFragment;
import com.lib.sdk.demo.R;
import com.yline.base.BaseAppCompatActivity;
import com.yline.base.BaseFragment;
import com.yline.log.LogFileUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseAppCompatActivity
{
	private static final String[] PAGER_TITLE = {"日志+入口类", "工具类"};

	private List<BaseFragment> fragmentList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LogFileUtil.v("content");

		fragmentList.add(new FunctionFragment());
		fragmentList.add(new UtilFragment());

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
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
				return PAGER_TITLE[position];
			}
		});
		tabLayout.setupWithViewPager(viewPager);
	}
}
