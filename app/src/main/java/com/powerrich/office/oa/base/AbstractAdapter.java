package com.powerrich.office.oa.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
/**
 * 抽象适配器(免去一些通用的代码)
 * @author dir_wang
 *
 * @param <T>
 */
public abstract class AbstractAdapter<T> extends BaseAdapter {
	
	public Context context;
	public List<T> listData;
	public LayoutInflater layoutInflater;
	
	public AbstractAdapter(Context context, List<T> listData) {
		this.context = context;
		this.listData = new ArrayList<T>();
	}
	
	public abstract void setData(List<T> data);
	
	@Override
	public int getCount() {
		return listData == null ? 0 : listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData == null ? 0 : listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public List<T> getListData() {
		return listData;
	}
	
	public void setListData(List<T> listData) {
		this.listData = listData;
	}
	
	public View inflate(int layoutId) {
		return View.inflate(context, layoutId, null);
	}

}
