package com.powerrich.office.oa.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.SIMeID.ResultParams;
import com.powerrich.office.oa.SIMeID.SIMeIDApplication;
import com.powerrich.office.oa.SIMeID.URLContextSMS;
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
import com.powerrich.office.oa.tools.GsonUtil;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.view.DeletableEditText;
import com.powerrich.office.oa.view.KeyboardLayout;
import com.powerrich.office.oa.view.LoadingDialog;
import com.powerrich.office.oa.view.MyDialog;
import com.yt.simpleframe.utils.StringUtil;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class LoginActivity extends BaseActivity implements OnClickListener {

    public static String EID_STR = "eid用户请用eid登录";
    public static String PWD_STR = "普通用户请用密码登录";

    /**
     * 根据用户名和密码查找人员信息
     */
    private static final int GETLOGINUER = 111;
    private static final int GETLOGINUERINFO = 112;
    private static final String TAG = LoginActivity.class.getSimpleName();
    //    private TextView tv_passwd_login;
    private TextView tv_eid_login, tv_register;
    private TextView tv_forget_pwd;
    private DeletableEditText et_name;
    private DeletableEditText et_psw;
    private Button bt_login;
    private ImageView iv_eye;
    private TextView bt_register;

    private ImageView mIvBack;
    //    private CheckBox cb_save_pwd;
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

    /**
     * 软键盘弹起将 view整体往上移动
     */
    private ScrollView sv;
    private KeyboardLayout kbLayout;
    private LinearLayout mLlUserName;
    private TextView mTvUserName;
    private View mVUserName;
    private LinearLayout mLlLegal;
    private TextView mTvLegal;
    private View mVLegal;
    //授权码
    private String authCode;

    //用户类型：0个人，1法人
    private String userType = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        app = ((SIMeIDApplication) getApplication());
        initOkHttp();
        initializeHandler();
        //获取首选项
        sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        mUser = new UserInfo();
        initView();
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


    private void initView() {

        //软键盘弹起View
        sv = (ScrollView) findViewById(R.id.sv);

        sv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                //不能滑动
                return true;
                //可以滑动
                //return false;
            }
        });

        kbLayout = (KeyboardLayout) findViewById(R.id.kb_layout);
        addLayoutListener();

        mLlUserName = findViewById(R.id.ll_user_name);
        mTvUserName = findViewById(R.id.tv_user_name);
        mVUserName = findViewById(R.id.v_user_name);
        mLlLegal = findViewById(R.id.ll_legal);
        mTvLegal = findViewById(R.id.tv_legal);
        mVLegal = findViewById(R.id.v_legal);
//        tv_passwd_login = (TextView) findViewById(R.id.tv_passwd_login);
        tv_eid_login = (TextView) findViewById(R.id.tv_eid_login);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_forget_pwd = (TextView) findViewById(R.id.tv_forget_pwd);
        et_name = (DeletableEditText) findViewById(R.id.et_name);
        et_psw = (DeletableEditText) findViewById(R.id.et_psw);
        bt_login = (Button) findViewById(R.id.bt_login);
        iv_eye = findViewById(R.id.iv_eye);
        bt_register = (TextView) findViewById(R.id.bt_register);
//        tv_passwd_login.setOnClickListener(this);
//        tv_passwd_login.setSelected(true);
        tv_eid_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        iv_eye.setOnClickListener(this);

        mLlUserName.setOnClickListener(this);
        mLlLegal.setOnClickListener(this);
//        cb_save_pwd = (CheckBox) findViewById(R.id.cb_save_pwd);
//        cb_save_pwd.setOnClickListener(this);
        tv_forget_pwd.setOnClickListener(this);
        mIvBack = (ImageView) findViewById(R.id.system_back);
        mIvBack.setOnClickListener(this);
