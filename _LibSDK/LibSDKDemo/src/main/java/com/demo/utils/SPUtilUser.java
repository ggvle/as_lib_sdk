package com.demo.utils;

import android.content.Context;

import com.yline.log.LogFileUtil;
import com.yline.utils.SPUtil;

public class SPUtilUser
{
	private static final String TAG = "SPUtilUser";

	public void test(Context context)
	{
		SPUtil.put(context, "null", null);
		String result = (String) SPUtil.get(context, "null", null);
		LogFileUtil.v(TAG, "put -> value = " + result);
		
		// 增加两条数据
		SPUtil.put(context, "username", "utilUsername");
		SPUtil.put(context, "password", "utilPassword");
		LogFileUtil.v(TAG, "put -> value - utilUsername");
		LogFileUtil.v(TAG, "put -> value - utilPassword");

		// 更新两条数据
		SPUtil.put(context, "username", "utilUpdateUsername");
		SPUtil.put(context, "password", "utilUpdatePassword");
		LogFileUtil.v(TAG, "put -> value - utilUpdateUsername");
		LogFileUtil.v(TAG, "put -> value - utilUpdatePassword");

		// 删除一条数据
		SPUtil.remove(context, "password");
		LogFileUtil.v(TAG, "remove -> key - password");

		// 获取两条数据
		String username = (String) SPUtil.get(context, "username", "");
		String password = (String) SPUtil.get(context, "password", "");
		LogFileUtil.v(TAG, "get -> key - username");
		LogFileUtil.v(TAG, "get -> key - password");
		LogFileUtil.i(TAG, "usrname = " + username + ",password = " + password);
	}
}
