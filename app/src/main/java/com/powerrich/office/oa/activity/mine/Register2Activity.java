package com.powerrich.office.oa.activity.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.powerrich.office.oa.AppManager;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.LoginActivity;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.enums.RegisterType;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.view.MyDialog;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.bean.LoginBean;
import com.yt.simpleframe.http.bean.UserInfoBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Register2Activity extends YTBaseActivity {

    @BindView(R.id.lt_group)
    LinearLayout mltGroup;
    @BindView(R.id.iv_l1)
    ImageView mIvL1;
    @BindView(R.id.iv_l2)
    ImageView mIvL2;

    @BindView(R.id.tv_username)
    TextView mTvUsername;

    @BindView(R.id.rb_company)
    RadioButton mRbCompany;
    @BindView(R.id.rb_person)
    RadioButton mRbPerson;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_pwd1)
    EditText mEtPwd1;
    @BindView(R.id.et_pwd2)
    EditText mEtPwd2;
    @BindView(R.id.tv_erro)
    TextView mTvErro;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.tv_login)
    TextView mTvLogin;

    String phoneNubmer = "";
    String userName = "";
    private RegisterType type;
    private int registerState = 0;

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_register2);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        phoneNubmer = getIntent().getExtras().getString("data");
        type = (RegisterType) getIntent().getExtras().get("type");
        if (type == RegisterType.注册) {
            mTvUsername.setVisibility(View.GONE);
            setTitle("注册");
            String txt = "已有账号，直接登录";
            SpannableString spannableString = new SpannableString(txt);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#11A3FA")), txt.length()-2, txt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvLogin.setText(spannableString);
        } else if (type == RegisterType.忘记密码) {
            setTitle("找回密码");
            mTvUsername.setVisibility(View.VISIBLE);
            mltGroup.setVisibility(View.GONE);
            mIvL1.setVisibility(View.GONE);
            mIvL2.setVisibility(View.GONE);
            mTvLogin.setVisibility(View.GONE);
            mEtName.setVisibility(View.GONE);
//            userName = getIntent().getExtras().getString("userName");
            mTvUsername.setText(StringUtil.replacePhone(phoneNubmer));
            mTvRegister.setText("确定");
        }
        mTvErro.setVisibility(View.GONE);
        queryUser(phoneNubmer);

    }

    @OnClick({R.id.rb_company, R.id.rb_person, R.id.tv_register, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_company:
                break;
            case R.id.rb_person:
                break;
            case R.id.tv_register:
                if (type == RegisterType.注册) {
                    String name = mEtName.getText().toString();
                    String pwd1 = mEtPwd1.getText().toString();
                    String pwd2 = mEtPwd2.getText().toString();
                    String type = "0";
                    if (mRbPerson.isChecked())
                        type = "0";
                    if (mRbCompany.isChecked())
                        type = "1";
                    if (checkData(name, pwd1, pwd2)) {
                        register(name, pwd1, type);
                    }
                } else if (type == RegisterType.忘记密码) {
                    //调用找密码接口
                    String pwd1 = mEtPwd1.getText().toString();
                    String pwd2 = mEtPwd2.getText().toString();
                    if(checkForgotData(pwd1,pwd2) && !(TextUtils.isEmpty(userName))){
                        resetPassword(userName, mEtPwd1.getText().toString());
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
        if( pwd1.length() < 6 || pwd1.length() > 16){
            ToastUtils.showMessage(this, "密码长度为6~16位");
            return boo;
        }

        if (!pwd1.equals(pwd2)) {
            ToastUtils.showMessage(this, "两次输入密码不一致！");
            mTvErro.setVisibility(View.VISIBLE);
            return boo;
        }

        return true;
    }

    private boolean checkData(String name, String pwd1, String pwd2) {
        boolean boo = false;


        if (!mRbCompany.isChecked() && !mRbPerson.isChecked()) {
            ToastUtils.showMessage(this, "请选择注册类型");
            return boo;
        }

        if(mRbPerson.isChecked()){
            registerState = 1;
        }
        if(mRbCompany.isChecked()){
            registerState = 2;
        }

        if (TextUtils.isEmpty(name)) {
            ToastUtils.showMessage(this, "名称不能为空");
            return boo;
        }

        if(name.length()<6 || name.length() > 18){
            ToastUtils.showMessage(this, "用户名长度为6~18位");
            return boo;
        }
        if(!StringUtil.checkUserName(name)){
            ToastUtils.showMessage(this, "用户名只能包含英文字母、数字");
            return boo;
        }


        if (TextUtils.isEmpty(pwd1)) {
            ToastUtils.showMessage(this, "密码输入不能为空");
            return boo;
        }

        if( pwd1.length() < 6 || pwd1.length() > 16){
            ToastUtils.showMessage(this, "密码长度为6~16位");
            return boo;
        }


        if (!pwd1.equals(pwd2)) {
            ToastUtils.showMessage(this, "两次输入密码不一致！");
            mTvErro.setVisibility(View.VISIBLE);
            return boo;
        }
        return true;
    }

    MyDialog dialog;

    private void register(final String name, final String pwd, final String type) {
//        ApiManager.getApi().register(RequestBodyUtils.register(name, pwd, phoneNubmer, type))
//                .compose(RxSchedulers.<BaseBean>io_main())
//                .subscribe(new BaseSubscriber<BaseBean>() {
//                    @Override
//                    public void result(BaseBean baseBean) {
//                        login(name,pwd);
//
////
//                    }
//                });

    }

    //注册完成后  悄悄登录
    public void login(final String userName,String pwd){
        ApiManager.getApi().login(RequestBodyUtils.login("0",userName, pwd))
                .compose(RxSchedulers.<LoginBean>io_main())
                .subscribe(new BaseSubscriber<LoginBean>() {
                    @Override
                    public void result(LoginBean baseBean) {
                        //将消息保存起来
                        ToastUtils.showMessage(Register2Activity.this, "注册成功");
                        UserInfo user = LoginUtils.getInstance().getUserInfo();
                        user.setAuthtoken(baseBean.getDATA().getAUTHTOKEN());
                        LoginUtils.getInstance().setUserInfo(user);
                        UserInfo.DATABean bean = new UserInfo.DATABean();
                        bean.setUSERNAME(userName);
                        bean.setMOBILE_NUM(phoneNubmer);
                        LoginUtils.getInstance().getUserInfo().setDATA(bean);

//                        if(registerState == 1){
//                            //跳转到个人实名认证
//                            goFinishActivity(ChooseIdentifyTypeActivity.class);
//                        }else if(registerState ==2 ){
//                            goFinishActivity(IdentifyCompanyActivity.class);
//                        }
                        AppManager.getAppManager().finishActivity(GetVarificationActivity.class);
                    }
                });
    }


    private void resetPassword(String phone, String newPwd) {
        ApiManager.getApi().exeNormal(RequestBodyUtils.resetPwd(phone, newPwd))
                .compose(RxSchedulers.<BaseBean>io_main())
                .subscribe(new BaseSubscriber<BaseBean>() {
                    @Override
                    public void result(BaseBean baseBean) {
                        ToastUtils.showMessage(Register2Activity.this, "密码修改成功！");
                        AppManager.getAppManager().finishActivity(GetVarificationActivity.class);
                        finish();
                    }
                });
    }

    //检查手机号码是否有效
    private void queryUser(final String phone) {
        ApiManager.getApi().queryUser(RequestBodyUtils.queryUser(phone))
                .compose(RxSchedulers.<UserInfoBean>io_main())
                .subscribe(new BaseSubscriber<UserInfoBean>() {
                    @Override
                    public void result(UserInfoBean baseBean) {
                        if (!StringUtil.isEmpty(baseBean.getDATA().getUSERNAME())) {
                            userName = baseBean.getDATA().getUSERNAME();
//                            mTvUsername.setText("当前用户名："+userName);
                        }
                    }
                });
    }
}
