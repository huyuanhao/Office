package com.powerrich.office.oa.activity.home.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.home.SocialSecurityActivity;
import com.powerrich.office.oa.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/1
 * 版权：
 */

public class SocialInquiryActivity extends BaseActivity {


    @BindView(R.id.rlt_town)
    RelativeLayout mRltTown;
    @BindView(R.id.rlt_rural)
    RelativeLayout mRltRural;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_social_inquire;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTitleBar("社保查询", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, null);

    }

    @OnClick({R.id.rlt_town, R.id.rlt_rural})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rlt_town:
                intent = new Intent(this, SocialSecurityActivity.class);
                break;
            case R.id.rlt_rural:
                intent = new Intent(this, SocialSecurityRuralActivity.class);
                break;
        }
        startActivity(intent);
    }
}
