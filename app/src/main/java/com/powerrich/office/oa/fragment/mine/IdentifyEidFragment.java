package com.powerrich.office.oa.fragment.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aeye.face.AEFacePack;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.IdentifyEidActivity;
import com.powerrich.office.oa.enums.IdentifyType;
import com.powerrich.office.oa.fragment.mine.base.YtBaseFragment;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.PickUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.yt.simpleframe.http.bean.xmlentity.XmlUserInfo;
import com.yt.simpleframe.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/21
 * 版权：
 */

public class IdentifyEidFragment extends YtBaseFragment {

    IdentifyType type;
    Unbinder unbinder;
    @BindView(R.id.iv_process)
    ImageView mIvProcess;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.rb_man)
    RadioButton mRbMan;
    @BindView(R.id.rb_women)
    RadioButton mRbWomen;
    @BindView(R.id.tv_group)
    TextView mTvGroup;
    @BindView(R.id.tv_card_type)
    TextView mTvCardType;
    @BindView(R.id.et_idcard)
    EditText mEtIdcard;
    @BindView(R.id.tv_select_city)
    TextView mTvSelectCity;
    @BindView(R.id.et_adrress)
    EditText mEtAdrress;
    @BindView(R.id.et_phone_number)
    EditText mEtPhoneNumber;
    @BindView(R.id.tv_next)
    TextView mTvNext;
    @BindView(R.id.lt_page1)
    RelativeLayout mLtPage1;
    @BindView(R.id.iv_dynamic)
    ImageView mIvDynamic;
    @BindView(R.id.tv_start)
    TextView mTvStart;
    @BindView(R.id.lt_page2)
    LinearLayout mLtPage2;
    @BindView(R.id.tv_end)
    TextView mTvEnd;
    @BindView(R.id.lt_page3)
    LinearLayout mLtPage3;
    Unbinder unbinder1;


    public static IdentifyEidFragment getInstance(IdentifyType type) {
        IdentifyEidFragment fragment = new IdentifyEidFragment();
        Bundle b = new Bundle();
        b.putSerializable("type", type);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (IdentifyType) getArguments().getSerializable("type");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        if (type == IdentifyType.页面1) {
            mLtPage1.setVisibility(View.VISIBLE);
            mLtPage2.setVisibility(View.GONE);
            mLtPage3.setVisibility(View.GONE);
            mIvProcess.setBackgroundResource(R.drawable.eidlname_1);
        } else if (type == IdentifyType.页面2) {
            mLtPage1.setVisibility(View.GONE);
            mLtPage2.setVisibility(View.VISIBLE);
            mLtPage3.setVisibility(View.GONE);
            mIvProcess.setBackgroundResource(R.drawable.eidlname_2);
        } else {
            mLtPage1.setVisibility(View.GONE);
            mLtPage2.setVisibility(View.GONE);
            mLtPage3.setVisibility(View.VISIBLE);
            mIvProcess.setBackgroundResource(R.drawable.eidlname_3);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (type == IdentifyType.页面2) {
            AEFacePack.getInstance().AEYE_Init(mActivity);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (type == IdentifyType.页面2) {
            AEFacePack.getInstance().AEYE_Destory(mActivity);
        }
    }


    @Override
    protected View createContentView() {
        View view = inflateContentView(R.layout.activity_identify_eid);
        ButterKnife.bind(this, view);
        return view;
    }

    protected IdentifyEidActivity mActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (IdentifyEidActivity) context;
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

    @OnClick({R.id.tv_next, R.id.tv_start, R.id.tv_end, R.id.rb_man, R.id.rb_women,
            R.id.tv_group, R.id.tv_card_type, R.id.tv_select_city})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rb_man:
                break;
            case R.id.rb_women:
                break;

            case R.id.tv_next:
                if (checkData()) {
                    mActivity.replaceFragment(IdentifyType.页面2);
                }
                break;
            case R.id.tv_start:
//                ToastUtils.showMessage(mActivity,"no eid");
                mActivity.startDoRealNameThread();
//                mActivity.replaceFragment(IdentifyType.页面3);
                break;
            case R.id.tv_end:
                //将图片和内容上传到服务器
//                AppManager.getAppManager().finishActivity(ChooseIdentifyTypeActivity.class);
//                mActivity.finish();
                break;

            case R.id.tv_group:
                PickUtils.showEthnicPickerView(this.mContext, new PickUtils.StringCallback() {
                    @Override
                    public void getString(String city) {
                        mTvGroup.setText(city);
                    }
                });
                break;
            case R.id.tv_card_type:
                PickUtils.showCardIdPickerView(mContext, new PickUtils.StringCallback() {
                    @Override
                    public void getString(String city) {
                        mTvCardType.setText(city);
                    }
                });
                break;
            case R.id.tv_select_city:
                PickUtils.showCityPickerView(mContext, new PickUtils.StringCallback() {
                    @Override
                    public void getString(String city) {
                        mTvSelectCity.setText(city);
                    }
                });
                break;
        }
    }


    public boolean checkData() {

//        mEtName.setText("曹傲兵");
//        mEtIdcard.setText("420683199112143712");
//        mEtPhoneNumber.setText("13387211520");
//        mTvGroup.setText("汉");

        boolean b = false;
        IdentifyEidActivity.name = mEtName.getText().toString();
        IdentifyEidActivity.cardNumber = mEtIdcard.getText().toString();
        IdentifyEidActivity.selectCity = mTvSelectCity.getText().toString();
        IdentifyEidActivity.group = mTvGroup.getText().toString();
        IdentifyEidActivity.phoneNumber = mEtPhoneNumber.getText().toString();

//        address = mEtAdrress.getText().toString();


        if (TextUtils.isEmpty(IdentifyEidActivity.name)) {
            ToastUtils.showMessage(mActivity, "名字不能为空！");
            return b;
        }

        if(!StringUtil.isMobileNO(IdentifyEidActivity.phoneNumber)){
            ToastUtils.showMessage(mActivity, "手机号码输入错误！");
            return b;
        }

        if (!StringUtil.validCard(IdentifyEidActivity.cardNumber)) {
            ToastUtils.showMessage(mActivity, "身份证信息错误！");
            return b;
        }


        if (TextUtils.isEmpty(IdentifyEidActivity.group)) {
            ToastUtils.showMessage(mActivity, "民族不能为空！");
            return b;
        }


        if (TextUtils.isEmpty(IdentifyEidActivity.selectCity)) {
            ToastUtils.showMessage(mActivity, "选择区域地址不能为空！");
            return b;
        }

        b = true;
        return b;
    }

    public void saveUserInfo(String appEid) {
//        String userName = LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME();
//        String phoneNumber = LoginUtils.getInstance().getUserInfo().getDATA().getTEL();
        String sex = "男";
        String sexInt = "0";
        if (mRbWomen.isChecked()) {
            sex = "女";
            sexInt = "1";
        }

        String token = LoginUtils.getInstance().getUserInfo().getAuthtoken();
        XmlUserInfo info = new XmlUserInfo(IdentifyEidActivity.name,IdentifyEidActivity.cardNumber,"","",IdentifyEidActivity.group,sexInt,appEid);

//        ApiManager.getApi().exeNormal(RequestBodyUtils.saveUserInfo(token,"1",info))
//                .compose(RxSchedulers.<BaseBean>io_main())
//                .subscribe(new BaseSubscriber<BaseBean>() {
//                    @Override
//                    public void result(BaseBean bean) {
//                        UserInfo.DATABean user = LoginUtils.getInstance().getUserInfo().getDATA();
//                        user.setMZ(IdentifyEidActivity.group);
//                        user.setIDCARD(IdentifyEidActivity.cardNumber);
//                        user.setREALNAME(IdentifyEidActivity.name);
//                        user.setAUDIT_STATE("0");
//                        LoginUtils.getInstance().getUserInfo().setDATA(user);
//                        SharedPreferences sp = mActivity.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor edit = sp.edit();
//                        edit.putString("userInfoBean", GsonUtil.GsonString(LoginUtils.getInstance().getUserInfo()));
//                        edit.commit();
//                        mActivity.replaceFragment(IdentifyType.页面3);
//                    }
//                });
    }


}
