package com.yline.common;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CommonRecyclerViewHolder extends RecyclerView.ViewHolder
{
	private SparseArrayCompat<View> sArray;

	public CommonRecyclerViewHolder(View itemView)
	{
		super(itemView);
		sArray = new SparseArrayCompat<>();
	}

	/**
	 * 获取到相应的资源
	 *
	 * @param viewId 子布局id
	 * @param <T>    View的子类
	 * @return 子布局
	 */
	public <T extends View> T get(int viewId)
	{
		if (sArray.get(viewId) == null)
		{
			View view = itemView.findViewById(viewId);
			sArray.put(viewId, view);
		}
		return (T) sArray.get(viewId);
	}

	/**
	 * 获取当前ViewHolder的View
	 *
	 * @return
	 */
	public View getItemView()
	{
		return this.itemView;
	}

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
	 * @return 返回当前类
	 */
	public ImageView setImageViewBackground(int viewId, int resId)
	{
		ImageView imageView = this.get(viewId);
		imageView.setBackgroundResource(resId);
		return imageView;
	}
}
