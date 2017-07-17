package com.huangjie.hjandroiddemos.rxjavademo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuangJie on 2017/7/14.
 */

public class TestWathed implements Watched {
    List<Watcher> mWatcherList = new ArrayList<>();

    @Override
    public void addWatcher(Watcher watcher) {
        mWatcherList.add(watcher);
    }

    @Override
    public void reMoveWatcher(Watcher watcher) {
        mWatcherList.remove(watcher);
    }

    @Override
    public void notifyWatcher(String str) {
        for (Watcher watcher : mWatcherList) {
            watcher.update(str);
        }
    }
}
