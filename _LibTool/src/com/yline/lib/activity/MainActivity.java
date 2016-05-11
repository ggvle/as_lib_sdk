package com.yline.lib.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.yline.lib.tool.R;

public class MainActivity extends Activity
{
    private com.yline.photo.User mUser;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mUser = new com.yline.photo.User();
        mUser.testDraw(this, (ImageView)findViewById(R.id.iv_test));
        //        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener()
        //        {
        //            
        //            @Override
        //            public void onClick(View v)
        //            {
        //                mUser.testBacBefore(MainActivity.this);
        //            }
        //        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mUser.testBack(this, requestCode, resultCode, data);
    }
}
