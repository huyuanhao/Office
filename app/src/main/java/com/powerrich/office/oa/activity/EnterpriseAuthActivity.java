package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;

/**
 * @author MingPeng
 * @date 2018/1/9
 * 企业实名认证界面
 */

public class EnterpriseAuthActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 是否为注册界面跳转过来
     */
    private boolean isFromRegister;
    private String registerUserName;
    private String registerUserduty;
    private EditText mSocialCreditCodeEt;
    private EditText mLegalNameEt;
    private EditText mLegalIdNumEt;
    private EditText mLegalPhoneEt;
    private EditText mEnterpriseAddressEt;
    private String socialCreditCode;
    private String legalName;
    private String legalIdNum;
    private String legalPhone;
    private String enterpriseAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getUserInfo();

        initTitleBar(R.string.auth, this, null);

        initView();

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_enterprise_auth;
    }

    private void initView() {
        mSocialCreditCodeEt = ((EditText) findViewById(R.id.social_credit_code));
        mLegalNameEt = ((EditText) findViewById(R.id.legal_person_name));
        mLegalIdNumEt = ((EditText) findViewById(R.id.legal_person_id_card_num));
        mLegalPhoneEt = ((EditText) findViewById(R.id.legal_person_phone));
        mEnterpriseAddressEt = ((EditText) findViewById(R.id.enterprise_address));
        findViewById(R.id.submit).setOnClickListener(this);
    }

    /**
     * 获取用户相关信息
     */
    private void getUserInfo() {
        Intent intent = getIntent();
        isFromRegister = intent.getBooleanExtra("isFromRegister", false);
        UserInfo.DATABean userInfo = (UserInfo.DATABean) intent.getSerializableExtra("userInfo");
        LoginUtils loginUtils = LoginUtils.getInstance();
        boolean loginSuccess = loginUtils.isLoginSuccess();

        registerUserName = loginSuccess ? loginUtils.getUserInfo().getDATA().getUSERNAME() : userInfo.getUSERNAME();
        registerUserduty = loginSuccess ? loginUtils.getUserInfo().getDATA().getUSERDUTY() : userInfo.getUSERDUTY();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.submit:
                if (canSubmit()) {
                    saveUserInfo();
                }
                break;
            default:
                break;
        }
    }

    private boolean canSubmit() {
        socialCreditCode = mSocialCreditCodeEt.getEditableText().toString().trim();
        if (BeanUtils.isEmptyStr(socialCreditCode)) {
            Toast.makeText(this, "社会信用代码输入无效！", Toast.LENGTH_SHORT).show();
            return false;
        }
        legalName = mLegalNameEt.getEditableText().toString().trim();
        if (BeanUtils.isEmptyStr(legalName)) {
            Toast.makeText(this, "法人姓名输入无效！", Toast.LENGTH_SHORT).show();
            return false;
        }
        legalIdNum = mLegalIdNumEt.getEditableText().toString().trim();
        if (!BeanUtils.validCard(legalIdNum)) {
            Toast.makeText(this, "法人身份证号码输入无效！", Toast.LENGTH_SHORT).show();
            return false;
        }
        legalPhone = mLegalPhoneEt.getEditableText().toString().trim();
        if (BeanUtils.isEmptyStr(legalPhone) || !BeanUtils.isMobileNO(legalPhone)) {
            Toast.makeText(this, "法人手机号码输入无效！", Toast.LENGTH_SHORT).show();
            return false;
        }
        enterpriseAddress = mEnterpriseAddressEt.getEditableText().toString().trim();
        if (BeanUtils.isEmptyStr(enterpriseAddress)) {
            Toast.makeText(this, "企业地址输入无效！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void saveUserInfo() {
        invoke.invokeWidthDialog(OAInterface.saveUserInfo(registerUserName, registerUserduty, socialCreditCode, legalName, legalIdNum, legalPhone, enterpriseAddress), callBack);
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String message = item.getString("message");
            String code = item.getString("code");
            DialogUtils.showToast(EnterpriseAuthActivity.this, message);

            if (Constants.SUCCESS_CODE.equals(code)) {
                if (isFromRegister) {
                    startActivity(new Intent(context, LoginActivity.class));
                } else {
                    LoginUtils.getInstance().getUserInfo().getDATA().setAUDIT_STATE("0");
                    LoginUtils.getInstance().getUserInfo().getDATA().setREALNAME(legalName);
                }
                EnterpriseAuthActivity.this.finish();
            }
        }
    };
}
