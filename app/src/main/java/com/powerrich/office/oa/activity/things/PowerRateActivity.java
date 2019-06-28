package com.powerrich.office.oa.activity.things;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
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
 * 缴费类型
 * huyuanhao
 */

public class PowerRateActivity extends BaseActivity {
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.tv_query)
    TextView tvQuery;
    @BindView(R.id.iv_type)
    ImageView ivType;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_tip)
            TextView tvTip;
    String title = "";
    String type = "0";
    String name = "";

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_power_rate;
    }

    @OnClick({R.id.bar_back, R.id.tv_query})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
            case R.id.tv_query:
                selectActivity();
                break;
        }
    }

    private void selectActivity() {
        switch (type){
            case "0":
            case "1":
            case "2":
                startActivity(new Intent(PowerRateActivity.this, PowerRateDetailActivity.class)
                        .putExtra("type", type));
                break;
            case "3":
                T.showShort(PowerRateActivity.this,"加油卡查询结果");
                break;
            case "4":
                T.showShort(PowerRateActivity.this,"电视费查询结果");
                break;
            case "5":
                T.showShort(PowerRateActivity.this,"宽带费查询结果");
                break;
        }
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
        name = getIntent().getStringExtra("name");
        switch (type) {
            case "0":
                title = "水费";
                ivType.setImageResource(R.drawable.icon_service_org_2);
                tvTitle.setText("用户编号");
                etNumber.setHint("请输入用户编号");
                etNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
                tvTip.setText("用户编号是9位数字，请查看账单/短信");
                break;
            case "1":
                title = "电费";
                ivType.setImageResource(R.drawable.icon_service_org_1);
                tvTitle.setText("用户编号");
                etNumber.setHint("请输入用户编号");
                etNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                tvTip.setText("用户编号是10位数字，请查看账单/短信");
                break;
            case "2":
                title = "燃气费";
                ivType.setImageResource(R.drawable.icon_service_org_3);
                tvTitle.setText("用户编号");
                etNumber.setHint("请输入用户编号");
                etNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                tvTip.setText("用户编号是10位数字，请查看账单/短信");
                break;
            case "3":
                title = "加油";
                ivType.setImageResource(R.drawable.home_icon_refuel);
                tvTitle.setText("油卡卡号");
                etNumber.setHint("请输入油卡卡号");
                etNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(19)});
                tvTip.setText("19位加油卡卡号");
                break;
            case "4":
                title = "电视费";
                ivType.setImageResource(R.drawable.icon_service_org_5);
                tvTitle.setText("智能卡号");
                etNumber.setHint("请输入智能卡号");
                tvTip.setText("");

                if(TextUtils.isEmpty(name)){
                    name = "湖北广电网络收视费";
                }
                break;
            case "5":
                title = "宽带费";
                ivType.setImageResource(R.drawable.icon_life_org_6);
                tvTitle.setText("宽带号码");
                etNumber.setHint("请输入宽带号码");
                tvTip.setText("");
                break;
        }
        barTitle.setText(title);
        tvType.setText(name);
    }
}
