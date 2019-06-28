package com.powerrich.office.oa.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.powerrich.office.oa.AppManager;
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


/**
 * 文 件 名：ModifyNewEmail2Activity
 * 描   述：修改邮箱界面
 * 作   者：Wangzheng
 * 时   间：2018-11-1 10:18:51
 * 版   权：v1.0
 */
public class ModifyNewEmail2Activity extends BaseActivity implements View.OnClickListener {

    private static final int UPDATE_USER_INFO_CODE = 0;
    //根据用户名同步省统一身份认证系统用户数据
    public static final int SYNCIDCARDUSERINFO = 333;
    private EditText et_new_email;
    private TextView tv_modify, tv_cancel;
    private SharedPreferences sp;
    private String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_new_modify_email1;
    }

    private void initView() {
        initTitleBar(getString(R.string.modify_email), this, null);
        et_new_email = findViewById(R.id.et_new_email);
        tv_modify = findViewById(R.id.tv_modify);
        tv_cancel = findViewById(R.id.tv_cancel);
    }

    private void initData() {
        tv_modify.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        getToken();
    }

    /**
     * 获取修改邮箱请求
     */
    private void updateEmail(String accessToken, String email) {
        ApiRequest request = OAInterface.updateEmail(accessToken, email);
        invoke.invokeWidthDialog(request, callBack, UPDATE_USER_INFO_CODE);
    }

    /**
     * 获取token
     */
    private void getToken() {
        GetTokenUtils.getToken(ModifyNewEmail2Activity.this, new GetTokenUtils.ICallback() {
            @Override
            public void callback(String token) {
                mToken = token;
            }
        });
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
                if (what == UPDATE_USER_INFO_CODE) {
                    DialogUtils.showToast(ModifyNewEmail2Activity.this, message);
                    String userPsw = sp.getString("userPsw", "");
                    try {
                        invoke.invokeWidthDialog(OAInterface.syncIdcardUserInfo("0",
                                LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME(),
                                AESUtil.decrypt("98563258", userPsw)), callBack, SYNCIDCARDUSERINFO);
                    } catch (Exception e) {
                        e.printStackTrace();
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
                    ModifyNewEmail2Activity.this.finish();
                    AppManager.getAppManager().finishActivity(ModifyNewEmailActivity.class);
                }
            } else {
                DialogUtils.showToast(ModifyNewEmail2Activity.this, message);
            }
        }

        @Override
        public void finish(Object dialogObj, int what) {
            super.finish(dialogObj, what);
            if (dialogObj != null) {
                if (dialogObj instanceof LoadingDialog) {
                    ((LoadingDialog) dialogObj).dismiss();
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                ModifyNewEmail2Activity.this.finish();
                break;
            case R.id.tv_modify:
                final String email = et_new_email.getText().toString().trim();
                if (BeanUtils.isEmptyStr(email)) {
                    DialogUtils.showToast(ModifyNewEmail2Activity.this, "新邮箱不能为空！");
                    return;
                } else if (!BeanUtils.isEmail(email)) {
                    DialogUtils.showToast(ModifyNewEmail2Activity.this, "新邮箱格式不正确！");
                    return;
                }
                if (BeanUtils.isEmptyStr(mToken)) {
                    DialogUtils.showToast(this, "网络不给力，请稍后再试！");
                    getToken();
                    return;
                }
                updateEmail(mToken, email);
                break;
            case R.id.tv_cancel:
                ModifyNewEmail2Activity.this.finish();
                break;
        }
    }
}
