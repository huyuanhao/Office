package com.powerrich.office.oa.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.bean.VersionInfoBean;
import com.powerrich.office.oa.tools.CheckVersionTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/8/27
 * 版权：
 */

public class AboutActivity extends YTBaseActivity implements CheckVersionTools.OnCheckSuccessListener {


    @BindView(R.id.iv)
    ImageView mIv;

    @BindView(R.id.iv_next)
    ImageView mIvNext;

    @BindView(R.id.tv_version_code)
    TextView mTvVersionCode;
    @BindView(R.id.rlt_version_code)
    RelativeLayout mRltVersionCode;
    @BindView(R.id.rlt_checkversion)
    RelativeLayout mRltCheckversion;
    @BindView(R.id.tv_version)
    TextView mTvVersion;

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_about);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("关于我们");
        showBack();
        initView();
        checkVersion(false);
    }

    void initView() {
        String localVersionName = CheckVersionTools.getLocalVersion(this);
        mTvVersionCode.setText(localVersionName);
    }

    void checkVersion(boolean isShowTipsDialog) {
        CheckVersionTools.checkVersion(this, isShowTipsDialog, true, this);
    }

    @OnClick(R.id.rlt_checkversion)
    public void onViewClicked() {
        checkVersion(true);
    }

    @Override
    public void onCheckSuccess(VersionInfoBean info) {
        String localVersionName = CheckVersionTools.getLocalVersion(this);
        if (info == null) {
            //当前已经是最新版本
            mTvVersion.setText("当前已是最新版");
            mTvVersion.setTextColor(getResources().getColor(R.color.gray));
            mIvNext.setVisibility(View.INVISIBLE);
        } else {
            if (localVersionName.compareTo(info.getVERCODE()) > 0) {
                mTvVersion.setText("当前已是最新版");
                mTvVersion.setTextColor(getResources().getColor(R.color.gray));
                mIvNext.setVisibility(View.INVISIBLE);
            } else {
                mTvVersion.setText("发现新版本 "+info.getVERCODE());
                mTvVersion.setTextColor(getResources().getColor(R.color.red));
                mIvNext.setVisibility(View.VISIBLE);
            }
        }
    }
}
