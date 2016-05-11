package com.yline.activity.liftcycle;

import android.app.Activity;
import android.os.Bundle;

import com.yline.lib.utils.LogUtil;

/**
 * simple introduction
 * 就是多一个Log
 *
 * @author YLine 2016-5-2 -> 下午8:45:58
 * @version 
 */
public class TestBaseActivity extends Activity
{
    private static final String TAG_LIFECYCLE = "lifeCycle";
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LogUtil.v(TAG_LIFECYCLE, this.getClass().getSimpleName() + " onCreate");
    }
    
    @Override
    protected void onStart()
    {
        LogUtil.v(TAG_LIFECYCLE, this.getClass().getSimpleName() + " onStart");
        super.onStart();
    }
    
    @Override
    protected void onRestart()
    {
        LogUtil.v(TAG_LIFECYCLE, this.getClass().getSimpleName() + " onRestart");
        super.onRestart();
    }
    
    @Override
    protected void onResume()
    {
        LogUtil.v(TAG_LIFECYCLE, this.getClass().getSimpleName() + " onResume");
        super.onResume();
    }
    
    @Override
    protected void onPause()
    {
        LogUtil.v(TAG_LIFECYCLE, this.getClass().getSimpleName() + " onPause");
        super.onPause();
    }
    
    @Override
    protected void onStop()
    {
        LogUtil.v(TAG_LIFECYCLE, this.getClass().getSimpleName() + " onStop");
        super.onStop();
    }
    
    @Override
    protected void onDestroy()
    {
        LogUtil.v(TAG_LIFECYCLE, this.getClass().getSimpleName() + " onDestroy");
        super.onDestroy();
    }
    
    /**
     * Activity窗口获得或失去焦点时被调用,在onResume之后或onPause之后  
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        LogUtil.v(TAG_LIFECYCLE, this.getClass().getSimpleName() + " onWindowFocusChanged called");
        super.onWindowFocusChanged(hasFocus);
    }
    
    /** 
     * Activity被系统杀死时被调用. 
     * 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死. 
     * 另外,当跳转到其他Activity或者按Home键回到主屏时该方法也会被调用,系统是为了保存当前View组件的状态. 
     * 在onPause之前被调用. 
     */
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        LogUtil.v(TAG_LIFECYCLE, this.getClass().getSimpleName() + " onSaveInstanceState called.");
        super.onSaveInstanceState(outState);
    }
    
    /** 
     * Activity被系统杀死后再重建时被调用. 
     * 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死,用户又启动该Activity. 
     * 这两种情况下onRestoreInstanceState都会被调用,在onStart之后. 
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        LogUtil.v(TAG_LIFECYCLE, this.getClass().getSimpleName() + " onRestoreInstanceState called.");
        super.onRestoreInstanceState(savedInstanceState);
    }
}
