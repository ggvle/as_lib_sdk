package com.yline.view.listview;

import android.content.Context;
import android.widget.ListView;

/**
 * simple introduction
 * 每次只能测试一个
 *
 * @author YLine 2016-5-1 -> 下午11:40:04
 * @version 
 */
public class User
{
    public static final String TAG_LISTVIEW = "listview";
    
    public void testSimpleAdapter(Context context, ListView lv)
    {
        SimpleAdapterInstance sInstance = new SimpleAdapterInstance();
        sInstance.setData();
        sInstance.show(context, lv);
    }
    
    public void testArrayAdapter(Context context, ListView lv)
    {
        ArrayAdapterInstance aInstance = new ArrayAdapterInstance();
        aInstance.setData();
        aInstance.show(context, lv);
    }
    
    public void testBaseAdapter(Context context, ListView lv){
        BaseAdapterInstance bInstance = new BaseAdapterInstance();
        bInstance.setData();
        bInstance.show(context, lv);
    }
}
