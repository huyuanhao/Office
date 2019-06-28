package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.AutoUtils;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.view.DeletableEditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 文件名：RegisterActivity
 * 描述：注册
 * 作者：白煜
 * 时间：2017/11/16 0016
 * 版权：
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    public static final String REGISTER_TYPE_PERSON = "person";
    public static final String REGISTER_TYPE = "register_type";
    public static final String REGISTER_TYPE_COMPANY = "company";
    private static final int WHAT_PERSON_REGISTER = 0;
    private static final int WHAT_COMPANY_REGISTER = 1;
    public static final int SENDYZM = 2;
    public static final int SENDYZM_P = 3;

    private View mCompanyRegisterLayout;
    private View mPersonRegisterLayout;
    private String mRegisterType;

    private boolean isFirstLoadCompanyRegisterLayout = true;
    private boolean isFirstLoadPersonRegisterLayout = true;
    private EditText mEt_company_name;
    private DeletableEditText mEt_mobile_phone_number;
    private DeletableEditText mEt_code;
    private TextView tv_timer;
    private EditText mEt_company_linkman;
    private EditText mEt_user_name;
    private EditText mEt_password;
    private TextView mTv_goto_login;
    private TextView mTv_person_register;
    private CheckBox mCb_protocols;
    private Button mBt_register;
    private String sendPhoneNum = "";

    private EditText mEt_p_user_name;
    private EditText mEt_p_password;
    private EditText mEt_p_password_again;
    private DeletableEditText mEt_p_mobile_phone_number;
    private DeletableEditText mEt_p_code;
    private TextView tv_p_timer;
    private CheckBox mCb_p_protocols;
    private Button mBt_p_register;
    private TextView mTv_p_goto_login;
    private TextView mTv_company_register;
    private String sendPPhoneNum = "";
    private String valiCode = "";
    private String valiCode_p = "";
    private int countDown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        mCompanyRegisterLayout = inflater.inflate(R.layout.activity_company_register, null);
        mPersonRegisterLayout = inflater.inflate(R.layout.activity_person_register, null);
        AutoUtils.auto(mCompanyRegisterLayout);
        AutoUtils.auto(mPersonRegisterLayout);
        mRegisterType = getIntent().getStringExtra(REGISTER_TYPE);
        if (mRegisterType.equals(REGISTER_TYPE_PERSON)) {
            loadPersonRegisterLayout();
        } else if (mRegisterType.equals(REGISTER_TYPE_COMPANY)) {
            loadCompanyRegisterLayout();
        }
    }

    @Override
    protected int provideContentViewId() {
        return -1;
    }

    private void loadCompanyRegisterLayout() {
        setContentView(mCompanyRegisterLayout);
        if (isFirstLoadCompanyRegisterLayout) {
            isFirstLoadCompanyRegisterLayout = false;
            mEt_company_name = (EditText) mCompanyRegisterLayout.findViewById(R.id.et_company_name);
            mEt_company_linkman = (EditText) mCompanyRegisterLayout.findViewById(R.id.et_company_linkman);
            mEt_user_name = (EditText) mCompanyRegisterLayout.findViewById(R.id.et_user_name);
            mEt_password = (EditText) mCompanyRegisterLayout.findViewById(R.id.et_password);
            mEt_mobile_phone_number = (DeletableEditText) mCompanyRegisterLayout.findViewById(R.id.et_mobile_phone_number);
            tv_timer = (TextView) mCompanyRegisterLayout.findViewById(R.id.tv_timer);
            mEt_code = (DeletableEditText) mCompanyRegisterLayout.findViewById(R.id.et_code);
            mCb_protocols = (CheckBox) mCompanyRegisterLayout.findViewById(R.id.cb_protocols);
            mBt_register = (Button) mCompanyRegisterLayout.findViewById(R.id.bt_register);
            mTv_goto_login = (TextView) mCompanyRegisterLayout.findViewById(R.id.tv_goto_login);
            mTv_person_register = (TextView) mCompanyRegisterLayout.findViewById(R.id.tv_person_register);
            tv_timer.setOnClickListener(this);
            mTv_goto_login.setOnClickListener(this);
            mTv_person_register.setOnClickListener(this);
            mBt_register.setOnClickListener(this);
        }
    }

    private void loadPersonRegisterLayout() {
        setContentView(mPersonRegisterLayout);
        if (isFirstLoadPersonRegisterLayout) {
            isFirstLoadPersonRegisterLayout = false;

            mEt_p_user_name = (EditText) mPersonRegisterLayout.findViewById(R.id.et_p_user_name);
            mEt_p_password = (EditText) mPersonRegisterLayout.findViewById(R.id.et_p_password);
            mEt_p_password_again = (EditText) mPersonRegisterLayout.findViewById(R.id.et_p_password_again);
            mEt_p_mobile_phone_number = (DeletableEditText) mPersonRegisterLayout.findViewById(R.id.et_p_mobile_phone_number);
            tv_p_timer = (TextView) mPersonRegisterLayout.findViewById(R.id.tv_p_timer);
            mEt_p_code = (DeletableEditText) mPersonRegisterLayout.findViewById(R.id.et_p_code);
            mCb_p_protocols = (CheckBox) mPersonRegisterLayout.findViewById(R.id.cb_p_protocols);
            mBt_p_register = (Button) mPersonRegisterLayout.findViewById(R.id.bt_p_register);
            mTv_p_goto_login = (TextView) mPersonRegisterLayout.findViewById(R.id.tv_p_goto_login);
            mTv_company_register = (TextView) mPersonRegisterLayout.findViewById(R.id.tv_company_register);
            tv_p_timer.setOnClickListener(this);
            mTv_p_goto_login.setOnClickListener(this);
            mTv_company_register.setOnClickListener(this);
            mBt_p_register.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_timer:
                sendPhoneNum = mEt_mobile_phone_number.getText().toString().trim();
                if (!BeanUtils.isMobileNO(sendPhoneNum)) {
                    DialogUtils.showToast(RegisterActivity.this, "手机号格式不正确");
                    return;
                }
                //发送短信接口
                invoke.invokeWidthDialog(OAInterface.getPhoneValidateCode(sendPhoneNum), callBack,SENDYZM);
                //设置倒计时
                setTimerTask(tv_timer);
                break;
            case R.id.tv_p_timer:
                sendPPhoneNum = mEt_p_mobile_phone_number.getText().toString().trim();
                if (!BeanUtils.isMobileNO(sendPPhoneNum)) {
                    DialogUtils.showToast(RegisterActivity.this, "手机号格式不正确");
                    return;
                }
                //发送短信接口
                invoke.invokeWidthDialog(OAInterface.getPhoneValidateCode(sendPPhoneNum), callBack,SENDYZM_P);
                //设置倒计时
                setTimerTask(tv_p_timer);
                break;
            case R.id.tv_person_register:
                loadPersonRegisterLayout();
                break;
            case R.id.tv_company_register:
                loadCompanyRegisterLayout();
                break;
            case R.id.tv_goto_login:
                finish();
                break;
            case R.id.tv_p_goto_login:
                finish();
                break;
            case R.id.bt_register:
                if (checkCompanyRegisterData()) {
                    invoke.invokeWidthDialog(OAInterface.register(mEt_user_name.getText().toString().trim(),
                            mEt_password.getText().toString().trim(),
                            mEt_company_name.getText().toString().trim(),
                            mEt_company_linkman.getText().toString().trim(),
                            sendPhoneNum,
                            "1"), callBack, WHAT_COMPANY_REGISTER);
                }
                break;
            case R.id.bt_p_register:
                if (checkPersonRegisterData()) {
                    invoke.invokeWidthDialog(OAInterface.register(mEt_p_user_name.getText().toString().trim(),
                            mEt_p_password.getText().toString().trim(),
                            "",
                            "",
                            sendPPhoneNum,
                            "0"), callBack, WHAT_PERSON_REGISTER);
                }
                break;
            default:
                break;
        }
    }

    private boolean checkPersonRegisterData() {
        if (BeanUtils.isEmptyStr(mEt_p_user_name.getText().toString().trim())) {
            DialogUtils.showToast(this, "用户名不能为空");
            return false;
        }
        if (BeanUtils.isEmptyStr(mEt_p_password.getText().toString().trim())) {
            DialogUtils.showToast(this, "密码不能为空");
            return false;
        }
        if (!mEt_p_password.getText().toString().trim().equals(mEt_p_password_again.getText().toString().trim())) {
            DialogUtils.showToast(this, "两次密码不相同");
            return false;
        }
        if (BeanUtils.isEmptyStr(mEt_p_mobile_phone_number.getText().toString().trim())) {
            DialogUtils.showToast(this, "手机号码不能为空");
            return false;
        }
        if (!BeanUtils.isMobileNO(mEt_p_mobile_phone_number.getText().toString().trim())) {
            DialogUtils.showToast(this, "手机号格式不正确");
            return false;
        }
        if (BeanUtils.isEmptyStr(mEt_p_code.getText().toString().trim())) {
            DialogUtils.showToast(this, "验证码不能为空");
            return false;
        }
        if (!valiCode_p.equals(mEt_p_code.getText().toString().trim())) {
            DialogUtils.showToast(this, "验证码不正确");
            return false;
        }
        if (!mCb_p_protocols.isChecked()) {
            DialogUtils.showToast(this, "请同意注册协议");
            return false;
        }
        return true;
    }


    private boolean checkCompanyRegisterData() {
        if (BeanUtils.isEmptyStr(mEt_company_name.getText().toString().trim())) {
            DialogUtils.showToast(this, "企业名称不能为空");
            return false;
        }
        if (BeanUtils.isEmptyStr(mEt_mobile_phone_number.getText().toString().trim())) {
            DialogUtils.showToast(this, "手机号码不能为空");
            return false;
        }
        if (BeanUtils.isEmptyStr(mEt_company_linkman.getText().toString().trim())) {
            DialogUtils.showToast(this, "企业联系人不能为空");
            return false;
        }
        if (BeanUtils.isEmptyStr(mEt_user_name.getText().toString().trim())) {
            DialogUtils.showToast(this, "用户名不能为空");
            return false;
        }
        if (BeanUtils.isEmptyStr(mEt_password.getText().toString().trim())) {
            DialogUtils.showToast(this, "密码不能为空");
            return false;
        }
        if (!BeanUtils.isMobileNO(mEt_mobile_phone_number.getText().toString().trim())) {
            DialogUtils.showToast(this, "手机号格式不正确");
            return false;
        }
        if (BeanUtils.isEmptyStr(mEt_code.getText().toString().trim())) {
            DialogUtils.showToast(this, "验证码不能为空");
            return false;
        }
        if (!valiCode.equals(mEt_code.getText().toString().trim())) {
            DialogUtils.showToast(this, "验证码不正确");
            return false;
        }
        if (!mCb_protocols.isChecked()) {
            DialogUtils.showToast(this, "请同意注册协议");
            return false;
        }
        return true;
    }


    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String msg = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(item.getString("code"))) {
                if (what == WHAT_PERSON_REGISTER || what == WHAT_COMPANY_REGISTER) {
                    DialogUtils.showToast(RegisterActivity.this, "注册成功");
                    UserInfo.DATABean userInfo = new UserInfo.DATABean();
                    if (what == WHAT_PERSON_REGISTER) {
                        userInfo.setUSERNAME(mEt_p_user_name.getText().toString().trim());
                        userInfo.setUSERPWD(mEt_p_password.getText().toString().trim());
                        userInfo.setUSERDUTY(what + "");
                    } else if (what == WHAT_COMPANY_REGISTER) {
                        userInfo.setUSERNAME(mEt_user_name.getText().toString().trim());
                        userInfo.setUSERPWD(mEt_password.getText().toString().trim());
                        userInfo.setUSERDUTY(what + "");
                    }
                    //显示是否进行实名认证对话框
//                    DialogUtils.createAuthDialog(RegisterActivity.this, what, userInfo);
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    close();
                } else if (what == SENDYZM){
                    valiCode = item.getString("vali_code");
                } else if (what == SENDYZM_P){
                    valiCode_p = item.getString("vali_code");
                }
            } else {
                DialogUtils.showToast(RegisterActivity.this, msg);
            }
        }
    };

    public void close(){
        finish();
    }


    /**
     * 设置倒计时
     */
    private void setTimerTask(final TextView tvTimer) {
        countDown = 60;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        countDown --;
                        if (countDown <= 0){
                            timer.cancel();//取消倒计时
                            tvTimer.setText("重新获取");
                            tvTimer.setClickable(true);//重新获得点击
                            tvTimer.setTextColor(ContextCompat.getColor(RegisterActivity.this,
                                    R.color.white));
                            tvTimer.setBackgroundResource(R.drawable.blue_corner_selector);  //还原背景色
                        } else {
                            tvTimer.setClickable(false); //设置不可点击
                            tvTimer.setTextColor(ContextCompat.getColor(RegisterActivity.this,
                                    R.color.gray));
                            tvTimer.setText("已发送(" + countDown + "s)");  //设置倒计时时间
                            tvTimer.setBackgroundResource(R.drawable.gray_bg); //设置按钮为灰色，这时是不能点击的
                        }
                    }
                });


            }
        }, 1000, 1000);//表示1000毫秒之后，每隔1000毫秒执行一次.
    }
}
