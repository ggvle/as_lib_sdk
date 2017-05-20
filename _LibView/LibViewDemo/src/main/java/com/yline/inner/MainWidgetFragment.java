package com.yline.inner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yline.base.BaseFragment;
import com.yline.view.demo.R;

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
		return inflater.inflate(R.layout.fragment_main_widget, container, false);
	}
}
