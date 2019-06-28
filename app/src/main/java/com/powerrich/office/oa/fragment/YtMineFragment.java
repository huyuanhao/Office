package com.powerrich.office.oa.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.LeaderMailActivity;
import com.powerrich.office.oa.activity.LoginActivity;
import com.powerrich.office.oa.activity.MyCertificateActivity;
import com.powerrich.office.oa.activity.MySpaceActivity;
import com.powerrich.office.oa.activity.mine.AdvisoryActivity;
import com.powerrich.office.oa.activity.mine.BillRecordListActivity;
import com.powerrich.office.oa.activity.mine.CertificateListActivity;
import com.powerrich.office.oa.activity.mine.ChooseHeadImgActivity;
import com.powerrich.office.oa.activity.mine.CollectionActivity;
import com.powerrich.office.oa.activity.mine.CompanyListActivity;
import com.powerrich.office.oa.activity.mine.Identify5Activity;
import com.powerrich.office.oa.activity.mine.LogisticsListActivity;
import com.powerrich.office.oa.activity.mine.MessageListActivity;
import com.powerrich.office.oa.activity.mine.ReservationActivity;
import com.powerrich.office.oa.activity.mine.SettingActivity;
import com.powerrich.office.oa.activity.mine.UserInfoActivity;
import com.powerrich.office.oa.activity.mine.YtSearchActivity;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseFragment;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.InvokeHelper;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.enums.AdvisoryType;
import com.powerrich.office.oa.enums.CertificateType;
import com.powerrich.office.oa.enums.SearchActivityType;
import com.powerrich.office.oa.enums.UserType;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.notify.NotifyKey;
import com.powerrich.office.oa.tools.AESUtil;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.ImageLoad;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.VerificationUtils;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.xmlentity.MessageListBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.notification.NotificationCenter;
import com.yt.simpleframe.notification.NotificationListener;
import com.yt.simpleframe.utils.StringUtil;
import com.yt.simpleframe.view.BadgeView;
import com.yt.simpleframe.view.CircleImageView;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/6
 * 版权：
 */

public class YtMineFragment extends BaseFragment implements NotificationListener {
    @BindView(R.id.iv_photo)
    CircleImageView mIvPhoto;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_certified)
    TextView mTvCertified;
    @BindView(R.id.tv_phone_number)
    TextView mTvPhoneNumber;
    @BindView(R.id.tv_id_card)
    TextView mTvIdCard;
    @BindView(R.id.iv_next)
    ImageView mIvNext;
    @BindView(R.id.tv_mine_process)
    TextView mTvMineWork;
    @BindView(R.id.tv_mine_reservation)
    TextView mTvMineReservation;
    @BindView(R.id.tv_mine_msg)
    TextView mTvMineMsg;
    @BindView(R.id.rlt_info)
    RelativeLayout mRltInfo;
    @BindView(R.id.rlt_collection)
    RelativeLayout mRltCollection;
    @BindView(R.id.rlt_advisory)
    RelativeLayout mRltAdvisory;

    @BindView(R.id.rlt_certificate)
    RelativeLayout mRltCertificate;

    @BindView(R.id.rlt_suggest)
    RelativeLayout mRltSuggest;
    @BindView(R.id.rlt_mailbox)
    RelativeLayout mRltMailbox;
    @BindView(R.id.rlt_pay_bills)
    RelativeLayout mRltPayBills;
    @BindView(R.id.rlt_logistics)
    RelativeLayout mRltLogistics;

    @BindView(R.id.rl_my_certificate)
    RelativeLayout rl_my_certificate;
    @BindView(R.id.rl_my_space)
    RelativeLayout rl_my_space;


    @BindView(R.id.rlt_setting)
    RelativeLayout mRltSetting;
    @BindView(R.id.lt_user)
    LinearLayout mLtUser;
    @BindView(R.id.tv_nametype)
    TextView mTvNametype;
    @BindView(R.id.flt_msg)
    FrameLayout mFlayoutMsg;

