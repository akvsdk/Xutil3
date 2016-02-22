package com.ep.joy.xutil3.http;

import android.app.ProgressDialog;
import android.content.Context;

import org.xutils.common.Callback;

public class BaseCall<ResultType> implements Callback.CommonCallback<ResultType> {


    private Context mContext;

    private ProgressDialog mDialog;

    public BaseCall(Context context) {

        mContext = context;

        initSpotsDialog();
    }

    private void initSpotsDialog() {

        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("拼命加载中...");
        showDialog();

    }

    public void showDialog() {
        mDialog.show();
    }

    public void dismissDialog() {

        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }

    }

    public void setLoadMessage(int resId) {
        mDialog.setMessage(mContext.getString(resId));
    }

    @Override
    public void onSuccess(ResultType result) {
        //可以根据公司的需求进行统一的请求成功的逻辑处理
        dismissDialog();


    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        //可以根据公司的需求进行统一的请求网络失败的逻辑处理
        dismissDialog();
    }

    @Override
    public void onCancelled(CancelledException cex) {
        dismissDialog();
    }

    @Override
    public void onFinished() {

    }


}
