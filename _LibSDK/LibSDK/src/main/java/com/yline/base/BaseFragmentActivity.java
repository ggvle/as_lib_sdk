package com.yline.base;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.yline.application.BaseApplication;
import com.yline.application.SDKConstant;
import com.yline.log.LogFileUtil;
import com.yline.utils.PermissionUtil;

import java.util.List;

/**
 * simple introduction
 *
 * @author YLine 2016-5-25 - 上午7:32:58
 */
public class BaseFragmentActivity extends FragmentActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		BaseApplication.addActivity(this);
		super.onCreate(savedInstanceState);
		PermissionUtil.request(this, SDKConstant.REQUEST_CODE_PERMISSION, Manifest.permission.WRITE_EXTERNAL_STORAGE);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		List<String> result = PermissionUtil.requestHandle(SDKConstant.REQUEST_CODE_PERMISSION, requestCode, permissions, grantResults);
		LogFileUtil.v(SDKConstant.TAG_HANDLE_PERMISSION, result.toString());
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		BaseApplication.removeActivity(this);
	}
}
