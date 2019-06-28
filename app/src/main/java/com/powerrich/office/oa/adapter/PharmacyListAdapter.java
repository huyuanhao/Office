package com.powerrich.office.oa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.IWantSuggestActivity;
import com.powerrich.office.oa.activity.OnlineDeclareActivity;
import com.powerrich.office.oa.bean.PharmacyInfo;
import com.powerrich.office.oa.tools.Constants;

import java.util.List;
/**
 * 开药店列表适配器
 * @author Administrator
 *
 */
public class PharmacyListAdapter extends BaseAdapter {

	private Context mContext;
	private List<PharmacyInfo> mData;

	public PharmacyListAdapter(Context context,List<PharmacyInfo> data) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.pharmacy_list_item, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
            holder.tv_dept = (TextView) view.findViewById(R.id.tv_dept);
            holder.tv_declare = (TextView) view.findViewById(R.id.tv_declare);
            holder.tv_material = (TextView) view.findViewById(R.id.tv_material);
            holder.tv_consulting = (TextView) view.findViewById(R.id.tv_consulting);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final PharmacyInfo file = mData.get(position);
        holder.tv_title.setText(file.getPkName());
        holder.tv_dept.setText(file.getPkdeptName());
        holder.tv_declare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//跳转到在线申诉
        		Intent intent = new Intent(mContext,OnlineDeclareActivity.class);
        		intent.putExtra("tag_id", file.getSxId());
        		mContext.startActivity(intent);
			}
		});
        holder.tv_material.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {

        	}
        });
        holder.tv_consulting.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		//跳转到我要咨询
        		Intent intent = new Intent(mContext,IWantSuggestActivity.class);
        		intent.putExtra("iwant_type", Constants.CONSULTING_TYPE);
        		intent.putExtra("dep_id", file.getDeptId());
        		intent.putExtra("dep_name", file.getPkdeptName());
        		mContext.startActivity(intent);
        	}
        });
        return view;
    }

	
	private class ViewHolder {
		private TextView tv_title;
		private TextView tv_dept;
		private TextView tv_declare;
		private TextView tv_material;
		private TextView tv_consulting;
    }

	
}
