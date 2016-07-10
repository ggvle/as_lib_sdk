package com.demo.activity;

import com.demo.utils.FileUtilUser;
import com.demo.utils.LogFileUtilUser;
import com.demo.utils.LogUtilUser;
import com.lib.sdk.demo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.btn_logutil).setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                new LogUtilUser().test();
            }
        });
        
        findViewById(R.id.btn_fileUtil).setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                new FileUtilUser().test();
            }
        });
        
        findViewById(R.id.btn_logFileUtil).setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                new LogFileUtilUser().test();
            }
        });
    }
}
