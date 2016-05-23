package com.yline.aidl.provider;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.yline.lib.utils.combine.LogFileUtil;

/**
 * 提供一个绑定的服务
 * simple introduction
 *
 * @author YLine 2016-5-21 -> 下午6:47:52
 * @version 
 */
public class ProviderService extends Service
{
    @Override
    public IBinder onBind(Intent intent)
    {
        LogFileUtil.v(com.yline.aidl.User.TAG, "onBind");
        return new ServiceBinder();
    }
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        LogFileUtil.v(com.yline.aidl.User.TAG, "onCreate success");
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        LogFileUtil.v(com.yline.aidl.User.TAG, "onStartCommand success");
        return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        LogFileUtil.v(com.yline.aidl.User.TAG, "destroyed");
    }
    
    private class ServiceBinder extends IService.Stub
    {
        
        @Override
        public void callMethodInService()
            throws RemoteException
        {
            method("ServiceBinder is called");
        }
    }
    
    private void method(String content)
    {
        LogFileUtil.v(com.yline.aidl.User.TAG, content);
    }
}
