package com.powerrich.office.oa.fragment.mine;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.LoginActivity;
import com.powerrich.office.oa.activity.mine.ModifyPhoneNumberActivity;
import com.powerrich.office.oa.enums.ModifyPhoneType;
import com.powerrich.office.oa.fragment.mine.base.YtBaseFragment;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.bean.ValiCodeBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.CountDownHelper;
import com.yt.simpleframe.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/15/015
 * 版权：
 */
public class ModifyPhoneFragment extends YtBaseFragment {


    @BindView(R.id.tv_verify1)
    TextView mTvVerify1;
//    @BindView(R.id.iv_step)
//    ImageView mIvStep;
    @BindView(R.id.tv_verify2)
    TextView mTvVerify2;
    @BindView(R.id.tv_verify3)
    TextView mTvVerify3;
    @BindView(R.id.tv_current_phonenumber)
    TextView mTvCurrentPhonenumber;
    @BindView(R.id.et_new_phonenumber)
    EditText mEtNewPhonenumber;
    @BindView(R.id.et_vetify_code)
    EditText mEtVetifyCode;
    @BindView(R.id.bt_vetify)
    TextView mBtVetify;
    @BindView(R.id.lt_valicode)
    LinearLayout mLtValicode;
    @BindView(R.id.tv_ok)
    TextView mTvOk;
    @BindView(R.id.tv_modify)
    TextView mTvModify;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.rlt_pn)
    RelativeLayout mRltPn;
    Unbinder unbinder;

    ModifyPhoneType type;
    private static String currentPhoneNumber = "";

    public static ModifyPhoneFragment getInstance(ModifyPhoneType type) {
        ModifyPhoneFragment fragment = new ModifyPhoneFragment();
        Bundle b = new Bundle();
        b.putSerializable("type", type);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (ModifyPhoneType) getArguments().getSerializable("type");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initView() {
        if (type == ModifyPhoneType.页面1) {
            mTvOk.setVisibility(View.GONE);
            mTvCurrentPhonenumber.setVisibility(View.VISIBLE);
            mEtNewPhonenumber.setVisibility(View.GONE);
            mRltPn.setVisibility(View.GONE);
            mTvVerify1.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.icon_phonenum_id_sel),null,null);
            mTvVerify1.setTextColor(getResources().getColor(R.color.main_select_color));
            mTvVerify2.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.icon_phonenum_phone_nor),null,null);
            mTvVerify2.setTextColor(getResources().getColor(R.color.gray));
            mTvVerify3.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.icon_phonenum_done_nor),null,null);
            mTvVerify3.setTextColor(getResources().getColor(R.color.gray));
            mTvModify.setText("验证");
        } else if (type == ModifyPhoneType.页面2) {
            mTvOk.setVisibility(View.GONE);
            mTvCurrentPhonenumber.setVisibility(View.GONE);
            mEtNewPhonenumber.setVisibility(View.VISIBLE);
            mRltPn.setVisibility(View.VISIBLE);
            mTvVerify1.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.icon_phonenum_id_nor),null,null);
            mTvVerify1.setTextColor(getResources().getColor(R.color.gray));
            mTvVerify2.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.icon_phonenum_phone_sel),null,null);
            mTvVerify2.setTextColor(getResources().getColor(R.color.main_select_color));
            mTvVerify3.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.icon_phonenum_done_nor),null,null);
            mTvVerify3.setTextColor(getResources().getColor(R.color.gray));
            mTvModify.setText("绑定");
        } else {
            mTvOk.setVisibility(View.VISIBLE);
            mTvCancel.setVisibility(View.GONE);
            mLtValicode.setVisibility(View.GONE);
            mRltPn.setVisibility(View.GONE);
            mTvVerify1.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.icon_phonenum_id_nor),null,null);
            mTvVerify1.setTextColor(getResources().getColor(R.color.gray));
            mTvVerify2.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.icon_phonenum_phone_nor),null,null);
            mTvVerify2.setTextColor(getResources().getColor(R.color.gray));
            mTvVerify3.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.icon_phonenum_done_sel),null,null);
            mTvVerify3.setTextColor(getResources().getColor(R.color.main_select_color));
            mTvModify.setText("完成");
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected View createContentView() {
        View view = inflateContentView(R.layout.activity_modify_phonenumber);
        ButterKnife.bind(this, view);
        if (LoginUtils.getInstance().isLoginSuccess()) {
            mTvCurrentPhonenumber.setText("当前手机号：" + StringUtil.replacePhone(LoginUtils.getInstance().getUserInfo().getDATA().getMOBILE_NUM()));
        } else {
            readyGoThenKill(LoginActivity.class);
        }
        initView();
        return view;
    }

    protected ModifyPhoneNumberActivity mActivity;

    /**
     * 如果你需要在Fragment中用到宿主Activity对象，建议在你的基类Fragment定义一个Activity的全局变量
     * 在onAttach中初始化，这不是最好的解决办法，
     * 但这可以有效避免一些意外Crash。详细原因参考第一篇的“getActivity()空指针”部分。
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (ModifyPhoneNumberActivity) context;
    }

    CountDownHelper helper;

    //开始倒计时
    private void startCountDown() {
        helper = new CountDownHelper(mBtVetify, "发送验证码", "重新获取", 60, 1);
        helper.setOnFinishListener(new CountDownHelper.OnFinishListener() {
            @Override
            public void finish() {
                mBtVetify.setText("发送验证码");
            }
        });
        helper.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closeCountDown();

    }
    private void closeCountDown(){
        if (helper != null)
            helper.stop();
    }


    @OnClick({R.id.bt_vetify, R.id.tv_modify, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_vetify:
                String phone = "";
                if(type == ModifyPhoneType.页面1) {

                    phone = LoginUtils.getInstance().getUserInfo().getDATA().getMOBILE_NUM();
                    if(phone.length() >= 11){
                        currentPhoneNumber = phone;
                    }else{
                        ToastUtils.showMessage(mActivity, "没有手机号码！");
                    }
                }else if(type == ModifyPhoneType.页面2){
                    phone = mEtNewPhonenumber.getText().toString();
                    if (!StringUtil.isMobileNO(phone)) {
                        ToastUtils.showMessage(mActivity, "手机号码不正确！");
                        return;
                    }

                    if(phone.equals(currentPhoneNumber)){
                        ToastUtils.showMessage(mActivity, "不能绑定之前手机号！");
                        return;
                    }

                }
                //获取验证码
                getValiCode(phone);
                //开始倒计时
                startCountDown();
                break;
            case R.id.tv_modify:
                if(type == ModifyPhoneType.页面3){
                    //提交新的手机号码给服务器
                    mActivity.finish();
                    return;
                }

                if (type == ModifyPhoneType.页面2){
                    String phoneNumber = mEtNewPhonenumber.getText().toString();
                    if(!StringUtil.isMobileNO(phoneNumber)){
                        ToastUtils.showMessage(mActivity, "手机号输入错误!");
                        return;
                    }
                    if(phoneNumber.equals(currentPhoneNumber)){
                        ToastUtils.showMessage(mActivity, "不能绑定之前手机号！");
                        return;
                    }
                }

                //开始验证 验证码
                String code = mEtVetifyCode.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.showMessage(mActivity, "验证码输入不能为空!");
                    return;
                }
                if (type == ModifyPhoneType.页面1) {
                    if (code1.equals(code)) {
                        mActivity.replaceFragment(ModifyPhoneType.页面2);
                        closeCountDown();
                    }else{
                        ToastUtils.showMessage(mActivity,"验证码输入错误");
                    }
                } else if (type == ModifyPhoneType.页面2) {
                    if (code2.equals(code)){
                        String phoneNumber = mEtNewPhonenumber.getText().toString();
                        modifyPhone(LoginUtils.getInstance().getUserInfo().getAuthtoken(),phoneNumber);
                        closeCountDown();
                    }else{
                        ToastUtils.showMessage(mActivity,"验证码输入错误");
                    }
                }
                break;
            case R.id.tv_cancel:
                if (type == ModifyPhoneType.页面1) {
                    mActivity.finish();
                    return;
                }
                getFragmentManager()
                        .popBackStack();
                break;
        }
    }

    private String code1 = "";//页面1的验证码
    private String code2 = "";//页面2的验证码

    private void getValiCode(String phoneNumber) {
        ApiManager.getApi().getPhoneValiCode(RequestBodyUtils.getPhoneValiCodeList(phoneNumber))
                .compose(RxSchedulers.<ValiCodeBean>io_main())
                .subscribe(new BaseSubscriber<ValiCodeBean>() {
                    @Override
                    public void result(ValiCodeBean baseBean) {
                        if (type == ModifyPhoneType.页面1) {
                            code1 = baseBean.getVali_code();
                        } else if (type == ModifyPhoneType.页面2) {
                            code2 = baseBean.getVali_code();
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void modifyPhone(String token ,String phone){
        ApiManager.getApi().exeNormal(RequestBodyUtils.modifyPhone(token,phone))
                .compose(RxSchedulers.<BaseBean>io_main())
                .subscribe(new BaseSubscriber<BaseBean>() {
                    @Override
                    public void result(BaseBean baseBean) {
                        closeCountDown();
                        mActivity.replaceFragment(ModifyPhoneType.页面3);
                    }
                });
    }
}
