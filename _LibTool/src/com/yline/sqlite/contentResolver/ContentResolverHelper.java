package com.yline.sqlite.contentResolver;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.yline.lib.utils.LogUtil;

/**
 * simple introduction
 *
 * @author YLine 2016-5-2 -> 下午12:33:59
 * @version 
 */
public class ContentResolverHelper
{
    // uri
    private static final String CONTENT             = "content://";
    
    private static final String AUTHORITY           = "com.yline.sqlite.contentProvider";
    
    private static final String URI_INSERT          = CONTENT + AUTHORITY + "/insert";
    
    private static final String URI_DELETE          = CONTENT + AUTHORITY + "/delete";
    
    private static final String URI_UPDATE          = CONTENT + AUTHORITY + "/update";
    
    private static final String URI_QUERY           = CONTENT + AUTHORITY + "/query";
    
    // table_key
    private static final String  TABLE_COLUMN_ID     = "id";
    
    private static final String TABLE_COLUMN_NAME   = "name";
    
    private static final String TABLE_COLUMN_NUMBER = "number";
    
    private ContentResolver     mContentResolver;
    
    public ContentResolverHelper(Context context)
    {
        mContentResolver = context.getContentResolver();
    }
    
    public void insert()
    {
        Uri uri = Uri.parse(URI_INSERT);
        
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_COLUMN_NAME, "Resolver Name");
        contentValues.put(TABLE_COLUMN_NUMBER, "Resolver Number");
        
        mContentResolver.insert(uri, contentValues);
    }
    
    public void delete()
    {
        Uri uri = Uri.parse(URI_DELETE);
        mContentResolver.delete(uri, "name = ?", new String[] {"yline"});
    }
    
    public void update()
    {
        Uri uri = Uri.parse(URI_UPDATE);
        
        ContentValues values = new ContentValues();
        values.put("number", "12306");
        
        mContentResolver.update(uri, values, "name = ?", new String[] {"name 2"});
        
    }
    
    public void query()
    {
        Uri uri = Uri.parse(URI_QUERY);
        
        Cursor cursor = mContentResolver.query(uri, null, null, null, null);
        if (null != cursor)
        {
            while (cursor.moveToNext())
            {
                String id = cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_NAME));
                String number = cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_NUMBER));
                LogUtil.v("tag", "TestBean [id=" + id + ", name=" + name + ", number=" + number + "]");
            }
            cursor.close();
        }else {
            LogUtil.v("tag", "query cursor is null");
        }
        
    }
}
