package com.ep.joy.xutil3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.ep.joy.xutil3.R;
import com.ep.joy.xutil3.entity.ImageInfo;
import com.ep.joy.xutil3.util.GlideProxy;
import com.ep.joy.xutil3.util.JsoupTool;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * author   Joy
 * Date:  2016/6/7.
 * version:  V1.0
 * Description:
 */
public class JsoupActivity extends AppCompatActivity {

    private ImageView jsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_);
        jsTv = (ImageView) findViewById(R.id.js_tv);
        Observable.create(new Observable.OnSubscribe<List<ImageInfo>>() {
            @Override
            public void call(Subscriber<? super List<ImageInfo>> subscriber) {

                subscriber.onNext(JsoupTool.getInstance().getContent("http://zhuangbi.info"));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ImageInfo>>() {
                    @Override
                    public void call(List<ImageInfo> s) {
                        s.remove(0);
                        GlideProxy.getInstance().loadImage(JsoupActivity.this, s.get(1).getImgurl(), jsTv);
                    }
                });


    }

    public void js(View v) {

    }
}
