package com.yline.base;

import android.app.Activity;
import android.os.Bundle;

import com.yline.application.BaseApplication;

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
        BaseApplication.addAcitivity(this);
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
