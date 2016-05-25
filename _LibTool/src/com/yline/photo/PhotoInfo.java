package com.yline.photo;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.yline.lib.utils.ScreenUtils;
import com.yline.utils.LogUtil;

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
            
            Cursor cursor = null;
            try
            {
                cursor = loader.loadInBackground();
                int column_index = cursor.getColumnIndex(projection[0]);
                cursor.moveToFirst();
                path = cursor.getString(column_index);
                LogUtil.v(com.yline.photo.User.TAG_PHOTO, "path = " + path);
                return path;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (null != cursor)
                {
                    cursor.close();
                }
            }
        }
        return null;
    }
    
    public void showPictureFromFileName(Context context, String filename, ImageView imageView)
    {
        LogUtil.v(com.yline.photo.User.TAG_PHOTO, "showPictureFromFileName start");
        
        // 图片解析的 配置 参数
        BitmapFactory.Options opts = getPictureScaleFromFileName(context, filename);
        
        // 得到图片,显示缩放图片(这个耗费的资源挺多的)
        Bitmap bitmap = BitmapFactory.decodeFile(filename, opts);
        
        imageView.setImageBitmap(bitmap);
        
        LogUtil.v(com.yline.photo.User.TAG_PHOTO, "showPictureFromFileName end");
    }
    
    /**
     * 设置   回显图片 缩略图
     * @param context
     * @param filename  文件路径
     * @return
     */
    private Options getPictureScaleFromFileName(Context context, String filename)
    {
        // 图片解析的 配置 参数
        BitmapFactory.Options opts = new Options();
        
        // 不去真的解析 图片，只是获取图片头部信息，宽高
        opts.inJustDecodeBounds = true;
        
        // 得到图片,显示缩放图片
        BitmapFactory.decodeFile(filename, opts);
        
        // 获取图片宽高
        int imageHeight = opts.outHeight;
        int imageWidth = opts.outWidth;
        LogUtil.v(com.yline.photo.User.TAG_PHOTO, "imageWidth = " + imageWidth + ",imageHeight = " + imageHeight);
        
        // 计算缩放的比例
        float scaleX = imageWidth * 1.0f / ScreenUtils.getScreenWidth(context);
        float scaleY = imageHeight * 1.0f / ScreenUtils.getScreenHeight(context);
        
        int scale = 1;
        LogUtil.v(com.yline.photo.User.TAG_PHOTO, "scaleX = " + scaleX + ",scaleY = " + scaleY);
        if (scaleX > scaleY && scaleX > 1)
        {
            scale = (int)scaleX + 1;
        }
        else if (scaleY > scaleX && scaleY > 1)
        {
            scale = (int)scaleY + 1;
        }
        
        // 设置 可以解析图片
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = (int)scale; // 设置采样频率
        
        return opts;
    }
}
