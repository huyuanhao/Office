package com.powerrich.office.oa.activity.things;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.Interaction.ConsultationDetailActivity;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.adapter.HydropowerNoticeAdapter;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.chad.library.adapter.base.BaseViewHolder;
import com.powerrich.office.oa.common.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.yt.simpleframe.view.recyclerview.decoration.GridSpaceItemDecoration;
import com.yt.simpleframe.view.recyclerview.decoration.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LifePaymentActivity extends BaseActivity {
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    BaseQuickAdapter adapter;
    HydropowerNoticeAdapter noticeAdapter;
    private int[] topImgs = {R.drawable.icon_service_org_2, R.drawable.icon_service_org_1, R.drawable.icon_service_org_3,
            R.drawable.icon_service_org_4, R.drawable.icon_service_org_5, R.drawable.icon_life_org_6};
    private String[] topTexts = {"水费", "电费", "燃气费", "话费", "电视费", "宽带费"};

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_payment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    @OnClick({R.id.bar_back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
        }
    }

    private void initView() {
        barTitle.setText("水电燃气");
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshLayout) {
                refreshLayout.finishLoadmore();
            }
        });
        setTopView();
        setMessageList();
    }

    private void setTopView() {
        recyclerview.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerview.addItemDecoration(new SpacesItemDecoration(0,(int)getResources().getDimension(R.dimen.px30)));
        recyclerview.setAdapter(adapter = new BaseQuickAdapter<HashMap<String, Object>, BaseViewHolder>
                (R.layout.service_gv_item,getTopList(topTexts, topImgs)) {

            @Override
            protected void convert(BaseViewHolder helper, HashMap<String, Object> item) {
                helper.setImageResource(R.id.image, (int) item.get("image"));
                helper.setText(R.id.text, ((String) item.get("text")));
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0://水费
                        startActivity(new Intent(LifePaymentActivity.this,ChoiceMechanismActivity.class)
                                .putExtra("type","0"));
                        break;
                    case 1://电费
                        startActivity(new Intent(LifePaymentActivity.this,ChoiceMechanismActivity.class)
                        .putExtra("type","1"));
                        break;
                    case 2://燃气费
                        startActivity(new Intent(LifePaymentActivity.this,ChoiceMechanismActivity.class)
                                .putExtra("type","2"));
                        break;
                    case 3://话费
                        break;
                    case 4://电视费
                        startActivity(new Intent(LifePaymentActivity.this,PowerRateActivity.class)
                                .putExtra("type","4"));
                        break;
                    case 5://宽带费
                        startActivity(new Intent(LifePaymentActivity.this,ChoiceMechanismActivity.class)
                                .putExtra("type","5"));
                        break;
                }
            }
        });
    }

    private List<HashMap<String, Object>> getTopList(String[] title, int[] img) {
        List<HashMap<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("text", title[i]);
            map.put("image", img[i]);
            list.add(map);
        }
        return list;
    }

    private void setMessageList() {
        List<String> list =new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("");
        }
        rvMessage.setLayoutManager(new LinearLayoutManager(this));
        noticeAdapter = new HydropowerNoticeAdapter(R.layout.item_hydroppower_notice,list);
        rvMessage.setAdapter(noticeAdapter);
        noticeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(LifePaymentActivity.this,WpNoticeDetailActivity.class));
            }
        });
    }
}
