package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.view.View;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;

public class AccFundLoansActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_acc_fund_loans;
    }

    private void initView() {
        initTitleBar("公积金贷款信息", this, null);
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
