package com.yline.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * 给本工程View使用的Util
 *
 * @author yline 2017/5/22 -- 8:49
 * @version 1.0.0
 */
public class LibViewUtil
{
	private static final String TAG = "LibView";

	/**
	 * dp to px
	 *
	 * @param context 上下文
	 * @param dpValue dp
	 * @return px
	 */
	public static int dp2px(Context context, float dpValue)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpValue,
				context.getResources().getDisplayMetrics());
	}

	/**
	 * 获得屏幕宽度
	 *
	 * @param context 上下文
	 * @return such as 720 if success
	 */
	public static int getScreenWidth(Context context)
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * 获得屏幕高度
	 *
	 * @param context 上下文
	 * @return such as 1184 if success
	 */
	public static int getScreenHeight(Context context)
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/**
	 * 获得状态栏高度
	 *
	 * @param context 上下文
	 * @return such as 50 if success
	 */
	public static int getStatusHeight(Context context)
	{
		int statusHeight = -1;
		try
		{
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e)
		{
			Log.e(TAG, "ScreenUtil -> getStatusHeight Exception", e);
		}
		return statusHeight;
	}
}
