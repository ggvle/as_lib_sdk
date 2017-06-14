package com.yline.inner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yline.base.BaseFragment;
import com.yline.view.demo.R;
import com.yline.widget.ad.WidgetADFragment;
import com.yline.widget.label.WidgetLabelFragment;
import com.yline.widget.menu.WidgetMenuFragment;

import java.util.ArrayList;
import java.util.List;

public class MainWidgetFragment extends BaseFragment
{
	public MainWidgetFragment()
	{
	}

	public static MainWidgetFragment newInstance()
	{
		MainWidgetFragment fragment = new MainWidgetFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.inner_fragment_main_widget, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		final List<BaseFragment> fragmentList = new ArrayList<>();
		final List<String> titleList = new ArrayList<>();

		fragmentList.add(WidgetLabelFragment.newInstance());
		titleList.add("Label");

		fragmentList.add(WidgetADFragment.newInstance());
		titleList.add("AD 广告");
		
		fragmentList.add(WidgetMenuFragment.newInstance());
		titleList.add("Menu 菜单");

		TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_widget);
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager_widget);

		viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager())
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

		tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
	}
}
