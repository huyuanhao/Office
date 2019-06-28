package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.PolicyStatuteInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.view.pull.PullToRefreshBase;
import com.powerrich.office.oa.view.pull.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名：PolicyStatuteActivity
 * 描   述：阳光政务政策法规界面
 * 作   者：Wangzheng
 * 时   间：2017/12/4
 * 版   权：v1.0
 */
public class PolicyStatuteActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_normative_file, tv_local_regulations, tv_government_regulations, tv_government_file, tv_department_file, tv_policy_unscramble;
    private PullToRefreshListView pull_lv;
    private TextView tv_no_data;
    private ListView listView;
    private int currentPage = 1;
    private String type = "1";
    private List<PolicyStatuteInfo> policyStatuteInfoList = new ArrayList<>();
    private CommonAdapter<PolicyStatuteInfo> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_policy_statute;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!BeanUtils.isEmpty(policyStatuteInfoList)) {
            policyStatuteInfoList.clear();
        }
        currentPage = 1;
        getPolicyStatute(type, currentPage);
    }

    private void initView() {
        initTitleBar(R.string.policy_statute, this, null);
        tv_normative_file = (TextView) findViewById(R.id.tv_normative_file);
        tv_local_regulations = (TextView) findViewById(R.id.tv_local_regulations);
        tv_government_regulations = (TextView) findViewById(R.id.tv_government_regulations);
        tv_government_file = (TextView) findViewById(R.id.tv_government_file);
        tv_department_file = (TextView) findViewById(R.id.tv_department_file);
        tv_policy_unscramble = (TextView) findViewById(R.id.tv_policy_unscramble);
        pull_lv = (PullToRefreshListView) findViewById(R.id.pull_lv);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
    }

    private void initData() {
        tv_normative_file.setOnClickListener(this);
        tv_local_regulations.setOnClickListener(this);
        tv_government_regulations.setOnClickListener(this);
        tv_government_file.setOnClickListener(this);
        tv_department_file.setOnClickListener(this);
        tv_policy_unscramble.setOnClickListener(this);
        listView = BeanUtils.setProperty(pull_lv);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PolicyStatuteActivity.this, PolicyStatuteDetailActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("policy_statute_id", policyStatuteInfoList.get(position).getPolicyStatuteId());
                intent.putExtra("title", policyStatuteInfoList.get(position).getTitle());
                startActivity(intent);
            }
        });
        pull_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                getPolicyStatute(type, currentPage);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                getPolicyStatute(type, currentPage);
            }
        });
    }

    /**
     * 查询阳光政务政策法规请求
     */
    private void getPolicyStatute(String type, int currentPage) {
        ApiRequest request = OAInterface.getPolicyStatute(type, "", String.valueOf(currentPage));
        invoke.invokeWidthDialog(request, callBack);
    }

    /**
     * 查询阳光政务政策法规数据解析
     * @param items
     */
    private void parseData(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            tv_no_data.setVisibility(View.VISIBLE);
            return;
        }
        for (ResultItem item : items) {
            PolicyStatuteInfo policyStatuteInfo = new PolicyStatuteInfo();
            policyStatuteInfo.setCreateTime(item.getString("CREATTIME"));
            policyStatuteInfo.setTitle(item.getString("TITLE"));
            policyStatuteInfo.setType(item.getString("TYPE"));
            policyStatuteInfo.setPolicyStatuteId(item.getString("ZCFGID"));
            policyStatuteInfoList.add(policyStatuteInfo);
        }
        if (adapter == null) {
            adapter = new CommonAdapter<PolicyStatuteInfo>(PolicyStatuteActivity.this, policyStatuteInfoList, R.layout.government_affairs_item) {
                @Override
                public void convert(ViewHolder holder, PolicyStatuteInfo item) {
                    holder.getItemView(R.id.tv_department).setVisibility(View.GONE);
                    holder.setTextView(R.id.text, item.getTitle());
                }
            };
            listView.setAdapter(adapter);
        } else {
            tv_no_data.setVisibility(View.GONE);
            adapter.setData(policyStatuteInfoList);
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
                if (currentPage == 1 && !BeanUtils.isEmpty(policyStatuteInfoList)) {
                    policyStatuteInfoList.clear();
                }
                parseData(items);
                //判断是否已经全部加载完成：如果完成了就关闭“下拉加载更多”功能，否则，继续打开“下拉加载更多”功能
                if (BeanUtils.isEmpty(items) || items.size() < Constants.COMMON_PAGE) {
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
            } else {
                DialogUtils.showToast(PolicyStatuteActivity.this, message);
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (!BeanUtils.isEmpty(policyStatuteInfoList)) {
            policyStatuteInfoList.clear();
        }
        switch (v.getId()) {
            case R.id.system_back:
                PolicyStatuteActivity.this.finish();
                break;
            case R.id.tv_normative_file:
                type = "1";
                currentPage = 1;
                getPolicyStatute(type, currentPage);
                break;
            case R.id.tv_local_regulations:
                type = "2";
                currentPage = 1;
                getPolicyStatute(type, currentPage);
                break;
            case R.id.tv_government_regulations:
                type = "3";
                currentPage = 1;
                getPolicyStatute(type, currentPage);
                break;
		    case R.id.tv_government_file:
                type = "4";
                currentPage = 1;
                getPolicyStatute(type, currentPage);
			    break;
		    case R.id.tv_department_file:
                type = "5";
                currentPage = 1;
                getPolicyStatute(type, currentPage);
			    break;
		    case R.id.tv_policy_unscramble:
                type = "6";
                currentPage = 1;
                getPolicyStatute(type, currentPage);
			    break;
            default:
                break;
        }
    }
}
