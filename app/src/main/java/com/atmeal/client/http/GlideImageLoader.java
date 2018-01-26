package com.atmeal.client.http;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2018/1/18.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Picasso 加载图片简单用法
        Picasso.with(context).load((String) path).into(imageView);
    }
}
