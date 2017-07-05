package com.yline.view.dialog.demo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.yline.application.SDKManager;
import com.yline.log.LogFileUtil;
import com.yline.test.BaseTestActivity;
import com.yline.view.dialog.InnerConstant;
import com.yline.view.dialog.WidgetDialogCenter;
import com.yline.view.dialog.WidgetDialogFoot;
import com.yline.view.dialog.pop.DeleteMenuActivity;
import com.yline.view.dialog.pop.DropMenuActivity;

public class MainActivity extends BaseTestActivity
{
	@Override
	public void testStart(View view, Bundle savedInstanceState)
	{
		addButton("WidgetDialogFoot", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				WidgetDialogFoot widgetDialogFoot = new WidgetDialogFoot(MainActivity.this, InnerConstant.getMvList(4));
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
				WidgetDialogCenter widgetDialogIos = new WidgetDialogCenter(MainActivity.this);
				widgetDialogIos.show();
			}
		});

		addButton("下拉菜单", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				DropMenuActivity.actionStart(MainActivity.this);
			}
		});

		addButton("长按删除", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				DeleteMenuActivity.actionStart(MainActivity.this);
			}
		});
	}
}
