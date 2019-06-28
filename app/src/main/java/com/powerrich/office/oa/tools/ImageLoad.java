package com.powerrich.office.oa.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.powerrich.office.oa.R;
import com.squareup.picasso.Transformation;

import java.io.File;

/**
 * Created by Administrator on 2018/6/13.
 */

public class ImageLoad {

    public static void setFile(Context context, File file, ImageView viewId) {
        Glide.with(context)
                .load(file)
                .centerCrop()
                .placeholder(R.drawable.ic_default)
                .error(R.mipmap.ic_launcher)
                .dontAnimate()
                .into(viewId);
    }

    public static void setUrl(Context context, String url, ImageView viewId) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .dontAnimate()
                .into(viewId);
//        Picasso.with(context).load(url).fit().centerCrop().into(viewId);
    }


    public static void setCircleUrl(Context context, String url, ImageView viewId, int defaultRes) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(defaultRes)
                .error(defaultRes)
                .transform(new GlideCircleTransform(context))
                .dontAnimate()
                .into(viewId);
//        Picasso.with(context).load(url).fit().centerCrop().into(viewId);
    }


    public static void setUrlImg(Context context, String url, ImageView viewId) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_default)
                .error(R.mipmap.ic_launcher)
                .dontAnimate()
                .into(viewId);
//        Picasso.with(context).load(url).fit().centerCrop().into(viewId);
    }

    public static void setUrlImgCc(Context context, String url, ImageView viewId, final InterfaceImageListener imageListener) {
        Glide.with(context)
                .load(url)
                .error(R.mipmap.ic_error)
                .placeholder(R.drawable.ic_default)
                .dontAnimate()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        if(imageListener!=null){
                            imageListener.onError();
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(viewId);
//        Picasso.with(context).load(url).fit().centerCrop().into(viewId);
    }

    public  interface  InterfaceImageListener{
        void onError();
    }

    public static void setUrl(Context context, String url, ImageView viewId, int defaultRes) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(defaultRes)
                .error(defaultRes)
                .dontAnimate()
                .into(viewId);
//        Picasso.with(context).load(url).fit().centerCrop().into(viewId);
    }

    //    public static void setImage(Context context,String url,ImageView viewId){
//        Picasso.with(context).load(new File(url)).fit().centerCrop().into(viewId);
//    }
    public static Transformation getTransformation(final ImageView view) {
        return new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                int targetWidth = view.getWidth();

                //返回原图
                if (source.getWidth() == 0 || source.getWidth() < targetWidth) {
                    return source;
                }

                //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                if (targetHeight == 0 || targetWidth == 0) {
                    return source;
                }
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };
    }

    //圆形图片
    public static class GlideCircleTransform extends BitmapTransformation {
        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

}
