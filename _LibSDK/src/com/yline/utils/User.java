package com.yline.utils;

import java.io.File;

/**
 * FileUtils 工具类使用案例
 * simple introduction
 *
 * @author YLine 2016-5-29 -> 上午8:49:46
 * @version
 */
public class User
{
    private static final String tag = "flieUtilsUser";
    
    public void testFileUtil()
    {
        LogUtil.v(tag, "FileUtil.getPath() = " + FileUtil.getPath());
        
        File fileDir = FileUtil.createFileDir(FileUtil.getPath() + "YlineTest/Log/");
        LogUtil.v(tag, "createFileDir success");
        
        File file = FileUtil.createFile(fileDir, "log.txt");
        LogUtil.i(tag, "createFile success");
        
        // 太多了,就会黑屏,因为大量占用了文件资源
        for (int i = 0; i < 1024; i++)
        {
            FileUtil.writeToFile(file, "content i = " + i);
        }
        
        int size = FileUtil.getFileSize(file);
        LogUtil.v(tag, "getFileSize size = " + size);
        
        boolean renameResult = FileUtil.renameFile(fileDir, "log.txt", "log1.txt");
        LogUtil.v(tag, "renameFile renameResult = " + renameResult);
        
        boolean deleteResult = FileUtil.deleteFile(fileDir, "log.txt");
        LogUtil.v(tag, "deleteFile deleteResult = " + deleteResult);
    }
}
