package com.yline.lib.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.yline.lib.tool.R;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        new com.yline.service.User().testServiceBinder(this);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
