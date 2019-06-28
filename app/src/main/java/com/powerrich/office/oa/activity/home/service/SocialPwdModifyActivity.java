package com.powerrich.office.oa.activity.home.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.yt.simpleframe.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/1
 * 版权：
 */

public class SocialPwdModifyActivity extends BaseActivity {
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_card_id)
    TextView mTvCardId;
    @BindView(R.id.et_social_id)
    EditText mEtSocialId;
    @BindView(R.id.tv_next)
    TextView mTvNext;

    private String name, cardId;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_social_pwd1;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTitleBar("社保卡密码修改", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, null);

        //填充数据
        UserInfo.DATABean userInfo = LoginUtils.getInstance().getUserInfo().getDATA();
        name = userInfo.getREALNAME();
        cardId = userInfo.getIDCARD();
        mTvName.setText(name);
        mTvCardId.setText(cardId);
    }


    @OnClick(R.id.tv_next)
    public void onViewClicked() {

        String socialId = mEtSocialId.getText().toString().trim();
        if(StringUtil.isEmpty(socialId)){
            ToastUtils.showMessage(this,"社保卡卡号不能为空");
            return;
        }
        if(socialId.length() <= 4){
            ToastUtils.showMessage(this,"社保卡位数不够");
            return;
        }

        Intent intent = new Intent(this, SocialPwdModify2Activity.class);
        intent.putExtra("socialid",socialId);
        intent.putExtra("name",name);
        intent.putExtra("cardId",cardId);
        startActivity(intent);
    }


}
