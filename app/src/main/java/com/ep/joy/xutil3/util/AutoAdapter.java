package com.ep.joy.xutil3.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * author   Joy
 * Date:  2016/2/18.
 * version:  V1.0
 * Description:
 */
public abstract class AutoAdapter extends MBaseAdapter {



    public AutoAdapter(Context context, List<?> list, int layoutID) {
        super(context, list);

    }

    public abstract int getLayoutID(int position);



    @Override
    public View getView33(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(getLayoutID(position), parent, false);
        }
        getView33(position, convertView, ViewHolder.getViewHolder(convertView));
        return convertView;
    }


    public abstract void getView33(int position, View v, ViewHolder vh);


}

