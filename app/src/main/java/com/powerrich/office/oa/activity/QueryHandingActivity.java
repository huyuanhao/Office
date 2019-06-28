package com.powerrich.office.oa.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.DoThingListBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.view.pull.PullToRefreshBase;
import com.powerrich.office.oa.view.pull.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 办件查询界面
 *
 * @author Administrator
 */
public class QueryHandingActivity extends BaseActivity implements View.OnClickListener {

    private PullToRefreshListView mPullToRefreshListView;
    private List<DoThingListBean.DATABeanX.DATABean> mList = new ArrayList<>();
    private ListView mListView;

    private TextView tv_tab_status_0;
    private TextView tv_tab_status_1;
    private TextView tv_tab_status_2;
    private TextView tv_tab_status_3;
    private EditText et_query_content;
    private TextView tv_query;
    private TextView tv_no_more;

    private int select_tab = 0;

    private int currentPage = 1;

    private CommonAdapter<DoThingListBean.DATABeanX.DATABean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_queryhanding;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoginUtils.getInstance().isLoginSuccess()) {
            getSearch(true, select_tab + "", et_query_content.getText().toString().trim(),
                    currentPage + "");
        }
    }

    private void initView() {
        initTitleBar(R.string.title_activity_queryhanding, this, null);
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_queryhanding);
        mListView = BeanUtils.setProperty(mPullToRefreshListView);
