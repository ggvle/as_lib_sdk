package com.yline.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.yline.lib.utils.combine.LogFileUtil;
import com.yline.service.binder.BinderService;
import com.yline.service.binder.IBinderService;

/**
 * simple introduction
 * 服务想要开启,必须先注册<不注册不会报错,也不会运行>
 *
 * @author YLine 2016-5-22 -> 上午11:05:02
 * @version 
 */
public class User
{
    public static final String TAG_SERVICE = "service";
    
    public void testService(Context context)
    {
        context.startService(new Intent(context, TestService.class));
        LogFileUtil.v(TAG_SERVICE, "startService success");
        
        context.stopService(new Intent(context, TestService.class));
        LogFileUtil.v(TAG_SERVICE, "stopService success");
    }
    
    private IBinderService iBinderService;
    
    public void testServiceBinder(Context context)
    {
        // 开启服务
        context.startService(new Intent(context, BinderService.class));
        
        // 绑定服务
        BinderConn binderConn = new BinderConn();
        context.bindService(new Intent(context, BinderService.class), binderConn, Context.BIND_AUTO_CREATE);
        
        // 测试的时候,立即解除绑定,会导致BinderConn不会被调用;因为它是异步的
        // 解除绑定服务
        context.unbindService(binderConn);
        
        // 关闭服务
        context.stopService(new Intent(context, BinderService.class));
    }
    
    private class BinderConn implements ServiceConnection
    {
        
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            LogFileUtil.v(com.yline.service.User.TAG_SERVICE, "BinderConn run");
            iBinderService = (IBinderService)service;
            
            // 必须在这个里面调用,而且中文编码会乱码
            // BinderConn 是异步处理的,因此调用iBinderService必须在这个里面
            // PS:这里其实不用 判空了
            if (null != iBinderService)
            {
                iBinderService.call("绑定服务后,调用");
            }
            else
            {
                LogFileUtil.e(com.yline.service.User.TAG_SERVICE, "iBinderService is null");
            }
        }
        
        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            // TODO Auto-generated method stub
        }
    }
}
