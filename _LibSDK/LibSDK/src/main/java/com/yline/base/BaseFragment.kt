package com.yline.base

import android.os.Bundle
import androidx.fragment.app.Fragment

import com.yline.application.BaseApplication

/**
 * simple introduction
 *
 * @author YLine 2016-5-25 - 上午7:32:43
 */
open class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApplication.addFragmentForRecord(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseApplication.removeFragmentForRecord(this)
    }
}
