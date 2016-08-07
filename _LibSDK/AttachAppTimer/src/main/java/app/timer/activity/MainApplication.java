package app.timer.activity;

import yline.application.AppConfig;
import yline.application.BaseApplication;

public class MainApplication extends BaseApplication
{

	@Override
	protected AppConfig initConfig()
	{
		AppConfig appConfig = new AppConfig();
		appConfig.setTimerServiceOpen(true);
		return appConfig;
	}
}
