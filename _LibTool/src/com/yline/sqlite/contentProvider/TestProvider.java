package com.yline.sqlite.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.yline.lib.utils.LogUtil;
import com.yline.sqlite.SQliteHelper;

/**
 * simple introduction
 * Manifest中注册一个Provider
 *
 * @author YLine 2016-5-2 -> 下午12:03:21
 * @version 
 */
public class TestProvider extends ContentProvider
{
    private SQliteHelper        mHelper;
    
    // 标识符
    private static UriMatcher   matcher      = new UriMatcher(UriMatcher.NO_MATCH);
    
    private static final String AUTHORITY    = "com.yline.sqlite.contentProvider";
    
    private static final String TYPEQUERY    = "vnd.android.cursor.dir";
    
    private static final String TYPEQUERYONE = "vnd.android.cursor.item";
    
    // 增删改查
    private static final int    INSERT       = 1;
    
    private static final int    DELETE       = 2;
    
    private static final int    UPDATE       = 3;
    
    private static final int    QUERY        = 4;
    
    private static final int    QUERYONE     = 5;
    
    static
    {
        matcher.addURI(AUTHORITY, "insert", INSERT);
        matcher.addURI(AUTHORITY, "delete", DELETE);
        matcher.addURI(AUTHORITY, "update", UPDATE);
        matcher.addURI(AUTHORITY, "query", QUERY);
        matcher.addURI(AUTHORITY, "query/#", QUERYONE);
    }
    
    @Override
    public boolean onCreate()
    {
        mHelper = SQliteHelper.getInstance(getContext());
        return false;
    }
    
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "provider insert start, number = " + matcher.match(uri));
        if (INSERT == matcher.match(uri))
        {
            SQLiteDatabase db = mHelper.getWritableDatabase();
            db.insert(com.yline.sqlite.SQliteHelper.TABLE_NAME, null, values);
        }
        else
        {
            throw new IllegalArgumentException("uri 错误");
        }
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "provider insert end");
        return null;
    }
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "provider delete start, number = " + matcher.match(uri));
        if (DELETE == matcher.match(uri))
        {
            SQLiteDatabase db = mHelper.getWritableDatabase();
            db.delete(com.yline.sqlite.SQliteHelper.TABLE_NAME, selection, selectionArgs);
        }
        else
        {
            throw new IllegalArgumentException("uri 错误");
        }
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "provider delete end");
        return 0;
    }
    
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "provider update start, number = " + matcher.match(uri));
        
        if (UPDATE == matcher.match(uri))
        {
            SQLiteDatabase db = mHelper.getWritableDatabase();
            db.update(com.yline.sqlite.SQliteHelper.TABLE_NAME, values, selection, selectionArgs);
        }
        else
        {
            throw new IllegalArgumentException("uri 错误");
        }
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "provider update end");
        return 0;
    }
    
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "provider query start, number = " + matcher.match(uri));
        if (QUERY == matcher.match(uri))
        {
            SQLiteDatabase db = mHelper.getReadableDatabase();
            Cursor cursor =
                db.query(com.yline.sqlite.SQliteHelper.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder);
            LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "provider queryAll end");
            return cursor;
        }
        else if (QUERYONE == matcher.match(uri))
        {
            long id = ContentUris.parseId(uri);
            SQLiteDatabase db = mHelper.getReadableDatabase();
            Cursor cursor =
                db.query(com.yline.sqlite.SQliteHelper.TABLE_NAME,
                    projection,
                    "id=?",
                    new String[] {id + ""},
                    null,
                    null,
                    sortOrder);
            LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "provider queryOne end");
            return cursor;
        }
        else
        {
            LogUtil.v(com.yline.sqlite.User.TAG_SQLITE, "provider query error end");
            throw new IllegalArgumentException("uri 错误");
        }
    }
    
    @Override
    public String getType(Uri uri)
    {
        if (QUERY == matcher.match(uri))
        {
            return TYPEQUERY + "/" + com.yline.sqlite.SQliteHelper.TABLE_NAME;
        }
        else if (QUERYONE == matcher.match(uri))
        {
            return TYPEQUERYONE + "/" + com.yline.sqlite.SQliteHelper.TABLE_NAME;
        }
        return null;
    }
    
}
