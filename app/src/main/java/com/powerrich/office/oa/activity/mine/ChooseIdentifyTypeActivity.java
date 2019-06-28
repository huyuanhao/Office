package com.powerrich.office.oa.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/7/3
 * 版权：
 */

public class ChooseIdentifyTypeActivity extends YTBaseActivity {

    @BindView(R.id.rlt_user_compare)
    RelativeLayout mRltUserCompare;
    @BindView(R.id.rlt_eid)
    RelativeLayout mRltEid;

    @BindView(R.id.tv_login)
    TextView mTvLogin;

    private String phoneNumber;
    private String userId;
    private String loginType;

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_choose_identity_type);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("选择认证方式");
        showBack();
        phoneNumber = getIntent().getExtras().getString("phoneNumber");
        userId = getIntent().getExtras().getString("userId");

        try {
            loginType = getIntent().getExtras().getString("type");
            if ("login".equals(loginType)) {
                mTvLogin.setVisibility(View.VISIBLE);
            }else{
                mTvLogin.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            mTvLogin.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.rlt_user_compare, R.id.rlt_eid, R.id.tv_login})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, Identify5Activity.class);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.putExtra("userId", userId);
        switch (view.getId()) {
            case R.id.rlt_user_compare:
                intent.putExtra("type", "normal");
                startActivity(intent);
                break;
            case R.id.rlt_eid:
                intent.putExtra("type", "eid");
                startActivity(intent);
                break;
            case R.id.tv_login:
                finish();
                break;
        }
    }
}
