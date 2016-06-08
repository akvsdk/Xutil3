package com.ep.joy.xutil3.entity;

/**
 * author   Joy
 * Date:  2016/6/7.
 * version:  V1.0
 * Description:
 */
public class UrlBean {
    String url;
    String title;

    public UrlBean(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
