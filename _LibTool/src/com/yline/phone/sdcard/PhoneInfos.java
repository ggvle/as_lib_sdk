package com.yline.phone.sdcard;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;

public class PhoneInfos
{
    /**
     * 内部储存空间
     * @return  手机已用内存   bytes
     */
    @SuppressWarnings("deprecation")
    public static float getTotalInternalMemorySize()
    {
        File path = Environment.getDataDirectory();
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
     * 内部储存空间
     * @return  手机剩余内存空间  bytes
     */
    @SuppressWarnings("deprecation")
    public static float getAvailableInternalMemorySize()
    {
        File path = Environment.getDataDirectory();
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
