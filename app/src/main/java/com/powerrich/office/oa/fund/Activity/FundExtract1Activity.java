package com.powerrich.office.oa.fund.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.FundInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.fund.bean.FundBean;
import com.powerrich.office.oa.fund.bean.FundCodeBean;
import com.powerrich.office.oa.fund.bean.FundTqlixi;
import com.powerrich.office.oa.fund.bean.FundTqyhxx;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.CountDownTimerUtils;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.GsonUtil;
import com.powerrich.office.oa.tools.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;



import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author PC
 * @date 2019/04/16 11:25
 */
public class FundExtract1Activity extends BaseActivity {
    @BindView(R.id.bar_back)
    ImageView mBarBack;
    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.tv_year)
    TextView mTvYear;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_yue)
    TextView mTvYue;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_lixi)
    TextView mTvLixi;
    @BindView(R.id.tv_heji)
    TextView mTvHeji;
    @BindView(R.id.tv_bank)
    TextView mTvBank;
    @BindView(R.id.tv_bank_number)
    TextView mTvBankNumber;
    @BindView(R.id.rl_bank)
    RelativeLayout mRlBank;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.tv_code)
    TextView mTvCode;
    @BindView(R.id.tv_finish)
    TextView mTvFinish;
    CountDownTimerUtils timerUtils;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_fund_extract1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mBarTitle.setText("退休提取");
        mTvName.setText(FundBean.xingming);
        mTvYue.setText(FundBean.grzhye);
        mTvPhone.setText(FundBean.sjhm);

        getInfo1();
    }

    public void setData(){
    }

    @OnClick({R.id.bar_back,R.id.tv_finish,R.id.rl_bank,R.id.rl_bank1,R.id.tv_code})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.bar_back:
                finish();
                break;
            case R.id.tv_finish:
                if(TextUtils.isEmpty(mTvBank.getText().toString())){
                    ToastUtils.showMessage(FundExtract1Activity.this,"请选择收款银行");
                    return;
                }
                if(TextUtils.isEmpty(mEtCode.getText().toString())){
                    ToastUtils.showMessage(FundExtract1Activity.this,"请输入验证码");
                    return;
                }
                if (null != invoke) {
                    //验证码验证
                    invoke.invokeWidthDialog(OAInterface.fund_smsverify(mEtCode.getText().toString()), callBack, 103);
                }
