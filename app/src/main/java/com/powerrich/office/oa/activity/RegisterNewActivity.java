package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.powerrich.office.oa.AppManager;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.Interaction.IwantActivity;
import com.powerrich.office.oa.activity.Interaction.OnMultiClickListener;
import com.powerrich.office.oa.activity.mine.Identify5Activity;
import com.powerrich.office.oa.activity.mine.SettingActivity;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.RegisterBean;
import com.powerrich.office.oa.fund.Activity.RefundDetailActivity;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.EditUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.tools.network.AuthorizationUtils;
import com.powerrich.office.oa.tools.network.HttpManager;
import com.powerrich.office.oa.tools.network.entity.NetStatus;
import com.powerrich.office.oa.view.MyDialog;
import com.powerrich.office.oa.view.NoEmojiEditText;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.CountDownHelper;
import com.yt.simpleframe.utils.KeyboardUtils;
import com.yt.simpleframe.utils.StringUtil;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterNewActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rl_thename)
    RelativeLayout rlThename;
    @BindView(R.id.rl_gr_idcard)
    RelativeLayout rlGrIdcard;
    @BindView(R.id.rl_gr_effectiveStarting)
    RelativeLayout rlGrEffectiveStarting;
    @BindView(R.id.rl_gr_periodofvalidity)
    RelativeLayout rlGrPeriodofvalidity;
    @BindView(R.id.et_thename)
    NoEmojiEditText etThename;
    @BindView(R.id.et_gr_idcard)
    NoEmojiEditText etGrIdcard;

    @BindView(R.id.tv_gr_idcard)
    TextView tvGrIdcard;
    @BindView(R.id.tv_gr_effectiveStarting)
    TextView tvGrEffectiveStarting;
    @BindView(R.id.tv_gr_periodofvalidity)
    TextView tvGrPeriodofvalidity;
    @BindView(R.id.tv_gr_effectiveStarting_value)
    TextView tvGrEffectiveStartingValue;
    @BindView(R.id.tv_gr_periodofvalidity_value)
    TextView tvGrPeriodofvalidityValue;
    @BindView(R.id.tv_thename)
    TextView tvThename;

    private TimePickerView pvTime;


    private RelativeLayout mTitleLayout;
    private ImageView mSystemBack;
    private TextView mTvTopTitle, bt_vetify;
    private NoEmojiEditText mEtMaterial;
    private NoEmojiEditText mEtPwd;
    private NoEmojiEditText mEtAgainPwd;
    private NoEmojiEditText mEtEmailVital;
    private NoEmojiEditText mEtPhonenumber;
    private NoEmojiEditText mEtAuthCode;
    private NoEmojiEditText mEtAdress;
    private CheckBox mCbAgree;
    private Button mBtRegister;
    private String authCodeStr;
    private RadioGroup mRgOut;
    private RadioButton mRbOutYes;
    private RadioButton mRbOutNo;
    private String materialStr;
    private String pwdStr;
    private String emailStr;
    private String grIdCardStr;
    private TextView mTvAgreement;

    private NoEmojiEditText mEtLegalName;
    private NoEmojiEditText mEtIdcard;
    private RadioGroup mRgLegalType;
    private NoEmojiEditText mEtCompanyName;
    private NoEmojiEditText mEtCreditCode;
    private NoEmojiEditText mEtBusinessAddress;
    private RelativeLayout mRlLegalName;
    private RelativeLayout mRlIdcard;
    private RelativeLayout mRlLegalType;
    private RelativeLayout mRlCompanyName;
    private RelativeLayout mRlCreditCode;
    private RelativeLayout mRlBusinessAddress;
    private RelativeLayout mRlEmail;
    private RelativeLayout rl_adress;
    private RelativeLayout mRlOutside;
    //区分是法人注册 还是个人注册  0 个人  1 法人
    private int type;
    private String numberStr;
    private String creditCode;
    private String companyName;
    private String idCardStr;
    private String legalNameStr;
    private TextView mTvMaterialName;
    private TextView mTvPwdName;
    private TextView mTvAgainPwdName;
    private TextView mTvLegalName;
    private TextView mTvIdcardName;
    private TextView mTvLegalTypeName;
    private TextView mTvCompanyName;
    private TextView mTvCreditCodeName;
    private TextView mTvEmialName;
    private TextView mTvOutsideName;
    private TextView mTvPhonenumberName;
    private TextView mTvAuthCodeName;
    private TextView mTvAdressName;


    List<RegisterBean> options1Items = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        type = intent.getIntExtra("TYPE", 0);
        numberStr = intent.getStringExtra("PHONENUMBER");
        initTimePicker();
        initView();
    }

    private void initTimePicker() {
        //时间选择器
        pvTime = new TimePickerBuilder(RegisterNewActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                ((TextView) view).setText(DateUtils.getCustomDateStr(date));
            }
        }).build();

        options1Items.add(new RegisterBean().setName("五年"));
        options1Items.add(new RegisterBean().setName("十年"));
        options1Items.add(new RegisterBean().setName("二十年"));
        options1Items.add(new RegisterBean().setName("永久有效"));


    }

    public void logout() {
        ApiManager.getApi().exeNormal(RequestBodyUtils.logout(
                LoginUtils.getInstance().getUserInfo().getAuthtoken()))
                .compose(RxSchedulers.<BaseBean>io_main())
                .compose(this.<BaseBean>loadingDialog())
                .subscribe(new BaseSubscriber<BaseBean>() {
                    @Override
                    public void result(BaseBean listBean) {
                        LoginUtils.getInstance().logout();
                        //清除登录信息
                        LoginUtils.clearLoginInfo(RegisterNewActivity.this);
                        finish();

                        AppManager.getAppManager().finishActivity(SettingActivity.class);
//                        AppManager.getAppManager().finishActivity(MainActivity.class);
                        Intent intent = new Intent(RegisterNewActivity.this, LoginActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
    }


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register_new;
    }

    CountDownHelper helper;

    //开始倒计时
    private void startCountDown() {
        helper = new CountDownHelper(bt_vetify, "获取验证码", "重新获取", 60, 1);
        helper.setOnFinishListener(new CountDownHelper.OnFinishListener() {
            @Override
            public void finish() {
                bt_vetify.setText("获取验证码");
            }
        });
        helper.start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == 1000) {
            mCbAgree.setChecked(true);
        }
    }

    private void initView() {
        mTitleLayout = findViewById(R.id.title_layout);
        mSystemBack = findViewById(R.id.system_back);
        mTvTopTitle = findViewById(R.id.tv_top_title);
        bt_vetify = findViewById(R.id.bt_vetify);


        mSystemBack.setVisibility(View.VISIBLE);
        mSystemBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEtMaterial = findViewById(R.id.et_material);
        mEtPwd = findViewById(R.id.et_pwd);
        mEtAgainPwd = findViewById(R.id.et_again_pwd);
        mEtEmailVital = findViewById(R.id.et_email_vital);
        mEtPhonenumber = findViewById(R.id.et_phonenumber);
        mEtAuthCode = findViewById(R.id.et_auth_code);
        mEtAdress = findViewById(R.id.et_adress);
        mCbAgree = findViewById(R.id.cb_agree);
        mBtRegister = findViewById(R.id.bt_register);

        bt_vetify.setOnClickListener(this);
        mBtRegister.setOnClickListener(this);
        mRgOut = findViewById(R.id.rg_out);
        mRbOutYes = findViewById(R.id.rb_out_yes);
        mRbOutNo = findViewById(R.id.rb_out_no);
        mTvAgreement = findViewById(R.id.tv_agreement);
        //我已阅读并同意《江西政务服务实名认证用户平台注册协议》
        String str = "我已阅读并同意" + "<font color = \"#06CFFF\">" + "《江西政务服务实名认证用户平台注册协议》" + "</font>";
        mTvAgreement.setText(Html.fromHtml(str));
        mTvAgreement.setOnClickListener(new OnMultiClickListener() {
            @Override
            protected void onMultiClick(View v) {
                Intent intent = new Intent(RegisterNewActivity.this, RegisterAgreementActivity.class);
                startActivityForResult(intent, 1001);

            }
        });
        mEtLegalName = findViewById(R.id.et_legal_name);
        mEtIdcard = findViewById(R.id.et_idcard);
        mRgLegalType = findViewById(R.id.rg_legal_type);
        mEtCompanyName = findViewById(R.id.et_company_name);
        mEtCreditCode = findViewById(R.id.et_credit_code);
        mEtBusinessAddress = findViewById(R.id.et_business_address);
        mRlLegalName = findViewById(R.id.rl_legal_name);
        mRlIdcard = findViewById(R.id.rl_idcard);
        mRlLegalType = findViewById(R.id.rl_legal_type);
        mRlCompanyName = findViewById(R.id.rl_company_name);
        mRlCreditCode = findViewById(R.id.rl_credit_code);
        mRlBusinessAddress = findViewById(R.id.rl_business_address);
        mRlEmail = findViewById(R.id.rl_email);
        rl_adress = findViewById(R.id.rl_adress);
        mRlOutside = findViewById(R.id.rl_outside);

        //个人
        if (type == 0) {
            mTvTopTitle.setText("个人注册");
            mRlLegalName.setVisibility(View.GONE);
            mRlIdcard.setVisibility(View.GONE);
            mRlLegalType.setVisibility(View.GONE);
            mRlCompanyName.setVisibility(View.GONE);
            mRlCreditCode.setVisibility(View.GONE);
            mRlBusinessAddress.setVisibility(View.GONE);

            rlThename.setVisibility(View.VISIBLE);
            rlGrIdcard.setVisibility(View.VISIBLE);
            rlGrEffectiveStarting.setVisibility(View.VISIBLE);
            rlGrPeriodofvalidity.setVisibility(View.VISIBLE);


            mRlEmail.setVisibility(View.VISIBLE);
            mRlOutside.setVisibility(View.VISIBLE);


            etGrIdcard.setInputType(InputType.TYPE_CLASS_NUMBER);
            String digists = "-0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            etGrIdcard.setKeyListener(DigitsKeyListener.getInstance(digists));


        } else {
            mTvTopTitle.setText("法人注册");
            mRlLegalName.setVisibility(View.VISIBLE);
            mRlIdcard.setVisibility(View.VISIBLE);
            mRlLegalType.setVisibility(View.VISIBLE);
            mRlCompanyName.setVisibility(View.VISIBLE);
            mRlCreditCode.setVisibility(View.VISIBLE);
            mRlBusinessAddress.setVisibility(View.VISIBLE);

            if (!TextUtils.isEmpty(numberStr)) {
                mEtPhonenumber.setText(numberStr);
                //             mEtPhonenumber.setEnabled(false);
                EditUtils.isEdit(mEtPhonenumber, false);

//                mEtPhonenumber.setBackgroundColor(getResources().getColor(R.color.ddd));
                mEtPhonenumber.setBackground(getResources().getDrawable(R.drawable.shape_gray_register));
                mEtPhonenumber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showMessage(RegisterNewActivity.this, "当前手机号不可选");
                    }
                });
            }


            rlThename.setVisibility(View.GONE);
            rlGrIdcard.setVisibility(View.GONE);
            rlGrEffectiveStarting.setVisibility(View.GONE);
            rlGrPeriodofvalidity.setVisibility(View.GONE);


            mRlEmail.setVisibility(View.GONE);
            mRlOutside.setVisibility(View.GONE);
            rl_adress.setVisibility(View.GONE);
        }


        mTvMaterialName = findViewById(R.id.tv_material_name);
        mTvPwdName = findViewById(R.id.tv_pwd_name);
        mTvAgainPwdName = findViewById(R.id.tv_again_pwd_name);
        mTvLegalName = findViewById(R.id.tv_legal_name);
        mTvIdcardName = findViewById(R.id.tv_idcard_name);
        mTvLegalTypeName = findViewById(R.id.tv_legal_type_name);
        mTvCompanyName = findViewById(R.id.tv_company_name);
        mTvCreditCodeName = findViewById(R.id.tv_credit_code_name);
        mTvEmialName = findViewById(R.id.tv_emial_name);
        mTvOutsideName = findViewById(R.id.tv_outside_name);
        mTvPhonenumberName = findViewById(R.id.tv_phonenumber_name);
        mTvAuthCodeName = findViewById(R.id.tv_auth_code_name);
        mTvAdressName = findViewById(R.id.tv_adress_name);

        changeMustFillIn(tvThename);
        changeMustFillIn(tvGrIdcard);
        changeMustFillIn(tvGrEffectiveStarting);
        changeMustFillIn(tvGrPeriodofvalidity);


        changeMustFillIn(mTvMaterialName);
        changeMustFillIn(mTvPwdName);
        changeMustFillIn(mTvAgainPwdName);
        changeMustFillIn(mTvLegalName);
        changeMustFillIn(mTvIdcardName);
        changeMustFillIn(mTvCompanyName);
        changeMustFillIn(mTvCreditCodeName);
        changeMustFillIn(mTvEmialName);
        changeMustFillIn(mTvOutsideName);
        changeMustFillIn(mTvPhonenumberName);
        changeMustFillIn(mTvAuthCodeName);


    }


    private void changeMustFillIn(TextView tvName) {
        String tvNameStr = tvName.getText().toString();
        tvName.setText(Html.fromHtml(tvNameStr + "<font color = \"#ff0000\">" + "*" + "</font>"));
    }


    String phoneStr = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //注册
            case R.id.bt_register:


                materialStr = mEtMaterial.getText().toString();
                if (StringUtil.isEmpty(materialStr)) {
                    ToastUtils.showMessage(this, "用户名不能为空！");
                    return;
                }


                if (!StringUtil.checkStrContentCc(materialStr)) {
                    ToastUtils.showMessage(this, "用户名只能包含数字和字母,且以字母开头");
                    return;
                }

                if (!StringUtil.checkStrLeng(materialStr)) {
                    ToastUtils.showMessage(this, "用户名长度只能在6~18位");
                    return;
                }


                pwdStr = mEtPwd.getText().toString();
                if (StringUtil.isEmpty(pwdStr)) {
                    ToastUtils.showMessage(this, "密码不能为空！");
                    return;
                }

                String againStr = mEtAgainPwd.getText().toString();
                if (StringUtil.isEmpty(againStr)) {
                    ToastUtils.showMessage(this, "确认密码不能为空！");
                    return;
                }

                if (!pwdStr.equals(againStr)) {
                    ToastUtils.showMessage(this, "两次密码不一致！");
                    return;
                }


                //个人
                if (type == 0) {

                    emailStr = mEtEmailVital.getText().toString();


                    if (StringUtil.isEmpty(tvThename.getText().toString())) {
                        ToastUtils.showMessage(this, "身份证不能为空！");
                        return;
                    }

                    if (StringUtil.isEmpty(etGrIdcard.getText().toString())) {
                        ToastUtils.showMessage(this, "身份证不能为空！");
                        return;
                    }


                    if (!BeanUtils.validCard(etGrIdcard.getText().toString())) {
                        Toast.makeText(this, "身份证号码输入无效！", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if (StringUtil.isEmpty(tvGrEffectiveStartingValue.getText().toString())) {
                        ToastUtils.showMessage(this, "身份证有效起始不能为空！");
                        return;
                    }

                    if (StringUtil.isEmpty(tvGrPeriodofvalidityValue.getText().toString())) {
                        ToastUtils.showMessage(this, "身份证有效期不能为空！");
                        return;
                    }


                    if (StringUtil.isEmpty(emailStr)) {
                        ToastUtils.showMessage(this, "电子邮件不能为空！");
                        return;
                    }

                    if (!StringUtil.checkEmail(emailStr)) {
                        ToastUtils.showMessage(this, "电子邮件格式不正确！");
                        return;
                    }

                    //法人
                } else {

                    legalNameStr = mEtLegalName.getText().toString().trim();
                    if (StringUtil.isEmpty(legalNameStr)) {
                        ToastUtils.showMessage(this, "法人姓名不能为空！");
                        return;
                    }

                    idCardStr = mEtIdcard.getText().toString();
                    if (StringUtil.isEmpty(idCardStr)) {
                        ToastUtils.showMessage(this, "身份证号不能为空！");
                        return;
                    }

                    if (!BeanUtils.validCard(idCardStr.toUpperCase())) {
                        Toast.makeText(this, "身份证号码输入无效！", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    companyName = mEtCompanyName.getText().toString();
                    if (StringUtil.isEmpty(companyName)) {
                        ToastUtils.showMessage(this, "企业名称不能为空！");
                        return;
                    }

                    creditCode = mEtCreditCode.getText().toString();
                    if (StringUtil.isEmpty(creditCode)) {
                        ToastUtils.showMessage(this, "信用代码不能为空！");
                        return;
                    }

                }


                phoneStr = mEtPhonenumber.getText().toString();
                if (StringUtil.isEmpty(phoneStr)) {
                    ToastUtils.showMessage(this, "手机号不能为空！");
                    return;
                }

                if (!StringUtil.isMobileNO(phoneStr)) {
                    ToastUtils.showMessage(this, "手机号码格式不正确！");
                    return;
                }


                authCodeStr = mEtAuthCode.getText().toString();
                if (StringUtil.isEmpty(authCodeStr)) {
                    ToastUtils.showMessage(this, "验证码不能为空！");
                    return;
                }


                if (!mCbAgree.isChecked()) {
                    ToastUtils.showMessage(this, "请确定已同意协议");
                    return;
                }


                Log.i("jsc", "onClick: " + mRgOut.getCheckedRadioButtonId() + "-" + R.id.cb_yes + "-no:" + R.id.cb_no);


                //----------------------  检验用户名是否存在  ----------------------
                Map map1 = new HashMap();
                map1.put("username", materialStr.trim());
                map1.put("usertype", type);
                map1.put("client_id", HttpManager.client_id);
                HttpManager.postNetWork(Constants.USEREXIST_URL, map1, new HttpManager.INetLister() {
                    @Override
                    public void onSuccess(NetStatus netStatus) {


                        if (netStatus.getData() instanceof Boolean) {
                            boolean isExist = (boolean) netStatus.getData();

                            if (isExist) {
                                //为空时抖动提示
                                mEtMaterial.setShakeAnimation();
                                ToastUtils.showMessage(RegisterNewActivity.this, "当前用户名已注册");
                                return;
                            }
                        }


                        //----------------------  检验手机验证码  ----------------------
                        Map map2 = new HashMap();
                        map2.put("phonenumber", phoneStr);
                        map2.put("code", authCodeStr);
                        map2.put("client_id", HttpManager.client_id);
                        HttpManager.postNetWork(Constants.VERIFY_CODE_URL, map2, new HttpManager.INetLister() {
                            @Override
                            public void onSuccess(NetStatus netStatus) {
                                if (netStatus.getData() instanceof Boolean) {
                                    boolean flag = (boolean) netStatus.getData();
                                    //验证码错误时
                                    if (!flag) {
                                        ToastUtils.showMessage(RegisterNewActivity.this, "验证码" + netStatus.getMsg());
                                        return;
                                    }
                                }


                                // 获取token
                                AuthorizationUtils.getAccessToken(new AuthorizationUtils.IGetCode() {
                                    @Override
                                    public void getCode(String code) {

                                        //----------------------  验证通过 开始注册  ----------------------
                                        //企业类型 0:企业 1：社会团体 2：事业单位；3：行政机关
                                        String qyType = "0";
                                        if (mRgLegalType.getCheckedRadioButtonId() == R.id.rb_legal_qy) {
                                            qyType = "0";
                                        } else if (mRgLegalType.getCheckedRadioButtonId() == R.id.rb_legal_shtt) {
                                            qyType = "1";
                                        } else if (mRgLegalType.getCheckedRadioButtonId() == R.id.rb_legal_sydw) {
                                            qyType = "2";
                                        } else if (mRgLegalType.getCheckedRadioButtonId() == R.id.rb_legal_zzjg) {
                                            qyType = "3";
                                        }
                                        Map mapR = new HashMap();
                                        mapR.put("username", materialStr);//用户名
                                        mapR.put("password", pwdStr.trim());//密码
                                        mapR.put("phoneNumber", phoneStr.trim());//手机号码

                                        //  mapR.put("realname", HttpManager.client_id);//真实姓名
                                        //个人
                                        if (type == 0) {
                                            mapR.put("address", toURLEncoded(mEtAdress.getText().toString())); //地址
                                            mapR.put("email", emailStr); //邮箱
                                            mapR.put("sfswry", mRgOut.getCheckedRadioButtonId() == R.id.cb_no ? "0" : "1");   //是否是省外 1:为省外人员，0:为省内人

                                            mapR.put("sjly", "1"); // 数据来源 固定传1
                                            mapR.put("status", "1"); // 用户状态 启用禁用
                                            mapR.put("realname", toURLEncoded(etThename.getText().toString())); // 姓名
                                            mapR.put("idcard", etGrIdcard.getText().toString()); // 身份证
                                            String effDateStr = tvGrEffectiveStartingValue.getText().toString();
                                            mapR.put("certEffDate", effDateStr.replaceAll("/", "")); //身份证有效起始
                                            mapR.put("certExpDate", getHaveADeadline(effDateStr, tvGrPeriodofvalidityValue.getText().toString())); //身份证有效期

                                        } else {
                                            mapR.put("fr_name", toURLEncoded(legalNameStr.trim())); //法人姓名
                                            mapR.put("fr_idcard", idCardStr.toUpperCase()); //身份证号
                                            mapR.put("grinfoId", LoginUtils.getInstance().getUserInfo().getDATA().getUSERID()); // 法人id
                                            mapR.put("qy_name", toURLEncoded(companyName.trim())); //企业名称
                                            mapR.put("qy_number", creditCode.trim()); //信用代码
                                            mapR.put("qy_type", qyType); //法人类型
                                            mapR.put("qy_address", toURLEncoded(mEtBusinessAddress.getText().toString().trim())); //企业地址
                                        }
                                        Map mapR2 = new HashMap();
                                        mapR2.put("accesstoken", code);
                                        mapR2.put("usertype", type);
                                        mapR2.put("userInfo", new JSONObject(mapR).toString());

                                        //注册
                                        HttpManager.postNetWork(Constants.REGISTER_URL, mapR2, new HttpManager.INetLister() {
                                            @Override
                                            public void onSuccess(NetStatus netStatus) {
                                                if (type == 0) {
                                                    ToastUtils.showMessage(RegisterNewActivity.this, "注册成功");
                                                    Intent intent = new Intent(RegisterNewActivity.this, LoginActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
//                                                    MyDialog.showDialogButtonStr(RegisterNewActivity.this, "注册成功", "是否进行实名认证?", "跳过，直接登录", "去认证", new MyDialog.InterfaceClickCc() {
//                                                        @Override
//                                                        public void onRightClick() {
//                                                            finish();
//                                                        }
//
//                                                        @Override
//                                                        public void onLeftClick() {
//
//                                                            Intent intent = new Intent(RegisterNewActivity.this, Identify5Activity.class);
////                        intent.putExtra("phoneNumber", LoginUtils.getInstance().getUserInfo().getDATA().getMOBILE_NUM());
////                        intent.putExtra("userId",  userid);
//                                                            intent.putExtra("userName", mEtMaterial.getText().toString());
//                                                            intent.putExtra("password", pwdStr);
//                                                            startActivity(intent);
//                                                            finish();
//
//                                                        }
//
//                                                    });

                                                } else {
                                                    MyDialog.showDialogCusColor(RegisterNewActivity.this, "注册成功", "你提交的企业信息已通过验证。", "去登录", getResources().getColor(R.color.actionsheet_blue), "取消", getResources().getColor(R.color.cancal2), new MyDialog.InterfaceClickCc() {
                                                        @Override
                                                        public void onRightClick() {
                                                            logout();
                                                        }

                                                        @Override
                                                        public void onLeftClick() {
                                                            finish();
                                                        }

                                                    });

                                                }


                                            }

                                            @Override
                                            public void onError(String error) {

                                                ToastUtils.showMessage(RegisterNewActivity.this, "" + error);
                                            }
                                        });

                                    }
                                });


                            }

                            @Override
                            public void onError(String error) {
                                ToastUtils.showMessage(RegisterNewActivity.this, error);
                            }
                        });

                    }

                    @Override
                    public void onError(String error) {
                        ToastUtils.showMessage(RegisterNewActivity.this, error);
                    }
                });


                break;
            //发送验证码
            case R.id.bt_vetify:

//
//                new Thread(){
//                    @Override
//                    public void run() {
//                        Demo.registerUser();
//                    }
//                }.start();


                phoneStr = mEtPhonenumber.getText().toString();
                if (StringUtil.isEmpty(phoneStr)) {
                    ToastUtils.showMessage(this, "手机号不能为空！");
                    return;
                }

                if (!StringUtil.isMobileNO(phoneStr)) {
                    ToastUtils.showMessage(this, "手机号码格式不正确！");
                    return;
                }


                //C.10　获取手机号是否存在接口
                Map map2 = new HashMap();
                map2.put("username", phoneStr);
                map2.put("usertype", type);
                map2.put("client_id", HttpManager.client_id);
                HttpManager.postNetWork(Constants.USEREXIST_URL, map2, new HttpManager.INetLister() {
                    @Override
                    public void onSuccess(NetStatus netStatus) {
                        Log.i("jsc", "获取用户是否存在接口-onSuccess: " + netStatus.toString());

                        if (netStatus.getData() instanceof Boolean) {
                            boolean isExist = (boolean) netStatus.getData();

                            if (!isExist) {
                                Map map = new HashMap();
                                map.put("phonenumber", phoneStr);
                                map.put("client_id", HttpManager.client_id);
                                HttpManager.postNetWork(Constants.SEND_CODE_URL, map, new HttpManager.INetLister() {
                                    @Override
                                    public void onSuccess(NetStatus netStatus) {
                                        startCountDown();
                                        Log.i("jsc", "onSuccess: " + netStatus.toString());
                                        ToastUtils.showMessage(RegisterNewActivity.this, "发送成功！");
                                    }

                                    @Override
                                    public void onError(String error) {
                                        Log.i("jsc", "onError: " + error);
                                        ToastUtils.showMessage(RegisterNewActivity.this, error);
                                    }
                                });

                            } else {
                                //为空时抖动提示
                                mEtPhonenumber.setShakeAnimation();
                                ToastUtils.showMessage(RegisterNewActivity.this, "当前手机号已注册");
                            }

                        } else {
                            ToastUtils.showMessage(RegisterNewActivity.this, "服务器返回有误");
                        }


                    }

                    @Override
                    public void onError(String error) {
                        Log.i("jsc", "onError: " + error);
                        ToastUtils.showMessage(RegisterNewActivity.this, error);
                    }
                });


                break;
        }
    }


    public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {

            return "";
        }

        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        } catch (Exception localException) {
            localException.printStackTrace();
        }

        return "";
    }


    private void testNetWork(String url, Map<String, Object> reqBody) {

        JSONObject jsonObject = new JSONObject(reqBody);
        JSONObject obj = new JSONObject();
        obj.put("params", jsonObject.toString());
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("params", jsonObject.toString());
        RequestBody requestBody = setRequestBody(stringStringHashMap);


        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();

            int statusCode = response.code();
            String string = response.body().string();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mEtMaterial.setText("报错");

                    Toast.makeText(RegisterNewActivity.this, "haha", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * post的请求参数，构造RequestBody
     *
     * @param BodyParams
     * @return
     */
    private RequestBody setRequestBody(Map<String, String> BodyParams) {
        RequestBody body = null;
        FormBody.Builder formEncodingBuilder = new FormBody.Builder();

        if (BodyParams != null) {
            Iterator<String> iterator = BodyParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                formEncodingBuilder.add(key, BodyParams.get(key));
                Log.d("post http", "post_Params===" + key + "====" + BodyParams.get(key));
            }
        }
        body = formEncodingBuilder.build();
        return body;

    }


    private OkHttpClient client;


    private void initOkHttp() {

        if (null == client) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build();
        }

    }


    /**
     * 获取最终有效期
     *
     * @param dateStr  当前时间
     * @param deadline 有效期 五、十、二十年
     * @return
     */
    private String getHaveADeadline(String dateStr, String deadline) {

        String inTheEndDate;
        int year;


        int i = -1;

        for (int j = 0; j < options1Items.size(); j++) {
            if (deadline.equals(options1Items.get(j).getName())) {
                i = j;
            }
        }

        switch (i) {
            //五年
            case 0:
                year = 5;
                break;
            //十年
            case 1:
                year = 10;
                break;
            //二十年
            case 2:
                year = 20;
                break;
            default:
                inTheEndDate = "00000000";
                return inTheEndDate;

        }

        inTheEndDate = DateUtils.getYearAgo(DateUtils.parseDate(dateStr, "yyyy/MM/dd"), year, "yyyy/MM/dd");
        return inTheEndDate.replaceAll("/", "").trim();
    }


    @OnClick({R.id.rl_gr_effectiveStarting, R.id.rl_gr_periodofvalidity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_gr_effectiveStarting:


                KeyboardUtils.hideSuperKeyBord(this);


                String monthAgo;
                monthAgo = BeanUtils.isEmpty(tvGrEffectiveStartingValue.getText().toString().trim()) ? DateUtils.getMonthAgo(new Date(), "yyyy/MM/dd") : tvGrEffectiveStartingValue.getText().toString().trim();
                pvTime.setDate(DateUtils.dataToCalendar(DateUtils.parseDate(monthAgo, "yyyy/MM/dd")));
                pvTime.show(tvGrEffectiveStartingValue);
                break;


            case R.id.rl_gr_periodofvalidity:

                KeyboardUtils.hideSuperKeyBord(this);
                OptionsPickerView pvOptions = new OptionsPickerBuilder(RegisterNewActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1).getPickerViewText();
                        tvGrPeriodofvalidityValue.setText(tx);

                    }
                }).build();
                pvOptions.setPicker(options1Items);

                int index = 0;
                for (int i = 0; i < options1Items.size(); i++) {
                    if (options1Items.get(i).getName().equals(tvGrPeriodofvalidityValue.getText())) {
                        index = i;
                        break;
                    }
                }

                pvOptions.setSelectOptions(index);


                pvOptions.show();


                break;
        }
    }
}
