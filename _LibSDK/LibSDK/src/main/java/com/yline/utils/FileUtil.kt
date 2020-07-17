package com.yline.utils

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils

import java.io.File
import java.io.FileFilter
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URI
import java.util.Comparator

/**
 * 目前提供给 LogFileUtil准备
 * simple introduction
 *
 * @author YLine 2016-5-25 - 上午8:06:08
 */
object FileUtil {
    val hiddenPrefix = "."
    /**
     * File and folder comparator. TODO Expose sorting option method
     */
    private val sComparator = Comparator<File> { f1, f2 ->
        // Sort alphabetically by lower case, which is much cleaner
        f1.name.toLowerCase().compareTo(f2.name.toLowerCase())
    }
    private val sFileFilter = FileFilter { file -> file.isFile }
    private val sDirFilter = FileFilter { file -> file.isDirectory }

    /**
     * File (not directories) filter.
     */
    private val sFilePointFilter = FileFilter { file ->
        val fileName = file.name
        // Return files only (not directories) and skip hidden files
        file.isFile && !fileName.startsWith(hiddenPrefix)
    }
    /**
     * Folder (directories) filter.
     */
    private val sDirPointFilter = FileFilter { file ->
        val fileName = file.name
        // Return directories only and skip hidden directories
        file.isDirectory && !fileName.startsWith(hiddenPrefix)
    }

    val isSDCardEnable: Boolean
        get() = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

    /**
     * 获取系统存储路径
     *
     * @return /System/
     */
    val pathRoot: String
        get() = Environment.getRootDirectory().absolutePath + File.separator

    /**
     * 获取内置sd卡最上层路径
     *
     * @return /storage/emulated/0/ or null
     */
    val pathTop: String?
        get() = if (isSDCardEnable) {
            Environment.getExternalStorageDirectory().absolutePath + File.separator
        } else {
            null
        }

    /**
     * 获取内置sd卡, 最上层路径
     *
     * @return /storage/emulated/0/ or null
     */
    val fileTop: File?
        get() = if (isSDCardEnable) {
            Environment.getExternalStorageDirectory()
        } else {
            null
        }

    /**
     * 获取内置sd卡, 最上层路径
     *
     * @param fileName 文件名 such as "yline.txt"
     * @return /storage/emulated/0/ or null
     */
    fun getFileTop(fileName: String): File? {
        return if (isSDCardEnable) {
            Environment.getExternalStoragePublicDirectory(fileName)
        } else {
            null
        }
    }

    /**
     * 获取内置sd卡, 最上层路径
     *
     * @param dirName  文件夹名称 such as "yline"
     * @param fileName 文件名 such as "yline.txt"
     * @return /storage/emulated/0/ or null
     */
    fun getFileTop(dirName: String, fileName: String): File? {
        if (isSDCardEnable) {
            val dirFile = Environment.getExternalStoragePublicDirectory(dirName)
            val dirPath = dirFile?.absolutePath
            return FileUtil.create(dirPath, fileName)
        } else {
            return null
        }
    }

    /**
     * @param context 上下文
     * @param dirName 文件夹名称 such as "yline"
     * @return /storage/emulated/0/Android/data/包名/files/ + dirName
     */
    fun getFileExternalDir(context: Context, dirName: String): File? {
        return context.getExternalFilesDir(dirName)
    }

    /**
     * @param context  上下文
     * @param dirName  文件夹名称 such as "yline"
     * @param fileName 文件名 such as "yline.txt"
     * @return /storage/emulated/0/Android/data/包名/files/ + dirName/ + fileName
     */
    fun getFileExternal(context: Context, dirName: String, fileName: String): File? {
        val dirFile = context.getExternalFilesDir(dirName)
        val dirPath = dirFile?.absolutePath
        return FileUtil.create(dirPath, fileName)
    }

    /**
     * @param context 上下文
     * @return /storage/emulated/0/Android/data/包名/cache
     */
    fun getFileExternalCacheDir(context: Context): File? {
        return context.externalCacheDir
    }

    /**
     * @param context 上下文
     * @return /data/data/包名/files
     */
    fun getFileInner(context: Context): File {
        return context.filesDir
    }

    /**
     * @param context 上下文
     * @return /data/data/包名/cache
     */
    fun getFileInnerCache(context: Context): File {
        return context.cacheDir
    }

    /**
     * 读取 Assets 中的 Stream文件
     *
     * @param context  上下文
     * @param fileName 文件名 such as "yline"
     * @return 文件流
     * @throws IOException 打开文件I/O异常
     */
    @Throws(IOException::class)
    fun getStreamAssets(context: Context, fileName: String): InputStream {
        return context.assets.open(fileName)
    }