//        mQueryHandingListAdapter = new QueryHandingListAdapter(this, infos);
//        mListView.setAdapter(mQueryHandingListAdapter);

        et_query_content = (EditText) findViewById(R.id.et_query_content);
        tv_query = (TextView) findViewById(R.id.tv_search);
        tv_query.setOnClickListener(this);

        tv_no_more = (TextView) findViewById(R.id.tv_nomore);

        tv_tab_status_0 = (TextView) findViewById(R.id.tv_tab_status_0);
        tv_tab_status_1 = (TextView) findViewById(R.id.tv_tab_status_1);
        tv_tab_status_2 = (TextView) findViewById(R.id.tv_tab_status_2);
        tv_tab_status_3 = (TextView) findViewById(R.id.tv_tab_status_3);

        tv_tab_status_1.setTextColor(Color.GRAY);
        tv_tab_status_2.setTextColor(Color.GRAY);
        tv_tab_status_3.setTextColor(Color.GRAY);

        tv_tab_status_0.setOnClickListener(this);
        tv_tab_status_1.setOnClickListener(this);
        tv_tab_status_2.setOnClickListener(this);
        tv_tab_status_3.setOnClickListener(this);

        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            // 上拉刷新数据
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                getSearch(true,select_tab + "", et_query_content.getText().toString().trim(),
                        currentPage + "");
            }

            // 下拉加载下一页的数据
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                getSearch(false,select_tab + "", et_query_content.getText().toString().trim(),
                        currentPage + "");
            }
        });
    }

    private void initData() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(QueryHandingActivity.this, QueryHandingDetailActivity.class);
                if (select_tab == 0) {
                    //暂存
                    intent.putExtra("zan_cun", true);
                }
                intent.putExtra("proKeyId", BeanUtils.isEmpty(mList) ? "" : mList.get(position).getPROKEYID());
                startActivity(intent);
            }
        });
        et_query_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    // 先隐藏键盘
                    ((InputMethodManager) et_query_content.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(QueryHandingActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    currentPage = 1;
                    getSearch(true,select_tab + "", et_query_content.getText().toString().trim(), currentPage + "");// 搜索
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 办件查询请求
     */
    private void getSearch(boolean isDiaLog, String state, String itemName, String currentPage) {
        ApiRequest request = OAInterface.queryBusinessList(state, itemName, currentPage);
        if (null != invoke)
            if (isDiaLog) {
                invoke.invokeWidthDialog(request, callBack);
            } else {
                invoke.invoke(request, callBack);

            }
    }

    /**
     * 获取办件查询数据解析
     *
     * @param list
     */
    private void showSearchData(List<DoThingListBean.DATABeanX.DATABean> list) {
        if (BeanUtils.isEmpty(list)) {
            tv_no_more.setVisibility(View.VISIBLE);
            mPullToRefreshListView.setVisibility(View.GONE);
            return;
        }
        mPullToRefreshListView.setVisibility(View.VISIBLE);
        tv_no_more.setVisibility(View.GONE);
        if (adapter == null) {
            adapter = new CommonAdapter<DoThingListBean.DATABeanX.DATABean>(QueryHandingActivity.this, mList, R.layout
                    .item_do_thing) {
                @Override
                public void convert(ViewHolder holder, DoThingListBean.DATABeanX.DATABean item) {
                    holder.setTextView(R.id.tv_query_title, item.getITEMNAME());
                    holder.setTextView(R.id.tv_time, item.getDESDATE_F().split(" ")[0]);
                    String a = item.getDESDATE_F().split(" ")[1];
                    String[] split = a.split("\\.");
                    holder.setTextView(R.id.tv_hour, split[0]);
                    if (item.getPROCESS_STATE().equals("0")) {
                        holder.setTextView(R.id.tv_query_state, mContext.getString(R.string.status_0));
                    } else if (item.getPROCESS_STATE().equals("1")) {
                        holder.setTextView(R.id.tv_query_state, mContext.getString(R.string.status_1));
                    } else if (item.getPROCESS_STATE().equals("2")) {
                        holder.setTextView(R.id.tv_query_state, mContext.getString(R.string.status_2));
                    } else if (item.getPROCESS_STATE().equals("3")) {
                        holder.setTextView(R.id.tv_query_state, mContext.getString(R.string.status_3));
                    }
                }
            };
            mListView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
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
                DoThingListBean doThingListBean = new DoThingListBean();
                doThingListBean.setDATA(new DoThingListBean.DATABeanX());
                doThingListBean.getDATA().setDATA(new ArrayList<DoThingListBean.DATABeanX.DATABean>());
                if (items != null) {
                    for (int k = 0; k < items.size(); k++) {
                        String desDate = items.get(k).getString("DESDATE");
                        String itemName = items.get(k).getString("ITEMNAME");
                        String process_state = items.get(k).getString("PROCESS_STATE");
                        String proKeyId = items.get(k).getString("PROKEYID");
                        DoThingListBean.DATABeanX.DATABean dataBean = new DoThingListBean.DATABeanX.DATABean();
                        dataBean.setDESDATE_F(desDate);
                        dataBean.setITEMNAME(itemName);
                        dataBean.setPROCESS_STATE(process_state);
                        dataBean.setPROKEYID(proKeyId);
                        doThingListBean.getDATA().getDATA().add(dataBean);
                    }
                }
                if (currentPage == 1 && !BeanUtils.isEmpty(mList)) {
                    mList.clear();
                }
                if (!BeanUtils.isEmpty(doThingListBean.getDATA().getDATA())) {
                    mList.addAll(doThingListBean.getDATA().getDATA());
                }
                showSearchData(mList);
                //判断是否已经全部加载完成：如果完成了就关闭“下拉加载更多”功能，否则，继续打开“下拉加载更多”功能
                if (BeanUtils.isEmpty(doThingListBean.getDATA().getDATA()) || doThingListBean.getDATA().getDATA().size
                        () < Constants.COMMON_PAGE) {
                    // 已经全部加载完成，关闭UI组件的下拉加载更多功能
                    mPullToRefreshListView.onPullDownRefreshComplete();
                    mPullToRefreshListView.onPullUpRefreshComplete();
                    mPullToRefreshListView.setHasMoreData(false);
                } else {
                    // 还有更多数据，继续打开“下拉加载更多”功能
                    mPullToRefreshListView.onPullDownRefreshComplete();
                    mPullToRefreshListView.onPullUpRefreshComplete();
                    mPullToRefreshListView.setHasMoreData(true);
                }
            } else {
                DialogUtils.showToast(QueryHandingActivity.this, message);
            }
        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                QueryHandingActivity.this.finish();
                break;

            case R.id.tv_tab_status_0:
                tv_tab_status_0.setBackgroundResource(R.drawable.select_tab_checked_left);
                tv_tab_status_0.setTextColor(Color.WHITE);
                tv_tab_status_1.setBackground(null);
                tv_tab_status_1.setTextColor(Color.GRAY);
                tv_tab_status_2.setBackground(null);
                tv_tab_status_2.setTextColor(Color.GRAY);
                tv_tab_status_3.setBackground(null);
                tv_tab_status_3.setTextColor(Color.GRAY);
                currentPage = 1;
                mListView.setSelectionAfterHeaderView();
                getSearch(false, "0", "", currentPage + "");
                et_query_content.setText("");
                select_tab = 0;
                break;
            case R.id.tv_tab_status_1:
                tv_tab_status_1.setBackgroundColor(getResources().getColor(R.color.blue_main));
                tv_tab_status_1.setTextColor(Color.WHITE);
                tv_tab_status_0.setBackground(null);
                tv_tab_status_0.setTextColor(Color.GRAY);
                tv_tab_status_2.setBackground(null);
                tv_tab_status_2.setTextColor(Color.GRAY);
                tv_tab_status_3.setBackground(null);
                tv_tab_status_3.setTextColor(Color.GRAY);
                currentPage = 1;
                mListView.setSelectionAfterHeaderView();
                getSearch(false, "1", "", currentPage + "");
                et_query_content.setText("");
                select_tab = 1;
                break;
            case R.id.tv_tab_status_2:
                tv_tab_status_2.setBackgroundColor(getResources().getColor(R.color.blue_main));
                tv_tab_status_2.setTextColor(Color.WHITE);
                tv_tab_status_1.setBackground(null);
                tv_tab_status_1.setTextColor(Color.GRAY);
                tv_tab_status_0.setBackground(null);
                tv_tab_status_0.setTextColor(Color.GRAY);
                tv_tab_status_3.setBackground(null);
                tv_tab_status_3.setTextColor(Color.GRAY);
                currentPage = 1;
                mListView.setSelectionAfterHeaderView();
                getSearch(false, "2", "", currentPage + "");
                et_query_content.setText("");
                select_tab = 2;
                break;
            case R.id.tv_tab_status_3:
                tv_tab_status_3.setBackgroundResource(R.drawable.select_tab_checked_right);
                tv_tab_status_3.setTextColor(Color.WHITE);
                tv_tab_status_1.setBackground(null);
                tv_tab_status_1.setTextColor(Color.GRAY);
                tv_tab_status_2.setBackground(null);
                tv_tab_status_2.setTextColor(Color.GRAY);
                tv_tab_status_0.setBackground(null);
                tv_tab_status_0.setTextColor(Color.GRAY);
                currentPage = 1;
                mListView.setSelectionAfterHeaderView();
                getSearch(false, "3", "", currentPage + "");
                et_query_content.setText("");
                select_tab = 3;
                break;
            case R.id.tv_search:
                String searchText = et_query_content.getText().toString().trim();
                if (BeanUtils.isEmptyStr(searchText)){
                    DialogUtils.showToast(QueryHandingActivity.this, "请输入搜索内容");
                } else {
                    currentPage = 1;
                    getSearch(true, select_tab + "", searchText, currentPage + "");
                }
                break;
            default:
                break;
        }
    }
}
