package com.powerrich.office.oa.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.SIMeID.SIMeIDApplication;
import com.powerrich.office.oa.activity.LoginActivity;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.view.MyDialog;
import com.yt.simpleframe.http.bean.xmlentity.CompanyInfo;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * 用户登陆工具类
 */
public class LoginUtils {

    private static final String TAG = LoginUtils.class.getSimpleName();
    /**
     * 登陆用户信息实例
     */
    private static UserInfo userInfo;

    /**
     * 当前类实例对象
     */
    private static LoginUtils instance;
    /**
     * 登陆是否成功
     */
    private static boolean loginSuccess;
    /**
     *
     * */
    private static boolean isGround;

    private LoginUtils() {

    }

    /**
     * 获取操作对象
     */
    public static LoginUtils getInstance() {
        if (null == instance) {
            instance = new LoginUtils();
        }
        return instance;
    }

    /**
     * 获取当前用户信息
     */
    public UserInfo getUserInfo() {
        if (null == userInfo) {
            userInfo = getLocalSharePreference();
        }
        if (null == userInfo) {
            userInfo = new UserInfo();
        }
        return userInfo;
    }

    public UserInfo getLocalSharePreference() {
        if (SIMeIDApplication.mContex != null) {
            SharedPreferences pref = SIMeIDApplication.mContex.getSharedPreferences("userInfo", Context
                    .MODE_PRIVATE);
            String gStr = pref.getString("userInfoBean", "");
            UserInfo info = GsonUtil.GsonToBean(gStr, UserInfo.class);
            return info;
        }
        return null;
    }


    /**
     * 将法人用户的企业转换为list
     */
    public List<CompanyInfo> getCompanyList() {
        List<CompanyInfo> list = new ArrayList<>();
        //判断COMPANYLIST != null
//        String companyListStr = LoginUtils.getInstance().getUserInfo().getDATA().getCOMPANYLIST();
//        if (!StringUtil.isEmpty(companyListStr)) {
//            try {
//                list = (List<CompanyInfo>) GsonUtil.GsonToList(companyListStr, list.getClass());
//            } catch (Exception e) {
//                e.printStackTrace();
//                list = new ArrayList<>();
//            }
//        }
        return list;
    }

    ;


    /**
     * 设置当前用户信息
     */
    public void setUserInfo(UserInfo info) {
        userInfo = info;
    }

    /**
     * 检查登陆账号和密码的正确性
     */
    public static boolean checkAcountAndPwd(String userNo, String pwd) {
        if (StringUtils.isNullOrEmpty(userNo) || StringUtils.isNullOrEmpty(pwd)) {
            return false;
        }
        String regex = "[\\d]{1,}";
        return userNo.trim().matches(regex);
    }

    /**
     * 保存当前用户信息
     */
    public void saveUserInfo(boolean isSavePwd) {
//		if (null != userInfo) {
//			String id = userInfo.getUserNo();
//			String pwd = userInfo.getPassword();
//			String userName = userInfo.getUserName();
//			id = Base64Utils.encode(id.getBytes());
//			if (isSavePwd) {
//				pwd = Base64Utils.encode(pwd.getBytes());
//			} else {
//				pwd = "";
//			}
//			SharePreferenceUtil pref = new SharePreferenceUtil();
//			pref.saveUserInfo(new UserInfo(id, pwd,userName));
//			LogUtils.d(TAG, "saveUserInfo: success: userName " + (id == null)
//					+ " pwd " + (null == pwd));
//		} else {
//			LogUtils.w(TAG, "saveUserInfo failed!!");
//		}
    }

    /**
     * 退出系统
     */
    public void logout() {
        if (null != userInfo) {
            userInfo = null;
        }
        loginSuccess = false;
    }

    /**
     * 清除用户信息
     */
    public void clearUserInfo(Context context) {
        SharedPreferences pref = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        pref.edit().clear().commit();
        logout();
    }

    /**
     * 检测是否自动登陆
     * */
//	public UserInfo checkAutoLogin() {
//		userInfo = new UserInfo();
//		SharePreferenceUtil pref = new SharePreferenceUtil();
//		UserInfo user = pref.getUserInfo();
//		String getUserNo() = user.getUserNo();
//		String pwd = user.getPassword();
//		String userName = user.userName;
//		if (!StringUtils.isNullOrEmpty(getUserNo())) {
//			userInfo.getUserNo() = new String(Base64Utils.decode(getUserNo()));
//		} else {
//			userInfo.getUserNo() = "";
//		}
//		if (!StringUtils.isNullOrEmpty(pwd)) {
//			userInfo.getPassword() = new String(Base64Utils.decode(pwd));
//		} else {
//			userInfo.getPassword() = "";
//		}
//		if (!StringUtils.isNullOrEmpty(userName)) {
//			userInfo.userName = userName;
//		} else {
//			userInfo.userName = "";
//		}		
//		return userInfo;
//	}

    /**
     * 是否需要自动登陆
     */
    public boolean isNeedAutoLogin() {
//		return null != userInfo && !StringUtils.isNullOrEmpty(userInfo.getUserNo())
//				&& !StringUtils.isNullOrEmpty(userInfo.getPassword());
        return false;
    }

    /**
     * 是否登陆成功
     */
    public boolean isLoginSuccess() {
        if (!loginSuccess) {
            if (SIMeIDApplication.mContex != null) {
                SharedPreferences pref = SIMeIDApplication.mContex.getApplicationContext().getSharedPreferences("userInfo",
                        Context
                        .MODE_PRIVATE);
                loginSuccess = pref.getBoolean("userIsLogin", false);
            }
        }
        return loginSuccess;
    }

    public void setIsLoginSuccess(boolean flag) {
        loginSuccess = flag;
    }

    public void setIsGround(boolean flag) {
        isGround = flag;
    }

    public boolean getIsGround() {
        return isGround;
    }

    public boolean isAppOnForeground(Context context, String packageName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return false;
        }
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }


    /**
     * 在进入界面之前，需要判断是否需要登录
     */
    public void checkNeedLogin(Activity context, boolean isDialog, String tipContent, int requestCode) {
        if (isDialog) {
            messageDialog(context, tipContent, requestCode);
        } else {
            gotoLogin(context, requestCode);
        }
        return;
    }

    /**
     * 登录弹框
     */
    private void messageDialog(final Activity context, String tips, final int requestCode) {
        MyDialog builder = new MyDialog(context).builder();
        builder.setMessage(tips);
        builder.setTitle(context.getString(R.string.system_exit_prompt));
        builder.setCancelable(true);
        builder.setPositiveButton(context.getString(R.string.goto_login), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                gotoLogin(context, requestCode);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 前往登录界面
     */
    private void gotoLogin(Activity context, int requestCode) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("requestCode", requestCode);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 清除登录信息
     *
     * @param context
     */
    public static void clearLoginInfo(Context context) {
        BaseActivity.isLogin = false;
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("is_login", false);//自动登录
        edit.putString("userInfoBean", "");
        edit.putBoolean("userIsLogin", false);
        edit.commit();
        JPushInterface.deleteAlias(context, 0);
    }

}
