package com.powerrich.office.oa.activity.Interaction;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.ConsulrationAdapter;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.common.ResultItem;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yt.simpleframe.view.recyclerview.decoration.LineItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/7.
 */

public class ConsultationDetailActivity extends BaseActivity implements OnRefreshLoadmoreListener {
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    ConsulrationAdapter adapter;
    private List<ResultItem> list = new ArrayList<>();
    String type = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_consultation_detail;
    }

    @OnClick({R.id.bar_back})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.bar_back:
                finish();
                break;
        }
    }
    private void initView() {
        type = getIntent().getStringExtra("type");
        switch (type){
            case "1":
                tvName.setText("最新调查");
                break;
            case "2":
                tvName.setText("往期调查");
                break;
            case "3":
                tvName.setText("调查结果");
                break;
        }
        barTitle.setText("咨询详情");
        setAdapter();
    }

    private void setAdapter() {
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnRefreshLoadmoreListener(this);
        for (int i = 0; i < 10; i++) {
            ResultItem resultItem = new ResultItem();
            resultItem.put("title","关于人们对广场舞现状发展的看法" + i);
            resultItem.put("time","2018/01/01");
            list.add(resultItem);
        }
        adapter = new ConsulrationAdapter(R.layout.item_consultation,list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new LineItemDecoration(2));
        rv.setAdapter(adapter);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshLayout) {
        refreshLayout.finishLoadmore();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {

    }
}
