package com.yline.lib.activity;

import android.content.Intent;
import android.os.Bundle;

import com.yline.base.BaseActivity;
import com.yline.lib.tool.R;

public class MainActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // new com.yline.service.User().testServiceBinder(this);
        new com.yline.gson.User().test();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
