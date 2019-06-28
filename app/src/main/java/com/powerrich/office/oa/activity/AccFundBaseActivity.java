package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;

import java.util.List;

public class AccFundBaseActivity extends BaseActivity implements View.OnClickListener{

    private TextView acc_fund1;
    private TextView acc_fund2;
    private TextView acc_fund3;
    private TextView acc_fund4;
    private TextView acc_fund5;
    private TextView acc_fund6;
    private TextView acc_fund7;
    private TextView acc_fund8;
    private TextView acc_fund9;
    private TextView acc_fund10;
    private View empty_view, result_view;
    private TextView bottom_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_acc_fund_base;
    }

    private void initView() {
        initTitleBar("公积金基本信息查询", this, null);
        findViewById(R.id.back).setOnClickListener(this);
        acc_fund1 = (TextView) findViewById(R.id.acc_fund1);
        acc_fund2 = (TextView) findViewById(R.id.acc_fund2);
        acc_fund3 = (TextView) findViewById(R.id.acc_fund3);
        acc_fund4 = (TextView) findViewById(R.id.acc_fund4);
        acc_fund5 = (TextView) findViewById(R.id.acc_fund5);
        acc_fund6 = (TextView) findViewById(R.id.acc_fund6);
        acc_fund7 = (TextView) findViewById(R.id.acc_fund7);
        acc_fund8 = (TextView) findViewById(R.id.acc_fund8);
        acc_fund9 = (TextView) findViewById(R.id.acc_fund9);
        acc_fund10 = (TextView) findViewById(R.id.acc_fund10);
        empty_view = findViewById(R.id.empty_view);
        result_view = findViewById(R.id.result_view);
        findViewById(R.id.empty_back).setOnClickListener(this);
        bottom_text = (TextView) findViewById(R.id.bottom_text);
        loadData();
    }

    private void loadData() {
        bottom_text.setText("鹰潭市住房公积金管理中心");
        //account:"000000012568", idcard:"362124197308231430"
        invoke.invokeWidthDialog(OAInterface.getAccumulationFund("", LoginUtils.getInstance().getUserInfo().getDATA().getIDCARD()), callBack);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                List<ResultItem> items = (List<ResultItem>) item.get("DATA");
                if (null != items && items.size() >= 1) {
                    result_view.setVisibility(View.VISIBLE);
                    empty_view.setVisibility(View.GONE);
                    ResultItem resultItem = items.get(0);
                    acc_fund1.setText(resultItem.getString("DWMC"));
                    acc_fund2.setText(resultItem.getString("GJJZH"));
                    acc_fund3.setText(resultItem.getString("GJJZHZT"));
                    acc_fund4.setText(resultItem.getString("GRXM"));
                    acc_fund5.setText(resultItem.getString("GRYJCE"));
                    acc_fund6.setText(resultItem.getString("GRZHYE"));
                    acc_fund7.setText(resultItem.getString("JCZRQ"));
                    acc_fund8.setText(resultItem.getString("KHRQ"));
                    acc_fund9.setText(resultItem.getString("SFDJ"));
                    acc_fund10.setText(resultItem.getString("SFZ"));
                } else {
                    empty_view.setVisibility(View.VISIBLE);
                    result_view.setVisibility(View.GONE);
                }
            } else {
                DialogUtils.showToast(AccFundBaseActivity.this, message);
            }
        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.empty_back:
                finish();
                break;
        }
    }
}
