package com.huangjie.hjandroiddemos.rxjavademo;

import com.huangjie.hjandroiddemos.utils.MyLogger;

/**
 * Created by HuangJie on 2017/7/14.
 */

public class TestWather implements Watcher {
    public MyLogger loggerHJ = MyLogger.getHuangJie();
    @Override
    public void update(String str) {
        loggerHJ.d(str);
    }
}
