package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;

public class AccumulationFundActivity extends BaseActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_accumulation_fund;
    }

    private void initView() {
        initTitleBar("公积金查询", this, null);
        findViewById(R.id.acc_fund1).setOnClickListener(this);
        findViewById(R.id.acc_fund2).setOnClickListener(this);
         findViewById(R.id.acc_fund3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.acc_fund1://公积金基本信息
                startActivity(new Intent(this, AccFundBaseActivity.class));
                break;
            case R.id.acc_fund2://公积金明细查询
                startActivity(new Intent(this, AccFundDetailActivity.class));
                break;
            case R.id.acc_fund3://贷款基本信息
                startActivity(new Intent(this, AccFundLoansActivity.class));
                break;
        }
    }
}
