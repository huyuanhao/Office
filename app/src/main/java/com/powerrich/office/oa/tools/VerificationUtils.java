package com.powerrich.office.oa.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.LoginActivity;
import com.powerrich.office.oa.activity.mine.ChooseIdentifyTypeActivity;
import com.powerrich.office.oa.activity.mine.Identify5Activity;
import com.powerrich.office.oa.view.CustomDialog;
import com.powerrich.office.oa.view.MyDialog;

public class VerificationUtils {
    public static boolean all(Context context){
        if(!isLogin(context)){
            return false;
        }
//        if(!isUserInfo(context)){
////            return false;
////        }
        return isAuthentication(context);
    }
    //是否登陆
    public static boolean isLogin(Context context){
        if(!LoginUtils.getInstance().isLoginSuccess()){
            messageDialog(context,"");
            return false;
        }
        return true;
    }
//    //是否完善用户信息
//    public static boolean isUserInfo(Context context){
//        if(LoginUtils.getInstance().getUserInfo().getDATA() == null ||
//                LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME() == null ||
//                TextUtils.isEmpty(LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME())){
//            context.startActivity(new Intent(context, PerfectInfomationActivity.class));
//            return false;
//        }
//        return true;
//    }

    /**
     * 登录弹框
     */
    private static void messageDialog(final Context context, String tips) {
        MyDialog builder = new MyDialog(context).builder();
        if(TextUtils.isEmpty(tips)){
            tips = "该功能需要登录后才可使用";
        }
        builder.setMessage(tips);
        builder.setTitle(context.getString(R.string.system_exit_prompt));
        builder.setCancelable(true);
        builder.setPositiveButton(context.getString(R.string.goto_login), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(context, LoginActivity.class));
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
     * 判断是否实名认证
     * @return
     */
    public static boolean isAuthentication(Context context) {
        if (null != LoginUtils.getInstance().getUserInfo().getDATA()) {
            if ("0".equals(LoginUtils.getInstance().getUserInfo().getDATA().getSFSMRZ())) {
                if ("0".equals(LoginUtils.getInstance().getUserInfo().getDATA().getAUDIT_STATE())) {
                    DialogUtils.showToast(context, "您的账号正在认证中！");
                } else {
                    dialog(context,LoginUtils.getInstance().getUserInfo().getUserType());
                }
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否实名认证
     * @return
     */
    public static boolean isAuthenticationCc(Context context) {
        if (null != LoginUtils.getInstance().getUserInfo().getDATA()) {
            if ("0".equals(LoginUtils.getInstance().getUserInfo().getDATA().getSFSMRZ())) {
                if ("0".equals(LoginUtils.getInstance().getUserInfo().getDATA().getAUDIT_STATE())) {

                } else {

                }
                return false;
            }
        }
        return true;
    }

    public static void dialog(final Context context, final String userType) {
        final CustomDialog customDialog = new CustomDialog(context);
        customDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (customDialog.isShowing()) {
                        customDialog.dismiss();
                    }
                }
                return false;
            }
        });
        customDialog.setCancelable(false);
        customDialog.show();
        customDialog.setClickListener(new CustomDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                customDialog.dismiss();
                gotoActivity(context,Identify5Activity.class);//直接跳转到认证界面
            }

            @Override
            public void doCancel() {
                customDialog.dismiss();
            }
        });
    }

    private static void gotoActivity(Context context,Class c) {
        Intent intent = new Intent(context, c);
        intent.putExtra("phoneNumber", LoginUtils.getInstance().getUserInfo().getDATA().getMOBILE_NUM());
        intent.putExtra("userId",  LoginUtils.getInstance().getUserInfo().getDATA().getUSERID());
        context.startActivity(intent);
    }
}
