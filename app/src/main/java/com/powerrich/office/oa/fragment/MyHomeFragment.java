package com.powerrich.office.oa.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.InsuranceReceiveActivity;
import com.powerrich.office.oa.activity.MainActivity;
import com.powerrich.office.oa.activity.NewsDetailActivity;
import com.powerrich.office.oa.activity.OnlineWorkActivity;
import com.powerrich.office.oa.activity.ServiceActivity;
import com.powerrich.office.oa.activity.WorkGuideActivity;
import com.powerrich.office.oa.activity.home.InsuranceInquiriesActivity;
import com.powerrich.office.oa.activity.home.MobileBusinessSortActivity;
import com.powerrich.office.oa.activity.home.service.SocialInquiryActivity;
import com.powerrich.office.oa.activity.things.PublicWebViewActivity;
import com.powerrich.office.oa.activity.things.VolunteerNewsActivity;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseFragment;
import com.powerrich.office.oa.bean.QrCommit;
import com.powerrich.office.oa.bean.RollItem;
import com.powerrich.office.oa.common.PicassoImageLoader;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.fund.Activity.FundHomeActivity;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.TimeTools;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.tools.VerificationUtils;
import com.powerrich.office.oa.tools.network.HttpManager;
import com.powerrich.office.oa.view.AutoScrollTextView;
import com.powerrich.office.oa.view.BannerLayout;
import com.powerrich.office.oa.view.GradationScrollView;
import com.powerrich.office.oa.view.NoScrollGridView;
import com.powerrich.office.oa.view.QBCodeDialog;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.entity.NewsInfo;
import com.yt.simpleframe.http.bean.xmlentity.NewsInfoBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 文 件 名：MyHomeFragment
 * 描   述：首页
 * 作   者：Wangzheng
 * 时   间：2018-6-8 08:59:53
 * 版   权：v2.0
 */
