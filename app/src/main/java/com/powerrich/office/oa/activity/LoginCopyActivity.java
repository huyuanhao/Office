package com.powerrich.office.oa.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.SIMeID.ResultParams;
import com.powerrich.office.oa.SIMeID.SIMeIDApplication;
import com.powerrich.office.oa.SIMeID.URLContextSMS;
import com.powerrich.office.oa.activity.mine.GetVarificationActivity;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.enums.RegisterType;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.AESUtil;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.GsonUtil;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.view.DeletableEditText;
import com.powerrich.office.oa.view.KeyboardLayout;
import com.powerrich.office.oa.view.LoadingDialog;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 登录界面
 *
 * @author Administrator
 */
public class LoginCopyActivity extends BaseActivity implements OnClickListener {
    /**
     * 获取token值
     */
    private static final int GETTOKEN = 000;
    /**
     * 根据用户名和密码查找人员信息
     */
    private static final int GETLOGINUER = 111;
    private static final int GETLOGINUERINFO = 112;
    private static final String TAG = LoginCopyActivity.class.getSimpleName();
    private TextView tv_passwd_login;
    private TextView tv_eid_login;
    private TextView tv_forget_pwd;
    private DeletableEditText et_name;
    private DeletableEditText et_psw;
    private Button bt_login;
    private TextView bt_register;
    private CheckBox cb_save_pwd;
    private SharedPreferences sp;
    private double exitTime = 0;
    private UserInfo mUser;
    private HandlerThread handlerThread;
    private Handler mBKHandler;
    private static final int MSG_SIGN_BEGIN = 1;
    private static final int MSG_SIGN_END = 2;
    private Handler mUIHandler;
    private SIMeIDApplication app;
    private OkHttpClient client;
    private String strMobileNo;

