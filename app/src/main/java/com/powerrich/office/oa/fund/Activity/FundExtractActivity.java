package com.powerrich.office.oa.fund.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author PC
 * @date 2018/12/24 16:38
 */
public class FundExtractActivity extends BaseActivity {
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.linear1)
    LinearLayout linear1;
    @BindView(R.id.linear2)
    LinearLayout linear2;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_fund_extract;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        barTitle.setText("我要提取");
    }

    @OnClick({R.id.bar_back,R.id.linear1,R.id.linear2})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.bar_back:
                finish();
                break;
            case R.id.linear1://离职提取
                break;
            case R.id.linear2://离休、退休提取
                break;
        }
    }
}
