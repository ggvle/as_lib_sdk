package com.yline.utils;

import java.io.File;

public class User
{
    private static final String tag = "base";
    
    public void testFileUtil()
    {
        LogUtil.v(tag, "FileUtil.getPath() = " + FileUtil.getPath());
        
        File fileDir = FileUtil.createFileDir(FileUtil.getPath() + "YlineTest/Log/");
        LogUtil.d(tag, "createFileDir success");
        
        File file = FileUtil.createFile(fileDir, "log.txt");
        LogUtil.i(tag, "createFile success");
        
        // 太多了,就会黑屏,因为大量占用了文件资源
        for (int i = 0; i < 1024; i++)
        {
            FileUtil.writeToFile(file, "content i = " + i);
        }
        
        int size = FileUtil.getFileSize(file);
        LogUtil.w(tag, "getFileSize size = " + size);
        
        boolean renameResult = FileUtil.renameFile(fileDir, "log.txt", "log1.txt");
        LogUtil.w(tag, "renameFile renameResult = " + renameResult);
        
        boolean deleteResult = FileUtil.deleteFile(fileDir, "log.txt");
        LogUtil.e(tag, "deleteFile deleteResult = " + deleteResult);
    }
}
