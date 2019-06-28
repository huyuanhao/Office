package com.powerrich.office.oa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.ItemsInfo;
import com.powerrich.office.oa.tools.AutoUtils;

import java.util.List;

/**
 * 文 件 名：ItemsAdapter
 * 描   述：事项适配器
 * 作   者：Wangzheng
 * 时   间：2017/11/21
 * 版   权：v1.0
 */
public class ItemsAdapter extends BaseAdapter {

    private Context context;
    private List<ItemsInfo> itemsInfoList;

    private int selectedPosition = 0;// 选中的位置

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public ItemsAdapter(Context context, List<ItemsInfo> itemsInfoList) {
        this.context = context;
        this.itemsInfoList = itemsInfoList;
    }

    @Override
    public int getCount() {
        return itemsInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.my_work_gv_item, null);
            AutoUtils.auto(view);
            holder = new ViewHolder();
            holder.ll_bg = (LinearLayout) view.findViewById(R.id.ll_bg);
            holder.text = (TextView) view.findViewById(R.id.text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (selectedPosition == position) {
            holder.ll_bg.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            holder.ll_bg.setBackgroundColor(Color.parseColor("#ececec"));
        }
        holder.text.setText(itemsInfoList.get(position).getTAG_NAME());
        return view;
    }

    private class ViewHolder {
        private LinearLayout ll_bg;
        private TextView text;
    }
}
