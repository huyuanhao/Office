package com.powerrich.office.oa.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.FamilyRegisterInfo;
import com.powerrich.office.oa.bean.QueryInfo;

public class FamilyRegisterListAdapter extends BaseAdapter {

	private Context mContext;
	private List<FamilyRegisterInfo> mData;

	public FamilyRegisterListAdapter(Context context,List<FamilyRegisterInfo> data) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.family_register_list_item, null);
            holder = new ViewHolder();
            holder.tv_query_title = (TextView) view.findViewById(R.id.tv_query_title);
            holder.tv_query_time = (TextView) view.findViewById(R.id.tv_query_time);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        FamilyRegisterInfo file = mData.get(position);
        holder.tv_query_title.setText(file.getTitle());
        holder.tv_query_time.setText(file.getTime());
        return view;
    }

	
	private class ViewHolder {
		private TextView tv_query_title;
		private TextView tv_query_time;
    }

	
}
