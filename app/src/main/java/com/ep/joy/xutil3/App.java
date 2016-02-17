package com.ep.joy.xutil3;

import android.app.Application;

import org.xutils.x;

/**
 * author  Joy
 * Date:  2016/2/16 0016.
 * version:  V1.0
 * Description:
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志

    }
}
