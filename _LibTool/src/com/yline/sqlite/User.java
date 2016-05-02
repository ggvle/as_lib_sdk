package com.yline.sqlite;

import java.util.List;

import com.yline.lib.utils.LogUtil;

import android.content.Context;

/**
 * simple introduction
 * 注意点: id,就算自己放进去了,也是自增长的,而与自己放入的值没有关系
 *
 * @author YLine 2016-5-2 -> 上午6:56:00
 * @version 
 */
public class User
{
    public static final String TAG_SQLITE = "sqlite";
    
    private DataBaseHelper     mHelper;
    
    public User(Context context)
    {
        mHelper = new DataBaseHelper(context);
    }
    
    public void testOrigin()
    {
        LogUtil.v(TAG_SQLITE, "*************************************");
        LogUtil.v(TAG_SQLITE, "*************************************");
        LogUtil.v(TAG_SQLITE, "*************************************");
        
        mHelper.addOrigin(new TestBean(0, "name " + 0, "number " + 0));
        mHelper.addOrigin(new TestBean(1, "name " + 1, "number " + 1));
        mHelper.addOrigin(new TestBean(2, "name " + 2, "number " + 2));
        
        mHelper.deleteOrigin("name 0");
        
        mHelper.updateOrigin(new TestBean(1, "update name", "update number"));
        
        TestBean bean = mHelper.queryOrigin(5);
        if (null != bean)
        {
            LogUtil.v(TAG_SQLITE, bean.toString());
        }
        
        List<TestBean> beans = mHelper.queryAllOrigin();
        if (null != beans)
        {
            LogUtil.v(TAG_SQLITE, beans.toString());
        }
        
    }
    
    public void testEncapse()
    {
        LogUtil.v(TAG_SQLITE, "*************************************");
        LogUtil.v(TAG_SQLITE, "*************************************");
        LogUtil.v(TAG_SQLITE, "*************************************");
        
        mHelper.addEncapse(new TestBean(10, "name " + 10, "number " + 10));
        mHelper.addEncapse(new TestBean(11, "name " + 11, "number " + 11));
        mHelper.addEncapse(new TestBean(12, "name " + 12, "number " + 12));
        
        mHelper.deleteEncapse("name 10");
        
        mHelper.updateEncapse(new TestBean(11, "update name", "update number"));
        
        TestBean bean = mHelper.queryEncapse(6);
        if (null != bean)
        {
            LogUtil.v(TAG_SQLITE, bean.toString());
        }
        
        List<TestBean> beans = mHelper.queryAllEncapse();
        if (null != beans)
        {
            LogUtil.v(TAG_SQLITE, beans.toString());
        }
    }
}
