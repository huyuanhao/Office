package com.powerrich.office.oa.fund.Adapter;

import android.support.annotation.Nullable;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author PC
 * @date 2018/12/24 14:05
 */
public class FundBusinessAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public FundBusinessAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.more);
    }


}
