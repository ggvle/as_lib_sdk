package com.yline.inner;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.yline.base.BaseAppCompatActivity;
import com.yline.base.BaseFragment;
import com.yline.view.demo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseAppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inner_activity_main);

		final List<BaseFragment> fragmentList = new ArrayList<>();
		final List<String> titleList = new ArrayList<>();

		fragmentList.add(MainViewFragment.newInstance());
		titleList.add("View");
		
		TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_main);
		ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_main);

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
