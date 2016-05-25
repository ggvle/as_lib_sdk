package com.yline.phone.messager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.yline.utils.LogUtil;

/**
 * simple introduction
 * 往系统中,插入短信
 *
 * @author YLine 2016-5-1 -> 下午1:40:07
 * @version 
 */
public class MsgInserter
{
    /**
     * 往系统中,插入一条短信
     * 因为从Android 4.4开始，只有默认的SMS APP才能对SMS数据库进行处理; 因此插入不会有反应
     * 只有则修改成默认的SMS APP,才能起作用
     */
    public void insert(Context context)
    {
        Uri uri = Uri.parse(com.yline.phone.messager.User.URI);
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(com.yline.phone.messager.User.ADDARESS, 1378709);
        values.put(com.yline.phone.messager.User.DATE, System.currentTimeMillis()); //系统时间
        values.put(com.yline.phone.messager.User.TYPE, 1); //1 代表发送给自己
        values.put(com.yline.phone.messager.User.BODY, "生，是为了证明爱存在的痕迹；火，燃烧后更伟大的生命；杀，是为了歌颂破灭前的壮丽；夜，是狼深邃眼睛孤独等待黎明。");
        resolver.insert(uri, values);
        LogUtil.v(com.yline.phone.messager.User.TAG_MSG, "插入结束");
    }
}
