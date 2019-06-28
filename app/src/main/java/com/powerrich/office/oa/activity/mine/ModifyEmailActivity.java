package com.powerrich.office.oa.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;
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

public class ModifyEmailActivity extends YTBaseActivity {

    @BindView(R.id.tv_current_email)
    TextView mTvCurrentEmail;
    @BindView(R.id.edt_email)
    EditText mEdtEmail;
    @BindView(R.id.edt_email_compare)
    EditText mEdtEmailCompare;
    @BindView(R.id.edt_pwd)
    EditText mEdtPwd;
    @BindView(R.id.tv_modify)
    TextView mTvModify;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;

    private UserInfo mUserInfo;


    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_modify_email);
        ButterKnife.bind(this, view);
        mUserInfo = LoginUtils.getInstance().getUserInfo();
        mTvCurrentEmail.setText(mUserInfo.getDATA().getEMAIL());
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("修改邮箱");
        showBack();
//        mEdtEmailCompare.setInputFocusInterface(new TypeEditTextView.InputFocusInterface() {
//            @Override
//            public void hasFocus() {
//                //将上一个内容给当前editview,好做比较
//                mEdtEmailCompare.setCompareStr(mEdtEmail.getText().toString());
//            }
//
//            @Override
//            public void noneFocus() {
//
//            }
//        });

    }

    @OnClick({R.id.tv_modify, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_modify:
                String emailStr1 = mEdtEmail.getText().toString();
                String emailStr2 = mEdtEmailCompare.getText().toString();
                String pwd = mEdtPwd.getText().toString();
                if(!StringUtil.checkEmail(emailStr1)){
                    ToastUtils.showMessage(this,"新邮箱输入不正确!");
                    return;
                }
                if(!StringUtil.checkEmail(emailStr2)){
                    ToastUtils.showMessage(this,"确认邮箱输入不正确!");
                    return;
                }else if(!emailStr1.equals(emailStr2)){
                    ToastUtils.showMessage(this,"确认邮箱输入不一致!");
                    return;
                }
                if(StringUtil.isEmpty(pwd) || pwd.length() < 6 || pwd.length() > 16){
                    ToastUtils.showMessage(this,"密码输入不正确");
                    return;
                }
                modifyEmail(mUserInfo.getAuthtoken(),emailStr1,pwd);
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    private void modifyEmail(String token , final String email, String password){
        ApiManager.getApi().exeNormal(RequestBodyUtils.modifyEmail(token,email,password))
                .compose(RxSchedulers.<BaseBean>io_main())
                .subscribe(new BaseSubscriber<BaseBean>() {
                    @Override
                    public void result(BaseBean baseBean) {
                        ToastUtils.showMessage(ModifyEmailActivity.this,"修改成功");
                        LoginUtils.getInstance().getUserInfo().getDATA().setEMAIL(email);
                        finish();
                    }
                });
    }
}
