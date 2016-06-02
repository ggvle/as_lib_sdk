package com.yline.application.timer;

import com.yline.application.BaseApplication;
import com.yline.application.timer.TimerManager.ITimerListener;
import com.yline.log.LogFileUtil;

/**
 * 计时器使用,案例
 * simple introduction
 *
 * @author YLine 2016-5-29 -> 上午8:59:03
 * @version
 */
public class User implements ITimerListener
{
    public User()
    {
        if (BaseApplication.getBaseConfig().isTimerServiceOpen())
        {
            BaseApplication.registerTimerListener("user1_1", 1000, 40, this);
            BaseApplication.registerTimerListener("user1_2", 3000, 20, this);
        }
        else
        {
            LogFileUtil.v(TimerManager.TAG_TIMER, "timer function is closed");
        }
    }
    
    @Override
    public void onResult(String tag)
    {
        LogFileUtil.v(TimerManager.TAG_TIMER, "onResult User , tag = " + tag);
        if ("user1_1".equals(tag))
        {
            LogFileUtil.v(TimerManager.TAG_TIMER, "User , tag = " + tag);
        }
        if ("user1_2".equals(tag))
        {
            LogFileUtil.v(TimerManager.TAG_TIMER, "User , tag = " + tag);
        }
    }
    
}
