package com.yline.phone.messager;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.yline.lib.utils.LogUtil;

/**
 * simple introduction
 * 读取系统短信
 *
 * @author YLine 2016-5-1 -> 下午1:40:41
 * @version 
 */
public class MsgReader
{
    /**
     * 读取系统短信
     */
    public void read(Context context)
    {
        Uri uri = Uri.parse(com.yline.phone.messager.User.URI);
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor =
            resolver.query(uri, new String[] {com.yline.phone.messager.User.ADDARESS, com.yline.phone.messager.User.DATE,
                com.yline.phone.messager.User.TYPE, com.yline.phone.messager.User.BODY}, null, null, null);
        while (cursor.moveToNext())
        {
            String address = cursor.getString(cursor.getColumnIndex(com.yline.phone.messager.User.ADDARESS));
            String date = cursor.getString(cursor.getColumnIndex(com.yline.phone.messager.User.DATE));
            String type = cursor.getString(cursor.getColumnIndex(com.yline.phone.messager.User.TYPE));
            String body = cursor.getString(cursor.getColumnIndex(com.yline.phone.messager.User.BODY));
            // 打印到 LogCat 中
            LogUtil.v(com.yline.phone.messager.User.TAG_MSG, com.yline.phone.messager.User.ADDARESS + " : " + address);
            LogUtil.v(com.yline.phone.messager.User.TAG_MSG, com.yline.phone.messager.User.DATE + " : " + date);
            LogUtil.v(com.yline.phone.messager.User.TAG_MSG, com.yline.phone.messager.User.TYPE + " : " + type);
            LogUtil.v(com.yline.phone.messager.User.TAG_MSG, com.yline.phone.messager.User.BODY + " : " + body);
            LogUtil.v(com.yline.phone.messager.User.TAG_MSG, "\n");
        }
        cursor.close();
        
    }
}
