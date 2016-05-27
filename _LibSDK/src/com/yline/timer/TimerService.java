package com.yline.timer;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.yline.application.BaseApplication;
import com.yline.log.LogFileUtil;

public class TimerService extends Service
{
    public static final String TAG_TIMER_SERVICE = "timer_service";
    
    private Thread mThread;
    
    private TimerManager mTimerManager = TimerManager.getInstance();
    
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        LogFileUtil.v(TAG_TIMER_SERVICE, "TimerService running in onCreate");
        mThread = new Thread(new TimerRunnable());
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        LogFileUtil.v(TAG_TIMER_SERVICE, "TimerService running in onStartCommand");
        if (null == intent)
        {
            intent = new Intent();
        }
        
        if (null == mThread)
        {
            LogFileUtil.v(TAG_TIMER_SERVICE, "TimerService new thread in onStartCommand");
            mThread = new Thread(new TimerRunnable());
        }
        
        if (!mThread.isAlive())
        {
            LogFileUtil.v(TAG_TIMER_SERVICE, "TimerService thread start in onStartCommand");
            mThread.start();
            
        }
        
        return super.onStartCommand(intent, flags, startId);
    }
    
    /**
     * 计时线程
     * simple introduction
     *
     * <p>detailed comment
     * @author lWX353514 2016-5-26
     * @see
     * @since 1.0
     */
    private final class TimerRunnable implements Runnable
    {
        
        private float durateTime = 0; // Ms,每次计时时间间隔
        
        private long startTime = 0; // Ms,每次计时,时间起始点
        
        private long lastStartTime = 0; // Ms,每次日志,记录上一次计时起始时间点
        
        private List<String> mTagRemoveList; // 移除tag的队列
        
        private int length = LOG_TIME; // 每间隔 1000 * 30 Ms 下打印一下日志
        
        private static final int SLEEP_TIME = 30; // Ms,休眠时间
        
        private static final int LOG_TIME = 1000; // Ms,日志间隔频率
        
        public TimerRunnable()
        {
            mTagRemoveList = new ArrayList<String>();
            startTime = System.currentTimeMillis();
            lastStartTime = startTime;
        }
        
        public void run()
        {
            while (true)
            {
                durateTime = (System.currentTimeMillis() - startTime);
                startTime = System.currentTimeMillis();
                
                // 间隔 1000 * 10s 下打印一下日志
                if (length / LOG_TIME >= 1)
                {
                    length = 0;
                    LogFileUtil.v(TAG_TIMER_SERVICE, "TimerRunnable this time = " + startTime + ",key size = "
                        + mTimerManager.getKeySet().size());
                    
                    LogFileUtil.v(TAG_TIMER_SERVICE, "TimerRunnable should expend time = " + SLEEP_TIME * LOG_TIME
                        + ",real expend time = " + (startTime - lastStartTime) + ",diff time = "
                        + (startTime - lastStartTime - SLEEP_TIME * LOG_TIME) + "/ unit = ms");
                    
                    lastStartTime = startTime;
                }
                else
                {
                    length++;
                }
                
                // 对现有判断            
                for (String tag : mTimerManager.getKeySet())
                {
                    // 这一个可以简化
                    TimeValueHolder timeValueHolder = mTimerManager.getTimeValueHolder(tag);
                    
                    if (timeValueHolder.isEnd())
                    {
                        // 添加tag到移除队列
                        mTagRemoveList.add(tag);
                    }
                    else
                    {
                        // 通知
                        if (timeValueHolder.updateCaculate(durateTime))
                        {
                            mTimerManager.setOnTimerListener(timeValueHolder.getTimerListener());
                            BaseApplication.timerServiceNotify(tag);
                            LogFileUtil.v(TAG_TIMER_SERVICE, "TimerRunnable notify tag = " + tag + ",rest number = "
                                + timeValueHolder.getNumber());
                        }
                    }
                    
                    mTimerManager.putTimeValueHolder(tag, timeValueHolder);
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
    };
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (null != mThread)
        {
            mThread = null;
            mTimerManager = null;
        }
    }
}
