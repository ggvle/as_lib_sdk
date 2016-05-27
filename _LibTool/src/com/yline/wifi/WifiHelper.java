package com.yline.wifi;

import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class WifiHelper
{
    
    /**
     * 设置中的WIfi开关是否打开
     * @param context
     * @return
     */
    public boolean isEnabled(Context context)
    {
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }
    
    /**
     * WIfi 是否 可用
     * @param context
     * @return
     */
    public boolean isAvailable(Context context)
    {
        ConnectivityManager connectivityManager =
            (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (null != networkInfo)
        {
            return networkInfo.isAvailable();
        }
        return false;
    }
    
    /**
     * WIfi 是否 已连接状态
     * @return
     */
    public boolean isConnected(Context context)
    {
        ConnectivityManager connectivityManager =
            (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (null != networkInfo)
        {
            return networkInfo.isConnected();
        }
        return false;
    }
    
    /**
     * 获取 WIfiInfo 对象
     * @param context
     * @return
     */
    public WifiInfo getWifiInfo(Context context)
    {
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo;
    }
    
    /**
     * 获取连接的WIfi名称; 
     * 注意
     * 1,断开连接WIfi时,获取的是上一次的WIfi名称 而不是null,因此使用这个之前,需要判断是否为连接状态
     * 2,WIfi开关,关闭时,获取的是null
     * @param context
     * @return  WIfi名称 or null
     */
    public String getWifiName(Context context)
    {
        String wifiName = getWifiInfo(context).getSSID();
        if ("0x".equals(wifiName) || null == wifiName || "0".equals(wifiName))
        {
            return null;
        }
        else
        {
            return wifiName;
        }
    }
    
    /**
     * @param context
     * @return WIfi地址 or null
     */
    public String getWifiMacAddress(Context context)
    {
        return getWifiInfo(context).getBSSID();
    }
    
    /**
     * 返回扫描到的WIfi列表; 
     * 注: 结果是最近扫描到的,即使WIfi开关关了,也有返回结果
     * @param context
     * @return  
     */
    public List<ScanResult> getWifiScanResult(Context context)
    {
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getScanResults();
    }
    
    /**
     * 断开连接
     * @param context
     * @return
     */
    public boolean disConnect(Context context)
    {
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.disconnect();
    }
    
    /**
     * 断开连接, 并在WIfi列表中移除
     * @param context
     * @return
     */
    public boolean disConnectAndRemove(Context context)
    {
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        boolean isBreak = wifiManager.disconnect();
        if (isBreak)
        {
            int netId = wifiManager.getConnectionInfo().getNetworkId();
            if (-1 != netId)
            {
                return wifiManager.removeNetwork(netId);
            }
        }
        return false;
    }
}
