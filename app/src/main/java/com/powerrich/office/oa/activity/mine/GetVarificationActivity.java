package com.powerrich.office.oa.activity.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.LoginActivity;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.enums.RegisterType;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.view.VerifyDialog;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.LoginBean;
import com.yt.simpleframe.http.bean.UserInfoBean;
import com.yt.simpleframe.http.bean.ValiCodeBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.CountDownHelper;
import com.yt.simpleframe.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GetVarificationActivity extends YTBaseActivity {


    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_vetify_code)
    EditText mEtVetifyCode;
    @BindView(R.id.bt_verify)
    TextView mBtVerify;
    //    @BindView(R.id.tv_erro)
//    TextView mTvErro;
    @BindView(R.id.tv_next)
    TextView mTvNext;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_find_phone)
    TextView mTvFindPhone;
    @BindView(R.id.et_pwd1)
    EditText mEtPwd1;
    @BindView(R.id.et_pwd2)
    EditText mEtPwd2;
    @BindView(R.id.iv_pwd_type)
    ImageView mIvPwdType;


    private RegisterType type;
    private String userName;

    private String mPhoneNumber = "";
    private String mPwd = "";

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_get_varification_code);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        type = (RegisterType) getIntent().getExtras().get("type");
//        mTvErro.setVisibility(View.GONE);
        if (type == RegisterType.注册) {
            setTitle("注册");
            String txt = "已有账号，直接登录";
            SpannableString spannableString = new SpannableString(txt);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#11A3FA")), txt.length() - 2, txt.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvLogin.setText(spannableString);
            mTvFindPhone.setVisibility(View.GONE);
            mEtPwd1.setVisibility(View.VISIBLE);
            mEtPwd2.setVisibility(View.VISIBLE);

            mEtPwd1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String txtPwd = mEtPwd1.getText().toString().trim();
                    updatePwd(txtPwd);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        } else if (type == RegisterType.忘记密码) {
            setTitle("找回密码");
            mTvLogin.setVisibility(View.GONE);
            mIvPwdType.setVisibility(View.GONE);
            mTvFindPhone.setVisibility(View.VISIBLE);
            mEtPwd1.setVisibility(View.GONE);
            mEtPwd2.setVisibility(View.GONE);
        }

    }

    void updatePwd(String txtPwd) {
        if (!StringUtil.isEmpty(txtPwd)) {
            mIvPwdType.setVisibility(View.VISIBLE);
            int i = StringUtil.checkPwdComplexity(txtPwd);
            if (i == 1 || i == 0) {
                mIvPwdType.setBackgroundResource(R.drawable.pasw_on);
            } else if (i == 2) {
                mIvPwdType.setBackgroundResource(R.drawable.pasw_tw);
            } else if (i == 3) {
                mIvPwdType.setBackgroundResource(R.drawable.pasw_thr);
            }
        } else {
            mIvPwdType.setVisibility(View.GONE);
        }
    }


    //检查手机号码是否有效
    private void queryUser(final String phone) {
        ApiManager.getApi().queryUser(RequestBodyUtils.queryUser(phone))
                .compose(RxSchedulers.<UserInfoBean>io_main())
                .subscribe(new BaseSubscriber<UserInfoBean>() {
                    @Override
                    public void result(UserInfoBean baseBean) {

                        if (type == RegisterType.忘记密码) {
                            if (baseBean.getDATA() != null && baseBean.getDATA().getUSERID() != null) {
                                getValiCode(phone);
                                startCountDown();
                                userName = baseBean.getDATA().getUSERNAME();
                            } else {
                                ToastUtils.showMessage(mActivity, "该手机号码没有注册");
                            }
                        } else if (type == RegisterType.注册) {
                            if (baseBean.getDATA() != null &&  baseBean.getDATA().getUSERID() != null) {
                                ToastUtils.showMessage(mActivity, "该手机已经注册了");
                            } else {
                                getValiCode(phone);
                                startCountDown();
                            }
                        }
                    }
                });
    }


    private String code = "-1";

    private void getValiCode(final String phoneNumber) {
        ApiManager.getApi().getPhoneValiCode(RequestBodyUtils.getPhoneValiCodeList(phoneNumber))
                .compose(RxSchedulers.<ValiCodeBean>io_main())
                .subscribe(new BaseSubscriber<ValiCodeBean>() {
                    @Override
                    public void result(ValiCodeBean baseBean) {
                        mPhoneNumber = phoneNumber;
                        code = baseBean.getVali_code();
                    }
                });
    }


    CountDownHelper helper;

    //开始倒计时
    private void startCountDown() {
        helper = new CountDownHelper(mBtVerify, "发送验证码", "重新获取", 60, 1);
        helper.setOnFinishListener(new CountDownHelper.OnFinishListener() {
            @Override
            public void finish() {
                mBtVerify.setText("发送验证码");
            }
        });
        helper.start();
    }

