package com.powerrich.office.oa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.BasicInfoFfcxBean;
import com.powerrich.office.oa.tools.AutoUtils;

import java.util.List;

public class ExpandableSocialPutAdapter extends BaseExpandableListAdapter {
    private List<BasicInfoFfcxBean.DATABeanX.DATABean> list;
    private Context mContext;

    public ExpandableSocialPutAdapter(Context mContext, List<BasicInfoFfcxBean.DATABeanX.DATABean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void setData(List<BasicInfoFfcxBean.DATABeanX.DATABean> list){
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_pay_group, viewGroup, false);
            AutoUtils.auto(view);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            groupViewHolder.img = (ImageView) view.findViewById(R.id.img);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        if(isExpanded){
            groupViewHolder.img.setBackgroundResource(R.drawable.expand_down);
        }else {
            groupViewHolder.img.setBackgroundResource(R.drawable.expand_right);
        }
        groupViewHolder.tvTitle.setText(list.get(i).getAae003() + "—发放" + list.get(i).getAae019());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_social_put_childview, viewGroup, false);
            AutoUtils.auto(view);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tv1 = (TextView) view.findViewById(R.id.tv_1);
            childViewHolder.tv2 = (TextView) view.findViewById(R.id.tv_2);
            childViewHolder.tv3 = (TextView) view.findViewById(R.id.tv_3);
            childViewHolder.tv4 = (TextView) view.findViewById(R.id.tv_4);
            childViewHolder.tv5 = (TextView) view.findViewById(R.id.tv_5);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        childViewHolder.tv1.setText(list.get(i).getAae003());
        childViewHolder.tv2.setText(list.get(i).getAae019());
        childViewHolder.tv3.setText(list.get(i).getAaa036());
        childViewHolder.tv4.setText(list.get(i).getAae140());
        childViewHolder.tv5.setText(list.get(i).getAaa027());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    static class GroupViewHolder {
        TextView tvTitle;
        ImageView img;
    }

    static class ChildViewHolder {
        TextView tv1,tv2,tv3,tv4,tv5;
    }
}
