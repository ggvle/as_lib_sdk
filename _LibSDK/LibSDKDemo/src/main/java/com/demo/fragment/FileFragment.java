package com.demo.fragment;

import android.view.View;

import com.demo.application.MainApplication;
import com.demo.utils.FileUtilUser;
import com.demo.utils.LogFileUtilUser;
import com.demo.utils.LogUtilUser;
import com.demo.utils.SPUtilUser;
import com.yline.log.LogFileUtil;
import com.yline.test.BaseTestFragment;

public class FileFragment extends BaseTestFragment
{


	@Override
	protected void testStart()
	{
		addButton("baseApplication", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LogFileUtil.v(MainApplication.TAG, "btn_baseApplication");
				MainApplication.toast("测试，toast");
			}
		});

		addButton("crashHandler", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				throw new ArithmeticException("crashHandler test");
			}
		});

		addButton("logUtil", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new LogUtilUser().test();
			}
		});

		addButton("LogFileUtil", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new LogFileUtilUser().test();
			}
		});

		addButton("FileUtil", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new FileUtilUser().test();
			}
		});

		addButton("SPUtil", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new SPUtilUser().test(getContext());
			}
		});
	}
}
