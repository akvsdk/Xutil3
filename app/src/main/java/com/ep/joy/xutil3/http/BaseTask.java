package com.ep.joy.xutil3.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Handler;

import com.ep.joy.xutil3.entity.JsonResult;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;


/**
 * BaseTask 异步任务基类
 *
 * @author 杨建洪
 * @param <E>
 * @param <T>
 */
public abstract class BaseTask<E,T> extends MyCallBack<T> {
	//private final static String tag = BaseTask.class.getName();
	protected Context context;
	protected ProgressDialog loadMask;

	private Handler mHandler ;
	public E result;


	public BaseTask(Context context) {
		this.context = context;
	}

	public BaseTask(Context context, String loadingMsg) {
		this(context);
		loadMask=new ProgressDialog(context);
		loadMask.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		loadMask.setMessage(loadingMsg);
		loadMask.show();
		// 点击后退按钮能取消dialog
		this.loadMask.setCancelable(true);
		// 点击空白地方不能取消dialog
		this.loadMask.setCanceledOnTouchOutside(false);
		this.loadMask.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface arg0) {
				BaseTask.this.doCancel();
			}
		});
	}

	public void doCancel() {
		if(loadMask != null && loadMask.isShowing()){
			loadMask.dismiss();
		}
	}

	public Cancelable requestByPost(String url,Map<String,Object> map){
		RequestParams params=new RequestParams(url);
		if(null!=map){
			for(Map.Entry<String, Object> entry : map.entrySet()){
				params.addParameter(entry.getKey(), entry.getValue());
			}
		}
		Cancelable cancelable = x.http().post(params,this);
		return cancelable;
		}


	@Override
	public void onSuccess(T myresult) {
		try {
			JSONObject jsonObject = new JSONObject((String) myresult);
			result = (E) new JsonResult(jsonObject,setTypeToken().getType()){} ;
			doCancel();

			onSuccess();

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}




	public abstract TypeToken setTypeToken();




	public abstract void onSuccess();
}
