package com.yline.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.yline.application.BaseApplication;

/**
 * simple introduction
 *
 * @author YLine 2016-5-25 -> 上午7:32:58
 * @version 
 */
public class BaseFragmentActivity extends FragmentActivity
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
        BaseApplication.removeActivity(this);
    }
}
