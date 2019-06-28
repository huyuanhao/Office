package com.powerrich.office.oa.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.MailBoxInfo;

public class WardenMailListAdapter extends BaseAdapter {

	private Context mContext;
	private List<MailBoxInfo> mData;

	public WardenMailListAdapter(Context context,List<MailBoxInfo> data) {
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
	public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == view) {
            view = LayoutInflater.from(mContext).inflate(R.layout.warden_mail_list_item, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv_responsibility = (TextView) view.findViewById(R.id.tv_responsibility);
            holder.tv_write_to_me = (TextView) view.findViewById(R.id.tv_write_to_me);
            holder.tv_detail = (TextView) view.findViewById(R.id.tv_detail);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        MailBoxInfo file = mData.get(position);
        holder.tv_name.setText(file.getName());
        holder.tv_responsibility.setText(file.getResponsibility());
        holder.tv_write_to_me.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (null != mContext && mContext instanceof WidgetClick) {
	        		((WidgetClick) mContext).click(position);
	        	}
			}
		});
        return view;
    }

	
	private class ViewHolder {
		private TextView tv_name;
		private TextView tv_responsibility;
		private TextView tv_write_to_me;
		private TextView tv_detail;
    }
	public interface WidgetClick {
		void click(int position);
	}
}
