package com.yline.application;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yline.log.CrashHandler;
import com.yline.log.LogFileUtil;
import com.yline.sdk.R;
import com.yline.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局配置, 要求初始化!!!
 *
 * @author yline 2017/4/17 -- 15:48
 * @version 1.0.0
 */
public class SDKManager {
	/**
	 * handler 吐丝
	 */
	private static final int HANDLER_TOAST = -1;
	
	/**
	 * 应用心跳
	 */
	private static final int HANDLER_PALPITATION = -2;
	
	/**
	 * 应用心跳频率
	 */
	private static final int APPLICATION_TIME = 2 * 60 * 1000;
	
	/**
	 * Activity管理
	 */
	private static List<Activity> mActivityList = new ArrayList<>();
	
	/**
	 * Fragment记录(记录的只有String)
	 */
	private static List<String> mFragmentList = new ArrayList<>();
	
	/**
	 * View记录
	 */
	private static List<String> mViewList = new ArrayList<>();
	
	/**
	 * Service记录
	 */
	private static List<String> mServiceList = new ArrayList<>();
	
	private static Application mApplication;
	
	private static SDKConfig mSdkConfig;
	
	/**
	 * Toast 工具
	 */
	private static Toast mToast;
	private static TextView mTvToast;
	// handler相关
	private static Handler handler;
	
	private SDKManager() {
		/* 实例化失败 */
		throw new UnsupportedOperationException("cannot be instantiated");
	}
	
	private static class DefaultHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case HANDLER_PALPITATION:
					LogFileUtil.m("this time = " + System.currentTimeMillis() + ",this thread = " + Thread.currentThread().getId());
					getHandler().sendEmptyMessageDelayed(HANDLER_PALPITATION, APPLICATION_TIME);
					break;
				case HANDLER_TOAST:
					showToast(getApplication(), (String) msg.obj);
					break;
				default:
					break;
			}
		}
	}
	
	public static void init(Application application, SDKConfig sdkConfig) {
		SDKManager.mApplication = application;  // 初始化全局变量
		SDKManager.mSdkConfig = sdkConfig;
		
		// 异常崩溃日志
		CrashHandler.initConfig(application);
		
		// 打印日志工具
		LogUtil.init(getSdkConfig());
		LogFileUtil.init(application, getSdkConfig());
		
		// 设立一个程序入口的log
		LogFileUtil.m("应用启动 *** application start id = " + Thread.currentThread().getId());
		LogFileUtil.m(getSdkConfig().toString());
		LogFileUtil.m("应用启动 *** application start id = " + Thread.currentThread().getId());
		
		getHandler().sendEmptyMessageDelayed(HANDLER_PALPITATION, APPLICATION_TIME);
	}
	
	/**
	 * 更改资源的时候,才需要做一步操作,引用不需要
	 * 原子操作
	 *
	 * @return 全局handler
	 */
	public static Handler getHandler() {
		if (null == handler) {
			synchronized (DefaultHandler.class) {
				if (null == handler) {
					handler = new DefaultHandler();
				}
			}
		}
		return handler;
	}
	
	/**
	 * 打印toast
	 *
	 * @param context 上下文
	 * @param msg     内容
	 */
	private static void showToast(Context context, String msg) {
		if (null == mToast) {
			mToast = new Toast(context);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		
		if (null == mTvToast) {
			mTvToast = new TextView(context);
			mTvToast.setBackgroundResource(R.drawable.lib_bg_toast);
			mTvToast.setTextColor(0xffffffff);
		}
		
		mTvToast.setText(msg);
		mToast.setView(mTvToast);
		mToast.show();
	}
	
	/**
	 * 吐司
	 *
	 * @param content 显示数据
	 */
	public static void toast(String content) {
		getHandler().obtainMessage(HANDLER_TOAST, content).sendToTarget();
	}
	
	/**
	 * @return 当前application, 因为onCreate为应用入口, 因此不用担心为null
	 */
	public static Application getApplication() {
		return mApplication;
	}
	
	/**
	 * @return 配置的SDKConfig，因为onCreate为应用入口, 因此不用担心为null
	 */
	public static SDKConfig getSdkConfig() {
		if (null == mSdkConfig) {
			synchronized (SDKConfig.class) {
				if (null == mSdkConfig) {
					mSdkConfig = new SDKConfig();
				}
			}
		}
		return mSdkConfig;
	}
	
	public static void addActivity(Activity activity) {
		LogFileUtil.m("addActivity:" + activity.getClass().getSimpleName());
		mActivityList.add(activity);
	}
	
	public static void removeActivity(Activity activity) {
		LogFileUtil.m("removeActivity:" + activity.getClass().getSimpleName());
		mActivityList.remove(activity);
	}
	
	public static void finishActivity() {
		for (Activity activity : mActivityList) {
			LogFileUtil.m("finishActivity:" + activity.getClass().getSimpleName());
			activity.finish();
		}
	}
	
	public static void addFragmentForRecord(android.support.v4.app.Fragment fragment) {
		LogFileUtil.m("addFragmentForRecord V4:" + fragment.getClass().getSimpleName());
		mFragmentList.add(fragment.getClass().getSimpleName());
	}
	
	public static void removeFragmentForRecord(android.support.v4.app.Fragment fragment) {
		LogFileUtil.m("removeFragmentForRecord V4:" + fragment.getClass().getSimpleName());
		mFragmentList.remove(fragment.getClass().getSimpleName());
	}
	
	public static void addFragmentForRecordFew(android.app.Fragment fragment) {
		LogFileUtil.m("addFragmentForRecord Few:" + fragment.getClass().getSimpleName());
		mFragmentList.add(fragment.getClass().getSimpleName());
	}
	
	public static void removeFragmentForRecordFew(android.app.Fragment fragment) {
		LogFileUtil.m("removeFragmentForRecord Few:" + fragment.getClass().getSimpleName());
		mFragmentList.remove(fragment.getClass().getSimpleName());
	}
	
	public static void addViewForRecord(View view) {
		LogFileUtil.m("addViewForRecord:" + view.getClass().getSimpleName());
		mViewList.add(view.getClass().getSimpleName());
	}
	
	public static void removeViewForRecord(View view) {
		LogFileUtil.m("removeViewForRecord:" + view.getClass().getSimpleName());
		mViewList.remove(view.getClass().getSimpleName());
	}
	
	public static void addServiceForRecord(Service service) {
		LogFileUtil.m("addServiceForRecord:" + service.getClass().getSimpleName());
		mServiceList.add(service.getClass().getSimpleName());
	}
	
	public static void removeServiceForRecord(Service service) {
		LogFileUtil.m("removeServiceForRecord:" + service.getClass().getSimpleName());
		mServiceList.remove(service.getClass().getSimpleName());
	}
	
}
