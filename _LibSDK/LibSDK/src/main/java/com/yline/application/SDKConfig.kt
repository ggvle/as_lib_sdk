package com.yline.application

/**
 * 在Application中配置父类所需要的配置选项
 * simple introduction
 *
 * @author YLine 2016-5-27 - 上午7:28:17
 */
class SDKConfig {
    /**
     * 打印日志工具,是否打印日志
     */
    var isUtilLog = true

    /**
     * 打印日志工具,是否打印日志到文件
     */
    var isUtilLogToFile = true

    /**
     * 打印日志工具,日志内容是否提供定位功能
     */
    var isUtilLogLocation = true

    /**
     * 打印日志工具,是否使用System.out.print打印日志
     * 正常的LogCat失效时，使用
     */
    var isUtilLogBySystem = false

    /**
     * SDK库工程(该工程)是否打印日志
     */
    var isSDKLog = true

    override fun toString(): String {
        return "SDKConfig{" +
                "isUtilLog=" + isUtilLog +
                ", isUtilLogToFile=" + isUtilLogToFile +
                ", isUtilLogLocation=" + isUtilLogLocation +
                ", isUtilLogBySystem=" + isUtilLogBySystem +
                ", isSDKLog=" + isSDKLog +
                '}'.toString()
    }
}
