package com.powerrich.office.oa.activity.Interaction;

import android.content.Intent;
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
import com.yt.simpleframe.view.recyclerview.decoration.LineItemDecoration;
import com.yt.simpleframe.view.recyclerview.decoration.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/7.
 */

public class ConsultationActivity extends BaseActivity {

    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.rv_new)
    RecyclerView rvNew;
    @BindView(R.id.more_new)
    TextView moreNew;
    @BindView(R.id.rv_old)
    RecyclerView rvOld;
    @BindView(R.id.more_old)
    TextView moreOld;
    @BindView(R.id.rv_result)
    RecyclerView rvResult;
    @BindView(R.id.more_result)
    TextView moreResult;
    ConsulrationAdapter adapter;
    private List<ResultItem> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_consultation;
    }

    private void initView(){
        barTitle.setText("咨询详情");
        setAdapter();
    }

    private void setAdapter() {
        for (int i = 0; i < 5; i++) {
            ResultItem resultItem = new ResultItem();
            resultItem.put("title","关于人们对广场舞现状发展的看法" + i);
            resultItem.put("time","2018/01/01");
            list.add(resultItem);
        }
        adapter = new ConsulrationAdapter(R.layout.item_consultation,list);
        rvNew.setLayoutManager(new LinearLayoutManager(this));
        rvOld.setLayoutManager(new LinearLayoutManager(this));
        rvResult.setLayoutManager(new LinearLayoutManager(this));
        rvNew.addItemDecoration(new LineItemDecoration(2));
        rvOld.addItemDecoration(new LineItemDecoration(2));
        rvResult.addItemDecoration(new LineItemDecoration(2));

        rvNew.setAdapter(adapter);
        rvOld.setAdapter(adapter);
        rvResult.setAdapter(adapter);
    }

    @OnClick({R.id.bar_back,R.id.more_new,R.id.more_old,R.id.more_result})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.bar_back:
                finish();
                break;
            case R.id.more_new:
                startActivity(new Intent(this,ConsultationDetailActivity.class).putExtra("type","1"));
                break;
            case R.id.more_old:
                startActivity(new Intent(this,ConsultationDetailActivity.class).putExtra("type","2"));
                break;
            case R.id.more_result:
                startActivity(new Intent(this,ConsultationDetailActivity.class).putExtra("type","3"));
                break;
        }
    }
}
