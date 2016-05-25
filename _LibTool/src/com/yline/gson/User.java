package com.yline.gson;

import java.util.List;

import com.google.gson.Gson;
import com.yline.log.LogFileUtil;

/**
 * 在一些大工程里面,需要做 非混淆 处理(proguard-project中添加)
 * 仅仅对父类进行该操作也是可以的
 * simple introduction
 *
 * <p>detailed comment
 * @author lWX353514 2016-5-3
 * @see
 * @since 1.0
 */
public class User
{
    public static final String TAG_PARSE_GSON = "parse_json";
    
    private final String jsonStr =
        "{ \"wifiListNum\": \"2\", \"wifiWhiteList\": [ { \"wifiName\": \"w90005368_SVNTes1\"},"
            + " { \"wifiName\": \"w90005368_SVNTest1\", \"macAddress\": \"a1:12:12:12:1A:45\" } ] }";
    
    public void test()
    {
        LogFileUtil.v(TAG_PARSE_GSON, "json parse running");
        try
        {
            Gson gson = new Gson();
            WifiPolicyJsonInfo wifiPolicyJsonInfo = gson.fromJson(jsonStr, WifiPolicyJsonInfo.class);
            
            if (null != wifiPolicyJsonInfo)
            {
                int number = wifiPolicyJsonInfo.getWifiListNum();
                LogFileUtil.v(TAG_PARSE_GSON, "json list size = " + number);
                if (0 != number)
                {
                    List<WiFiPolicy> wifiPolicyList = wifiPolicyJsonInfo.getWifiWhiteList();
                    // to do
                    LogFileUtil.v(TAG_PARSE_GSON, wifiPolicyList.toString());
                }
            }
            else
            {
                LogFileUtil.v(TAG_PARSE_GSON, "json parse result is null");
            }
        }
        catch (Exception e)
        {
            LogFileUtil.e(TAG_PARSE_GSON, "Exception = " + android.util.Log.getStackTraceString(e));
        }
        
    }
}
