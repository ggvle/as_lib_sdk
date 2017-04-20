package com.demo.fragment;

import android.os.Bundle;
import android.view.View;

import com.demo.application.MainApplication;
import com.demo.utils.FileUtilUser;
import com.demo.utils.LogFileUtilUser;
import com.demo.utils.LogUtilUser;
import com.demo.utils.SPUtilUser;
import com.yline.log.LogFileUtil;
import com.yline.test.BaseTestFragment;
import com.yline.utils.FileUtil;
import com.yline.utils.IOUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileFragment extends BaseTestFragment
{
	@Override
	protected void testStart(View view, Bundle savedInstanceState)
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

		addButton("测试 IOUtil", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				File dir = getContext().getExternalFilesDir("test");
				File file = FileUtil.create(dir, "sample.txt");

				try
				{
					FileOutputStream fileOutputStream = new FileOutputStream(file, true);
					IOUtil.write(System.currentTimeMillis() + ";汉字;;", fileOutputStream);
					IOUtil.close(fileOutputStream);
				} catch (FileNotFoundException e)
				{
					e.printStackTrace();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}





























