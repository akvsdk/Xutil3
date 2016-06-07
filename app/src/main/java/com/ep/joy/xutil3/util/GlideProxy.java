package com.ep.joy.xutil3.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ep.joy.xutil3.R;


/**
 * author meikoz on 2016/4/13.
 * email  meikoz@126.com
 */
public class GlideProxy {

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    private GlideProxy() {
    }

    private static class GlideControlHolder {
        private static GlideProxy instance = new GlideProxy();
    }

    public static GlideProxy getInstance() {
        return GlideControlHolder.instance;
    }

    // 将资源ID转为Uri
    public Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }


    // 加载drawable图片
    public void loadResImage(Context context, int resId, ImageView imageView) {
        Glide.with(context)
                .load(resourceIdToUri(context, resId))
                .placeholder(R.color.material_white)
                .error(R.color.material_white)
                .crossFade()
                .into(imageView);
    }

    // 加载本地图片
    public void loadLocalImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load("file://" + path)
                .placeholder(R.color.material_white)
                .error(R.color.material_white)
                .crossFade()
                .into(imageView);
    }

    // 加载网络图片
    public void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .animate(R.anim.image_load)
                .placeholder(R.color.material_white)
                .error(R.color.material_white)
                .crossFade()
                .into(imageView);
    }


    // 加载网络圆型图片
    public void loadCircleImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .animate(R.anim.image_load)
                .placeholder(R.color.material_white)
                .error(R.color.material_white)
                .crossFade()
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }

    // 加载drawable圆型图片
    public void loadCircleResImage(Context context, int resId, ImageView imageView) {
        Glide.with(context)
                .load(resourceIdToUri(context, resId))
                .placeholder(R.color.material_white)
                .error(R.color.material_white)
                .crossFade()
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }

    // 加载本地圆型图片
    public void loadCircleLocalImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load("file://" + path)
                .placeholder(R.color.material_white)
                .error(R.color.material_white)
                .crossFade()
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }

    // 清理缓存
    public static void clear(Context context) {
        Glide.get(context).clearMemory();
    }

}
