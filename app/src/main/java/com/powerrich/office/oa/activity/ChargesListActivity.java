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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.powerrich.office.oa.bean.ChargeListInfo;
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
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名：ChargesListActivity
 * 描   述：阳光政务行政事业性收费清单界面
 * 作   者：Wangzheng
 * 时   间：2017/12/4
 * 版   权：v1.0
 */
public class ChargesListActivity extends BaseActivity implements View.OnClickListener, OnRefreshLoadmoreListener {

    private TextView tv_no_data;
    private LinearLayout ll_search;
    private EditText et_query_content;
    private TextView tv_query;
    private List<ChargeListInfo> mChargeListInfo = new ArrayList<>();
    private CommonRvAdapter<ChargeListInfo> adapter;
    private int currentPage = 1;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refresh_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_list;
    }

    private void initView() {
        initTitleBar(R.string.charge_list, this, null);
        ll_search = (LinearLayout) findViewById(R.id.layout_search);
        ll_search.setVisibility(View.VISIBLE);
        refresh_layout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        et_query_content = (EditText) findViewById(R.id.et_query_content);
        tv_query = (TextView) findViewById(R.id.tv_search);
        initData();
    }

    private void initData() {
        tv_query.setOnClickListener(this);
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
                            .hideSoftInputFromWindow(ChargesListActivity.this
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
     * 获取阳光政务行政事业性收费清单请求
     */
    private void getChargeItemList(String chargeProject, int currentPage) {
        ApiRequest request = OAInterface.getChargeItemList(chargeProject, String.valueOf(currentPage));
        invoke.invoke(request, callBack);
    }

    /**
     * 获取阳光政务行政事业性收费清单数据解析
     *
     * @param items
     */
    private void parseData(List<ResultItem> items) {
        if (currentPage == 1 && mChargeListInfo.size() > 0) {
            mChargeListInfo.clear();
        }
        if (!BeanUtils.isEmpty(items)) {
            for (ResultItem item : items) {
                String listId = item.getString("LISTID");
                String chargeProject = item.getString("SFXM");
                String siteName = item.getString("SITENAME");
                String siteNo = item.getString("SITENO");
                ChargeListInfo chargeListInfo = new ChargeListInfo();
                chargeListInfo.setListId(listId);
                chargeListInfo.setChargeProject(chargeProject);
                chargeListInfo.setSiteName(siteName);
                chargeListInfo.setSiteNo(siteNo);
                mChargeListInfo.add(chargeListInfo);
            }
        }
        if (BeanUtils.isEmpty(mChargeListInfo)) {
            tv_no_data.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
        }
        if (adapter == null) {
            adapter = new CommonRvAdapter<ChargeListInfo>(mChargeListInfo, R.layout.government_affairs_item) {
                @Override
                public void convert(ViewHolderRv holder, ChargeListInfo item, int position) {
                    holder.setText(R.id.tv_department, "[" + item.getSiteName() + "]");
                    TextView tv_department = holder.getItemView(R.id.tv_department);
                    tv_department.setTextColor(ContextCompat.getColor(ChargesListActivity.this, R.color.blue_main));
                    holder.setText(R.id.text, item.getChargeProject());
                }
            };
            adapter.setOnItemClickListener(new CommonRvAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(ChargesListActivity.this, ChargesDetailActivity.class);
                    intent.putExtra("list_id", mChargeListInfo.get(position).getListId());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setData(mChargeListInfo);
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
                DialogUtils.showToast(ChargesListActivity.this, message);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                ChargesListActivity.this.finish();
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
        getChargeItemList(et_query_content.getText().toString().trim(), currentPage);
    }
}
