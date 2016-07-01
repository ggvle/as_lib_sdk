package com.yline.application;

import java.io.File;

/**
 * 在Application中配置父类所需要的配置选项
 * simple introduction
 *
 * @author YLine 2016-5-27 -> 上午7:28:17
 * @version
 */
public class AppConfig
{
    private boolean isLog = true;
    
    private boolean isLogToFile = true;
    
    private boolean isLogLocation = true;
    
    private String fileLogPath = "libsdk" + File.separator;
    
    public String getFileLogPath()
    {
        return fileLogPath;
    }
    
    /**
     * @param fileLogPath 日志目录,default is "libsdk"
     */
    public void setFileLogPath(String fileLogPath)
    {
        if (!fileLogPath.endsWith(File.separator))
        {
            this.fileLogPath = fileLogPath;
        }
        else
        {
            this.fileLogPath = fileLogPath + File.separator;
        }
    }
    
    public boolean isLog()
    {
        return isLog;
    }
    
    /** 
     * @param isLog 日志开关, default is true
     */
    public void setLog(boolean isLog)
    {
        this.isLog = isLog;
    }
    
    public boolean isLogToFile()
    {
        return isLogToFile;
    }
    
    /**
     * @param isLogToFile 日志是否写到文件, default is true
     */
    public void setLogToFile(boolean isLogToFile)
    {
        this.isLogToFile = isLogToFile;
    }
    
    public boolean isLogLocation()
    {
        return isLogLocation;
    }
    
    /**
     * @param isLogLocation 日志是否包含定位信息,default is true
     */
    public void setLogLocation(boolean isLogLocation)
    {
        this.isLogLocation = isLogLocation;
    }
    
    @Override
    public String toString()
    {
        return "AppConfig [isLog=" + isLog + ", isLogToFile=" + isLogToFile + ", isLogLocation=" + isLogLocation
            + ", fileLogPath=" + fileLogPath + "]";
    }
    
}
