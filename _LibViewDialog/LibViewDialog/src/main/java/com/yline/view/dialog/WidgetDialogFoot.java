package com.yline.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

/**
 * 底部出现弹框,仿IOS
 *
 * @author yline 2017/4/11 -- 10:54
 * @version 1.0.0
 */
public class WidgetDialogFoot
{
	private Dialog dialog;

	private OnSelectedListener onSelectedListener;

	protected View parentView;

	/**
	 * 当前最多只支持4个
	 *
	 * @param context
	 * @param dataList 数据是从下往上排序
	 */
	public WidgetDialogFoot(Context context, @NonNull List<String> dataList)
	{
		parentView = LayoutInflater.from(context).inflate(getResourceId(), null);
		initData(dataList);

		dialog = new Dialog(context, getDialogStyle());// android.R.style.Theme_Holo_Light_Dialog_NoActionBar
		dialog.setContentView(parentView);
		dialog.setCanceledOnTouchOutside(true);

		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.BOTTOM);

		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
		lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		dialog.onWindowAttributesChanged(lp);
	}

	/**
	 * @param dataList 数据是从下往上排序
	 */
	private void initData(final List<String> dataList)
	{
		int dataLength = dataList.size();
		if (dataLength > 4)
		{
			throw new IllegalArgumentException("dialog only support to 4");
		}

		if (dataLength > 3)
		{
			TextView textView = (TextView) parentView.findViewById(R.id.btn_dialog_foot_one);
			textView.setText(dataList.get(3));
			textView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if (null != onSelectedListener)
					{
						onSelectedListener.onOptionSelected(dialog, 3, dataList.get(3));
					}
				}
			});
		}
		else
		{
			parentView.findViewById(R.id.btn_dialog_foot_one).setVisibility(View.GONE);
			parentView.findViewById(R.id.view_dialog_foot_one).setVisibility(View.GONE);
		}

		if (dataLength > 2)
		{
			TextView textView = (TextView) parentView.findViewById(R.id.btn_dialog_foot_two);
			textView.setText(dataList.get(2));
			textView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if (null != onSelectedListener)
					{
						onSelectedListener.onOptionSelected(dialog, 2, dataList.get(2));
					}
				}
			});
		}
		else
		{
			parentView.findViewById(R.id.btn_dialog_foot_two).setVisibility(View.GONE);
			parentView.findViewById(R.id.view_dialog_foot_two).setVisibility(View.GONE);
		}

		if (dataLength > 1)
		{
			TextView textView = (TextView) parentView.findViewById(R.id.btn_dialog_foot_three);
			textView.setText(dataList.get(1));
			textView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if (null != onSelectedListener)
					{
						onSelectedListener.onOptionSelected(dialog, 1, dataList.get(1));
					}
				}
			});
		}
		else
		{
			parentView.findViewById(R.id.btn_dialog_foot_three).setVisibility(View.GONE);
			parentView.findViewById(R.id.view_dialog_foot_three).setVisibility(View.GONE);
		}

		if (dataLength > 0)
		{
			TextView textView = (TextView) parentView.findViewById(R.id.btn_dialog_foot_four);
			textView.setText(dataList.get(0));
			textView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if (null != onSelectedListener)
					{
						onSelectedListener.onOptionSelected(dialog, 0, dataList.get(0));
					}
				}
			});
		}
		else
		{
			parentView.findViewById(R.id.btn_dialog_foot_four).setVisibility(View.GONE);
		}

		parentView.findViewById(R.id.btn_dialog_foot_cancel).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (null != onSelectedListener)
				{
					onSelectedListener.onCancelSelected(dialog);
				}
			}
		});
	}

	public void setOnSelectedListener(OnSelectedListener onSelectedListener)
	{
		this.onSelectedListener = onSelectedListener;
	}

	public void show()
	{
		dialog.show();
	}

	public void show(OnSelectedListener onSelectedListener)
	{
		this.onSelectedListener = onSelectedListener;
		dialog.show();
	}

	public void show(OnSelectedListener onSelectedListener, DialogInterface.OnDismissListener dismissListener)
	{
		this.onSelectedListener = onSelectedListener;
		if (null != dismissListener)
		{
			dialog.setOnDismissListener(dismissListener);
		}
		dialog.show();
	}

	public void show(OnSelectedListener onSelectedListener, DialogInterface.OnDismissListener dismissListener, DialogInterface.OnShowListener showListener)
	{
		this.onSelectedListener = onSelectedListener;
		if (null != dismissListener)
		{
			dialog.setOnDismissListener(dismissListener);
		}
		if (null != showListener)
		{
			dialog.setOnShowListener(showListener);
		}
		dialog.show();
	}

	public interface OnSelectedListener
	{
		void onCancelSelected(DialogInterface dialog);

		/**
		 * This method will be invoked when the dialog is dismissed.
		 *
		 * @param dialog The dialog that was dismissed will be passed into the
		 *               method.
		 */
		void onOptionSelected(DialogInterface dialog, int position, String content);
	}

	/* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 重写 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% */
	protected int getResourceId()
	{
		return R.layout.lib_view_dialog_foot;
	}

	protected int getDialogStyle()
	{
		return R.style.Widget_LibView_Dialog_Default;
	}
}
