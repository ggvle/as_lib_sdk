package com.demo.utils;

import com.yline.log.LogFileUtil;

public class LogFileUtilUser
{
    private static final String TAG = "LogFileUtilUser";
    
    public void test()
    {
        LogFileUtil.m("m");
        LogFileUtil.m("m", LogFileUtil.LOG_LOCATION_PARENT);
        
        LogFileUtil.v(TAG, "v");
        LogFileUtil.v(TAG, "v", LogFileUtil.LOG_LOCATION_PARENT);
        
        LogFileUtil.d(TAG, "d");
        LogFileUtil.d(TAG, "d", LogFileUtil.LOG_LOCATION_PARENT);
        
        LogFileUtil.i(TAG, "i");
        LogFileUtil.i(TAG, "i", LogFileUtil.LOG_LOCATION_PARENT);
        LogFileUtil.i(TAG, "i", new Exception("i -> Exception"));
        LogFileUtil.i(TAG, "i", LogFileUtil.LOG_LOCATION_PARENT, new Exception("i -> Exception"));
        
        LogFileUtil.w(TAG, "w");
        LogFileUtil.w(TAG, "w", LogFileUtil.LOG_LOCATION_PARENT);
        
        LogFileUtil.e(TAG, "e");
        LogFileUtil.e(TAG, "e", LogFileUtil.LOG_LOCATION_PARENT);
        LogFileUtil.e(TAG, "e", new Exception("e -> Exception"));
        LogFileUtil.e(TAG, "e", LogFileUtil.LOG_LOCATION_PARENT, new Exception("e -> Exception"));
    }
}
