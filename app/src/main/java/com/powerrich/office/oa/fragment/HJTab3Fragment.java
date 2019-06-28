package com.powerrich.office.oa.fragment;


import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 我想知道的碎片页面
 * @author wwj_748
 *
 */
public class HJTab3Fragment extends BaseFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Bundle bundle = getArguments();//从activity传过来的Bundle
		TextView tv_text = (TextView) view.findViewById(R.id.tv_text);
		tv_text.setText(bundle.getString("str"));
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.hj_tab_three_fragment;
	}

}
