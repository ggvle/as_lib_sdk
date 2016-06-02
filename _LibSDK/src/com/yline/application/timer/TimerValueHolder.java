package com.yline.application.timer;

import com.yline.application.timer.TimerManager.ITimerListener;

public class TimerValueHolder
{
    // 每次放入时,确定重复的次数,默认永远
    private int mNumber = -1;
    
    // 每次间隔时间
    private float mTime;
    
    // 时间,计算,中间量
    private float mCaculateTime;
    
    // 监听器
    private ITimerListener mTimeListener;
    
    /**
     * 新建,设置默认
     * @param durateTime    间隔时间
     */
    public TimerValueHolder setHolder(float time)
    {
        this.mTime = time;
        this.mCaculateTime = time;
        return this;
    }
    
    /**
     * 新建,设置默认
     * @param durateTime    间隔时间
     * @param number    计算的次数(小于0,永远)(大于等于0,次数)
     */
    public TimerValueHolder setHolder(float time, int number)
    {
        this.mTime = time;
        this.mCaculateTime = time;
        this.mNumber = number;
        return this;
    }
    
    /**
     * 
     * @param listener  监听器
     * @return
     */
    public TimerValueHolder setListener(ITimerListener listener)
    {
        this.mTimeListener = listener;
        return this;
    }
    
    /**
     * 判断是否终结
     * @return
     */
    public boolean isEnd()
    {
        return 0 == this.mNumber;
    }
    
    /**
     * 更新数据
     * @param durateTime    已经度过多长时间
     * @return  true if time is overflow
     */
    public boolean updateCaculate(float durateTime)
    {
        this.mCaculateTime -= durateTime;
        
        if (this.mCaculateTime < 0)
        {
            this.mCaculateTime = this.mTime;
            this.mNumber = this.mNumber > 0 ? --mNumber : mNumber;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * @return  null if listener is not set
     */
    public ITimerListener getTimerListener()
    {
        if (null != this.mTimeListener)
        {
            return this.mTimeListener;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * @return  剩余次数
     */
    public int getNumber()
    {
        return this.mNumber;
    }
}
