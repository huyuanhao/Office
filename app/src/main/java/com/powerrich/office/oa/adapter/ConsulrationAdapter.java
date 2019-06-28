package com.powerrich.office.oa.adapter;

import android.support.annotation.Nullable;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.chad.library.adapter.base.BaseViewHolder;
import com.powerrich.office.oa.common.ResultItem;

import java.util.List;

/**
 * Created by Administrator on 2018/6/7.
 */

public class ConsulrationAdapter extends BaseQuickAdapter<ResultItem,BaseViewHolder> {
    public ConsulrationAdapter(int layoutResId, @Nullable List<ResultItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ResultItem item) {
        helper.setText(R.id.tv_title,item.getString("title"));
        helper.setText(R.id.tv_time,item.getString("time"));
    }
}
