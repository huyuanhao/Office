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
import com.powerrich.office.oa.bean.PublicityInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.view.pull.PullToRefreshBase;
import com.powerrich.office.oa.view.pull.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名：PublicityActivity
 * 描   述：阳光政务负面公示列表界面
 * 作   者：Wangzheng
 * 时   间：2017/12/4
 * 版   权：v1.0
 */
public class PublicityActivity extends BaseActivity implements View.OnClickListener {

    private ListView lv;
    private TextView tv_no_data;
    private List<PublicityInfo> publicityInfoList = new ArrayList<>();
    private CommonAdapter<PublicityInfo> adapter;
    private PullToRefreshListView pull_lv;
    private int currentPage = 1;

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

    private void initView() {
        initTitleBar(R.string.negative_publicity, this, null);
        pull_lv = (PullToRefreshListView) findViewById(R.id.pull_lv);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
    }

    private void initData() {
        lv = BeanUtils.setProperty(pull_lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PublicityActivity.this, PublicityDetailActivity.class);
                intent.putExtra("enterprise_name", publicityInfoList.get(position).getEnterpriseName());
                intent.putExtra("publicity_id", publicityInfoList.get(position).getPublicityId());
                startActivity(intent);
            }
        });
        pull_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                getPublicityList(currentPage, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                getPublicityList(currentPage, false);
            }
        });
        getPublicityList(currentPage, true);
    }

    /**
     * 查询阳光政务负面公示请求
     */
    private void getPublicityList(int currentPage, boolean isDialog) {
        if (isDialog) {
            ApiRequest request = OAInterface.getPublicityList("", String.valueOf(currentPage));
            invoke.invokeWidthDialog(request, callBack);
        } else {
            ApiRequest request = OAInterface.getPublicityList("", String.valueOf(currentPage));
            invoke.invoke(request, callBack);
        }
    }

    /**
     * 查询阳光政务负面公示数据解析
     *
     * @param items
     */
    private void parseData(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            tv_no_data.setVisibility(View.VISIBLE);
            return;
        }
        for (ResultItem item : items) {
            String enterpriseName = item.getString("APPLY_ERPRISE_NAME");
            String applyName = item.getString("APPLY_NAME");
            String approveEnterprise = item.getString("APPROVE_ENTERPRISE");
            String desDate = item.getString("DESDATE");
            String failDescribe = item.getString("FAIL_DESCRIBE");
            String publicityId = item.getString("FMGSID");
            String link = item.getString("FRINASH_LINK");
            String showMessage = item.getString("SHOW_MESSAGE");
            PublicityInfo publicityInfo = new PublicityInfo();
            publicityInfo.setEnterpriseName(enterpriseName);
            publicityInfo.setApplyName(applyName);
            publicityInfo.setApproveEnterprise(approveEnterprise);
            publicityInfo.setDesDate(desDate);
            publicityInfo.setFailDescribe(failDescribe);
            publicityInfo.setPublicityId(publicityId);
            publicityInfo.setLink(link);
            publicityInfo.setShowMessage(showMessage);
            publicityInfoList.add(publicityInfo);
        }
        if (adapter == null) {
            if (!BeanUtils.isEmpty(publicityInfoList)) {
                adapter = new CommonAdapter<PublicityInfo>(PublicityActivity.this, publicityInfoList, R.layout.publicity_item) {
                    @Override
                    public void convert(ViewHolder holder, PublicityInfo item) {
                        holder.setTextView(R.id.tv_title, item.getShowMessage());
                        holder.setTextView(R.id.tv_enterprise_name, item.getEnterpriseName());
                        holder.setTextView(R.id.tv_time, DateUtils.getDateStr(item.getDesDate(), DateUtils.DATE_FORMAT));
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


    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                ResultItem result = (ResultItem) item.get("DATA");
                List<ResultItem> items = result.getItems("DATA");
                if (currentPage == 1 && !BeanUtils.isEmpty(publicityInfoList)) {
                    publicityInfoList.clear();
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
                DialogUtils.showToast(PublicityActivity.this, message);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                PublicityActivity.this.finish();
                break;
            default:
                break;
        }
    }

}
