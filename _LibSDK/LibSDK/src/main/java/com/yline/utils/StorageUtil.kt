package com.yline.utils

import android.content.Context
import android.os.Environment
import android.text.TextUtils

import com.yline.application.SDKManager

import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

/**
 * 储存相关的工具类
 *
 * @author yline 2019/8/14 -- 18:19
 */
object StorageUtil {
    private var sPath: String? = null  //隐藏文件
    private var sCachePath: String? = null //sd卡公开文件

    /**
     * 存放配置
     * 首先去sd卡路径，如果没有sd卡，则取内存路径，两者都取不到，返回null。
     * 获取的路径，可能文件夹并没有创建
     *
     * @return /storage/emulated/0/ or null
     */
    @Synchronized
    fun getPath(context: Context): String {
        if (sPath == null) {
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
                sPath = Environment.getExternalStorageDirectory().path
            } else {
                sPath = context.filesDir.absolutePath
            }

            if (!TextUtils.isEmpty(sPath)) {
                sPath += File.separator + ".yline" + File.separator
            }
        }
        return sPath!!
    }

    /**
     * 存放缓存的图片、数据等
     */
    @Synchronized
    fun getCachePath(context: Context): String {
        if (sCachePath == null) {
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
                sCachePath = Environment.getExternalStorageDirectory().path
            } else {
                sCachePath = context.filesDir.absolutePath
            }

            if (!TextUtils.isEmpty(sCachePath)) {
                sCachePath += File.separator + "yline" + File.separator
            }
        }
        return sCachePath!!
    }

    /**
     * 从路径中读取bytes
     *
     * @param filePath 文件路径
     * @return bytes数组，null if Exception
     */
    fun path2Bytes(filePath: String): ByteArray? {
        var fis: FileInputStream? = null
        var bos: ByteArrayOutputStream? = null
        try {
            fis = FileInputStream(filePath)
            bos = ByteArrayOutputStream()

            val temp = ByteArray(1024)
            var n: Int
            n = fis.read(temp)
            while (n != -1) {
                bos.write(temp, 0, n)

                n = fis.read(temp)
            }

            return bos.toByteArray()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bos?.close()

                fis?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return null
    }

    /**
     * bytes转文件路径
     *
     * @param fileBytes bytes内容
     * @return 文件路径，null if Exception
     */
    fun bytes2Path(fileBytes: ByteArray): String? {
        val path = getPath(SDKManager.getApplication())

        // 创建文件
        val file = FileUtil.create(path, System.currentTimeMillis().toString() + ".jpg")
                ?: return null

        // 内容，转，文件，并返回文件路径
        return bytes2Path(fileBytes, file.absolutePath)
    }

    /**
     * bytes转文件路径
     *
     * @param fileBytes bytes内容
     * @param filePath  文件路径，要求路径上文件必须存在
     * @return 文件路径，null if Exception
     */
    private fun bytes2Path(fileBytes: ByteArray, filePath: String): String? {
        var bos: BufferedOutputStream? = null
        var fos: FileOutputStream? = null

        try {
            fos = FileOutputStream(filePath)
            bos = BufferedOutputStream(fos)
            bos.write(fileBytes)

            return filePath
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bos?.close()

                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        return null
    }
}
