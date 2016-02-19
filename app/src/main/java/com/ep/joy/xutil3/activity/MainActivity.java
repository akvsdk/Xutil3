package com.ep.joy.xutil3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ep.joy.xutil3.R;
import com.ep.joy.xutil3.adapter.YouAdapter;
import com.ep.joy.xutil3.entity.Bean;
import com.ep.joy.xutil3.entity.JsonResult;
import com.ep.joy.xutil3.entity.YourBean;
import com.ep.joy.xutil3.http.BaseTask;
import com.ep.joy.xutil3.http.MyCallBack;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private String url;
    private ListView mListView;
    private List<YourBean.ListEntity> bean = new ArrayList<>();
    private YouAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        mListView = (ListView) findViewById(R.id.list);
        url = "http://120.25.0.216/userfindmacth.json";
        adapter = new YouAdapter(this, bean, R.layout.item);
        mListView.setAdapter(adapter);
    }

    public void send1(View view) {
        tv.setVisibility(View.VISIBLE);
        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("pageNo", "1");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                tv.setText(s);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void send2(View view) {
        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("pageNo", "1");
        x.http().post(params,new MyCallBack<YourBean>(){
            @Override
            public void onSuccess(YourBean result) {
                super.onSuccess(result);
                tv.setVisibility(View.GONE);
                bean.clear();
                bean.addAll(result.getList());
                adapter.notifyDataSetChanged();
            }
        });

    }

    public void send(View view) {
        String url = "http://222.177.210.200/public/news/getNewsList";
        Map<String, Object> map = new HashMap<>();
        map.put("newsTypeVal", "CC");
        new BaseTask<JsonResult<Bean>, String>(this, "Loading……") {

            @Override
            public TypeToken setTypeToken() {
                return new TypeToken<Bean>() {
                };
            }

            @Override
            public void onSuccess() {
                if (result.isSuccess()) {
                    tv.setText(result.getRecord().getNewsList().get(0).getTitle());
                }
            }
        }.requestByPost(url, map);

//        XUtil.Post(url,map,new MyCallBack<JsonResult>(){
//
//            @Override
//            public void onSuccess(JsonResult result) {
//                super.onSuccess(result);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                super.onError(ex, isOnCallback);
//            }
//        });


    }
}
