package com.powerrich.office.oa.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.tools.ScreenUtils;

public class DepartmentEVAdapter extends BaseExpandableListAdapter{
	      
		private Context context;
		private List<String> mParent;
		private Map<String, List<String>> map;
		public DepartmentEVAdapter(Context context,List<String> parent ,Map<String, List<String>> map ) {
		super();
		this.context=context;
		this.mParent=parent;
		this.map=map;
	}

		//得到子item需要关联的数据
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			String key = mParent.get(groupPosition);
			return (map.get(key).get(childPosition));
		}

		//得到子item的ID
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		//设置子item的组件
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			String key = mParent.get(groupPosition);
			String info = map.get(key).get(childPosition);
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.layout_children, null);
			}
			TextView tv = (TextView) convertView
					.findViewById(R.id.second_textview);
			tv.setPadding(ScreenUtils.dp2px(context, 20),ScreenUtils.dp2px(context, 10),ScreenUtils.dp2px(context, 10), ScreenUtils.dp2px(context, 10));
			tv.setText(info);
			return tv;
		}

		//获取当前父item下的子item的个数
		@Override
		public int getChildrenCount(int groupPosition) {
			String key = mParent.get(groupPosition);
			int size=map.get(key).size();
			return size;
		}
      //获取当前父item的数据
		@Override
		public Object getGroup(int groupPosition) {
			return mParent.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return mParent.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}
       //设置父item组件
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.layout_parent, null);
			}
			TextView tv = (TextView) convertView
					.findViewById(R.id.parent_textview);
			tv.setPadding(ScreenUtils.dp2px(context, 10),ScreenUtils.dp2px(context, 10),ScreenUtils.dp2px(context, 10), ScreenUtils.dp2px(context, 10));
			tv.setText(mParent.get(groupPosition));
			return tv;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}
