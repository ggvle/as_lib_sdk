package com.yline.base;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.yline.application.BaseApplication;

/**
 * @author yline 2017/11/20 -- 14:18
 * @version 1.0.0
 */
public class BaseListFragment extends ListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.addFragmentForRecord(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.removeFragmentForRecord(this);
    }
}
