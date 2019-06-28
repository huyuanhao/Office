package com.powerrich.office.oa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.tools.AutoUtils;
import com.powerrich.office.oa.tools.DateUtils;
import com.yt.simpleframe.http.bean.SocialInfo;

import java.util.List;

public class ExpandableMedicalRecordsAdapter extends BaseExpandableListAdapter {
    private List<SocialInfo> list;
    private Context mContext;

    public ExpandableMedicalRecordsAdapter(Context mContext, List<SocialInfo> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return list.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return list.get(i);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean isExpanded, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_medical_group, viewGroup, false);
            AutoUtils.auto(view);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            groupViewHolder.img = (ImageView) view.findViewById(R.id.img);
            groupViewHolder.statu = (TextView) view.findViewById(R.id.statu);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        groupViewHolder.statu.setText(list.get(i).getBkc002());
        if(isExpanded){
            groupViewHolder.img.setBackgroundResource(R.drawable.expand_down);
        }else {
            groupViewHolder.img.setBackgroundResource(R.drawable.expand_right);
        }
        if(list.get(i).getBkc002().equals("审核通过")){
            groupViewHolder.statu.setTextColor(Color.parseColor("#10B940"));
        }else if(list.get(i).getBkc002().equals("审核中")){
            groupViewHolder.statu.setTextColor(Color.parseColor("#F31E1E"));
        }else {
            groupViewHolder.statu.setTextColor(Color.parseColor("#000000"));
        }
        String address = "";
        if(list.get(i).getAae006().contains("省")){
            address = list.get(i).getAae006().substring(list.get(i).getAae006().indexOf("省") + 1);
        }
        groupViewHolder.tvTitle.setText(DateUtils.getDateStr(list.get(i).getAae127(),"yyyy-MM-dd","yyyyMMdd") + "—就医地点:" + address);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean isExpanded, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_medical_childview, viewGroup, false);
            AutoUtils.auto(view);
            childViewHolder = new ChildViewHolder();
            childViewHolder.name = (TextView) view.findViewById(R.id.name);
            childViewHolder.type = (TextView) view.findViewById(R.id.type);
            childViewHolder.date_apply = (TextView) view.findViewById(R.id.date_apply);
            childViewHolder.reason = (TextView) view.findViewById(R.id.reason);
            childViewHolder.state = (TextView) view.findViewById(R.id.state);
            childViewHolder.date = (TextView) view.findViewById(R.id.date);
            childViewHolder.hos1 = (TextView) view.findViewById(R.id.hos1);
            childViewHolder.hos2 = (TextView) view.findViewById(R.id.hos2);
            childViewHolder.address = (TextView) view.findViewById(R.id.address);
            childViewHolder.phone = (TextView) view.findViewById(R.id.phone);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        childViewHolder.name.setText(list.get(i).getAac003());
        childViewHolder.type.setText(list.get(i).getAka083());
        childViewHolder.date_apply.setText(list.get(i).getAae127());
        childViewHolder.reason.setText(list.get(i).getAkc030());
        childViewHolder.state.setText(list.get(i).getBkc002());
        childViewHolder.date.setText(list.get(i).getAae034());
        childViewHolder.hos1.setText(list.get(i).getBkb050() + "(" + list.get(i).getBkb023() + ")");
        childViewHolder.hos2.setText(list.get(i).getBkb051() + "(" + list.get(i).getBkb024() + ")");
        childViewHolder.address.setText(list.get(i).getAae006());
        childViewHolder.phone.setText(list.get(i).getAae005());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    static class GroupViewHolder {
        TextView tvTitle;
        ImageView img;
        TextView statu;
    }

    static class ChildViewHolder {
        TextView name;
        TextView type;
        TextView date_apply;
        TextView reason;
        TextView state;
        TextView date;
        TextView hos1;
        TextView hos2;
        TextView address;
        TextView phone;
    }
}
