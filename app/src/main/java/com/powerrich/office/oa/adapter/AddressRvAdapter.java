package com.powerrich.office.oa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.AddressDetailActivity;
import com.powerrich.office.oa.bean.AddressListBean;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：白煜
 * 时间：2017/11/30 0030
 * 版权：
 */

public class AddressRvAdapter extends RecyclerView.Adapter<AddressRvAdapter.ViewHolder> {

    private List<AddressListBean.DATABean> mList;
    private Context mContext;

    public interface onItemDeleteListener{
        void onItemDeleteListener(AddressListBean.DATABean deleteData);
    }

    private onItemDeleteListener mOnItemDeleteListener;

    public void setOnItemDeleteListener(onItemDeleteListener onItemDeleteListener) {
        mOnItemDeleteListener = onItemDeleteListener;
    }

    public AddressRvAdapter(List<AddressListBean.DATABean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_rv_address, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if("1".equals(mList.get(position).getISDEFAULT())){
            holder.ll_item.setBackgroundResource(R.drawable.bg_address_item);
            mList.get(position).setDefault(true);
            holder.rb_address.setChecked(true);
        }else{
            holder.ll_item.setBackgroundResource(R.color.white);
            mList.get(position).setDefault(false);
            holder.rb_address.setChecked(false);
        }

        holder.tv_address.setText(mList.get(position).getADDRESS());
        holder.tv_name.setText(mList.get(position).getSJRXM());
        holder.tv_phone.setText(mList.get(position).getHANDSET());
        holder.rb_address.setChecked(mList.get(position).isDefault());
        holder.rb_address.setClickable(false);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (AddressListBean.DATABean dataBean : mList) {
//                    dataBean.setDefault(false);
//                }
//                mList.get(position).setDefault(true);
//                notifyDataSetChanged();
//            }
//        });

        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddressDetailActivity.class);
                intent.putExtra("type",AddressDetailActivity.TYPE_EDIT);
                intent.putExtra("address_data",mList.get(position));
                mContext.startActivity(intent);
            }
        });

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemDeleteListener.onItemDeleteListener(mList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public RadioButton rb_address;
        public TextView tv_edit;
        public TextView tv_delete;
        public TextView tv_name;
        public TextView tv_phone;
        public TextView tv_address;

        public LinearLayout ll_item;

        public ViewHolder(View itemView) {
            super(itemView);
            rb_address = (RadioButton) itemView.findViewById(R.id.rb_address);
            tv_edit = (TextView) itemView.findViewById(R.id.tv_edit);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }
}
