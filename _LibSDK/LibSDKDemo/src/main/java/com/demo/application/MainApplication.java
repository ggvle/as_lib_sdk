package com.demo.application;

import android.app.Application;

import androidx.multidex.MultiDex;

import com.yline.application.BaseApplication;

import leakcanary.LeakCanary;

public class MainApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
        initLeakCanary(this);
    }

    private void initLeakCanary(Application application) {
        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        LeakCanary.install(application);*/
    }
}
