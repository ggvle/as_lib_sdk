package com.yline.base;

import com.yline.application.BaseApplication;

import android.app.Activity;
import android.os.Bundle;

/**
 * simple introduction
 *
 * @author YLine 2016-5-25 -> 上午7:32:33
 * @version 
 */
public class BaseActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        BaseApplication.addAcitivity(this);
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        BaseApplication.removeActivity(this);
    }
}
