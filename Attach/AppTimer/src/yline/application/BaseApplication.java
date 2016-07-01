package yline.application;

import yline.application.timer.TimerManager;
import yline.application.timer.TimerManager.ITimerListener;
import android.app.Application;
import android.os.Handler;
import android.os.Message;

/**
 * 计时器功能
 */
public abstract class BaseApplication extends Application
{
    private static AppConfig mBaseConfig = new AppConfig(); // 先选用默认配置
    
    // handler相关
    private static Handler handler;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        
        // 配置,定制基础信息
        setBaseConfig(initConfig());
        
        // 伴生服务 开启
        AppService.initAppService(this);
        
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case AppConstant.HANDLER_TIME_SERVICE:
                        TimerManager.getInstance().setTimerListener((String)msg.obj);
                        break;
                }
            }
        };
    }
    
    /**
     * @return 当前Application的配置信息
     */
    public static AppConfig getBaseConfig()
    {
        return mBaseConfig;
    }
    
    /**
     * 配置当前Application的配置信息
     * 返回null,则按默认配置
     * @param mBaseConfig 配置对象
     */
    private void setBaseConfig(AppConfig mBaseConfig)
    {
        if (null != mBaseConfig)
        {
            BaseApplication.mBaseConfig = mBaseConfig;
        }
    }
    
    /**
     * 更改资源的时候,才需要做一步操作,引用不需要
     * 原子操作
     * @return
     */
    public static Handler getHandler()
    {
        return handler;
    }
    
    /**
     * 进行一些基础配置,要求上级必须配置的信息
     * @return
     */
    protected abstract AppConfig initConfig();
    
    /**
     * 注册或更新设置,并重新开始计时
     * @param tag   标签
     * @param time  每次通知的时间
     * @param number    次数,(小于0,永远)(大于等于0,次数)
     * @param listener
     */
    public static void registerTimerListener(String tag, float time, int number, ITimerListener timerListener)
    {
        TimerManager.getInstance().register(tag, time, number, timerListener);
    }
}
