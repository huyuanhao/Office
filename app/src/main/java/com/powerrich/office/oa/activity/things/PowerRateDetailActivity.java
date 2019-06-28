package com.powerrich.office.oa.activity.things;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.yt.simpleframe.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 水电燃气费详情
 * huyuanhao
 */

public class PowerRateDetailActivity extends BaseActivity {
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    String type = "0";
    String title = "";

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_power_rate_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        type = getIntent().getStringExtra("type");
        switch (type){
            case "0":
                title = "水费";
                break;
            case "1":
                title = "电费";
                break;
            case "2":
                title = "燃气费";
                break;
        }
    }

    @OnClick({R.id.bar_back,R.id.tv_pay})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
            case R.id.tv_pay:
                T.showShort(PowerRateDetailActivity.this,"跳转支付页面");
                break;
        }
    }
}
