package com.powerrich.office.oa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.tools.AutoUtils;
import com.yt.simpleframe.http.bean.SocialInfo;

import java.util.List;

public class ExpandableSocialAdapter extends BaseExpandableListAdapter {
    private List<SocialInfo> list;
    private Context mContext;

    public ExpandableSocialAdapter(Context mContext, List<SocialInfo> list) {
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
        groupViewHolder.tvTitle.setText(list.get(i).getAae003() + "—当月应缴" + list.get(i).getAae020qt());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_pay_childview, viewGroup, false);
            AutoUtils.auto(view);
            childViewHolder = new ChildViewHolder();
            childViewHolder.sign = (TextView) view.findViewById(R.id.sign);
            childViewHolder.type = (TextView) view.findViewById(R.id.type);
            childViewHolder.money_all = (TextView) view.findViewById(R.id.money_all);
            childViewHolder.base = (TextView) view.findViewById(R.id.base);
            childViewHolder.money = (TextView) view.findViewById(R.id.money);
            childViewHolder.company = (TextView) view.findViewById(R.id.company);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
//        double dw = Double.parseDouble(list.get(i).getAae080dw());
//        double gr = Double.parseDouble(list.get(i).getAae080gr());
//        double ss = Double.parseDouble(list.get(i).getAae080ss());

        childViewHolder.sign.setText(list.get(i).getBae152());
//        if (ss > 0) {
//            childViewHolder.sign.setText("已缴费");
//        } else {
//            childViewHolder.sign.setText("未交费");
//        }

        childViewHolder.type.setText(list.get(i).getAae140());
//        switch (list.get(i).getAae140()) {
//            case "11":
//                childViewHolder.type.setText("基本养老保险");
//                break;
//            case "31":
//                childViewHolder.type.setText("医疗保险");
//                break;
//            case "41":
//                childViewHolder.type.setText("失业保险");
//                break;
//            case "51":
//                childViewHolder.type.setText("生育保险");
//                break;
//        }
        childViewHolder.money_all.setText(list.get(i).getAae020qt() + "元");
        childViewHolder.base.setText(list.get(i).getAae180()+"元");
        childViewHolder.money.setText(list.get(i).getAae020gr()+"元");
        childViewHolder.company.setText(list.get(i).getAab004());
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
        TextView sign;
        TextView type;
        TextView money_all;
        TextView base;
        TextView money;
        TextView company;
    }
}
