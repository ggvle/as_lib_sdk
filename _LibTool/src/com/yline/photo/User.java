package com.yline.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import com.yline.lib.utils.LogUtil;

/**
 * simple introduction
 * 读取图片信息ExifInterface
 * android.permission.WRITE_EXTERNAL_STORAGE
 *
 * @author YLine 2016-5-4 -> 上午7:35:13
 * @version 
 */
public class User
{
    public static final String TAG_PHOTO = "photo";
    
    private PhotoInfo          mPhotoInfo;
    
    /**
     * 跳转 到 选择界面
     * @param activity
     */
    public void testBackBefore(Activity activity)
    {
        mPhotoInfo = new PhotoInfo();
        mPhotoInfo.intentToAlbum(activity);
    }
    
    /**
     * 这个用于  onActivityResult
     */
    public void testBack(Context context, ImageView imageView, int requestCode, int resultCode, Intent data)
    {
        Uri uri = mPhotoInfo.intentBackOfAlbum(requestCode, data);
        LogUtil.v(TAG_PHOTO, "uri = " + uri);
        
        String fileName = mPhotoInfo.Uri2Path(context, uri);
        LogUtil.v(TAG_PHOTO, "fileName = " + fileName);
        
        // 拿到文件名之后
        mPhotoInfo.getPhotoExifInfo(fileName);
        
        // 拿到文件名之后,显示缩略图
        mPhotoInfo.showPictureFromFileName(context, fileName, imageView);
    }
    
    private PhotoDrawInstance instance;
    
    public void testDraw(Context context, ImageView imageView)
    {
        instance = new PhotoDrawInstance();
        instance.draw(context, imageView);
    }
    
    public void testSavePic()
    {
        instance.save();
    }
}
