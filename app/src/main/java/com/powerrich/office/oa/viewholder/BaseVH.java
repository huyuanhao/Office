package com.powerrich.office.oa.viewholder;


import android.view.View;

import com.yt.simpleframe.view.recyclerview.viewholder.RecycleviewViewHolder;

import butterknife.Unbinder;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/7
 * 版权：
 */

public class BaseVH  extends RecycleviewViewHolder {
    protected Unbinder unbinder;

    public BaseVH(View view) {
        super(view);
    }

    protected void unbinder(){
        if(unbinder != null){

        }
    }
}
