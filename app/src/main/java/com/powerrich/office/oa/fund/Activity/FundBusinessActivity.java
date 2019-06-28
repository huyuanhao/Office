package com.powerrich.office.oa.fund.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.fund.Adapter.FundBusinessAdapter;
import com.yt.simpleframe.utils.DimensUtils;
import com.yt.simpleframe.utils.T;
import com.yt.simpleframe.view.recyclerview.decoration.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author PC
 * @date 2018/12/24 13:56
 */
public class FundBusinessActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;

    FundBusinessAdapter adapter;
    private List<String> list;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_fund_business;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        barTitle.setText("我的业务");
        tabLayout.addTab(tabLayout.newTab().setText("待办事项"));
        tabLayout.addTab(tabLayout.newTab().setText("我发起的"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    T.showShort(FundBusinessActivity.this, "待办事项");
                } else {
                    T.showShort(FundBusinessActivity.this, "我发起的");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FundBusinessAdapter(R.layout.item_fund_business, list);
        recyclerView.addItemDecoration(new SpaceItemDecoration(DimensUtils.dip2px(this,15)));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId() == R.id.more){
                    switch (position){
                        case 0:
                            T.showShort(FundBusinessActivity.this,"0");
                            break;
                    }
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @OnClick({R.id.bar_back})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.bar_back:
                finish();
                break;
        }
    }
}
