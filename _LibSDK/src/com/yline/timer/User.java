package com.yline.timer;

import com.yline.application.BaseApplication;
import com.yline.log.LogFileUtil;
import com.yline.timer.TimerManager.ITimerListener;

/**
 * 稍后再写,这个还没结束
 * simple introduction
 *
 * <p>detailed comment
 * @author lWX353514 2016-5-3
 * @see
 * @since 1.0
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
            LogFileUtil.v(TimerService.TAG_TIMER_SERVICE, "timer server is closed");
        }
    }
    
    @Override
    public void onResult(String tag)
    {
        LogFileUtil.v(TimerService.TAG_TIMER_SERVICE, "onResult User , tag = " + tag);
        if ("user1_1".equals(tag))
        {
            LogFileUtil.v(TimerService.TAG_TIMER_SERVICE, "User , tag = " + tag);
        }
        if ("user1_2".equals(tag))
        {
            LogFileUtil.v(TimerService.TAG_TIMER_SERVICE, "User , tag = " + tag);
        }
    }
    
}