//        loadData();

        //默认普通密码登录
        tv_eid_login.setText(EID_STR);
        changeLogin();
        String userName = sp.getString("userName", "");
        try {
            et_name.setText(AESUtil.decrypt("98563258", userName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //回显个人或企业的选中状态
        String locUserType = sp.getString("userType", "");
        if (!BeanUtils.isEmptyStr(locUserType)) {
            userType = locUserType;
        }
        if ("0".equals(userType)) {
            mTvUserName.setTextColor(getResources().getColor(R.color.login_user_name_select));
            mTvLegal.setTextColor(getResources().getColor(R.color.login_logal_nomal));
            mVUserName.setVisibility(View.VISIBLE);
            mVLegal.setVisibility(View.GONE);
            et_name.setHint("请输入手机号/用户名/身份证号");
            tv_register.setText("没有账号? 立即注册");
        } else if ("1".equals(userType)){
            mTvLegal.setTextColor(getResources().getColor(R.color.login_user_name_select));
            mTvUserName.setTextColor(getResources().getColor(R.color.login_logal_nomal));
            mVLegal.setVisibility(View.VISIBLE);
            mVUserName.setVisibility(View.GONE);
            et_name.setHint("请输入用户名、企业统一信用代码");
            tv_register.setText("注册说明");
        }
//        et_name.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////                if (tv_passwd_login.isSelected()) {
//                    et_psw.setText("");
////                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
        et_psw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //当有字符时为true
//                boolean isVisible = getText().toString().length() >= 1;
                boolean isVisible = s.length() >= 1;
                if (isVisible) {
                    iv_eye.setVisibility(View.VISIBLE);
                } else {
                    iv_eye.setVisibility(View.GONE);
                }
            }
        });


    }

//    String userName = "";

