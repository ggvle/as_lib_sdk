package app.netstate.activity;

import yline.application.AppConstant;
import yline.application.BaseApplication;
import yline.application.netstate.NetStateManager.INetStateListener;
import yline.application.netstate.NetStateManager.NETWORK_STATE;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.yline.app.netstate.R;

/**
 * 手动改变网络状态就可以看到测试结果
 */
public class MainActivity extends Activity implements INetStateListener
{
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.btn_netstate).setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                register();
            }
        });
    }
    
    public void register()
    {
        if (BaseApplication.getBaseConfig().isNetStateListenerOpen())
        {
            if (!BaseApplication.registerNetStateListener("User", this))
            {
                android.util.Log.v(AppConstant.TAG_NETWORK_CHANGE, "User netState register Listener failed");
            }
            
            if (!BaseApplication.registerNetStateListener("User", this))
            {
                android.util.Log.v(AppConstant.TAG_NETWORK_CHANGE, "User netState register Listener failed");
            }
        }
        else
        {
            android.util.Log.v(AppConstant.TAG_NETWORK_CHANGE, "netState Listener function is closed");
        }
    }
    
    @Override
    public void onMessageReceiver(String tag, NETWORK_STATE state)
    {
        android.util.Log.v(AppConstant.TAG_NETWORK_CHANGE, "User listener success tag = " + tag + " && state = "
            + state);
    }
}
