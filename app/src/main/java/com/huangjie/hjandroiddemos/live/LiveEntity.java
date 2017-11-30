package com.huangjie.hjandroiddemos.live;

import java.io.Serializable;

/**
 * Created by HuangJie on 2017/11/30.
 */

public class LiveEntity implements Serializable {
    String name;
    String url;

    public LiveEntity(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
