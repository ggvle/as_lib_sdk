package com.yline.application.netstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;

import com.yline.application.netstate.NetStateManager.NETWORK_STATE;
import com.yline.log.LogFileUtil;

public class NetStateReceiver extends BroadcastReceiver
{
    // 所有的网络状态改变都用这个tag
    public static final String TAG_NETWORK_RECEIVER = "NetStateReceiver";
    
    @Override
    public void onReceive(Context context, Intent intent)
    {
        LogFileUtil.v(TAG_NETWORK_RECEIVER, "onReceive running");
        
        // WIfi 是否处于 可用状态
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction()))
        {
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != networkInfo)
            {
                LogFileUtil.v(TAG_NETWORK_RECEIVER, "wifi state = " + networkInfo.getState());
                if (State.CONNECTED.equals(networkInfo.getState()))
                {
                    // to do notify NetStateManager
                    LogFileUtil.i(TAG_NETWORK_RECEIVER, "wifi state = CONNECTED \n\n");
                    NetStateManager.getInstance().dispatchMessage(NETWORK_STATE.WIFI_CONNECTED);
                }
            }
            else
            {
                LogFileUtil.v(TAG_NETWORK_RECEIVER, "networkInfo is null");
            }
        }
    }
}
