package com.demo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.demo.fragment.FileFragment;
import com.demo.fragment.FunctionFragment;
import com.demo.fragment.UtilFragment;
import com.lib.sdk.demo.R;
import com.yline.base.BaseAppCompatActivity;
import com.yline.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseAppCompatActivity
{
	/**
	 * 用于请求权限
	 */
	public static final int REQUEST_CODE_PERMISSION = 1025;

	public static final String TAG_HANDLE_PERMISSION = "Deny permissions : ";

	private static final String[] PAGER_TITLE = {"储存功能", "拓展功能", "工具类"};

	private List<BaseFragment> fragmentList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fragmentList.add(new FileFragment());
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
