package com.yline.application;

import java.io.File;

/**
 * 在Application中配置父类所需要的配置选项
 * simple introduction
 *
 * @author YLine 2016-5-27 -> 上午7:28:17
 * @version
 */
public class BaseConfig
{
    private boolean isLog = true;
    
    private boolean isLogToFile = true;
    
    private boolean isLogLocation = true;
    
    private String fileLogPath = "libsdk" + File.separator;
    
    private boolean isTimerServiceOpen = true;
    
    public String getFileLogPath()
    {
        return fileLogPath;
    }
    
    /**
     * @param fileLogPath 日志目录,such as "libsdk" + File.separator
     */
    public void setFileLogPath(String fileLogPath)
    {
        this.fileLogPath = fileLogPath;
    }
    
    public boolean isLog()
    {
        return isLog;
    }
    
    /**
     * @param isLog 日志开关, such as true
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
     * @param isLogToFile 日志是否写到文件, such as true
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
     * @param isLogLocation 日志是否包含定位信息,such as true
     */
    public void setLogLocation(boolean isLogLocation)
    {
        this.isLogLocation = isLogLocation;
    }
    
    public boolean isTimerServiceOpen()
    {
        return isTimerServiceOpen;
    }
    
    /**
     * 记得注册表MAinfest中注册
     * @param isTimerServiceOpen 伴随服务是否开启,such as true
     */
    public void setTimerServiceOpen(boolean isTimerServiceOpen)
    {
        this.isTimerServiceOpen = isTimerServiceOpen;
    }
    
}
