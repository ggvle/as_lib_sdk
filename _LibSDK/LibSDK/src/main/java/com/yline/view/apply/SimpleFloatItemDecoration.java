package com.yline.view.apply;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.yline.utils.UIScreenUtil;
import com.yline.view.common.LinearItemDecoration;

import java.util.HashMap;
import java.util.Map;

public class SimpleFloatItemDecoration extends LinearItemDecoration
{
	private Map<Integer, String> keys = new HashMap<>();

	private Context sContext;

	private Paint mTextPaint;

	private Paint mBackgroundPaint;

	public SimpleFloatItemDecoration(Context context)
	{
		super(context);

		this.sContext = context;

		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		initTextPaint(mTextPaint);

		mBackgroundPaint = new Paint();
		mBackgroundPaint.setAntiAlias(true);
		initBackgroundPaint(mBackgroundPaint);
	}

	@Override
	public boolean isDrawDivide(int totalCount, int currentPosition)
	{
		// 头部
		if (getHeadNumber() > currentPosition)
		{
			return false;
		}

		// 底部
		if (currentPosition > totalCount - 1 - getFootNumber())
		{
			return false;
		}

		// 最后一个
		if ((currentPosition == totalCount - 1 - getFootNumber()) && !isDivideLastLine())
		{
			return false;
		}

		return true;
	}

	@Override
	public void setVerticalOffsets(Rect outRect, Drawable divider, int currentPosition)
	{
		currentPosition = currentPosition - getHeadNumber();
		if (keys.containsKey(currentPosition))
		{
			outRect.set(0, getTitleHeight(), 0, 0);
		}
		else
		{
			outRect.set(0, divider.getIntrinsicHeight(), 0, 0);
		}
	}

	@Override
	public void setHorizontalOffsets(Rect outRect, Drawable divider, int currentPosition)
	{
		currentPosition = currentPosition - getHeadNumber();
		if (keys.containsKey(currentPosition))
		{
			outRect.set(getTitleHeight(), 0, 0, 0);
		}
		else
		{
			outRect.set(divider.getIntrinsicWidth(), 0, 0, 0);
		}
	}

	@Override
	public void drawVerticalDivider(Canvas c, Drawable divide, int currentPosition, int originalLeft, int originalTop, int originalRight, int originalBottom)
	{
		currentPosition = currentPosition - getHeadNumber();
		if (keys.containsKey(currentPosition))
		{
			originalBottom = originalTop + getTitleHeight();
			c.drawRect(originalLeft, originalTop, originalRight, originalBottom, mBackgroundPaint);
			c.drawText(keys.get(currentPosition), 0, 0, mTextPaint);
		}
		else
		{
			divide.setBounds(originalLeft, originalTop, originalRight, originalBottom);
			divide.draw(c);
		}
	}

	@Override
	public void drawHorizontalDivider(Canvas c, Drawable divide, int currentPosition, int originalLeft, int originalTop, int originalRight, int originalBottom)
	{
		currentPosition = currentPosition - getHeadNumber();
		if (keys.containsKey(currentPosition))
		{
			originalRight = originalLeft + getTitleHeight();
			c.drawRect(originalLeft, originalTop, originalRight, originalBottom, mBackgroundPaint);
			c.drawText(keys.get(currentPosition), 0, 0, mTextPaint);
		}
		else
		{
			divide.setBounds(originalLeft, originalTop, originalRight, originalBottom);
			divide.draw(c);
		}
	}

	public void setKeys(Map<Integer, String> keys)
	{
		this.keys.clear();
		this.keys.putAll(keys);
	}

	public void initTextPaint(Paint textPaint)
	{
		textPaint.setTextSize(UIScreenUtil.dp2px(sContext, 16));
		textPaint.setColor(Color.BLACK);
	}

	public void initBackgroundPaint(Paint backgroundPaint)
	{
		backgroundPaint.setColor(Color.GRAY);
	}

	/**
	 * 确定头部有几个不绘制分割线
	 *
	 * @return
	 */
	protected int getHeadNumber()
	{
		return 0;
	}

	/**
	 * 确定底部有几个不绘制分割线
	 *
	 * @return
	 */
	protected int getFootNumber()
	{
		return 0;
	}

	/**
	 * 最后一个分割线是否绘制
	 *
	 * @return
	 */
	protected boolean isDivideLastLine()
	{
		return false;
	}

	public int getTitleHeight()
	{
		return UIScreenUtil.dp2px(sContext, 30);
	}
}
