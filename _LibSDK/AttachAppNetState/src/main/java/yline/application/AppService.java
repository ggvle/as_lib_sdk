package yline.application;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * 伴生Application的服务---计时服务
 */
public final class AppService extends Service
{
    private Thread mThread;
    
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        android.util.Log.v(AppConstant.TAG_NETWORK_CHANGE, "AppService running in onCreate");
        mThread = new Thread(new AppRunnable());
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        android.util.Log.v(AppConstant.TAG_NETWORK_CHANGE, "AppService running in onStartCommand");
        if (null == intent)
        {
            intent = new Intent();
        }
        
        if (null == mThread)
        {
            android.util.Log.v(AppConstant.TAG_NETWORK_CHANGE, "AppService new thread in onStartCommand");
            mThread = new Thread(new AppRunnable());
        }
        
        if (!mThread.isAlive())
        {
            android.util.Log.v(AppConstant.TAG_NETWORK_CHANGE, "AppService thread start in onStartCommand");
            mThread.start();
        }
        
        return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (null != mThread)
        {
            mThread = null;
        }
    }
    
    /**
     * 开启此Service
     * @param context
     */
    public static void initAppService(Context context)
    {
        context.startService(new Intent(context, AppService.class));
    }
}
