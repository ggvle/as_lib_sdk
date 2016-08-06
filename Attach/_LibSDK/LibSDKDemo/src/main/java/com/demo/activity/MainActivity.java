package com.demo.activity;

import com.demo.application.MainApplication;
import com.demo.utils.FileUtilUser;
import com.demo.utils.LogFileUtilUser;
import com.demo.utils.LogUtilUser;
import com.demo.utils.SPUtilUser;
import com.lib.sdk.demo.R;
import com.yline.base.BaseActivity;
import com.yline.log.LogFileUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.btn_baseapplication).setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                LogFileUtil.v(MainApplication.TAG, "btn_baseapplication");
                MainApplication.toast("测试，toast");
            }
        });
        
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
        
        findViewById(R.id.btn_spUtil).setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                new SPUtilUser().test(MainActivity.this);
            }
        });
        
        findViewById(R.id.btn_crashHandler).setOnClickListener(new View.OnClickListener()
        {
            
            @SuppressWarnings("null")
            @Override
            public void onClick(View v)
            {
                Button btn = null;
                btn.setText(100);
            }
        });
    }
}
