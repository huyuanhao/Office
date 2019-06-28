package com.powerrich.office.oa.tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.CheckResult;
import android.text.TextUtils;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.base.InvokeHelper;
import com.powerrich.office.oa.bean.VersionInfoBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.view.MyDialog;

import java.io.File;

/**
 * Created by MingPeng on 2017/10/9.
 */

public class CheckVersionTools {

    private static OnCheckSuccessListener mOnCheckSuccessListener;
    private static boolean mIsShowTipsDialog;//是否需要显示提示更新的dialog
    private static Context mContext;
    private static int DIALOG_VERSION = 1111;
    private static String TAG = CheckVersionTools.class.getSimpleName();
    private static MyDialog builder;

    private static boolean mCancelLocalLocationBoo = false;

    public interface OnCheckSuccessListener {
        void onCheckSuccess(VersionInfoBean info);
    }

    public static void checkVersion(Context context, Boolean isShowTipsDialog) {
        checkVersion(context, isShowTipsDialog, null);
    }


    public static void checkVersion(Context context, Boolean isShowTipsDialog, boolean cancelLocalLocationBoo,
                                    OnCheckSuccessListener listener) {
        mContext = context;
        mOnCheckSuccessListener = listener;
        mIsShowTipsDialog = isShowTipsDialog;
        mCancelLocalLocationBoo = cancelLocalLocationBoo;
        getVersion();
    }

    public static void checkVersion(Context context, Boolean isShowTipsDialog, OnCheckSuccessListener listener) {
        mContext = context;
        mOnCheckSuccessListener = listener;
        mIsShowTipsDialog = isShowTipsDialog;
        getVersion();
    }

    /**
     * 获取当前版本名称
     *
     * @param context
     * @return
     */
    @CheckResult
    public static String getLocalVersion(Context context) {
        int localVersionCode = -1;
        String localVersionName = "";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            localVersionCode = info.versionCode;
            localVersionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersionName;
    }

    /**
     * 获取服务器版本号
     */
    private static void getVersion() {
        ApiRequest request = OAInterface.getVersion();
        new InvokeHelper(mContext).invoke(request, callBack, DIALOG_VERSION);
    }

    private static IRequestCallBack callBack = new BaseRequestCallBack() {
        @Override
        public void process(HttpResponse response, int what) {
            if (DIALOG_VERSION == what) {
                ResultItem item = response.getResultItem(ResultItem.class);
                String code = item.getString("code");
                // 操作成功
                if (Constants.SUCCESS_CODE.equals(code)) {
                    ResultItem resultItem = (ResultItem) item.get("DATA");
//                    int versionCode = Integer.parseInt(resultItem.getString("VERCODE"));
//                    int localVersionCode = getLocalVersion(mContext);
                    if (null != resultItem) {
                        String versionName = resultItem.getString("VERCODE");
                        String localVersionName = getLocalVersion(mContext);
                        // 判断版本
                        if (localVersionName.compareTo(versionName) < 0) {
                            String apkSize = resultItem.getString("APKSIZE");
                            String content = resultItem.getString("CONTENT");
                            String downPath = resultItem.getString("DOWNPATH");
                            String fileName = resultItem.getString("FILENAME");
                            String HDFSFileName = resultItem.getString("HDFSFILENAME");
                            String isForce = resultItem.getString("ISFORCE"); //是否强制更新：0为是，1为否
                            String downUrl = resultItem.getString("URL");
                            VersionInfoBean bean = new VersionInfoBean();
                            bean.setAPKSIZE(apkSize);
                            bean.setCONTENT(content);
                            bean.setDOWNPATH(downPath);
                            bean.setFILENAME(fileName);
                            bean.setHDFSFILENAME(HDFSFileName);
                            bean.setVERCODE(versionName);
                            bean.setISFORCE(isForce);
                            bean.setURL(downUrl);
                            if (mIsShowTipsDialog) {
                                showTipsDialog(mContext, bean);
                            }
                            if (null != mOnCheckSuccessListener) {
                                mOnCheckSuccessListener.onCheckSuccess(bean);
                            }
                        } else {
                            if (mCancelLocalLocationBoo && null != mOnCheckSuccessListener) {
                                mOnCheckSuccessListener.onCheckSuccess(null);
                            }else{
                                mCancelLocalLocationBoo = false;
//                                LocationUtils.requestLocation(mContext, false, null);
                            }
                        }
                    }
                } else {
                    if (mCancelLocalLocationBoo && null != mOnCheckSuccessListener) {
                        mOnCheckSuccessListener.onCheckSuccess(null);
                    }else{
                        mCancelLocalLocationBoo = false;
//                        LocationUtils.requestLocation(mContext, false, null);
                    }
                }
            }
        }
    };

    /**
     * 提示更新的对话框
     *
     * @param context
     * @param bean
     */
    public static void showTipsDialog(final Context context, final VersionInfoBean bean) {
        if (builder != null && builder.isShowing()) {
            return;
        }
        builder = new MyDialog(context).builder();
        builder.setTitle("版本更新");
        builder.setMessage("更新内容：\n" + bean.getCONTENT() + "\n文件大小：" + bean.getAPKSIZE());
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                final String path;
                String status = Environment.getExternalStorageState();
                if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
                    path = Environment.getExternalStorageDirectory().getAbsolutePath();
                } else {
                    path = mContext.getCacheDir().getAbsolutePath();
                }

                // apk命名
                final String apkName = "iyingtan" + bean.getVERCODE() + ".apk";

                //判断已下载的文件是否存在，存在直接安装，不存在，则下载
                String filePath = path + "/" + apkName;
                File checkFile = new File(filePath);
                if (checkFile != null && checkFile.exists()) {
                    long fileLength = checkFile.length();
                    // 判断已下载的文件是否完整，完整直接安装，不完整，则下载
                    if (fileLength >= bean.getApkByteSize()) {
                        AndroidFileUtil.openFile(mContext, new File(filePath));
                        return;
                    }
                }
                ((BaseActivity) context).doPermissionRW("存储", new BaseActivity.PermissionCallBack() {
                    @Override
                    public void accept() {
                        if (bean.getISFORCE()) {
                            final ProgressDialog progressBar = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
                            progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progress_bar));
                            progressBar.setTitle(mContext.getString(R.string.downloading));
                            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progressBar.setCancelable(false);
                            progressBar.setCanceledOnTouchOutside(false);
                            progressBar.show();
                            UpdateThread updateTask = new UpdateThread(mContext, bean, path, apkName, progressBar);
                            updateTask.start();
                        } else {
                            NotificationUtil notificationUtil = new NotificationUtil(mContext);
                            notificationUtil.showNotification();
                            UpdateThread updateTask = new UpdateThread(mContext, bean, path, apkName, notificationUtil);
                            updateTask.start();

                        }
                    }
                }, bean.getISFORCE());

            }
        });
        //不是强制更新才能取消
        if (!bean.getISFORCE()) {
            builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
//                    LocationUtils.requestLocation(mContext, false, null);
                }
            });
        }
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 方法说明：<br>
     * 打开apk文件(进行安装apk程序)
     *
     * @param filePath 文件路径
     * @return
     */
    public static boolean fileIsExists(String filePath) {
        try {
            if (TextUtils.isEmpty(filePath)) {
                return false;
            }
            File file = new File(filePath);
            if (!file.isFile() || !file.exists()) {
                return false;
            }
            Logger.e(TAG, "---------- 执行升级安装，安装包路径：" + filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
