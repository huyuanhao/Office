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
import com.powerrich.office.oa.bean.CollectListBean;
import com.powerrich.office.oa.bean.OnlineBookingInfo;
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
 * 文件名：CollectListActivity
 * 描述：收藏列表
 * 作者：白煜
 * 时间：2018/1/12 0012
 * 版权：
 */

public class CollectListActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout ll_search;
    private PullToRefreshListView pull_lv;
    private TextView tv_no_data;

    private List<CollectListBean.DATABeanX.DATABean> mList = new ArrayList<>();

    private int currentPage = 1;
    private static final int CODE_GET_DATA = 886;
    private ListView lv;
    private CommonAdapter<CollectListBean.DATABeanX.DATABean> adapter;
    
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
        invoke.invokeWidthDialog(OAInterface.collectItemList("", currentPage), callBack, CODE_GET_DATA);
    }

    private void initData() {
        lv = BeanUtils.setProperty(pull_lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (BeanUtils.isEmpty(mList)) return;
                // fixme 保存预约信息
                //保存预约所需信息
//                OnlineBookingInfo.siteName = mList.get(position).getNORMACCEPTDEPART();
//                OnlineBookingInfo.siteid = mList.get(position).getNORMACCEPTDEPARTID();
//                OnlineBookingInfo.itemName = mList.get(position).getITEMNAME();
//                OnlineBookingInfo.itemId = mList.get(position).getITEMID();
                Intent intent = new Intent(CollectListActivity.this, WorkGuideNewActivity.class);
                intent.putExtra("item_id", mList.get(position).getITEMID());
                intent.putExtra("item_name", mList.get(position).getITEMNAME());
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
        initTitleBar(R.string.my_collect, this, null);
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
                    CollectListBean CollectListBean = gson.fromJson(jsonStr, CollectListBean.class);
                    if (currentPage == 1 && !BeanUtils.isEmpty(mList)) {
                        mList.clear();
                    }
                    if(!BeanUtils.isEmpty(CollectListBean.getDATA().getDATA())) {
                        mList.addAll(CollectListBean.getDATA().getDATA());
                    }
                    showData(mList);
                    //判断是否已经全部加载完成：如果完成了就关闭“下拉加载更多”功能，否则，继续打开“下拉加载更多”功能
                    if (BeanUtils.isEmpty(CollectListBean.getDATA().getDATA()) || CollectListBean.getDATA().getDATA().size
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
                DialogUtils.showToast(CollectListActivity.this, message);
            }
        }
    };

    private void showData(List<CollectListBean.DATABeanX.DATABean> list) {
        if (BeanUtils.isEmpty(list)) {
            tv_no_data.setVisibility(View.VISIBLE);
            return;
        }
        if (adapter == null) {
            if (!BeanUtils.isEmpty(list)) {
                adapter = new CommonAdapter<CollectListBean.DATABeanX.DATABean>(CollectListActivity.this, mList, R.layout
                        .item_collect_list) {
                    @Override
                    public void convert(ViewHolder holder, CollectListBean.DATABeanX.DATABean item) {
                        holder.setTextView(R.id.tv_name, item.getITEMNAME());
                        holder.setTextView(R.id.tv_do_department, item.getNORMACCEPTDEPART());
                        holder.setTextView(R.id.tv_time, item.getSCTIME());
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
                CollectListActivity.this.finish();
                break;
        }
    }
}
