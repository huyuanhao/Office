package com.powerrich.office.oa.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.AESUtil;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.GetTokenUtils;
import com.powerrich.office.oa.tools.GsonUtil;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.view.LoadingDialog;
import com.yt.simpleframe.utils.CountDownHelper;


/**
 * 文 件 名：ModifyNewPhoneNumberActivity
 * 描   述：修改手机号界面
 * 作   者：Wangzheng
 * 时   间：2018-11-2 16:27:04
 * 版   权：v1.0
 */
public class ModifyNewPhoneNumberActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_USER_EXIST_CODE = 0;
    private static final int SEND_CHECK_CODE = 1;
    private static final int CHECK_CODE = 2;
    private static final int UPDATE_PHONE_NUMBER_CODE = 3;
    private static final int GET_MODIFY_USER_EXIST_CODE = 4;
    //根据用户名同步省统一身份认证系统用户数据
    public static final int SYNCIDCARDUSERINFO = 333;
    private TextView tv_current_phone_number;
    private EditText et_new_phone, et_verify_code;
    private TextView tv_verify_code;
    private TextView tv_modify, tv_cancel;
    private UserInfo.DATABean data;

    private CountDownHelper helper;
    private String newPhoneNumber;
    private String verifyCode;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = LoginUtils.getInstance().getUserInfo().getDATA();
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_new_modify_phone_number;
    }

    private void initView() {
        initTitleBar(getString(R.string.modify_phone_number), this, null);
        tv_current_phone_number = findViewById(R.id.tv_current_phone_number);
        et_new_phone = findViewById(R.id.et_new_phone);
        et_verify_code = findViewById(R.id.et_verify_code);
        tv_verify_code = findViewById(R.id.tv_verify_code);
        tv_modify = findViewById(R.id.tv_modify);
        tv_cancel = findViewById(R.id.tv_cancel);

    }

    private void initData() {
        tv_verify_code.setOnClickListener(this);
        tv_modify.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        if (!BeanUtils.isEmpty(data)) {
            String userDuty = data.getUSERDUTY();
            if ("0".equals(userDuty)) {//个人
                tv_current_phone_number.setText("更换手机号后，下次登录可使用新手机号登录。当前手机号：" + data.getMOBILE_NUM());
            } else if ("1".equals(userDuty)) {//企业
                tv_current_phone_number.setText("更换手机号后，下次登录可使用新手机号登录。当前手机号：" + data.getGR_REALPHONE());
            }
        }
    }

    /**
     * 发送手机验证码请求
     *
     * @param phoneNumber
     */
    private void sendCheckCode(String phoneNumber) {
        ApiRequest request = OAInterface.sendCheckCode(phoneNumber);
        invoke.invokeWidthDialog(request, callBack, SEND_CHECK_CODE);
    }

    /**
     * 验证手机验证码请求
     *
     * @param phoneNumber
     * @param code
     */
    private void checkCode(String phoneNumber, String code) {
        ApiRequest request = OAInterface.checkCode(phoneNumber, code);
        invoke.invokeWidthDialog(request, callBack, CHECK_CODE);
    }

    /**
     * 获取用户是否存在请求
     */
    private void getUserExist(String userName, int what) {
        ApiRequest request = OAInterface.getUserExist(userName, LoginUtils.getInstance().getUserInfo().getUserType());
        invoke.invoke(request, callBack, what);
    }

    /**
     * 获取修改手机号请求
     *
     * @param accessToken
     * @param phoneNumber
     */
    private void updatePhoneNumber(String accessToken, String phoneNumber) {
        ApiRequest request = OAInterface.updatePhoneNumber(accessToken, phoneNumber);
        invoke.invokeWidthDialog(request, callBack, UPDATE_PHONE_NUMBER_CODE);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = "";
            if (what == SYNCIDCARDUSERINFO) {
                message = item.getString("message");
            } else {
                message = item.getString("msg");
            }
            if (Constants.CODE.equals(code)) {
                if (what == GET_USER_EXIST_CODE) {
                    boolean data = item.getBoolean("data", false);
                    if (data) {
                        DialogUtils.showToast(ModifyNewPhoneNumberActivity.this, "输入的手机号已注册！");
                    } else {
                        sendCheckCode(newPhoneNumber);
                    }
                } else if (what == SEND_CHECK_CODE) {
                    startCountDown();
                } else if (what == CHECK_CODE) {
                    if ("验证成功".equals(message)) {
                        GetTokenUtils.getToken(ModifyNewPhoneNumberActivity.this, new GetTokenUtils.ICallback() {
                            @Override
                            public void callback(String token) {
                                updatePhoneNumber(token, newPhoneNumber);
                            }
                        });
                    } else {
                        DialogUtils.showToast(ModifyNewPhoneNumberActivity.this, "验证码" + message);
                    }
                } else if (what == UPDATE_PHONE_NUMBER_CODE) {
                    DialogUtils.showToast(ModifyNewPhoneNumberActivity.this, message);
                    String userPsw = sp.getString("userPsw", "");
                    try {
                        invoke.invokeWidthDialog(OAInterface.syncIdcardUserInfo("0",
                                LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME(),
                                AESUtil.decrypt("98563258", userPsw)), callBack, SYNCIDCARDUSERINFO);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (what == GET_MODIFY_USER_EXIST_CODE) {
                    boolean data = item.getBoolean("data", false);
                    if (data) {
                        DialogUtils.showToast(ModifyNewPhoneNumberActivity.this, "输入的手机号已注册！");
                    } else {
                        checkCode(newPhoneNumber, verifyCode);
                    }
                }
            } else if (Constants.SUCCESS_CODE.equals(code)) {
                    if (what == SYNCIDCARDUSERINFO) {
                    String jsonStr = item.getJsonStr();
                    Gson gson = new Gson();
                    UserInfo userInfo = gson.fromJson(jsonStr, UserInfo.class);
                    LoginUtils.getInstance().getUserInfo().setDATA(userInfo.getDATA());
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("userInfoBean", GsonUtil.GsonString(LoginUtils.getInstance().getUserInfo()));
                    edit.commit();
                    ModifyNewPhoneNumberActivity.this.finish();
                }
            } else {
                DialogUtils.showToast(ModifyNewPhoneNumberActivity.this, message);
            }
        }

//        @Override
//        public void finish(Object dialogObj, int what) {
//            super.finish(dialogObj, what);
//            if (dialogObj != null) {
//                if (dialogObj instanceof LoadingDialog) {
//                    ((LoadingDialog) dialogObj).dismiss();
//                }
//            }
//        }
    };

    //开始倒计时
    private void startCountDown() {
        helper = new CountDownHelper(tv_verify_code, "获取验证码", "重新获取", 60, 1);
        helper.setOnFinishListener(new CountDownHelper.OnFinishListener() {
            @Override
            public void finish() {
                tv_verify_code.setText("获取验证码");
            }
        });
        helper.start();
    }

    @Override
    public void onClick(View v) {
        newPhoneNumber = et_new_phone.getText().toString();
        verifyCode = et_verify_code.getText().toString();
        switch (v.getId()) {
            case R.id.system_back:
                ModifyNewPhoneNumberActivity.this.finish();
                break;
            case R.id.tv_verify_code:
                if (BeanUtils.isEmptyStr(newPhoneNumber)) {
                    DialogUtils.showToast(ModifyNewPhoneNumberActivity.this, "手机号不能为空！");
                    return;
                } else if (!BeanUtils.isMobileNO(newPhoneNumber)) {
                    DialogUtils.showToast(ModifyNewPhoneNumberActivity.this, "手机号码格式不正确！");
                    return;
                }
                if (data.getMOBILE_NUM().equals(newPhoneNumber)) {
                    DialogUtils.showToast(ModifyNewPhoneNumberActivity.this, "输入的手机号不能和当前的手机号一样！");
                    return;
                }
                getUserExist(newPhoneNumber, GET_USER_EXIST_CODE);
                break;
            case R.id.tv_modify:
                if (BeanUtils.isEmptyStr(newPhoneNumber)) {
                    DialogUtils.showToast(ModifyNewPhoneNumberActivity.this, "手机号不能为空！");
                    return;
                }
                if (BeanUtils.isEmptyStr(verifyCode)) {
                    DialogUtils.showToast(ModifyNewPhoneNumberActivity.this, "验证码不能为空！");
                    return;
                }
                getUserExist(newPhoneNumber, GET_MODIFY_USER_EXIST_CODE);
                break;
            case R.id.tv_cancel:
                ModifyNewPhoneNumberActivity.this.finish();
                break;
        }
    }

}
