package com.powerrich.office.oa.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;

import com.powerrich.office.oa.AppManager;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.view.MyDialog;

import java.io.File;

/**
 * 文 件 名：NotificationUtil
 * 描    述：
 * 作    者：chenhao
 * 时    间：2018/8/20
 * 版    权：v1.0
 */
public class NotificationUtil {
    private Context mContext;
    private String mTitleText;
    // NotificationManager ： 是状态栏通知的管理类，负责发通知、清楚通知等。
    private NotificationManager manager;

    private Notification.Builder builder;
    private Notification notification;
    private int mProgress = -100;

    public NotificationUtil(Context context) {
        this.mContext = context;
        mTitleText = context.getResources().getString(R.string.app_name);
        // NotificationManager 是一个系统Service，必须通过 getSystemService()方法来获取。
        manager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //创建 通知通道  channelid和channelname是必须的（自己命名就好）
            NotificationChannel channel = new NotificationChannel("1",
                    "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
            manager.createNotificationChannel(channel);
            // 创建通知对象
            builder = new Notification.Builder(mContext, "1");
//            builder.setContentTitle(mTitleText + " 正在更新...") //设置通知标题
//                    .setSmallIcon(R.drawable.ic_launcher) //设置通知的大图标
//                    .setDefaults(Notification.DEFAULT_LIGHTS) //设置通知的提醒方式： 呼吸灯
//                    .setPriority(Notification.PRIORITY_MAX) //设置通知的优先级：最大
//                    .setAutoCancel(false)//设置通知被点击一次是否自动取消
//                    .setContentText("下载进度:" + "0%")
//                    .setProgress(100, 0, false)
//                    .setAutoCancel(false);//设置通知被点击一次是否自动取消
//            // 发出通知
//            notification = builder.build();
        } else {
            // 创建通知对象
            builder = new Notification.Builder(mContext);

        }
        builder.setContentTitle(mTitleText + " 正在更新...") //设置通知标题
                .setSmallIcon(R.drawable.ic_launcher) //设置通知的大图标
                .setDefaults(Notification.DEFAULT_LIGHTS) //设置通知的提醒方式： 呼吸灯
                .setPriority(Notification.PRIORITY_MAX) //设置通知的优先级：最大
                .setAutoCancel(false)//设置通知被点击一次是否自动取消
                .setContentText("下载进度:" + "0%")
                .setProgress(100, 0, false);
        // 发出通知
        notification = builder.build();
        //打开通知
        manager.notify(0, notification);
    }

    /**
     * 取消通知操作
     */
    public void cancel() {
        if (manager != null) {
            manager.cancel(0);
        }
    }

    /**
     * 更新进度条
     */
    public void updateProgress(int progress) {
        if (mProgress == progress) return;//防止发送通知过多而阻塞线程
        if (builder != null) {
            // 修改进度条
            builder.setProgress(100, progress, false);
            int i = progress > 100 ? 100 : progress;
            builder.setContentText("下载进度:" + i + "%");
            notification = builder.build();
            manager.notify(0, notification);
            mProgress = progress;
        }
    }

    /**
     * 完成下载
     */
    public void finish(final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (builder != null) {
                    builder.setContentTitle(mTitleText + " 下载完成")
                            .setContentText("点击安装")
                            .setAutoCancel(true);//设置通知被点击一次是否自动取消
                    installProcess(new File(filePath));
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showTipDialog("安装应用需要打开未知来源权限，请去设置中开启权限");
        }
    };
    //安装应用的流程
    @SuppressLint("CheckResult")
    private void installProcess(final File apk) {
        installApkIntent(apk);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先获取是否有安装未知来源应用的权限
            boolean haveInstallPermission = mContext.getPackageManager().canRequestPackageInstalls();
            if (haveInstallPermission) {//没有权限
                //有权限，开始安装应用程序
                if (apk.exists()) {
                    AndroidFileUtil.openFile(mContext,apk);
                }
            } else {
                mHandler.sendEmptyMessage(0);
            }
        } else {
            if (apk.exists()) {
                AndroidFileUtil.openFile(mContext,apk);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        Uri packageURI = Uri.parse("package:" + mContext.getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        ((Activity) mContext).startActivityForResult(intent, 10086);
    }

    //安装应用
    private void installApkIntent(File apk) {
        //点击安装代码块
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //获取文件file的MIME类型
        String type = AndroidFileUtil.getMIMEType(apk);
        //设置intent的data和Type属性。android 7.0以上crash,改用provider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri fileUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", apk);//android 7.0以上
            intent.setDataAndType(fileUri, type);
            AndroidFileUtil.grantUriPermission(mContext, fileUri, intent);
        } else {
            intent.setDataAndType(Uri.fromFile(apk), type);
        }
        PendingIntent pi = PendingIntent.getActivity(mContext, 0, intent, 0);
        notification = builder.setContentIntent(pi).build();
        manager.notify(0, notification);
    }

    private MyDialog dialog;
    public void showTipDialog(String message) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new MyDialog(AppManager.getAppManager().currentActivity()).builder()
                .setTitle("权限通知")
                .setMessage(message)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startInstallPermissionSettingActivity();
                        }
                    }
                });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

}
