package com.huangjie.hjandroiddemos.service.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.huangjie.hjandroiddemos.service.db.MyDownloadDbSchema.MyDownloadTable;

/**
 * Created by HuangJie on 2017/11/13.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "download.db";
    private static final int VERSION = 1;

//    private static final String SQL_CREATE = "create table thread_info(_id integer primary key autoincrement," +
//            "thread_id integer,url text,start integer,end integer,finished integer)";
    private static final String SQL_CREATE = "create table " + MyDownloadTable.NAME + "(" +
            "_id integer primary key autoincrement, " +
            MyDownloadTable.Clos.my_thread_id + " integer" + ", " +
            MyDownloadTable.Clos.my_url + " text" + ", " +
            MyDownloadTable.Clos.my_start + " integer" + ", " +
            MyDownloadTable.Clos.my_end + " integer" + ", " +
            MyDownloadTable.Clos.my_finished + " integer" + ")";


    private static final String SQL_DROP = "drop table if exists "+MyDownloadTable.NAME;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }
}
