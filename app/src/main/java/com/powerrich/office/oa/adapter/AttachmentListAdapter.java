package com.powerrich.office.oa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.FileListBean;

import java.util.List;

public class AttachmentListAdapter extends BaseAdapter {

	private Context mContext;
	private List<FileListBean> mData;

	public AttachmentListAdapter(Context context,List<FileListBean> data) {
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
        ViewHolder holder;
        if (null == view) {
            view = LayoutInflater.from(mContext).inflate(R.layout.attachment_list_item, null);
            holder = new ViewHolder();
			holder.tv_comp_file_name = (TextView) view.findViewById(R.id.tv_comp_file_name);
			holder.tv_file_name = (TextView) view.findViewById(R.id.tv_file_name);
            holder.iv_download = (ImageView) view.findViewById(R.id.iv_download);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
		FileListBean file = mData.get(position);
        holder.tv_comp_file_name.setText(file.getCompFileName());
        holder.tv_file_name.setText(file.getFileName());
      //"1"为文件存在，否则为不存在
        if ("1".equals(file.getFileState())) {
			holder.iv_download.setImageResource(R.drawable.fj_icon_05);
		} else {
			holder.iv_download.setImageResource(R.drawable.fj_icon_02);
		}
        return view;
    }

	
	private class ViewHolder {
		private TextView tv_comp_file_name;
		private TextView tv_file_name;
		private ImageView iv_download;
    }

	
}
