package com.demo.utils;

import com.yline.utils.LogUtil;

public class LogUtilUser
{
    public void test()
    {
        LogUtil.v("test -> v");
        LogUtil.v("test -> v", LogUtil.LOG_LOCATION_PARENT);
        
        LogUtil.d("test -> d");
        LogUtil.d("test -> d", LogUtil.LOG_LOCATION_PARENT);
        
        LogUtil.i("test -> i");
        LogUtil.i("test -> i", LogUtil.LOG_LOCATION_PARENT);
        LogUtil.i("test -> i", new Exception("test -> i -> Exception"));
        LogUtil.i("test -> i", LogUtil.LOG_LOCATION_PARENT, new Exception("test -> i -> Exception"));
        
        LogUtil.w("test -> w");
        LogUtil.w("test -> w", LogUtil.LOG_LOCATION_PARENT);
        
        LogUtil.e("test -> e");
        LogUtil.e("test -> e", LogUtil.LOG_LOCATION_PARENT);
        LogUtil.e("test -> e", new Exception("test -> e -> Exception"));
        LogUtil.e("test -> e", LogUtil.LOG_LOCATION_PARENT, new Exception("test -> e -> Exception"));
    }
}