//    private void closeCountDown(){
//        if (helper != null)
//            helper.stop();
//    }


    @OnClick({R.id.bt_verify, R.id.tv_next, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_verify:
                String phone = mEtPhone.getText().toString();
                if (!StringUtil.isMobileNO(phone)) {
                    ToastUtils.showMessage(mActivity, "手机号码不正确！");
                    return;
                } else {
                    queryUser(phone);
//                    getValiCode(phone);
//                    startCountDown();

                }
                break;
            case R.id.tv_next:
                phone = mEtPhone.getText().toString();
                if (!StringUtil.isMobileNO(phone)) {
                    ToastUtils.showMessage(mActivity, "手机号码不正确！");
                    return;
                }
                String inputCode = mEtVetifyCode.getText().toString();
                if (StringUtil.isEmpty(inputCode)) {
                    ToastUtils.showMessage(mActivity, "验证码不能为空！");
                    return;
                }
                if (!code.equals(inputCode)) {
                    ToastUtils.showMessage(mActivity, "验证码输入不正确！");
                    return;
                }

                if (type == RegisterType.忘记密码) {
                    Intent intent = new Intent(this, Register2Activity.class);
                    intent.putExtra("data", mPhoneNumber);
                    intent.putExtra("type", type);
                    startActivity(intent);
                } else if (type == RegisterType.注册) {
                    String pwd1 = mEtPwd1.getText().toString().trim();
                    String pwd2 = mEtPwd2.getText().toString().trim();
                    if (checkForgotData(pwd1, pwd2)) {
                        //开始注册
                        mPwd = pwd1;
                        //弹出对话框
//                        showDialog();
                        register(mPhoneNumber,mPwd);
                    }
                }


                break;
            case R.id.tv_login:
                goFinishActivity(LoginActivity.class);
                break;
        }
    }

    private boolean checkForgotData(String pwd1, String pwd2) {
        boolean boo = false;
        if (TextUtils.isEmpty(pwd1)) {
            ToastUtils.showMessage(this, "密码输入不能为空");
            return boo;
        }
        if (pwd1.length() < 6 || pwd1.length() > 16) {
            ToastUtils.showMessage(this, "密码长度为6~16位");
            return boo;
        }

        if (!pwd1.equals(pwd2)) {
            ToastUtils.showMessage(this, "两次输入密码不一致！");
//            mTvErro.setVisibility(View.VISIBLE);
            return boo;
        }

        return true;
    }


    private void register(final String phone, final String password) {
        ApiManager.getApi().register(RequestBodyUtils.register(phone, password))
                .compose(RxSchedulers.<LoginBean>io_main())
                .compose(this.<LoginBean>loadingDialog())
                .subscribe(new BaseSubscriber<LoginBean>() {
                    @Override
                    public void result(LoginBean baseBean) {
                        ToastUtils.showMessage(GetVarificationActivity.this, "注册成功！");
                        Intent intent = new Intent(GetVarificationActivity.this, ChooseIdentifyTypeActivity.class);
                        intent.putExtra("phoneNumber", mPhoneNumber);
                        intent.putExtra("userId", baseBean.getDATA().getUSERID());
                        intent.putExtra("type","login");
                        startActivity(intent);
                        finish();
                    }
                });

    }


    void showDialog() {
        final VerifyDialog dialog = new VerifyDialog(this);
        if (dialog.isShowing()) {
            return;
        }
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(final DialogInterface d, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                        finish();
                    }
                }
                return false;
            }
        });
        dialog.setCancelable(false);
        dialog.show();

        dialog.setClickListener(new VerifyDialog.ClickListenerInterface() {
            @Override
            public void cancel() {
                dialog.dismiss();
                finish();
            }

            @Override
            public void verifyNomal() {
                dialog.dismiss();
                Intent intent = new Intent(GetVarificationActivity.this, Identify3Activity.class);
                intent.putExtra("phoneNumber", mPhoneNumber);
                intent.putExtra("password", mPwd);
                intent.putExtra("type", "normal");
                startActivity(intent);
                finish();
            }

            @Override
            public void verifyEid() {
                dialog.dismiss();
                Intent intent = new Intent(GetVarificationActivity.this, Identify3Activity.class);
                intent.putExtra("phoneNumber", mPhoneNumber);
                intent.putExtra("password", mPwd);
                intent.putExtra("type", "eid");
                startActivity(intent);
                finish();
            }
        });
    }

}
