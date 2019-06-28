package com.powerrich.office.oa.common;

/**
 * 在ViewPager中，当切换tab时，需要重新加载当前界面的数据，当实现该接口，并在接口方法中加载数据
 * */
public interface LoadDataListener {

	void loadData(int position);
}
