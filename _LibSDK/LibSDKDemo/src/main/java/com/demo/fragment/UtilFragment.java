package com.demo.fragment;

import android.view.View;

import com.yline.log.LogFileUtil;
import com.yline.test.BaseTestFragment;
import com.yline.utils.third.TimeConvertUtil;

public class UtilFragment extends BaseTestFragment
{
	@Override
	protected void testStart()
	{
		addButton("时间转换", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				long oldTime = 1490411992l * 1000;

				String result = TimeConvertUtil.stamp2FormatTime(oldTime);
				LogFileUtil.v("result = " + result);
			}
		});
	}
}
