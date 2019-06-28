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
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;

/**
 * 文 件 名：PowerListDetailActivity
 * 描   述：阳光政务行政权力清单详情界面
 * 作   者：Wangzheng
 * 时   间：2018/1/23
 * 版   权：v1.0
 */
public class PowerListDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_exploiting_work_unit, tv_project_name, tv_sub_item_name, tv_sub_item_exploiting_work_unit, tv_power_type, tv_setting_gist, tv_remark;
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pid = getIntent().getStringExtra("pid");
        initView();
        initData();
        getPowerListDetail(pid);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_power_list_deail;
    }

    private void initView() {
        initTitleBar("行政权力清单详情", this, null);
        tv_exploiting_work_unit = (TextView) findViewById(R.id.tv_exploiting_work_unit);
        tv_project_name = (TextView) findViewById(R.id.tv_project_name);
        tv_sub_item_name = (TextView) findViewById(R.id.tv_sub_item_name);
        tv_sub_item_exploiting_work_unit = (TextView) findViewById(R.id.tv_sub_item_exploiting_work_unit);
        tv_power_type = (TextView) findViewById(R.id.tv_power_type);
        tv_setting_gist = (TextView) findViewById(R.id.tv_setting_gist);
        tv_remark = (TextView) findViewById(R.id.tv_remark);
    }

    private void initData() {

    }

    /**
     * 获取行政权力清单-根据id查询行政权力清单具体详情请求
     */
    private void getPowerListDetail(String pid) {
        ApiRequest request = OAInterface.getPowerById(pid);
        invoke.invokeWidthDialog(request, callBack);
    }

    /**
     * 获取行政权力清单-根据id查询行政权力清单具体详情数据解析
     *
     * @param result
     */
    private void parseData(ResultItem result) {
        if (result == null) {
            return;
        }
        tv_exploiting_work_unit.setText(result.getString("NORMACCEPTDEPART"));
        tv_project_name.setText(result.getString("ITEMNAME"));
        tv_sub_item_name.setText("".equals(result.getString("PARENTNAME")) ? getString(R.string.none_sub_item_name) : result.getString("PARENTNAME"));
        tv_sub_item_exploiting_work_unit.setText("".equals(result.getString("PARENTID")) ? getString(R.string.none_sub_item_exploiting_work_unit) : result.getString("PARENTID"));
        tv_power_type.setText(result.getString("POWER_TYPE_NAME"));
        tv_setting_gist.setText(result.getString("SDYJ"));
        tv_remark.setText(result.getString("REMARK"));
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
                DialogUtils.showToast(PowerListDetailActivity.this, message);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                PowerListDetailActivity.this.finish();
                break;
            default:
                break;
        }
    }

}
