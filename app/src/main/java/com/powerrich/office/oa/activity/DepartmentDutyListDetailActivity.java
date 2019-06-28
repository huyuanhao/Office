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
 * 文 件 名：DepartmentDutyListDetailActivity
 * 描   述：阳光政务部门责任清单详情界面
 * 作   者：Wangzheng
 * 时   间：2017/12/6
 * 版   权：v1.0
 */
public class DepartmentDutyListDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_department, tv_project_approval, tv_charge_project, tv_funds_management, tv_policy_gist;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_charges_deail;
    }

    private void initView() {
        initTitleBar("部门责任清单详情", this, null);
        tv_department = (TextView) findViewById(R.id.tv_department);
        tv_project_approval = (TextView) findViewById(R.id.tv_project_approval);
        tv_charge_project = (TextView) findViewById(R.id.tv_charge_project);
        tv_funds_management = (TextView) findViewById(R.id.tv_funds_management);
        tv_policy_gist = (TextView) findViewById(R.id.tv_policy_gist);
        loadData();
    }

    private void loadData() {
//        ApiRequest request = OAInterface.getDepartmentDutyList(id, "1");
//        invoke.invokeWidthDialog(request, callBack);
        getChargeDetail();
    }

    /**
     * 获取阳光政务行政事业性收费清单详情请求
     */
    private void getChargeDetail() {
        ApiRequest request = OAInterface.getChargeDetail(id);
        invoke.invokeWidthDialog(request, callBack);
    }

    /**
     * 获取阳光政务行政事业性收费清单数据解析
     *
     * @param result
     */
    private void parseData(ResultItem result) {
        tv_department.setText(result.getString("SITENAME"));
        tv_project_approval.setText(result.getString("LX"));
        tv_charge_project.setText(result.getString("SFXM"));
        tv_funds_management.setText(result.getString("ZJGLFS"));
        tv_policy_gist.setText(result.getString("ZCYJ"));
    }


    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
//                ResultItem result = (ResultItem) item.get("DATA");
//                parseData(result);
            } else {
                DialogUtils.showToast(DepartmentDutyListDetailActivity.this, message);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                DepartmentDutyListDetailActivity.this.finish();
                break;
            default:
                break;
        }
    }

}
