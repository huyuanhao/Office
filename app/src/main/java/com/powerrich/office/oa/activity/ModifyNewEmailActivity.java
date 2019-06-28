package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
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
import com.yt.simpleframe.utils.CountDownHelper;


/**
 * 文 件 名：ModifyNewEmailActivity
 * 描   述：修改邮箱界面
 * 作   者：Wangzheng
 * 时   间：2018-11-1 10:18:51
 * 版   权：v1.0
 */
public class ModifyNewEmailActivity extends BaseActivity implements View.OnClickListener {

    private static final int SEND_CHECK_CODE = 0;
    private static final int CHECK_CODE = 1;
    private TextView tv_current_email;
    private EditText et_phone, et_verify_code;
    private TextView tv_verify_code;
    private TextView tv_next, tv_cancel;
    private UserInfo.DATABean data;

    private CountDownHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = LoginUtils.getInstance().getUserInfo().getDATA();
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_new_modify_email;
    }

    private void initView() {
        initTitleBar(getString(R.string.modify_email), this, null);
        tv_current_email = findViewById(R.id.tv_current_email);
        et_phone = findViewById(R.id.et_phone);
        et_verify_code = findViewById(R.id.et_verify_code);
        tv_verify_code = findViewById(R.id.tv_verify_code);
        tv_next = findViewById(R.id.tv_next);
        tv_cancel = findViewById(R.id.tv_cancel);

    }

    private void initData() {
        tv_verify_code.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        if (!BeanUtils.isEmpty(data)) {
            String userDuty = data.getUSERDUTY();
            if ("0".equals(userDuty)) {//个人
                tv_current_email.setText(data.getEMAIL());
                et_phone.setText(data.getMOBILE_NUM());
            } else if ("1".equals(userDuty)) {//企业
                tv_current_email.setText(data.getGR_EMAIL());
                et_phone.setText(data.getGR_REALPHONE());
            }
        }

    }

    /**
     * 发送手机验证码请求
     * @param phoneNumber
     */
    private void sendCheckCode(String phoneNumber) {
        ApiRequest request = OAInterface.sendCheckCode(phoneNumber);
        invoke.invokeWidthDialog(request, callBack, SEND_CHECK_CODE);
    }

    /**
     * 验证手机验证码请求
     * @param phoneNumber
     * @param code
     */
    private void checkCode(String phoneNumber, String code) {
        ApiRequest request = OAInterface.checkCode(phoneNumber, code);
        invoke.invokeWidthDialog(request, callBack, CHECK_CODE);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("msg");
            if (Constants.CODE.equals(code)) {
                if (what == SEND_CHECK_CODE) {
                    startCountDown();
                } else if (what == CHECK_CODE) {
                    if ("验证成功".equals(message)) {
                        Intent intent = new Intent(ModifyNewEmailActivity.this, ModifyNewEmail2Activity.class);
                        startActivity(intent);
                    } else {
                        DialogUtils.showToast(ModifyNewEmailActivity.this, "验证码" + message);
                    }
                }
            } else {
                DialogUtils.showToast(ModifyNewEmailActivity.this, message);
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
        String phone = et_phone.getText().toString();
        String verifyCode = et_verify_code.getText().toString();
        switch (v.getId()) {
            case R.id.system_back:
                ModifyNewEmailActivity.this.finish();
                break;
            case R.id.tv_verify_code:
                sendCheckCode(phone);
                break;
            case R.id.tv_next:
                if (BeanUtils.isEmptyStr(verifyCode)) {
                    DialogUtils.showToast(ModifyNewEmailActivity.this, "验证码不能为空！");
                    return;
                }
                checkCode(phone, verifyCode);
                break;
            case R.id.tv_cancel:
                ModifyNewEmailActivity.this.finish();
                break;
        }
    }

}
