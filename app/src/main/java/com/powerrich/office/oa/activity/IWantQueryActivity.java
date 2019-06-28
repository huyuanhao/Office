package com.powerrich.office.oa.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
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
 * 文 件 名：IWantQueryActivity
 * 描   述：阳光政务我要查询列表界面
 * 作   者：Wangzheng
 * 时   间：2017/12/5
 * 版   权：v1.0
 */
public class IWantQueryActivity extends BaseActivity implements View.OnClickListener {

    private ListView lv;
    private PullToRefreshListView pull_lv;
    private TextView tv_no_data;
    private List<PublicityInfo> publicityInfoList = new ArrayList<>();
    private CommonAdapter<PublicityInfo> adapter;
    private LinearLayout ll_search;
    private EditText et_query_content;
    private TextView tv_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        getIWantQuery("");
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_list;
    }

    private void initView() {
        initTitleBar(R.string.want_query, this, null);
        ll_search = (LinearLayout) findViewById(R.id.layout_search);
        ll_search.setVisibility(View.VISIBLE);
        et_query_content = (EditText) findViewById(R.id.et_query_content);
        tv_query = (TextView) findViewById(R.id.tv_search);
        pull_lv = (PullToRefreshListView) findViewById(R.id.pull_lv);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
    }

    private void initData() {
        pull_lv.setPullRefreshEnabled(true);
        pull_lv.setPullLoadEnabled(true);
        lv = pull_lv.getRefreshableView();
        lv.setCacheColorHint(Color.TRANSPARENT);
        lv.setFadingEdgeLength(0);
        lv.setSelector(android.R.color.transparent);

        tv_query.setOnClickListener(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        pull_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                et_query_content.setText("");
                pull_lv.onPullDownRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                et_query_content.setText("");
                pull_lv.onPullUpRefreshComplete();
            }
        });
    }

    /**
     * 阳光政务我要查询请求
     */
    private void getIWantQuery(String condition) {
        ApiRequest request = OAInterface.iWantSearch(condition);
        invoke.invokeWidthDialog(request, callBack);
    }

    /**
     * 阳光政务我要查询数据解析
     * @param items
     */
    private void parseData(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            tv_no_data.setVisibility(View.VISIBLE);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            lv.setVisibility(View.GONE);
            return;
        }
        for (ResultItem item : items) {
            String projectName = item.getString("BIZ_TITLE");
            String proposer = item.getString("USERNAME");
            String desDate = item.getString("DESDATE");
            PublicityInfo publicityInfo = new PublicityInfo();
            publicityInfo.setShowMessage(projectName);
            publicityInfo.setEnterpriseName(proposer);
            publicityInfo.setDesDate(desDate);
            publicityInfoList.add(publicityInfo);
        }
        if (adapter == null) {
            if (!BeanUtils.isEmpty(publicityInfoList)) {
                adapter = new CommonAdapter<PublicityInfo>(IWantQueryActivity.this, publicityInfoList, R.layout.publicity_item) {
                    @Override
                    public void convert(ViewHolder holder, PublicityInfo item) {
                        holder.setTextView(R.id.tv_title, item.getShowMessage());
                        holder.setTextView(R.id.tv_enterprise_name, item.getEnterpriseName());
                        holder.setTextView(R.id.tv_time, DateUtils.getDateStr(item.getDesDate(), DateUtils.DATE_FORMAT));
                    }
                };
                lv.setAdapter(adapter);
                lv.setVisibility(View.VISIBLE);
                tv_no_data.setVisibility(View.GONE);
            } else {
                tv_no_data.setVisibility(View.VISIBLE);
            }

        } else {
            lv.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
            adapter.setData(publicityInfoList);
        }
    }


    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                List<ResultItem> items = item.getItems("DATA");
                parseData(items);
            } else {
                DialogUtils.showToast(IWantQueryActivity.this, message);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                IWantQueryActivity.this.finish();
                break;
            case R.id.tv_search:
                // 通过输入的条件进行查询
                if (!BeanUtils.isEmpty(publicityInfoList)) {
                    publicityInfoList.clear();
                }
                getIWantQuery(et_query_content.getText().toString().trim());
                break;
            default:
                break;
        }
    }

}
