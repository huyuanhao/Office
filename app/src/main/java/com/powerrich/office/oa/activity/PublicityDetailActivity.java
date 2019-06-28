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
 * 文 件 名：PublicityDetailActivity
 * 描   述：阳光政务负面公示详情界面
 * 作   者：Wangzheng
 * 时   间：2017/12/5
 * 版   权：v1.0
 */
public class PublicityDetailActivity extends BaseActivity implements View.OnClickListener {

    private String enterpriseName;
    private String publicityId;
    private TextView tv_enterprise_name, tv_accuse_reason, tv_publicity_information, tv_publicity_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enterpriseName = getIntent().getStringExtra("enterprise_name");
        publicityId = getIntent().getStringExtra("publicity_id");
        initView();
        initData();
        getPublicityDetail(publicityId);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_publicity_deail;
    }

    private void initView() {
        initTitleBar(enterpriseName, this, null);
        tv_enterprise_name = (TextView) findViewById(R.id.tv_enterprise_name);
        tv_accuse_reason = (TextView) findViewById(R.id.tv_accuse_reason);
        tv_publicity_information = (TextView) findViewById(R.id.tv_publicity_information);
        tv_publicity_date = (TextView) findViewById(R.id.tv_publicity_date);
    }

    private void initData() {

    }

    /**
     * 获取阳光政务负面公示详情请求
     */
    private void getPublicityDetail(String publicityId) {
        ApiRequest request = OAInterface.getPublicityList(publicityId, "");
        invoke.invokeWidthDialog(request, callBack);
    }

    /**
     * 阳光政务负面公示详情数据解析
     *
     * @param result
     */
    private void parseData(ResultItem result) {
        tv_enterprise_name.setText(result.getString("APPLY_ERPRISE_NAME"));
        tv_accuse_reason.setText(result.getString("APPLY_NAME"));
        tv_publicity_information.setText(result.getString("SHOW_MESSAGE"));
        tv_publicity_date.setText(result.getString("DESDATE"));
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
                DialogUtils.showToast(PublicityDetailActivity.this, message);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                PublicityDetailActivity.this.finish();
                break;
            default:
                break;
        }
    }

}
