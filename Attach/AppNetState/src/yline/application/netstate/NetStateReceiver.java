package yline.application.netstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import yline.application.AppConstant;
import yline.application.netstate.NetStateManager.NETWORK_STATE;

public class NetStateReceiver extends BroadcastReceiver
{
    
    @Override
    public void onReceive(Context context, Intent intent)
    {
        android.util.Log.v(AppConstant.TAG_NETWORK_CHANGE, "onReceive running");
        
        // WIfi 是否处于 可用状态
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction()))
        {
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != networkInfo)
            {
                android.util.Log.v(AppConstant.TAG_NETWORK_CHANGE, "wifi state = " + networkInfo.getState());
                if (State.CONNECTED.equals(networkInfo.getState()))
                {
                    // to do notify NetStateManager
                    android.util.Log.i(AppConstant.TAG_NETWORK_CHANGE, "wifi state = CONNECTED \n\n");
                    NetStateManager.getInstance().dispatchMessage(NETWORK_STATE.WIFI_CONNECTED);
                }
            }
            else
            {
                android.util.Log.v(AppConstant.TAG_NETWORK_CHANGE, "networkInfo is null");
            }
        }
    }
}
