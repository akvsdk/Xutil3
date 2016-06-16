package com.ep.joy.xutil3.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ep.joy.xutil3.R;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jiongbull.jlog.JLog;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;

/**
 * author   Joy
 * Date:  2016/6/16.
 * version:  V1.0
 * Description:
 */

public class TestActivity extends AppCompatActivity {
    private EditText textView;
    private EditText textView2;
    Button btn;
    Button btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        textView = (EditText) findViewById(R.id.textView);
        textView2 = (EditText) findViewById(R.id.textView2);
        btn = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);

        init();


        RxView.clicks(btn2).compose(RxPermissions.getInstance(this).ensure(Manifest.permission.CAMERA))
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            Toast.makeText(TestActivity.this, "Nice", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TestActivity.this, "hava try", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        RxView.clicks(btn).debounce(500, TimeUnit.MILLISECONDS).
                subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        JLog.e(textView.getText().toString() + textView2.getText().toString());
                    }
                });
    }

    private void init() {
        Observable<CharSequence> ObservableEmail = RxTextView.textChanges(textView);
        Observable<CharSequence> ObservablePassword = RxTextView.textChanges(textView2);
        Observable.combineLatest(ObservableEmail, ObservablePassword, new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2) {
                return isEmailValid(charSequence.toString()) && isPasswordValid(charSequence2.toString());
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                btn.setEnabled(aBoolean);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                new Exception(throwable);
            }
        });
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
