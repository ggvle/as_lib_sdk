package com.yline.phone.caller;

import com.yline.lib.utils.LogUtil;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

/**
 * simple introduction
 * 拨打电话
 * android.permission.CALL_PHONE
 * android.permission.READ_CONTACTS
 * android.permission.WRITE_CONTACTS
 *
 * @author YLine 2016-5-1 -> 上午11:39:26
 * @version 
 */
public class User
{
    public static final String TAG_PHONE_CALLER = "phone_caller";
    
    public static final String RAW_CONTACTS_URI = "content://com.android.contacts/raw_contacts";
    
    public static final String DATA_URI         = "content://com.android.contacts/data";
    
    public static final String NAME_MIMETYPE    = "vnd.android.cursor.item/name";
    
    public static final String PHONE_MIMETYPE   = "vnd.android.cursor.item/phone_v2";
    
    public static final String EMAIL_MIMETYPE   = "vnd.android.cursor.item/email_v2";
    
    public void testCaller(Context context)
    {
        // 这个是直接拨打,而不是跳转到此 拨打界面
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + 563850));
        context.startActivity(intent);
        LogUtil.v(TAG_PHONE_CALLER, "拨打成功");
    }
    
    public void testQueryContacter(Context context)
    {
        Uri raw_contactsUri = Uri.parse(RAW_CONTACTS_URI);
        Uri dataUri = Uri.parse(DATA_URI);
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(raw_contactsUri, null, null, null, null);
        
        while (cursor.moveToNext())
        {
            String contact_id = cursor.getString(cursor.getColumnIndex("contact_id"));
            String display_name = cursor.getString(cursor.getColumnIndex("display_name"));
            /**
             * 联系人删除操作：使contact_id = null 数据并不删除
             * 目的：有一个功能，服务器同步;为了在网络不好时，删除而本地删除，网络没有删除;
             * 联网后，检查是否为空即可而不需要全部遍历
             */
            if (contact_id != null)
            {
                LogUtil.v(TAG_PHONE_CALLER, "contact_id=" + contact_id + ";  display_name=" + display_name);
                
                Cursor cursorId = resolver.query(dataUri, null, "contact_id=?", new String[] {contact_id}, null);
                while (cursorId.moveToNext())
                {
                    String data1 = cursorId.getString(cursorId.getColumnIndex("data1"));
                    String mimetype = cursorId.getString(cursorId.getColumnIndex("mimetype"));
                    LogUtil.v(TAG_PHONE_CALLER, "data1= " + data1);
                    LogUtil.v(TAG_PHONE_CALLER, "mimetype= " + mimetype);
                }
                
                cursorId.close();
            }
        }
        cursor.close();
    }
    
    /**
     * 需要添加 写联系人 权限,(没有不会报错)
     * @param context
     */
    public void testInsertContacter(Context context)
    {
        LogUtil.v(TAG_PHONE_CALLER, "insert start");
        
        Uri raw_contacturi = Uri.parse(RAW_CONTACTS_URI);
        Uri datauri = Uri.parse(DATA_URI);
        ContentResolver resolver = context.getContentResolver();
        Cursor cursorId = resolver.query(raw_contacturi, new String[] {"_id"}, null, null, null);
        //得到 插入新的id的值
        cursorId.moveToLast();
        int oldId = cursorId.getInt(cursorId.getColumnIndex("_id"));
        int newId = oldId + 1;
        //插入id
        ContentValues values = new ContentValues();
        values.put("contact_id", newId);
        resolver.insert(raw_contacturi, values);
        
        //用联系人的id，向data表中插入 姓名、电话、邮箱 以及对应的类型
        ContentValues namevalues = new ContentValues();
        namevalues.put("data1", "yline");
        namevalues.put("mimetype", NAME_MIMETYPE);
        namevalues.put("raw_contact_id", newId);
        resolver.insert(datauri, namevalues);
        
        ContentValues phonevalues = new ContentValues();
        phonevalues.put("data1", "15958148487");
        phonevalues.put("mimetype", PHONE_MIMETYPE);
        phonevalues.put("raw_contact_id", newId);
        resolver.insert(datauri, phonevalues);
        
        ContentValues emailvalues = new ContentValues();
        emailvalues.put("data1", "957339173@qq.com");
        emailvalues.put("mimetype", EMAIL_MIMETYPE);
        emailvalues.put("raw_contact_id", newId);
        resolver.insert(datauri, emailvalues);
        
        LogUtil.v(TAG_PHONE_CALLER, "insert end");
    }
}
