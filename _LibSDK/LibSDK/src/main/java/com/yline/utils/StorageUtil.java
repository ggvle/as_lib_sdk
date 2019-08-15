package com.yline.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.yline.application.SDKManager;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 储存相关的工具类
 *
 * @author yline 2019/8/14 -- 18:19
 */
public class StorageUtil {
    private static String sPath = null;  //隐藏文件
    private static String sCachePath = null; //sd卡公开文件

    /**
     * 存放配置
     * 首先去sd卡路径，如果没有sd卡，则取内存路径，两者都取不到，返回null。
     * 获取的路径，可能文件夹并没有创建
     *
     * @return /storage/emulated/0/ or null
     */
    public static synchronized String getPath(Context context) {
        if (sPath == null) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                sPath = Environment.getExternalStorageDirectory().getPath();
            } else {
                sPath = context.getFilesDir().getAbsolutePath();
            }

            if (!TextUtils.isEmpty(sPath)) {
                sPath += (File.separator + ".yline" + File.separator);
            }
        }
        return sPath;
    }

    /**
     * 存放缓存的图片、数据等
     */
    public static synchronized String getCachePath(Context context) {
        if (sCachePath == null) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                sCachePath = Environment.getExternalStorageDirectory().getPath();
            } else {
                sCachePath = context.getFilesDir().getAbsolutePath();
            }

            if (!TextUtils.isEmpty(sCachePath)) {
                sCachePath += (File.separator + "yline" + File.separator);
            }
        }
        return sCachePath;
    }

    /**
     * 从路径中读取bytes
     *
     * @param filePath 文件路径
     * @return bytes数组，null if Exception
     */
    public static byte[] path2Bytes(String filePath) {
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            fis = new FileInputStream(filePath);
            bos = new ByteArrayOutputStream();

            byte[] temp = new byte[1024];
            int n;
            while ((n = fis.read(temp)) != -1) {
                bos.write(temp, 0, n);
            }

            return bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bos) {
                    bos.close();
                }

                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * bytes转文件路径
     *
     * @param fileBytes bytes内容
     * @return 文件路径，null if Exception
     */
    public static String bytes2Path(byte[] fileBytes) {
        String path = getPath(SDKManager.getApplication());

        // 创建文件
        File file = FileUtil.create(path, System.currentTimeMillis() + ".jpg");
        if (null == file) {
            return null;
        }

        // 内容，转，文件，并返回文件路径
        return bytes2Path(fileBytes, file.getAbsolutePath());
    }

    /**
     * bytes转文件路径
     *
     * @param fileBytes bytes内容
     * @param filePath  文件路径，要求路径上文件必须存在
     * @return 文件路径，null if Exception
     */
    private static String bytes2Path(byte[] fileBytes, String filePath) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(filePath);
            bos = new BufferedOutputStream(fos);
            bos.write(fileBytes);

            return filePath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bos) {
                    bos.close();
                }

                if (null != fos) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