public class MyHomeFragment extends BaseFragment implements GradationScrollView.ScrollViewListener {
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.gradation_scroll_view)
    GradationScrollView gradationScrollView;
    @BindView(R.id.ll_zhjs)
    LinearLayout llZhjs;
    @BindView(R.id.ll_chzn)
    LinearLayout llChzn;
    @BindView(R.id.ll_lxwm)
    LinearLayout llLxwm;
    @BindView(R.id.ll_qb_code)
    LinearLayout llQbCode;
    Unbinder unbinder1;
    @BindView(R.id.ll_volunteer)
    LinearLayout llVolunteer;
    @BindView(R.id.qr_fl)
    FrameLayout ivGuide;
    @BindView(R.id.ll_qr)
    LinearLayout llQr;
    private int[] icon = {R.drawable.home_icon_1, R.drawable.home_icon_2,
            R.drawable.home_icon_3, R.drawable.home_icon_4,
            R.drawable.home_icon_5, R.drawable.home_icon_6,
            R.drawable.home_icon_7, R.drawable.home_icon_8};
    private String[] iconName = {"社保查询", "公积金", "医保", "社保年检", "挂号", "违章查询", "加油", "全部"};

    private int[] bannerImgs = {R.drawable.banner_home_1, R.drawable.banner_home_2, R.drawable.banner_home_4, R.drawable.banner_home_3};
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.iv_scan)
    ImageView iv_scan;
    @BindView(R.id.banner)
    BannerLayout banner;
    @BindView(R.id.gv_list_home)
    NoScrollGridView gv_home;
    @BindView(R.id.scroll_view)
    AutoScrollTextView scroll_view;
    @BindView(R.id.ll_personal_work)
    LinearLayout ll_personal_work;
    @BindView(R.id.ll_enterprise_work)
    LinearLayout ll_enterprise_work;
    @BindView(R.id.ll_find_near)
    LinearLayout findNear;
    @BindView(R.id.ll_yingtan)
    LinearLayout llYingTan;
    @BindView(R.id.mobile_business)
    LinearLayout mobile_business;
    Unbinder unbinder;
    private int height;

    private int REQUEST_CODE = 111;

    //二维码生成图
    private String qRContent;

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        unbinder = ButterKnife.bind(this, view);
        showBanner();
        initListeners();
        showHomeList();
        getNewList();

        /**
         * 移动物联网博览会 只在20190720之前展示
         */
        if (TimeTools.qrLimited()) {
            llQr.setVisibility(View.VISIBLE);
            qRInit();
        } else {
            llQr.setVisibility(View.GONE);
        }

    }


    private boolean mHidden = false;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mHidden = hidden;
        Log.e("jsc", "MyHomeFragment-onHiddenChanged:" + hidden);
        if (!hidden) {
            //第一次展示二维码的引导图
            boolean qr = (boolean) SharedPreferencesUtils.getParam(getActivity(), "QR_First", true);
            if (qr) {
                qRInit();
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("jsc", "MyHomeFragment-onResume:");
        if (!mHidden) {
            //第一次展示二维码的引导图
            boolean qr = (boolean) SharedPreferencesUtils.getParam(getActivity(), "QR_First", true);
            if (qr) {
                qRInit();
            }
        }
    }


    private void showBanner() {
        banner.setImageLoader(new PicassoImageLoader());


        banner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.e("jsc", "MyHomeFragment-onItemClick:" + position);

//                if(position==0){
//
//                }else if(position==1){
//
//                }else if(position==2){
//
//                }

                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                        .putExtra("url", "https://www.jximiote.com/")
                        .putExtra("title", "2019江西国际移动物联网博览会"));


            }
        });

        List<RollItem> rolls = new ArrayList<>();
        for (int i = 0; i < bannerImgs.length; i++) {
            RollItem item = new RollItem();
            item.setDefaultImage(bannerImgs[i]);
            rolls.add(item);
        }
        banner.setViewUrls(rolls);


    }

    /**
     * 获取顶部图片高度后，设置滚动监听
     */
    private void initListeners() {
        ViewTreeObserver vto = banner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llTitle.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = llTitle.getHeight();

                gradationScrollView.setScrollViewListener(MyHomeFragment.this);
            }
        });
    }

    /**
     * 滑动监听
     *
     * @param scrollView
     * @param x
     * @param y
     * @param oldx
     * @param oldy
     */
    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y,
                                int oldx, int oldy) {
        if (y <= 0) {   //设置标题的背景颜色
            llTitle.setBackgroundColor(Color.argb((int) 0, 17, 163, 250));
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            llTitle.setBackgroundColor(Color.argb((int) alpha, 17, 163, 250));
        } else {    //滑动到banner下面设置普通颜色
            llTitle.setBackgroundColor(Color.argb((int) 255, 17, 163, 250));
        }
    }

    /**
     * 展示九宫格列表
     */
    protected void showHomeList() {
        gv_home.setAdapter(new CommonAdapter<Map<String, Object>>(this.getContext(), getData(), R.layout.home_gv_item_1) {

            @Override
            public void convert(ViewHolder holder, Map<String, Object> item) {
                holder.setImageResource(R.id.image, ((int) item.get("image")));
                holder.setTextView(R.id.text, ((String) item.get("text")));
            }
        });
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://社保

                        if (VerificationUtils.all(getActivity())) {
                            String type = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();
                            if ("1".equals(type)) {
                                ToastUtils.showMessage(getActivity(), "亲，该功能需要个人账号才能使用哦！");
                                return;
                            } else {
                                startActivity(new Intent(getActivity(), SocialInquiryActivity.class));
                            }
                        }
                        break;

                    case 1://公积金
                        // startActivity(new Intent(getActivity(), EarlyActivity.class));

                        //     startActivity(new Intent(getActivity(), RefundDetailActivity.class).putExtra("type",2));
                        if (VerificationUtils.isLogin(getContext())) {
                            if (VerificationUtils.isAuthentication(getContext())) {

                                String type = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();
                                if ("1".equals(type)) {
                                    ToastUtils.showMessage(getActivity(), "亲，该功能需要个人账号才能使用哦！");
                                    return;
                                } else {
                                    startActivity(new Intent(getActivity(), FundHomeActivity.class));
//                                    startActivity(new Intent(getActivity(), AccumulationFundActivity.class));
                                }
                            }
                        }
                        break;

                    case 2:
                        //医保
                        if (VerificationUtils.all(getContext())) {

                            String type = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();
                            if ("1".equals(type)) {
                                ToastUtils.showMessage(getActivity(), "亲，该功能需要个人账号才能使用哦！");
                                return;
                            } else {
                                startActivity(new Intent(getActivity(), InsuranceInquiriesActivity.class)
                                        .putExtra("type", 1));
                            }
                        }
                        break;
                    case 3:
                        //社保年检
                        if (VerificationUtils.isLogin(getContext())) {
                            if (VerificationUtils.isAuthentication(getContext())) {

                                String type = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();
                                if ("1".equals(type)) {
                                    ToastUtils.showMessage(getActivity(), "亲，该功能需要个人账号才能使用哦！");
                                    return;
                                } else {
                                    ((MainActivity) getActivity()).doPermissionRW("存储", new BaseActivity.PermissionCallBack() {
                                        @Override
                                        public void accept() {
                                            startActivity(new Intent(getActivity(), InsuranceReceiveActivity.class));
                                        }
                                    });
                                }
                            }
                        }
                        break;

//                    case 4://生活缴费
//                        startActivity(new Intent(getActivity(), LifePaymentActivity.class));
//                        break;
                    case 4:
                        //挂号
                        startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                                .putExtra("url", "https://wy.guahao.com/")
                                .putExtra("title", "挂号"));
                        break;
                    case 5:
                        //违章查询
                        startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
