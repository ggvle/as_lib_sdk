package com.yline.sqlite;

import java.util.ArrayList;
import java.util.List;

import com.yline.lib.utils.LogUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * simple introduction
 *
 * @author YLine 2016-5-2 -> 上午7:25:01
 * @version 
 */
public class DataBaseHelper
{
    private SQliteHelper sqliteHelper;
    
    /**
     * 初始化 构建数据库 的方法
     */
    public DataBaseHelper(Context context)
    {
        sqliteHelper = SQliteHelper.getInstance(context);
    }
    
    /**
     * insert into table_name (name_key,number_key) values ('zhangsan','123')
     * @param bean
     */
    public void addOrigin(TestBean bean)
    {
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "add start");
        
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        String sql =
            "insert into " + com.yline.sqlite.SQliteHelper.TABLE_NAME + " ("
                + com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NAME + ", "
                + com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NUMBER + ") values (?, ?)";
        db.execSQL(sql, new Object[] {bean.getName(), bean.getNumber()});
        db.close();
        
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "add end");
    }
    
    public void addEncapse(TestBean bean)
    {
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "add start");
        
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NAME, bean.getName());
        values.put(com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NUMBER, bean.getNumber());
        db.insert(com.yline.sqlite.SQliteHelper.TABLE_NAME, null, values);
        
        db.close();
        
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "add end");
    }
    
    /**
     * delete from table_name where [id_key = 9 and] name_key = 'zhangsan'
     * @param name
     */
    public void deleteOrigin(String name)
    {
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "delete start");
        
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        String sql =
            "delete from " + com.yline.sqlite.SQliteHelper.TABLE_NAME + " where "
                + com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NAME + " = ? ";
        db.execSQL(sql, new Object[] {name});
        db.close();
        
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "delete end");
    }
    
    public void deleteEncapse(String name)
    {
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "delete start");
        
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        
        db.delete(com.yline.sqlite.SQliteHelper.TABLE_NAME,
            com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NAME + "= ?",
            new String[] {name});
        
        db.close();
        
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "delete end");
    }
    
    /**
     * update table_name set number_key = '12345465',name_key = "z421" where id = 10
     * @param bean
     */
    public void updateOrigin(TestBean bean)
    {
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "update start");
        
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        
        String sql =
            "update " + com.yline.sqlite.SQliteHelper.TABLE_NAME + " set "
                + com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NAME + " = ?,"
                + com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NUMBER + " = ? " + " where "
                + com.yline.sqlite.SQliteHelper.TABLE_COLUMN_ID + " = ? ";
        db.execSQL(sql, new Object[] {bean.getName(), bean.getNumber(), bean.getId()});
        
        db.close();
        
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "update end");
    }
    
    public void updateEncapse(TestBean bean)
    {
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "update start");
        
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NAME, bean.getName());
        values.put(com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NUMBER, bean.getNumber());
        db.update(com.yline.sqlite.SQliteHelper.TABLE_NAME, values, com.yline.sqlite.SQliteHelper.TABLE_COLUMN_ID
            + "= ? ", new String[] {bean.getId() + ""});
        
        db.close();
        
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "update end");
    }
    
    /**
     * select * from table_name where id_key = 10
     */
    public TestBean queryOrigin(int id)
    {
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "query one start");
        
        TestBean result = null;
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();
        String sql =
            " select * from " + com.yline.sqlite.SQliteHelper.TABLE_NAME + " where "
                + com.yline.sqlite.SQliteHelper.TABLE_COLUMN_ID + " = ? ";
        
        Cursor cursor = db.rawQuery(sql, new String[] {id + ""});
        
        if (cursor.moveToNext())
        {
            result = new TestBean();
            result.setId(cursor.getInt(cursor.getColumnIndex(com.yline.sqlite.SQliteHelper.TABLE_COLUMN_ID)));
            result.setName(cursor.getString(cursor.getColumnIndex(com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NAME)));
            result.setNumber(cursor.getString(cursor.getColumnIndex(com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NUMBER)));
        }
        cursor.close();
        db.close();
        
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "query one end");
        return result;
    }
    
    public TestBean queryEncapse(int id)
    {
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "query one start");
        
        TestBean result = null;
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();
        
        Cursor cursor =
            db.query(com.yline.sqlite.SQliteHelper.TABLE_NAME, null, com.yline.sqlite.SQliteHelper.TABLE_COLUMN_ID
                + " = ? ", new String[] {id + ""}, null, null, null);
        
        if (cursor.moveToNext())
        {
            result = new TestBean();
            result.setId(cursor.getInt(cursor.getColumnIndex(com.yline.sqlite.SQliteHelper.TABLE_COLUMN_ID)));
            result.setName(cursor.getString(cursor.getColumnIndex(com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NAME)));
            result.setNumber(cursor.getString(cursor.getColumnIndex(com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NUMBER)));
        }
        cursor.close();
        db.close();
        
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "query one end");
        return result;
    }
    
    /**
     * select * from table_name
     * @return
     */
    public List<TestBean> queryAllOrigin()
    {
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "query all start");
        
        List<TestBean> list = new ArrayList<TestBean>();
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();
        String sql = " select * from " + com.yline.sqlite.SQliteHelper.TABLE_NAME;
        
        Cursor cursor = db.rawQuery(sql, null);
        
        TestBean result = null;
        while (cursor.moveToNext())
        {
            result = new TestBean();
            result.setId(cursor.getInt(cursor.getColumnIndex(com.yline.sqlite.SQliteHelper.TABLE_COLUMN_ID)));
            result.setName(cursor.getString(cursor.getColumnIndex(com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NAME)));
            result.setNumber(cursor.getString(cursor.getColumnIndex(com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NUMBER)));
            list.add(result);
        }
        cursor.close();
        db.close();
        
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "query all end");
        return list;
    }
    
    public List<TestBean> queryAllEncapse()
    {
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "query all start");
        
        List<TestBean> list = new ArrayList<TestBean>();
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();
        
        Cursor cursor = db.query(com.yline.sqlite.SQliteHelper.TABLE_NAME, null, null, null, null, null, null);
        
        TestBean result = null;
        while (cursor.moveToNext())
        {
            result = new TestBean();
            result.setId(cursor.getInt(cursor.getColumnIndex(com.yline.sqlite.SQliteHelper.TABLE_COLUMN_ID)));
            result.setName(cursor.getString(cursor.getColumnIndex(com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NAME)));
            result.setNumber(cursor.getString(cursor.getColumnIndex(com.yline.sqlite.SQliteHelper.TABLE_COLUMN_NUMBER)));
            list.add(result);
        }
        
        cursor.close();
        db.close();
        
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "query all end");
        return list;
    }
    
    /**
     * 查询 数据表的 数据行数
     * @return
     */
    public long getCount()
    {
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "getCount start");
        
        long result = 0;
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();
        
        String sql = "select count(*) from " + com.yline.sqlite.SQliteHelper.TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        result = cursor.getLong(0);
        
        db.close();
        
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "getCount end");
        return result;
    }
    
}
