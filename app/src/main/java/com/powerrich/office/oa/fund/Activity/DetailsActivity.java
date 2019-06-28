package com.powerrich.office.oa.fund.Activity;

import android.os.Bundle;
import android.view.View;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;

/**
 * 详情
 */
public class DetailsActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar("详情", this, null);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_details;
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
