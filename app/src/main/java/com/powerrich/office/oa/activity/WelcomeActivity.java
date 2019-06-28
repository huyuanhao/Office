package com.powerrich.office.oa.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.AESUtil;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.GsonUtil;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.view.LoadingDialog;

import cn.jpush.android.api.JPushInterface;

/**
 * 欢迎界面
 *
 * @author Administrator
 */
public class WelcomeActivity extends BaseActivity {

    private static final int GETLOGINUER = 000;
    private static final int GETLOGINUERINFO = 111;
    private UserInfo mUser;
    boolean autoLoginBoo;
    SharedPreferences sp;
    private String userPsw_decode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断以前是否登录过
        sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        autoLoginBoo = sp.getBoolean("is_login", false);
        if (autoLoginBoo) {
            String userName = sp.getString("userName", "");
            String userPsw = sp.getString("userPsw", "");
            String userType = sp.getString("userType", "");
            if (!"".equals(userName) && !"".equals(userPsw)) {
                mUser = new UserInfo();
                String userName_decode;
                try {
                    userPsw_decode = AESUtil.decrypt("98563258", userPsw);
                } catch (Exception ex) {
                    userPsw_decode = "";
                }
                try {
                    userName_decode = AESUtil.decrypt("98563258", userName);
                } catch (Exception ex) {
                    userName_decode = "";

                }
                invoke.invoke(OAInterface.login(userType, userName_decode, userPsw_decode), callBack, GETLOGINUER);
            }
        }

        //设置3秒后跳转
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_welcome;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (getBoolean(getBaseContext(), "welcomeGuide", "isFirst")) {
                goHome();//主页
            } else {
                goGuide();//引导页
                putBoolean(getBaseContext(), "welcomeGuide", "isFirst", true);
            }
        }

    };

    /**
     * 跳转到登录界面
     */
    public void goHome() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
//		overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }

    /**
     * 跳转到引导界面
     */
    public void goGuide() {
        Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String msg = item.getString("message");
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
                                mUser.getDATA().getUSERNAME(), userPsw_decode), callBack,
                                GETLOGINUERINFO);

                    } else {
                        //清除登录信息
                        LoginUtils.clearLoginInfo(WelcomeActivity.this);
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
                    } else {
                        //清除登录信息
                        LoginUtils.clearLoginInfo(WelcomeActivity.this);
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
                    sp.edit().putString("userInfoBean", GsonUtil.GsonString(userInfo)).commit();
                    sp.edit().putBoolean("userIsLogin", true).commit();
                } else {
                    //清除登录信息
                    LoginUtils.clearLoginInfo(WelcomeActivity.this);
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

        @Override
        public void onReturnError(HttpResponse response, ResultItem error, int what) {
            LoginUtils.clearLoginInfo(WelcomeActivity.this);
        }

        @Override
        public void onNetError(int what) {
            LoginUtils.clearLoginInfo(WelcomeActivity.this);
        }
    };

    private void setAlias() {
        JPushInterface.setAlias(WelcomeActivity.this, 0, mUser.getDATA().getUSERID());
    }

    private boolean putBoolean(Context context, String fileName, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }


    private boolean getBoolean(Context context, String fileName, String key) {
        return getBoolean(context, fileName, key, false);
    }


    private boolean getBoolean(Context context, String fileName, String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }
}