//    @BindView(R.id.iv_userinfo_line1)
//    ImageView mIvUserinfoLine1;
//    @BindView(R.id.iv_userinfo_line2)
//    ImageView mIvUserinfoLine2;
//    @BindView(R.id.rlt_company)
//    RelativeLayout mRltCompany;
//    @BindView(R.id.rlt_recorde)
//    RelativeLayout mRltRecorde;

    Unbinder unbinder;

    Unbinder unbinder1;
    private InvokeHelper invoke;
    BadgeView badgeView;//未读消息


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        unbinder = ButterKnife.bind(this, view);
        invoke = new InvokeHelper(getContext());
        //注册通知
        if (LoginUtils.getInstance().isLoginSuccess()) {
            NotificationCenter.defaultCenter.addListener(NotifyKey.UPDATE_MESSAGE_KEY, this);
            if (badgeView == null) {
                badgeView = new BadgeView(this.getContext());
                badgeView.setBadgeMargin(0, 0, dip2px(11), 0);
                mFlayoutMsg.addView(badgeView);
            }
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_yt_mine;
    }


    @Override
    public void onResume() {
        super.onResume();
        //判断是否登录
        if (LoginUtils.getInstance().isLoginSuccess()) {
            initView(LoginUtils.getInstance().getUserInfo());
            //根据用户名和密码查找人员信息接口
            if (!BeanUtils.isEmpty(LoginUtils.getInstance().getUserInfo().getDATA())) {
                SharedPreferences sp = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String userPsw = "";
                try {
                    userPsw = AESUtil.decrypt("98563258", sp.getString("userPsw", ""));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                invoke.invoke(OAInterface.syncIdcardUserInfo(LoginUtils.getInstance().getUserInfo().getUserType(),
                        LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME(), userPsw
                ), new
                        BaseRequestCallBack() {
                            @Override
                            public void process(HttpResponse response, int what) {
                                ResultItem item = response.getResultItem(ResultItem.class);
                                if (Constants.SUCCESS_CODE.equals(item.getString("code"))) {
                                    String jsonStr = item.getJsonStr();
                                    Gson gson = new Gson();
                                    UserInfo userInfo = gson.fromJson(jsonStr, UserInfo.class);
                                    userInfo.setUserType(LoginUtils.getInstance().getUserInfo().getUserType());
                                    userInfo.setAuthtoken(LoginUtils.getInstance().getUserInfo().getAuthtoken());
                                    userInfo.setSiteNo(LoginUtils.getInstance().getUserInfo().getSiteNo());
                                    LoginUtils.getInstance().setUserInfo(userInfo);
                                    initView(LoginUtils.getInstance().getUserInfo());
                                }
                            }
                        });
            }
        } else {
            initView(null);
        }

    }

    @SuppressLint("ResourceAsColor")
    private void initView(UserInfo userInfo) {
        mIvPhoto.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.pic_mine_head));
        if (userInfo == null) {
            mTvName.setText("未登录");
            mTvPhoneNumber.setText("");
            mTvIdCard.setText("");
            mTvPhoneNumber.setVisibility(View.GONE);
            mTvIdCard.setVisibility(View.GONE);
            mTvCertified.setVisibility(View.GONE);
            if (badgeView != null)
                badgeView.setVisibility(View.GONE);
        } else {
            mTvPhoneNumber.setVisibility(View.VISIBLE);
            UserInfo.DATABean info = userInfo.getDATA();

            //判断是企业还是个人
            String type = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();
            String sf = info.getSFSMRZ();

            if ("0".equals(type)) {
                mTvName.setText(info.getREALNAME());
                mTvCertified.setVisibility(View.VISIBLE);
                if (!BeanUtils.isEmptyStr(info.getMOBILE_NUM())) {
                    mTvPhoneNumber.setText(StringUtil.replacePhone(info.getMOBILE_NUM()));
                }
                if (!BeanUtils.isEmptyStr(info.getIDCARD())) {
                    mTvIdCard.setText(StringUtil.replaceIdCard(info.getIDCARD()));
                }
            } else if ("1".equals(type)) {
                mTvName.setText(info.getCOMPANYNAME());
                mTvCertified.setVisibility(View.GONE);
                mTvPhoneNumber.setVisibility(View.INVISIBLE);
                mTvPhoneNumber.setText("");
                String businessLicence = info.getBUSINESSLICENCE();
                if (!BeanUtils.isEmptyStr(businessLicence)) {
                    mTvIdCard.setText(businessLicence.substring(0, 4) + "**********" + businessLicence.substring(14, businessLicence.length()));
                }
            }
            if ("1".equals(sf)) {
                mTvCertified.setText("已认证");
                mTvCertified.setEnabled(false);
                mTvIdCard.setVisibility(View.VISIBLE);
            } else {
                mTvName.setText(info.getUSERNAME());
                mTvCertified.setEnabled(true);
                mTvCertified.setText("去认证");
                mTvIdCard.setVisibility(View.INVISIBLE);
            }


//            if ("0".equals(type)) {
////                mRltCompany.setVisibility(View.GONE);
////                mIvUserinfoLine1.setVisibility(View.GONE);
////                mIvUserinfoLine2.setVisibility(View.VISIBLE);
//
//                mTvNametype.setText("我的资料资料");
////                mTvCertified.setText("已认证");
////                mTvCertified.setEnabled(false);
//                mTvName.setText(info.getREALNAME());
//
////                if ("1".equals(sf)) {
////                    mTvCertified.setText("已认证");
////                    mTvCertified.setEnabled(false);
////                    mTvName.setText(info.getREALNAME());
////                } else {
////                    mTvName.setText(info.getUSERNAME());
////                    if ("0".equals(info.getAUDIT_STATE())) {
////                        mTvCertified.setEnabled(false);
////                        mTvCertified.setText("认证中");
////                    } else {
////                        mTvCertified.setEnabled(true);
////                        mTvCertified.setText("去认证");
////                    }
////                }
//
//            } else if ("1".equals(type)) {
////                mRltCompany.setVisibility(View.VISIBLE);
////                mIvUserinfoLine1.setVisibility(View.VISIBLE);
////                mIvUserinfoLine2.setVisibility(View.GONE);
//                mTvNametype.setText("我的资料");
////                mTvCertified.setText("已认证");
////                mTvCertified.setEnabled(false);
//                mTvName.setText(info.getCOMPANYNAME());
////                if ("1".equals(sf)) {
////                    mTvCertified.setText("已认证");
////                    mTvCertified.setEnabled(false);
////                    mTvName.setText(info.getCOMPANYNAME());
////                } else {
////                    mTvName.setText(info.getUSERNAME());
////                    if ("0".equals(info.getAUDIT_STATE())) {
////                        mTvCertified.setEnabled(false);
////                        mTvCertified.setText("认证中");
////                    } else {
////                        mTvCertified.setEnabled(true);
////                        mTvCertified.setText("去认证");
////                    }
////                }
//            }

            if ("0".equals(sf)) {
                mTvCertified.setBackgroundResource(R.drawable.identity);
                mTvCertified.setTextColor(getResources().getColor(R.color.color_fc7705));
            } else {
                mTvCertified.setTextColor(getResources().getColor(R.color.blue_main));
            }


            String filePath = info.getHEADPORTRAIT_DOWNPATH();
            final String hdfsFileName = info.getHEADPORTRAIT_FILENAME();
            String url = "http://218.87.176.156:80/platform/DownFileServlet?" + "type=1" + "&DOWNPATH=" + filePath +
                    "&HDFSFILENAME=" + hdfsFileName + "&FILENAME=" + "head.jpg";
            ImageLoad.setUrl(this.getContext(), url, mIvPhoto, R.drawable.pic_mine_head);

            getMessageUnRead();
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.rl_my_space, R.id.rl_my_certificate, R.id.iv_photo, R.id.iv_next, R.id.tv_mine_process, R.id.tv_mine_reservation, R.id.tv_mine_msg, R.id.rlt_info, R.id
            .rlt_collection, R.id.rlt_advisory, R.id.rlt_suggest, R.id.rlt_mailbox, R.id.rlt_pay_bills, R.id.rlt_logistics, R
            .id.rlt_setting, R
            .id.lt_user, R.id.tv_certified, R.id.rlt_company, R.id.rlt_certificate})
    public void onViewClicked(View view) {

        if (BaseActivity.isFastDoubleClick()) {
            return;
        }

        if (!(view.getId() == R.id.lt_user)) {


            if (!LoginUtils.getInstance().isLoginSuccess()) {
                // 没有登录则让用户先去登录
                LoginUtils.getInstance().checkNeedLogin(getActivity(), true, getString(R.string.declare_qb_tips), 111);
                return;
            }
        }


        //没有登录点击任何按钮，返回登录界面
//        if (!LoginUtils.getInstance().isLoginSuccess()) {
//            goLoginActivity();
//            return;
//        }
        switch (view.getId()) {
            //我的空间
            case R.id.rl_my_space:
                startActivity(new Intent(getActivity(), MySpaceActivity.class));
                break;
            //我的证件
            case R.id.rl_my_certificate:
                startActivity(new Intent(getActivity(), MyCertificateActivity.class));
                break;
            case R.id.rlt_certificate:
                if (VerificationUtils.isAuthentication(this.getActivity())) {
                    //  CertificateListNewActivity
                    Intent intent = new Intent(this.getActivity(), CertificateListActivity.class);
                    intent.putExtra("type", CertificateType.查看);
                    startActivity(intent);
                }
                break;

            case R.id.iv_photo:
                goActivity(ChooseHeadImgActivity.class);
//                goActivity(ArticleListActivity.class);
                break;
            case R.id.iv_next:
                break;
            case R.id.tv_mine_process:
                goYtSearchActivity(SearchActivityType.我的办件);
                break;
            case R.id.tv_mine_reservation://我的预约
                goActivity(ReservationActivity.class);
                break;
            case R.id.tv_mine_msg:
                goActivity(MessageListActivity.class);
                break;
            case R.id.rlt_info:
                Intent intent = new Intent(this.getContext(), UserInfoActivity.class);
                String type = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();
                if ("0".equals(type)) {
                    intent.putExtra("type", UserType.个人);
                } else {
                    intent.putExtra("type", UserType.法人);
                }
                startActivity(intent);
                break;
            case R.id.rlt_collection://我的收藏
                goActivity(CollectionActivity.class);
                break;
            case R.id.rlt_advisory:
                goIntentActivity(AdvisoryActivity.class, AdvisoryType.咨询);
                break;
            case R.id.rlt_suggest:
                goIntentActivity(AdvisoryActivity.class, AdvisoryType.投诉);
                break;
            case R.id.rlt_mailbox:
                goActivity(LeaderMailActivity.class);
                break;
            case R.id.rlt_pay_bills:
                goActivity(BillRecordListActivity.class);
                break;
            case R.id.rlt_logistics:
                goActivity(LogisticsListActivity.class);
                break;
            case R.id.rlt_setting:
                goActivity(SettingActivity.class);
                break;
            case R.id.lt_user:
                goActivity(SettingActivity.class);
                break;
            case R.id.tv_certified:
                intent = new Intent(this.getContext(), Identify5Activity.class);//直接跳转到认证界面
//                intent.putExtra("phoneNumber", LoginUtils.getInstance().getUserInfo().getDATA().getMOBILE_NUM());
//                intent.putExtra("userId", LoginUtils.getInstance().getUserInfo().getDATA().getUSERID());
                startActivity(intent);
                break;
            case R.id.rlt_company:
                goActivity(CompanyListActivity.class);
                break;
        }
    }

    public void goIntentActivity(Class c, Serializable o) {
        Intent intent = new Intent(this.getContext(), c);
        intent.putExtra("type", o);
        startActivity(intent);
    }

    public void goActivity(Class c) {
        FragmentActivity activitiy = this.getActivity();
        if (activitiy != null) {
            startActivity(new Intent(activitiy, c));
        }

    }

    public void goLoginActivity() {
        FragmentActivity activitiy = this.getActivity();
        if (activitiy != null) {
            Intent intent = new Intent(activitiy, LoginActivity.class);
            startActivity(intent);
        }
    }


    public void goYtSearchActivity(SearchActivityType type) {
        FragmentActivity activitiy = this.getActivity();
        if (activitiy != null) {
            Intent intent = new Intent(activitiy, YtSearchActivity.class);
            intent.putExtra("mineType", type);
            startActivity(intent);
        }
    }


    /**
     * dip转化成px
     */
    public int dip2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void getMessageUnRead() {
        if (badgeView == null)
            return;
        String token = LoginUtils.getInstance().getUserInfo()
                .getAuthtoken();
        ApiManager.getApi().getPushMessageList(RequestBodyUtils.getPushMessageList(token
                , 1 + "", 1 + ""))
                .compose(RxSchedulers.<MessageListBean>io_main())
                .subscribe(new BaseSubscriber<MessageListBean>() {
                    @Override
                    public void result(MessageListBean listBean) {
                        if (null != listBean) {
                            String rows = listBean.getCOUNT();
                            if ("0".equals(rows) || StringUtil.isEmpty(rows)) {
                                badgeView.setVisibility(View.GONE);
                            } else {
                                badgeView.setVisibility(View.VISIBLE);
                                badgeView.setText(listBean.getCOUNT());
                            }
                        } else {
                            badgeView.setVisibility(View.GONE);
                        }
                    }
                });
    }


    @Override
    public boolean onNotification(Notification notification) {
        getMessageUnRead();
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationCenter.defaultCenter.removeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
