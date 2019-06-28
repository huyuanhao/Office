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
import com.powerrich.office.oa.bean.FileInfo;
import com.powerrich.office.oa.tools.AutoUtils;
import com.powerrich.office.oa.tools.BeanUtils;

import java.util.List;

/**
 * 文 件 名：MyExpandableListViewAdapter
 * 描   述：个人、企业办事申报材料适配器
 * 作   者：Wangzheng
 * 时   间：2018-6-13 11:05:36
 * 版   权：v1.0
 */
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<FileInfo> groupFile;
    private List<List<FileInfo>> childFile;

    public MyExpandableListViewAdapter(Context context, List<FileInfo> groupFile, List<List<FileInfo>> childFile) {
        this.context = context;
        this.groupFile = groupFile;
        this.childFile = childFile;
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childFile.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        ItemHolder itemHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.material_group_item, null);
            AutoUtils.auto(convertView);
            itemHolder = new ItemHolder();
            itemHolder.tv_file = (TextView) convertView.findViewById(R.id.tv_file);
            itemHolder.tv_file_name = (TextView) convertView.findViewById(R.id.tv_file_name);
            itemHolder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        if (BeanUtils.isEmptyStr(childFile.get(groupPosition).get(childPosition).getPreName())) {
            if (BeanUtils.isEmptyStr(groupFile.get(groupPosition).getAsyncJhpt())) {
                itemHolder.tv_file.setText("已上传文件：");
                itemHolder.tv_file.setTextColor(Color.parseColor("#999999"));
                itemHolder.tv_file_name.setTextColor(Color.parseColor("#999999"));
                itemHolder.iv_delete.setVisibility(View.VISIBLE);
            } else {
                if (!BeanUtils.isEmptyStr(childFile.get(groupPosition).get(childPosition).getFileId())) {
                    itemHolder.tv_file.setText("已上传文件：");
                    itemHolder.tv_file.setTextColor(Color.parseColor("#999999"));
                    itemHolder.tv_file_name.setTextColor(Color.parseColor("#999999"));
                    itemHolder.iv_delete.setVisibility(View.VISIBLE);
                } else {
                    itemHolder.tv_file.setText("已同步文件：");
                    itemHolder.tv_file.setTextColor(Color.parseColor("#0293ea"));
                    itemHolder.tv_file_name.setTextColor(Color.parseColor("#0293ea"));
                    itemHolder.iv_delete.setVisibility(View.GONE);
                }

            }
            itemHolder.tv_file_name.setText(childFile.get(groupPosition).get(childPosition).getFileName());
        } else {
            itemHolder.tv_file.setText("已同步证照：");
            itemHolder.tv_file_name.setText(childFile.get(groupPosition).get(childPosition).getFileName());
            itemHolder.iv_delete.setVisibility(View.GONE);
            itemHolder.tv_file.setTextColor(Color.parseColor("#0293ea"));
            itemHolder.tv_file_name.setTextColor(Color.parseColor("#0293ea"));
        }
        itemHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != deleteListener) {
                    deleteListener.onDelete(groupPosition, childPosition);
                }
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return BeanUtils.isEmpty(childFile) ? 0 : childFile.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupFile.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return BeanUtils.isEmpty(groupFile) ? 0 : groupFile.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.material_item, null);
            AutoUtils.auto(convertView);
            groupHolder = new GroupHolder();
            groupHolder.tv_material_name = (TextView) convertView.findViewById(R.id.tv_material_name);
            groupHolder.tv_upload = (TextView) convertView.findViewById(R.id.tv_upload);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.tv_material_name.setText((groupPosition + 1) + "." + groupFile.get(groupPosition).getMaterialName());
        groupHolder.tv_upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onClick(groupPosition);
                }
            }
        });
        return convertView;
    }

    public interface IDeleteListener {
        void onDelete(int groupPosition, int childPosition);
    }

    private IDeleteListener deleteListener;

    public void setOnDeleteListener(IDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public interface IDownloadListener {
        void onClick(int position);
    }

    private IDownloadListener listener;

    public void setOnClickListener(IDownloadListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder {
        private TextView tv_material_name;
        private TextView tv_upload;
    }

    class ItemHolder {
        private TextView tv_file;
        private TextView tv_file_name;
        private ImageView iv_delete;
    }

}
