package com.yline.photo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.yline.lib.utils.LogUtil;
import com.yline.lib.utils.ScreenUtils;
import com.yline.lib.utils.UILayoutUtils;

/**
 * simple introduction
 * android.permission.WRITE_EXTERNAL_STORAGE
 *
 * @author YLine 2016-5-5 -> 上午7:27:48
 * @version 
 */
public class PhotoDrawInstance
{
    private Bitmap drawBitmap;
    
    private Paint  mPaint;
    
    private Canvas mCanvas;
    
    private int    startX, startY;
    
    public void draw(Context context, final ImageView imageView)
    {
        // 确定画的宽高
        final int width = ScreenUtils.getScreenWidth(context) * 2 / 3;
        final int height = ScreenUtils.getScreenWidth(context) * 2 / 3;
        
        // 画笔
        mPaint = new Paint();
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.GREEN);
        
        // imageView大小
        UILayoutUtils.setLayout(imageView, width, height);
        
        // 画布 + bitmap
        drawBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        // canvas 背景色
        mCanvas = new Canvas(drawBitmap);
        mCanvas.drawColor(Color.WHITE);
        
        // 初次显现到界面上
        imageView.setImageBitmap(drawBitmap);
        
        LogUtil.v(com.yline.photo.User.TAG_PHOTO, "draw start" + ",image width = " + imageView.getWidth()
            + ",image height = " + imageView.getHeight());
        LogUtil.v(com.yline.photo.User.TAG_PHOTO, "bmp width = " + drawBitmap.getWidth() + ",bmp height = "
            + drawBitmap.getHeight());
        
        imageView.setOnTouchListener(new View.OnTouchListener()
        {
            
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        LogUtil.v(com.yline.photo.User.TAG_PHOTO, "draw down");
                        
                        startX = (int)event.getX();
                        startY = (int)event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 从新更新画笔的 开始位置
                        mCanvas.drawLine(startX, startY, (int)event.getX(), (int)event.getY(), mPaint);
                        
                        startX = (int)event.getX();
                        startY = (int)event.getY();
                        
                        // 只有加上这句话,才保证了 时刻的刷新界面
                        imageView.setImageBitmap(drawBitmap);
                        break;
                    case MotionEvent.ACTION_UP:
                        LogUtil.v(com.yline.photo.User.TAG_PHOTO, "draw up");
                        break;
                }
                return true;
            }
        });
        
        LogUtil.v(com.yline.photo.User.TAG_PHOTO, "draw end");
    }
    
    /**
     * 保存文件到,目录下
     */
    public void save()
    {
        LogUtil.v(com.yline.photo.User.TAG_PHOTO, "save start");
        
        if (null != drawBitmap)
        {
            File file =
                new File(Environment.getExternalStorageDirectory(), "yline" + System.currentTimeMillis() + ".png");
            FileOutputStream stream = null;
            try
            {
                stream = new FileOutputStream(file);
                drawBitmap.compress(CompressFormat.PNG, 100, stream);
                stream.close();
            }
            catch (FileNotFoundException e)
            {
                LogUtil.e(com.yline.photo.User.TAG_PHOTO, "error", e);
            }
            catch (IOException e)
            {
                LogUtil.e(com.yline.photo.User.TAG_PHOTO, "error", e);
            }finally{
                try
                {
                    stream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            LogUtil.v(com.yline.photo.User.TAG_PHOTO, "save success end");
        }
        else
        {
            LogUtil.v(com.yline.photo.User.TAG_PHOTO, "bitmap is null,end");
        }
    }
}
