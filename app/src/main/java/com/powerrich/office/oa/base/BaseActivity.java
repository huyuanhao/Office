package com.powerrich.office.oa.base;

import android.Manifest;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.powerrich.office.oa.AppManager;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.SIMeID.SIMeIDApplication;
import com.powerrich.office.oa.activity.LoginActivity;
import com.powerrich.office.oa.bean.FilterClass;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.tools.AutoUtils;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.PermissionPageUtils;
import com.powerrich.office.oa.tools.StatusBarUtils;
import com.powerrich.office.oa.tools.StringUtils;
import com.powerrich.office.oa.view.LoadingDialog;
import com.powerrich.office.oa.view.MyDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


/**
 * @author Administrator
 */
public abstract class BaseActivity extends FragmentActivity {

    /**
     * ProfileActivity 中的RequestCode
     */
    public static final int TO_SELECT_PHOTO = 1; // 选择图片文件
    public static final int TO_SELECT_LOCALPHOTO = 13; // 选择本地相册图片
    public static final int TO_SELECT_GETPHOTO = 14; // 拍照显示图片
    public static final int TO_SELECT_UPDNICKNAME = 2; // 昵称
    public static final int TO_SELECT_UPDGENDER = 3; // 性别
    public static final int TO_SELECT_UPDAGE = 4; // age
    public static final int TO_SELECT_PLACE = 5; // 地区
    public static final int TO_SELECT_HEALTHSTATUS = 6; // 健康状况
    public static final int TO_SELECT_TAG = 7; // 咨询

    public static final int DIALOG_ERROR = 11111;
    public static final int DIALOG_POWER = 22222;
    protected String errorMessage;
    public ImageView backBtn;
    public ImageView rightBtn;
    public ImageView customBtn;
    public TextView titleTxt;
    public TextView rightTxt;
    public InvokeHelper invoke;

    //是否要使用px 转化的这个过程  默认是需要的
    protected boolean isAutoFlag =true;

    /**
     * 过滤需要登录的界面
     */
    public static final ArrayList<FilterClass> filterActivityList = new ArrayList<>();
    public static boolean isLogin;

    static {
        filterActivityList.add(new FilterClass("com.powerrich.office.oa.activity.IWantSuggestActivity", false, StringUtils
                .getResId("suggest_tips", R.string.class)));
//		filterActivityList.add(new FilterClass("com.powerrich.office.oa.activity.OnlineBookingActivity", false, StringUtils
// .getResId("booking_tips", R.string.class)));
        filterActivityList.add(new FilterClass("com.powerrich.office.oa.activity.QueryHandingActivity", true, -1));
        filterActivityList.add(new FilterClass("com.powerrich.office.oa.activity.OnlineDeclareActivity", true, -1));
        filterActivityList.add(new FilterClass("com.powerrich.office.oa.activity.EnterpriseDeclareActivity", true, -1));
//		filterActivityList.add(new FilterClass("com.powerrich.office.oa.activity.MyWorkActivity", true, -1));
        filterActivityList.add(new FilterClass("com.powerrich.office.oa.activity.IWantListActivity", true, -1));
//		filterActivityList.add(new FilterClass("com.powerrich.office.oa.activity.OnlineBookingActivity", false, StringUtils
// .getResId("booking_tips", R.string.class)));
//		filterActivityList.add(new FilterClass("com.powerrich.office.oa.activity.ConsultingActivity", false, StringUtils
// .getResId("address", R.string.class)));
        filterActivityList.add(new FilterClass("com.powerrich.office.oa.activity.AddressListActivity", true, -1));
        filterActivityList.add(new FilterClass("com.powerrich.office.oa.activity.PaymentListActivity", true, -1));
        filterActivityList.add(new FilterClass("com.powerrich.office.oa.activity.LogisticsListActivity", true, -1));
        filterActivityList.add(new FilterClass("com.powerrich.office.oa.activity.AppointmentListActivity", true, -1));
        filterActivityList.add(new FilterClass("com.powerrich.office.oa.activity.CollectListActivity", true, -1));
        filterActivityList.add(new FilterClass("com.powerrich.office.oa.activity.LeaderEmailAddActivity", false, StringUtils
                .getResId("suggest_tips", R.string.class)));
    }

