package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.adapter.CommonRvAdapter;
import com.powerrich.office.oa.adapter.ViewHolderRv;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.ApproveServicesInfo;
import com.powerrich.office.oa.bean.DepartmentInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.view.pull.PullToRefreshBase;
import com.powerrich.office.oa.view.pull.PullToRefreshListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名：AdminPowerListActivity
 * 描   述：阳光政务行政权力清单界面
 * 作   者：Wangzheng
 * 时   间：2017/12/7
 * 版   权：v1.0
 */
public class AdminPowerListActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener {

    private static final int DEPARTMENT_LIST_REQ = 0;
    private static final int CATEGORY_LIST_REQ = 1;
    private TextView tv_department_list, tv_category_list;
    private ArrayList<DepartmentInfo> departmentInfoList = new ArrayList<>();
    private CommonRvAdapter<DepartmentInfo> adapter;
    private int index = 0;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refresh_layout;
    private TextView tv_no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        getSiteList();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_admin_power_list;
    }

    private void initView() {
        initTitleBar(R.string.admin_power_list, this, null);
        tv_department_list = (TextView) findViewById(R.id.tv_department_list);
        tv_category_list = (TextView) findViewById(R.id.tv_category_list);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        refresh_layout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tv_department_list.setOnClickListener(this);
        tv_category_list.setOnClickListener(this);
        tv_department_list.setOnClickListener(this);
        tv_category_list.setOnClickListener(this);
        tv_category_list.setTextColor(Color.GRAY);
    }

    private void initData() {
        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setEnableLoadmore(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    /**
     * 获取部门列表请求
     */
    private void getSiteList() {
        ApiRequest request = OAInterface.getSiteList();
        invoke.invoke(request, callBack, DEPARTMENT_LIST_REQ);
    }

    /**
     * 获取全部行政类别请求
     */
    private void getCategoryList() {
        ApiRequest request = OAInterface.getCategoryList();
        invoke.invoke(request, callBack, CATEGORY_LIST_REQ);
    }

    /**
     * 获取部门列表和全部行政类别数据解析
     *
     * @param items
     */
    private void parseData(List<ResultItem> items) {
        if (departmentInfoList.size() > 0) {
            departmentInfoList.clear();
        }
        if (!BeanUtils.isEmpty(items)) {
            if (index == 0) {
                for (ResultItem item : items) {
                    DepartmentInfo deptInfo = new DepartmentInfo();
                    deptInfo.setSiteName(item.getString("SITE_NAME"));
                    deptInfo.setSiteNo(item.getString("SITE_NO"));
                    departmentInfoList.add(deptInfo);
                }
            } else {
                for (ResultItem item : items) {
                    DepartmentInfo deptInfo = new DepartmentInfo();
                    deptInfo.setSiteName(item.getString("DISP_VIEW"));
                    deptInfo.setSiteNo(item.getString("XZLBID"));
                    departmentInfoList.add(deptInfo);
                }
            }
        }
        if (BeanUtils.isEmpty(departmentInfoList)) {
            tv_no_data.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
        }
        if (adapter == null) {
            adapter = new CommonRvAdapter<DepartmentInfo>(departmentInfoList, R.layout.government_affairs_item) {
                @Override
                public void convert(ViewHolderRv holder, DepartmentInfo item, int position) {
                    holder.getItemView(R.id.tv_department).setVisibility(View.GONE);
                    holder.setText(R.id.text, item.getSiteName());
                }
            };
            adapter.setOnItemClickListener(new CommonRvAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(AdminPowerListActivity.this, PowerListActivity.class);
                    intent.putExtra("index", index);
                    intent.putExtra("siteNo", departmentInfoList.get(position).getSiteNo());
                    intent.putExtra("siteName", departmentInfoList.get(position).getSiteName());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setData(departmentInfoList);
        }
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                List<ResultItem> items = item.getItems("DATA");
                if (what == DEPARTMENT_LIST_REQ) {
                    parseData(items);
                } else if (what == CATEGORY_LIST_REQ) {
                    parseData(items);
                }
            } else {
                DialogUtils.showToast(AdminPowerListActivity.this, message);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                AdminPowerListActivity.this.finish();
                break;
            case R.id.tv_department_list:
                tv_department_list.setBackgroundResource(R.drawable.select_tab_checked_left);
                tv_department_list.setTextColor(ContextCompat.getColor(AdminPowerListActivity.this, R.color.white));
                tv_category_list.setBackground(null);
                tv_category_list.setTextColor(Color.GRAY);
                index = 0;
                load(index);
                break;
            case R.id.tv_category_list:
                tv_category_list.setBackgroundResource(R.drawable.select_tab_checked_right);
                tv_category_list.setTextColor(ContextCompat.getColor(AdminPowerListActivity.this, R.color.white));
                tv_department_list.setBackground(null);
                tv_department_list.setTextColor(Color.GRAY);
                index = 1;
                load(index);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        load(index);
        refresh_layout.finishRefresh();
    }

    private void load(int index){
        if (index == 0) {
            getSiteList();
        } else {
            getCategoryList();
        }
    }
}
