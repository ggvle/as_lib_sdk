package yline.application;

import java.util.ArrayList;
import java.util.List;

import yline.application.timer.TimerManager;
import yline.application.timer.TimerValueHolder;

public final class AppRunnable implements Runnable
{
    // 默认设置,间隔一段时间,打印AppRunnable日志
    private float tempDurateTime = 0; // MS,每次计时时间间隔
    
    private long tempStartTime = 0; // Ms,每次计时,时间起始点
    
    private static final int SLEEP_TIME = 30; // Ms,休眠时间
    
    // 设置,计时对外暴露是否开启
    private final boolean isRunningTimer = BaseApplication.getBaseConfig().isTimerServiceOpen();
    
    private List<String> mTagRemoveList; // 移除tag的队列
    
    private TimerManager mTimerManager; // 计时器管理器
    
    public AppRunnable()
    {
        tempStartTime = System.currentTimeMillis();
        
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
            tempDurateTime = (System.currentTimeMillis() - tempStartTime);
            tempStartTime = System.currentTimeMillis();
            
            if (isRunningTimer)
            {
                timer(tempDurateTime);
            }
            
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
    
    private void timer(float durateTime)
    {
        // 对现有判断            
        for (String tag : mTimerManager.getKeySet())
        {
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
                    BaseApplication.getHandler().obtainMessage(AppConstant.HANDLER_TIME_SERVICE, tag).sendToTarget();
                    
                    android.util.Log.v(AppConstant.TAG_TIMER_MANAGER, "timer tag = " + tag + ",rest time of " + tag
                        + " = " + timerValueHolder.getNumber() + ",number of tags = "
                        + mTimerManager.getKeySet().size());
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
