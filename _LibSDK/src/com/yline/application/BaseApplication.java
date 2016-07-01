package com.yline.application;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.yline.log.CrashHandler;
import com.yline.log.LogFileUtil;

/**
 * 后期所有的方法调用,可以采取xUtils一样,集成到x里面
 * 1, Log to File
 * 2, Log location
 * 3, 异常错误抛出记录
 * 4, Activity管理
 * 5, Application标配Handler、Application
 *
 * @author YLine 2016-5-25 -> 上午7:32:23
 * @version 
 */
public abstract class BaseApplication extends Application
{
    private static AppConfig mBaseConfig = new AppConfig(); // 先选用默认配置
    
    private static List<Activity> mActivityList = new ArrayList<Activity>(); // Activity管理
    
    private static Application mApplication;
    
    // handler相关
    private static Handler handler;
    
    /**
     * @return  当前application,因为onCreate为应用入口,因此不用担心为null
     */
    public static Application getApplication()
    {
        return mApplication;
    }
    
    private void setApplication(Application application)
    {
        BaseApplication.mApplication = application;
    }
    
    public static void addAcitivity(Activity activity)
    {
        mActivityList.add(activity);
    }
    
    public static void removeActivity(Activity activity)
    {
        mActivityList.remove(activity);
    }
    
    public static void finishActivity()
    {
        for (Activity activity : mActivityList)
        {
            activity.finish();
        }
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
    
    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate()
    {
        super.onCreate();
        setApplication(this); // 初始化全局变量
        
        // 配置,定制基础信息
        setBaseConfig(initConfig());
        
        // 设立一个程序入口的log
        LogFileUtil.v(AppConstant.TAG_BASE, mBaseConfig.toString());
        for (int i = 0; i < 3; i++)
        {
            LogFileUtil.v(AppConstant.TAG_BASE, "应用启动 *** application start id = " + Thread.currentThread().getId());
        }
        
        // 异常崩溃日志
        CrashHandler.getInstance().init(this);
        
        AppService.initAppService(this); // 伴生服务
        
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case AppConstant.HANDLER_TOAST:
                        Toast.makeText(BaseApplication.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        handlerDefault(msg);
                        break;
                }
            }
        };
    }
    
    /**
     * 吐司
     * @param content
     */
    public static void toast(String content)
    {
        handler.obtainMessage(AppConstant.HANDLER_TOAST, content).sendToTarget();
    }
    
    /**
     * 被上一级重写,使用Application中的handler封装方法(在该SDK之外的标签)
     */
    protected abstract void handlerDefault(Message msg);
    
    /**
     * 进行一些基础配置,要求上级必须配置的信息
     * @return
     */
    protected abstract AppConfig initConfig();
    
}
