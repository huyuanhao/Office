package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.tools.AutoUtils;

public class RegisterAgreementActivity extends BaseActivity {
    private RelativeLayout titleLayout;
    private TextView mTvContent;
    private RelativeLayout mTitleLayout;
    private ImageView mSystemBack;
    private TextView mTvTopTitle,tv_agree;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register_agreement;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isAutoFlag = false;
        super.onCreate(savedInstanceState);
        titleLayout = findViewById(R.id.title_layout);
        AutoUtils.auto(titleLayout);
        initView();
    }


    private void initView() {
        mTvContent = findViewById(R.id.tv_content);
        mTvContent.setText(R.string.agreement_content);

        mSystemBack = findViewById(R.id.system_back);

        mTvTopTitle = (TextView) findViewById(R.id.tv_top_title);
        tv_agree = (TextView) findViewById(R.id.tv_agree);
        tv_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1000);
                finish();
            }
        });

        mTvTopTitle.setText("注册协议");

        mSystemBack.setVisibility(View.VISIBLE);
        mSystemBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
