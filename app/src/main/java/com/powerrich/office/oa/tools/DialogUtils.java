package com.powerrich.office.oa.tools;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.EnterpriseDeclareActivity;
import com.powerrich.office.oa.activity.MainActivity;
import com.powerrich.office.oa.activity.OnlineDeclareActivity;
import com.powerrich.office.oa.activity.RealNameWithSMSActivity;
import com.powerrich.office.oa.activity.RegisterActivity;
import com.powerrich.office.oa.activity.VerifyIDActivity;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.view.MyDialog;

public class DialogUtils {

    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    /**
     * Toast提示
     *
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg) {
//        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (msg.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = msg;
                toast.setText(msg);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    /**
     * Toast提示
     *
     * @param context
     * @param msgId
     */
    public static void showToast(Context context, int msgId) {
        showToast(context, String.valueOf(msgId));
    }


    /**
     * 得到自定义的progressDialog
     * @param context
     * @param msg
     * @return
     */
   /* public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context,R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        //loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;
    }*/


    /**
     * Dialog
     *
     * @param context
     * @param iconId
     * @param title
     * @param message
     * @param positiveBtnName
     * @param negativeBtnName
     * @param positiveBtnListener
     * @param negativeBtnListener
     * @return
     */
    public static MyDialog createConfirmDialog(Context context, int iconId, String title, String message,
                                             String positiveBtnName, String negativeBtnName,
                                             android.content.DialogInterface.OnClickListener positiveBtnListener,
                                             android.content.DialogInterface.OnClickListener negativeBtnListener) {
        MyDialog builder = new MyDialog(context).builder();
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveBtnName, positiveBtnListener);
        builder.setNegativeButton(negativeBtnName, negativeBtnListener);
        return builder;
    }

    /**
     * @param activity
     * @param type     0个人实名认证 1企业实名认证
     * @param userInfo 注册界面传过来的用户信息 已登录状态传null即可
     */
    public static void createAuthDialog(final Activity activity, final int type, final UserInfo.DATABean userInfo) {
        if (type == 1) {
            MyDialog builder = new MyDialog(activity).builder();
            builder.setMessage(activity.getResources().getString(R.string.sure_auth));
            builder.setCancelable(false);
            builder.setPositiveButton(activity.getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent();
                    //跳转到企业实名认证界面
//                    intent.setClass(activity, EnterpriseAuthActivity.class);
                    intent.setClass(activity, MainActivity.class);
                    if (null != userInfo) {
                        intent.putExtra("userInfo", userInfo);
                        intent.putExtra("isFromRegister", true);
                    }
                    activity.startActivity(intent);
                    //如果从注册界面跳转过来 则关闭注册界面
                    if (activity.getClass().getName().equals(RegisterActivity.class.getName())) {
                        activity.finish();
                    } else if (activity.getClass().getName().equals(OnlineDeclareActivity.class.getName()) || activity.getClass().getName().equals(EnterpriseDeclareActivity.class.getName())) {
                        activity.finish();
                    }
                }
            });
            builder.setNegativeButton(activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //如果从注册界面跳转过来 则关闭注册界面
                    if (activity.getClass().getName().equals(RegisterActivity.class.getName())) {
                        activity.finish();
                    } else if (activity.getClass().getName().equals(OnlineDeclareActivity.class.getName()) || activity.getClass().getName().equals(EnterpriseDeclareActivity.class.getName())) {
                        activity.finish();
                    }
                }
            });
            builder.show();
        } else if (type == 0) {
            showSingleChoiceDialog(activity, userInfo);
        }
    }

    public static void showSingleChoiceDialog(final Activity activity, final UserInfo.DATABean userInfo) {
        final String[] items = {"人工认证", "eid认证"};
        final int[] yourChoice = {0};
        MyDialog singleChoiceDialog = new MyDialog(activity).builder();
        singleChoiceDialog.setTitle("请选择认证方式");
        singleChoiceDialog.setCancelable(false);
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice[0] = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        if (yourChoice[0] == 0) {
                            //人工认证
                            intent.setClass(activity, VerifyIDActivity.class);
                        } else if (yourChoice[0] == 1) {
                            //eid认证
                            intent.setClass(activity, RealNameWithSMSActivity.class);
                        }
                        if (null != userInfo) {
                            intent.putExtra("userInfo", userInfo);
                            intent.putExtra("isFromRegister", true);
                        }
                        activity.startActivity(intent);
                        //如果从注册界面跳转过来 则关闭注册界面
                        if (activity.getClass().getName().equals(RegisterActivity.class.getName())) {
                            activity.finish();
                        } else if (activity.getClass().getName().equals(OnlineDeclareActivity.class.getName()) || activity.getClass().getName().equals(EnterpriseDeclareActivity.class.getName())) {
                            activity.finish();
                        }
                    }
                });
        singleChoiceDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (activity.getClass().getName().equals(RegisterActivity.class.getName())) {
                            activity.finish();
                        } else if (activity.getClass().getName().equals(OnlineDeclareActivity.class.getName()) || activity.getClass().getName().equals(EnterpriseDeclareActivity.class.getName())) {
                            activity.finish();
                        }
                    }
                });
        singleChoiceDialog.show();
    }

    /**
     * Dialog
     *
     * @param context
     * @param title
     * @param message
     * @param positiveBtnName
     * @param positiveBtnListener
     * @return
     */
    public static MyDialog createConfirmDialog(Context context, String title, String message, String positiveBtnName,
                                             android.content.DialogInterface.OnClickListener positiveBtnListener) {
        MyDialog builder = new MyDialog(context).builder();
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveBtnName, positiveBtnListener);
        return builder;
    }

    /**
     * 弹PopupWindow对话框
     * @param context
     * @param layoutView
     * @param view
     * @param popWidth
     * @param popHeight
     * @return
     */
    public static PopupWindow createPopWindow(Context context, View layoutView, View view, int popWidth, int popHeight) {
        PopupWindow pop = new PopupWindow(layoutView);
        pop.setWidth(popWidth);
        pop.setHeight(popHeight);
        pop.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.gray_bottom_corners_bg));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        pop.showAsDropDown(view);
        return pop;
    }

}
