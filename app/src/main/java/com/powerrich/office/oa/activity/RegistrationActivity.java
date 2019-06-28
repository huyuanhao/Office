package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.view.View;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;

/**
 * 文 件 名：RegistrationActivity
 * 描   述：挂号
 * 作   者：Wangzheng
 * 时   间：2018-6-8 14:21:10
 * 版   权：v1.0
 */
public class RegistrationActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_registration;
    }

    private void initView() {
        initTitleBar(R.string.registration, this, null);
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                RegistrationActivity.this.finish();
                break;
        }
    }
}
