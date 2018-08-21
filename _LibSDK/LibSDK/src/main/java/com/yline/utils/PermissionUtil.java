package com.yline.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

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
	 * 检查某一项权限,是否需要动态申请
	 *
	 * @param context    上下文
	 * @param permission 请求的权限
	 * @return 是否需要动态申请权限
	 */
	public static boolean check(Context context, String permission) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
				return true;
			}
		} else {
			return true;
		}
		return false;
	}
	
	/**
	 * 动态请求权限
	 *
	 * @param activity    请求的上下文
	 * @param requestCode 请求码
	 * @param permissions 请求的权限
	 */
	public static void request(Activity activity, int requestCode, String... permissions) {
		List<String> list = new ArrayList<String>();
		for (String permission : permissions) {
			if (isRequest(activity, permission)) {
				list.add(permission);
			}
		}
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && list.size() > 0) {
			activity.requestPermissions(list.toArray(new String[list.size()]), requestCode);
		}
	}
	
	/**
	 * 是否发送用户请求
	 *
	 * @return 如果用户首次看见，或者版本小于6.0则发送请求
	 */
	private static boolean isRequest(Context context, String permission) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
				return true;
			}
			// 这里还可以添加,如果上次用户已经拒绝,就不再申请
		} else {
			return true;
		}
		return false;
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
	public static List<String> requestHandle(int requestCode, int requestBackCode, String[] permissions, int[] grantResults) {
		List<String> result = new ArrayList<String>();
		if (requestCode == requestBackCode) {
			if (null != grantResults) {
				// 获取被拒绝的权限
				for (int i = 0; i < grantResults.length; i++) {
					if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
						result.add(permissions[i]);
					}
				}
			}
		}
		return result;
	}
}
