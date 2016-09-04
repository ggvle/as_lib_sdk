package com.yline.application;

import java.io.File;

/**
 * 在Application中配置父类所需要的配置选项
 * simple introduction
 *
 * @author YLine 2016-5-27 -> 上午7:28:17
 */
public class SDKConfig
{
	/** 文件保存父路径 */
	private String fileParentPath = "_yline" + File.separator;

	private boolean isLog = true;

	private boolean isLogToFile = true;

	private boolean isLogLocation = true;

	private String logFilePath = "libsdk" + File.separator;

	public String getFileParentPath()
	{
		return fileParentPath;
	}

	/** 文件保存父路径 */
	public void setFileParentPath(String fileParentPath)
	{
		if (fileParentPath.endsWith(File.separator))
		{
			this.fileParentPath = fileParentPath;
		}
		else
		{
			this.fileParentPath = fileParentPath + File.separator;
		}
	}


	public String getLogFilePath()
	{
		return logFilePath;
	}

	/**
	 * @param logFilePath 日志目录,default is "libsdk"
	 */
	public void setLogFilePath(String logFilePath)
	{
		if (logFilePath.endsWith(File.separator))
		{
			this.logFilePath = logFilePath;
		}
		else
		{
			this.logFilePath = logFilePath + File.separator;
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
		return "SDKConfig{" +
				", isLog=" + isLog +
				", isLogToFile=" + isLogToFile +
				", isLogLocation=" + isLogLocation +
				", logFilePath='" + logFilePath + '\'' +
				'}';
	}
}
