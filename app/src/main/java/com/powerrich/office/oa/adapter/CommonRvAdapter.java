package com.powerrich.office.oa.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yt.simpleframe.http.bean.entity.ReFundInfo;

import java.util.List;

/**
 * @author chenhao
 * @date 2018/7/17
 * 通用的RecyclerView适配器
 */
public abstract class CommonRvAdapter<T> extends RecyclerView.Adapter<ViewHolderRv> implements View.OnClickListener {

    protected List<T> mData;
    protected int mItemLayoutId;

    public CommonRvAdapter(List<T> mData, int mItemLayoutId) {
        this.mData = mData;
        this.mItemLayoutId = mItemLayoutId;
    }



    public void setData(List<T> data){
        mData = data;

        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mData;
    }

    @Override
    public ViewHolderRv onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutId, parent, false);
        //设置item点击事件
        view.setOnClickListener(this);
        //实例化一个ViewHolder
        return ViewHolderRv.getHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRv holder, int position) {
        holder.itemView.setTag(position);
        convert(holder, mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public T getItem(int position) {
        return mData == null ? null : mData.get(position);
    }
    /**
     * 对外公布了一个convert方法，并且还把ViewHolder和本item对应的Bean对象给传出去
     * 现在convert方法里面需要干嘛呢？通过ViewHolder把View找到，通过Item设置值
     */
    public abstract void convert(ViewHolderRv holder, T item, int position);

    /** 设置点击事件回调*/
    @Override
    public void onClick(View v) {
        if (null != mItemClickListener) {
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    private OnItemClickListener mItemClickListener;

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
