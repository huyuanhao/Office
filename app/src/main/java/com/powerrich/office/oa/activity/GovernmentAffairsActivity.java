package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;

/**
 * 文 件 名：GovernmentAffairsActivity
 * 描   述：阳光政务界面
 * 作   者：Wangzheng
 * 时   间：2017/12/5
 * 版   权：v1.0
 */
public class GovernmentAffairsActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_admin_power_list, tv_department_duty_list, tv_negative_publicity, tv_policy_statute,
            tv_market_admittance_list, tv_commonality_service_list, tv_admin_charge_list,
            tv_admin_approve_intermediary_services_list, tv_want_query, tv_query_statistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_government_affairs;
    }

    private void initView() {
        initTitleBar(R.string.home_sunshine_affair, this, null);
        tv_admin_power_list = (TextView) findViewById(R.id.tv_admin_power_list);
        tv_department_duty_list = (TextView) findViewById(R.id.tv_department_duty_list);
        tv_negative_publicity = (TextView) findViewById(R.id.tv_negative_publicity);
        tv_policy_statute = (TextView) findViewById(R.id.tv_policy_statute);
        tv_market_admittance_list = (TextView) findViewById(R.id.tv_market_admittance_list);
        tv_commonality_service_list = (TextView) findViewById(R.id.tv_commonality_service_list);
        tv_admin_charge_list = (TextView) findViewById(R.id.tv_admin_charge_list);
        tv_admin_approve_intermediary_services_list = (TextView) findViewById(R.id.tv_admin_approve_intermediary_services_list);
        tv_want_query = (TextView) findViewById(R.id.tv_want_query);
        tv_query_statistics = (TextView) findViewById(R.id.tv_query_statistics);
    }

    private void initData() {
        tv_admin_power_list.setOnClickListener(this);
        tv_department_duty_list.setOnClickListener(this);
        tv_negative_publicity.setOnClickListener(this);
        tv_policy_statute.setOnClickListener(this);
        tv_market_admittance_list.setOnClickListener(this);
        tv_commonality_service_list.setOnClickListener(this);
        tv_admin_charge_list.setOnClickListener(this);
        tv_admin_approve_intermediary_services_list.setOnClickListener(this);
        tv_want_query.setOnClickListener(this);
        tv_query_statistics.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                GovernmentAffairsActivity.this.finish();
                break;
            case R.id.tv_admin_power_list:
                startActivity(new Intent(GovernmentAffairsActivity.this, AdminPowerListActivity.class));
                break;
            case R.id.tv_department_duty_list:
                startActivity(new Intent(GovernmentAffairsActivity.this, DepartmentDutyListActivity.class));
                break;
            case R.id.tv_market_admittance_list:
                startActivity(new Intent(GovernmentAffairsActivity.this, MarketAdmittanceListActivity.class));
                break;
            case R.id.tv_commonality_service_list:
                break;
            case R.id.tv_admin_charge_list:
                startActivity(new Intent(GovernmentAffairsActivity.this, ChargesListActivity.class));
                break;
            case R.id.tv_admin_approve_intermediary_services_list:
                startActivity(new Intent(GovernmentAffairsActivity.this, AdminApproveServicesListActivity.class));
                break;
            case R.id.tv_negative_publicity:
                startActivity(new Intent(GovernmentAffairsActivity.this, PublicityActivity.class));
                break;
            case R.id.tv_policy_statute:
                startActivity(new Intent(GovernmentAffairsActivity.this, PolicyStatuteActivity.class));
                break;
            case R.id.tv_want_query:
                startActivity(new Intent(GovernmentAffairsActivity.this, IWantQueryActivity.class));
                break;
            case R.id.tv_query_statistics:
                startActivity(new Intent(GovernmentAffairsActivity.this, QueryStatisticsActivity.class));
                break;
            default:
                break;
        }
    }
}
