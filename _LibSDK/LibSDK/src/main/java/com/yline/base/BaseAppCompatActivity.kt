package com.yline.base

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.yline.application.BaseApplication
import com.yline.log.LogUtil
import com.yline.utils.PermissionUtil

/**
 * @author yline 2016/9/4 -- 17:57
 * @version 1.0.0
 */
open class BaseAppCompatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApplication.addActivity(this)
        PermissionUtil.request(this, PermissionUtil.REQUEST_CODE_PERMISSION, *initRequestPermission())
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val result = PermissionUtil.getPermissionGranted(PermissionUtil.REQUEST_CODE_PERMISSION, requestCode, permissions, grantResults)
        LogUtil.v(PermissionUtil.TAG_HANDLE_PERMISSION + ", " + result.toString())
    }

    /**
     * 初始化需要的全选
     *
     * @return 默认需要的权限，数组
     */
    protected fun initRequestPermission(): Array<String> {
        return arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseApplication.removeActivity(this)
    }
}
