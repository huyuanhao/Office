package com.powerrich.office.oa.SIMeID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.imnjh.imagepicker.ImageLoader;
import com.imnjh.imagepicker.PickerConfig;
import com.imnjh.imagepicker.SImagePicker;
import com.powerrich.office.oa.BuildConfig;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.LoginActivity;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.yt.simpleframe.http.exception.ExceptionHandle;
import com.yt.simpleframe.notification.NotificationCenter;
import com.yt.simpleframe.notification.NotificationListener;
import com.yt.simpleframe.utils.LogUtil;
import com.yt.simpleframe.utils.TimerUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Sober on 2017/10/16.
 */

public class SIMeIDApplication extends MultiDexApplication implements NotificationListener {
    public static final String base_url = "http://218.87.176.156:80/";
    public boolean boo = true;//多个请回错误处理，只允许一次 的开关值
    public static Context mContex;
    private static Gson mGson;

    public static Gson getmGson() {
        return mGson;
    }

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @SuppressLint("ResourceAsColor")
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, android.R.color.black);//全局设置主题颜色
//                mArrowImageView.setImageResource(R.drawable.ic_loading_default_rotate);
                ClassicsHeader head = new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));
                // 指定为经典Header，默认是 贝塞尔雷达Header
                /*head.setFinishDuration(10);
                head.setProgressResource(R.drawable.ic_loading_default_rotate);
                head.setArrowResource(R.drawable.ic_loading_default_rotate);*/
                return head;
            }
        });

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @SuppressLint("ResourceAsColor")
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter footer = new ClassicsFooter(context).setDrawableSize(20);
                footer.setArrowResource(R.color.transparent);
//                footer.setArrowResource(R.drawable.ic_loading_default_rotate);
//                footer.setProgressResource(R.drawable.ic_loading_default_rotate);

                return footer;
            }
        });
    }


    private static final String TAG = SIMeIDApplication.class.getName();

    private Object attached = null;

    private SharedPreferences pref;
    private static final String LOCAL_DATA = TAG + ".localData";

    @Override
    public void onCreate() {
        super.onCreate();
        mContex = this;
        if(!BuildConfig.DEBUG){
            //bugly初始化
            CrashReport.initCrashReport(getApplicationContext(), "3ea06c0cc8", false);
        }

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        pref = getApplicationContext().getSharedPreferences(LOCAL_DATA, Context.MODE_PRIVATE);
        //注册错误处理
        NotificationCenter.defaultCenter.addListener("exception", this);
        NotificationCenter.defaultCenter.addListener("error_code", this);

        if (mGson == null) {
            mGson = new Gson();
        }

        //图片选择器图片加载
        SImagePicker.init(new PickerConfig.Builder().setAppContext(mContex)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void bindImage(ImageView imageView, Uri uri, int width, int height) {
                        Glide.with(mContex).load(uri).placeholder(R.drawable.ic_default)
                                .error(R.mipmap.ic_launcher).override(width, height).dontAnimate().into(imageView);
                    }

                    @Override
                    public void bindImage(ImageView imageView, Uri uri) {
                        Glide.with(mContex).load(uri).placeholder(R.drawable.ic_default)
                                .error(R.mipmap.ic_launcher).dontAnimate().into(imageView);
                    }

                    @Override
                    public ImageView createImageView(Context context) {
                        ImageView imageView = new ImageView(context);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        return imageView;
                    }

                    @Override
                    public ImageView createFakeImageView(Context context) {
                        return new ImageView(context);
                    }
                })
                .setToolbaseColor(ContextCompat.getColor(this,R.color.colorPrimary))
                .build());
        MultiDex.install(this);//解决64K限制
        initX5();//腾讯X5WebView初始化
    }

    private void initX5() {
        //x5内核初始化接口
        QbSdk.initX5Environment(this,  cb);
        // 在调用TBS初始化、创建WebView之前进行如下配置，以开启优化方案
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
//        QbSdk.initTbsSettings(map);
    }
    //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

        @Override
        public void onViewInitFinished(boolean arg0) {
            // TODO Auto-generated method stub
            //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            Log.d("app", " onViewInitFinished is " + arg0);
        }

        @Override
        public void onCoreInitFinished() {
            // TODO Auto-generated method stub
        }
    };

    public void setReaderAttached(Object attached) {

        this.attached = attached;

    }

    public Object getReaderAttached() {

        return attached;

    }

    private Stack<AppCompatActivity> activityStack = new Stack<>();

    public void addActivity(final AppCompatActivity curAT) {

        if (null == activityStack) {

            activityStack = new Stack<>();

        }

        activityStack.add(curAT);

    }

    public void removeActivity(final Activity curAT) {

        if (null == activityStack) {

            activityStack = new Stack<>();

        }

        activityStack.remove(curAT);

    }


    public AppCompatActivity currentActivity() {

        AppCompatActivity activity = activityStack.lastElement();
        return activity;

    }


    public int howManyActivities() {

        return activityStack.size();

    }

    public void finishAllActivities() {

        for (int i = 0, size = activityStack.size(); i < size; i++) {

            if (null != activityStack.get(i)) {

                activityStack.get(i).finish();

            }
        }

        activityStack.clear();

    }

    public void finishAllActivities(final AppCompatActivity exceptAT) {

        Iterator<AppCompatActivity> itr = activityStack.iterator();

        while (itr.hasNext()) {

            final AppCompatActivity at = itr.next();
            if (null != at && !at.equals(exceptAT)) {

                at.finish();
                itr.remove();

            }

        }

    }


    public void exit() {

        finishAllActivities();

    }

    public void write(String key, String value) {

        synchronized (this) {

            pref.edit().putString(key, value).commit();

        }

    }

    public void write(String key, int value) {

        synchronized (this) {

            pref.edit().putInt(key, value).commit();

        }

    }

    public void write(String key, boolean value) {

        synchronized (this) {

            pref.edit().putBoolean(key, value).commit();

        }

    }

    public String read(String key, String defValue) {

        return pref.getString(key, defValue);

    }

    public int read(String key, int defValue) {

        return pref.getInt(key, defValue);

    }

    public boolean read(String key, boolean defValue) {

        return pref.getBoolean(key, defValue);

    }

    @Override
    public boolean onNotification(Notification notification) {
        if (notification.key.equals("exception")) {
            ExceptionHandle.ResponeThrowable throwable = (ExceptionHandle.ResponeThrowable) notification.object;
            Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show();
        } else if (notification.key.equals("error_code")) {
            String code = (String) notification.object;
            LogUtil.e("SIMeIDApplication....code = " + code);
            if (boo) {
                boo = false;
                //启动一个3秒定时器，避免做重复操作， 比如showToast ,或者会主界面
                TimerUtils.startTimeCount(new TimerUtils.TimeCallBack() {
                    @Override
                    public void timeOver() {
                        boo = true;
                    }
                });
                if ("-403".equals(code)) {
                    Toast.makeText(this, "状态已经失效，请重新登录", Toast.LENGTH_SHORT).show();
                    Context context = getBaseContext();
                    Activity activity = getCurrentActivity();
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    //将数据清除
                    LoginUtils.getInstance().logout();
                    SharedPreferences sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("userIsLogin", false);
                    edit.commit();

                    if (activity != null) {
                        intent.putExtra("targetName", activity.getClass().getName());
                        activity.finish();
                    }
                    startActivity(intent);
                }else{
                    ToastUtils.showMessages(this,code);
                }
            }

        }
        return false;
    }


    /**
     * 获取当前activity
     *
     * @return
     */
    public static Activity getCurrentActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(
                    null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
