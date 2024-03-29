package com.powerrich.office.oa.common;

import android.content.Context;
import android.widget.ImageView;

import com.powerrich.office.oa.view.BannerLayout;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public class PicassoImageLoader implements BannerLayout.ImageLoader {
    @Override
    public void displayImage(Context context, ImageView imageView, File file) {
        Picasso.with(context).load(file).into(imageView);
    }

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Picasso.with(context).load(path).into(imageView);
    }

}
