package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonRvAdapter;
import com.powerrich.office.oa.adapter.ViewHolderRv;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.ApproveServicesInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名：AdminApproveServicesListActivity
 * 描   述：阳光政务行政审批中介服务清单界面
 * 作   者：Wangzheng
 * 时   间：2017/12/8
 * 版   权：v1.0
 */
public class AdminApproveServicesListActivity extends BaseActivity implements View.OnClickListener, OnRefreshLoadmoreListener {

    private TextView tv_department_list, tv_category_list;
    private List<ApproveServicesInfo> approveServicesInfoList = new ArrayList<>();
    private CommonRvAdapter<ApproveServicesInfo> adapter;
    private String type = "0";
    private int currentPage = 1;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refresh_layout;
    private TextView tv_no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_admin_power_list;
    }

    private void initView() {
        initTitleBar(R.string.admin_approve_intermediary_services_list, this, null);
        tv_department_list = (TextView) findViewById(R.id.tv_department_list);
        tv_category_list = (TextView) findViewById(R.id.tv_category_list);
        tv_department_list.setText(R.string.reserve_admin_approve_services_list);
        tv_category_list.setText(R.string.cancel_admin_approve_services_list);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        refresh_layout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tv_department_list.setOnClickListener(this);
        tv_category_list.setOnClickListener(this);
        tv_category_list.setTextColor(Color.GRAY);
    }

    private void initData() {
        refresh_layout.setOnRefreshLoadmoreListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        load(true);
    }

    /**
     * 查询行政审批中介服务清单请求
     * @param type
     * @param currentPage
     */
    private void getAdminApproveServicesList(String type, int currentPage) {
        ApiRequest request = OAInterface.getApproveServicesList(type, "", String.valueOf(currentPage));
        invoke.invoke(request, callBack);
    }

    /**
     * 获取部门列表和全部行政类别数据解析
     *
     * @param items
     */
    private void parseData(List<ResultItem> items) {
        if (currentPage == 1 && !BeanUtils.isEmpty(approveServicesInfoList)) {
            approveServicesInfoList.clear();
        }
        if (!BeanUtils.isEmpty(items)) {
            for (ResultItem item : items) {
                ApproveServicesInfo approveServicesInfo = new ApproveServicesInfo();
                approveServicesInfo.setCreateTime(item.getString("CREATETIME"));
                approveServicesInfo.setSiteName(item.getString("SITENAME"));
                approveServicesInfo.setSiteNo(item.getString("SITENO"));
                approveServicesInfo.setServicesName(item.getString("ZJFWMC"));
                approveServicesInfo.setServicesId(item.getString("ZJID"));
                approveServicesInfoList.add(approveServicesInfo);
            }
        }
        if (BeanUtils.isEmpty(approveServicesInfoList)) {
            tv_no_data.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
        }
        if (adapter == null) {
            adapter = new CommonRvAdapter<ApproveServicesInfo>(approveServicesInfoList, R.layout.government_affairs_item) {
                @Override
                public void convert(ViewHolderRv holder, ApproveServicesInfo item, int position) {
                    holder.getItemView(R.id.tv_department).setVisibility(View.GONE);
                    holder.setText(R.id.text, item.getServicesName());
                }
            };
            adapter.setOnItemClickListener(new CommonRvAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(AdminApproveServicesListActivity.this, AdminApproveServicesDetailActivity.class);
                    intent.putExtra("services_id", approveServicesInfoList.get(position).getServicesId());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setData(approveServicesInfoList);
        }
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                ResultItem result = (ResultItem) item.get("DATA");
                List<ResultItem> items = result.getItems("DATA");
                parseData(items);
                //判断是否已经全部加载完成：如果完成了就关闭“下拉加载更多”功能，否则，继续打开“下拉加载更多”功能
                if (BeanUtils.isEmpty(items) || items.size() < Constants.COMMON_PAGE) {
                    // 已经全部加载完成，关闭UI组件的下拉加载更多功能
                    refresh_layout.setLoadmoreFinished(true);
                } else {
                    // 还有更多数据，继续打开“下拉加载更多”功能
                    refresh_layout.setLoadmoreFinished(false);
                }
            } else {
                DialogUtils.showToast(AdminApproveServicesListActivity.this, message);
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (!BeanUtils.isEmpty(approveServicesInfoList)) {
            approveServicesInfoList.clear();
        }
        switch (v.getId()) {
            case R.id.system_back:
                AdminApproveServicesListActivity.this.finish();
                break;
            case R.id.tv_department_list:
                tv_department_list.setBackgroundResource(R.drawable.select_tab_checked_left);
                tv_department_list.setTextColor(ContextCompat.getColor(AdminApproveServicesListActivity.this, R.color.white));
                tv_category_list.setBackground(null);
                tv_category_list.setTextColor(Color.GRAY);
                type = "0";
                load(true);
                break;
            case R.id.tv_category_list:
                tv_category_list.setBackgroundResource(R.drawable.select_tab_checked_right);
                tv_category_list.setTextColor(ContextCompat.getColor(AdminApproveServicesListActivity.this, R.color.white));
                tv_department_list.setBackground(null);
                tv_department_list.setTextColor(Color.GRAY);
                type = "1";
                load(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        load(false);
        refresh_layout.finishLoadmore();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        load(true);
        refresh_layout.finishRefresh();
    }

    private void load(boolean flag){
        if (flag) {
            currentPage = 1;
            refresh_layout.setLoadmoreFinished(false);
        } else {
            currentPage++;
        }
        getAdminApproveServicesList(type, currentPage);
    }
}
