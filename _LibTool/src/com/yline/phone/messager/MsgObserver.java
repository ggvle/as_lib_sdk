package com.yline.phone.messager;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import com.yline.lib.base.MainApplication;
import com.yline.utils.LogUtil;

/**
 * simple introduction
 *
 * @author YLine 2016-5-1 -> 下午3:22:18
 * @version 
 */
public class MsgObserver extends ContentObserver
{
    
    /**
     * @param handler
     */
    public MsgObserver(Handler handler)
    {
        super(handler);
    }
    
    /**
     * 自己给自己发送一次短信,会调用比较多的次数(4次)
     * 可以利用type和selfChange来判断
     * 
     * 当内容观察者 观察到数据库的变化时，调用这个方法
     * 实际上为，观察到消息邮箱里面有一条数据库内容变化的通知
     */
    @Override
    public void onChange(boolean selfChange)
    {
        super.onChange(selfChange);
        LogUtil.v(com.yline.phone.messager.User.TAG_MSG, "selfChange = " + selfChange);
        
        ContentResolver resolver = MainApplication.getApplication().getContentResolver();
        Uri uri = Uri.parse(com.yline.phone.messager.User.URI);
        Cursor cursor = resolver.query(uri, new String[] {"address", "date", "body", "type"}, null, null, null);
        
        cursor.moveToFirst();
        String address = cursor.getString(cursor.getColumnIndex(com.yline.phone.messager.User.ADDARESS));
        String date = cursor.getString(cursor.getColumnIndex(com.yline.phone.messager.User.DATE));
        String type = cursor.getString(cursor.getColumnIndex(com.yline.phone.messager.User.TYPE));
        String body = cursor.getString(cursor.getColumnIndex(com.yline.phone.messager.User.BODY));
        
        LogUtil.v(com.yline.phone.messager.User.TAG_MSG, "type : " + type);
        LogUtil.v(com.yline.phone.messager.User.TAG_MSG, "address : " + address);
        LogUtil.v(com.yline.phone.messager.User.TAG_MSG, "body : " + body);
        LogUtil.v(com.yline.phone.messager.User.TAG_MSG, "date : " + date);
        
        cursor.close();
    }
}
