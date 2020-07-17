package com.yline.utils

import android.annotation.TargetApi
import android.os.Build
import android.os.StatFs

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.text.DecimalFormat

/**
 * 获取文件大小
 *
 * @author yline 2018/1/31 -- 15:31
 * @version 1.0.0
 */
object FileSizeUtil {
    val errorSize = -1

    val suffixTypeB = 1// 获取文件大小单位为B的double值
    val suffixTypeKb = 2// 获取文件大小单位为KB的double值
    val suffixTypeMb = 3// 获取文件大小单位为MB的double值
    val suffixTypeGb = 4// 获取文件大小单位为GB的double值

    /**
     * 获取SD卡，所有的容量，单位byte
     *
     * @param topPath FileUtil.getPathTop();
     * @return 0 if SDcard cannot be use
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun getFileBlockSize(topPath: String): Long {
        if (FileUtil.isSDCardEnable) {
            val stat = StatFs(topPath)

            val blockSize: Long
            val blockCount: Long
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.blockSizeLong
                blockCount = stat.blockCountLong - 4
            } else {
                blockSize = stat.blockSize.toLong()
                blockCount = (stat.blockCount - 4).toLong()
            }

            return blockCount * blockSize
        }
        return 0
    }

    /**
     * 获取SD卡，剩余的容量，单位byte
     *
     * @param topPath FileUtil.getPathTop();
     * @return 0 if SDCard cannot be use
     */
    fun getFileAvailableSize(topPath: String): Long {
        if (FileUtil.isSDCardEnable) {
            val stat = StatFs(topPath)

            var blockSize: Long = 0
            var availableBlocks: Long = 0
            // 版本判断
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.blockSizeLong
                availableBlocks = stat.availableBlocksLong - 4
            } else {
                blockSize = stat.blockSize.toLong()
                availableBlocks = (stat.availableBlocks - 4).toLong()
            }

            return availableBlocks * blockSize
        }
        return 0
    }

    /**
     * 格式化大小
     *
     * @param size 大小
     * @return 字符串形式的大小，即：带单位
     */
    fun formatFileAutoSize(size: Long): String {
        val df = DecimalFormat("0.00")
        var fileSizeStr = "0.00B"

        if (errorSize.toLong() == size) {
            return fileSizeStr
        }

        if (size < 1024) {
            fileSizeStr = df.format(size) + "B"
        } else if (size < 1048576) {
            fileSizeStr = df.format(size / 1024.0) + "KB"
        } else if (size < 1073741824) {
            fileSizeStr = df.format(size / 1048576.0) + "MB"
        } else if (size < 1099511627776L) {
            fileSizeStr = df.format(size / 1073741824.0) + "GB"
        } else {
            fileSizeStr = df.format(size / 1099511627776.0) + "TB"
        }

        return fileSizeStr
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS    文件大小
     * @param sizeType 单位类型
     * @return 转换后的大小
     */
    fun formatFileSize(fileS: Long, sizeType: Int): Double {
        val df = DecimalFormat("0.00")
        var fileSizeLong = 0.0
        when (sizeType) {
            suffixTypeB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble()))
            suffixTypeKb -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1024))
            suffixTypeMb -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1048576))
            suffixTypeGb -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1073741824))
            else -> {
            }
        }
        return fileSizeLong
    }

    /**
     * 获取文件夹或文件的大小
     *
     * @param file 文件或文件夹
     * @return 大小
     */
    fun getFileOrDirAutoSize(file: File?): Long {
        var blockSize: Long = 0
        if (null != file && file.isDirectory) {
            blockSize = getDirSize(file)
        } else {
            blockSize = getFileSize(file)
        }

        return blockSize
    }

    /**
     * 读取文件的大小
     *
     * @param file 目标文件夹
     * @return 大小
     */
    fun getFileSize(file: File?): Long {
        if (null != file && file.exists()) {
            var fis: FileInputStream? = null

            try {
                fis = FileInputStream(file)
                return fis.available().toLong()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                return errorSize.toLong()
            } catch (e: IOException) {
                e.printStackTrace()
                return errorSize.toLong()
            } finally {
                if (null != fis) {
                    try {
                        fis.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }

        return errorSize.toLong()
    }

    /**
     * 获取指定文件夹
     *
     * @param file 目标文件夹
     * @return 磁盘大小
     */
    fun getDirSize(file: File?): Long {
        var size: Long = 0
        if (null != file) {
            val fileList = file.listFiles()
            if (null != fileList) {
                for (i in fileList.indices) {
                    if (fileList[i].isDirectory) {
                        size += getDirSize(fileList[i])
                    } else {
                        size += getFileSize(fileList[i])
                    }
                }
            }
        }
        return size
    }
}
