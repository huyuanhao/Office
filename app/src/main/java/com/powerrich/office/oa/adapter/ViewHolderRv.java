package com.powerrich.office.oa.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.tools.AutoUtils;

/**
 * @author chenhao
 * @date 2018/7/17
 * RecyclerView通用的ViewHolder
 */
public class ViewHolderRv extends RecyclerView.ViewHolder{

    private SparseArray<View> mViews = new SparseArray<>();
    private View mConvertView;

    //构造函数
    private ViewHolderRv(View itemView) {
        super(itemView);
        this.mConvertView = itemView;
        AutoUtils.auto(mConvertView);
    }

    //获取一个ViewHolder
    public static ViewHolderRv getHolder(View view) {
        ViewHolderRv holder = new ViewHolderRv(view);
        return holder;
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

    /**
     * 为TextView赋值
     */
    public void setText(int viewId, String text) {
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
