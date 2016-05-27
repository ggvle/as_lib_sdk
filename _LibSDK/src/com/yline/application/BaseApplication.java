package com.yline.application;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.yline.log.LogFileUtil;
import com.yline.timer.TimerManager;
import com.yline.timer.TimerManager.ITimerListener;

/**
 * simple introduction
 *
 * @author YLine 2016-5-25 -> 上午7:32:23
 * @version 
 */
public abstract class BaseApplication extends Application
{
    public static final String FILE_PARENT_PATH = "_yline_lib" + File.separator; // 文件保存父路径
    
    public static final String TAG_BASE_APPLICATION = "base_Application"; // sdk jar 包主线程tag
    
    private static BaseConfig mBaseConfig = new BaseConfig(); // 先选用默认配置
    
    private static Application mApplication;
    
    // handler相关
    private static Handler handler;
    
    /** handler 吐丝 */
    private static final int HANDLER_TOAST = 1;
    
    /** handler 计时服务 */
    private static final int HANDLER_TIME_SERVICE = 2;
    
    /**
     * @return  当前application,因为onCreate为应用入口,因此不用担心为null
     */
    public static Application getApplication()
    {
        return mApplication;
    }
    
    private static void setApplication(Application application)
    {
        BaseApplication.mApplication = application;
    }
    
    /**
     * @return 当前Application的配置信息
     */
    public static BaseConfig getBaseConfig()
    {
        return mBaseConfig;
    }
    
    /**
     * 配置当前Application的配置信息
     * 返回null,则按默认配置
     * @param mBaseConfig 配置对象
     */
    public static void setBaseConfig(BaseConfig mBaseConfig)
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
    
    @SuppressLint("HandlerLeak") @Override
    public void onCreate()
    {
        super.onCreate();
        setApplication(this); // 初始化全局变量
        
        // 配置,定制基础信息
        setBaseConfig(initConfig());
        
        // 设立一个程序入口的log
        for (int i = 0; i < 10; i++)
        {
            LogFileUtil.v(TAG_BASE_APPLICATION, "应用启动 *** application start id = " + Thread.currentThread().getId());
        }
        
        // 计时服务
        if (getBaseConfig().isTimerServiceOpen())
        {
            TimerManager.getInstance().initTimer(this);
        }
        
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case HANDLER_TOAST:
                        if (null != mApplication)
                        {
                            Toast.makeText(mApplication, (String)msg.obj, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            LogFileUtil.e(TAG_BASE_APPLICATION, "handler toast mContext is null");
                        }
                        break;
                    case HANDLER_TIME_SERVICE:
                        TimerManager.getInstance().setTimerListener((String)msg.obj);
                        break;
                    default:
                        handlerDefault(msg);
                        break;
                }
            }
        };
    }
    
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
    
    /**
     * 吐司
     * @param content
     */
    public static void toast(String content)
    {
        handler.obtainMessage(HANDLER_TOAST, content).sendToTarget();
    }
    
    /**
     * 定时,发送计时服务
     * @param tag
     */
    public static void timerServiceNotify(String tag)
    {
        handler.obtainMessage(HANDLER_TIME_SERVICE, tag).sendToTarget();
    }
    
    /**
     * 被上一级重写,使用Application中的handler封装方法(在该SDK之外的标签)
     */
    protected abstract void handlerDefault(Message msg);
    
    /**
     * 进行一些基础配置,要求上级必须配置的信息
     * @return
     */
    protected abstract BaseConfig initConfig();
    
}
