package com.powerrich.office.oa.activity.mine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.AESUtil;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.GetTokenUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.view.LoadingDialog;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/11
 * 版权：
 */

public class ModifyPwdActivity extends YTBaseActivity {

    private static final int UPDATE_PASSWORD_CODE = 0;
    @BindView(R.id.tv_username)
    TextView mTvUserName;
    @BindView(R.id.edt_current_pwd)
    EditText mEdtCurrentPwd;
    @BindView(R.id.edt_pwd)
    EditText mEdtPwd;
    @BindView(R.id.iv_pwd_type)
    ImageView mIvPwdType;
    @BindView(R.id.edt_pwd_compare)
    EditText mEdtPwdCompare;
    @BindView(R.id.tv_modify)
    TextView mTvModify;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    private SharedPreferences sp;

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("修改密码");
        showBack();
        mIvPwdType.setVisibility(View.GONE);
//        mEdtPwdCompare.setInputFocusInterface(new TypeEditTextView.InputFocusInterface() {
//            @Override
//            public void hasFocus() {
//                //将上一个内容给当前editview,好做比较
//                mEdtPwdCompare.setCompareStr(mEdtPwd.getText().toString());
//            }
//
//            @Override
//            public void noneFocus() {
//
//            }
//        });
//
//        mEdtPwd.setInputFocusInterface(new TypeEditTextView.InputFocusInterface() {
//            @Override
//            public void hasFocus() {
//            }
//            @Override
//            public void noneFocus() {
//                String pwd = mEdtPwd.getText().toString();
//                updatePwd(pwd);
//
//            }
//        });

        mEdtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String txtPwd = mEdtPwd.getText().toString().trim();
                updatePwd(txtPwd);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        UserInfo.DATABean data = LoginUtils.getInstance().getUserInfo().getDATA();
        if (data != null) {
            mTvUserName.setText(data.getUSERNAME());
        }

    }

    /**
     * 获取修改密码请求
     */
    private void updatePassword(String accessToken, String oldPwd, String newPwd) {
        ApiRequest request = OAInterface.updatePassword(accessToken, oldPwd, newPwd);
        invoke.invokeWidthDialog(request, callBack, UPDATE_PASSWORD_CODE);
    }


    void updatePwd(String txtPwd) {
        if (!StringUtil.isEmpty(txtPwd)) {
            mIvPwdType.setVisibility(View.VISIBLE);
            int i = StringUtil.checkPwdComplexity(txtPwd);
            if (i == 1 || i == 0) {
                mIvPwdType.setBackgroundResource(R.drawable.pasw_on);
            } else if (i == 2) {
                mIvPwdType.setBackgroundResource(R.drawable.pasw_tw);
            } else if (i == 3) {
                mIvPwdType.setBackgroundResource(R.drawable.pasw_thr);
            }
        } else {
            mIvPwdType.setVisibility(View.GONE);
        }
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("msg");
            if (Constants.CODE.equals(code)) {
                if (what == UPDATE_PASSWORD_CODE) {
                    DialogUtils.showToast(ModifyPwdActivity.this, message);
                    ModifyPwdActivity.this.finish();
                }
            } else {
                DialogUtils.showToast(ModifyPwdActivity.this, message);
            }
        }

        @Override
        public void finish(Object dialogObj, int what) {
            super.finish(dialogObj, what);
            if (dialogObj != null) {
                if (dialogObj instanceof LoadingDialog) {
                    ((LoadingDialog) dialogObj).dismiss();
                }
            }
        }
    };

    private void resetPassword(String phone, String newPwd) {
        ApiManager.getApi().exeNormal(RequestBodyUtils.resetPwd(phone, newPwd))
                .compose(RxSchedulers.<BaseBean>io_main())
                .subscribe(new BaseSubscriber<BaseBean>() {
                    @Override
                    public void result(BaseBean baseBean) {
                        ToastUtils.showMessage(ModifyPwdActivity.this, "密码修改成功！");
                        finish();
                    }
                });
    }


    @OnClick({R.id.tv_modify, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_modify:
                mEdtPwd.clearFocus();
                mEdtPwdCompare.clearFocus();
                final String oldPwd = mEdtCurrentPwd.getText().toString().trim();
                final String newPwd = mEdtPwd.getText().toString().trim();
                String comPwd = mEdtPwdCompare.getText().toString().trim();
//                String username = LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME();
                if (check(oldPwd, newPwd, comPwd)) {
                    String localPwd = sp.getString("userPsw", "");
                    String aesPwd = "";
                    try {
                        aesPwd = AESUtil.decrypt("98563258", localPwd);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!oldPwd.equals(aesPwd)) {
                        ToastUtils.showMessage(this, "原始密码输入错误！");
                        return;
                    }
                    GetTokenUtils.getToken(ModifyPwdActivity.this, new GetTokenUtils.ICallback() {
                        @Override
                        public void callback(String token) {
                            updatePassword(token, oldPwd, newPwd);
                        }
                    });
//                    resetPassword(username, newPwd);
                }
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    public boolean check(String oldPwd, String newPwd, String comPwd) {
        boolean b = false;
        if (TextUtils.isEmpty(oldPwd)) {
            ToastUtils.showMessage(this, "原密码不能为空！");
            return b;
        }
        if (!(oldPwd.length() >= 6 && oldPwd.length() <= 16)) {
            ToastUtils.showMessage(this, "原密码长输入错误！");
            return b;
        }

        if (TextUtils.isEmpty(newPwd)) {
            ToastUtils.showMessage(this, "新密码不能为空！");
            return b;
        }
        if (!(newPwd.length() >= 6 && newPwd.length() <= 16)) {
            ToastUtils.showMessage(this, "新密码长度控制 6~16位！");
            return b;
        }

        if (TextUtils.isEmpty(comPwd)) {
            ToastUtils.showMessage(this, "确认密码不能为空！");
            return b;
        }
        if (!(comPwd.length() >= 6 && comPwd.length() <= 16)) {
            ToastUtils.showMessage(this, "确认密码长度控制 6~16位！");
            return b;
        }
        if (!comPwd.equals(newPwd)) {
            ToastUtils.showMessage(this, "两次密码输入不正确！");
            return b;
        }
        return true;
    }
}
