package com.powerrich.office.oa.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.AccumulationFundActivity;
import com.powerrich.office.oa.activity.LongHuYingTanActivity;
import com.powerrich.office.oa.activity.WaterServiceActivity;
import com.powerrich.office.oa.activity.home.service.SocialInquiryActivity;
import com.powerrich.office.oa.activity.things.PublicWebViewActivity;
import com.powerrich.office.oa.activity.things.TravelActivity;
import com.powerrich.office.oa.activity.things.WebNearActivity;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.base.BaseFragment;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.tools.VerificationUtils;
import com.powerrich.office.oa.view.GradationScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author MingPeng
 * @date 2017/11/21
 */
public class ServiceFragment extends BaseFragment implements GradationScrollView.ScrollViewListener {

    @BindView(R.id.gv_convenient_service)
    GridView gvConvenientService;
    @BindView(R.id.gv_nearby)
    GridView gvNearby;
    Unbinder unbinder;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.ll_view_spot)
    LinearLayout llViewSpot;
    @BindView(R.id.ll_food)
    LinearLayout llFood;
    @BindView(R.id.ll_hotel)
    LinearLayout llHotel;
    @BindView(R.id.ll_travel)
    LinearLayout llTravel;
    @BindView(R.id.iv_lhs)
    ImageView ivLhs;
    @BindView(R.id.iv_lxh)
    ImageView ivLxh;
    @BindView(R.id.iv_sqgz)
    ImageView ivSqgz;
    @BindView(R.id.ll_banner)
    ImageView llBanner;
    @BindView(R.id.gradation_scroll_view)
    GradationScrollView gradationScrollView;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    //    icon_trade_bmfu_on
    private int[] serviceIcon = {R.drawable.icon_service_org_6, R.drawable.icon_trade_bmfu_tw, R.drawable.icon_trade_bmfu_tr,
            R.drawable.icon_trade_bmfu_fo, R.drawable.icon_trade_bmfu_six, R.drawable.icon_trade_bmfu_sev, R.drawable.icon_service_org_2
            , R.drawable.icon_out_on};
    //    R.drawable.icon_trade_bmfu_fv,"公安查询",
    private String[] serviceIconName = {"快递服务", "预约挂号", "公交站", "违章查询", "社保查询", "公积金查询", "供水服务", "天气"};

    private int[] nearbyIcon = {R.drawable.icon_trade_fj_on, R.drawable.icon_trade_fj_tw, R.drawable.icon_trade_fj_tr,
            R.drawable.icon_trade_fj_onfo, R.drawable.icon_trade_fv, R.drawable.icon_trade_fj_six, R.drawable.icon_trade_fj_sev};
    private String[] nearbyIconName = {"景点", "美食", "酒店", "银行", "公厕", "加油站", "医院"};
    private int height;

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_service;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        initListeners();
        showConvenientServiceList();
        showNearbyList();
    }

    /**
     * 获取顶部图片高度后，设置滚动监听
     */
    private void initListeners() {

        ViewTreeObserver vto = llBanner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                titleLayout.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = titleLayout.getHeight();

                gradationScrollView.setScrollViewListener(ServiceFragment.this);
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
            titleLayout.setBackgroundColor(Color.argb((int) 0, 17, 163, 250));
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            titleLayout.setBackgroundColor(Color.argb((int) alpha, 17, 163, 250));
        } else {    //滑动到banner下面设置普通颜色
            titleLayout.setBackgroundColor(Color.argb((int) 255, 17, 163, 250));
        }
    }

    /**
     * 展示便民服务列表
     */
    protected void showConvenientServiceList() {
        gvConvenientService.setAdapter(new CommonAdapter<Map<String, Object>>(this.getContext(), getData(serviceIcon, serviceIconName), R.layout.service_gv_item) {

            @Override
            public void convert(ViewHolder holder, Map<String, Object> item) {
                holder.setImageResource(R.id.image, ((int) item.get("image")));
                holder.setTextView(R.id.text, ((String) item.get("text")));
            }
        });
        gvConvenientService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取前判断是否登录了
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                                .putExtra("url", "http://m.kuaidi100.com/?uid=&isnight=0&siteid=55&version=3.0.7&platform=2")
                                .putExtra("title", "快递服务"));
                        //生活缴费
//                        startActivity(new Intent(getActivity(), LifePaymentActivity.class));
                        break;
                    case 1:
                        //挂号
                        startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                                .putExtra("url", "https://wy.guahao.com/")
                                .putExtra("title", "挂号"));
                        break;
                    case 2:
                        //公交站
                        startActivity(new Intent(getActivity(), WebNearActivity.class)
                                .putExtra("type", "20"));
                        break;
                    case 3:
                        //违章查询
                        startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
//                                .putExtra("url", "http://whjg.alipaycs.com/alipay/whjgquery/dzjc")
                                .putExtra("url", "http://city.mzywx.com/yingtan/front/car/toillegalquery")
                                .putExtra("title", "违章查询"));
                        break;
