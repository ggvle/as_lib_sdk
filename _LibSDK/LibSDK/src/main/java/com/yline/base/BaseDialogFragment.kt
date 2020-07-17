package com.yline.base

import android.os.Bundle
import androidx.fragment.app.DialogFragment

import com.yline.application.BaseApplication

class BaseDialogFragment : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApplication.addFragmentForRecord(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseApplication.removeFragmentForRecord(this)
    }
}
