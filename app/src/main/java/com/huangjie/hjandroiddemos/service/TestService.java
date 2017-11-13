package com.huangjie.hjandroiddemos.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.utils.MyLogger;
import com.huangjie.hjandroiddemos.utils.ToastUtils;

public class TestService extends Service {
    protected static MyLogger loggerHJ = MyLogger.getHuangJie();

    private MyBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        loggerHJ.d("onCreate() executed");
        ToastUtils.showToast("onCreate() executed");
        Notification notification = new Notification(R.mipmap.ic_launcher,
                "有通知到来", System.currentTimeMillis());
        Intent notificationIntent = new Intent(this, ServiceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
//        notification.setLatestEventInfo(this, "这是通知的标题", "这是通知的内容",
//                pendingIntent);
        startForeground(1, notification);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        loggerHJ.d("onStartCommand() executed");
        ToastUtils.showToast("onStartCommand() executed");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 开始执行后台任务
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loggerHJ.d("onDestroy() executed");
        ToastUtils.showToast("onDestroy() executed");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class MyBinder extends Binder {

        public void startDownload() {
            loggerHJ.d("startDownload() executed");
            ToastUtils.showToast("startDownload() executed");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 执行具体的下载任务
                }
            }).start();
        }

    }


}
