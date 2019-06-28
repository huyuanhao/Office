package com.powerrich.office.oa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.IWantListBean;
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
 * @author dir_wang
 * @title 个人中心个人建议，咨询，投诉列表界面
 */
public class IWantListActivity extends BaseActivity implements View.OnClickListener {
    private String iwant_type;
    private PullToRefreshListView lv_iwant;
    private List<IWantListBean.DATABeanX.DATABean> mList = new ArrayList<>();
    private TextView tv_no_data;

    private int currentPage = 1;
    private ListView mListView;
    private CommonAdapter<IWantListBean.DATABeanX.DATABean> adapter;
    private EditText et_query_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iwant_type = getIntent().getStringExtra("type");
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_iwant_list;
    }

    private void initView() {
        if (iwant_type.equals(Constants.CONSULTING_TYPE)) {
            initTitleBar(R.string.iwant_consulting, this, null);
        } else if (iwant_type.equals(Constants.COMPLAIN_TYPE)) {
            initTitleBar(R.string.iwant_complain, this, null);
        } else if (iwant_type.equals(Constants.SUGGEST_TYPE)) {
            initTitleBar(R.string.iwant_suggest, this, null);
        }
        et_query_content = (EditText) findViewById(R.id.et_query_content);
        findViewById(R.id.tv_search).setOnClickListener(this);
        lv_iwant = (PullToRefreshListView) findViewById(R.id.lv_iwant);

        et_query_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    // 先隐藏键盘
                    ((InputMethodManager) et_query_content.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(IWantListActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    getIWantList(true, getSearchText());// 搜索
                    return true;
                }
                return false;
            }
        });

        lv_iwant.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            // 上拉刷新数据
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                getIWantList(true, getSearchText());
            }

            // 下拉加载下一页的数据
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                getIWantList(false, getSearchText());
            }
        });

        mListView = BeanUtils.setProperty(lv_iwant);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(IWantListActivity.this, IWantSuggestActivity.class);
                intent.putExtra("iwant_type", iwant_type);
                intent.putExtra("id", mList.get(position).getJL_ID());
                intent.putExtra("isShowDetail", true);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoginUtils.getInstance().isLoginSuccess()) {
            getIWantList(true, getSearchText());
        }
    }
    /**
     * 个人中心个人建议，咨询，投诉列表请求
     */
    private void getIWantList(boolean isDiaLog, String searchText) {
        ApiRequest request = OAInterface.getIWantList(iwant_type,searchText,currentPage+"");
        if (null != invoke)
            if (isDiaLog) {
                invoke.invokeWidthDialog(request, callBack);
            } else {
                invoke.invoke(request, callBack);
            }
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                String jsonStr = item.getJsonStr();
                Gson gson = new Gson();
                IWantListBean iWantListBean = gson.fromJson(jsonStr, IWantListBean.class);
                if (currentPage == 1 && !BeanUtils.isEmpty(mList)) {
                    mList.clear();
                }
                if(!BeanUtils.isEmpty(iWantListBean.getDATA().getDATA())) {
                    mList.addAll(iWantListBean.getDATA().getDATA());
                }
                showIWantList(mList);
                //判断是否已经全部加载完成：如果完成了就关闭“下拉加载更多”功能，否则，继续打开“下拉加载更多”功能
                if (BeanUtils.isEmpty(iWantListBean.getDATA().getDATA()) || iWantListBean.getDATA().getDATA().size
                        () < Constants.COMMON_PAGE) {
                    // 已经全部加载完成，关闭UI组件的下拉加载更多功能
                    lv_iwant.onPullDownRefreshComplete();
                    lv_iwant.onPullUpRefreshComplete();
                    lv_iwant.setHasMoreData(false);
                } else {
                    // 还有更多数据，继续打开“下拉加载更多”功能
                    lv_iwant.onPullDownRefreshComplete();
                    lv_iwant.onPullUpRefreshComplete();
                    lv_iwant.setHasMoreData(true);
                }
            } else {
                DialogUtils.showToast(IWantListActivity.this, message);
            }
        }

    };


    protected void showIWantList(List<IWantListBean.DATABeanX.DATABean> list) {
        if (BeanUtils.isEmpty(list)) {
            tv_no_data.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            return;
        }
        mListView.setVisibility(View.VISIBLE);
        tv_no_data.setVisibility(View.GONE);
        if (adapter == null) {
            adapter = new CommonAdapter<IWantListBean.DATABeanX.DATABean>(IWantListActivity.this, mList, R.layout
                    .item_do_thing) {
                @Override
                public void convert(ViewHolder holder, IWantListBean.DATABeanX.DATABean item) {
                    holder.setTextView(R.id.tv_query_title, item.getTITLE());
                    holder.setTextView(R.id.tv_time, item.getCRETTIME().split(" ")[0]);
                    holder.setTextView(R.id.tv_hour, item.getCRETTIME().split(" ")[1]);

//            if (dataBean.getISREVERT().equals("0")) {
//                holder.tv_query_state.setText(mContext.getString(R.string.status_0));
//            } else if (dataBean.getISREVERT().equals("1")){
//                holder.tv_query_state.setText(mContext.getString(R.string.status_1));
//            } else if (dataBean.getISREVERT().equals("2")){
//                holder.tv_query_state.setText(mContext.getString(R.string.status_2));
//            }else if (dataBean.getISREVERT().equals("3")){
//                holder.tv_query_state.setText(mContext.getString(R.string.status_3));
//            }
                    if (item.getISREVERT().equals("1")) {
                        holder.setTextView(R.id.tv_query_state, mContext.getString(R.string.status_4));
                    } else if (item.getISREVERT().equals("0")) {
                        holder.setTextView(R.id.tv_query_state, mContext.getString(R.string.status_5));
                    }
                }
            };
            mListView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private String getSearchText() {
        return et_query_content.getText().toString().trim();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                IWantListActivity.this.finish();
                break;
            case R.id.tv_search:
                String searchText = getSearchText();
                if (BeanUtils.isEmptyStr(getSearchText())){
                    DialogUtils.showToast(IWantListActivity.this, "请输入搜索内容");
                } else {
                    currentPage = 1;
                    getIWantList(true, searchText);
                }
                break;

            default:
                break;
        }
    }

}
