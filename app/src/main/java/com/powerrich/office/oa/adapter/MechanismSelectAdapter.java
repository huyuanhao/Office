package com.powerrich.office.oa.adapter;

import android.content.Context;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.common.ViewHolder;

import java.util.List;

public class MechanismSelectAdapter extends CommonAdapter<String> {
    public MechanismSelectAdapter(Context mContext, List<String> mData, int mItemLayoutId) {
        super(mContext, mData, mItemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, String item) {
        holder.setTextView(R.id.tv_spinner,item);
    }
}
