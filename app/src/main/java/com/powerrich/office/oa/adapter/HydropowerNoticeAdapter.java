package com.powerrich.office.oa.adapter;

import android.support.annotation.Nullable;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class HydropowerNoticeAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    public HydropowerNoticeAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_title,"·" + "通知")
                .setText(R.id.tv_time,"2018-01-01");
    }
}
