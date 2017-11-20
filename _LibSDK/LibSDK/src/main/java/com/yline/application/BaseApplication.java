package com.yline.application;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.os.Handler;
import android.view.View;

/**
 * 后期所有的方法调用,可以采取xUtils一样,集成到x里面
 * 1, Log to File
 * 2, Log location
 * 3, 异常错误抛出记录
 * 4, Activity管理
 * 5, Application标配Handler、Application
 *
 * @author YLine 2016-5-25 - 上午7:32:23
 */
public class BaseApplication extends Application {
    public static final String TAG = SDKManager.TAG;

    /**
     * 更改资源的时候,才需要做一步操作,引用不需要
     * 原子操作
     *
     * @return 全局handler
     */
    public static Handler getHandler() {
        return SDKManager.getHandler();
    }

    /**
     * 吐司
     *
     * @param content 显示数据
     */
    public static void toast(String content) {
        SDKManager.toast(content);
    }

	/* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 重复提供方法 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% */

    /**
     * @return 当前application, 因为onCreate为应用入口, 因此不用担心为null
     */
    public static Application getApplication() {
        return SDKManager.getApplication();
    }

    public static void addActivity(Activity activity) {
        SDKManager.addActivity(activity);
    }

    public static void removeActivity(Activity activity) {
        SDKManager.removeActivity(activity);
    }

    public static void finishActivity() {
        SDKManager.finishActivity();
    }

    public static void addFragmentForRecord(android.support.v4.app.Fragment fragment) {
        SDKManager.addFragmentForRecord(fragment);
    }

    public static void removeFragmentForRecord(android.support.v4.app.Fragment fragment) {
        SDKManager.removeFragmentForRecord(fragment);
    }

    public static void addFragmentForRecordFew(android.app.Fragment fragment) {
        SDKManager.addFragmentForRecordFew(fragment);
    }

    public static void removeFragmentForRecordFew(android.app.Fragment fragment) {
        SDKManager.removeFragmentForRecordFew(fragment);
    }

    public static void addViewForRecord(View view) {
        SDKManager.addViewForRecord(view);
    }

    public static void removeViewForRecord(View view) {
        SDKManager.removeViewForRecord(view);
    }

    public static void addServiceForRecord(Service service) {
        SDKManager.addServiceForRecord(service);
    }

    public static void removeServiceForRecord(Service service) {
        SDKManager.removeServiceForRecord(service);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SDKManager.init(this, initConfig());
    }

    /**
     * 进行一些基础配置,要求上级必须配置的信息
     *
     * @return 配置数据
     */
    public SDKConfig initConfig() {
        return new SDKConfig();
    }
}
