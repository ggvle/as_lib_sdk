package com.demo.fragment;

import android.content.Context;
import android.os.Environment;
import android.view.View;

import com.demo.application.MainApplication;
import com.demo.utils.FileUtilUser;
import com.demo.utils.LogFileUtilUser;
import com.demo.utils.LogUtilUser;
import com.demo.utils.SPUtilUser;
import com.yline.log.LogFileUtil;
import com.yline.test.BaseTestFragment;

import java.io.File;

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

		addButton("测试每一个路径 + 权限", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Context context = getContext();

				// 应用私有存储
				File fileDir = context.getFilesDir();
				LogFileUtil.v(fileDir.getAbsolutePath());
				File cacheDir = context.getCacheDir();
				LogFileUtil.v(cacheDir.getAbsolutePath());

				// 应用扩展存储
				File fileDir21 = context.getExternalFilesDir(Environment.DIRECTORY_ALARMS);
				LogFileUtil.v(fileDir21.getAbsolutePath());
				File fileDir22 = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
				LogFileUtil.v(fileDir22.getAbsolutePath());
				File fileDir23 = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
				LogFileUtil.v(fileDir23.getAbsolutePath());
				File fileDir24 = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
				LogFileUtil.v(fileDir24.getAbsolutePath());
				File fileDir25 = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
				LogFileUtil.v(fileDir25.getAbsolutePath());
				File fileDir26 = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
				LogFileUtil.v(fileDir26.getAbsolutePath());
				File fileDir27 = context.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS);
				LogFileUtil.v(fileDir27.getAbsolutePath());
				File fileDir28 = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
				LogFileUtil.v(fileDir28.getAbsolutePath());
				File fileDir29 = context.getExternalFilesDir(Environment.DIRECTORY_PODCASTS);
				LogFileUtil.v(fileDir29.getAbsolutePath());
				File fileDir20 = context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES);
				LogFileUtil.v(fileDir20.getAbsolutePath());
				File cacheDir2 = context.getExternalCacheDir();
				LogFileUtil.v(cacheDir2.getAbsolutePath());

				// 公共存储
				File file3 = Environment.getExternalStorageDirectory();
				LogFileUtil.v(file3.getAbsolutePath());
				File file31 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
				LogFileUtil.v(file31.getAbsolutePath());
				File file32 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
				LogFileUtil.v(file32.getAbsolutePath());
				File file33 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
				LogFileUtil.v(file33.getAbsolutePath());
				File file34 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
				LogFileUtil.v(file34.getAbsolutePath());
				File file35 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
				LogFileUtil.v(file35.getAbsolutePath());
				File file36 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
				LogFileUtil.v(file36.getAbsolutePath());
				File file37 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS);
				LogFileUtil.v(file37.getAbsolutePath());
				File file38 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				LogFileUtil.v(file38.getAbsolutePath());
				File file39 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS);
				LogFileUtil.v(file39.getAbsolutePath());
				File file30 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES);
				LogFileUtil.v(file30.getAbsolutePath());
			}
		});
	}
}





























