package com.powerrich.office.oa.fragment.mine.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.tools.PermissionPageUtils;
import com.powerrich.office.oa.view.LoadingDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yt.simpleframe.R;

import java.lang.reflect.Field;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/6
 * 版权：
 */

public class YtBaseFragment extends Fragment {

    private LayoutInflater mLayoutInflater;
    protected Context mContext;
    private boolean detached = false;
    FrameLayout rootView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.mLayoutInflater = inflater;
        rootView = (FrameLayout) inflater.inflate(R.layout.fragment_base, null);
        //添加子类 内容区域
        if (createContentView() != null)
            rootView.addView(createContentView());
        return rootView;
    }

    /**
     * 给子类实现的内容区域
     */
    protected View createContentView() {
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 在onAttached()中执行修改detach属性
        mContext = context;
        detached = false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @SuppressLint("RestrictedApi")
    protected View inflateContentView(int resId) {
        View view = getLayoutInflater(null).inflate(resId, rootView, false);
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 这个方法让外部能够访问detached属性
     */
    public boolean isFragmentDetached() {
        return detached;
    }

    protected void replaceFragment(int frameLayoutId, Fragment fragment) {
        // 如果已经detached了，则不再执行任何操作
        if (isFragmentDetached()) {
            return;
        }
        if (getActivity() != null && !getActivity().isFinishing()) {
            if (fragment != null) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(frameLayoutId, fragment);
                transaction.commitAllowingStateLoss();
            }
        }
    }


    protected void replaceFragmentAnimation(int frameLayoutId, Fragment fragment) {
        // 如果已经detached了，则不再执行任何操作
        if (isFragmentDetached()) {
            return;
        }
        if (getActivity() != null && !getActivity().isFinishing()) {
            if (fragment != null) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim
                        .out_to_right);
                transaction.replace(frameLayoutId, fragment);
                transaction.commitAllowingStateLoss();
            }
        }
    }

    /**
     * 界面跳转
     *
     * @param cls 目标Activity
     */
    protected void readyGo(Class<?> cls) {
        readyGo(cls, null);
    }

    /**
     * 跳转界面，传参
     *
     * @param cls    目标Activity
     * @param bundle 数据
     */
    public void readyGo(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转界面并关闭当前界面
     *
     * @param cls 目标Activity
     */
    protected void readyGoThenKill(Class<?> cls) {
        readyGoThenKill(cls, null);
    }

    /**
     * @param cls    目标Activity
     * @param bundle 数据
     */
    protected void readyGoThenKill(Class<?> cls, Bundle bundle) {
        readyGo(cls, bundle);
        getActivity().finish();
    }

    /**
     * startActivityForResult
     *
     * @param cls         目标Activity
     * @param requestCode 发送判断值
     */
    protected void readyGoForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param cls         目标Activity
     * @param requestCode 发送判断值
     * @param bundle      数据
     */
    protected void readyGoForResult(Class<?> cls, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    private Dialog permissionDialog;

    /**
     * 提示开启权限
     */
    public void showTipDialog(String message) {
        if (this.getActivity() == null) {
            Log.e("YtBaseFragment", "activity is null");
        }
        if (permissionDialog == null) {
            permissionDialog = new AlertDialog.Builder(this.getActivity())
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
                            PermissionPageUtils.getInstance(YtBaseFragment.this.getActivity()).jumpPermissionPage();
                        }
                    })
                    .create();
        }
        if (permissionDialog.isShowing()) return;
        permissionDialog.show();
    }


    public void doPremissionCamera(BaseActivity.PermissionCallBack callBack){
        doPremission("读写、相机",callBack, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA);
    }

    public void doPremission(final String msg, final BaseActivity.PermissionCallBack callBack, String... permissions) {
        if (this.getActivity() == null) {
            Log.e("YtBaseFragment", "activity is null");
        }
        RxPermissions rxPermissions = new RxPermissions(this.getActivity());
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

    public <T> ObservableTransformer<T, T> loadingDialog() {
        final LoadingDialog dialog = new LoadingDialog(this.getContext());
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
}
