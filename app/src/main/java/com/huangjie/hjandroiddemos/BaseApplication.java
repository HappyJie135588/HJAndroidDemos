package com.huangjie.hjandroiddemos;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by HuangJie on 2017/6/23.
 */

public class BaseApplication extends Application {

    private static Context mContext;//上下文
    private static Thread  mMainThread;
    private static long    mMainThreadId;
    private static Looper  mMainLooper;
    private static Handler mMainHandler;

    //应用程序的入口
    @Override
    public void onCreate() {
        super.onCreate();

        //上下文
        mContext = this;

        //主线程
        mMainThread = Thread.currentThread();

        //主线程id
        //mMainThreadId = mMainThread.getId();
        mMainThreadId = android.os.Process.myTid();

        mMainLooper = getMainLooper();

        mMainHandler = new Handler();

    }

    public static Context getContext() {
        return mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static Handler getMainHandler() {
        return mMainHandler;
    }
}
