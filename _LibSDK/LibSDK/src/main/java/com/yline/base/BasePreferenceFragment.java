package com.yline.base;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.yline.application.BaseApplication;

/**
 * 添加进入Application管理
 * 注意:这是一个 非V4包 的Fragment
 *
 * @author yline 2017/2/25 -- 11:49
 * @version 1.0.0
 */
public class BasePreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.addFragmentForRecordFew(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.removeFragmentForRecordFew(this);
    }
}
