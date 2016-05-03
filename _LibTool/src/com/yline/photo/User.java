package com.yline.photo;

import android.app.Activity;
import android.content.Intent;

/**
 * simple introduction
 *
 * @author YLine 2016-5-4 -> 上午7:35:13
 * @version 
 */
public class User
{
    public static final String TAG_PHOTO = "photo";
    
    private final String       fileName  = "";
    
    public void test(Activity activity)
    {
        PhotoInfo photoInfo = new PhotoInfo();
        photoInfo.getPhotoExifInfo(fileName);
        photoInfo.intentToAlbum(activity);
    }
    
    /**
     * 这个用于  onActivityResult
     */
    public void testBack(int requestCode, int resultCode, Intent data)
    {
        new PhotoInfo().intentBackOfAlbum(requestCode, data);
    }
}
