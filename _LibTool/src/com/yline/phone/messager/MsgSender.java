package com.yline.phone.messager;

import java.util.ArrayList;

import android.telephony.SmsManager;

import com.yline.utils.LogUtil;

/**
 * simple introduction
 * 发送短信
 *
 * @author YLine 2016-5-1 -> 下午1:36:49
 * @version 
 */
public class MsgSender
{
    public void send(String number, String content){
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> contents = smsManager.divideMessage(content);
        for (String str : contents)
        {
            smsManager.sendTextMessage(number, null, str, null, null);
        }
        LogUtil.v("TAG_MSG", "发送成功");
    }
}
