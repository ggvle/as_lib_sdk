package app.timer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.attach.app.timer.R;

import yline.application.AppConstant;
import yline.application.BaseApplication;
import yline.application.timer.TimerManager.ITimerListener;

public class MainActivity extends Activity implements ITimerListener
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.btn_timer_register).setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				register();
			}
		});
	}
	
	public void register()
	{
		if (BaseApplication.getBaseConfig().isTimerServiceOpen())
		{
			BaseApplication.registerTimerListener("user1_1", 1000, 40, this);
			BaseApplication.registerTimerListener("user1_2", 3000, 20, this);
		}
		else
		{
			android.util.Log.v(AppConstant.TAG_TIMER_MANAGER, "timer function is closed");
		}
	}

	@Override
	public void onResult(String tag)
	{
		android.util.Log.v(AppConstant.TAG_TIMER_MANAGER, "onResult User , tag = " + tag);
		if ("user1_1".equals(tag))
		{
			android.util.Log.v(AppConstant.TAG_TIMER_MANAGER, "User , tag = " + tag);
		}
		if ("user1_2".equals(tag))
		{
			android.util.Log.v(AppConstant.TAG_TIMER_MANAGER, "User , tag = " + tag);
		}
	}
}
