package com.yline.gson;

import java.util.List;

public class WifiPolicyJsonInfo
{
    public int wifiListNum;
    
    public List<WiFiPolicy> wifiWhiteList;
    
    public int getWifiListNum()
    {
        return wifiListNum;
    }
    
    public void setWifiListNum(int wifiListNum)
    {
        this.wifiListNum = wifiListNum;
    }
    
    public List<WiFiPolicy> getWifiWhiteList()
    {
        return wifiWhiteList;
    }
    
    public void setWifiWhiteList(List<WiFiPolicy> wifiWhiteList)
    {
        this.wifiWhiteList = wifiWhiteList;
    }
    
}
