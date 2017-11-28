package com.huangjie.hjandroiddemos;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.huangjie.hjandroiddemos.crashhandler.CrashHandler;

/**
 * Created by HuangJie on 2017/6/23.
 */

public class BaseApplication extends Application {

    private static Context mContext;//上下文
    private static Thread  mMainThread;
    private static long    mMainThreadId;
    private static Looper  mMainLooper;
    private static Handler mMainHandler;
    //异常捕获
    private CrashHandler mCrashHandler;

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
        //异常捕获
        mCrashHandler = CrashHandler.getInstance();
        mCrashHandler.init(mContext);

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
