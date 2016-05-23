package com.libjar;

import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;

/**
 * gson并不能运行,只能放到某些库里面,看源码
 * simple introduction
 *
 * @author YLine 2016-5-23 -> 下午10:50:56
 * @version
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Gson gson = new Gson();
    }
}
