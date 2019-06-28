package com.powerrich.office.oa.common;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.tools.ImageLoad;
import com.powerrich.office.oa.view.BannerLayout;
import com.squareup.picasso.Picasso;

import java.io.File;

public class GlideImageLoader implements BannerLayout.ImageLoader {
    @Override
    public void displayImage(Context context, ImageView imageView, File file) {
        ImageLoad.setFile(context,file,imageView);
    }

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        ImageLoad.setUrl(context,path,imageView);
    }

}
