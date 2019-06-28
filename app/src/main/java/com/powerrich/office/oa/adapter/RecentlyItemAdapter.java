package com.powerrich.office.oa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.RecentlyItemBean;
import com.powerrich.office.oa.tools.AutoUtils;

import java.util.List;

/**
 * 文 件 名：RecentlyItemAdapter
 * 描   述：最近使用事项适配器
 * 作   者：Wangzheng
 * 时   间：2018-7-10 09:36:33
 * 版   权：v1.0
 */
public class RecentlyItemAdapter extends BaseAdapter {

    private Context context;
    private List<RecentlyItemBean> itemBeans;
    private int[] res = {R.drawable.icon_married_register, R.drawable.icon_id_card_reapply, R.drawable.icon_housing_loans, R.drawable.icon_health_certificate_transact};

    public RecentlyItemAdapter(Context context, List<RecentlyItemBean> itemBeans) {
        this.context = context;
        this.itemBeans = itemBeans;
    }

    @Override
    public int getCount() {
        return itemBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return itemBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.recently_item, null);
            AutoUtils.auto(view);
            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.image);
            holder.text = (TextView) view.findViewById(R.id.text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.image.setImageResource(R.drawable.icon_work_rencently);
        holder.text.setText(itemBeans.get(position).getItemName());
        return view;
    }

    private class ViewHolder {
        private ImageView image;
        private TextView text;
    }
}
