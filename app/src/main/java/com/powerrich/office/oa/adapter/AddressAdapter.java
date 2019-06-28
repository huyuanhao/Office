package com.powerrich.office.oa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.tools.AutoUtils;
import com.yt.simpleframe.http.bean.entity.AddressInfo;

import java.util.List;

/**
 * 文 件 名：AddressAdapter
 * 描   述：地址适配器
 * 作   者：Wangzheng
 * 时   间：2018-7-4 16:08:04
 * 版   权：v1.0
 */
public class AddressAdapter extends BaseAdapter {

    private Context context;
    private List<AddressInfo> addressInfoList;
    private int selected = -1;

    public AddressAdapter(Context context, List<AddressInfo> addressInfoList) {
        this.context = context;
        this.addressInfoList = addressInfoList;
    }

    public void setSelected(int position) {
        selected = position;
    }

    public int getSelected() {
        return selected;
    }

    @Override
    public int getCount() {
        return addressInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return addressInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.address_item, null);
            AutoUtils.auto(view);
            holder = new ViewHolder();
            holder.tv_default = (TextView) view.findViewById(R.id.tv_default);
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv_phone_number = (TextView) view.findViewById(R.id.tv_phone_number);
            holder.tv_company = (TextView) view.findViewById(R.id.tv_company);
            holder.tv_address = (TextView) view.findViewById(R.id.tv_address);
            holder.tv_modify = (TextView) view.findViewById(R.id.tv_modify);
            holder.rb_select = (RadioButton) view.findViewById(R.id.rb_select);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final AddressInfo addressInfo = addressInfoList.get(position);
        holder.tv_name.setText(addressInfo.getSJRXM());
        holder.tv_phone_number.setText(addressInfo.getHANDSET());
        holder.tv_company.setText(addressInfo.getCOMPANY_NAME());
        holder.tv_address.setText(addressInfo.getADDRESS());
        if (addressInfo.isDefault()) {
            selected = position;
        }
        if ("1".equals(addressInfo.getISDEFAULT())) {
            holder.tv_default.setVisibility(View.VISIBLE);
        } else {
            holder.tv_default.setVisibility(View.GONE);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressInfo.setDefault(!addressInfo.isDefault());
                if (selected != -1 && selected != position) {
                    addressInfoList.get(selected).setDefault(false);
                }
                selected = position;
                notifyDataSetChanged();
                clickListener.onItemClick(position);
            }
        });

        if (selected == position) {
            // 选中的条目和当前的条目是否相等
            holder.rb_select.setChecked(true);
        } else {
            holder.rb_select.setChecked(false);
        }
        holder.tv_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
        return view;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener clickListener;

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface IModifyListener {
        void onClick(int position);
    }

    private IModifyListener listener;

    public void setOnClickListener(IModifyListener listener) {
        this.listener = listener;
    }

    private class ViewHolder {
        private TextView tv_default;
        private TextView tv_name;
        private TextView tv_phone_number;
        private TextView tv_company;
        private TextView tv_address;
        private TextView tv_modify;
        private RadioButton rb_select;
    }
}
