package com.yline.lib.helper;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;

import com.yline.log.LogFileUtil;

/**
 * simple introduction
 * android.permission.ACCESS_NETWORK_STATE
 * android.permission.ACCESS_WIFI_STATE
 * android.permission.CHANGE_WIFI_STATE
 *
 * <p>detailed comment
 * @author lWX353514 2016-5-3
 * @see
 * @since 1.0
 */
public class User
{
    public static final String TAG_WIFI_HELPER = "wifi_helper";
    
    public void testWifi(Context context)
    {
        WifiHelper wifiHelper = new WifiHelper();
        
        boolean isEnabled = wifiHelper.isEnabled(context);
        LogFileUtil.v(TAG_WIFI_HELPER, "isEnabled = " + isEnabled);
        
        boolean isAvailable = wifiHelper.isAvailable(context);
        LogFileUtil.v(TAG_WIFI_HELPER, "isAvailable = " + isAvailable);
        
        boolean isConnected = wifiHelper.isConnected(context);
        LogFileUtil.v(TAG_WIFI_HELPER, "isConnected = " + isConnected);
        
        // WIfi name
        String name = wifiHelper.getWifiName(context);
        if (null != name)
        {
            LogFileUtil.v(TAG_WIFI_HELPER, "name = " + name);
        }
        else
        {
            LogFileUtil.v(TAG_WIFI_HELPER, "name = " + name);
        }
        
        // WIfi macAddress
        String macAddress = wifiHelper.getWifiMacAddress(context);
        if (null != macAddress)
        {
            LogFileUtil.v(TAG_WIFI_HELPER, "macAddress = " + macAddress);
        }
        else
        {
            LogFileUtil.v(TAG_WIFI_HELPER, "macAddress = " + macAddress);
        }
        
        List<ScanResult> scanResultList = wifiHelper.getWifiScanResult(context);
        for (ScanResult scanResult : scanResultList)
        {
            LogFileUtil.v(TAG_WIFI_HELPER, "wifi name = " + scanResult.SSID);
            LogFileUtil.v(TAG_WIFI_HELPER, "wifi name = " + scanResult.BSSID);
        }
    }
    
    public void testDisConnect(Context context)
    {
        WifiHelper wifiHelper = new WifiHelper();
        wifiHelper.disConnect(context);
    }
    
    public void testDisAndRemove(Context context)
    {
        WifiHelper wifiHelper = new WifiHelper();
        wifiHelper.disConnectAndRemove(context);
    }
}
