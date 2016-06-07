package com.ep.joy.xutil3;

import android.app.Application;

import com.ep.joy.xutil3.imageloader.ImageLoaderUtil;
import com.jiongbull.jlog.JLog;
import com.jiongbull.jlog.constant.LogLevel;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

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
        List<LogLevel> logLevels = new ArrayList<>();
        logLevels.add(LogLevel.ERROR);
        logLevels.add(LogLevel.JSON);

        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志

        JLog.init(this)
                .writeToFile(false)    //输出到文件中
                .setLogLevelsForFile(logLevels)
                .setLogDir(getString(R.string.app_name))
                .setTimeFormat("yyyy年MM月dd日 HH时mm分ss---秒")
                .setDebug(BuildConfig.DEBUG);
        ImageLoaderUtil.init(this);
    }
}
