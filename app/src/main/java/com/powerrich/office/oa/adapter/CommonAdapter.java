package com.powerrich.office.oa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.powerrich.office.oa.common.ViewHolder;

import java.util.List;

/**
 * @author MingPeng
 * @date 2017/10/20
 * 通用的适配器
 * 1、所有的Adapter的第一行（ViewHolder viewHolder = getViewHolder(position, convertView,parent);）和 最后一行：return viewHolder.getConvertView();一定是一样的。
 * 2、那么我们可以这样做：我们把第一行和最后一行写死，把中间变化的部分抽取出来，这不就是OO的设计原则嘛。
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected LayoutInflater mLayoutInflater;
    protected Context mContext;
    protected List<T> mData;
    protected int mItemLayoutId;
    protected int mPosition;

    public CommonAdapter(Context mContext, List<T> mData, int mItemLayoutId) {
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mData = mData;
        this.mItemLayoutId = mItemLayoutId;
    }

    public void setData(List<T> data){
        mData=data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.mPosition = position;
        //实例化一个ViewHolder
        ViewHolder holder = getViewHolder(position, convertView, parent);
        //使用对外公开的convert方法，通过ViewHolder把View找到，通过Item设置值
        convert(holder, getItem(position));
        return holder.getmConvertView();
    }

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent){
        return ViewHolder.getHolder(mContext, mItemLayoutId, convertView, parent);
    }

    public int getPosition(T item) {
        return mData == null ? 0 : mData.indexOf(item);
    }

    /**
     * 对外公布了一个convert方法，并且还把ViewHolder和本item对应的Bean对象给传出去
     * 现在convert方法里面需要干嘛呢？通过ViewHolder把View找到，通过Item设置值
     */
    public abstract void convert(ViewHolder holder, T item);

}
