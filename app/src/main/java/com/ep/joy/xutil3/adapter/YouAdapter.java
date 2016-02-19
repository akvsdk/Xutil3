package com.ep.joy.xutil3.adapter;

import android.content.Context;
import android.view.View;

import com.ep.joy.xutil3.R;
import com.ep.joy.xutil3.entity.YourBean;
import com.ep.joy.xutil3.util.AutoAdapter;
import com.ep.joy.xutil3.util.ViewHolder;

import java.util.List;

/**
 * author   Joy
 * Date:  2016/2/19.
 * version:  V1.0
 * Description:
 */
public class YouAdapter extends AutoAdapter {

    public YouAdapter(Context context, List<?> list, int layoutID) {
        super(context, list, layoutID);
    }

    @Override
    public int getLayoutID(int position) {
        return R.layout.item;
    }

    @Override
    public void getView33(int position, View v, ViewHolder vh) {
        YourBean.ListEntity bean = (YourBean.ListEntity) list.get(position);
        vh.getTextView(R.id.name).setText(bean.getMacthName());
        vh.getTextView(R.id.time).setText(bean.getSignEndTime());
    }
}