    private MyDialog progressDialog;

    private MyDialog dialog;

    public static Context mContext;

    private boolean isDebug=true;
      protected  void  i(String content){
          if(isDebug){
              Log.i("jsc", this+""+content);
          }

      }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        i("类名："+this);
        //设置设计稿尺寸
//		AutoUtils.setSize(this, false, 1242, 2208);
        setSize();
        //子类不再需要设置布局ID
        int id = provideContentViewId();
        if (id != -1) {
            setContentView(provideContentViewId());
        }
        if(isAutoFlag){
            AutoUtils.auto(this);
        }

        StatusBarUtils.initWindows(this, R.color.blue_main);

        invoke = new InvokeHelper(this);
        if (!isLogin) {
            checkNeedLogin();
        }
        AppManager.getAppManager().addActivity(this);
    }

    protected void setSize() {
        AutoUtils.setSize(this, false, 1242, 2208);
    }

    /**
     * 得到当前界面的布局文件id(由子类实现)
     */
    protected abstract int provideContentViewId();



    /**
     * 在进入界面之前，需要判断是否需要登录
     */
    private void checkNeedLogin() {
        String targetName = this.getClass().getName();
        if (BeanUtils.isEmpty(LoginUtils.getInstance().getUserInfo().getAuthtoken())) {
            for (FilterClass filter : filterActivityList) {
                if (filter.filterClassName.equals(targetName)) {
                    if (filter.isForceLogin) {
                        gotoLogin();
                    } else {
                        messageDialog(getString(filter.tipContent));
                    }
                    return;
                }
            }
        }
    }

    /**
     * 前往登录界面
     */
    private void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("targetName", getClass().getName());
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * 验证返回的数据是否正常
     *
     * @return
     * @ param resultItems
     */
    @SuppressWarnings("deprecation")
    public boolean checkResult(ResultItem item) {
        try {
            if (!BeanUtils.isEmpty(item)) {
                String code = item.getString("code");
                if (!Constants.SUCCESS_CODE.equals(code)) {
                    errorMessage = item.getString("message");
                    // 令牌失效
                    if (Constants.LOGIN_FAIL_CODE.equals(code)) {
                        errorMessage = "您的账号在其它地方已登录，请重新登录！";
                        showDialog(DIALOG_POWER);
                    } else {
                        showDialog(DIALOG_ERROR);
                    }
                    return false;
                }
            } else {
                errorMessage = getString(R.string.system_request_error_message);
                showDialog(DIALOG_ERROR);
                return false;
            }
        } catch (Exception e) {

        }
        return true;
    }

    /**
     * 初始化title
     */
    public void initTitleBar(int titleRes, OnClickListener backListener,
                             OnClickListener rightListener) {
        backBtn = (ImageView) findViewById(R.id.system_back);
        rightBtn = (ImageView) findViewById(R.id.btn_top_right);
        customBtn = (ImageView) findViewById(R.id.btn_custom_right);
        titleTxt = (TextView) findViewById(R.id.tv_top_title);
        if (titleRes > 0) {
            titleTxt.setText(titleRes);
        }
        if (null != backListener) {
            backBtn.setOnClickListener(backListener);
            backBtn.setVisibility(View.VISIBLE);
        }
        if (null != rightListener) {
            rightBtn.setOnClickListener(rightListener);
            rightBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置自定义的返回按钮
     */
    public void setBackBtn(int res, OnClickListener listener) {
        if (res > 0) {
            backBtn.setBackgroundResource(res);
        }
        backBtn.setOnClickListener(listener);
        backBtn.setVisibility(View.VISIBLE);
    }

    /**
     * 设置自定义的返回按钮
     */
    public void setRightBtn(int res, OnClickListener listener) {
        rightBtn.setImageResource(res);
        rightBtn.setOnClickListener(listener);
        rightBtn.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化title
     */
    public void initTitleBar(String titleRes, OnClickListener backListener,
                             OnClickListener rightListener) {
        backBtn = (ImageView) findViewById(R.id.system_back);
        rightBtn = (ImageView) findViewById(R.id.btn_top_right);
        customBtn = (ImageView) findViewById(R.id.btn_custom_right);
        titleTxt = (TextView) findViewById(R.id.tv_top_title);

        if (!StringUtils.isNullOrEmpty(titleRes)) {
            titleTxt.setText(titleRes);
        }
        if (null != backListener) {
            backBtn.setOnClickListener(backListener);
            backBtn.setVisibility(View.VISIBLE);
        }
        if (null != rightListener) {
            rightBtn.setOnClickListener(rightListener);
            rightBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化title
     */
    public void initTitleBar(String titleRes, String rightStr, OnClickListener backListener,
                             OnClickListener rightListener) {
        backBtn = (ImageView) findViewById(R.id.system_back);
        rightBtn = (ImageView) findViewById(R.id.btn_top_right);
        customBtn = (ImageView) findViewById(R.id.btn_custom_right);
        titleTxt = (TextView) findViewById(R.id.tv_top_title);
        rightTxt = (TextView) findViewById(R.id.tv_top_right);
        if (!StringUtils.isNullOrEmpty(titleRes)) {
            titleTxt.setText(titleRes);
        }
        if (null != backListener) {
            backBtn.setOnClickListener(backListener);
            backBtn.setVisibility(View.VISIBLE);
        }
        if (null != rightListener) {
            rightTxt.setText(rightStr);
            rightTxt.setOnClickListener(rightListener);
            rightTxt.setVisibility(View.VISIBLE);
        }
    }


    public void setCustom(OnClickListener listener, int imgRes) {
        customBtn.setOnClickListener(listener);
        if (imgRes > 0) {
            customBtn.setImageResource(imgRes);
        }
        customBtn.setVisibility(View.VISIBLE);
    }

    public void setMainNavigator() {
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            }

        });
        rightBtn.setImageResource(R.drawable.white_icon_search);
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            }

        });
    }

    public void showErrorMessage(String message) {
        errorMessage = message;
        showDialog(DIALOG_ERROR);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_POWER:
                return new Builder(BaseActivity.this)
                        .setTitle(R.string.app_name)
                        .setMessage(errorMessage)
                        .setPositiveButton(R.string.system_dialog_confirm,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
//									 LoginUtils.getInstance().logout();
//									 OADroid.getInstance().onTerminate();
//									 MainActivity.getInstance().finish();
//									 Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
//									 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//									 startActivity(intent);
//									 finish();
                                    }
                                }).create();
            case DIALOG_ERROR:
                Toast.makeText(BaseActivity.this, errorMessage, Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }
        return super.onCreateDialog(id);
    }

    /**
     * 检测网络是否连接
     *
     * @return
     */
    public boolean checkNetworkState() {
        boolean flag = false;
        //得到网络连接信息  
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接  
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        if (!flag) {
            errorMessage = "网络不给力，请检查网络设置";
            showDialog(DIALOG_ERROR);
        }

        return flag;
    }

    /**
     * 检测网络是否连接,不吐司
     *
     * @return
     */
    public boolean isNetworkEnable() {
        boolean flag = false;
        //得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }

    public boolean fileIsExists(String fileName) {
        try {
            File f = new File("/mnt/sdcard/Download/" + fileName);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String getFileSize(String fileName) {
        String path = Environment.getExternalStorageDirectory().getPath() + "/Download/";
//		File f = new File("/mnt/sdcard/Download/" + fileName);
        File f = new File(path + fileName);
        if (f.exists()) {
            return (f.length() / 1024) + "KB";
        }
        return (f.length() / 1024) + "KB";
    }

    /**
     * 删除弹框
     */
    private void messageDialog(String tips) {
        MyDialog builder = new MyDialog(this).builder();
        builder.setMessage(tips);
        builder.setTitle(getString(R.string.system_exit_prompt));
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.goto_login), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                gotoLogin();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    protected void showProgressDlg(String tip, String message) {

        if (null != progressDialog && progressDialog.isShowing()) {

            progressDialog.dismiss();
            progressDialog = null;

        }

        progressDialog = MyDialog.showWaitDialog(this, tip, message);
//        progressDialog.setTitle(tip);
//        progressDialog.setMessage(message);
//        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();

    }

    protected void showProgressDlg(String message) {

        showProgressDlg(getResources().getString(R.string.tip), message);

    }

    protected void hideProgressDlg() {

        if (isInProgress()) {

//            progressDialog.hide();
            progressDialog.dismiss();
            progressDialog = null;

        }

    }

    protected boolean isInProgress() {

        return (null != progressDialog && progressDialog.isShowing());

    }

    @Override
    protected void onPause() {
        if (null != progressDialog) {
            progressDialog.dismiss();
            progressDialog = null;

        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (null != progressDialog) {
            progressDialog.dismiss();
            progressDialog = null;

        }
        super.onDestroy();
    }

    /**
     * 提示开启权限
     */
    public void showTipDialog(String message) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new MyDialog(BaseActivity.this).builder()
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
                        PermissionPageUtils.getInstance(BaseActivity.this).jumpPermissionPage();
                    }
                });
        dialog.show();
    }

    public void showTipDialog(String message, final boolean isFinish) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new MyDialog(BaseActivity.this).builder()
                .setTitle("权限通知")
                .setMessage(message)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (isFinish) {
                            finish();
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        PermissionPageUtils.getInstance(BaseActivity.this).jumpPermissionPage();
                        if (isFinish) {
                            finish();
                        }
                    }
                });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * 快速双击时间控制
     */
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 300) {
            if (SIMeIDApplication.mContex != null) {
                DialogUtils.showToast(SIMeIDApplication.mContex, "请慢一点！");
                return true;
            }
        }
        lastClickTime = time;
        return false;
    }

    public interface PermissionCallBack {
        void accept();
    }

    public void doPermission(final String msg, final PermissionCallBack callBack, String... permissions) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            callBack.accept();
                        } else {
                            showTipDialog("没有 " + msg + " 权限，请打开相关权限");
                        }
                    }
                });
    }

    public void doPermissionRW(final String msg, final PermissionCallBack callBack) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            callBack.accept();
                        } else {
                            showTipDialog("没有 " + msg + " 权限，请打开相关权限");
                        }
                    }
                });
    }

    public void doPermissionRW(final String msg, final PermissionCallBack callBack, final boolean isFinish) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            callBack.accept();
                        } else {
                            showTipDialog("没有 " + msg + " 权限，请打开相关权限", isFinish);
                        }
                    }
                });
    }

    public void doPermissionRWF(final String msg, final PermissionCallBack callBack) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            callBack.accept();
                        } else {
                            showTipDialog("没有 " + msg + " 权限，请打开相关权限");
                        }
                    }
                });
    }

    public <T> ObservableTransformer<T, T> loadingDialog() {
        final LoadingDialog dialog = new LoadingDialog(this);
        dialog.show();
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        dialog.dismiss();
                    }
                });
            }
        };
    }

    protected  <T extends View> T generateFindViewById(int id) {
        return (T) findViewById(id);
    }

    @Override
    public void finish() {
        super.finish();
        AppManager.getAppManager().popActivity(this);
    }
}