    /**
     * android.permission.WRITE_EXTERNAL_STORAGE
     * 构建一个文件,真实的创建
     *
     * @param dirPath  文件的目录 such as /storage/emulated/0/Yline/Log/
     * @param fileName 文件名     such as log.txt
     * @return file or null
     */
    fun create(dirPath: String?, fileName: String): File? {
        return if (TextUtils.isEmpty(dirPath) || TextUtils.isEmpty(fileName)) {
            null
        } else create(File(dirPath!!), fileName)

    }

    /**
     * android.permission.WRITE_EXTERNAL_STORAGE
     * 构建一个文件,真实的创建
     *
     * @param dirFile  文件的目录 such as /storage/emulated/0/Yline/Log/
     * @param fileName 文件名     such as log.txt
     * @return file or null
     */
    fun create(dirFile: File?, fileName: String): File? {
        if (null == dirFile || TextUtils.isEmpty(fileName)) {
            return null
        }

        if (!dirFile.exists() || dirFile.isFile) {
            dirFile.mkdirs()
        }

        val file = File(dirFile, fileName)
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    return file
                }
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }

        } else {
            return file
        }

        return null
    }

    /**
     * android.permission.WRITE_EXTERNAL_STORAGE
     * 创建一个文件夹
     *
     * @param dirPath such as /storage/emulated/0/Yline/Log/
     * @return file or null
     */
    fun createDir(dirPath: String): File? {
        if (TextUtils.isEmpty(dirPath)) {
            return null
        }

        val dirFile = File(dirPath)
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs()) {
                return null
            }
        }
        return dirFile
    }

    /**
     * 是否存在该文件
     *
     * @param dir  文件目录
     * @param name 文件名称
     * @return false(参数错误 、 文件不存在)
     */
    fun isExist(dir: File?, name: String): Boolean {
        return if (null == dir || TextUtils.isEmpty(name)) {
            false
        } else File(dir, name).exists()

    }

    /**
     * android.permission.WRITE_EXTERNAL_STORAGE
     * 删除一个文件
     *
     * @param dir  文件的目录
     * @param name 文件名  such as log.txt
     * @return false(参数错误 、 不存在该文件 、 删除失败)
     */
    fun delete(dir: File?, name: String): Boolean {
        if (null == dir || TextUtils.isEmpty(name)) {
            return false
        }

        val file = File(dir, name)
        return if (file.exists()) {
            file.delete()
        } else false

    }

    /**
     * 重命名一个文件
     *
     * @param dir     文件的目录
     * @param oldName 文件名  such as log0.txt
     * @param newName 文件名  such as log1.txt
     * @return false(参数错误 、 不存在该文件 、 重命名失败)
     */
    fun rename(dir: File?, oldName: String, newName: String): Boolean {
        if (null == dir || TextUtils.isEmpty(oldName)) {
            return false
        }

        val oldFile = File(dir, oldName)
        // 不存在该文件,即算作命名成功
        if (oldFile.exists()) {
            if (TextUtils.isEmpty(newName)) {
                return false
            }
            val newFile = File(dir, newName)
            return oldFile.renameTo(newFile)
        }

        return false
    }

    /**
     * @param file    文件
     * @param content 内容
     * @return false(写入失败, 写入工具关闭失败)
     */
    fun write(file: File, content: String): Boolean {
        try {
            val fileOutputStream = FileOutputStream(file, true)
            IOUtil.write(content + "\n", fileOutputStream)
            IOUtil.close(fileOutputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }

        return true
    }

    fun getsComparator(): Comparator<File> {
        return sComparator
    }

    fun getsFilePointFilter(): FileFilter {
        return sFilePointFilter
    }

    fun getsDirPointFilter(): FileFilter {
        return sDirPointFilter
    }

    fun getsFileFilter(): FileFilter {
        return sFileFilter
    }

    fun getsDirFilter(): FileFilter {
        return sDirFilter
    }

    /**
     * uri 路径 转成 文件路径
     * 测试结果: 跳转图片ok; 跳转文件管理es ok; 跳转系统缩略图 failed
     *
     * @param context 上下文
     * @param uri     文件路径
     * @return
     */
    fun uri2File(context: Context, uri: Uri?): String? {
        if (null == uri) {
            return null
        }

        val scheme = uri.scheme
        var data: String? = null
        if (scheme == null) {
            data = uri.path
        } else if (ContentResolver.SCHEME_FILE == scheme) {
            data = uri.path
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val cursor = context.contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DATA), null, null, null)
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    if (index > -1) {
                        data = cursor.getString(index)
                    }
                }
                cursor.close()
            }
        }
        return data
    }

    fun file2Uri(file: File): URI {
        return file.toURI()
    }
}
