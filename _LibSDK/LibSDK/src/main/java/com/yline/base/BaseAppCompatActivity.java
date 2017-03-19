package com.yline.base;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yline.application.BaseApplication;
import com.yline.application.SDKConstant;
import com.yline.log.LogFileUtil;
import com.yline.utils.PermissionUtil;

import java.util.List;

/**
 * @author yline 2016/9/4 -- 17:57
 * @version 1.0.0
 */
public class BaseAppCompatActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		BaseApplication.addActivity(this);
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
