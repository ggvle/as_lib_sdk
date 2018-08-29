package com.yline.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yline 2016/11/19 -- 15:07
 * @version 1.0.0
 */
public class PermissionUtil {
	/**
	 * 用于请求权限
	 */
	public static final int REQUEST_CODE_PERMISSION = 1025;
	
	/**
	 * 权限返回的 tag
	 */
	public static final String TAG_HANDLE_PERMISSION = "Deny permissions : ";
	
	/**
	 * 动态请求权限
	 *
	 * @param activity    请求的上下文
	 * @param requestCode 请求码
	 * @param permissions 请求的权限
	 * @return true(正在请求)，false(不需要请求权限)
	 */
	public static boolean request(Activity activity, int requestCode, String... permissions) {
		List<String> list = new ArrayList<>();
		for (String permission : permissions) {
			if (!isPermissionGranted(activity, permission)) {
				list.add(permission);
			}
		}
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && list.size() > 0) {
			activity.requestPermissions(list.toArray(new String[list.size()]), requestCode);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 小于6.0，默认权限都是允许的
	 * 大于等于6.0，检查权限是否允许
	 *
	 * @param context    上下文
	 * @param permission 对应的权限
	 * @return true(已经允许)，false(未被允许)
	 */
	private static boolean isPermissionGranted(Context context, String permission) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			return (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
		} else {
			return true;
		}
	}
	
	/**
	 * 判断请求回调结果
	 *
	 * @param grantResults 回调结果
	 * @return true(权限都允许了)，false(有权限被拒绝了)
	 */
	public static boolean isPermissionGranted(int[] grantResults) {
		for (int result : grantResults) {
			if (result == PackageManager.PERMISSION_DENIED) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 获取，请求回调结果
	 *
	 * @param permissions  所有请求的权限
	 * @param grantResults 所有请求权限的结果
	 * @return 所有拒绝的权限的列表
	 */
	public static List<String> getPermissionGranted(@NonNull String[] permissions, @NonNull int[] grantResults) {
		List<String> deniedPermissionList = new ArrayList<>();
		for (int i = 0; i < grantResults.length; i++) {
			if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
				deniedPermissionList.add(permissions[i]);
			}
		}
		return deniedPermissionList;
	}
	
	/**
	 * 处理响应请求,返回被拒绝的权限
	 *
	 * @param requestCode     请求码
	 * @param requestBackCode 请求参数返回码
	 * @param permissions     请求的权限
	 * @param grantResults    默认成功的结果值
	 * @return 被拒绝的权限
	 */
	public static List<String> getPermissionGranted(int requestCode, int requestBackCode, String[] permissions, int[] grantResults) {
		if (requestCode == requestBackCode) {
			return getPermissionGranted(permissions, grantResults);
		}
		return new ArrayList<>();
	}
}
