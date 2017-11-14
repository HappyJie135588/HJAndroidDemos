package com.huangjie.hjandroiddemos.service.db;

/**
 * Created by HuangJie on 2017/7/18.
 */

public class MyDownloadDbSchema {
    public static final class MyDownloadTable {
        //_id integer primary key autoincrement,thread_id integer,url text,start integer,end integer,finished integer
        public static final String NAME = "my_thread_info";

        public static final class Clos {
            public static final String my_thread_id = "my_thread_id";
            public static final String my_url = "my_url";
            public static final String my_start = "my_start";
            public static final String my_end = "my_end";
            public static final String my_finished = "my_finished";
        }
    }
}
