package com.yline.base;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.yline.application.BaseApplication;

public class BaseDialogFragment extends DialogFragment {
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
