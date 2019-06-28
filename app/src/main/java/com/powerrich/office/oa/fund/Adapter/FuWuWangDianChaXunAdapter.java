package com.powerrich.office.oa.fund.Adapter;

import android.support.annotation.Nullable;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.chad.library.adapter.base.BaseViewHolder;
import com.powerrich.office.oa.fund.bean.FundFunctionBean;
import com.powerrich.office.oa.fund.bean.FuwuWangdianBean;
import com.powerrich.office.oa.tools.DateUtils;

import java.util.List;

/**
 * @author PC
 * @date 2018/12/24 14:05
 */
public class FuWuWangDianChaXunAdapter extends BaseQuickAdapter<FundFunctionBean,BaseViewHolder> {
    public FuWuWangDianChaXunAdapter(int layoutResId, @Nullable List<FundFunctionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FundFunctionBean item) {
        String year = DateUtils.getDateStr(item.getYwfsrq(),"yyyy-MM-dd","yyyy");
        helper.setText(R.id.tv_year,year);

        if(helper.getAdapterPosition()>0) {
            if (mData.get(helper.getAdapterPosition()-1).getYwfsrq().contains(year)) {
                helper.setGone(R.id.tv_year, false);
            } else {
                helper.setGone(R.id.tv_year, true);
            }
        }else {
            helper.setGone(R.id.tv_year, true);
        }
        helper.setText(R.id.tv1,item.getYwzy());
        helper.setText(R.id.tv2,item.getFse());
        helper.setText(R.id.tv3,DateUtils.getDateStr(item.getYwfsrq(),"yyyy-MM-dd","MM-dd"));
    }
}
