package com.yline.widget.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.yline.application.SDKManager;
import com.yline.inner.InnerConstant;
import com.yline.log.LogFileUtil;
import com.yline.test.BaseTestFragment;

public class WidgetDialogFragment extends BaseTestFragment
{
	public static WidgetDialogFragment newInstance()
	{
		WidgetDialogFragment fragment = new WidgetDialogFragment();
		return fragment;
	}

	@Override
	public void testStart(View view, Bundle savedInstanceState)
	{
		addButton("WidgetDialogFoot", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				WidgetDialogFoot widgetDialogFoot = new WidgetDialogFoot(getContext(), InnerConstant.getMvList(4));
				widgetDialogFoot.show(new WidgetDialogFoot.OnSelectedListener()
				{
					@Override
					public void onCancelSelected(DialogInterface dialog)
					{
						SDKManager.toast("Cancel");
						dialog.dismiss();
					}

					@Override
					public void onOptionSelected(DialogInterface dialog, int position, String content)
					{
						SDKManager.toast("Select  = " + content);
						dialog.dismiss();
					}
				}, new DialogInterface.OnDismissListener()
				{
					@Override
					public void onDismiss(DialogInterface dialog)
					{
						LogFileUtil.v("Dismiss");
					}
				});
			}
		});

		addButton("WidgetDialogCenter", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				WidgetDialogCenter widgetDialogIos = new WidgetDialogCenter(getContext());
				widgetDialogIos.show();
			}
		});
	}
}
