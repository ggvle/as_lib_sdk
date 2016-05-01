package com.yline.phone.sdcard;

import java.io.File;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.StatFs;

public class SDcardInfos
{
    /**
     * sd存储,对于华为这种手机,这种方式获取的结果 等于 内部储存的获取结果
     * @return  sd卡已用空间  bytes
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static float getTotalExternalMemorySize()
    {
        File path = Environment.getExternalStorageDirectory();
        
        StatFs stat = new StatFs(path.getPath());
        long blockSize = 0;
        float totalBlocks = 0;
        int version = android.os.Build.VERSION.SDK_INT;
        // 版本判断
        if (version > android.os.Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            blockSize = stat.getBlockSizeLong();
            totalBlocks = stat.getBlockCountLong();
        }
        else
        {
            blockSize = stat.getBlockSize();
            totalBlocks = stat.getBlockCount();
        }
        
        return totalBlocks * blockSize;
    }
    
    /**
     * sd存储,对于华为这种手机,这种方式获取的结果 等于 内部储存的获取结果
     * @return  当前sdcard 是否可用
     */
    public static boolean isSdcardEnable()
    {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
    
    /**
     * @return  sd卡剩余空间 bytes
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static float getAvailableExternalMemorySize()
    {
        File path = Environment.getExternalStorageDirectory();
        
        StatFs stat = new StatFs(path.getPath());
        
        long blockSize = 0;
        float availableBlocks = 0;
        int version = android.os.Build.VERSION.SDK_INT;
        // 版本判断
        if (version > android.os.Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        }
        else
        {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
        }
        
        return availableBlocks * blockSize;
    }
}
