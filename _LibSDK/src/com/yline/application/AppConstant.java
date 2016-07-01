package com.yline.application;

import java.io.File;

/**
 * 底层库专用的常量
 * simple introduction
 */
public final class AppConstant
{
    // application 功能
    /** handler 吐丝 */
    public static final int HANDLER_TOAST = 1;
    
    // 其他
    /** 文件保存父路径 */
    public static final String FILE_PARENT_PATH = "_yline_lib" + File.separator;
    
    // tag
    /** sdk jar 包主线程tag */
    public static final String TAG_BASE = "LibSDK";
    
    /** sdk jar 包伴生服务 */
    public static final String TAG_APP_SERVICE = "LibSDKAppService";
}
