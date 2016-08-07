package com.demo.utils;

import java.io.File;

import com.yline.utils.FileUtil;
import com.yline.utils.LogUtil;

public class FileUtilUser
{
    private static final String TAG = "FileUtilUser";
    
    public void test()
    {
        LogUtil.v(TAG + " -> FileUtil.getPath() = " + FileUtil.getPath());
        
        File fileDir = FileUtil.createFileDir(FileUtil.getPath() + "YlineTest/Log/");
        LogUtil.d(TAG + " -> createFileDir success");
        
        File file = FileUtil.createFile(fileDir, "log.txt");
        LogUtil.i(TAG + " -> createFile success");
        
        // 太多了,就会黑屏,因为大量占用了文件资源
        for (int i = 0; i < 1024; i++)
        {
            FileUtil.writeToFile(file, "content i = " + i);
        }
        
        int size = FileUtil.getFileSize(file);
        LogUtil.w(TAG + " -> getFileSize size = " + size);
        
        boolean renameResult = FileUtil.renameFile(fileDir, "log.txt", "log1.txt");
        LogUtil.w(TAG + " -> renameFile renameResult = " + renameResult);
        
        boolean deleteResult = FileUtil.deleteFile(fileDir, "log.txt");
        LogUtil.e(TAG + " -> deleteFile deleteResult = " + deleteResult);
    }
}
