package com.powerrich.office.oa.activity.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.LoginActivity;
import com.powerrich.office.oa.activity.ModifyNewEmailActivity;
import com.powerrich.office.oa.activity.ModifyNewPhoneNumberActivity;
import com.powerrich.office.oa.activity.RegisterNewActivity;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.VerificationUtils;
import com.powerrich.office.oa.view.MyDialog;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.StringUtil;
import com.yt.simpleframe.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/11
 * 版权：
 */

public class SettingActivity extends YTBaseActivity {


    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.civ_img)
    CircleImageView mCivImg;
    @BindView(R.id.rlt_img)
    RelativeLayout mRltImg;
    @BindView(R.id.tv_email)
    TextView mTvEmail;
    @BindView(R.id.rlt_name)
    RelativeLayout mRltName;
    @BindView(R.id.rl_register_legal)
    RelativeLayout rl_register_legal;
    @BindView(R.id.rlt_email)
    RelativeLayout mRltEmail;
    @BindView(R.id.rlt_modify_pwd)
    RelativeLayout mRltModifyPwd;
    @BindView(R.id.tv_phone_number)
    TextView mTvPhoneNumber;
    @BindView(R.id.rlt_phonenumber)
    RelativeLayout mRltPhonenumber;
    @BindView(R.id.rlt_address)
    RelativeLayout mRltAddress;

    @BindView(R.id.rlt_about)
    RelativeLayout mRltAbout;

    @BindView(R.id.tv_logout)
    TextView mTvLogout;
    @BindView(R.id.iv_next)
    ImageView mIvNext;

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_setting);
//        mCivImg = (CircleImageView) view.findViewById(R.id.iv_img);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("设置");
        showBack();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserInfo user = LoginUtils.getInstance().getUserInfo();
        if (LoginUtils.getInstance().isLoginSuccess()) {
            initView(user);
        } else {
            goFinishActivity(LoginActivity.class);
        }
    }


    private void initView(UserInfo userInfo) {
        if (userInfo == null)
            return;
        UserInfo.DATABean info = userInfo.getDATA();
        String sf = info.getSFSMRZ();
        mTvName.setText(info.getUSERNAME());
        mRltName.setEnabled(false);
        mIvNext.setVisibility(View.GONE);

        if ("1".equals(sf)) {
            mTvName.setText(info.getUSERNAME() + " (已认证)");
            mRltName.setEnabled(false);
        } else {
//            if("0".equals(info.getAUDIT_STATE())){
//                mTvName.setText(info.getUSERNAME()+" (认证中)");
//                mRltName.setEnabled(false);
//            }else{
            mTvName.setText(info.getUSERNAME() + " (未认证)");
            mRltName.setEnabled(true);
            mIvNext.setVisibility(View.VISIBLE);
//            }
        }

        mTvEmail.setText(info.getEMAIL());
        mTvPhoneNumber.setText(info.getMOBILE_NUM());

        //当前用户为个人
        if ("0".equals(info.getUSERDUTY())) {
            //已实名
            if (VerificationUtils.isAuthenticationCc(this)) {
                rl_register_legal.setVisibility(View.VISIBLE);
            }
        } else if ("1".equals(info.getUSERDUTY())) {
            mRltPhonenumber.setVisibility(View.GONE);
            mRltEmail.setVisibility(View.GONE);
        }


    }

    @OnClick({R.id.rlt_name, R.id.rl_register_legal, R.id.rlt_img, R.id.rlt_email, R.id.rlt_modify_pwd, R.id.rlt_phonenumber, R.id.rlt_address, R.id
            .tv_logout, R.id.rlt_about})
    public void onViewClicked(View view) {
        if (BaseActivity.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.rlt_name:
                Intent intent = new Intent(this, Identify5Activity.class);
//                intent.putExtra("phoneNumber", LoginUtils.getInstance().getUserInfo().getDATA().getMOBILE_NUM());
//                intent.putExtra("userId", LoginUtils.getInstance().getUserInfo().getDATA().getUSERID());
                startActivity(intent);
//                String type = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();
//                if("0".equals(type)){
//                    goActivity(ChooseIdentifyTypeActivity.class);
//                }else{
//                    goActivity(IdentifyCompanyActivity.class);
//                }

                break;
            case R.id.rlt_img:
                goActivity(ChooseHeadImgActivity.class);
                break;
            case R.id.rlt_email:
//                goActivity(ModifyEmailActivity.class);
                goActivity(ModifyNewEmailActivity.class);
                break;
            case R.id.rlt_modify_pwd:
                goActivity(ModifyPwdActivity.class);
                break;
            case R.id.rlt_phonenumber:
//                goActivity(ModifyPhoneNumberActivity.class);
                goActivity(ModifyNewPhoneNumberActivity.class);
                break;
            case R.id.rlt_address:
                goActivity(AddressManagerListActivity.class);
                break;
            case R.id.tv_logout:
                new MyDialog(this).builder().setTitle("提示")
                        .setMessage("是否要退出登录")
                        .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                logout();
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                break;
            case R.id.rlt_about:
                goActivity(AboutActivity.class);
                break;

            case R.id.rl_register_legal:
                UserInfo.DATABean data = LoginUtils.getInstance().getUserInfo().getDATA();
                Intent intent_register = new Intent(this, RegisterNewActivity.class);
                intent_register.putExtra("TYPE", 1);

                intent_register.putExtra("PHONENUMBER", TextUtils.isEmpty(data.getMOBILE_NUM())?"":data.getMOBILE_NUM());
                startActivity(intent_register);
                break;
        }
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
                        LoginUtils.clearLoginInfo(SettingActivity.this);
                        finish();
                    }
                });
    }


}
