package com.yline.application;

import com.yline.log.LogFileUtil;

/**
 * 默认设置,间隔一段时间,打印AppRunnable日志
 */
public final class SDKRunnable implements Runnable
{
    private static final int LOG_TIME = 1000; // Ms,日志间隔频率
    
    private static final int SLEEP_TIME = 30; // Ms,休眠时间
    
    private float tempDurateTime = 0; // MS,每次计时时间间隔
    
    private long tempStartTime = 0; // Ms,每次计时,时间起始点
    
    private long tempLastStartTime = 0; // Ms,每次日志,记录上一次计时起始时间点
    
    private int tempLength = LOG_TIME; // 每间隔 1000 * 30 Ms 下打印一下日志
    
    public SDKRunnable()
    {
        tempStartTime = System.currentTimeMillis();
        tempLastStartTime = tempStartTime;
    }
    
    @Override
    public void run()
    {
        while (true)
        {
            tempDurateTime = (System.currentTimeMillis() - tempStartTime);
            tempStartTime = System.currentTimeMillis();
            
            appRunning();
            
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
     * 主Running日志方法
     * 间隔 30s 打印一次日志
     */
    private void appRunning()
    {
        if (tempLength >= LOG_TIME)
        {
            tempLength = 0;
            LogFileUtil.m("this time = " + tempStartTime + ",experienced time = " + (tempStartTime - tempLastStartTime)
                + ",sleep time = " + SLEEP_TIME * LOG_TIME);
            
            tempLastStartTime = tempStartTime;
        }
        else
        {
            tempLength++;
        }
    }
}
