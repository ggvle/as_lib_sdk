package com.yline.base;

import com.yline.log.LogFileUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BaseReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        LogFileUtil.m("BaseReceiver -> " + this.getClass().getSimpleName());
    }
    
}
