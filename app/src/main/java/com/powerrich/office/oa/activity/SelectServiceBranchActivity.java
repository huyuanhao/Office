package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonRvAdapter;
import com.powerrich.office.oa.adapter.ViewHolderRv;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.ServiceBranchBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 文 件 名：SelectServiceBranchActivity
 * 描   述：选择服务网点
 * 作   者：Wangzheng
 * 时   间：2018-7-25 09:23:56
 * 版   权：v1.0
 */
public class SelectServiceBranchActivity extends BaseActivity implements View.OnClickListener {
    private static final int GET_ALL_FILE_LIST_BY_USER_CODE = 0;
    private LinearLayout layout_search;
    private SmartRefreshLayout refresh_layout;
    private RecyclerView recycler_view;
    private TextView tv_no_data;
    private CommonRvAdapter<ServiceBranchBean> adapter;
    private int currentPage = 1;
    private List<ServiceBranchBean> beanList = new ArrayList<>();
    private int selectedPosition = -1;
    private int currentRows = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_materials_depot;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!BeanUtils.isEmpty(beanList)) {
            beanList.clear();
        }
        queryServiceBranchList();
    }

    private void initView() {
        initTitleBar(getString(R.string.service_branch), getString(R.string.confirm), this, this);
        layout_search = findViewById(R.id.layout_search);
        layout_search.setVisibility(View.GONE);
        refresh_layout = findViewById(R.id.refresh_layout);
        recycler_view = findViewById(R.id.recycler_view);
        tv_no_data =                                                                                                                                                findViewById(R.id.tv_no_data);
    }

    private void initData() {
        recycler_view.setLayoutManager(new LinearLayoutManager(SelectServiceBranchActivity.this));
        refresh_layout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refresh_layout.finishRefresh();
                currentPage = 1;
                queryServiceBranchList();
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refresh_layout.finishLoadmore();
                currentPage++;
                queryServiceBranchList();
            }

        });
    }

    /**
     * 获取办理服务网点请求
     */
    private void queryServiceBranchList() {
        ApiRequest request = OAInterface.queryServiceBranchList("", String.valueOf(currentPage));
        invoke.invokeWidthDialog(request, callBack, GET_ALL_FILE_LIST_BY_USER_CODE);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == GET_ALL_FILE_LIST_BY_USER_CODE) {
                    ResultItem result = (ResultItem) item.get("DATA");
                    if (currentPage == 1 && !BeanUtils.isEmpty(beanList)) {
                        beanList.clear();
                        currentRows = 0;
                    }
                    currentRows += result.getInt("CURRENTROWS");
                    if (currentRows == result.getInt("ROWS")) {
                        refresh_layout.setLoadmoreFinished(true);
                    } else {
                        refresh_layout.setLoadmoreFinished(false);
                    }
                    List<ResultItem> items = result.getItems("DATA");
                    parseData(items);
                }
            } else {
                DialogUtils.showToast(SelectServiceBranchActivity.this, message);
            }
        }
    };

    private void parseData(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            tv_no_data.setVisibility(View.VISIBLE);
            return;
        }
        tv_no_data.setVisibility(View.GONE);
        for (ResultItem item : items) {
            ServiceBranchBean bean = new ServiceBranchBean();
            bean.setCREAT_TIME(item.getString("CREAT_TIME"));
            bean.setID(item.getString("ID"));
            bean.setNAME(item.getString("NAME"));
            bean.setSXWINDOWS_ID(item.getString("SXWINDOWS_ID"));
            bean.setSXWINDOW_NAME(item.getString("SXWINDOW_NAME"));
            beanList.add(bean);
        }
        if (null == adapter) {
            adapter = new CommonRvAdapter<ServiceBranchBean>(beanList, R.layout.service_branch_item) {
                @Override
                public void convert(ViewHolderRv holder, ServiceBranchBean item, int position) {
                    holder.setText(R.id.tv_branch_name, item.getNAME());
                    holder.setText(R.id.tv_window, item.getSXWINDOW_NAME());
                    CheckBox cb_selected = holder.getItemView(R.id.cb_selected);
                    if (selectedPosition == position) {
                        cb_selected.setChecked(true);
                    } else {
                        cb_selected.setChecked(false);
                    }
                }
            };
            recycler_view.setAdapter(adapter);
        } else {
            adapter.setData(beanList);
        }
        adapter.setOnItemClickListener(new CommonRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                beanList.get(position).setSelected(!beanList.get(position).isSelected());
                if (selectedPosition != -1 && selectedPosition != position) {
                    beanList.get(selectedPosition).setSelected(false);
                }
                selectedPosition = position;
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                SelectServiceBranchActivity.this.finish();
                break;
            case R.id.tv_top_right:
                List<ServiceBranchBean> branchBeanList = new ArrayList<>();
                if (!BeanUtils.isEmpty(beanList)) {
                    for (ServiceBranchBean serviceBranchBean : beanList) {
                        if (serviceBranchBean.isSelected()) {
                            branchBeanList.add(serviceBranchBean);
                        }
                    }
                    if (BeanUtils.isEmpty(branchBeanList)) {
                        DialogUtils.showToast(SelectServiceBranchActivity.this, "请选择服务网点！");
                        return;
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("branchBeanList", (Serializable) branchBeanList);
                setResult(RESULT_OK, intent);
                SelectServiceBranchActivity.this.finish();
                break;
        }
    }
}
