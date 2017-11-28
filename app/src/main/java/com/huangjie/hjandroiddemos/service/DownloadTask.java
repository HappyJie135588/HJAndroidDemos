package com.huangjie.hjandroiddemos.service;

import android.content.Context;
import android.content.Intent;

import com.huangjie.hjandroiddemos.service.db.ThreadDAO;
import com.huangjie.hjandroiddemos.service.db.ThreadDAOImpl;
import com.huangjie.hjandroiddemos.service.entity.FileInfo;
import com.huangjie.hjandroiddemos.service.entity.ThreadInfo;
import com.huangjie.hjandroiddemos.utils.MyLogger;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 下载任务类
 * Created by HuangJie on 2017/11/14.
 */

public class DownloadTask {
    private MyLogger loggerHJ = MyLogger.getHuangJie();
    private Context mContext;
    private FileInfo mFileInfo;
    private ThreadDAO mDAO;
    private long mFinished = 0;
    private boolean isPause;
    //线程数量
    private int mThreadCount;
    //线程集合
    private List<DownloadThread> mThreadList;
    //线程池
    private static ExecutorService sExecutorService = Executors.newCachedThreadPool();
    //定时器
    private Timer mTimer = new Timer();


    public DownloadTask(Context context, FileInfo fileInfo) {
        this(context, fileInfo, 1);
    }

    public DownloadTask(Context context, FileInfo fileInfo, int threadCount) {
        this.mContext = context;
        this.mFileInfo = fileInfo;
        this.mThreadCount = threadCount;
        mDAO = new ThreadDAOImpl(mContext);
    }

    public void setPause(boolean pause) {
        isPause = pause;
        mTimer.cancel();
    }

    public static ExecutorService getExecutorService() {
        return sExecutorService;
    }

    public void download() {
        //读取数据库的线程信息
        List<ThreadInfo> threadInfos = mDAO.getThreads(mFileInfo.getUrl());
        if (threadInfos.size() == 0) {
            //获取每个线程下载长度
            int length = mFileInfo.getLength() / mThreadCount;
            for (int i = 0; i < mThreadCount; i++) {
                //创建线程信息
                ThreadInfo threadInfo = new ThreadInfo(i, mFileInfo.getUrl(), i * length, (i + 1) * length, 0);
                if (i == mThreadCount - 1) {
                    threadInfo.setEnd(mFileInfo.getLength());
                }
                //添加到线程信息集合中
                threadInfos.add(threadInfo);
                //向数据库插入线程信息
                mDAO.insertThread(threadInfo);
            }
        }
        mThreadList = new ArrayList<>();
        //启动多个线程进行下载
        for (ThreadInfo threadInfo : threadInfos) {
            DownloadThread thread = new DownloadThread(threadInfo);
            sExecutorService.execute(thread);
            //添加线程到集合中
            mThreadList.add(thread);
        }
        //启动定时任务
        Intent intent = new Intent(DownloadService.ACTION_UPDATE);
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //发送广播修改Activity进度
                loggerHJ.d("mFinished:" + mFinished);
                mFileInfo.setProgress((int) (mFinished * 100 / mFileInfo.getLength()));
                intent.putExtra("fileInfo", mFileInfo);
                mContext.sendBroadcast(intent);
            }
        }, 100, 500);

    }

    /**
     * 判断是否所有线程都执行完毕
     */
    private synchronized void checkAllThreadsFinshed() {
        boolean allFinished = true;
        //遍历线程集合,判断线程是否都执行完毕
        for (DownloadThread thread : mThreadList) {
            if (!thread.isFinished()) {
                allFinished = false;
                break;
            }
        }
        if (allFinished) {
            //取消定时器
            mTimer.cancel();
            //删除线程信息
            mDAO.deleteThread(mFileInfo.getUrl());
            //发送广播通知UI下载任务结束
            Intent intent = new Intent(DownloadService.ACTION_FINISH);
            intent.putExtra("fileInfo", mFileInfo);
            mContext.sendBroadcast(intent);
        }

    }

    /**
     * 下载线程
     */
    class DownloadThread extends Thread {
        private ThreadInfo mThreadInfo;
        //线程是否执行完毕
        private boolean isFinished = false;

        public DownloadThread(ThreadInfo threadInfo) {
            this.mThreadInfo = threadInfo;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream input = null;
            try {
                URL url = new URL(mThreadInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                //设置下载位置
                int start = mThreadInfo.getStart() + mThreadInfo.getFinished();
                conn.setRequestProperty("Range", "bytes=" + start + "-" + mThreadInfo.getEnd());
                //设置文件写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH, mFileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);
                synchronized (DownloadTask.this) {
                    loggerHJ.d(getId() + "mFileInfo:开始 " + mFinished);
                    mFinished += mThreadInfo.getFinished();
                    loggerHJ.d(getId() + "mThreadInfo.getFinished(): " + mThreadInfo.getFinished());
                    loggerHJ.d(getId() + "mFileInfo:结束 " + mFinished);
                }
                //开始下载
                if (conn.getResponseCode() == 206) {
                    //读取数据
                    input = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 8];
                    int len = -1;
                    while ((len = input.read(buffer)) != -1) {
                        //写入文件
                        raf.write(buffer, 0, len);
                        //把下载进度发送广播给Acticity
                        //累加每个线程完成的进度
                        mFinished += len;
                        mThreadInfo.setFinished(mThreadInfo.getFinished() + len);
                        //在下载暂停时,保存下载进度
                        if (isPause) {
                            mDAO.updateThread(mThreadInfo.getUrl(), mThreadInfo.getId(), mThreadInfo.getFinished());
                            return;
                        }
                    }
                    //标识线程执行完毕
                    isFinished = true;
                    //检查下载任务是否执行完毕
                    checkAllThreadsFinshed();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.disconnect();
                    input.close();
                    raf.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
