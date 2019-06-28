package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.LogisticsListBean;
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
 * 文件名：LogisticsListActivity
 * 描述：物流列表
 * 作者：白煜
 * 时间：2017/12/13 0013
 * 版权：
 */

public class LogisticsListActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_search;
    private PullToRefreshListView pull_lv;
    private TextView tv_no_data;

    private List<LogisticsListBean.DATABeanX.DATABean> mList = new ArrayList<>();

    private int currentPage = 1;
    private static final int CODE_GET_DATA = 886;
    private ListView lv;
    private CommonAdapter<LogisticsListBean.DATABeanX.DATABean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoginUtils.getInstance().isLoginSuccess()) {
            loadData(currentPage + "");
        }
    }

    private void loadData(String currentPage) {
        invoke.invokeWidthDialog(OAInterface.getExpressList("", currentPage), callBack, CODE_GET_DATA);
    }

    private void initData() {
        lv = BeanUtils.setProperty(pull_lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LogisticsListActivity.this, LogisticsDetailActivity.class);
                intent.putExtra("expressNum", mList.get(position).getORDER_NUM());
                startActivity(intent);
            }
        });
        pull_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                loadData(currentPage + "");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                loadData(currentPage + "");
            }
        });
    }

    private void initView() {
        initTitleBar(R.string.express, this, null);
        pull_lv = (PullToRefreshListView) findViewById(R.id.pull_lv);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == CODE_GET_DATA) {
                    String jsonStr = item.getJsonStr();
                    Gson gson = new Gson();
                    LogisticsListBean logisticsListBean = gson.fromJson(jsonStr, LogisticsListBean.class);
                    if (currentPage == 1 && !BeanUtils.isEmpty(mList)) {
                        mList.clear();
                    }
                    if(!BeanUtils.isEmpty(logisticsListBean.getDATA().getDATA())) {
                        mList.addAll(logisticsListBean.getDATA().getDATA());
                    }
                    showData(mList);
                    //判断是否已经全部加载完成：如果完成了就关闭“下拉加载更多”功能，否则，继续打开“下拉加载更多”功能
                    if (BeanUtils.isEmpty(logisticsListBean.getDATA().getDATA()) || logisticsListBean.getDATA().getDATA().size
                            () < Constants.COMMON_PAGE) {
                        // 已经全部加载完成，关闭UI组件的下拉加载更多功能
                        pull_lv.onPullDownRefreshComplete();
                        pull_lv.onPullUpRefreshComplete();
                        pull_lv.setHasMoreData(false);
                    } else {
                        // 还有更多数据，继续打开“下拉加载更多”功能
                        pull_lv.onPullDownRefreshComplete();
                        pull_lv.onPullUpRefreshComplete();
                        pull_lv.setHasMoreData(true);
                    }
                }
            } else {
                DialogUtils.showToast(LogisticsListActivity.this, message);
            }
        }
    };

    private void showData(List<LogisticsListBean.DATABeanX.DATABean> list) {
        if (BeanUtils.isEmpty(list)) {
            tv_no_data.setVisibility(View.VISIBLE);
            return;
        }
        if (adapter == null) {
            if (!BeanUtils.isEmpty(list)) {
                adapter = new CommonAdapter<LogisticsListBean.DATABeanX.DATABean>(LogisticsListActivity.this, mList, R.layout
                        .item_logistics) {
                    @Override
                    public void convert(ViewHolder holder, LogisticsListBean.DATABeanX.DATABean item) {
                        holder.setTextView(R.id.tv_send, item.getSEND_NAME());
                        holder.setTextView(R.id.tv_take, item.getCONSIGNEE_NAME());
                        holder.setTextView(R.id.tv_express_num, item.getTRACKING_NUM());
                        // 0已下单，1物流中，2已签收
                        if("0".equals(item.getORDER_STATUS())){
                            holder.setTextView(R.id.tv_take_status, getString(R.string.order_status1));
                        }else if("1".equals(item.getORDER_STATUS())){
                            holder.setTextView(R.id.tv_take_status, getString(R.string.order_status2));
                        }else if("2".equals(item.getORDER_STATUS())){
                            holder.setTextView(R.id.tv_take_status, getString(R.string.express_is_take1));
                        }
                        holder.setTextView(R.id.tv_time, item.getEMIT_TIME());
                    }
                };
                lv.setAdapter(adapter);
            } else {
                tv_no_data.setVisibility(View.VISIBLE);
            }

        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                LogisticsListActivity.this.finish();
                break;
        }
    }
}
