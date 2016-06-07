package com.ep.joy.xutil3.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Scroller;

import com.ep.joy.xutil3.R;
import com.ep.joy.xutil3.entity.ImageInfo;
import com.ep.joy.xutil3.util.JsoupTool;
import com.ep.joy.xutil3.util.UrlBean;
import com.tomandjerry.coolanim.lib.CoolAnimView;

import org.xutils.x;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import me.drakeet.materialdialog.MaterialDialog;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class ShowActivity extends AppCompatActivity {

    private List<UrlBean> bean1;
    private List<ImageInfo> bean2;
    ViewPager mViewPager;
    private ImageView mImageView;
    String Base_url = "http://www.dbmeinv.com/dbgroup/show.htm";
    private PicLoopAdapter loopAdapter;
    private List<ImageView> mCacheViews = new ArrayList<>();
    private Subscription subscribe_auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mImageView = (ImageView) findViewById(R.id.img);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        Observable.create(new Observable.OnSubscribe<List<ImageInfo>>() {
            @Override
            public void call(Subscriber<? super List<ImageInfo>> subscriber) {
                bean1 = JsoupTool.getInstance().getUrl(Base_url);
                if (bean1.size() > 0) {
                    int index = new Random().nextInt(bean1.size());
                    subscriber.onNext(JsoupTool.getInstance().getAllImages(bean1.get(index).getUrl()));
                } else {
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ImageInfo>>() {
                    @Override
                    public void call(List<ImageInfo> imageInfos) {
                        x.image().bind(mImageView, imageInfos.get(5).getImgurl());
                        bean2 = imageInfos;
                        initViewPager();
                    }
                });

    }

    private void initViewPager() {
        mImageView.setVisibility(View.GONE);
        loopAdapter = new PicLoopAdapter(bean2);
        mViewPager.setAdapter(loopAdapter);

        try {
            //自定义滑动速度
            Field mScrollerField = ViewPager.class.getDeclaredField("mScroller");
            mScrollerField.setAccessible(true);
            mScrollerField.set(mViewPager, new ViewPagerScroller(mViewPager.getContext()));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public void send2(View view) {
        if (subscribe_auto != null && !subscribe_auto.isUnsubscribed()) {
            subscribe_auto.unsubscribe();
        }
    }

    public void send1(View view) {
        if (subscribe_auto == null || subscribe_auto.isUnsubscribed()) {
            subscribe_auto = Observable.interval(3000, 3000, TimeUnit.MILLISECONDS)
                    //延时3000 ，每间隔3000，时间单位
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            int currentIndex = mViewPager.getCurrentItem();
                            if (++currentIndex == loopAdapter.getCount()) {
                                mViewPager.setCurrentItem(0);
                            } else {
                                mViewPager.setCurrentItem(currentIndex, true);
                            }
                        }
                    });
        }
    }


    public void showdialog(View view) {
        MaterialDialog mMaterialDialog = new MaterialDialog(this).setContentView(new CoolAnimView(ShowActivity.this));
        mMaterialDialog.show();
    }

    private class PicLoopAdapter extends PagerAdapter {

        private List<ImageInfo> bean;

        public PicLoopAdapter(List<ImageInfo> datas) {
            this.bean = datas;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            int index = position % bean.size();
            ImageView iv;
            if (mCacheViews.size() > 0) {
                iv = mCacheViews.remove(0);
            } else {
                iv = new ImageView(ShowActivity.this);

                iv.setLayoutParams(new ViewPager.LayoutParams());
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            x.image().bind(iv, bean.get(index).getImgurl());
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            mCacheViews.add((ImageView) object);
        }
    }

    /**
     * 自定义Scroller，用于调节ViewPager滑动速度
     */
    public class ViewPagerScroller extends Scroller {
        private static final int M_SCROLL_DURATION = 1200;// 滑动速度

        public ViewPagerScroller(Context context) {
            super(context);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, M_SCROLL_DURATION);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, M_SCROLL_DURATION);
        }
    }

}
