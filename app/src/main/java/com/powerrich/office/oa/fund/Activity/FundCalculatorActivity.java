package com.powerrich.office.oa.fund.Activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.tools.AverageCapitalPlusInterestUtils;
import com.powerrich.office.oa.tools.AverageCapitalUtils;
import com.powerrich.office.oa.tools.SoftKeyBoardListener;
import com.yt.simpleframe.utils.T;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author PC
 * @date 2018/12/20 11:53
 */
public class FundCalculatorActivity extends BaseActivity {
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.radio)
    RadioGroup radio;
    @BindView(R.id.mt_money)
    TextView mtMoney;
    @BindView(R.id.mt_interest)
    TextView mtInterest;
    @BindView(R.id.mt_subtract)
    TextView mtSubtract;
    @BindView(R.id.mt_amount)
    TextView mtAmount;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.et_year)
    EditText etYear;
    private int type = 0;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_fund_calculator;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        initView();

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb1){
                    type = 0;
                    setFundData1();
                }else {
                    type = 1;
                    setFundData2();
                }
            }
        });
        etYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//
//        etYear.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                //判断是否是“完成”键
//                if(actionId == EditorInfo.IME_ACTION_DONE){
//                    //隐藏软键盘
//                    InputMethodManager imm = (InputMethodManager) v
//                            .getContext().getSystemService(
//                                    Context.INPUT_METHOD_SERVICE);
//                    if (imm.isActive()) {
//                        imm.hideSoftInputFromWindow(
//                                v.getApplicationWindowToken(), 0);
//                    }
//                    if(type == 0){
//                        setFundData1();
//                    }else {
//                        setFundData2();
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //键盘显示
            }

            @Override
            public void keyBoardHide(int height) {
                //键盘隐藏
                if(type == 0){
                    setFundData1();
                }else {
                    setFundData2();
                }
            }
        });
    }
    public void setFundData1(){
        if(TextUtils.isEmpty(etMoney.getText())){
            T.showShort(FundCalculatorActivity.this,"请输入贷款金额");
            return;
        }
        if(TextUtils.isEmpty(etYear.getText())){
            T.showShort(FundCalculatorActivity.this,"请输入贷款期限");
            return;
        }
        double money = Double.parseDouble(etMoney.getText().toString()) * 10000;
        int month = Integer.parseInt(etYear.getText().toString())*12;
        if(month<0){
            return;
        }
        double yearRate = 0.0325;
        mtMoney.setText(AverageCapitalPlusInterestUtils.getPerMonthPrincipalInterest(money,yearRate,month)+"");
        mtSubtract.setText("每月等额");

        Map<Integer, BigDecimal> map = AverageCapitalPlusInterestUtils.getPerMonthInterest(money,yearRate,month);
        mtInterest.setText(map.get(1).toString()+"");
        mtAmount.setText(AverageCapitalPlusInterestUtils.getPrincipalInterestCount(money,yearRate,month)+"");
    }
    public void setFundData2(){
        if(TextUtils.isEmpty(etMoney.getText())){
            T.showShort(FundCalculatorActivity.this,"请输入贷款金额");
            return;
        }
        if(TextUtils.isEmpty(etYear.getText())){
            T.showShort(FundCalculatorActivity.this,"请输入贷款期限");
            return;
        }
        double money = Double.parseDouble(etMoney.getText().toString()) * 10000;;
        int month = Integer.parseInt(etYear.getText().toString())*12;
        if(month<0){
            return;
        }
        double yearRate = 0.0325;
        Map<Integer, Double> map1 = AverageCapitalUtils.getPerMonthPrincipalInterest(money,yearRate,month);
        Map<Integer, Double> map2 = AverageCapitalUtils.getPerMonthInterest(money,yearRate,month);

        mtMoney.setText(map1.get(1)+"");

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        mtSubtract.setText(decimalFormat.format((map1.get(1)-map1.get(2)))+"");
        mtInterest.setText(map2.get(1)+"");
        mtAmount.setText(AverageCapitalUtils.getInterestCount(money,yearRate,month)+money+"");
    }

    public void initView() {
        barTitle.setText("房贷计算器");
        tvRight.setText("利率表");
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1:
                        T.showShort(mContext,"1");
                        break;
                    case R.id.rb2:
                        T.showShort(mContext,"2");
                        break;
                }
            }
        });
    }

    public void setData(){
        mtMoney.setText("00.00");
        mtSubtract.setText("00.00");
        mtInterest.setText("00.00");
        mtAmount.setText("00.00");
    }

    @OnClick({R.id.bar_back,R.id.tv_right})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.bar_back:
                finish();
                break;
            case R.id.tv_right://利率表

                break;
        }
    }
}
