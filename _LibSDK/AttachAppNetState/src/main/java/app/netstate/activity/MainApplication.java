package app.netstate.activity;

import yline.application.AppConfig;
import yline.application.BaseApplication;

public class MainApplication extends BaseApplication
{
    
    @Override
    protected AppConfig initConfig()
    {
        AppConfig appConfig = new AppConfig();
        appConfig.setNetStateListenerOpen(true);
        return appConfig;
    }
    
}