//                    case 4:
//                        ToastUtils.showMessage(getContext(), getString(R.string.developing));
//                        break;
                    case 4://社保查询
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
                    case 5://公积金查询
                        if (VerificationUtils.all(getActivity())) {
                            String type = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();
                            if ("1".equals(type)) {
                                ToastUtils.showMessage(getActivity(), "亲，该功能需要个人账号才能使用哦！");
                                return;
                            } else {
                                startActivity(new Intent(getActivity(), AccumulationFundActivity.class));
                            }
                        }
                        break;
                    case 6://供水服务
                        startActivity(new Intent(getActivity(), WaterServiceActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(getActivity(), WebNearActivity.class)
                                .putExtra("type", "19"));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 展示附近列表
     */
    protected void showNearbyList() {
        gvNearby.setAdapter(new CommonAdapter<Map<String, Object>>(this.getContext(), getData(nearbyIcon, nearbyIconName), R.layout.service_gv_item) {

            @Override
            public void convert(ViewHolder holder, Map<String, Object> item) {
                holder.setImageResource(R.id.image, ((int) item.get("image")));
                holder.setTextView(R.id.text, ((String) item.get("text")));
            }
        });
        gvNearby.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getActivity().startActivity(new Intent()
                        .setClass(getActivity(), WebNearActivity.class)
                        .putExtra("type", position + ""));
            }
        });
    }


    public List<Map<String, Object>> getData(int[] iconIds, String[] iconNames) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < iconNames.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", iconIds[i]);
            map.put("text", iconNames[i]);
            dataList.add(map);
        }
        return dataList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_search, R.id.ll_view_spot, R.id.ll_food, R.id.ll_hotel, R.id.ll_travel,
            R.id.iv_lhs, R.id.iv_lxh, R.id.iv_sqgz, R.id.yingtan_news1, R.id.yingtan_news2,
            R.id.yingtan_news3, R.id.yingtan_news4, R.id.yingtan_news5, R.id.ll_banner})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_search:
                break;
            case R.id.ll_view_spot:
                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
//                        .putExtra("url", "http://1807.jxtrip.cc/wx/app/saleProduct?type=2")
                        .putExtra("url", "https://m.ctrip.com/webapp/you/sight/186.html?ishidenavbar=yes&popup=close&autoawaken=close")
                        .putExtra("title", "景点"));
                break;
            case R.id.ll_food:
                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
//                        .putExtra("url", "https://m.tuniu.com/g1714/specialty-0-0/")
                        .putExtra("url", "https://m.ctrip.com/webapp/you/restaurant/186.html?ishidenavbar=yes&popup=close&autoawaken=close")
                        .putExtra("title", "美食"));
                break;
            case R.id.ll_hotel:
                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                        .putExtra("url", "https://m.ctrip.com/webapp/hotel/city534")
                        .putExtra("title", "酒店"));
                break;
            case R.id.ll_travel:
                startActivity(new Intent(getActivity(), TravelActivity.class));
                break;
            case R.id.iv_lhs:
                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                        .putExtra("url", "https://m.ctrip.com/webapp/you/sight/160/137685.html?ishidenavbar=yes&popup=close&autoawaken=close")
                        .putExtra("title", "龙虎山"));
                break;
            case R.id.iv_lxh:
                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                        .putExtra("url", "https://m.ctrip.com/webapp/you/sight/160/14319.html?ishidenavbar=yes&popup=close&autoawaken=close")
                        .putExtra("title", "芦溪河"));
                break;
            case R.id.iv_sqgz:
                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                        .putExtra("url", "https://m.ctrip.com/webapp/you/sight/160/14548.html?ishidenavbar=yes&popup=close&autoawaken=close")
                        .putExtra("title", "上清古镇"));
                break;
            case R.id.yingtan_news1:
                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                        .putExtra("url", "https://mp.weixin.qq.com/s/E8ktLv1MbcFkoWoJPMTyEw")
                        .putExtra("title", "鹰潭旅游"));
                break;
            case R.id.yingtan_news2:
                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                        .putExtra("url", "https://mp.weixin.qq.com/s/NdRmSwLGkaKn354B-Q1WMw")
                        .putExtra("title", "鹰潭旅游"));
                break;
            case R.id.yingtan_news3:
                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                        .putExtra("url", "https://mp.weixin.qq.com/s/NJj7ALIRt3WCW0Nc4axkUw")
                        .putExtra("title", "鹰潭旅游"));
                break;
            case R.id.yingtan_news4:
                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                        .putExtra("url", "https://mp.weixin.qq.com/s/qJTy64_mixJygPD-JShUfg")
                        .putExtra("title", "鹰潭旅游"));
                break;
            case R.id.yingtan_news5:
                startActivity(new Intent(getActivity(), PublicWebViewActivity.class)
                        .putExtra("url", "https://mp.weixin.qq.com/s/lOwhxOkEiAakr0_VUx40Rg")
                        .putExtra("title", "鹰潭旅游"));

                break;
            case R.id.ll_banner:
                startActivity(new Intent(getActivity(), LongHuYingTanActivity.class));
                break;
            default:
                break;
        }
    }

}
