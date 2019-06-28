package com.powerrich.office.oa.fund.Adapter;

import android.support.annotation.Nullable;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.chad.library.adapter.base.BaseViewHolder;
import com.powerrich.office.oa.fund.bean.FundLhhBean;

import java.util.List;

/**
 * @author PC
 * @date 2019/06/24 17:02
 */
public class BankSelectAdapter extends BaseQuickAdapter< FundLhhBean.DataBean, BaseViewHolder > {
    public BankSelectAdapter(int layoutResId, @Nullable List< FundLhhBean.DataBean > data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FundLhhBean.DataBean item) {
        helper.setText(R.id.tv_spinner,item.getGrckzhkhyhmc());
    }
}
