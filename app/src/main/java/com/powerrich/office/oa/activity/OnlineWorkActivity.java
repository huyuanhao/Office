package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.RecentlyItemAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.RecentlyItemBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.view.GradationScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Cc个人办事
 * 文 件 名：OnlineWorkActivity
 * 描   述：个人、企业在线办事
 * 作   者：Wangzheng
 * 时   间：2018-6-6 17:07:29
 * 版   权：v1.0
 */
public class OnlineWorkActivity extends BaseActivity implements View.OnClickListener, GradationScrollView.ScrollViewListener {


    private GradationScrollView gradationScrollView;
    private TextView tvTopTitle;
    private RelativeLayout titleLayout;
    private ImageView banner;
    private LinearLayout ll_recently;
    private GridView gv_list;
    private List<RecentlyItemBean> itemBeans = new ArrayList<>();
    private RecentlyItemAdapter adapter;
    //判断首页点击的是哪个办事 type类型2.个人办事   type类型1.企业办事
    private String type;
    private int height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");

        initView();
        initListeners();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_online_work;
    }

    /**
     * 获取顶部图片高度后，设置滚动监听
     */
    private void initListeners() {
        gradationScrollView = (GradationScrollView) findViewById(R.id.gradation_scroll_view);
        banner = (ImageView) findViewById(R.id.banner);
        titleLayout = (RelativeLayout) findViewById(R.id.title_layout);
        tvTopTitle = (TextView) findViewById(R.id.tv_top_title);
        ViewTreeObserver vto = banner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                titleLayout.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = titleLayout.getHeight();

                gradationScrollView.setScrollViewListener(OnlineWorkActivity.this);
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

    private void initView() {

        if (type.equals(Constants.PERSONAL_WORK_TYPE)) {
            initTitleBar(R.string.personal_work, this, this);
        } else {
            initTitleBar(R.string.enterprise_work, this, this);
        }
        ImageView iv = (ImageView) findViewById(R.id.btn_top_right);
        findViewById(R.id.ll_online_work).setOnClickListener(this);
        findViewById(R.id.ll_order_work).setOnClickListener(this);
        findViewById(R.id.ll_do_thing_query).setOnClickListener(this);
        findViewById(R.id.ll_enterprise_information_query).setOnClickListener(this);
        findViewById(R.id.ll_admin_power_list).setOnClickListener(this);
        findViewById(R.id.ll_department_duty_list).setOnClickListener(this);
        findViewById(R.id.ll_admin_charge_list).setOnClickListener(this);
        findViewById(R.id.ll_admin_approve_intermediary_services_list).setOnClickListener(this);
        findViewById(R.id.ll_market_admittance_list).setOnClickListener(this);
        iv.setImageResource(R.drawable.icon_search_common);
        ll_recently = (LinearLayout) findViewById(R.id.ll_recently);
        gv_list = (GridView) findViewById(R.id.gv_list);
    }

    private void initData() {
        gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OnlineWorkActivity.this, WorkGuideNewActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("item_id", itemBeans.get(position).getItemId());
                intent.putExtra("item_name", itemBeans.get(position).getItemName());
                intent.putExtra("typeHome", itemBeans.get(position).getBSDX());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!BeanUtils.isEmpty(itemBeans)) {
            itemBeans.clear();
        }
        getReaderItem();
    }

    /**
     * 获取查询最近浏览的事项信息(4条数据)请求
     */
    private void getReaderItem() {
        ApiRequest request = OAInterface.getReaderItem(type);
        invoke.invoke(request, callBack);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                List<ResultItem> items = item.getItems("DATA");
                parseData(items);
            } else {
                DialogUtils.showToast(OnlineWorkActivity.this, message);
            }
        }

    };

    private void parseData(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            ll_recently.setVisibility(View.GONE);
            return;
        }
        ll_recently.setVisibility(View.VISIBLE);
        for (ResultItem item : items) {
            RecentlyItemBean bean = new RecentlyItemBean();
            bean.setBrowseTime(item.getString("BROWSETIME"));
            bean.setId(item.getString("ID"));
            bean.setItemId(item.getString("ITEMID"));
            bean.setItemName(item.getString("ITEMNAME"));
            bean.setUserId(item.getString("USERID"));
            bean.setBSDX(item.getString("BROWSE_TYPE"));
            itemBeans.add(bean);
        }
        if (adapter == null) {
            adapter = new RecentlyItemAdapter(OnlineWorkActivity.this, itemBeans);
            gv_list.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void gotoActivity(Class c) {
        startActivity(new Intent(OnlineWorkActivity.this, c));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                OnlineWorkActivity.this.finish();
                break;
            case R.id.btn_top_right:
                gotoActivity(WorkGuideActivity.class);
                break;
                //在线办事
            case R.id.ll_online_work:
                // 在线个人、企业办事
//                gotoActivity(WorkActivity.class);
      //         startActivity(new Intent(OnlineWorkActivity.this, WorkActivity.class).putExtra("type", type));
                startActivity(new Intent(OnlineWorkActivity.this, WorkNewActivity.class).putExtra("type", type).putExtra("typeChild","1"));
                break;
                //预约办事
            case R.id.ll_order_work:
                // 在线预约
         //   startActivity(new Intent(OnlineWorkActivity.this, OnlineBookingActivity.class).putExtra("type", type));
                startActivity(new Intent(OnlineWorkActivity.this, WorkNewActivity.class).putExtra("type", type).putExtra("typeChild","2"));
                break;
            case R.id.ll_do_thing_query:
                // 办件查询
                gotoActivity(DoThingQueryActivity.class);
                break;
                //办事指南
            case R.id.ll_enterprise_information_query:
              //  gotoActivity(DoThingQueryActivity.class);
                startActivity(new Intent(OnlineWorkActivity.this, WorkNewActivity.class).putExtra("type", type).putExtra("typeChild","3"));


                break;
            case R.id.ll_admin_power_list:
                // 行政权力清单
                gotoActivity(AdminPowerListActivity.class);
                break;
            case R.id.ll_department_duty_list:
                // 部门责任清单
                gotoActivity(DepartmentDutyListActivity.class);
                break;
            case R.id.ll_admin_charge_list:
                // 行政事业性收费清单
                gotoActivity(ChargesListActivity.class);
                break;
            case R.id.ll_admin_approve_intermediary_services_list:
                // 行政审批中介服务清单
                gotoActivity(AdminApproveServicesListActivity.class);
                break;
            case R.id.ll_market_admittance_list:
                // 市场准入清单
                gotoActivity(MarketAdmittanceListActivity.class);
                break;
            default:
                break;
        }
    }
}
