package com.ep.joy.xutil3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ep.joy.xutil3.R;
import com.ep.joy.xutil3.entity.ImageInfo;
import com.ep.joy.xutil3.util.GlideProxy;
import com.ep.joy.xutil3.util.JsoupTool;
import com.ep.joy.xutil3.util.ShareElement;
import com.youzan.titan.QuickAdapter;
import com.youzan.titan.TitanRecyclerView;
import com.youzan.titan.holder.AutoViewHolder;
import com.youzan.titan.internal.ItemClickSupport;

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
    private Toolbar toolbar;
    private AppBarLayout mbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_);
        initToolBar();
        mRecyclerView = (TitanRecyclerView) findViewById(R.id.rcy);
        mQuickAdapter = new QuickAdapter<ImageInfo>(R.layout.item_zb, mdatas) {
            @Override
            public void bindView(AutoViewHolder holder, int position, ImageInfo model) {
                ShareElement.shareDrawable = holder.getImageView(R.id.iv_index_photo).getDrawable();
                holder.getTextView(R.id.tv_time).setText(model.getContent());
                GlideProxy.getInstance().loadImage(JsoupActivity.this, model.getImgurl(), holder.getImageView(R.id.iv_index_photo));
            }
        };
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(mQuickAdapter);
        loaddate();
        mRecyclerView.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View view, int position, long id) {
                ImageInfo img = mQuickAdapter.getItem(position);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(JsoupActivity.this,view,"share");
                Intent intent = new Intent(JsoupActivity.this, SavaActivity.class);
                intent.putExtra(SavaActivity.BEAN, img);
                ActivityCompat.startActivity(JsoupActivity.this, intent, optionsCompat.toBundle());
            }
        });

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

    private void initToolBar() {

        mbar = (AppBarLayout) findViewById(R.id.app_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("装B小能手");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        }

    }

}
