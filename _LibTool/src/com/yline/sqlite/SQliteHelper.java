package com.yline.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.yline.lib.utils.LogUtil;

/**
 * simple introduction
 * 创建数据库等相关操作
 *
 * @author YLine 2016-5-2 -> 上午6:54:50
 * @version 
 */
public class SQliteHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME             = "yline_db";
    
    public static final String TABLE_NAME          = "yline_table";
    
    public static final String TABLE_COLUMN_ID     = "id";
    
    public static final String TABLE_COLUMN_NAME   = "name";
    
    public static final String TABLE_COLUMN_NUMBER = "number";
    
    public static final int    VERSION             = 1;
    
    private static SQliteHelper sInstance = null;
    
    public static synchronized SQliteHelper getInstance(Context context){
        if (null == sInstance)
        {
            sInstance = new SQliteHelper(context);
        }
        return sInstance;
    }
    
    private SQliteHelper(Context context)
    {
        this(context, DB_NAME, null, VERSION);
    }
    
    /**
     * @param context   上下文
     * @param name      数据库名称
     * @param factory   创建一个cursor(游标); null for the default
     * @param version   版本 >=1
     */
    private SQliteHelper(Context context, String name, CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "construct ");
    }
    
    /**
     * create table table_name (key_id integer primary key autoincrement,key_name varchar(20),key_number varchar(20))
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sqlCreate =
            "create table " + TABLE_NAME + " (" + TABLE_COLUMN_ID + " integer primary key autoincrement, "
                + TABLE_COLUMN_NAME + " varchar(20), " + TABLE_COLUMN_NUMBER + " varchar(20))";
        db.execSQL(sqlCreate); // 执行sq语句
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "onCreate ");
    }
    
    /**
     * alter table value_version add account varchar(20)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String sqlUpdate = "alter table version add account varchar(20)";
        db.execSQL(sqlUpdate);
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "Sqlite onUpgraded...");
    }
}
