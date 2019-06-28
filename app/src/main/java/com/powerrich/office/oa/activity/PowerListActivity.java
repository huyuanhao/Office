package com.powerrich.office.oa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonRvAdapter;
import com.powerrich.office.oa.adapter.ViewHolderRv;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.PowerListInfo;
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
 * 文 件 名：PowerListActivity
 * 描   述：阳光政务行政权力清单列表界面
 * 作   者：Wangzheng
 * 时   间：2018/1/23
 * 版   权：v1.0
 */
public class PowerListActivity extends BaseActivity implements View.OnClickListener, OnRefreshLoadmoreListener {

    private TextView tv_no_data;
    private List<PowerListInfo> powerListInfoList = new ArrayList<>();
    private CommonRvAdapter<PowerListInfo> adapter;
    private int currentPage = 1;
    private int index;
    private String siteNo, siteName;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refresh_layout;
    private EditText et_query_content;
    private TextView tv_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getIntent().getIntExtra("index", 0);
        siteNo = getIntent().getStringExtra("siteNo");
        siteName = getIntent().getStringExtra("siteName");
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_list;
    }

    private void initView() {
        initTitleBar(R.string.power_list, this, null);
        refresh_layout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        et_query_content = (EditText) findViewById(R.id.et_query_content);
        tv_query = (TextView) findViewById(R.id.tv_search);
        tv_query.setOnClickListener(this);
    }

    private void initData() {
        refresh_layout.setOnRefreshLoadmoreListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        et_query_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_query_content.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(PowerListActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    load(true);// 搜索
                    return true;
                }
                return false;
            }
        });
        load(true);
    }

    /**
     * 获取行政权力清单-查询行政权力清单列表请求
     * @param currentPage
     */
    private void getPowerList(String sitePower, String name, int currentPage) {
        ApiRequest request = OAInterface.getPowerList(sitePower, name, String.valueOf(currentPage));
        invoke.invoke(request, callBack);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                ResultItem data = (ResultItem) item.get("DATA");
                List<ResultItem> items = data.getItems("DATA");
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
                DialogUtils.showToast(PowerListActivity.this, message);
            }
        }
    };


    /**
     * 获取行政权力清单-查询行政权力清单列表数据解析
     * @param items
     */
    private void parseData(List<ResultItem> items) {
        if (currentPage == 1 && powerListInfoList.size() > 0) {
            powerListInfoList.clear();
        }
        if (!BeanUtils.isEmpty(items)) {
            for (ResultItem item : items) {
                PowerListInfo powerListInfo = new PowerListInfo();
                powerListInfo.setItemName(item.getString("ITEMNAME"));
                powerListInfo.setDepartment(item.getString("NORMACCEPTDEPART"));
                powerListInfo.setDepartmentId(item.getString("NORMACCEPTDEPARTID"));
                powerListInfo.setPid(item.getString("PID"));
                powerListInfo.setPowerTypeName(item.getString("POWER_TYPE_NAME"));
                powerListInfo.setUpdateTime(item.getString("UPDATETIME"));
                powerListInfoList.add(powerListInfo);
            }
        }
        if (BeanUtils.isEmpty(powerListInfoList)) {
            tv_no_data.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
        }
        if (adapter == null) {
            adapter = new CommonRvAdapter<PowerListInfo>(powerListInfoList, R.layout.government_affairs_item) {
                @Override
                public void convert(ViewHolderRv holder, PowerListInfo item, int position) {
                    holder.setText(R.id.tv_department, "[" + item.getPowerTypeName() + "]");
                    TextView tv_department = holder.getItemView(R.id.tv_department);
                    tv_department.setTextColor(ContextCompat.getColor(PowerListActivity.this, R.color.blue_main));
                    holder.setText(R.id.text, item.getItemName());
                }
            };
            adapter.setOnItemClickListener(new CommonRvAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(PowerListActivity.this, PowerListDetailActivity.class);
                    intent.putExtra("pid", powerListInfoList.get(position).getPid());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setData(powerListInfoList);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                PowerListActivity.this.finish();
                break;
            case R.id.tv_search:
                // 通过输入的条件进行查询
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
        if (index == 0) {
            getPowerList(siteNo, et_query_content.getText().toString().trim(), currentPage);
        } else {
            getPowerList(siteName, et_query_content.getText().toString().trim(), currentPage);
        }
    }
}
