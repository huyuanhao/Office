package com.powerrich.office.oa.activity.home.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.ImgTxInfo;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 城乡居民社保查询
 */
public class SocialSecurityRuralActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    BaseQuickAdapter adapter;
    @BindView(R.id.bar_back)
    ImageView mBarBack;

    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_socialsecurity;
    }

    public void initView() {
        List<ImgTxInfo> list = new ArrayList<>();
        list.add(new ImgTxInfo("个人基本信息", R.drawable.cxjm_1));
        list.add(new ImgTxInfo("养老账户查询", R.drawable.cxjm_2));
        list.add(new ImgTxInfo("养老发放查询", R.drawable.cxjm_3));
        list.add(new ImgTxInfo("社保缴费信息", R.drawable.cxjm_4));
        barTitle.setText("城乡居民社保查询");
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BaseQuickAdapter<ImgTxInfo, BaseViewHolder>(R.layout.item_social, list) {
            @Override
            protected void convert(BaseViewHolder helper, ImgTxInfo item) {
                helper.setText(R.id.title, item.getTitle())
                        .setImageResource(R.id.img, item.getImg());
            }
        };
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(SocialSecurityRuralActivity.this, PersonalInformatioActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(SocialSecurityRuralActivity.this, PensionAccountInquiryActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(SocialSecurityRuralActivity.this, PensionPutActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(SocialSecurityRuralActivity.this, SocialSecurityInquryActivity.class));
                        break;

                }
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
