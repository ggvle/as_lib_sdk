package com.yline.application;

import java.util.ArrayList;
import java.util.List;

import com.yline.application.netstate.NetStateManager;
import com.yline.application.netstate.NetStateManager.NETWORK_STATE;
import com.yline.application.timer.TimerManager;
import com.yline.application.timer.TimerValueHolder;
import com.yline.log.LogFileUtil;

public final class AppRunnable implements Runnable
{
    // 默认设置,间隔一段时间,打印AppRunnable日志
    
    private float durateTime = 0; // MS,每次计时时间间隔
    
    private long startTime = 0; // Ms,每次计时,时间起始点
    
    private long lastStartTime = 0; // Ms,每次日志,记录上一次计时起始时间点
    
    private static final int LOG_TIME = 1000; // Ms,日志间隔频率
    
    private int length = LOG_TIME; // 每间隔 1000 * 30 Ms 下打印一下日志
    
    private static final int SLEEP_TIME = 30; // Ms,休眠时间
    
    // 设置,计时对外暴露是否开启
    private final boolean isRunningTimer = BaseApplication.getBaseConfig().isTimerServiceOpen();
    
    private List<String> mTagRemoveList; // 移除tag的队列
    
    private TimerManager mTimerManager; // 计时器管理器
    
    private final boolean isRunningNetStateListener = BaseApplication.getBaseConfig().isNetStateListenerOpen();
    
    public AppRunnable()
    {
        startTime = System.currentTimeMillis();
        lastStartTime = startTime;
        
        if (isRunningTimer)
        {
            mTagRemoveList = new ArrayList<String>();
            mTimerManager = TimerManager.getInstance();
        }
    }
    
    @Override
    public void run()
    {
        while (true)
        {
            appRunningLog();
            
            attachFunction(durateTime);
            
            try
            {
                Thread.sleep(SLEEP_TIME); // 时间最小单位,间隔越大,消耗资源越少
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 主Running日志方法、计算获取durateTime<因此,该方法必须被执行>
     */
    private void appRunningLog()
    {
        durateTime = (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        
        // 间隔 1000 频率 下打印一下日志
        if (length / LOG_TIME >= 1)
        {
            length = 0;
            LogFileUtil.v(AppService.TAG_APP_SERVICE, "AppRunnable this time = " + startTime
                + ",real experienced time = " + (startTime - lastStartTime));
            
            attachFunctionLog(startTime, lastStartTime);
            
            lastStartTime = startTime;
        }
        else
        {
            length++;
        }
    }
    
    /**
     * 打印其他的日志
     * @param thisTime  此时打印日志的时间
     * @param lastTime  上一次打印日志的时间
     */
    private void attachFunctionLog(float thisTime, float lastTime)
    {
        LogFileUtil.v(AppService.TAG_APP_SERVICE, "AppRunnable sleep time = " + SLEEP_TIME * LOG_TIME
            + ",diff time between sleep&expericed  = " + (startTime - lastStartTime - SLEEP_TIME * LOG_TIME)
            + "/ unit ms");
        
        // 网络监听状态是否打开
        LogFileUtil.v(AppService.TAG_APP_SERVICE, "AppRunnable NetStateListener is state = "
            + isRunningNetStateListener);
    }
    
    /**
     * 该服务附带的功能
     * @param experienceTime   每一次调用附带功能的间隔时间
     */
    private void attachFunction(float experienceTime)
    {
        if (isRunningTimer)
        {
            timer();
        }
        
        // 用户监听网络状态,且当前状态不为默认状态
        if (isRunningNetStateListener && !NetStateManager.getInstance().isNetStateNone())
        {
            NetStateManager.getInstance().notifyAllNetStateListener();
            // 通知一遍之后,还原
            NetStateManager.getInstance().dispatchMessage(NETWORK_STATE.NONE);
        }
    }
    
    private void timer()
    {
        // 对现有判断            
        for (String tag : mTimerManager.getKeySet())
        {
            // 这一个可以简化
            TimerValueHolder timerValueHolder = mTimerManager.getTimeValueHolder(tag);
            
            if (timerValueHolder.isEnd())
            {
                // 添加tag到移除队列
                mTagRemoveList.add(tag);
            }
            else
            {
                // 通知
                if (timerValueHolder.updateCaculate(durateTime))
                {
                    mTimerManager.setOnTimerListener(timerValueHolder.getTimerListener());
                    BaseApplication.getHandler()
                        .obtainMessage(BaseApplication.HANDLER_TIME_SERVICE, tag)
                        .sendToTarget();
                    
                    LogFileUtil.v(AppService.TAG_APP_SERVICE, "AppRunnable notify tag = " + tag
                        + ",this tag rest number = " + timerValueHolder.getNumber()
                        + ",all timerManager register tag number = " + mTimerManager.getKeySet().size());
                }
            }
            
            mTimerManager.putTimeValueHolder(tag, timerValueHolder);
        }
        
        // 将移除队列清空
        if (null != mTagRemoveList && mTagRemoveList.size() > 0)
        {
            for (String tag : mTagRemoveList)
            {
                mTimerManager.remove(tag);
            }
            mTagRemoveList.clear();
        }
    }
    
}