//    private void loadData() {
////        tv_passwd_login.setSelected(true);
//
////        String userPsw = "";
////        try {
////            userName = AESUtil.decrypt("98563258", sp.getString("userName", ""));
////        } catch (Exception ex) {
////            userName = "";
////        }
////        try {
////            userPsw = AESUtil.decrypt("98563258", sp.getString("userPsw", ""));
////        } catch (Exception ex) {
////            userPsw = "";
////        }
////        et_name.setText(userName);
//
////        boolean b = sp.getBoolean("save_pwd", false);
////        cb_save_pwd.setChecked(b);
////        if (b) {
////            et_psw.setText(userPsw);
////        } else {
////            et_psw.setText("");
////        }
//
////        if (sp.getString("userName", "").equals("")) {
////            cb_save_pwd.setChecked(false);
////        } else {
////            cb_save_pwd.setChecked(true);
////        }
//    }

    private boolean sysIsCheck = false;

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        if (BaseActivity.isFastDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            //密码登录
            case R.id.tv_passwd_login:
                break;

            case R.id.tv_register:

                if (userType.equals("0")) {
                    Intent intent = new Intent(this, RegisterNewActivity.class);
                    intent.putExtra("TYPE", 0);
                    startActivity(intent);
                } else {
                    MyDialog.showDialogCc(this, "请先注册个人账号，实名认证通过后,在【我的-设置】中进行【法人账号注册】", new MyDialog.InterfaceClick() {
                        @Override
                        public void click() {

                        }
                    });
                }


                break;
            case R.id.tv_eid_login:
                btBottomHight = -1;//重新定位y的高度
                String chooseText = tv_eid_login.getText().toString();
                if (chooseText.contains("eid")) {
                    tv_eid_login.setText(PWD_STR);
                    et_psw.setVisibility(View.GONE);
                    tv_forget_pwd.setVisibility(View.GONE);
                    bt_register.setVisibility(View.GONE);
                } else {
                    tv_eid_login.setText(EID_STR);
                    et_psw.setVisibility(View.VISIBLE);
                    tv_forget_pwd.setVisibility(View.VISIBLE);
                    bt_register.setVisibility(View.VISIBLE);
                }
                changeLogin();
                et_name.setText("");
                et_psw.setText("");
                break;
            case R.id.bt_login:
                chooseText = tv_eid_login.getText().toString();
                if (chooseText.contains("eid")) {
                    //普通密码登录
                    String name = et_name.getText().toString().trim();
                    String psw = et_psw.getText().toString().trim();
                    //判断账户字符是否为空，
                    if (TextUtils.isEmpty(name)) {
                        //为空时抖动提示
                        et_name.setShakeAnimation();
                        DialogUtils.showToast(LoginActivity.this, "账户或密码不能为空");
                        return;
                    }
                    //判断密码字符是否为空
                    if (TextUtils.isEmpty(psw)) {
                        //为空时抖动提示
                        et_psw.setShakeAnimation();
                        DialogUtils.showToast(LoginActivity.this, "账户或密码不能为空");
                        return;
                    }
                    invoke.invokeWidthDialog(OAInterface.login(userType, name, psw), callBack, GETLOGINUER);
                } else {
                    //eid登录
                    strMobileNo = et_name.getText().toString().trim();
                    //判断账户字符是否为空，
                    if (TextUtils.isEmpty(strMobileNo)) {
                        //为空时抖动提示
                        et_name.setShakeAnimation();
                        DialogUtils.showToast(LoginActivity.this, "手机号不能为空");
                        return;
                    }
                    if (!StringUtil.isMobileNO(strMobileNo)) {
                        //为空时抖动提示
                        et_name.setShakeAnimation();
                        DialogUtils.showToast(LoginActivity.this, "手机输入格式不正确");
                        return;
                    }
                    //开始eID匿名认证
                    startDoDirectLoginThread();
                }
                break;

            case R.id.iv_eye:
                if (sysIsCheck) {
                    sysIsCheck = false;
                    iv_eye.setImageResource(R.drawable.login_eye_grey);
                    et_psw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_psw.setSelection(et_psw.length());
                } else {
                    sysIsCheck = true;
                    iv_eye.setImageResource(R.drawable.login_eye);
                    et_psw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_psw.setSelection(et_psw.length());
                }

                break;

            case R.id.bt_register:
//                Intent intent = new Intent(this, RegisterNewActivity.class);
//
//                startActivity(intent);

//                Intent intent = new Intent(this, GetVarificationActivity.class);
//                intent.putExtra("type", RegisterType.注册);
//                startActivity(intent);

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
                Intent intent = new Intent(this, FindPasswordUsePhone.class);
                intent.putExtra("userType", userType);
                startActivity(intent);
                break;
//            case R.id.cb_save_pwd:
//                SharedPreferences.Editor edit = sp.edit();
//
//                if (cb_save_pwd.isChecked()) {
//                    edit.putBoolean("save_pwd", true);
//                } else {
//                    edit.putBoolean("save_pwd", false);
//                }
//                edit.commit();
//                break;
            case R.id.system_back:
                finish();
                break;

            //个人
            case R.id.ll_user_name:
                mTvUserName.setTextColor(getResources().getColor(R.color.login_user_name_select));
                mTvLegal.setTextColor(getResources().getColor(R.color.login_logal_nomal));
                mVUserName.setVisibility(View.VISIBLE);
                mVLegal.setVisibility(View.GONE);
                et_name.setHint("请输入手机号/用户名/身份证号");
                tv_register.setText("没有账号? 立即注册");
                userType = "0";
                break;

            //法人
            case R.id.ll_legal:
                mTvLegal.setTextColor(getResources().getColor(R.color.login_user_name_select));
                mTvUserName.setTextColor(getResources().getColor(R.color.login_logal_nomal));
                mVLegal.setVisibility(View.VISIBLE);
                mVUserName.setVisibility(View.GONE);
                et_name.setHint("请输入用户名、企业统一信用代码");
                tv_register.setText("注册说明");
                userType = "1";
                break;
            default:
                break;
        }
    }


    private void changeLogin() {
        //默认的是密码登录
        String chooseText = tv_eid_login.getText().toString().trim();
        SpannableString spannableString;

        if (chooseText.contains("eid")) {
            spannableString = new SpannableString(EID_STR);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#11A3FA")), EID_STR.length() - 5, EID_STR
                    .length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            et_name.setHint("请输入手机号/用户名/身份证号");
            et_name.setInputType(InputType.TYPE_CLASS_TEXT);
            et_name.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});

            et_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    et_psw.setText("");
                    String editable = et_name.getText().toString();
                    String regEx = "[^a-zA-Z0-9]";
                    //只能输入字母或数字和特殊字符
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(editable);
                    String str = m.replaceAll("").trim();    //删掉不是字母或数字的字符
                    if (!editable.equals(str)) {
                        et_name.setText(str);  //设置EditText的字符
                        et_name.setSelection(str.length()); //因为删除了字符，要重写设置新的光标所在位置
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        } else {
            spannableString = new SpannableString(PWD_STR);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#11A3FA")), PWD_STR.length() - 4, PWD_STR
                    .length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            et_name.setHint("请输入手机号码");
            et_name.setInputType(InputType.TYPE_CLASS_PHONE);
            et_name.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        }
        tv_eid_login.setText(spannableString);
    }


    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            if (what == GETLOGINUER) {
                if (!Constants.IS_CERTIFY) {
                    if (Constants.SUCCESS_CODE.equals(item.getString("code"))) {
                        ResultItem data = (ResultItem) item.get("DATA");
                        UserInfo.DATABean dataBean = new UserInfo.DATABean();
                        mUser.setDATA(dataBean);
                        mUser.getDATA().setUSERID(data.getString("USERID"));
                        mUser.getDATA().setUSERNAME(data.getString("USERNAME"));
                        mUser.setUserType(data.getString("USERTYPE"));
                        mUser.setAuthtoken(data.getString("TOKEN"));

                        isLogin = true;
                        LoginUtils.getInstance().setIsLoginSuccess(true);
                        //极光推送设置别名
                        setAlias();
                        //根据用户名和密码查找人员信息接口
                        invoke.invoke(OAInterface.syncIdcardUserInfo(data.getString("USERTYPE"),
                                mUser.getDATA().getUSERNAME(), et_psw.getText().toString().trim()), callBack,
                                GETLOGINUERINFO);
                        //标记自动登录
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putBoolean("is_login", true);
                        edit.putBoolean("userIsLogin", true);//是否登录
                        edit.commit();
                    } else {
                        DialogUtils.showToast(LoginActivity.this, item.getString("message"));
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
                        DialogUtils.showToast(LoginActivity.this, item.getString("message"));
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
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("userInfoBean", GsonUtil.GsonString(userInfo));
                    edit.commit();

                    gotoMain();
                } else {
                    DialogUtils.showToast(LoginActivity.this, item.getString("message"));
                }
            }
        }

        private Object mDialogObj;

        @Override
        public void finish(Object dialogObj, int what) {
            //处理登录成功会弹两次dialog的问题
            if (what == GETLOGINUER) {
                if (isLogin) {
                    this.mDialogObj = dialogObj;
                } else {
                    super.finish(dialogObj, what);
                }
            } else if (what == GETLOGINUERINFO) {
                if (mDialogObj != null) {
                    if (mDialogObj instanceof LoadingDialog) {
                        ((LoadingDialog) mDialogObj).dismiss();
                    }
                }
            }
        }
    };

    private void setAlias() {
        JPushInterface.setAlias(LoginActivity.this, 0, mUser.getDATA().getUSERID());
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
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("userName", name_encode);
        edit.putString("userPsw", psw_encode);
        edit.putString("userType", userType);
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

        private LoginActivity curAT = null;

        public EventHandler(LoginActivity curAT, Looper looper) {
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


    /**
     * 处理软键盘部分
     **/
    public void addLayoutListener() {
        kbLayout.setKeyboardListener(new KeyboardLayout.KeyboardLayoutListener() {
            @Override
            public void onKeyboardStateChanged(boolean isActive, int viewBottom) {
                Log.e("onKeyboardStateChanged", "isActive:" + isActive + " keyboardHeight:" + viewBottom);
                if (isActive) {
                    scrollToBottom(viewBottom);
                } else {
                    sv.smoothScrollTo(0, -sv.getScrollY());
//                    sv.post(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            sv.smoothScrollTo(0, -sv.getScrollY());
//                        }
//                    });
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        ViewTreeObserver vto = bt_login.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                //获取底部按钮的位置
                if (btBottomHight != -1) {
                    return true;
                }
                final int[] location = new int[2];
                bt_login.getLocationOnScreen(location);
                btBottomHight = location[1] + bt_login.getHeight() * 3 / 2;
                return true;
            }
        });
    }


    int btBottomHight = -1;

    /**
     * 弹出软键盘时将SVContainer滑到底
     */
    private void scrollToBottom(final int viewBottom) {
        int modifyH = btBottomHight - viewBottom;
        if (modifyH > 0)
            sv.smoothScrollTo(0, modifyH);
    }


}
