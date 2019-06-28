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
 * 文 件 名：ChargesDetailActivity
 * 描   述：阳光政务行政事业性收费清单详情界面
 * 作   者：Wangzheng
 * 时   间：2017/12/4
 * 版   权：v1.0
 */
public class ChargesDetailActivity extends BaseActivity implements View.OnClickListener {

    private String listId;
    private TextView tv_department, tv_project_approval, tv_charge_project, tv_funds_management, tv_policy_gist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listId = getIntent().getStringExtra("list_id");
        initView();
        initData();
        getChargeDetail(listId);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_charges_deail;
    }

    private void initView() {
        initTitleBar("行政事业性收费清单详情", this, null);
        tv_department = (TextView) findViewById(R.id.tv_department);
        tv_project_approval = (TextView) findViewById(R.id.tv_project_approval);
        tv_charge_project = (TextView) findViewById(R.id.tv_charge_project);
        tv_funds_management = (TextView) findViewById(R.id.tv_funds_management);
        tv_policy_gist = (TextView) findViewById(R.id.tv_policy_gist);
    }

    private void initData() {

    }

    /**
     * 获取阳光政务行政事业性收费清单详情请求
     */
    private void getChargeDetail(String listId) {
        ApiRequest request = OAInterface.getChargeDetail(listId);
        invoke.invokeWidthDialog(request, callBack);
    }

    /**
     * 获取阳光政务行政事业性收费清单数据解析
     *
     * @param result
     */
    private void parseData(ResultItem result) {
        if (result == null) {
            return;
        }
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
                ResultItem result = (ResultItem) item.get("DATA");
                parseData(result);
            } else {
                DialogUtils.showToast(ChargesDetailActivity.this, message);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                ChargesDetailActivity.this.finish();
                break;
            default:
                break;
        }
    }

}
