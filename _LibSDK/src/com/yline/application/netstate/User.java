package com.yline.application.netstate;

import com.yline.application.BaseApplication;
import com.yline.application.netstate.NetStateManager.INetStateListener;
import com.yline.application.netstate.NetStateManager.NETWORK_STATE;
import com.yline.application.timer.TimerManager;
import com.yline.log.LogFileUtil;

public class User implements INetStateListener
{
    public User()
    {
        if (BaseApplication.getBaseConfig().isNetStateListenerOpen())
        {
            if (!BaseApplication.registerNetStateListener("User", this))
            {
                LogFileUtil.v(TimerManager.TAG_TIMER, "User netState register Listener failed");
            }
            
            if (!BaseApplication.registerNetStateListener("User", this))
            {
                LogFileUtil.v(TimerManager.TAG_TIMER, "User netState register Listener failed");
            }
        }
        else
        {
            LogFileUtil.v(TimerManager.TAG_TIMER, "netState Listener function is closed");
        }
    }
    
    @Override
    public void onMessageReceiver(String tag, NETWORK_STATE state)
    {
        LogFileUtil.v(NetStateReceiver.TAG_NETWORK_RECEIVER, "User listener success tag = " + tag + " && state = "
            + state);
    }
    
}
