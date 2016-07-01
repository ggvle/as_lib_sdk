package yline.application;

import yline.application.netstate.NetStateManager;
import yline.application.netstate.NetStateManager.NETWORK_STATE;

public final class AppRunnable implements Runnable
{
    private static final int SLEEP_TIME = 30; // Ms,休眠时间
    
    private final boolean isRunningNetStateListener = BaseApplication.getBaseConfig().isNetStateListenerOpen();
    
    public AppRunnable()
    {
        
    }
    
    @Override
    public void run()
    {
        while (true)
        {
            if (isRunningNetStateListener)
            {
                netState();
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
    
    /**
     * 网络监听
     */
    private void netState()
    {
        // 用户监听网络状态,且当前状态不为默认状态
        if (isRunningNetStateListener && !NetStateManager.getInstance().isNetStateNone())
        {
            NetStateManager.getInstance().notifyAllNetStateListener();
            // 通知一遍之后,还原
            NetStateManager.getInstance().dispatchMessage(NETWORK_STATE.NONE);
        }
    }
}
