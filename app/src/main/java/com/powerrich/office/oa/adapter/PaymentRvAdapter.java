package com.powerrich.office.oa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.PaymentListBean;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：白煜
 * 时间：2017/12/6 0006
 * 版权：
 */

public class PaymentRvAdapter extends RecyclerView.Adapter<PaymentRvAdapter.ViewHolder> {

    private Context mContext;
    private List<PaymentListBean.DATABean> mList;

    public PaymentRvAdapter(Context context, List<PaymentListBean.DATABean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_payment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_name.setText(mList.get(position).getSQXMNAME());
        holder.tv_payment_money.setText(mList.get(position).getSSZJE());
        if ("0".equals(mList.get(position).getSTATUS())) {
            holder.tv_payment_state.setText(mContext.getString(R.string.payment_no));
        } else if ("1".equals(mList.get(position).getSTATUS())) {
            holder.tv_payment_state.setText(mContext.getString(R.string.payment_yes));
        } else if ("2".equals(mList.get(position).getSTATUS())) {
            holder.tv_payment_state.setText(mContext.getString(R.string.payment_fail));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        public TextView tv_payment_money;
        public TextView tv_payment_state;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_payment_money = (TextView) itemView.findViewById(R.id.tv_payment_money);
            tv_payment_state = (TextView) itemView.findViewById(R.id.tv_payment_state);
        }
    }
}
