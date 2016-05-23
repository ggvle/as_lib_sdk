package com.yline.service.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.yline.lib.utils.combine.LogFileUtil;

/**
 * 使用binder,开启长期在后台运行的Service
 * PS:服务想要开启,必须先注册<不注册不会报错,也不会运行>
 * simple introduction
 *
 * @author YLine 2016-5-22 -> 上午11:22:16
 * @version 
 */
public class BinderService extends Service
{
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (null == intent)
        {
            intent = new Intent();
        }
        LogFileUtil.v(com.yline.service.User.TAG_SERVICE, "onStartCommand run");
        return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public IBinder onBind(Intent intent)
    {
        LogFileUtil.v(com.yline.service.User.TAG_SERVICE, "onBind");
        return new TestBinder();
    }
    
    @Override
    public boolean onUnbind(Intent intent)
    {
        LogFileUtil.v(com.yline.service.User.TAG_SERVICE, "onUnbind");
        return super.onUnbind(intent);
    }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        LogFileUtil.v(com.yline.service.User.TAG_SERVICE, "onDestroyed");
    }
    
    private class TestBinder extends Binder implements IBinderService
    {
        
        @Override
        public boolean call(String name)
        {
            player(name);
            return false;
        }
    }
    
    private void player(String content)
    {
        LogFileUtil.v(com.yline.service.User.TAG_SERVICE, "player ->" + content);
    }
}
