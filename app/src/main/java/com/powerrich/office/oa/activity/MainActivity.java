package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.SIMeID.SIMeIDApplication;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.FragmentCallback;
import com.powerrich.office.oa.bean.VersionInfoBean;
import com.powerrich.office.oa.fragment.InteractFragment;
import com.powerrich.office.oa.fragment.MyHomeFragment;
import com.powerrich.office.oa.fragment.ServiceFragment;
import com.powerrich.office.oa.fragment.YtMineFragment;
import com.powerrich.office.oa.tools.CheckVersionTools;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.FragmentUtils;
import com.powerrich.office.oa.tools.LocationUtils;
import com.powerrich.office.oa.view.QBCodeDialog;
import com.powerrich.office.oa.view.TabView;


public class MainActivity extends BaseActivity implements TabView.OnTabChangeListener, FragmentCallback, CheckVersionTools.OnCheckSuccessListener {

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private TabView mTabView;
    private TextView mTitleTextView;

    /**
     * 当前状态
     */
    private int mCurrentTabIndex = 0;

    /**
     * 再按一次退出程序
     */
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LocationUtils.requestLocation(this);
        mFragmentManager = getSupportFragmentManager();
//        mCurrentTabIndex = 0;
        setupViews();
        // 检测版本
        CheckVersionTools.checkVersion(this, true, this);
//       ImageView button = findViewById(R.id.iv_qb_code);
//
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
    }


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_start;
    }


    private void setupViews() {
        mTitleTextView = (TextView) findViewById(R.id.text_title);
        mTitleTextView.setText(R.string.government_service);
        mTabView = (TabView) findViewById(R.id.view_tab);
        mTabView.setOnTabChangeListener(this);
        mTabView.setCurrentTab(0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mCurrentTabIndex", mCurrentTabIndex);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentTabIndex = savedInstanceState.getInt("mCurrentTabIndex");
        if (mTabView != null) {
            mTabView.setCurrentTab(mCurrentTabIndex);
        }
    }

    @Override
    public void onFragmentCallback(Fragment fragment, int id, Bundle args) {
//        mTabView.setCurrentTab(0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("jsc", "MainActivity-onActivityResult:" + requestCode);
        if (requestCode == 111) {
            if (null != mCurrentFragment && mCurrentFragment instanceof MyHomeFragment) {
                mMyHomeFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    MyHomeFragment mMyHomeFragment;

    @Override
    public void onTabChange(String tag) {


        if (tag != null) {
            if (tag.equals("home")) {
                mCurrentTabIndex = 0;
                replaceFragment(MyHomeFragment.class);
//                mTitleTextView.setText(R.string.text_top_home);
                // 检查，如果没有登录则跳转到登录界面
              /*  final UserConfigManager manager = UserConfigManager.getInstance();
                if (manager.getId() <= 0) {
                    startActivityForResult(new Intent(this, LoginActivity.class),
                            BaseActivity.REQUEST_OK_LOGIN);
                }*/
            } else if (tag.equals("service")) {
                mCurrentTabIndex = 1;
//                mTitleTextView.setText(R.string.text_top_home);
                replaceFragment(ServiceFragment.class);
//                mTitleTextView.setText(R.string.text_tab_message);
            } else if (tag.equals("mine")) {
                mCurrentTabIndex = 3;
//                mTitleTextView.setText(R.string.text_tab_setting);
//                replaceFragment(MineFragment.class);
                replaceFragment(YtMineFragment.class);
                // 检查，如果没有登录则跳转到登录界面

              /*  final UserConfigManager manager = UserConfigManager.getInstance();
                if (manager.getId() <= 0) {
                    startActivityForResult(new Intent(this, LoginActivity.class),
                            BaseActivity.REQUEST_OK_LOGIN);
                }*/
            } else if (tag.equals("third")) {
                mCurrentTabIndex = 2;
//                mTitleTextView.setText(R.string.text_tab_setting);
//                replaceFragment(ThirdFragment.class);
                replaceFragment(InteractFragment.class);
            }
        }
    }

    private void replaceFragment(Class<? extends Fragment> newFragment) {


        mCurrentFragment = FragmentUtils.switchFragment(mFragmentManager,
                R.id.layout_content, mCurrentFragment,
                newFragment, null, false);

        if (null != mCurrentFragment && mCurrentFragment instanceof MyHomeFragment) {
            mMyHomeFragment = (MyHomeFragment) mCurrentFragment;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                DialogUtils.showToast(this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onCheckSuccess(VersionInfoBean info) {
        String localVersionName = CheckVersionTools.getLocalVersion(MainActivity.this);
        // 判断版本
        if (localVersionName.compareTo(info.getVERCODE()) > 0) {
//            LocationUtils.requestLocation(this, false, null);
        }
    }
}
