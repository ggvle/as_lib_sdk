package com.yline.lib.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yline.lib.tool.R;

public class MainActivity extends Activity
{
    private com.yline.photo.User mUser;
    
    private ImageView            imageView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        imageView = (ImageView)findViewById(R.id.iv_test);
        
        mUser = new com.yline.photo.User();
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                mUser.testBackBefore(MainActivity.this);
            }
        });
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mUser.testBack(this, imageView, requestCode, resultCode, data);
    }
}
