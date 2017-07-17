package com.huangjie.hjandroiddemos.rxjavademo;

/**
 * Created by HuangJie on 2017/7/14.
 */

public interface Watched {
    void addWatcher(Watcher watcher);
    void reMoveWatcher(Watcher watcher);
    void notifyWatcher(String str);
}
