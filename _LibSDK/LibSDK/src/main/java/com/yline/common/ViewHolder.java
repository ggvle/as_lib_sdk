package com.yline.common;

import android.app.Activity;
import android.support.v4.util.SparseArrayCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author yline 2017/3/19 -- 3:03
 * @version 1.0.0
 */
public class ViewHolder
{
	private SparseArrayCompat<View> sArray;

	private View sView;

	public ViewHolder(View view)
	{
		this.sView = view;
		sArray = new SparseArrayCompat<>();
	}

	public ViewHolder(Activity activity)
	{
		this.sView = activity.getWindow().getDecorView();
		this.sArray = new SparseArrayCompat<>();
	}

	/**
	 * 获取到相应的资源
	 *
	 * @param viewId 子布局id
	 * @param <T>    View的子类
	 * @return View的子类
	 */
	public <T extends View> T get(int viewId)
	{
		if (sArray.get(viewId) == null)
		{
			View view = sView.findViewById(viewId);
			sArray.put(viewId, view);
		}
		return (T) sArray.get(viewId);
	}

	/**
	 * 获取当前的View
	 *
	 * @return
	 */
	public View getItemView()
	{
		return this.sView;
	}

	/**
	 * 获取 文本内容
	 *
	 * @param viewId
	 * @return
	 */
	public String getText(int viewId)
	{
		TextView textView = this.get(viewId);
		return textView.getText().toString();
	}

	/**
	 * 要求是TextView;   这样的方法就可以多写几个,然后就可以作死的连缀了
	 *
	 * @param viewId  资源
	 * @param content 内容
	 * @return 当前ViewHolder
	 */
	public TextView setText(int viewId, String content)
	{
		TextView textView = this.get(viewId);
		textView.setText(content);
		return textView;
	}
	
	/**
	 * 要求是ImageView;
	 *
	 * @param viewId 资源id
	 * @param resId  图片背景id
	 * @return 当前ViewHolder
	 */
	public ImageView setImageBackgroundResource(int viewId, int resId)
	{
		ImageView imageView = this.get(viewId);
		imageView.setBackgroundResource(resId);
		return imageView;
	}

	/**
	 * 设置监听事件
	 *
	 * @param viewId
	 * @param listener
	 */
	public void setOnClickListener(int viewId, View.OnClickListener listener)
	{
		this.get(viewId).setOnClickListener(listener);
	}
}
