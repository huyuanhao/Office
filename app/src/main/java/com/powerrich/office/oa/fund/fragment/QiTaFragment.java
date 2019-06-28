package com.powerrich.office.oa.fund.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.fragment.mine.base.LazyLoadFragment;
import com.powerrich.office.oa.fund.Activity.FundFunctionActivity;
import com.powerrich.office.oa.fund.Adapter.FuWuWangDianChaXunAdapter;
import com.powerrich.office.oa.fund.bean.FundFunctionBean;
import com.powerrich.office.oa.fund.bean.FuwuWangdianBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yt.simpleframe.view.recyclerview.decoration.SpaceItemDecoration;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/16
 * 版权：
 */

public class QiTaFragment extends LazyLoadFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
//    @BindView(R.id.refresh_layout)
//    SmartRefreshLayout mRefreshLayout;

    private List<FundFunctionBean> list;

    //懒加载处理
    @Override
    public void requestData() {
    }

    @Override
    protected View createContentView() {
        View view = inflateContentView(R.layout.fragment_fund_base);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        //模拟数据源
//        list = Arrays.asList(new FuwuWangdianBean("鹰潭住房公积金管理中心", "鹰潭市月湖区胜利东路32号"),
//                new FuwuWangdianBean("鹰潭市住房公积金管理中心余江办事处", "鹰潭市余江区广场路10号"),
//                new FuwuWangdianBean("鹰潭市住房公积金管理中心贵溪办事处", "鹰潭市贵溪市治金路工商银行滨江支行二楼\n"));

        mRecyclerview.addItemDecoration(new SpaceItemDecoration((mContext).getResources().getDimensionPixelSize(com.yt.simpleframe.R.dimen.dimen_div_01), mContext));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        FuWuWangDianChaXunAdapter adapter = new FuWuWangDianChaXunAdapter(R.layout.item_fragment_quanbu, list);
        mRecyclerview.setAdapter(adapter);

        list = ((FundFunctionActivity)getActivity()).getList4();
        adapter.notifyDataSetChanged();
    }

}
