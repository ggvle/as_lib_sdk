package com.yline.lib.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.yline.lib.tool.R;

public class MainActivity extends Activity
{
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        com.yline.sqlite.User user = new com.yline.sqlite.User(this);
        user.testOrigin();
        user.testEncapse();
        
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
            }
        });
    }
}