//                if (null != invoke) {
//                    //获取联行号
//                    invoke.invokeWidthDialog(OAInterface.jcrkhdj(), callBack, 104);
//                }
                break;
            case R.id.rl_bank:
            case R.id.rl_bank1:
                startActivityForResult(new Intent(FundExtract1Activity.this, FundExtract2Activity.class),100);
                break;
            case R.id.tv_code:

                timerUtils = new CountDownTimerUtils(mTvCode,60*1000,1000);
                timerUtils.start();

                if (null != invoke) {
                    //验证码发送
                    invoke.invokeWidthDialog(OAInterface.fund_smssend(), callBack, 102);
                }
                break;
        }
    }

    private void getInfo1() {
        if (null != invoke) {
            //缴存人销户利息查询
            invoke.invokeWidthDialog(OAInterface.jcrtqxxcx(), callBack, 100);

            //收款银行卡信息查询
            invoke.invokeWidthDialog(OAInterface.jcrtqxxcx1(), callBack, 101);
        }
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {
        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String message = item.getString("message");
            String code = item.getString("code");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if(what == 100){
                    //{"DATA":"{\"success\":false,\"msg\":null,\"totalcount\":0,\"results\":null,\"erros\":null,\"vdMapList\":null,\"data\":
                    // {\"grbh\":null,\"dkqk\":null,\"tqrq\":null,\"tqjehj\":0.0,\"dkhth\":null,\"zjhm\":null,\"bm\":null,\"mc\":null,\"minsybj\":0.0,
                    // \"minhjcs\":0,\"maxtqbl\":0.0,\"tqzq\":null,\"tqcs\":0,\"sfxd\":0,\"age1\":0,\"age2\":0,\"tqsx\":0,\"minyhcs\":0,\"bm1\":null,
                    // \"mc1\":null,\"count\":0,\"page\":0,\"size\":0,\"userid\":21167,\"dwzh\":\"010117\",\"dwmc\":null,\"grzh\":\"000000018173\",
                    // \"xingming\":null,\"grzhye\":0.0,\"grzhzt\":null,\"fwxxdz\":null,\"fwmj\":0.0,\"dkbj\":0.0,\"dkye\":0.0,\"yhbxje\":0.0,\"zjlx\
                    // ":248.7,\"zxbm\":\"0101\",\"jgbm\":\"01\",\"khbh\":null,\"zhbh\":null,\"ywfl\":\"02\",\"ywlb\":\"12\",\"blqd\":\"app\",\"ffbm\"
                    // :\"08\",\"sfqyxd\":null,\"sftqsx\":null,\"msg\":null,\"ret\":0,\"tqlx\":null,\"tqje\":0.0,\"tqjedws\":0,\"maxtqe\":0.0,\"dkzt\"
                    // :null,\"dkffrq\":null,\"dkjqrq\":null,\"fwzj\":0.0,\"gfsfk\":0.0,\"lnljtqbj\":0.0,\"gfhtbh\":null}}","code":"0","message":"操作成功！"}
                    String data = item.getDataStr();
                        FundTqlixi fundTqlixi = GsonUtil.GsonToBean(data,FundTqlixi.class);
                        if(fundTqlixi.getData()!= null) {
                            FundBean.zjlx = fundTqlixi.getData().getZjlx();
                            mTvLixi.setText(FundBean.zjlx);
                            mTvHeji.setText((Double.parseDouble(FundBean.grzhye) +Double.parseDouble(FundBean.zjlx))+"");
                        }
                }else if(what == 101){
                    String data = item.getDataStr();
                    FundTqyhxx fundTqyhxx = GsonUtil.GsonToBean(data,FundTqyhxx.class);
                    if(fundTqyhxx.getData()!= null) {
                        if(fundTqyhxx.getData().size()>0) {
//                            FundBean.yhzh = fundTqyhxx.getData().get(0).getYhzh();
//                            FundBean.khyh = fundTqyhxx.getData().get(0).getKhyh();
//                            mTvBank.setText(FundBean.khyh);
//                            mTvBankNumber.setText(FundBean.yhzh);
                        }
                    }
                }else if(what == 102){
                    String data = item.getDataStr();
                    FundCodeBean fundCodeBean = GsonUtil.GsonToBean(data,FundCodeBean.class);
                        try {
                            JSONObject jsonObject = new JSONObject(fundCodeBean.getDATA());
                            if (jsonObject.optString("ret").equals("0")) {
                                ToastUtils.showMessage(FundExtract1Activity.this, "验证码发送成功");
                            } else {
                                ToastUtils.showMessage(FundExtract1Activity.this, jsonObject.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }else if(what == 103){
                    String data = item.getDataStr();
                    FundCodeBean fundCodeBean = GsonUtil.GsonToBean(data,FundCodeBean.class);
//                    if(fundCodeBean.getData()!= null){
//                        if(fundCodeBean.getData().getRet().equals("0")){
//                            ToastUtils.showMessage(FundExtract1Activity.this,"验证成功");
//                        }else {
//                            ToastUtils.showMessage(FundExtract1Activity.this,"验证失败，请重新获取");
//                        }
//                    }else {
//                        ToastUtils.showMessage(FundExtract1Activity.this,"验证失败，请重新获取");
//                    }
                }else if(what == 104){
                    String data = item.getDataStr();
                }
            } else {
                DialogUtils.showToast(FundExtract1Activity.this, message);
            }
        }
    };

    String lhh;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100 && resultCode == 100){
            String yhzh = data.getStringExtra("yhzh");
            String skyhmc = data.getStringExtra("skyhmc");
            lhh = data.getStringExtra("lhh");
            mTvBank.setText(skyhmc);
            mTvBankNumber.setText(yhzh);
        }
    }
}
