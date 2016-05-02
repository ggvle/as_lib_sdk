package com.yline.sqlite.contentResolver;

import android.content.Context;

import com.yline.lib.utils.LogUtil;

/**
 * simple introduction
 * 这个部分的运行,需要放到另外一个应用中.
 * 已经实现成功
 *
 * @author YLine 2016-5-2 -> 下午8:18:33
 * @version 
 */
public class User
{
    private ContentResolverHelper mHelper;
    
    public void test(Context context)
    {
        mHelper = new ContentResolverHelper(context);
        
        LogUtil.v("tag", "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        mHelper.query();
        
        LogUtil.v("tag", "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        mHelper.insert();
        mHelper.query();
        
        LogUtil.v("tag", "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        mHelper.delete();
        mHelper.query();
        
        LogUtil.v("tag", "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        mHelper.update();
        mHelper.query();
    }
}
