package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
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
import com.powerrich.office.oa.tools.GetTokenUtils;
import com.yt.simpleframe.utils.StringUtil;

/**
 * 文件名：ReSetPasswordActivity
 * 描述：重新设置密码
 * 作者：chenhao
 * 时间：2018/1/18 0018
 * 版权：
 */

public class ReSetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private static final int GETLOGINUERINFO = 1;
    private static final int RESETPASSWORD = 2;
    private Button bt_confirm;
    private String userType;
    private String phoneNum;
    private EditText et_psd;
    private EditText et_confirm_psd;
    private ImageView iv_pwd_type;
    private String mToken;
    private String userName;
    private String confirmPsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userType = getIntent().getStringExtra("userType");
        phoneNum = getIntent().getStringExtra("phoneNum");
        userName = getIntent().getStringExtra("userName");
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_reset_password;
    }


    private void initView() {
        initTitleBar("重置密码", this, null);
        if (BeanUtils.isEmptyStr(userType))  return;
        TextView tv_phone = findViewById(R.id.tv_phone);
        if ("0".equals(userType)) {
            if (!BeanUtils.isEmptyStr(phoneNum) && phoneNum.length() == 11) {
                String maskNumber = phoneNum.substring(0, 3) + "****" + phoneNum.substring(7, phoneNum.length());
                tv_phone.setVisibility(View.VISIBLE);
                tv_phone.setText(maskNumber);
            } else {
                tv_phone.setVisibility(View.GONE);
            }

        } else if ("1".equals(userType)) {

            tv_phone.setVisibility(View.GONE);
        }
        et_psd = findViewById(R.id.et_psd);
        et_confirm_psd = findViewById(R.id.et_confirm_psd);
        iv_pwd_type = findViewById(R.id.iv_pwd_type);
        bt_confirm = findViewById(R.id.bt_confirm);

        bt_confirm.setOnClickListener(this);
        et_psd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String txtPwd = et_psd.getText().toString().trim();
                updatePwd(txtPwd);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        getToken();
    }

    /**
     * 获取token
     */
    private void getToken() {
        GetTokenUtils.getToken(this, new GetTokenUtils.ICallback() {
            @Override
            public void callback(String token) {
                mToken = token;
            }
        });
    }

    private void updatePwd(String txtPwd) {
        if (!StringUtil.isEmpty(txtPwd)) {
            iv_pwd_type.setVisibility(View.VISIBLE);
            int i = StringUtil.checkPwdComplexity(txtPwd);
            if (i == 1 || i == 0) {
                iv_pwd_type.setBackgroundResource(R.drawable.pasw_on);
            } else if (i == 2) {
                iv_pwd_type.setBackgroundResource(R.drawable.pasw_tw);
            } else if (i == 3) {
                iv_pwd_type.setBackgroundResource(R.drawable.pasw_thr);
            }
        } else {
            iv_pwd_type.setVisibility(View.GONE);
        }
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            if (what == RESETPASSWORD) {
                String message = item.getString("msg");
                if (Constants.CODE.equals(code)) {
                    Boolean success = item.getBoolean("success", false);
                    if (success) {
                        DialogUtils.showToast(ReSetPasswordActivity.this, "密码重置成功");
                        toLogin();
                    }
                } else {
                    DialogUtils.showToast(ReSetPasswordActivity.this, message);
                }
            } else if (what == GETLOGINUERINFO) {
                String message = item.getString("message");
                if (Constants.SUCCESS_CODE.equals(code)) {
                    String jsonStr = item.getJsonStr();
                    Gson gson = new Gson();
                    UserInfo userInfo = gson.fromJson(jsonStr, UserInfo.class);
                    if (!BeanUtils.isEmpty(userInfo)) {
                        invoke.invokeWidthDialog(OAInterface.resetPassword(mToken, userType, confirmPsd,
                                userInfo.getDATA().getUSERID()), callBack, RESETPASSWORD);
                    }
                } else {
                    DialogUtils.showToast(ReSetPasswordActivity.this, message);
                }
            }
        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.bt_confirm:
                String psd = et_psd.getText().toString().trim();
                confirmPsd = et_confirm_psd.getText().toString().trim();
                if (BeanUtils.isEmptyStr(psd) || psd.length() < 6) {
                    DialogUtils.showToast(ReSetPasswordActivity.this, "密码长度不能少于6位");
                    return;
                }
                if (!psd.equals(confirmPsd)) {
                    DialogUtils.showToast(ReSetPasswordActivity.this, getString(R.string.two_pwd_unlike));
                    return;
                }
                if (BeanUtils.isEmptyStr(mToken)) {
                    DialogUtils.showToast(this, "网络不给力，请稍后再试！");
                    return;
                }
                //重置用户密码
                if ("0".equals(userType)) {
                    invoke.invokeWidthDialog(OAInterface.resetPassword(mToken, userType, confirmPsd, phoneNum), callBack, RESETPASSWORD);
                } else if ("1".equals(userType)) {
                    //根据用户名查找人员信息接口
                    invoke.invokeWidthDialog(OAInterface.getUserByIdInfo(userName), callBack, GETLOGINUERINFO);
                }
                break;
        }
    }

    /**
     * 转跳到登录界面
     */
    private void toLogin() {
        Intent intent = new Intent(ReSetPasswordActivity.this, LoginActivity.class);
//        intent.putExtra("userName", userName);
//        intent.putExtra("psd", et_new_pwd.getText().toString().trim());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        ReSetPasswordActivity.this.finish();
    }


}
