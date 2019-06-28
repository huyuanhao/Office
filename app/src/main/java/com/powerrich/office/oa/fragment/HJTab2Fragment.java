package com.powerrich.office.oa.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.MaterialsAdapter;
import com.powerrich.office.oa.base.BaseFragment;
import com.powerrich.office.oa.bean.MaterialsInfo;
import com.powerrich.office.oa.tools.AutoUtils;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.view.NoScrollListView;

import java.util.List;

/**
 * 所需材料 页面
 */
public class HJTab2Fragment extends BaseFragment {

    private NoScrollListView lv_material_form;
    private List<MaterialsInfo> materialsInfoList;
    private MaterialsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        materialsInfoList = (List<MaterialsInfo>) bundle.getSerializable("material");
        lv_material_form = (NoScrollListView) view.findViewById(R.id.lv_material_form);
        loadData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.hj_tab_two_fragment;
    }

    private void loadData() {
        if (!BeanUtils.isEmpty(materialsInfoList)) {
            View head = getActivity().getLayoutInflater().inflate(R.layout.booking_detail_list_header, null);
            AutoUtils.auto(head);
            lv_material_form.addHeaderView(head);
        }
        if (null == adapter) {
            adapter = new MaterialsAdapter(getActivity(), materialsInfoList, R.layout.material_form_item);
            lv_material_form.setAdapter(adapter);
        } else {
            adapter.setData(materialsInfoList);
        }
    }

}
