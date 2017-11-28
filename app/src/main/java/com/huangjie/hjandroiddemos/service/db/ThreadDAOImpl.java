package com.huangjie.hjandroiddemos.service.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huangjie.hjandroiddemos.service.entity.ThreadInfo;

import java.util.ArrayList;
import java.util.List;

import static com.huangjie.hjandroiddemos.service.db.MyDownloadDbSchema.MyDownloadTable.*;

/**
 * Created by HuangJie on 2017/11/13.
 */

public class ThreadDAOImpl implements ThreadDAO {

    private DBHelper mHelper = null;

    public ThreadDAOImpl(Context context) {
        mHelper = DBHelper.getInstance(context);
    }

    @Override
    public synchronized void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Clos.my_thread_id, threadInfo.getId());
        contentValues.put(Clos.my_url, threadInfo.getUrl());
        contentValues.put(Clos.my_start, threadInfo.getStart());
        contentValues.put(Clos.my_end, threadInfo.getEnd());
        contentValues.put(Clos.my_finished, threadInfo.getFinished());
        db.insert(NAME, null, contentValues);
        db.close();
    }

    @Override
    public synchronized void deleteThread(String url) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(NAME, Clos.my_url + " = ? ", new String[]{url});
        db.close();
    }

    @Override
    public synchronized void updateThread(String url, int thread_id, int finished) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Clos.my_finished, finished);
        db.update(NAME, contentValues, Clos.my_url + " = ? and " + Clos.my_thread_id + " = ? ", new String[]{url, thread_id + ""});
        db.close();
    }

    @Override
    public List<ThreadInfo> getThreads(String url) {
        List<ThreadInfo> threadInfoList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(NAME, null, Clos.my_url + " = ? ", new String[]{url}, null, null, null);
        while (cursor.moveToNext()) {
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex(Clos.my_thread_id)));
            threadInfo.setUrl(cursor.getString(cursor.getColumnIndex(Clos.my_url)));
            threadInfo.setStart(cursor.getInt(cursor.getColumnIndex(Clos.my_start)));
            threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex(Clos.my_end)));
            threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex(Clos.my_finished)));
            threadInfoList.add(threadInfo);
        }
        cursor.close();
        db.close();
        return threadInfoList;
    }

    @Override
    public boolean isExists(String url, int thread_id) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(NAME, null, Clos.my_url + " = ? and " + Clos.my_thread_id + " = ? ", new String[]{url, thread_id + ""}, null, null, null);
        boolean isExists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isExists;
    }

    public int getProgress(String url) {
        List<ThreadInfo> threadInfos = getThreads(url);
        int length = 0;
        long finished = 0;
        for (ThreadInfo threadInfo : threadInfos) {
            length += threadInfo.getEnd() - threadInfo.getStart();
            finished += threadInfo.getFinished();
        }
        if (length == 0) {
            return 0;
        } else {
            return (int) (finished * 100 / length);
        }
    }
}
