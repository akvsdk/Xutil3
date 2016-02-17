package com.ep.joy.xutil3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ep.joy.xutil3.R;
import com.ep.joy.xutil3.entity.Bean;
import com.ep.joy.xutil3.entity.JsonResult;
import com.ep.joy.xutil3.http.BaseTask;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
    }


    public void send(View view) {
        String url = "http://222.177.210.200/public/news/getNewsList";
        Map<String, Object> map = new HashMap<>();
        map.put("newsTypeVal", "CC");
        new BaseTask<JsonResult<Bean>, String>(this,"Loading……") {

            @Override
            public TypeToken setTypeToken() {
                return new TypeToken<Bean>() {
                };
            }

            @Override
            public void onSuccess() {
               if (result.isSuccess()){
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