//                                .putExtra("url", "http://whjg.alipaycs.com/alipay/whjgquery/dzjc")
                                .putExtra("url", "http://city.mzywx.com/yingtan/front/car/toillegalquery")
                                .putExtra("title", "违章查询"));
                        break;
                    case 6:
                        startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                                .putExtra("url", "http://m.sinopecsales.com/webmobile/html/webhome.jsp")
                                .putExtra("title", "加油"));
//                        startActivity(new Intent(getActivity(), ChoiceMechanismActivity.class)
//                                .putExtra("type", "3"));

                        break;
                    case 7:
                        gotoActivity(ServiceActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> data_list = new ArrayList<>();
        for (int i = 0; i < iconName.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }

    @OnClick({R.id.ll_search, R.id.iv_scan, R.id.ll_personal_work, R.id.ll_enterprise_work, R.id.ll_find_near, R.id.ll_yingtan, R.id.ll_volunteer, R.id.mobile_business})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_search:
                startActivity(new Intent(getActivity(), WorkGuideActivity.class));
                break;
            case R.id.iv_scan://扫一扫
                break;
            case R.id.ll_personal_work:
                //   startActivity(new Intent(getActivity(), WorkNewActivity.class).putExtra("type", Constants.PERSONAL_WORK_TYPE));
                startActivity(new Intent(getActivity(), OnlineWorkActivity.class).putExtra("type", Constants.PERSONAL_WORK_TYPE));
                break;
            case R.id.ll_enterprise_work:
                //  startActivity(new Intent(getActivity(), WorkNewActivity.class).putExtra("type", Constants.COMPANY_WORK_TYPE));
                startActivity(new Intent(getActivity(), OnlineWorkActivity.class).putExtra("type", Constants.COMPANY_WORK_TYPE));
                break;
            case R.id.ll_find_near:
                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                        .putExtra("url", "https://map.baidu.com/mobile/webapp/index/index/")
                        .putExtra("LocationPermission", true)
                        .putExtra("title", "找周边"));
                break;

            case R.id.ll_yingtan:
