package com.yline.base

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

import com.yline.application.BaseApplication
import com.yline.log.LogUtil
import com.yline.utils.PermissionUtil

/**
 * simple introduction
 *
 * @author YLine 2016-5-25 - 上午7:32:58
 */
open class BaseFragmentActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        BaseApplication.addActivity(this)
        super.onCreate(savedInstanceState)
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
