package com.powerrich.office.oa.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.PolicyStatute;

public class PolicyUnscrambleAdapter extends BaseAdapter {

	private Context mContext;
	private List<PolicyStatute> mData;

	public PolicyUnscrambleAdapter(Context context,List<PolicyStatute> data) {
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
        ViewHolder holder = null;
        if (null == view) {
            view = LayoutInflater.from(mContext).inflate(R.layout.policy_unscramble_list_item, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
            holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        PolicyStatute policyStatute = mData.get(position);
        holder.tv_title.setText(policyStatute.getTitle());
        holder.tv_time.setText(policyStatute.getCreateTime());
        return view;
    }

	
	private class ViewHolder {
		private TextView tv_title;
		private TextView tv_time;
    }

	
}
