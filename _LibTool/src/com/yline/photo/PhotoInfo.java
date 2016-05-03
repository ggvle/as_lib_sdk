package com.yline.photo;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.ExifInterface;
import android.net.Uri;

import com.yline.lib.utils.LogUtil;

/**
 * simple introduction
 *
 * @author YLine 2016-5-4 -> 上午7:34:30
 * @version 
 */
public class PhotoInfo
{
    private static final int REQUEST_CODE = 0;
    
    /**
     * 获取相册信息
     * @param fileName  路径
     */
    public void getPhotoExifInfo(String fileName)
    {
        try
        {
            ExifInterface exifInterface = new ExifInterface(fileName);
            
            // 时间
            String time = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            LogUtil.v(com.yline.photo.User.TAG_PHOTO, "time = " + time);
            
            // 相机模式
            String model = exifInterface.getAttribute(ExifInterface.TAG_MODEL);
            LogUtil.v(com.yline.photo.User.TAG_PHOTO, "model = " + model);
        }
        catch (IOException e)
        {
            LogUtil.v(com.yline.photo.User.TAG_PHOTO, "new ExifInterface error");
            e.printStackTrace();
        }
    }
    
    /**
     * 跳转到系统相册
     * @param activity
     */
    public void intentToAlbum(Activity activity)
    {
        activity.startActivityForResult(new Intent().setAction(Intent.ACTION_PICK).setType("image/*"), REQUEST_CODE);
    }
    
    /**
     * 系统相册返回路劲
     * @param requestCode 请求码
     * @param data 数据
     * @return  URI or null
     */
    public Uri intentBackOfAlbum(int requestCode, Intent data)
    {
        if (null != data)
        {
            LogUtil.v(com.yline.photo.User.TAG_PHOTO, "intentBackOfAlbum requestCode = " + requestCode);
            if (REQUEST_CODE == requestCode)
            {
                return data.getData();
            }
        }
        return null;
    }
}
