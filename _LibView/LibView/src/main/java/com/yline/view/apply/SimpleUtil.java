package com.yline.view.apply;

import android.content.Context;
import android.util.TypedValue;

/**
 * 只作为临时的工具类，并不提供使用
 *
 * @author yline 2017/5/11 -- 10:26
 * @version 1.0.0
 */
class SimpleUtil
{
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
}
