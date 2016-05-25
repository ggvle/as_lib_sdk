package com.yline.view.listview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yline.lib.tool.R;
import com.yline.utils.LogUtil;

/**
 * simple introduction
 *
 * @author YLine 2016-5-2 -> 上午12:32:47
 * @version 
 */
public class ArrayAdapterInstance
{
    private List<String> list;
    
    public void setData()
    {
        list = new ArrayList<String>();
        list.add("funtion 1");
        list.add("funtion 2");
        list.add("funtion 3");
        list.add("funtion 4");
        list.add("funtion 5");
        list.add("funtion 6");
        list.add("funtion 7");
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void show(Context context, ListView lv)
    {
        lv.setAdapter(new ArrayAdapter(context, R.layout.listview_item, R.id.tv_id, list));
        LogUtil.v(com.yline.view.listview.User.TAG_LISTVIEW, "this end");
    }
}
