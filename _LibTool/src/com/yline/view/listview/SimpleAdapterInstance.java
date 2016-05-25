package com.yline.view.listview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.yline.lib.tool.R;
import com.yline.utils.LogUtil;

/**
 * simple introduction
 *
 * @author YLine 2016-5-1 -> 下午11:40:43
 * @version 
 */
public class SimpleAdapterInstance
{
    private static final String       ID   = "id";
    
    private static final String       NAME = "name";
    
    private List<Map<String, String>> data;
    
    /**
     * 
     */
    public void setData()
    {
        data = new ArrayList<Map<String, String>>();
        data.add(getMap("1", "云彩"));
        data.add(getMap("2", "地球"));
        data.add(getMap("3", "阳光"));
        data.add(getMap("4", "树木"));
        data.add(getMap("5", "天空"));
        data.add(getMap("6", "群山"));
    }
    
    private Map<String, String> getMap(String id, String name)
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put(ID, id);
        map.put(NAME, name);
        return map;
    }
    
    /**
     * 
     */
    public void show(Context context, ListView lv)
    {
        String[] from = new String[] {ID, NAME};
        int[] to = new int[] {R.id.tv_id, R.id.tv_name};
        
        lv.setAdapter(new SimpleAdapter(context, data, R.layout.listview_item, from, to));
        LogUtil.v(com.yline.view.listview.User.TAG_LISTVIEW, "this end");
    }
}
