package com.powerrich.office.oa.adapter;

import android.support.annotation.Nullable;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.ItemBean;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by hasee on 2018/7/20.
 */

public class ServiceListAdapter extends BaseQuickAdapter<ItemBean, BaseViewHolder> {


    public ServiceListAdapter(int layoutResId, @Nullable List<ItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemBean item) {
        helper.setImageResource(R.id.item_image, item.getImageRes());
        helper.setText(R.id.item_title, item.getTitle());
        helper.setText(R.id.item_content, item.getContent());
    }
}
