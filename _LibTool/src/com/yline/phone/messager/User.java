package com.yline.phone.messager;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;

/**
 * simple introduction
 * 权限
 * android.Manifest.permission.SEND_SMS 
 * android.permission.READ_SMS
 * android.permission.WRITE_SMS
 * 
 * @author YLine 2016-5-1 -> 上午11:51:50
 * @version 
 */
public class User
{
    public static final String TAG_MSG  = "SmsManager";
    
    public static final String URI      = "content://sms/";
    
    public static final String ADDARESS = "address";
    
    public static final String DATE     = "date";
    
    public static final String TYPE     = "type";
    
    public static final String BODY     = "body";
    
    public void testSend()
    {
        // 发送短信
        String number = "563850";
        String content = "content";
        MsgSender sender = new MsgSender();
        sender.send(number, content);
    }
    
    public void testReader(Context context)
    {
        // 读取短信
        new MsgReader().read(context);
    }
    
    public void testInserter(Context context)
    {
        // 插入短信
        new MsgInserter().insert(context);
    }
    
    public void testObserver(Context context)
    {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = Uri.parse(URI);
        //接受内容观察者数据
        resolver.registerContentObserver(uri, true, new MsgObserver(new Handler()));
    }
}
