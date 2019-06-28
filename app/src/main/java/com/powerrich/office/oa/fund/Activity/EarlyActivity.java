package com.powerrich.office.oa.fund.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.FundInfo;
import com.powerrich.office.oa.bean.RepaymentPlanInfo;
import com.powerrich.office.oa.fund.bean.LoanAccountInfo;
import com.powerrich.office.oa.fund.utils.FundUtils;
import com.powerrich.office.oa.fund.view.CInterface.IGenerateUI;
import com.powerrich.office.oa.fund.view.GenerateUtil;
import com.powerrich.office.oa.fund.view.WidgetType;
import com.powerrich.office.oa.fund.view.entity.Prepayment;
import com.yt.simpleframe.http.bean.entity.ReFundJkrgxInfo;
import com.yt.simpleframe.http.bean.entity.ReFundYQCXInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 提前还本
 */
public class EarlyActivity extends BaseActivity implements View.OnClickListener , IGenerateUI {

    private LinearLayout llContent;
    private GenerateUtil mGenerateUtil;
    private LoanAccountInfo mFundDkInfo;
    private RepaymentPlanInfo.DATABean mFundHkjhInfo;
    private ReFundYQCXInfo mFundYqwhkInfo;
    private ReFundJkrgxInfo mFundJkrgxInfo;
    private FundInfo.DATABean mFundGjjZhInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar("提前还本", this, null);
        mGenerateUtil = new GenerateUtil();
        initView();
        initData();

        Log.e("jsc", "EarlyActivity-onCreate:"+new Gson().toJson(mFundDkInfo));
    }

    private void initData() {
        mFundGjjZhInfo = FundUtils.getInstance().getFundGjjZhInfo(this);
        mFundDkInfo = FundUtils.getInstance().getFundDkInfo(this);
        mFundHkjhInfo = FundUtils.getInstance().getFundHkjhInfo(this);
        mFundYqwhkInfo = FundUtils.getInstance().getFundYqwhkInfo(this);
        mFundJkrgxInfo = FundUtils.getInstance().getFundJkrgxInfo(this);
        List<Prepayment> prepaymentList = new ArrayList<>();
        prepaymentList.add(new Prepayment().setTitle("贷款信息").setType(WidgetType.CcSmallTitleView));
        prepaymentList.add(new Prepayment().setTitle("借款合同编号").setValue(mFundDkInfo.getJkhtbh()).setClick(true));
        prepaymentList.add(new Prepayment().setTitle("贷款余额").setValue(mFundDkInfo.getDkye()));
        prepaymentList.add(new Prepayment().setTitle("当前月还金额").setValue(mFundHkjhInfo.getYhbjhj()+"元"));
        prepaymentList.add(new Prepayment().setTitle("应还日期").setValue(mFundHkjhInfo.getYhrq()));
        prepaymentList.add(new Prepayment().setTitle("逾期本金").setValue(mFundYqwhkInfo.getYqbj()+"元"));
        prepaymentList.add(new Prepayment().setTitle("逾期利息").setValue(mFundYqwhkInfo.getYqlx()+"元"));
        prepaymentList.add(new Prepayment().setTitle("逾期罚息").setValue(mFundYqwhkInfo.getYqfx()+"元"));
        prepaymentList.add(new Prepayment().setTitle("逾期期次").setValue(mFundDkInfo.getDqyqcs()+"次"));
        prepaymentList.add(new Prepayment().setTitle("还款银行").setValue(mFundDkInfo.getZhkhyhmc()));
        prepaymentList.add(new Prepayment().setTitle("还款银行卡号").setValue(mFundDkInfo.getZhkhyhmc()));

        prepaymentList.add(new Prepayment().setTitle("缴存信息").setType(WidgetType.CcSmallTitleView));
        prepaymentList.add(new Prepayment().setTitle("与主借款人关系").setValue(mFundJkrgxInfo.getJkrgx()).setClick(true));
        prepaymentList.add(new Prepayment().setTitle("姓名").setValue(mFundJkrgxInfo.getXm()));
        prepaymentList.add(new Prepayment().setTitle("个人账号").setValue(mFundGjjZhInfo.getGrzh()));
        prepaymentList.add(new Prepayment().setTitle("证件号码").setValue(mFundGjjZhInfo.getZjhm()));
        prepaymentList.add(new Prepayment().setTitle("个人账户状态").setValue(mFundGjjZhInfo.getGrzhzt()));
        prepaymentList.add(new Prepayment().setTitle("月缴存额").setValue(mFundGjjZhInfo.getGryjce()));
        prepaymentList.add(new Prepayment().setTitle("公积金余额").setValue(mFundGjjZhInfo.getGrzhye()));

        prepaymentList.add(new Prepayment().setTitle("我要还款").setType(WidgetType.CcSmallTitleView));
        prepaymentList.add(new Prepayment().setTitle("提前还款金额").setType(WidgetType.CcEditView));

        prepaymentList.add(new Prepayment().setTitle("提前还款录入").setType(WidgetType.CcSmallTitleView));
        prepaymentList.add(new Prepayment().setTitle("提前还本方式").setValue("期限不变，重新计算月还金额").setClick(true));
        prepaymentList.add(new Prepayment().setTitle("支取公积金金额").setType(WidgetType.CcEditView));
        prepaymentList.add(new Prepayment().setTitle("银行扣划金额").setValue("0.00元"));


        prepaymentList.add(new Prepayment().setTitle("贷款信息").setType(WidgetType.CcSmallTitleView));
        prepaymentList.add(new Prepayment().setTitle("提前还本金").setType(WidgetType.CcEditView));
        prepaymentList.add(new Prepayment().setTitle("提前归还利息").setValue("265元"));
        prepaymentList.add(new Prepayment().setTitle("本次还款后月还金额").setValue("959.22元"));
        prepaymentList.add(new Prepayment().setTitle("变更后剩余还款期次").setValue("10"));
        mGenerateUtil.startUI(this,prepaymentList);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_early;
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private void initView() {
        llContent = (LinearLayout) findViewById(R.id.ll_content);
        mGenerateUtil.setIGenerateUI(this);

    }

    @Override
    public void onStartUISuceess(View view) {
        llContent.addView(view);
    }

    @Override
    public void onFail(String error) {
        Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
    }
}