//                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
//                        .putExtra("url", "https://mp.weixin.qq.com/s/06wuIjGByPCXhDpB54sCkg")
//                        .putExtra("title", "鹰潭旅游"));

                //全域旅游
                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                        .putExtra("url", "http://1807.jxtrip.cc/wx/app/?from=singlemessage&Token=")
                        .putExtra("title", "全域旅游"));

                break;
            case R.id.ll_volunteer:
                //自愿者资讯
                startActivity(new Intent(getActivity(), VolunteerNewsActivity.class));


//                //全域旅游
//                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
//                        .putExtra("url", "http://1807.jxtrip.cc/wx/app/?from=singlemessage&Token=")
//                        .putExtra("title", "全域旅游"));
                break;
            case R.id.mobile_business://移动政商
                startActivity(new Intent(getActivity(), MobileBusinessSortActivity.class));
                break;
            default:
                break;
        }
    }

    //获取热门新闻列表
    public void getNewList() {
        ApiManager.getApi().getNewsList(RequestBodyUtils.getNews("", "", 1))
                .compose(RxSchedulers.<NewsInfoBean>io_main())
                .subscribe(new BaseSubscriber<NewsInfoBean>() {
                    @Override
                    public void result(NewsInfoBean baseBean) {
                        setViewFlipper(baseBean.getDATA().getDATA());
                    }
                });
    }

    //设置新闻滚动
    private void setViewFlipper(final List<NewsInfo> newsInfoList) {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < newsInfoList.size(); i++) {
            list.add(newsInfoList.get(i).getTITLE());
        }
        if (list.size() == 0) {
            return;
        }
        scroll_view.setText(list.get(0));
        scroll_view.setList(list);
        scroll_view.startScroll();
        scroll_view.setClickLisener(new AutoScrollTextView.ItemClickLisener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(getActivity(), NewsDetailActivity.class).putExtra("news_id", newsInfoList.get(position).getNEWS_ID()));
            }
        });
    }

    private void gotoActivity(Class c) {
        startActivity(new Intent(getActivity(), c));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (scroll_view != null) {
            scroll_view.stopScroll();
        }
    }


    private void qRInit() {

        //是否登录
        if (!LoginUtils.getInstance().isLoginSuccess()) {
            return;
        }




        //查询
        Map map1 = new HashMap();
        map1.put("RealName", LoginUtils.getInstance().getUserInfo().getDATA().getREALNAME());
        map1.put("Mobile", LoginUtils.getInstance().getUserInfo().getDATA().getMOBILE_NUM());
        map1.put("Email", "");
        map1.put("JoinId", "0");
        map1.put("SignInCode", "");
        map1.put("WeiXinOpenId", "");
        String X_31HuiYi_AppAuth = "{\"APPID\":1664766576,\"httpMethod\":\"POST\",\"ClientIP\":\"Windows2012R2\",\"WebRequestGuid\":null,\"AppRequestGuid\":\"2019-06-11 14:04:09:  dade82a5-321e-49ee-8b6f-4ddc14c6ebc7\",\"RequestURL\":\"http://restful.31huiyi.com/api/EventJoins/GetJoins/eventId/1655847707/PageIndex/1/PageSize/20\",\"WebRequestURL\":null,\"WebRequestUrlReferrer\":null,\"APPAuth\":\"dbd8028e6df4e2714d2a1489967a3db6\"}";
        HttpManager.postNetQrWork(Constants.QR_OBTAIN_INFO, X_31HuiYi_AppAuth, map1, new HttpManager.IQrNetLister() {


            @Override
            public void onSuccess(String qrStr) {
                Log.e("jsc-qr", "MyHomeFragment-onSuccess:" + qrStr);
                boolean qrDialog = (boolean) SharedPreferencesUtils.getParam(getActivity(), "QR_Dialog_First", true);
                if (!BeanUtils.isEmptyStr(qrStr)) {
                    qRContent = qrStr;
                    if(qrDialog){
                        SharedPreferencesUtils.setParam(getActivity(), "QR_Dialog_First", false);
                        showDialog(qRContent);
                    }


                } else {
                    qrCommit();
                }
            }

            @Override
            public void onError(String error) {
                ToastUtils.showMessage(getActivity(), error);
            }
        });


    }


    private void qrCommit() {

        //是否登录
        if (!LoginUtils.getInstance().isLoginSuccess()) {
            return;
        }


        String realname = LoginUtils.getInstance().getUserInfo().getDATA().getREALNAME();
        String fr_phone_num = LoginUtils.getInstance().getUserInfo().getDATA().getMOBILE_NUM();
        String email = LoginUtils.getInstance().getUserInfo().getDATA().getEMAIL();
        String companyname = LoginUtils.getInstance().getUserInfo().getDATA().getCOMPANYNAME();
        String idcard = LoginUtils.getInstance().getUserInfo().getDATA().getIDCARD();
        String addr = LoginUtils.getInstance().getUserInfo().getDATA().getADDR();


        //提交    *****最重要的内容是联系人的手机号和参会人的手机号中的一个保持一致，不能写死来

        QrCommit qrCommit = new QrCommit();


        /*参会人信息 必传*/
        List<QrCommit.JoinListBean> JoinList = new ArrayList<>();
        QrCommit.JoinListBean joinListBean = new QrCommit.JoinListBean();
        /*门票ID集合 必传*/
        List<Integer> TicketIdList = new ArrayList<>();
        TicketIdList.add(Integer.valueOf(Constants.TicketslId));
        joinListBean.setTicketIdList(TicketIdList);
        /*参会人字段集合 必传*/
        List<QrCommit.JoinListBean.JoinFieldInfoListBean> JoinFieldInfoList = new ArrayList<>();
        JoinFieldInfoList.add(new QrCommit.JoinListBean.JoinFieldInfoListBean().setName("RealName").setValue(realname));
        JoinFieldInfoList.add(new QrCommit.JoinListBean.JoinFieldInfoListBean().setName("Mobile").setValue(fr_phone_num));
        JoinFieldInfoList.add(new QrCommit.JoinListBean.JoinFieldInfoListBean().setName("Email").setValue(email));
        JoinFieldInfoList.add(new QrCommit.JoinListBean.JoinFieldInfoListBean().setName("Field1664735566").setValue("否"));
        JoinFieldInfoList.add(new QrCommit.JoinListBean.JoinFieldInfoListBean().setName("Field1664735958").setValue(addr));
        JoinFieldInfoList.add(new QrCommit.JoinListBean.JoinFieldInfoListBean().setName("Field1655550497").setValue(idcard));
        joinListBean.setJoinFieldInfoList(JoinFieldInfoList);
        JoinList.add(joinListBean);
        qrCommit.setJoinList(JoinList);


        /*联系人信息 必传*/
        QrCommit.ContactInfoBean contactInfoBean = new QrCommit.ContactInfoBean();
        contactInfoBean.setContactRealName(realname);
        contactInfoBean.setContactMobile(fr_phone_num);
        contactInfoBean.setContactEmail(email);
        contactInfoBean.setContactCompany(companyname);
        qrCommit.setContactInfo(contactInfoBean);

        /*优惠码*/
        qrCommit.setDiscountCode("");
        /*邀请码*/
        qrCommit.setInviteCode("");
        qrCommit.setOpenId("");
        qrCommit.setAppId("");

        HttpManager.postNetQrCommit(Constants.QR_send, qrCommit.toString(), new HttpManager.IQrNetLister() {
            @Override
            public void onSuccess(String netStatus) {
                qRInit();
            }

            @Override
            public void onError(String error) {
                ToastUtils.showMessage(getActivity(), error);
            }
        });

    }


  /*  private  void showGuideView() {
        View view = getActivity().getWindow().getDecorView().findViewById(R.id.activity_main);

        if (view == null) return;

        ViewParent viewParent = view.getParent();
        if (viewParent instanceof FrameLayout) {
            final FrameLayout frameParent = (FrameLayout) viewParent;//整个父布局

            final LinearLayout linearLayout = new LinearLayout(getActivity());//新建一个LinearLayout
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setBackgroundResource(R.color.color_88000000);//背景设置灰色透明
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frameParent.removeView(linearLayout);
                }
            });

            Rect rect = new Rect();
            Point point = new Point();
            nearby.getGlobalVisibleRect(rect, point);//获得nearby这个控件的宽高以及XY坐标 nearby这个控件对应就是需要高亮显示的地方

            ImageView topGuideview = new ImageView(this);
            topGuideview.setLayoutParams(new ViewGroup.LayoutParams(rect.width(), rect.height()));           topGuideview.setBackgroundResource(R.drawable.iv_topguide);

            Rect rt = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(rt);
            topGuideview.setY(point.y - rt.top);//rt.top是手机状态栏的高度
            ImageView bottomGuideview = new ImageView(this);
            bottomGuideview.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
            bottomGuideview.setBackgroundResource(R.drawable.iv_bottomguide);
            bottomGuideview.setY(point.y + topGuideview.getHeight());

            linearLayout.addView(topGuideview);
            linearLayout.addView(bottomGuideview);
            frameParent.addView(linearLayout);
        }

    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.ll_zhjs, R.id.ll_chzn, R.id.ll_lxwm, R.id.ll_qb_code})
    public void onViewClicked(View view) {


        switch (view.getId()) {
            case R.id.ll_zhjs:
                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                        .putExtra("url", "http://www.jximiote.com/index.php?m=content&c=index&a=lists&catid=18")
                        .putExtra("title", "展会介绍"));
                break;
            case R.id.ll_chzn:
                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                        .putExtra("url", "http://www.jximiote.com/index.php?m=content&c=index&a=lists&catid=23")
                        .putExtra("title", "参会指南"));
                break;
            case R.id.ll_lxwm:
                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                        .putExtra("url", "http://www.jximiote.com/index.php?m=content&c=index&a=lists&catid=25")
                        .putExtra("title", "联系我们"));
                break;
            case R.id.ll_qb_code:

                //是否登录
                if (!LoginUtils.getInstance().isLoginSuccess()) {
                    // 没有登录则让用户先去登录
                    LoginUtils.getInstance().checkNeedLogin(getActivity(), true, getString(R.string.declare_qb_tips), REQUEST_CODE);
                    return;
                }


                if (!BeanUtils.isEmptyStr(qRContent)) {
                    showDialog(qRContent);
                } else {
                    qRInit();
                    ToastUtils.showMessage(getActivity(), "请稍后操作！");
                }


                break;
        }
    }

    private void showDialog(String content) {
        QBCodeDialog qbCodeDialog = new QBCodeDialog(getActivity(), content);
        qbCodeDialog.setClickListener(new QBCodeDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {

            }

            @Override
            public void doCancel() {
                //第一次展示二维码的引导图
                boolean qr = (boolean) SharedPreferencesUtils.getParam(getActivity(), "QR_First", true);
                if (qr) {
                    ivGuide.setVisibility(View.VISIBLE);
                    SharedPreferencesUtils.setParam(getActivity(), "QR_First", false);
                } else {
                    ivGuide.setVisibility(View.GONE);
                }

            }
        });
        qbCodeDialog.show();
    }

    @OnClick(R.id.qr_fl)
    public void onViewClicked() {
        ivGuide.setVisibility(View.GONE);

    }

/*    public void mengcengDialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.custom_dialog);


        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(R.drawable.guide_qr);
//        TextView textView = new TextView(getActivity());
//        textView.setText("你好啊");
//        textView.setTextSize(18);
//        textView.setTextColor(Color.WHITE);
//        textView.setGravity(Gravity.CENTER);
//        //textView.setPadding(0, 0, 0, 600);
//        textView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                if (dialog.isShowing())
//                    dialog.dismiss();
//            }
//        });

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        hideSyste mUI(imageView);
        dialog.addContentView(imageView, layoutParams);
        dialog.show();

        dialog.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); // 设置宽度
        lp.height = (int) (display.getHeight());
        dialog.getWindow().setAttributes(lp);
// dialog.getWindow().setGravity(80);
    }

    public void hideSystemUI(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }*/


}
