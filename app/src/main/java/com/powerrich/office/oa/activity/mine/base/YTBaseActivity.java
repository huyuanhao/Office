package com.powerrich.office.oa.activity.mine.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.view.LoadingDialog;
import com.yt.simpleframe.utils.KeyboardUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Action;


/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/6
 * 版权：
 */

public class YTBaseActivity extends BaseActivity {

    //构建一个toolsbar
    protected Context mContext;
    protected Activity mActivity;
    protected String TAG = getClass().getSimpleName();
    protected LinearLayout _rootView;
    protected FrameLayout _containerLayout;
    protected View _contentView;
    protected InputMethodManager mInputMethodManager;
    protected RelativeLayout titleRelativeLayout;
//    protected TextView titleView;
//    private Toolbar toolbar;

    protected View onCreateContentView() {
        return null;
    }


    protected View createView() {
        _rootView = (LinearLayout) inflateContentView(R.layout.activity_root);
//        titleView = (TextView) _rootView.findViewById(R.id.tv_title);
        _containerLayout = (FrameLayout) _rootView.findViewById(R.id.root_flt_content);
        titleRelativeLayout = (RelativeLayout) _rootView.findViewById(R.id.rlt_title);
        //嵌入内容区
        _contentView = onCreateContentView();
        if (_contentView != null) {
            _containerLayout.addView(_contentView);
        }
//        toolbar = (Toolbar) _rootView.findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mActivity = this;
        return _rootView;
    }

    protected void setSize() {
    }

    public void setNoneTitle() {
        titleRelativeLayout.setVisibility(View.GONE);
    }

    protected View inflateContentView(int resId) {
        return getLayoutInflater().inflate(resId, _containerLayout, false);
    }


    public void setTitle(String title) {
//        titleView.setText(title);
        initTitleBar(title, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftInput(YTBaseActivity.this);
                finish();
            }
        }, null);
    }

    public void setTitle(String title, View.OnClickListener listener) {
        initTitleBar(title, listener, null);
    }

    public void setTitle(String title, String rightBt, View.OnClickListener listener) {
        initTitleBar(title, rightBt, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                KeyboardUtils.hideSoftInput(YTBaseActivity.this);
            }
        }, listener);
    }

    public void showBack() {
//        if (toolbar != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    finish();
//                }
//            });
//        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        mContext = this;
        setContentView(this.createView());
    }

    @Override
    protected int provideContentViewId() {
        return -1;
    }

    protected void replaceFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(frameLayoutId, fragment);
            transaction.commitAllowingStateLoss();
        }
    }

    protected void replaceFragmentAnimation(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
            transaction.replace(frameLayoutId, fragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        }
    }

    public void goFinishActivity(Class c) {
        startActivity(new Intent(this, c));
        finish();
    }

    public void goActivity(Class c) {
        startActivity(new Intent(this, c));
    }

    public void goActivity(Class c, String data) {
        Intent intent = new Intent(this, c);
        intent.putExtra("data", data);
        startActivity(intent);
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
}
