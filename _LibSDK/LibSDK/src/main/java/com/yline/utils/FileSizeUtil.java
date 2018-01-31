package com.yline.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StatFs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * 获取文件大小
 *
 * @author yline 2018/1/31 -- 15:31
 * @version 1.0.0
 */
public class FileSizeUtil {
    private static final int ERROR_SIZE = -1;

    private static final int SUFFIX_TYPE_B = 1;// 获取文件大小单位为B的double值
    private static final int SUFFIX_TYPE_KB = 2;// 获取文件大小单位为KB的double值
    private static final int SUFFIX_TYPE_MB = 3;// 获取文件大小单位为MB的double值
    private static final int SUFFIX_TYPE_GB = 4;// 获取文件大小单位为GB的double值

    /**
     * 获取SD卡，所有的容量，单位byte
     *
     * @param topPath FileUtil.getPathTop();
     * @return 0 if SDcard cannot be use
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getFileBlockSize(String topPath) {
        if (FileUtil.isSDCardEnable()) {
            StatFs stat = new StatFs(topPath);

            long blockSize;
            long blockCount;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getBlockSizeLong();
                blockCount = stat.getBlockCountLong() - 4;
            } else {
                blockSize = stat.getBlockSize();
                blockCount = stat.getBlockCount() - 4;
            }

            return blockCount * blockSize;
        }
        return 0;
    }

    /**
     * 获取SD卡，剩余的容量，单位byte
     *
     * @param topPath FileUtil.getPathTop();
     * @return 0 if SDCard cannot be use
     */
    public static long getFileAvailableSize(String topPath) {
        if (FileUtil.isSDCardEnable()) {
            StatFs stat = new StatFs(topPath);

            long blockSize = 0;
            long availableBlocks = 0;
            // 版本判断
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getBlockSizeLong();
                availableBlocks = stat.getAvailableBlocksLong() - 4;
            } else {
                blockSize = stat.getBlockSize();
                availableBlocks = stat.getAvailableBlocks() - 4;
            }

            return availableBlocks * blockSize;
        }
        return 0;
    }

    /**
     * 格式化大小
     *
     * @param size 大小
     * @return 字符串形式的大小，即：带单位
     */
    public static String formatFileAutoSize(long size) {
        DecimalFormat df = new DecimalFormat("0.00");
        String fileSizeStr = "0.00B";

        if (ERROR_SIZE == size) {
            return fileSizeStr;
        }

        if (size < 1024) {
            fileSizeStr = df.format(size) + "B";
        } else if (size < 1048576) {
            fileSizeStr = df.format(size / 1024.0) + "KB";
        } else if (size < 1073741824) {
            fileSizeStr = df.format(size / 1048576.0) + "MB";
        } else if (size < 1099511627776l) {
            fileSizeStr = df.format(size / 1073741824.0) + "GB";
        } else {
            fileSizeStr = df.format(size / 1099511627776.0) + "TB";
        }

        return fileSizeStr;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS    文件大小
     * @param sizeType 单位类型
     * @return 转换后的大小
     */
    public static double formatFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("0.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SUFFIX_TYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SUFFIX_TYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SUFFIX_TYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SUFFIX_TYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    /**
     * 获取文件夹或文件的大小
     *
     * @param file 文件或文件夹
     * @return 大小
     */
    public static long getFileOrDirAutoSize(File file) {
        long blockSize = 0;
        if (null != file && file.isDirectory()) {
            blockSize = getDirSize(file);
        } else {
            blockSize = getFileSize(file);
        }

        return blockSize;
    }

    /**
     * 读取文件的大小
     *
     * @param file 目标文件夹
     * @return 大小
     */
    public static long getFileSize(File file) {
        if (null != file && file.exists()) {
            FileInputStream fis = null;

            try {
                fis = new FileInputStream(file);
                return fis.available();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return ERROR_SIZE;
            } catch (IOException e) {
                e.printStackTrace();
                return ERROR_SIZE;
            } finally {
                if (null != fis) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return ERROR_SIZE;
    }

    /**
     * 获取指定文件夹
     *
     * @param file 目标文件夹
     * @return 磁盘大小
     */
    public static long getDirSize(File file) {
        long size = 0;
        if (null != file) {
            File fileList[] = file.listFiles();
            if (null != fileList) {
                for (int i = 0; i < fileList.length; i++) {
                    if (fileList[i].isDirectory()) {
                        size += getDirSize(fileList[i]);
                    } else {
                        size += getFileSize(fileList[i]);
                    }
                }
            }
        }
        return size;
    }

    public static int getErrorSize() {
        return ERROR_SIZE;
    }

    public static int getSuffixTypeB() {
        return SUFFIX_TYPE_B;
    }

    public static int getSuffixTypeKb() {
        return SUFFIX_TYPE_KB;
    }

    public static int getSuffixTypeMb() {
        return SUFFIX_TYPE_MB;
    }

    public static int getSuffixTypeGb() {
        return SUFFIX_TYPE_GB;
    }
}
