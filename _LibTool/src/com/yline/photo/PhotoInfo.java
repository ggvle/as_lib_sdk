package com.yline.photo;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

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
     * 路径格式: /storage/emulated/0/Pictures/Screenshots/Screenshot_2016-05-02-07-15-22.jpeg
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
            
            // 相机模式
            String imageWidth = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
            LogUtil.v(com.yline.photo.User.TAG_PHOTO, "imageWidth = " + imageWidth);
            
            // 相机模式
            String imageLength = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
            LogUtil.v(com.yline.photo.User.TAG_PHOTO, "imageLength = " + imageLength);
        }
        catch (IOException e)
        {
            LogUtil.v(com.yline.photo.User.TAG_PHOTO, "new ExifInterface error");
            e.printStackTrace();
        }
    }
    
    /**
     * 跳转到系统相册;
     * 注:如果有多个,跳之前会让你选择
     * @param activity
     */
    public void intentToAlbum(Activity activity)
    {
        activity.startActivityForResult(new Intent().setAction(Intent.ACTION_PICK).setType("image/*"), REQUEST_CODE);
    }
    
    /**
     * 系统相册返回路径,示例:content://media/external/images/media/143649
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
    
    /**
     * such as
     * from:    content://media/external/images/media/155645
     * to:      /storage/emulated/0/Pictures/Screenshots/Screenshot_2016-05-02-07-15-22.jpeg
     * @param uri
     * @return path路径 or null
     */
    public String Uri2Path(Context context, Uri uri)
    {
        String path = null;
        LogUtil.v(com.yline.photo.User.TAG_PHOTO, "uri = " + uri);
        if (null != uri)
        {
            String[] projection = {MediaStore.Images.Media.DATA};
            CursorLoader loader = new CursorLoader(context, uri, projection, null, null, null);
            Cursor cursor = loader.loadInBackground();
            
            try
            {
                int column_index = cursor.getColumnIndex(projection[0]);
                cursor.moveToFirst();
                path = cursor.getString(column_index);
                LogUtil.v(com.yline.photo.User.TAG_PHOTO, "path = " + path);
                return path;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                if (null != cursor)
                {
                    cursor.close();
                }
            }
        }
        return null;
    }
}
