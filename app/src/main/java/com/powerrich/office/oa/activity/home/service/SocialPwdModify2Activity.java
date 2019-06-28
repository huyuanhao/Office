package com.powerrich.office.oa.activity.home.service;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.view.NoEmojiEditText;

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

public class SocialPwdModify2Activity extends BaseActivity {


    @BindView(R.id.edt_current_pwd)
    NoEmojiEditText mEdtCurrentPwd;
    @BindView(R.id.edt_pwd)
    NoEmojiEditText mEdtPwd;
    @BindView(R.id.edt_pwd_compare)
    NoEmojiEditText mEdtPwdCompare;
    @BindView(R.id.tv_modify)
    TextView mTvModify;
    private String name, cardId, socialId;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_social_pwd2;
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
        name = getIntent().getExtras().getString("name");
        cardId = getIntent().getExtras().getString("cardId");
        socialId = getIntent().getExtras().getString("socialId");

    }


    @OnClick(R.id.tv_modify)
    public void onViewClicked() {
        String oldPwd = mEdtCurrentPwd.getText().toString().trim();
        String newPwd = mEdtPwd.getText().toString().trim();
        String comPwd = mEdtPwdCompare.getText().toString().trim();
        boolean boo = check(oldPwd, newPwd, comPwd);
        if (boo) {
            ApiRequest request = OAInterface.ciBasicInfo(cardId,name,"01",socialId,oldPwd,newPwd);
//            ApiRequest request = OAInterface.ciBasicInfo("340123198209242591", "许勇", "01", "615245303",
//                    "12345", "11111");
            invoke.invokeWidthDialog(request, new BaseRequestCallBack() {
                @Override
                public void process(HttpResponse response, int what) {

                    ResultItem item = response.getResultItem(ResultItem.class);
                    String code = item.getString("code");
                    String message = item.getString("message");
                    DialogUtils.showToast(SocialPwdModify2Activity.this, message);

                }
            });
        }
    }


    private boolean check(String oldPwd, String newPwd, String comPwd) {
        boolean b = false;
        if (TextUtils.isEmpty(oldPwd)) {
            ToastUtils.showMessage(this, "原密码不能为空");
            return b;
        }
        if (oldPwd.length() != 6) {
            ToastUtils.showMessage(this, "原密码长度必须是6位");
            return b;
        }

        if (TextUtils.isEmpty(newPwd)) {
            ToastUtils.showMessage(this, "新密码不能为空");
            return b;
        }
        if (newPwd.length() != 6) {
            ToastUtils.showMessage(this, "新密码长度必须是6位");
            return b;
        }

        if (TextUtils.isEmpty(comPwd)) {
            ToastUtils.showMessage(this, "确认密码不能为空");
            return b;
        }

        if (!comPwd.equals(newPwd)) {
            ToastUtils.showMessage(this, "两次密码输入不正确");
            return b;
        }
        return true;
    }
}
