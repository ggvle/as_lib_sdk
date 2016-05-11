package com.yline.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.yline.lib.utils.LogUtil;

/**
 * simple introduction
 *
 * @author YLine 2016-5-1 -> 上午10:02:24
 * @version 
 */
public class User
{
    public static final String TAG_SHAREDPREFERENCES = "SharedPreferences";
    
    public User()
    {
        
    }
    
    /**
     * infos 文件名
     * log 的结果 应该只有 username
     * @param context
     */
    public void testNormal(Context context)
    {
        // 增加两条数据
        SharedPreferences sp = context.getSharedPreferences("infos", Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString("username", "normal username");
        editor.putString("password", "normal password");
        editor.commit();
        
        // 更新两条数据
        editor.putString("username", "normal update username");
        editor.putString("password", "normal update password");
        editor.commit();
        
        // 删除一条数据
        editor.remove("password");
        editor.commit();
        
        // 获取两条数据
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");
        LogUtil.v(TAG_SHAREDPREFERENCES, "usrname = " + username + ",password = " + password);
    }
    
    /**
     * sp_data 文件名
     * log 的结果 应该只有 username
     * @param context
     */
    public void testUtil(Context context)
    {
        // 增加两条数据
        SPUtil.put(context, "username", "util username");
        SPUtil.put(context, "password", "util password");
        
        // 更新两条数据
        SPUtil.put(context, "username", "util update username");
        SPUtil.put(context, "password", "util update password");
        
        // 删除一条数据
        SPUtil.remove(context, "password");
        
        // 获取两条数据
        String username = (String)SPUtil.get(context, "username", "");
        String password = (String)SPUtil.get(context, "password", "");
        
        LogUtil.v(TAG_SHAREDPREFERENCES, "usrname = " + username + ",password = " + password);
    }
}
