package com.huangjie.hjandroiddemos.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.service.entity.FileInfo;

import java.util.HashMap;
import java.util.Map;

import static com.huangjie.hjandroiddemos.service.DownloadService.FILE_INFO;
import static com.huangjie.hjandroiddemos.service.DownloadService.loggerHJ;

/**
 * Created by HuangJie on 2017/11/16.
 */

public class NotificationUtil {
    private Context mContext;
    private NotificationManager mNotificationManager;
    private Map<Integer, Notification> mNotificationMap;

    public NotificationUtil(Context context) {
        this.mContext = context;
        //获得通知系统服务
        mNotificationManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        //创建通知的集合
        mNotificationMap = new HashMap<>();
    }

    /**
     * 显示通知
     *
     * @param fileInfo
     */
    public void showNotification(FileInfo fileInfo) {
        //判断通知是否已经显示了
        if (!mNotificationMap.containsKey(fileInfo.getId())) {
            //创建通知对象
            Notification notification = new Notification();
            //设置滚动文字
            notification.tickerText = fileInfo.getFileName() + "开始下载";
            //设置显示时间
            notification.when = System.currentTimeMillis();
            //设置图标
            notification.icon = R.mipmap.ic_launcher;
            //设置通知特性   点击自动取消
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            //设置点击通知栏操作
            Intent intent = new Intent(mContext, DownloadActivity.class);
            PendingIntent pintent = PendingIntent.getActivity(mContext, 0, intent, 0);
            notification.contentIntent = pintent;
            //创建RemoteViews远程视图对象
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.notification);
            //设置TextView
            remoteViews.setTextViewText(R.id.tv_name,fileInfo.getFileName());
//            //设置开始按钮操作
//            Intent intentStart = new Intent(mContext, DownloadService.class);
//            intentStart.setAction(DownloadService.ACTION_START);
//            intentStart.putExtra(FILE_INFO, fileInfo);
//            PendingIntent piStart = PendingIntent.getService(mContext, fileInfo.getId(), intentStart, 0);
//            remoteViews.setOnClickPendingIntent(R.id.bt_start, piStart);
//            //设置停止按钮操作
//            Intent intentStop = new Intent(mContext, DownloadService.class);
//            intentStop.setAction(DownloadService.ACTION_STOP);
//            intentStop.putExtra(FILE_INFO, fileInfo);
//            PendingIntent piStop = PendingIntent.getService(mContext, fileInfo.getId(), intentStop, 0);
//            remoteViews.setOnClickPendingIntent(R.id.bt_stop, piStop);
            //设置Notification的视图
            notification.contentView = remoteViews;
            //发出通知
            mNotificationManager.notify(fileInfo.getId(), notification);
            //把通知加到集合中
            mNotificationMap.put(fileInfo.getId(), notification);
        }
    }

    /**
     * 取消通知
     * @param id
     */
    public void cancelNotification(int id){
        mNotificationManager.cancel(id);
        mNotificationMap.remove(id);
    }

    /**
     * 更新进度条
     * @param id
     * @param progress
     */
    public void updateNotification(int id,int progress){
        Notification notification = mNotificationMap.get(id);
        if(notification!=null){
            //修改进度条
            notification.contentView.setProgressBar(R.id.pb_progress,100,progress,false);
            mNotificationManager.notify(id,notification);
        }
    }


}
