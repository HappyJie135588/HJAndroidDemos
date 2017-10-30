package com.huangjie.hjandroiddemos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.huangjie.hjandroiddemos.utils.MyLogger;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by HuangJie on 2017/7/14.
 */

public class BaseActivity extends AppCompatActivity {
    protected static MyLogger loggerHJ = MyLogger.getHuangJie();
    protected Gson gson = new Gson();

    // 对所有的activity进行管理
    private static List<Activity> mActivities = new LinkedList<Activity>();
    private static Activity mCurrentActivity;

    public static void exitApp() {
        // 遍历所有的activity，finish
        ListIterator<Activity> iterator = mActivities.listIterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            next.finish();
        }
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public static Activity getTopActivity() {
        return mActivities.get(mActivities.size() - 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        synchronized (mActivities) {
            mActivities.add(this);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }
}