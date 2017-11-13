package com.huangjie.hjandroiddemos.service.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.huangjie.hjandroiddemos.service.entity.ThreadInfo;

import java.util.List;

/**
 * Created by HuangJie on 2017/11/13.
 */

public class ThreadDAOImpl implements ThreadDAO {

    private DBHelper mHelper = null;

    public ThreadDAOImpl(Context context) {
        mHelper = new DBHelper(context);
    }

    @Override
    public void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.insert("thread_info")

    }

    @Override
    public void deleteThread(String url, int thread_id) {

    }

    @Override
    public void updateThread(String url, int thread_id, int finished) {

    }

    @Override
    public List<ThreadInfo> getThreads(String url) {
        return null;
    }

    @Override
    public boolean isExists(String url, int thread_id) {
        return false;
    }
}
