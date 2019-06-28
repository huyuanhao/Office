package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.Interaction.IwantActivity;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.LeaderEmailListBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.VerificationUtils;
import com.powerrich.office.oa.view.pull.PullToRefreshBase;
import com.powerrich.office.oa.view.pull.PullToRefreshListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 文件名：LeaderMailActivity
 */

public class LeaderMailActivity extends BaseActivity implements View.OnClickListener {

    private SmartRefreshLayout lv_iwant;
    private ListView mListView;
    private TextView tv_no_data;
    private TextView tv_top_right;

    private List<LeaderEmailListBean.DATABeanX.DATABean> mList = new ArrayList<>();

    private int currentPage = 1;
    private CommonAdapter<LeaderEmailListBean.DATABeanX.DATABean> adapter;
    private TextView tv_query;
    private EditText et_query_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_leader_mail;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData("");
    }

    private void loadData(String title) {
        ApiRequest request = OAInterface.getLeadermailList(title,currentPage+"");
        if (null != invoke)
            invoke.invokeWidthDialog(request, callBack);
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
                LeaderEmailListBean leaderEmailListBean = gson.fromJson(jsonStr, LeaderEmailListBean.class);
                if (currentPage == 1 && !BeanUtils.isEmpty(mList)) {
                    mList.clear();
                }
                if(!BeanUtils.isEmpty(leaderEmailListBean.getDATA().getDATA())) {
                    mList.addAll(leaderEmailListBean.getDATA().getDATA());
                }
                showIWantList(mList);
                //判断是否已经全部加载完成：如果完成了就关闭“下拉加载更多”功能，否则，继续打开“下拉加载更多”功能
                if (BeanUtils.isEmpty(leaderEmailListBean.getDATA().getDATA()) || leaderEmailListBean.getDATA().getDATA().size
                        () < Constants.COMMON_PAGE) {
                    // 已经全部加载完成，关闭UI组件的下拉加载更多功能
                    lv_iwant.finishRefresh();
                    lv_iwant.finishLoadmore();
                    lv_iwant.setEnableLoadmore(false);
                } else {
                    // 还有更多数据，继续打开“下拉加载更多”功能
                    lv_iwant.finishRefresh();
                    lv_iwant.finishLoadmore();
                    lv_iwant.setEnableLoadmore(true);
                }
            } else {
                DialogUtils.showToast(LeaderMailActivity.this, message);
            }
        }

    };

    protected void showIWantList(List<LeaderEmailListBean.DATABeanX.DATABean> list) {
//        Collections.reverse(mList);
        if (BeanUtils.isEmpty(list)) {
            tv_no_data.setVisibility(View.VISIBLE);
            if(adapter != null){
                adapter.notifyDataSetChanged();
            }
            mListView.setVisibility(View.GONE);
            return;
        }else{
            tv_no_data.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
        }
        if (adapter == null) {
            if (!BeanUtils.isEmpty(list)) {
                adapter = new CommonAdapter<LeaderEmailListBean.DATABeanX.DATABean>(LeaderMailActivity.this, mList, R.layout
                        .item_do_thing) {
                    @Override
                    public void convert(ViewHolder holder, LeaderEmailListBean.DATABeanX.DATABean item) {
                        holder.setTextView(R.id.tv_query_title, item.getTITLE());
                        holder.setTextView(R.id.tv_time, item.getSEND_TIME().split(" ")[0]);
                        holder.setTextView(R.id.tv_hour, item.getSEND_TIME().split(" ")[1]);

                        if (item.getIS_REVERT().equals("1")) {
                            holder.setTextView(R.id.tv_query_state, mContext.getString(R.string.status_4));
                        } else if (item.getIS_REVERT().equals("0")){
                            holder.setTextView(R.id.tv_query_state, mContext.getString(R.string.status_5));
                        }
                    }
                };
                mListView.setAdapter(adapter);
            } else {
                tv_no_data.setVisibility(View.VISIBLE);
            }

        } else {
            adapter.notifyDataSetChanged();
        }
    }


    private void initView() {
        initTitleBar(R.string.consulting_lead_mail, this, null);
        tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        tv_top_right.setVisibility(View.VISIBLE);
        tv_top_right.setText(R.string.add);
        tv_top_right.setOnClickListener(this);

        et_query_content = (EditText) findViewById(R.id.et_query_content);
        et_query_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if("".equals(s.toString())){
                    currentPage = 1;
                    loadData("");
                }
            }
        });
        tv_query = (TextView) findViewById(R.id.tv_search);
        tv_query.setOnClickListener(this);

        lv_iwant = (SmartRefreshLayout) findViewById(R.id.lv_iwant);
        mListView = (ListView) findViewById(R.id.listView);

        lv_iwant.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshLayout) {
                currentPage++;
                loadData("");
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                currentPage = 1;
                loadData("");
            }
        });

        mListView.setDivider(null);//不设置会角标越界
        mListView.setDividerHeight(1);//每个条目的分界线
        mListView.setCacheColorHint(Color.TRANSPARENT);
        mListView.setFadingEdgeLength(0);
        mListView.setSelector(android.R.color.transparent);

        tv_no_data = (TextView) findViewById(R.id.tv_no_data);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LeaderMailActivity.this, LeaderMailInfoActivity.class);
                intent.putExtra("mid", mList.get(position).getMID());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                LeaderMailActivity.this.finish();
                break;

            case R.id.tv_top_right:
                if (!VerificationUtils.all(LeaderMailActivity.this)) {
                    return;
                }
                //新建
                startActivity(new Intent(this, IwantActivity.class).putExtra("iwant_type", Constants.LETTER_TYPE));
//                startActivity(new Intent(this, LeaderEmailAddActivity.class));
                break;
            case R.id.tv_search:
                if (et_query_content.getText().toString().trim().isEmpty()) {
                    DialogUtils.showToast(LeaderMailActivity.this, "请输入搜索内容");
                } else {
                    currentPage = 1;
                    loadData(et_query_content.getText().toString().trim());
                }
                break;
            default:
                break;
        }
    }
}
