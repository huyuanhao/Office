package com.powerrich.office.oa.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.QueryInfo;

public class QueryTabInfoAdapter extends BaseAdapter {

	private Context mContext;
	private List<QueryInfo> mData;

	public QueryTabInfoAdapter(Context context,List<QueryInfo> data) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.query_tab_info_item, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv_content = (TextView) view.findViewById(R.id.tv_content);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        QueryInfo file = mData.get(position);
        holder.tv_name.setText(file.getItemName());
        holder.tv_content.setText(file.getContent());
        return view;
    }

	
	private class ViewHolder {
		private TextView tv_name;
		private TextView tv_content;
    }

	
}
