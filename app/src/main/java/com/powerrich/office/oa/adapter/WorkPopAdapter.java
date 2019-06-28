package com.powerrich.office.oa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.powerrich.office.oa.R;

import org.w3c.dom.Entity;

import java.util.List;

/**
 * 办事popwindow 显示
 */
public class WorkPopAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> stringList;
    private final LayoutInflater inflater;
    private int index ;

    public WorkPopAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<String> stringList) {
        this.stringList = stringList;
        notifyDataSetChanged();
    }

    public void setData(List<String> stringList,int index) {
        this.index =index;
        this.stringList = stringList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return stringList != null ? stringList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.work_pop_item,parent,false);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (index==position) {
            viewHolder.tv_title.setTextColor(mContext.getResources().getColor(R.color.app_work_select));
        }else{
            viewHolder.tv_title.setTextColor(mContext.getResources().getColor(R.color.app_work_no_select));
        }

        String s = stringList.get(position);
        viewHolder.tv_title.setText( s);
        return convertView;
    }

    public class ViewHolder {
        TextView tv_title;
    }

}
