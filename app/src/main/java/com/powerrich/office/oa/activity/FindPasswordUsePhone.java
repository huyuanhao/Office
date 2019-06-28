package com.powerrich.office.oa.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.network.CodeUtils;
import com.powerrich.office.oa.view.MyDialog;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 文件名：FindPasswordUsePhone
 * 描述：手机号找回密码
 * 作者：chenhao
 * 时间：2018/1/18 0018
 * 版权：
 */

public class FindPasswordUsePhone extends BaseActivity implements View.OnClickListener{

    private static int GETUSEREXIST = 111;
    private static int CHECKFRINFO = 222;
    private static int SENDCHECKCODE = 333;
    private static int CHECKCODE = 555;
    private EditText et_phone;
    private EditText et_code;
    private Button bt_next;
    private TextView tv_timer;
    private int countDown;
    private String sendPhoneNum = "";
    private String userType;//用户类型 0：个人 1：法人
    private EditText et_userName;
    private EditText et_qyname;
    private EditText et_xydm;
    private EditText et_frname;
    private EditText et_fridcard;
    private boolean isSendCheckCode = false;
    private String verifyUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userType = getIntent().getStringExtra("userType");
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_find_password_use_phone;
    }

    private void initView() {
        initTitleBar("找回密码", this, null);
        if (BeanUtils.isEmptyStr(userType))  return;
        LinearLayout ll_gr = findViewById(R.id.ll_gr);
        LinearLayout ll_fr = findViewById(R.id.ll_fr);
        if ("0".equals(userType)) {
            ll_gr.setVisibility(View.VISIBLE);
            ll_fr.setVisibility(View.GONE);
            et_phone = findViewById(R.id.et_phone);
            et_code = findViewById(R.id.et_code);
            tv_timer = findViewById(R.id.tv_timer);
            tv_timer.setOnClickListener(this);
        } else if ("1".equals(userType)) {
            ll_fr.setVisibility(View.VISIBLE);
            ll_gr.setVisibility(View.GONE);
            et_userName = findViewById(R.id.et_userName);
            et_qyname = findViewById(R.id.et_qyname);
            et_xydm = findViewById(R.id.et_xydm);
            et_frname = findViewById(R.id.et_frname);
            et_fridcard = findViewById(R.id.et_fridcard);
        }
        bt_next = findViewById(R.id.bt_next);
        bt_next.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.bt_next:
                if ("0".equals(userType)) {
                    String code = et_code.getText().toString().trim();
                    String curPhoneNum = et_phone.getText().toString().trim();
                    if (!curPhoneNum.equals(sendPhoneNum)) {
                        isSendCheckCode = false;
                    } else {
                        isSendCheckCode = true;
                    }
                    if (!BeanUtils.isMobileNO(et_phone.getText().toString().trim())) {
                        setReturnMsg("请输入正确的手机号");
                        return;
                    } else if (!isSendCheckCode) {
                        setReturnMsg("请先获取验证码");
                        return;
                    } else if (BeanUtils.isEmptyStr(code)) {
                        setReturnMsg("请输入验证码");
                        return;
                    }
                    //验证手机验证码
                    invoke.invokeWidthDialog(OAInterface.checkCode(sendPhoneNum, code), callBack, CHECKCODE);
                } else if ("1".equals(userType)) {
                    verifyUserName = et_userName.getText().toString().trim();
                    if (BeanUtils.isEmptyStr(verifyUserName)) {
                        setReturnMsg("请输入用户名");
                        return;
                    }
                    getUserExist(verifyUserName, "1");
                }

                break;

            case R.id.tv_timer:
                sendPhoneNum = et_phone.getText().toString().trim();
                if (!BeanUtils.isMobileNO(sendPhoneNum)) {
                    DialogUtils.showToast(FindPasswordUsePhone.this, "请输入正确的手机号");
                    return;
                }
                getUserExist(sendPhoneNum, "0");

                break;
        }
    }

    private void getUserExist(String username, String usertype) {
        //获取用户是否存在
        invoke.invokeWidthDialog(OAInterface.getUserExist(username, usertype), callBack, GETUSEREXIST);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("msg");
            if (Constants.CODE.equals(code)) {
                if (what == GETUSEREXIST){//获取用户是否存在
                    Boolean data = item.getBoolean("data", false);
                    if (data) {//用户存在
                        if ("0".equals(userType)) {
                            //获取短信验证码
                            invoke.invokeWidthDialog(OAInterface.sendCheckCode(sendPhoneNum), callBack, SENDCHECKCODE);
                        } else if ("1".equals(userType)) {
                            String qyname = et_qyname.getText().toString().trim();
                            String xydm = et_xydm.getText().toString().trim();
                            String frname = et_frname.getText().toString().trim();
                            String fridcard = et_fridcard.getText().toString().trim();
                            if (BeanUtils.isEmptyStr(qyname)) {
                                setReturnMsg("请输入企业名称");
                                return;
                            } else if (BeanUtils.isEmptyStr(xydm)) {
                                setReturnMsg("请输入信用代码");
                                return;
                            } else if (BeanUtils.isEmptyStr(frname)) {
                                setReturnMsg("请输入法人姓名");
                                return;
                            } else if (BeanUtils.isEmptyStr(fridcard)) {
                                setReturnMsg("请输入身份证号");
                                return;
                            } else if (!BeanUtils.validCard(fridcard)) {
                                setReturnMsg("身份证号格式错误");
                                return;
                            }
                            //法人信息身份验证
                            invoke.invokeWidthDialog(OAInterface.checkFrInfo(qyname, xydm, frname, fridcard.toUpperCase()), callBack, CHECKFRINFO);
                        }
                    } else {//用户不存在
                        if ("0".equals(userType)) {
                            showCloseDialog("该手机号用户没有注册");
                        } else if ("1".equals(userType)) {
                            showCloseDialog("该用户名没有注册");
                        }
                    }
                } else if (what == CHECKFRINFO){//法人信息身份验证
                    if ("验证成功".equals(message)) {
                        Intent intent = new Intent(FindPasswordUsePhone.this, ReSetPasswordActivity.class);
                        intent.putExtra("userType", userType);
                        intent.putExtra("userName", verifyUserName);
                        startActivity(intent);
                    } else {
                        DialogUtils.showToast(FindPasswordUsePhone.this, "法人信息" + message);
                    }
                } else if (what == SENDCHECKCODE){//获取短信验证码
                    isSendCheckCode = true;
                    //设置倒计时
                    setTimerTask();
                } else if (what == CHECKCODE){//验证手机验证码
                    if ("验证成功".equals(message)) {
                        Intent intent = new Intent(FindPasswordUsePhone.this, ReSetPasswordActivity.class);
                        intent.putExtra("userType", userType);
                        intent.putExtra("phoneNum", sendPhoneNum);
                        startActivity(intent);
                    } else {
                        DialogUtils.showToast(FindPasswordUsePhone.this, "验证码" + message);
                    }
                }
            } else {
                DialogUtils.showToast(FindPasswordUsePhone.this, CodeUtils.getErrorMsg(code));
            }
        }

    };

    private void setReturnMsg(String msg){
        DialogUtils.showToast(FindPasswordUsePhone.this, msg);
    }

    /**
     * 设置倒计时
     */
    private void setTimerTask() {
        countDown = 61;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        countDown--;
                        if (countDown <= 0){
                            timer.cancel();//取消倒计时
                            tv_timer.setText("获取验证码");
                            tv_timer.setClickable(true);//重新获得点击
                            tv_timer.setTextColor(ContextCompat.getColor(FindPasswordUsePhone.this,
                                    R.color.white));
                            tv_timer.setBackgroundResource(R.drawable.valicode_selecter_shap);  //还原背景色
                        } else {
                            tv_timer.setClickable(false); //设置不可点击
                            tv_timer.setTextColor(ContextCompat.getColor(FindPasswordUsePhone.this,
                                    R.color.gray));
                            String timerTxt = "<font color='#ea7805'>" + countDown + "</font>秒重新获取";
                            tv_timer.setText(Html.fromHtml(timerTxt));  //设置倒计时时间
                            tv_timer.setBackgroundResource(R.drawable.gray_bg); //设置按钮为灰色，这时是不能点击的
                        }
                    }
                });


            }
        }, 1000, 1000);//表示1000毫秒之后，每隔1000毫秒执行一次.
    }

    /**
     *用户不存在弹框
     */
    private void showCloseDialog(String message){
        MyDialog builder = new MyDialog(FindPasswordUsePhone.this).builder();
        builder
                .setTitle("提示信息")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
