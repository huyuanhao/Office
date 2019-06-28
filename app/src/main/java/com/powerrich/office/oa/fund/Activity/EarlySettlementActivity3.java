package com.powerrich.office.oa.fund.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.FundInfo;
import com.powerrich.office.oa.fund.bean.LoanAccountInfo;
import com.powerrich.office.oa.fund.utils.FundUtils;
import com.powerrich.office.oa.tools.BeanUtils;
import com.yt.simpleframe.http.bean.entity.ReFundYQCXInfo;
import com.yt.simpleframe.utils.CountDownHelper;

public class EarlySettlementActivity3 extends BaseActivity implements View.OnClickListener {

    private TextView mTv_dqdkye;
    private TextView mTv_tqhkhj;
    private TextView mTv_tqghlx;
    private TextView mTv_dqyqhj;
    private TextView mTv_ysqzqzjkrgjj;
    private TextView mTv_dhje;
    private TextView mTv_phone;
    private TextView mTv_verify_code;
    private EditText mEt_verify_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar("提前结清", this, null);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_early_settlement3;
    }

    private void initView() {
        mTv_dqdkye = findViewById(R.id.tv_dqdkye);
        mTv_tqhkhj = findViewById(R.id.tv_tqhkhj);
        mTv_tqghlx = findViewById(R.id.tv_tqghlx);
        mTv_dqyqhj = findViewById(R.id.tv_dqyqhj);
        mTv_ysqzqzjkrgjj = findViewById(R.id.tv_ysqzqzjkrgjj);
        mTv_dhje = findViewById(R.id.tv_dhje);
        mTv_phone = findViewById(R.id.tv_phone);
        mTv_verify_code = findViewById(R.id.tv_verify_code);
        mEt_verify_code = findViewById(R.id.et_verify_code);
        mTv_verify_code.setOnClickListener(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String tqhkhj = intent.getStringExtra("tqhkhj");
        String tqghlx = intent.getStringExtra("tqghlx");
        String tqgjjhkje = intent.getStringExtra("tqgjjhkje");
        LoanAccountInfo fundDkInfo = FundUtils.getInstance().getFundDkInfo(this);
        ReFundYQCXInfo fundYqwhkInfo = FundUtils.getInstance().getFundYqwhkInfo(this);
        FundInfo.DATABean fundGjjZhInfo = FundUtils.getInstance().getFundGjjZhInfo(this);
        if (!BeanUtils.isEmpty(fundDkInfo)) {
            mTv_dqdkye.setText(fundDkInfo.getDkye() + "元");
        }
        if (!BeanUtils.isEmptyStr(tqhkhj)) {
            mTv_tqhkhj.setText(tqhkhj + "元");
        }
        if (!BeanUtils.isEmptyStr(tqghlx)) {
            mTv_tqghlx.setText(tqghlx + "元");
        }
        if (!BeanUtils.isEmpty(fundYqwhkInfo)) {
            float yqToatal = Float.valueOf(fundYqwhkInfo.getYqbjhj()) + Float.valueOf(fundYqwhkInfo.getYqfxhj()) + Float.valueOf(fundYqwhkInfo.getYqlxhj());
            mTv_dqyqhj.setText(yqToatal + "元");
        }
        if (!BeanUtils.isEmptyStr(tqgjjhkje)) {
            mTv_ysqzqzjkrgjj.setText(tqgjjhkje + "元");
        }
        if (!BeanUtils.isEmpty(fundGjjZhInfo)) {
            mTv_phone.setText(fundGjjZhInfo.getSjhm());
        }

    }

    //开始倒计时
    private void startCountDown() {
        CountDownHelper helper = new CountDownHelper(mTv_verify_code, "发送短信验证码", "重新获取", 60, 1);
        helper.setOnFinishListener(new CountDownHelper.OnFinishListener() {
            @Override
            public void finish() {
                mTv_verify_code.setTextColor(Color.parseColor("#404E61"));
            }
        });
        helper.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.tv_verify_code:
                startCountDown();
                break;
        }
    }
}
