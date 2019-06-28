package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;

/**
 * 文 件 名：AdminApproveServicesDetailActivity
 * 描   述：阳光政务行政审批中介服务详情界面
 * 作   者：Wangzheng
 * 时   间：2017/12/5
 * 版   权：v1.0
 */
public class AdminApproveServicesDetailActivity extends BaseActivity implements View.OnClickListener {

    private String servicesId;
    private TextView tv_order_number, tv_services_project_name, tv_admin_approve_project_name, tv_approve_department,
            tv_services_project_setting_gist, tv_services_implement_organization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        servicesId = getIntent().getStringExtra("services_id");
        initView();
        initData();
        getAdminApproveServicesDetail();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_deail;
    }

    private void initView() {
        initTitleBar("行政审批中介服务清单详情", this, null);
        tv_order_number = (TextView) findViewById(R.id.tv_order_number);
        tv_services_project_name = (TextView) findViewById(R.id.tv_services_project_name);
        tv_admin_approve_project_name = (TextView) findViewById(R.id.tv_admin_approve_project_name);
        tv_approve_department = (TextView) findViewById(R.id.tv_approve_department);
        tv_services_project_setting_gist = (TextView) findViewById(R.id.tv_services_project_setting_gist);
        tv_services_implement_organization = (TextView) findViewById(R.id.tv_services_implement_organization);
    }

    private void initData() {

    }

    private void getAdminApproveServicesDetail() {
        ApiRequest request = OAInterface.getAdminApproveServicesDetail(servicesId);
        invoke.invokeWidthDialog(request, callBack);
    }

    private void parseData(ResultItem result) {
        tv_order_number.setText(result.getString("ORDERNO"));
        tv_services_project_name.setText(result.getString("ZJFWMC"));
        tv_admin_approve_project_name.setText(result.getString("XZSPXMMC"));
        tv_approve_department.setText(result.getString("SITENAME"));
        tv_services_project_setting_gist.setText(result.getString("SDYJ"));
        tv_services_implement_organization.setText(result.getString("SSJG"));
    }


    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                ResultItem result = (ResultItem) item.get("DATA");
                parseData(result);
            } else {
                DialogUtils.showToast(AdminApproveServicesDetailActivity.this, message);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                AdminApproveServicesDetailActivity.this.finish();
                break;
            default:
                break;
        }
    }

}
