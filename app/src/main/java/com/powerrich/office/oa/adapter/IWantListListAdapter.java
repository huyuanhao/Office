package com.powerrich.office.oa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.IWantListBean;

import java.util.List;

    public class IWantListListAdapter extends BaseAdapter {

        private Context mContext;
        private List<IWantListBean.DATABeanX.DATABean> mData;

        public IWantListListAdapter(Context context, List<IWantListBean.DATABeanX.DATABean> data) {
            this.mContext = context;
            this.mData = data;
        }


        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            com.powerrich.office.oa.adapter.IWantListListAdapter.ViewHolder holder = null;
            if (null == view) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_do_thing, null);
                holder = new com.powerrich.office.oa.adapter.IWantListListAdapter.ViewHolder();
                holder.tv_query_title = (TextView) view.findViewById(R.id.tv_query_title);
                holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
                holder.tv_hour = (TextView) view.findViewById(R.id.tv_hour);
                holder.tv_query_state = (TextView) view.findViewById(R.id.tv_query_state);
                view.setTag(holder);
            } else {
                holder = (com.powerrich.office.oa.adapter.IWantListListAdapter.ViewHolder) view.getTag();
            }
            IWantListBean.DATABeanX.DATABean dataBean = mData.get(position);
            holder.tv_query_title.setText(dataBean.getTITLE());
            holder.tv_time.setText(dataBean.getCRETTIME().split(" ")[0]);
            holder.tv_hour.setText(dataBean.getCRETTIME().split(" ")[1]);
//            if (dataBean.getISREVERT().equals("0")) {
//                holder.tv_query_state.setText(mContext.getString(R.string.status_0));
//            } else if (dataBean.getISREVERT().equals("1")){
//                holder.tv_query_state.setText(mContext.getString(R.string.status_1));
//            } else if (dataBean.getISREVERT().equals("2")){
//                holder.tv_query_state.setText(mContext.getString(R.string.status_2));
//            }else if (dataBean.getISREVERT().equals("3")){
//                holder.tv_query_state.setText(mContext.getString(R.string.status_3));
//            }
            if (dataBean.getISREVERT().equals("1")) {
                holder.tv_query_state.setText(mContext.getString(R.string.status_4));
            } else if (dataBean.getISREVERT().equals("0")){
                holder.tv_query_state.setText(mContext.getString(R.string.status_5));
            }
            return view;
        }


        private class ViewHolder {
            private TextView tv_query_title;
            private TextView tv_time;
            private TextView tv_hour;
            private TextView tv_query_state;
        }


    }
