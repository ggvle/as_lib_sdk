package com.yline.phone.messager;

import com.yline.lib.utils.LogUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

/**
 * simple introduction
 * android.permission.RECEIVE_SMS
 *
 * @author YLine 2016-5-2 -> 下午9:34:44
 * @version 
 */
public class MsgBroadcastReceiver extends BroadcastReceiver
{
    private static final String TAG_MSG_BROADCAST_RECEIVER = "msg_broadcast_receiver";
    
    @Override
    public void onReceive(Context context, Intent intent)
    {
        LogUtil.v(TAG_MSG_BROADCAST_RECEIVER, "receiver msg start");
        
        // 描述短信的格式
        Object[] pdus = (Object[])intent.getExtras().get("pdus");
        if (null != pdus)
        {
            for (Object object : pdus)
            {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])object);
                String body = smsMessage.getMessageBody(); // 短信内容
                String sender = smsMessage.getOriginatingAddress(); // 短信发送人
                LogUtil.v(TAG_MSG_BROADCAST_RECEIVER, "sender = " + sender + ",content = " + body);
            }
        }
        else
        {
            LogUtil.v(TAG_MSG_BROADCAST_RECEIVER, "get pdus is null");
        }
        
        /*
        // 因为安全机制,这句话在4.0以后就失效了
        abortBroadcast();
        */
        
        LogUtil.v(TAG_MSG_BROADCAST_RECEIVER, "receiver msg end");
    }
}
