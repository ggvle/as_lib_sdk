package com.yline.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import com.yline.log.LogUtil

class BaseReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        LogUtil.v("BaseReceiver -> " + this.javaClass.simpleName)
    }

}
