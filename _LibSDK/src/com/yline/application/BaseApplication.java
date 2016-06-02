package com.yline.application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.yline.application.netstate.NetStateManager;
import com.yline.application.netstate.NetStateManager.INetStateListener;
import com.yline.application.timer.TimerManager;
import com.yline.application.timer.TimerManager.ITimerListener;
import com.yline.log.CrashHandler;
import com.yline.log.LogFileUtil;

/**
 * 后期所有的方法调用,可以采取xUtils一样,集成到x里面
 * simple introduction
 * 已集成功能:(功能不完善,只是走通)
 * 1, Log to File
 * 2, Log location
 * 3, 计时器
 * 4, 网络状态监听器
 * 5, 异常错误抛出记录
 * 
 * ING 集成功能
 * 1, 锁屏广播
 * 2, 单元测试,集成测试
 *
 * @author YLine 2016-5-25 -> 上午7:32:23
 * @version 
 */
public abstract class BaseApplication extends Application
{
    public static final String FILE_PARENT_PATH = "_yline_lib" + File.separator; // 文件保存父路径
    
    public static final String TAG_BASE_APPLICATION = "BaseApplication"; // sdk jar 包主线程tag
    
    private static AppConfig mBaseConfig = new AppConfig(); // 先选用默认配置
    
    private static List<Activity> mActivityList = new ArrayList<Activity>(); // Activity管理
    
    private static Application mApplication;
    
    // handler相关
    private static Handler handler;
    
    /** handler 吐丝 */
    private static final int HANDLER_TOAST = 1;
    
    /** handler 计时服务 */
    public static final int HANDLER_TIME_SERVICE = 2;
    
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
        LogFileUtil.v(TAG_BASE_APPLICATION, mBaseConfig.toString());
        for (int i = 0; i < 10; i++)
        {
            LogFileUtil.v(TAG_BASE_APPLICATION, "应用启动 *** application start id = " + Thread.currentThread().getId());
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
     * 注册网络监听器 <目前只支持WIfi>
     * 相同的tag,会注册失败
     * @param netStateListener
     * @return true(success)
     */
    public static boolean registerNetStateListener(String tag, INetStateListener netStateListener)
    {
        return NetStateManager.getInstance().registerListener(tag, netStateListener);
    }
    
    /**
     * 移除网络监听器 <目前只支持WIfi>
     * @param netStateListener
     * @return true(success)
     */
    public static boolean removeNetStateListener(String tag, INetStateListener netStateListener)
    {
        return NetStateManager.getInstance().removeListener(tag, netStateListener);
    }
    
    /**
     * 清除所有的网络监听器 <目前只支持WIfi>
     */
    public static void removeAllNetStateListener()
    {
        NetStateManager.getInstance().clearListener();
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
     * 被上一级重写,使用Application中的handler封装方法(在该SDK之外的标签)
     */
    protected abstract void handlerDefault(Message msg);
    
    /**
     * 进行一些基础配置,要求上级必须配置的信息
     * @return
     */
    protected abstract AppConfig initConfig();
    
}
