package com.ep.joy.xutil3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.ep.joy.xutil3.R;
import com.ep.joy.xutil3.entity.ImageInfo;
import com.ep.joy.xutil3.util.GlideProxy;
import com.ep.joy.xutil3.util.JsoupTool;
import com.youzan.titan.QuickAdapter;
import com.youzan.titan.TitanRecyclerView;
import com.youzan.titan.holder.AutoViewHolder;

import java.util.ArrayList;
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

    private TitanRecyclerView mRecyclerView;
    private QuickAdapter<ImageInfo> mQuickAdapter;
    private List<ImageInfo> mdatas = new ArrayList<>();
    private int pages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_);
        mRecyclerView = (TitanRecyclerView) findViewById(R.id.rcy);
        mQuickAdapter = new QuickAdapter<ImageInfo>(R.layout.item_girl, mdatas) {
            @Override
            public void bindView(AutoViewHolder holder, int position, ImageInfo model) {
                holder.getTextView(R.id.tv_time).setText(model.getContent());
                GlideProxy.getInstance().loadImage(JsoupActivity.this, model.getImgurl(), holder.getImageView(R.id.iv_index_photo));
            }
        };
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(mQuickAdapter);
        loaddate();

        mRecyclerView.setOnLoadMoreListener(new TitanRecyclerView.OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                pages++;
                if (pages < 44) {
                    mRecyclerView.setHasMore(true);
                    loaddate();
                } else {
                    mRecyclerView.setHasMore(false);
                }
            }
        });

    }

    private void loaddate() {
        Observable.create(new Observable.OnSubscribe<List<ImageInfo>>() {
            @Override
            public void call(Subscriber<? super List<ImageInfo>> subscriber) {
                subscriber.onNext(JsoupTool.getInstance().getContent("http://zhuangbi.info/?page=" + pages));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ImageInfo>>() {
                    @Override
                    public void call(List<ImageInfo> s) {
                        mQuickAdapter.addDataEnd(s);
                    }
                });

    }

}
