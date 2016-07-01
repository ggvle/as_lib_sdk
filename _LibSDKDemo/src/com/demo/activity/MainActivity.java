package com.demo.activity;

import android.app.Activity;
import android.os.Bundle;

import com.demo.utils.FileUtilUser;
import com.lib.sdk.demo.R;

public class MainActivity extends Activity
{
    private static final boolean isOldTestOpen = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (isOldTestOpen)
        {
            testOld();
        }
    }
    
    private void testOld()
    {
        // 写文件工具类
        new FileUtilUser().test();
    }
}
