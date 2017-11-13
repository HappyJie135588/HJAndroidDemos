package com.huangjie.hjandroiddemos.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.IntDef;

import com.huangjie.hjandroiddemos.service.entity.FileInfo;
import com.huangjie.hjandroiddemos.utils.MyLogger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends Service {
    protected static MyLogger loggerHJ = MyLogger.getHuangJie();

    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String FILE_INFO = "FILE_INFO";
    public static final int MSG_INIT = 0;


    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/HJAndroidDemos/";

    public DownloadService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FileInfo fileInfo = (FileInfo) intent.getSerializableExtra(FILE_INFO);
        loggerHJ.d(fileInfo.toString());
        if (ACTION_START.equals(intent.getAction())) {
            new InitThread(fileInfo).start();

        } else if (ACTION_STOP.equals(intent.getAction())) {

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    loggerHJ.d(fileInfo.toString());
                    break;
            }
        }
    };

    class InitThread extends Thread {
        private FileInfo mFileInfo;

        public InitThread(FileInfo fileInfo) {
            this.mFileInfo = fileInfo;
        }

        @Override
        public void run() {
            super.run();
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            //链接网络文件
            try {
                URL url = new URL(mFileInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                int length = -1;
                int responseCode = conn.getResponseCode();
                loggerHJ.d("responseCode: " + responseCode);
                if (responseCode == 200) {
                    //获取文件长度
                    length = conn.getContentLength();
                }
                if (length <= 0) {
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                //在本地创建文件
                File file = new File(dir, mFileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                //设置文件长度
                raf.setLength(length);
                mFileInfo.setLength(length);
                mHandler.obtainMessage(MSG_INIT, mFileInfo).sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.disconnect();
                    raf.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
