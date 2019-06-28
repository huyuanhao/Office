package com.powerrich.office.oa.activity.home.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.powerrich.office.oa.R;
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

public class SocialActivity extends BaseActivity {
    @BindView(R.id.title_layout)
    RelativeLayout mTitleLayout;
    @BindView(R.id.rlt_process)
    RelativeLayout mRltProcess;
    @BindView(R.id.rlt_modify)
    RelativeLayout mRltModify;
    @BindView(R.id.rlt_loss)
    RelativeLayout mRltLoss;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_social;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTitleBar("社保卡", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, null);

    }

    @OnClick({R.id.rlt_process, R.id.rlt_modify, R.id.rlt_loss})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rlt_process:
                intent = new Intent(this, SocialProcessActivity.class);
                break;
            case R.id.rlt_modify:
                intent = new Intent(this, SocialPwdModifyActivity.class);
                break;
            case R.id.rlt_loss:
                intent = new Intent(this, SocialLossActivity.class);
                break;
        }
        startActivity(intent);
    }
}
