package com.yline.application.netstate;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class NetStateManager
{
    // 可重入锁,用于代码同步
    private Lock mLock = new ReentrantLock();
    
    private HashMap<String, INetStateListener> mNetStateListenerMap;
    
    private NETWORK_STATE mNetState; // 当前网络状态
    
    public static NetStateManager getInstance()
    {
        return NetListenerManagerHolder.instance;
    }
    
    private static class NetListenerManagerHolder
    {
        private static NetStateManager instance = new NetStateManager();
    }
    
    private NetStateManager()
    {
        mNetStateListenerMap = new HashMap<String, NetStateManager.INetStateListener>();
        mNetState = NETWORK_STATE.NONE;
    }
    
    /**
     * 注册监听者;不能重复添加
     * @param netStateListener
     */
    public boolean registerListener(String tag, INetStateListener netStateListener)
    {
        if (null != netStateListener)
        {
            mLock.lock();
            try
            {
                if (!mNetStateListenerMap.containsKey(tag))
                {
                    mNetStateListenerMap.put(tag, netStateListener);
                    return true;
                }
            }
            finally
            {
                mLock.unlock();
            }
        }
        return false;
    }
    
    /**
     * 移除监听者
     * @param netStateListener
     */
    public boolean removeListener(String tag, INetStateListener netStateListener)
    {
        if (null != netStateListener)
        {
            mLock.lock();
            try
            {
                if (mNetStateListenerMap.containsKey(tag))
                {
                    mNetStateListenerMap.remove(tag);
                    return true;
                }
            }
            finally
            {
                mLock.unlock();
            }
        }
        return false;
    }
    
    /**
     * 清空监听者
     */
    public void clearListener()
    {
        mLock.lock();
        try
        {
            mNetStateListenerMap.clear();
        }
        finally
        {
            mLock.unlock();
        }
    }
    
    /**
     * 通知监听者
     * @param msg
     */
    public void dispatchMessage(final NETWORK_STATE state)
    {
        this.mNetState = state;
    }
    
    /**
     * @return true(没有通知状态)
     */
    public boolean isNetStateNone()
    {
        return NETWORK_STATE.NONE.equals(this.mNetState);
    }
    
    /**
     * 被AppRunnable调用
     * 通知所有监听器
     */
    public void notifyAllNetStateListener()
    {
        mLock.lock();
        try
        {
            for (String tag : mNetStateListenerMap.keySet())
            {
                mNetStateListenerMap.get(tag).onMessageReceiver(tag, mNetState);
            }
        }
        finally
        {
            mLock.unlock();
        }
    }
    
    public interface INetStateListener
    {
        /**
         * 在子线程中执行,不能做UI操作
         * @param tag   添加时的tag
         * @param state
         */
        void onMessageReceiver(String tag, NETWORK_STATE state);
    }
    
    public enum NETWORK_STATE
    {
        /** WIfi 连接可用 */
        WIFI_CONNECTED,
        /** 没有通知状态;该状态不交给用户判断 */
        NONE;
    }
}
