package com.powerrich.office.oa.fund.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.FundInfo;
import com.powerrich.office.oa.bean.RepaymentPlanInfo;
import com.powerrich.office.oa.fund.bean.LoanAccountInfo;
import com.powerrich.office.oa.fund.utils.FundUtils;
import com.powerrich.office.oa.tools.BeanUtils;

/**
 * 提前结清
 */
public class EarlySettlementActivity extends BaseActivity implements View.OnClickListener {

    /** 公积金账号*/
    private TextView mTv_gjjzh;
    /** 借款合同编号*/
    private TextView mTv_jkhtbh;
    /** 借款人姓名*/
    private TextView mTv_jkrxm;
    /** 借款人证件号码*/
    private TextView mTv_jkrzjhm;
    /** 当前贷款余额*/
    private TextView mTv_dqdkye;
    /** 当前月还余额*/
    private TextView mTv_dqyhye;
    /** 还款卡号*/
    private TextView mTv_hkkh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar("提前结清", this, null);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_early_settlement;
    }

    private void initView() {
        findViewById(R.id.bt_next).setOnClickListener(this);
        mTv_gjjzh = findViewById(R.id.tv_gjjzh);
        mTv_jkhtbh = findViewById(R.id.tv_jkhtbh);
        mTv_jkrxm = findViewById(R.id.tv_jkrxm);
        mTv_jkrzjhm = findViewById(R.id.tv_jkrzjhm);
        mTv_dqdkye = findViewById(R.id.tv_dqdkye);
        mTv_dqyhye = findViewById(R.id.tv_dqyhye);
        mTv_hkkh = findViewById(R.id.tv_hkkh);
        initData();
    }

    private void initData() {
        FundInfo.DATABean fundGjjZhInfo = FundUtils.getInstance().getFundGjjZhInfo(this);
        LoanAccountInfo fundDkInfo = FundUtils.getInstance().getFundDkInfo(this);
        RepaymentPlanInfo.DATABean fundHkjhInfo = FundUtils.getInstance().getFundHkjhInfo(this);
        if (!BeanUtils.isEmpty(fundGjjZhInfo)) {
            mTv_gjjzh.setText(fundGjjZhInfo.getGrzh());
        }
        if (!BeanUtils.isEmpty(fundDkInfo)) {
            mTv_jkhtbh.setText(fundDkInfo.getJkhtbh());
            mTv_jkrxm.setText(fundDkInfo.getJkrxm());
            mTv_jkrzjhm.setText(fundDkInfo.getJkrzjh());
            mTv_dqdkye.setText(fundDkInfo.getDkye() + "元");
            mTv_hkkh.setText(fundDkInfo.getHkzh());
        }
        if (!BeanUtils.isEmpty(fundHkjhInfo)) {
            mTv_dqyhye.setText(fundHkjhInfo.getYhbx() + "元");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.bt_next:
                startActivity(new Intent(this, EarlySettlementActivity2.class));
                break;
        }
    }
}
