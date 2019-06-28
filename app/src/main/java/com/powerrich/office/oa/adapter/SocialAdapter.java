package com.powerrich.office.oa.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.ImgTxInfo;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.chad.library.adapter.base.BaseViewHolder;
import com.powerrich.office.oa.tools.ImageLoad;

import java.util.List;

public class SocialAdapter extends BaseQuickAdapter<ImgTxInfo,BaseViewHolder>{
    public SocialAdapter(int layoutResId, @Nullable List<ImgTxInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImgTxInfo item) {
        helper.setText(R.id.title,item.getTitle())
                .setImageResource(R.id.img,item.getImg());
    }
}
