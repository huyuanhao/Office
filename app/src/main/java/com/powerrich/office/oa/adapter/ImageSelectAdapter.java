package com.powerrich.office.oa.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.chad.library.adapter.base.BaseViewHolder;
import com.powerrich.office.oa.tools.ImageLoad;

import java.util.List;

/**
 * Created by Administrator on 2018/6/11.
 */

public class ImageSelectAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public ImageSelectAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if(item.equals("")){
            helper.setImageResource(R.id.img,R.drawable.icon_boxmore_add);
        }else {
            ImageLoad.setUrl(mContext,item,(ImageView)helper.getView(R.id.img));
        }
    }
}
