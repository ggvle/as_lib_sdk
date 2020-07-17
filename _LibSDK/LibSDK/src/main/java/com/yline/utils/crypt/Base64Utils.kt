package com.yline.utils.crypt

import android.util.Base64

/**
 * 使用系统api
 *
 * @author yline 2018/8/28 -- 18:04
 */
object Base64Utils {
    fun encodeToString(data: ByteArray): String {
        return Base64.encodeToString(data, Base64.NO_WRAP)
    }

    fun encode(data: ByteArray): ByteArray {
        return Base64.encode(data, Base64.NO_WRAP)
    }

    fun decode(data: String): ByteArray {
        return Base64.decode(data, Base64.NO_WRAP)
    }

    fun decode(data: ByteArray): ByteArray {
        return Base64.decode(data, Base64.NO_WRAP)
    }
}
