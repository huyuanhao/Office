package com.powerrich.office.oa.fund.Adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.chad.library.adapter.base.BaseViewHolder;
import com.powerrich.office.oa.fund.bean.FundItemBean;

import java.util.List;

/**
 * @author PC
 * @date 2019/04/12 11:56
 */
public class FundItemAdapter extends BaseQuickAdapter<FundItemBean,BaseViewHolder> {
    public FundItemAdapter(int layoutResId, @Nullable List<FundItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FundItemBean item) {
        helper.setText(R.id.tv_title,item.getTitle())
                .setText(R.id.tv_info,item.getInfo());
    }
}
