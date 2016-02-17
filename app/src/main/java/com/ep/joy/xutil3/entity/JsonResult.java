package com.ep.joy.xutil3.entity;

import com.ep.joy.xutil3.http.JsonResponseParser;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.xutils.http.annotation.HttpResponse;

import java.lang.reflect.Type;

@HttpResponse(parser = JsonResponseParser.class)

public class JsonResult<T> {

    private boolean success;
    private String msg;
    private int errorCode;
    private T record;

    public JsonResult(JSONObject jsonResult, Type clsType) {
        success = jsonResult.optBoolean("success");
        msg = jsonResult.optString("msg");
        errorCode = jsonResult.optInt("errorCode");
        Gson gson = new Gson();
        if (clsType == JSONObject.class) {
            record = (T) jsonResult.optJSONObject("record");
        } else if (clsType == String.class) {
            record = (T) jsonResult.optString("record");
        } else {
            record = (T) gson.fromJson(jsonResult.optString("record"), clsType);
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getRecord() {
        return record;
    }

    public void setRecord(T record) {
        this.record = record;
    }
}
