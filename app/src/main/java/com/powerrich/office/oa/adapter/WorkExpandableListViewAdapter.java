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
import com.powerrich.office.oa.bean.Items;
import com.powerrich.office.oa.tools.AutoUtils;
import com.powerrich.office.oa.tools.BeanUtils;

import java.util.List;


/**
 * 文 件 名：WorkExpandableListViewAdapter
 * 描   述：个人、企业办事事项适配器
 * 作   者：Wangzheng
 * 时   间：2018-6-13 11:05:36
 * 版   权：v1.0
 */
public class WorkExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Items> itemsList;

    public WorkExpandableListViewAdapter(Context context, List<Items> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return itemsList.get(groupPosition).getSonItems().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.work_list_item, null);
            AutoUtils.auto(convertView);
            itemHolder = new ItemHolder();
            itemHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            itemHolder.tv_to_guide = (TextView) convertView.findViewById(R.id.tv_to_guide);
            itemHolder.tv_todo = (TextView) convertView.findViewById(R.id.tv_todo);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        final Items item = itemsList.get(groupPosition);
        itemHolder.tv_to_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childListener.onClickChildGuide(item, childPosition);
            }
        });
        itemHolder.tv_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childListener.onClickChildWork(item, childPosition);
            }
        });
        itemHolder.tv_name.setText(item.getSonItems().get(childPosition).getITEMNAME());
        isEnabled(itemHolder.tv_todo, item.getSonItems().get(childPosition).getISAPP());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return BeanUtils.isEmpty(itemsList.get(groupPosition).getSonItems()) ? 0 : itemsList.get(groupPosition).getSonItems().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return itemsList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return BeanUtils.isEmpty(itemsList) ? 0 : itemsList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.online_booking_list_item, null);
            AutoUtils.auto(convertView);
            groupHolder = new GroupHolder();
            groupHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            groupHolder.iv_item = (ImageView) convertView.findViewById(R.id.iv_item);
            groupHolder.tv_department = (TextView) convertView.findViewById(R.id.tv_department);
            groupHolder.tv_to_guide = (TextView) convertView.findViewById(R.id.tv_to_guide);
            groupHolder.tv_todo = (TextView) convertView.findViewById(R.id.tv_todo);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        final Items item = itemsList.get(groupPosition);
        groupHolder.tv_name.setText(item.getITEMNAME());
        groupHolder.tv_department.setText(item.getNORMACCEPTDEPART());
        groupHolder.tv_todo.setText("立即办事");
        if (!BeanUtils.isEmpty(item.getSonItems())) {
            groupHolder.iv_item.setVisibility(View.VISIBLE);
            groupHolder.tv_to_guide.setVisibility(View.GONE);
            groupHolder.tv_todo.setVisibility(View.GONE);
            if (isExpanded) {
                groupHolder.iv_item.setBackgroundResource(R.drawable.icon_open);
            } else {
                groupHolder.iv_item.setBackgroundResource(R.drawable.icon_close);
            }
        } else {
            groupHolder.iv_item.setVisibility(View.GONE);
            groupHolder.tv_to_guide.setVisibility(View.VISIBLE);
            groupHolder.tv_todo.setVisibility(View.VISIBLE);
        }
        isEnabled(groupHolder.tv_todo, item.getISAPP());
        groupHolder.tv_to_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickGuide(item);
            }
        });
        groupHolder.tv_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickWork(item);
            }
        });
        return convertView;
    }

    public interface IGroupListener {
        void onClickGuide(Items item);

        void onClickWork(Items item);
    }

    public IGroupListener listener;

    public void setGuideListener(IGroupListener listener) {
        this.listener = listener;
    }

    public void setWorkLister(IGroupListener listener) {
        this.listener = listener;
    }

    public interface IChildListener {
        void onClickChildGuide(Items item, int childPosition);

        void onClickChildWork(Items item, int childPosition);
    }

    private IChildListener childListener;

    public void setChildGuideListener(IChildListener childListener) {
        this.childListener = childListener;
    }

    public void setChildWorkListener(IChildListener childListener) {
        this.childListener = childListener;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    private void isEnabled(TextView tv, String isApp) {
        if (!isApp.contains("2")) {
            // 判断是否可以立即办事
            tv.setBackgroundResource(R.drawable.gray_border_white_bg_corners2);
            tv.setTextColor(Color.parseColor("#999999"));
            tv.setEnabled(false);
        } else {
            tv.setBackgroundResource(R.drawable.blue2_corners2_icon);
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setEnabled(true);
        }
    }

    class GroupHolder {
        private TextView tv_name;
        private ImageView iv_item;
        private TextView tv_department;
        private TextView tv_to_guide;
        private TextView tv_todo;
    }

    class ItemHolder {
        private TextView tv_name;
        private TextView tv_to_guide;
        private TextView tv_todo;
    }

}
