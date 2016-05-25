package com.yline.phone.sdcard;

import com.yline.utils.LogUtil;

/**
 * simple introduction
 *
 * @author YLine 2016-5-1 -> 上午10:36:40
 * @version 
 */
public class User
{
    public static final String TAG_SDCARD = "sdcard";
    
    public void testNormal()
    {
        // 手机内部存储 信息
        float phoneUsedMemorySize = PhoneInfos.getTotalInternalMemorySize() / (1024 * 1024);
        float phoneFreeMemorySize = PhoneInfos.getAvailableInternalMemorySize() / (1024 * 1024);
        LogUtil.v(TAG_SDCARD, "phoneUsedMemorySize = " + phoneUsedMemorySize + " MB" + ",phoneFreeMemorySize = "
            + phoneFreeMemorySize + "MB");
        
        // 手机sd卡信息
        float SDUsedMemorySize = SDcardInfos.getTotalExternalMemorySize() / (1024 * 1024) / 1024;
        float SDUseableSize = SDcardInfos.getAvailableExternalMemorySize() / (1024 * 1024) / 1024;
        if (SDcardInfos.isSdcardEnable())
        {
            LogUtil.v(TAG_SDCARD, "SDUsedMemorySize = " + SDUsedMemorySize + " GB" + ",SDUseableSize = "
                + SDUseableSize + "GB");
        }
        else
        {
            LogUtil.v(TAG_SDCARD, "不存在sdcard");
        }
    }
    
    public void testUtil()
    {
        String rootPath = SDCardUtils.getRootDirectoryPath();
        String sdcardPath = SDCardUtils.getSDCardPath();
        LogUtil.v(TAG_SDCARD, "rootPath = " + rootPath);
        LogUtil.v(TAG_SDCARD, "sdcardPath = " + sdcardPath);
        
        float sdcardAllSize = 0;
        float sdcardFreeSize = 0;
        if (SDCardUtils.isSDCardEnable())
        {
            sdcardAllSize = SDCardUtils.getSDCardBlockSize() / (1024 * 1024);
            sdcardFreeSize = SDCardUtils.getFreeBytes(SDCardUtils.getSDCardPath()) / (1024 * 1024);
        }
        LogUtil.v(TAG_SDCARD, "sdcardUsedSize = " + sdcardAllSize / 1024 + "GB");
        LogUtil.v(TAG_SDCARD, "sdcardFreeSize = " + sdcardFreeSize / 1024 + "GB");
    }
}
