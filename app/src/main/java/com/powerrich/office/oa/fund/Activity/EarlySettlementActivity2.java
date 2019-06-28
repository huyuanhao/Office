package com.powerrich.office.oa.fund.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.FundInfo;
import com.powerrich.office.oa.bean.FundLoanInfo;
import com.powerrich.office.oa.bean.RepaymentPlanInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.fund.bean.LoanAccountInfo;
import com.powerrich.office.oa.fund.utils.FundUtils;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.GsonUtil;
import com.yt.simpleframe.http.bean.entity.ReFundYQCXInfo;

import java.util.List;

public class EarlySettlementActivity2 extends BaseActivity implements View.OnClickListener {

    private TextView mTv_dqdkye;
    private TextView mTv_dqyqhj;
    private TextView mTv_bctqhkze;
    private TextView mTv_tqghlx;
    private TextView mTv_grzhye;
    private EditText mEt_tqgjjhkje;
    private TextView mTv_dhkje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar("提前结清", this, null);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_early_settlement2;
    }

    private void initView() {
        findViewById(R.id.bt_next).setOnClickListener(this);
        mTv_dqdkye = findViewById(R.id.tv_dqdkye);
        mTv_dqyqhj = findViewById(R.id.tv_dqyqhj);
        mTv_bctqhkze = findViewById(R.id.tv_bctqhkze);
        mTv_tqghlx = findViewById(R.id.tv_tqghlx);
        mTv_grzhye = findViewById(R.id.tv_grzhye);
        mEt_tqgjjhkje = findViewById(R.id.et_tqgjjhkje);
        mTv_dhkje = findViewById(R.id.tv_dhkje);

        initData();
    }

    private void initData() {
        LoanAccountInfo fundDkInfo = FundUtils.getInstance().getFundDkInfo(this);
        ReFundYQCXInfo fundYqwhkInfo = FundUtils.getInstance().getFundYqwhkInfo(this);
        FundInfo.DATABean fundGjjZhInfo = FundUtils.getInstance().getFundGjjZhInfo(this);
        if (!BeanUtils.isEmpty(fundDkInfo)) {
            mTv_dqdkye.setText(fundDkInfo.getDkye() + "元");
            mTv_bctqhkze.setText(fundDkInfo.getDkye());
            mTv_dhkje.setText(fundDkInfo.getDkye());
            //公积金信息查询
            invoke.invokeWidthDialog(OAInterface.jstqjqlx("20180502030002", fundDkInfo.getDkye()), callBack);
        }
        if (!BeanUtils.isEmpty(fundYqwhkInfo)) {
            float yqToatal = Float.valueOf(fundYqwhkInfo.getYqbjhj()) + Float.valueOf(fundYqwhkInfo.getYqfxhj()) + Float.valueOf(fundYqwhkInfo.getYqlxhj());
            mTv_dqyqhj.setText(yqToatal + "元");
        }
        if (!BeanUtils.isEmpty(fundGjjZhInfo)) {
            mTv_grzhye.setText(fundGjjZhInfo.getGrzhye() + "元");
        }

    }

    IRequestCallBack callBack = new BaseRequestCallBack() {
        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String message = item.getString("message");
            String code = item.getString("code");
            if (Constants.SUCCESS_CODE.equals(code)) {

            } else {
                DialogUtils.showToast(EarlySettlementActivity2.this, message);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.bt_next:
                Intent intent = new Intent(this, EarlySettlementActivity3.class);
                intent.putExtra("tqghlx", mTv_tqghlx.getText().toString());
                intent.putExtra("tqgjjhkje", mEt_tqgjjhkje.getText().toString());
                startActivity(intent);
                break;
        }
    }
}
