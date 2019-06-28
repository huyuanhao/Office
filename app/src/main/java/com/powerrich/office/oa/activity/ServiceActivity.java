package com.powerrich.office.oa.activity;

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
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.home.InsuranceInquiriesActivity;
import com.powerrich.office.oa.activity.home.service.SocialActivity;
import com.powerrich.office.oa.activity.home.service.SocialInquiryActivity;
import com.powerrich.office.oa.activity.things.PublicWebViewActivity;
import com.powerrich.office.oa.activity.things.WebNearActivity;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.base.BaseActivity;
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
 * 全部
 * 文 件 名：OnlineWorkActivity
 * 描   述：个人办事
 * 作   者：Wangzheng
 * 时   间：2018-6-6 17:07:29
 * 版   权：v1.0
 */
public class ServiceActivity extends BaseActivity implements View.OnClickListener, GradationScrollView.ScrollViewListener {

    @BindView(R.id.gv_living)
    GridView gvLiving;
    @BindView(R.id.gv_medical)
    GridView gvMedical;
    @BindView(R.id.gv_insurance)
    GridView gvInsurance;
    @BindView(R.id.gv_travel)
    GridView gvTravel;
    @BindView(R.id.lately1)
    LinearLayout lately1;
    @BindView(R.id.lately2)
    LinearLayout lately2;
    @BindView(R.id.lately3)
    LinearLayout lately3;
    @BindView(R.id.lately4)
    LinearLayout lately4;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.fading_sv)
    GradationScrollView fadingSv;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;

    private int[] livingIcon = {R.drawable.icon_service_life_1, R.drawable.icon_service_life_2,
            R.drawable.icon_service_life_3, R.drawable.icon_service_life_4,
            R.drawable.icon_service_life_5, R.drawable.icon_service_life_6};
    private String[] livingIconName = {"供水服务", "快递服务", "违章查询", "加油卡充值", "天气", "空气水质查询"};
    private int[] medicalIcon = {R.drawable.icon_service_health_1, R.drawable.icon_service_health_2, R.drawable
            .icon_service_health_3};
    private String[] medicalIconName = {"医保查询", "定点医院", "预约挂号"};
    private int[] insuranceIcon = {R.drawable.icon_service_home_1, R.drawable.icon_service_home_2, R.drawable
            .icon_service_home_3, R.drawable.icon_service_home_4};
    private String[] insuranceIconName = {"社保查询", "公积金查询", "社保年检", "社保卡"};
    private int[] travelIcon = {R.drawable.icon_service_walk_1, R.drawable.icon_service_walk_2};
    private String[] travelIconName = {"火车", "机票"};
    private Unbinder bind;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_service;
    }

    private void initView() {
        initTitleBar(R.string.service, this, null);
        initListeners();
        showLivingList();
        showMedicalList();
        showInsuranceList();
        showTravelList();
    }

    /**
     * 获取顶部图片高度后，设置滚动监听
     */
    private void initListeners() {

        ViewTreeObserver vto = iv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                titleLayout.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = iv.getHeight() / 2;

                fadingSv.setScrollViewListener(ServiceActivity.this);
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
            tvTopTitle.setTextColor(Color.argb((int) 0, 255, 255, 255));
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            titleLayout.setBackgroundColor(Color.argb((int) alpha, 17, 163, 250));
            tvTopTitle.setTextColor(Color.argb((int) alpha, 255, 255, 255));
        } else {    //滑动到banner下面设置普通颜色
            titleLayout.setBackgroundColor(Color.argb((int) 255, 17, 163, 250));
            tvTopTitle.setTextColor(Color.argb((int) 255, 255, 255, 255));
        }
    }

    /**
     * 生活缴费
     */
    private void showLivingList() {

        gvLiving.setAdapter(new CommonAdapter<Map<String, Object>>(this, getData(livingIcon, livingIconName), R.layout
                .layout_service_gv_item) {

            @Override
            public void convert(ViewHolder holder, Map<String, Object> item) {
                holder.setImageResource(R.id.image, ((int) item.get("image")));
                holder.setTextView(R.id.text, ((String) item.get("text")));
            }
        });
        gvLiving.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
//                    case 0://电费
//                        startActivity(new Intent(ServiceActivity.this, ChoiceMechanismActivity.class)
//                                .putExtra("type", "1"));
//                        break;
                    case 0://水费
                        startActivity(new Intent(ServiceActivity.this, WaterServiceActivity.class));
//                        startActivity(new Intent(ServiceActivity.this, ChoiceMechanismActivity.class)
//                                .putExtra("type", "0"));
                        break;
//                    case 2://燃气费
//                        startActivity(new Intent(ServiceActivity.this, ChoiceMechanismActivity.class)
//                                .putExtra("type", "2"));
//                        break;
//                    case 3://话费查询
//                        ToastUtils.showMessage(ServiceActivity.this, getString(R.string.developing));
//                        break;
//                    case 4://电视费
//                        startActivity(new Intent(ServiceActivity.this, PowerRateActivity.class)
//                                .putExtra("type", "4"));
//                        break;
                    case 1://快递服务
                        startActivity(new Intent(ServiceActivity.this, PublicWebViewActivity.class)
                                .putExtra("url", "http://m.kuaidi100.com/?uid=&isnight=0&siteid=55&version=3.0.7&platform=2")
                                .putExtra("title", "快递服务"));
                        break;
                    case 2://违章查询
                        startActivity(new Intent(ServiceActivity.this, PublicWebViewActivity.class)
//                                .putExtra("url", "http://whjg.alipaycs.com/alipay/whjgquery/dzjc")
                                .putExtra("url", "http://city.mzywx.com/yingtan/front/car/toillegalquery")
                                .putExtra("title", "违章查询"));
                        break;
                    case 3://加油
                        startActivity(new Intent(ServiceActivity.this, PublicWebViewActivity.class)
                                .putExtra("url", "http://m.sinopecsales.com/webmobile/html/webhome.jsp")
                                .putExtra("title", "加油卡充值"));
                        break;
                    case 4://天气
                        startActivity(new Intent(ServiceActivity.this, WebNearActivity.class)
                                .putExtra("type", "19"));
                        break;
                    case 5://空气
                        startActivity(new Intent(ServiceActivity.this, PublicWebViewActivity.class)
                                .putExtra("url", "http://zfb.ipe.org.cn/index.aspx?cityId=297")
                                .putExtra("title", "空气水质查询"));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 医疗健康
     */
    private void showMedicalList() {
        gvMedical.setAdapter(new CommonAdapter<Map<String, Object>>(this, getData(medicalIcon, medicalIconName), R.layout
                .layout_service_gv_item) {

            @Override
            public void convert(ViewHolder holder, Map<String, Object> item) {
                holder.setImageResource(R.id.image, ((int) item.get("image")));
                holder.setTextView(R.id.text, ((String) item.get("text")));
            }
        });
        gvMedical.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //医保
                        if (VerificationUtils.all(ServiceActivity.this)) {
                            startActivity(new Intent(ServiceActivity.this, InsuranceInquiriesActivity.class)
                                    .putExtra("type", 1));
                        }
                        break;
                    case 1://定点医院
                        startActivity(new Intent()
                                .setClass(ServiceActivity.this, WebNearActivity.class)
                                .putExtra("type", "22"));
                        break;
                    case 2:
                        //挂号
                        startActivity(new Intent(ServiceActivity.this, PublicWebViewActivity.class)
                                .putExtra("url", "https://wy.guahao.com/")
                                .putExtra("title", "挂号"));
                        break;
                    case 3:
                        ToastUtils.showMessage(ServiceActivity.this, getString(R.string.developing));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 保险与住房
     */
    private void showInsuranceList() {
        gvInsurance.setAdapter(new CommonAdapter<Map<String, Object>>(this, getData(insuranceIcon, insuranceIconName), R.layout
                .layout_service_gv_item) {

            @Override
            public void convert(ViewHolder holder, Map<String, Object> item) {
                holder.setImageResource(R.id.image, ((int) item.get("image")));
                holder.setTextView(R.id.text, ((String) item.get("text")));
            }
        });
        gvInsurance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //只允许个人
                if (VerificationUtils.all(ServiceActivity.this)) {
                    String type = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();
                    if ("1".equals(type)) {
                        ToastUtils.showMessage(ServiceActivity.this, "亲，该功能需要个人账号才能使用哦！");
                        return;
                    }
                } else {
                    return;
                }


                switch (position) {
                    case 0://社保查询
                        startActivity(new Intent(ServiceActivity.this, SocialInquiryActivity.class));
                        break;
                    case 1://公积金查询
                        startActivity(new Intent(ServiceActivity.this, AccumulationFundActivity.class));
                        break;
                    case 2://社保年检
                        doPermissionRW("存储", new PermissionCallBack() {
                            @Override
                            public void accept() {
                                startActivity(new Intent(ServiceActivity.this, InsuranceReceiveActivity.class));
                            }
                        });
                        break;
                    case 3://社保卡
                        startActivity(new Intent(ServiceActivity.this, SocialActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void showTravelList() {
        gvTravel.setAdapter(new CommonAdapter<Map<String, Object>>(this, getData(travelIcon, travelIconName), R.layout
                .layout_service_gv_item) {

            @Override
            public void convert(ViewHolder holder, Map<String, Object> item) {
                holder.setImageResource(R.id.image, ((int) item.get("image")));
                holder.setTextView(R.id.text, ((String) item.get("text")));
            }
        });
        gvTravel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
//                    case 0://公交
//                        break;
                    case 0://火车
                        startActivity(new Intent(ServiceActivity.this, PublicWebViewActivity.class)
//                                .putExtra("url", "http://m.ctrip.com/webapp/train/")
                                .putExtra("url", "https://touch.train.qunar.com/?bd_source=qunar")
                                .putExtra("title", "火车票"));
                        break;
                    case 1:
                        startActivity(new Intent(ServiceActivity.this, PublicWebViewActivity.class)
//                                .putExtra("url", "http://m.ctrip.com/webapp/train/")
                                .putExtra("url", "https://m.flight.qunar.com/h5/flight/?bd_source=qunar")
                                .putExtra("title", "机票"));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initData() {

    }

    public List<Map<String, Object>> getData(int icons[], String iconNames[]) {
        List<Map<String, Object>> data_list = new ArrayList<>();
        for (int i = 0; i < iconNames.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", icons[i]);
            map.put("text", iconNames[i]);
            data_list.add(map);
        }
        return data_list;
    }

    @OnClick({R.id.lately1, R.id.lately2, R.id.lately3, R.id.lately4})
    public void onclikc(View v) {
        switch (v.getId()) {
            case R.id.lately1:
                //电费
//                startActivity(new Intent(ServiceActivity.this, ChoiceMechanismActivity.class)
//                        .putExtra("type", "1"));
                //社保查询
                if (VerificationUtils.all(this)) {
//                    startActivity(new Intent(this, SocialSecurityActivity.class));

                    String type = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();
                    if ("1".equals(type)) {
                        ToastUtils.showMessage(ServiceActivity.this, "亲，该功能需要个人账号才能使用哦！");
                        return;
                    } else {
                        startActivity(new Intent(this, SocialInquiryActivity.class));
                    }


                }
                break;
            case R.id.lately2://公积金
                if (VerificationUtils.isLogin(ServiceActivity.this)) {
                    if (VerificationUtils.isAuthentication(ServiceActivity.this)) {

                        String type = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();
                        if ("1".equals(type)) {
                            ToastUtils.showMessage(ServiceActivity.this, "亲，该功能需要个人账号才能使用哦！");
                            return;
                        } else {
                            startActivity(new Intent(ServiceActivity.this, AccumulationFundActivity.class));
                        }
                    }
                }
                break;
            case R.id.lately3:
                //医保
                if (VerificationUtils.all(ServiceActivity.this)) {
                    String type = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();
                    if ("1".equals(type)) {
                        ToastUtils.showMessage(ServiceActivity.this, "亲，该功能需要个人账号才能使用哦！");
                        return;
                    } else {
                        startActivity(new Intent(ServiceActivity.this, InsuranceInquiriesActivity.class)
                                .putExtra("type", 1));
                    }

                }
                break;
            case R.id.lately4:
                //挂号
                startActivity(new Intent(ServiceActivity.this, PublicWebViewActivity.class)
                        .putExtra("url", "https://wy.guahao.com/")
                        .putExtra("title", "挂号"));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                ServiceActivity.this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
