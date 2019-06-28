package com.powerrich.office.oa.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.tools.AutoUtils;

/**
 * @author MingPeng
 * @date 2017/10/20
 * 通用的ViewHolder
 * 1、ViewHolder的作用是通过convertView.setTag与convertView进行绑定，然后当convertView复用时，直接从与之对于的ViewHolder(getTag)中拿到convertView布局中的控件，省去了findViewById的时间；
 * 2、也就是说，实际上们每个convertView会绑定一个ViewHolder对象，这个viewHolder主要用于帮convertView存储布局中的控件；
 * 3、那么我们只要写出一个通用的ViewHolder，然后对于任意的convertView，提供一个对象让其setTag即可；
 * 4、既然是通用，那么我们这个ViewHolder就不可能含有各种控件的成员变量了，因为每个Item的布局是不同的，最好的方式是什么呢？
 * 5、提供一个容器，专门存每个Item布局中的所有控件，而且还要能够查找出来；既然需要查找，那么ListView肯定是不行了，需要一个键值对进行保存，键为控件的Id，值为控件的引用；
 * 6、相信大家立刻就能想到Map；但是我们不用Map，因为有更好的替代类，就是我们android提供的SparseArray这个类，和Map类似，但是比Map效率，不过键只能为Integer.
 * <p/>
 * 最后的封装：
 * 我们现在在convertView里面需要这样:
 *
 * @Override public void convert(ViewHolder viewHolder, String item) {
 * TextView view = viewHolder.getView(R.id.id_tv_title);
 * view.setText(item);
 * }
 * 我们细想一下，其实布局里面的View常用也就那么几种：ImageView,TextView,Button,CheckBox等等；
 * 那么我觉得ViewHolder还可以封装一些常用的方法，比如setText(id,String)；setImageResource(viewId, resId)；setImageBitmap(viewId, bitmap)；
 */
public class ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;

    //构造函数
    private ViewHolder(Context context, int resLayoutId, ViewGroup parent) {
        this.mViews = new SparseArray<View>();
        this.mConvertView = LayoutInflater.from(context).inflate(resLayoutId, parent, false);
        AutoUtils.auto(mConvertView);
        this.mConvertView.setTag(this);
    }

    //获取一个ViewHolder
    public static ViewHolder getHolder(Context context, int resLayoutId, View convertView, ViewGroup parent) {
        if (convertView == null) {
            return new ViewHolder(context, resLayoutId, parent);
        }
        return (ViewHolder) convertView.getTag();
    }

    //通过控件的id获取对应的控件，如果没有则加入mViews;记住 <T extends View> T 这种用法
    public <T extends View> T getItemView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    //获得一个convertView
    public View getmConvertView() {
        return mConvertView;
    }

    /**
     * 为TextView赋值
     */
    public void setTextView(int viewId, String text) {
        TextView view = getItemView(viewId);
        view.setText(text);
    }

    /**
     * 为ImageView赋值——drawableId
     */
    public void setImageResource(int viewId, int drawableId) {
        ImageView view = getItemView(viewId);
        view.setImageResource(drawableId);
    }

    /**
     * 为ImageView赋值——bitmap
     */
    public void setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getItemView(viewId);
        view.setImageBitmap(bitmap);
    }

}