    /**软键盘弹起将 view整体往上移动*/
    private ScrollView sv;
    private KeyboardLayout kbLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = ((SIMeIDApplication) getApplication());
        initOkHttp();
        initializeHandler();
        //获取首选项
        sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        mUser = new UserInfo();
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login;
    }

    private void initOkHttp() {

        if (null == client) {

            client = new OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build();

        }

    }

    private void initData() {
        //获取token值
//		    invoke.invoke(OAInterface.getToken("AE912D644F39F1F51CF70A07DB4F22A4", "E12CF98663A822983118AD92B2FF3AB5"),
// callBack,GETTOKEN);
    }

    private void initView() {

        //软键盘弹起View
        sv = (ScrollView) findViewById(R.id.sv);
        kbLayout = (KeyboardLayout) findViewById(R.id.kb_layout);
        addLayoutListener();


        tv_passwd_login = (TextView) findViewById(R.id.tv_passwd_login);
        tv_eid_login = (TextView) findViewById(R.id.tv_eid_login);
        tv_forget_pwd = (TextView) findViewById(R.id.tv_forget_pwd);
        et_name = (DeletableEditText) findViewById(R.id.et_name);
        et_psw = (DeletableEditText) findViewById(R.id.et_psw);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_register = (TextView) findViewById(R.id.bt_register);
//		bt_unname_login = (Button) findViewById(R.id.bt_unname_login);
        tv_passwd_login.setOnClickListener(this);
        tv_passwd_login.setSelected(true);
        tv_eid_login.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
//		bt_unname_login.setOnClickListener(this);
        cb_save_pwd = (CheckBox) findViewById(R.id.cb_save_pwd);
        cb_save_pwd.setOnClickListener(this);

        tv_forget_pwd.setOnClickListener(this);
        loadData();

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (tv_passwd_login.isSelected()) {
                    et_psw.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    String userName = "";

    private void loadData() {
        tv_passwd_login.setSelected(true);

        String userPsw = "";
        try {
            userName = AESUtil.decrypt("98563258", sp.getString("userName", ""));
        } catch (Exception ex) {
            userName = "";
        }
        try {
            userPsw = AESUtil.decrypt("98563258", sp.getString("userPsw", ""));
        } catch (Exception ex) {
            userPsw = "";
        }
        et_name.setText(userName);
        boolean b = sp.getBoolean("save_pwd", false);
        cb_save_pwd.setChecked(b);
        if (b) {
            et_psw.setText(userPsw);
        } else {
            et_psw.setText("");
        }

//        if (sp.getString("userName", "").equals("")) {
//            cb_save_pwd.setChecked(false);
//        } else {
//            cb_save_pwd.setChecked(true);
//        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //密码登录
            case R.id.tv_passwd_login:
                if (!tv_passwd_login.isSelected()) {
                    tv_passwd_login.setSelected(true);
                    tv_eid_login.setSelected(false);
                    et_psw.setVisibility(View.VISIBLE);
                    et_name.setHint(R.string.login_name_hint);
                    cb_save_pwd.setVisibility(View.VISIBLE);
                    tv_forget_pwd.setVisibility(View.VISIBLE);
                    bt_register.setVisibility(View.VISIBLE);
                    et_name.setText(userName);
                }
                break;
            //eid登录
            case R.id.tv_eid_login:
                if (!tv_eid_login.isSelected()) {
                    tv_eid_login.setSelected(true);
                    tv_passwd_login.setSelected(false);
                    et_psw.setVisibility(View.GONE);
                    et_name.setHint(R.string.login_name_hint_1);
                    cb_save_pwd.setVisibility(View.GONE);
                    tv_forget_pwd.setVisibility(View.GONE);
                    bt_register.setVisibility(View.GONE);
                    et_name.setText("");
                }
                break;
            case R.id.bt_login:
//                et_name.setText("chenzhiqiang");
//                et_psw.setText("000000");
                if (tv_passwd_login.isSelected()) {
                    String name = et_name.getText().toString().trim();
                    String psw = et_psw.getText().toString().trim();
                    //判断账户字符是否为空，
                    if (TextUtils.isEmpty(name)) {
                        //为空时抖动提示
                        et_name.setShakeAnimation();
                        DialogUtils.showToast(LoginCopyActivity.this, "账户或密码不能为空");
                        return;
                    }
                    //判断密码字符是否为空
                    if (TextUtils.isEmpty(psw)) {
                        //为空时抖动提示
                        et_psw.setShakeAnimation();
                        DialogUtils.showToast(LoginCopyActivity.this, "账户或密码不能为空");
                        return;
                    }
//			}
                    invoke.invokeWidthDialog(OAInterface.login("0", name, psw), callBack, GETLOGINUER);
                } else if (tv_eid_login.isSelected()) {
                    //eid登录
                    strMobileNo = et_name.getText().toString().trim();
                    //判断账户字符是否为空，
                    if (TextUtils.isEmpty(strMobileNo)) {
                        //为空时抖动提示
                        et_name.setShakeAnimation();
                        DialogUtils.showToast(LoginCopyActivity.this, "手机号不能为空");
                        return;
                    }
                    //开始eID匿名认证
                    startDoDirectLoginThread();
                }
                break;
            case R.id.bt_register:
//                Intent intent = new Intent(this, RegisterActivity.class);
//                intent.putExtra(RegisterActivity.REGISTER_TYPE, RegisterActivity.REGISTER_TYPE_PERSON);
//                startActivity(intent);
                Intent intent = new Intent(this, GetVarificationActivity.class);
                intent.putExtra("type", RegisterType.注册);
                startActivity(intent);
                break;
//		case R.id.bt_unname_login:
//			LoginUtils.getInstance().setIsLoginSuccess(false);
//			Intent intent = new Intent(this, MainActivity.class);
//			startActivity(intent);
//			finish();
//			break;
            case R.id.tv_forget_pwd:
//                Intent intent_forget_pwd = new Intent(this, FindPasswordWay.class);
//                startActivity(intent_forget_pwd);
                intent = new Intent(this, GetVarificationActivity.class);
                intent.putExtra("type", RegisterType.忘记密码);
                startActivity(intent);
                break;
            case R.id.cb_save_pwd:
                Editor edit = sp.edit();

                if (cb_save_pwd.isChecked()) {
                    edit.putBoolean("save_pwd", true);
                } else {
                    edit.putBoolean("save_pwd", false);
                }
                edit.commit();
                break;
            default:
                break;
        }
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String msg = item.getString("message");
            if (what == GETTOKEN) {
                if (Constants.SUCCESS == item.getBoolean("success")) {
                    String token = item.getString("data");
                    mUser.setAuthtoken(token);
                }
            } else if (what == GETLOGINUER) {
                if (!Constants.IS_CERTIFY) {
                    if (Constants.SUCCESS_CODE.equals(item.getString("code"))) {
                        ResultItem data = (ResultItem) item.get("DATA");
                        UserInfo.DATABean dataBean = new UserInfo.DATABean();
                        mUser.setDATA(dataBean);
                        mUser.getDATA().setUSERID(data.getString("USERID"));
                        mUser.getDATA().setUSERNAME(data.getString("USERNAME"));
                        mUser.setUserType(data.getString("USERTYPE"));
                        mUser.setAuthtoken(data.getString("AUTHTOKEN"));

                        isLogin = true;
                        LoginUtils.getInstance().setIsLoginSuccess(true);
                        //极光推送设置别名
                        setAlias();
                        //根据用户名和密码查找人员信息接口
                        invoke.invokeWidthDialog(OAInterface.getUserByIdInfo(mUser.getDATA().getUSERNAME()), callBack,
                                GETLOGINUERINFO);
                        //标记自动登录
                        Editor edit = sp.edit();
                        edit.putBoolean("is_login", true);
                        edit.putBoolean("userIsLogin", true);//是否登录
                        edit.commit();

                    } else {
                        DialogUtils.showToast(LoginCopyActivity.this, msg);
                    }
                } else {
                    if (Constants.SUCCESS_CODE.equals(item.getString("code"))) {
                        ResultItem data = (ResultItem) item.get("data");
//                        user.setAccount(data.getString("account"));
//                        user.setPersonid(data.getString("personid"));
//                        user.setSfsmrz(data.getString("sfsmrz"));
//                        user.setSjh(data.getString("sjh"));
//                        user.setZclx(data.getString("zclx"));
                        LoginUtils.getInstance().setUserInfo(mUser);
                        gotoMain();
                    } else {
                        DialogUtils.showToast(LoginCopyActivity.this, msg);
                    }
                }

				/*if (Constants.SUCCESS_CODE.equals(item.getString("code"))) {
                    ResultItem data = (ResultItem) item.get("data");
					user.setPersonid(data.getString("USERID"));
					user.setAccount(data.getString("REALNAME"));
					gotoMain();
				}else {
					Toast.makeText(LoginActivity.this, "用户名或密码不正确",
							Toast.LENGTH_SHORT).show();
				}*/
            } else if (what == GETLOGINUERINFO) {
                if (Constants.SUCCESS_CODE.equals(item.getString("code"))) {
                    String jsonStr = item.getJsonStr();
                    Gson gson = new Gson();
                    UserInfo userInfo = gson.fromJson(jsonStr, UserInfo.class);

                    userInfo.setUserType(mUser.getUserType());
                    userInfo.setAuthtoken(mUser.getAuthtoken());
                    userInfo.setSiteNo(mUser.getSiteNo());
                    LoginUtils.getInstance().setUserInfo(userInfo);
                    Editor edit = sp.edit();
                    edit.putString("userInfoBean", GsonUtil.GsonString(userInfo));
                    edit.commit();

                    gotoMain();
                } else {
                    DialogUtils.showToast(LoginCopyActivity.this, msg);
                }
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

    private void setAlias() {
        JPushInterface.setAlias(LoginCopyActivity.this, 0, mUser.getDATA().getUSERID());
    }

    /**
     * 跳转到主界面
     */
    private void gotoMain() {
        String name = et_name.getText().toString().trim();
        String psw = et_psw.getText().toString().trim();
        String name_encode;
        String psw_encode;
        try {
            name_encode = AESUtil.encrypt("98563258", name);
        } catch (Exception ex) {
            name_encode = "";
        }

        try {
            psw_encode = AESUtil.encrypt("98563258", psw);
        } catch (Exception ex) {
            psw_encode = "";
        }
        Editor edit = sp.edit();
        edit.putString("userName", name_encode);
        edit.putString("userPsw", psw_encode);
        edit.commit();
        //办事指南的登录返回码
        int requestCode = getIntent().getIntExtra("requestCode", -1);
        if (requestCode != -1) {
            //设置返回码
            setResult(requestCode);
            finish();
            return;
        }

        String targeName = getIntent().getStringExtra("targetName");
        if (!BeanUtils.isEmpty(targeName)) {
            try {
                Class clss = Class.forName(targeName);
                Intent intent = new Intent(this, clss);
                Bundle bundle = getIntent().getExtras();
                if (null != bundle) {
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            //跳转到主界面
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }

    private void initializeHandler() {

        Looper looper;
        if (null != (looper = Looper.myLooper())) {

            mUIHandler = new EventHandler(this, looper);

        } else if (null != (looper = Looper.getMainLooper())) {

            mUIHandler = new EventHandler(this, looper);

        } else {

            mUIHandler = null;

        }
    }

    private static class EventHandler extends Handler {

        private LoginCopyActivity curAT = null;

        public EventHandler(LoginCopyActivity curAT, Looper looper) {
            super(looper);

            this.curAT = curAT;

        }

        public void handleMessage(Message msg) {

            if (null != curAT) {

                curAT.processMsg(msg);
                return;

            }

            super.handleMessage(msg);

        }

    }

    private void startDoDirectLoginThread() {

        //创建一个HandlerThread并启动它
        handlerThread = new HandlerThread("DoDirectLoginThread");
        handlerThread.start();

        //使用HandlerThread的looper对象创建Handler，如果使用默认的构造方法，会阻塞UI线程。
        mBKHandler = new Handler(handlerThread.getLooper(), new DoDirectLoginCallback());
        mBKHandler.sendEmptyMessage(MSG_SIGN_BEGIN);

    }

    class DoDirectLoginCallback implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {

            //在子线程中进行耗时操作
            mUIHandler.sendEmptyMessage(MSG_SIGN_BEGIN);

            //执行网络请求
            String URL = Constants.SIM_EID_AUTH_URL + URLContextSMS.DIRECT_LOGIN;
            ResultParams result = doSign(URL, strMobileNo, "login");
            Message msgToUI = mUIHandler.obtainMessage(MSG_SIGN_END, result);
            mUIHandler.sendMessage(msgToUI);

            return false;

        }
    }

    private void processMsg(Message msg) {

        switch (msg.what) {

            case MSG_SIGN_BEGIN: {

                String title = strMobileNo + " - 提示";
                String message = "正在请求匿名认证";
                showProgressDlg(title, message);

            }
            break;

            case MSG_SIGN_END: {

                hideProgressDlg();
                ResultParams resultParams = (ResultParams) msg.obj;
                parseResp(resultParams.more);

            }
            break;

            default:
                break;
        }
    }

    private void parseResp(String resp) {

        try {

            JSONObject root = JSONObject.parseObject(resp);
            String result = root.getString(RealNameWithSMSActivity.SECTION.RESULT);
            String resultDetails = root.getString(RealNameWithSMSActivity.SECTION.RESULT_DETAILS);

            if (result.length() == 4) {
                // 认证失败
                ToastUtils.showMessage(this, resultDetails);
            } else {

                if (result.equals("00")) {
                    String userInfoForEID = root.getString(RealNameWithSMSActivity.SECTION.USER_INFO);
                    JSONObject object = JSONObject.parseObject(userInfoForEID);
                    String appEIDCode = object.getString(RealNameWithSMSActivity.SECTION.APP_EID_CODE);
                    // 认证成功，调用后台实名认证接口保存实名信息
                    invoke.invokeWidthDialog(OAInterface.eidLogin(appEIDCode), callBack, GETLOGINUER);
                } else {
                    // 认证失败
                    ToastUtils.showMessage(this, resultDetails);
                }
            }
            Log.e(TAG, result);
            Log.e(TAG, resultDetails);
        } catch (JSONException e) {
            ToastUtils.showMessage(this, e.getMessage());
        }

    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    /**
     * 请求签名
     *
     * @param url
     * @param mobileNo
     * @param dataToSign
     * @return
     */
    private ResultParams doSign(
            String url,
            String mobileNo,
            String dataToSign) {


        ResultParams resultParams = new ResultParams("联调测试");

        JSONObject obj = new JSONObject();

        try {

            obj.put("mobile_no", mobileNo);
            obj.put("data_to_sign", dataToSign);

            RequestBody body = RequestBody.create(JSON, obj.toString());

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            int statusCode = response.code();
            Log.d(TAG, "HTTP Code : " + statusCode);
            if (response.isSuccessful()) {

                resultParams.build(true, response.body().string());

            } else {

                String result = "服务器返回错误：statusCode = " + statusCode;
                resultParams.build(false, result);
                Log.e(TAG, response.toString());

            }


        } catch (Exception e) {

            Log.e(TAG, e.toString());

            String result = "请求异常，url = \"" + url + "\"[" + e.toString() + "]";
            resultParams.build(false, result);

        }

        return resultParams;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != handlerThread) {

            handlerThread.quit();

        }

        app.removeActivity(this);
    }


    /**处理软键盘部分**/
    public void addLayoutListener() {
        kbLayout.setKeyboardListener(new KeyboardLayout.KeyboardLayoutListener() {
            @Override
            public void onKeyboardStateChanged(boolean isActive, int keyboardHeight) {
                Log.e("onKeyboardStateChanged", "isActive:" + isActive + " keyboardHeight:" + keyboardHeight);
                if (isActive) {
                    scrollToBottom();
                }
            }
        });
    }
    /**
     * 弹出软键盘时将SVContainer滑到底
     */
    private void scrollToBottom() {

        sv.postDelayed(new Runnable() {

            @Override
            public void run() {
                sv.smoothScrollTo(0, sv.getBottom() +30);
            }
        }, 100);

    }
}
