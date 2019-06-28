package com.powerrich.office.oa.adapter;

import android.content.Context;
import android.util.Log;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.MaterialsInfo;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.tools.BeanUtils;

import java.util.List;

/**
 * 文 件 名：MaterialsAdapter
 * 描   述：办理材料适配器
 * 作   者：Wangzheng
 * 时   间：2018-7-30 16:27:02
 * 版   权：v1.0
 */
public class MaterialsAdapter extends CommonAdapter<MaterialsInfo> {

    public MaterialsAdapter(Context mContext, List<MaterialsInfo> mData, int mItemLayoutId) {
        super(mContext, mData, mItemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, MaterialsInfo item) {
        holder.setTextView(R.id.tv_table01, item.getMaterialName());
        holder.setTextView(R.id.tv_table02, BeanUtils.isEmptyStr(item.getMaterialCopies()) ? "0" : item.getMaterialCopies());
        holder.setTextView(R.id.tv_table03, item.getMaterialNecessity().equals("1") ? "必要" : "不必要");
        Log.i("jsc", "形式convert: "+item.getMaterialForm());
        if (!BeanUtils.isNullOrEmpty(item.getMaterialForm())) {
            holder.setTextView(R.id.tv_table04, BeanUtils.getMaterialForm(mContext, String.valueOf(Integer.parseInt(item.getMaterialForm()) - 1)));
        }
        holder.setTextView(R.id.tv_table05, item.getMaterialType().equals("0") ? "附件" : "表单");
    }
}
