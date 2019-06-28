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
import com.powerrich.office.oa.bean.IntegratedQueryBean;
import com.powerrich.office.oa.bean.OnlineBookingInfo;
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
 * 文 件 名：IntegratedQueryActivity
 * 描   述：综合查询列表界面
 * 作   者：Wangzheng
 * 时   间：2018/2/9
 * 版   权：v1.0
 */
public class IntegratedQueryActivity extends BaseActivity implements View.OnClickListener {

    private ListView lv;
    private PullToRefreshListView pull_lv;
    private TextView tv_no_data;
    private List<IntegratedQueryBean> queryList = new ArrayList<>();
    private CommonAdapter<IntegratedQueryBean> adapter;
    private EditText et_query_content;
    private TextView tv_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_integrated_query;
    }

    private void initView() {
        initTitleBar(R.string.integrated_query, this, null);
        et_query_content = (EditText) findViewById(R.id.et_query_content);
        tv_query = (TextView) findViewById(R.id.tv_search);
        pull_lv = (PullToRefreshListView) findViewById(R.id.pull_lv);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
    }

    private void initData() {
        pull_lv.setPullRefreshEnabled(true);
        pull_lv.setPullLoadEnabled(true);
        lv = pull_lv.getRefreshableView();
//        lv.setDivider(new ColorDrawable(Color.TRANSPARENT));//不设置会角标越界
//        lv.setDividerHeight(50);//每个条目的分界线
        lv.setCacheColorHint(Color.TRANSPARENT);
        lv.setFadingEdgeLength(0);
        lv.setSelector(android.R.color.transparent);
        tv_query.setOnClickListener(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                if("0".equals(queryList.get(position).getType())) { //"0":事项
                    // fixme 保存预约信息
                    //保存预约所需信息
//                    OnlineBookingInfo.siteName = queryList.get(position).getShortName();
//                    OnlineBookingInfo.siteid = queryList.get(position).getSiteNo();
//                    OnlineBookingInfo.itemName = queryList.get(position).getItemName();
//                    OnlineBookingInfo.itemId = queryList.get(position).getItemId();
                    intent.setClass(IntegratedQueryActivity.this, WorkGuideNewActivity.class);
                    intent.putExtra("item_id", queryList.get(position).getItemId());
                    intent.putExtra("item_name", queryList.get(position).getItemName());
                } else if ("1".equals(queryList.get(position).getType())){ //"1":事项主题
                    intent.setClass(IntegratedQueryActivity.this, ListActivity.class);
                    intent.putExtra("type", "1".equals(queryList.get(position).getTagType())? 1 : 0);
                    intent.putExtra("tag_id", queryList.get(position).getItemId());
                } else if ("2".equals(queryList.get(position).getType())){ //"2":行政权力清单
                    intent.setClass(IntegratedQueryActivity.this, PowerListDetailActivity.class);
                    intent.putExtra("itemName", queryList.get(position).getItemName());
                    intent.putExtra("pid", queryList.get(position).getItemId());
                } else{ //"3":投诉、咨询、建议
                    intent.setClass(IntegratedQueryActivity.this, IWantSuggestActivity.class);
                    intent.putExtra("iwant_type", queryList.get(position).getIwantType());
                    intent.putExtra("id", queryList.get(position).getItemId());
                    intent.putExtra("isShowDetail", true);
                }
                startActivity(intent);
            }
        });
        pull_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pull_lv.onPullDownRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pull_lv.onPullUpRefreshComplete();
            }
        });

        et_query_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    // 先隐藏键盘
                    ((InputMethodManager) et_query_content.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(IntegratedQueryActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (!BeanUtils.isEmpty(queryList)) {
                        queryList.clear();
                    }
                    searchContent(et_query_content.getText().toString().trim());// 搜索
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 综合查询请求
     */
    private void searchContent(String content) {
        ApiRequest request = OAInterface.searchContent(content);
        invoke.invokeWidthDialog(request, callBack);
    }

    /**
     * 综合查询数据解析
     * @param result
     */
    private void parse(ResultItem result) {
        List<ResultItem> itemData = result.getItems("ITEM_DATA");
        List<ResultItem> jyData = result.getItems("JY_DATA");
        List<ResultItem> powerData = result.getItems("POWER_DATA");
        List<ResultItem> tagsData = result.getItems("TAGS_DATA");
        List<ResultItem> tsData = result.getItems("TS_DATA");
        List<ResultItem> zxData = result.getItems("ZX_DATA");
        if (BeanUtils.isEmpty(itemData) && BeanUtils.isEmpty(jyData) && BeanUtils.isEmpty(powerData)
                && BeanUtils.isEmpty(tagsData) && BeanUtils.isEmpty(tsData) && BeanUtils.isEmpty(zxData)) {
            tv_no_data.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            return;
        }
        if (!BeanUtils.isEmpty(itemData)) {
            for (ResultItem resultItem : itemData) {
                IntegratedQueryBean queryBean = new IntegratedQueryBean();
                queryBean.setItemId(resultItem.getString("HID"));
                queryBean.setItemName(resultItem.getString("ITEMNAME"));
                queryBean.setShortName(resultItem.getString("SHORT_NAME"));
                queryBean.setSiteNo(resultItem.getString("SITENO"));
                queryBean.setType("0");
                queryList.add(queryBean);
            }
        }
        if (!BeanUtils.isEmpty(jyData)) {
            for (ResultItem resultItem : jyData) {
                IntegratedQueryBean queryBean = new IntegratedQueryBean();
                queryBean.setItemId(resultItem.getString("JL_ID"));
                queryBean.setItemName(resultItem.getString("TITLE"));
                queryBean.setType("3");
                queryBean.setIwantType(Constants.SUGGEST_TYPE);
                queryList.add(queryBean);
            }
        }
        if (!BeanUtils.isEmpty(powerData)) {
            for (ResultItem resultItem : powerData) {
                IntegratedQueryBean queryBean = new IntegratedQueryBean();
                queryBean.setItemId(resultItem.getString("PID"));
                queryBean.setItemName(resultItem.getString("ITEMNAME"));
                queryBean.setType("2");
                queryList.add(queryBean);
            }
        }
        if (!BeanUtils.isEmpty(tagsData)) {
            for (ResultItem resultItem : tagsData) {
                IntegratedQueryBean queryBean = new IntegratedQueryBean();
                queryBean.setItemId(resultItem.getString("TAG_ID"));
                queryBean.setItemName(resultItem.getString("TAG_NAME"));
                queryBean.setTagType(resultItem.getString("TAG_TYPE"));
                queryBean.setType("1");
                queryList.add(queryBean);
            }
        }
        if (!BeanUtils.isEmpty(tsData)) {
            for (ResultItem resultItem : tsData) {
                IntegratedQueryBean queryBean = new IntegratedQueryBean();
                queryBean.setItemId(resultItem.getString("JL_ID"));
                queryBean.setItemName(resultItem.getString("TITLE"));
                queryBean.setType("3");
                queryBean.setIwantType(Constants.COMPLAIN_TYPE);
                queryList.add(queryBean);
            }
        }
        if (!BeanUtils.isEmpty(zxData)) {
            for (ResultItem resultItem : zxData) {
                IntegratedQueryBean queryBean = new IntegratedQueryBean();
                queryBean.setItemId(resultItem.getString("JL_ID"));
                queryBean.setItemName(resultItem.getString("TITLE"));
                queryBean.setType("3");
                queryBean.setIwantType(Constants.CONSULTING_TYPE);
                queryList.add(queryBean);
            }
        }
        if (adapter == null) {
            if (!BeanUtils.isEmpty(queryList)) {
                adapter = new CommonAdapter<IntegratedQueryBean>(IntegratedQueryActivity.this, queryList, R.layout.government_affairs_item) {
                    @Override
                    public void convert(ViewHolder holder, IntegratedQueryBean item) {
                        holder.getItemView(R.id.tv_department).setVisibility(View.GONE);
                        holder.setTextView(R.id.text, item.getItemName());
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
            adapter.setData(queryList);
        }
    }

    /**
     * 综合查询数据解析
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
            IntegratedQueryBean queryBean = new IntegratedQueryBean();
            queryBean.setItemName(item.getString("ITEMNAME"));
            queryBean.setTransactScope(item.getString("BSDX"));
            queryBean.setTransactOrganization(item.getString("NORMACCEPTDEPART"));
            queryBean.setTransactLimit(item.getString("NORMTIMELIMIT"));
            queryBean.setIsCharge(item.getString("SFSF"));
            queryBean.setTransactCount(item.getString("DEGREE"));
            queryBean.setTransactWindowAddress(item.getString("BLWINDOW"));
            queryBean.setConsultPhone(item.getString("ZXMKZXDH"));
            queryBean.setComplainPhone(item.getString("JDTSDH"));
            queryList.add(queryBean);
        }
        if (adapter == null) {
            if (!BeanUtils.isEmpty(queryList)) {
                adapter = new CommonAdapter<IntegratedQueryBean>(IntegratedQueryActivity.this, queryList, R.layout.integrated_query_item) {
                    @Override
                    public void convert(ViewHolder holder, IntegratedQueryBean item) {
                        holder.setTextView(R.id.tv_item_name, item.getItemName());
                        holder.setTextView(R.id.tv_transact_scope, item.getTransactScope());
                        holder.setTextView(R.id.tv_transact_organization, item.getTransactOrganization());
                        holder.setTextView(R.id.tv_transact_limit, item.getTransactLimit());
                        holder.setTextView(R.id.tv_is_charge, "0".equals(item.getIsCharge()) ? "不收费" : "收费");
                        holder.setTextView(R.id.tv_transact_count, item.getTransactCount());
                        holder.setTextView(R.id.tv_transact_window_address, item.getTransactWindowAddress());
                        holder.setTextView(R.id.tv_consult_phone, item.getConsultPhone());
                        holder.setTextView(R.id.tv_complain_phone, item.getComplainPhone());
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
            adapter.setData(queryList);
        }
    }


    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                /*ResultItem result = (ResultItem) item.get("DATA");
                ResultItem data = (ResultItem) result.get("DATA");
                List<ResultItem> items = data.getItems("DATA");
                parseData(items);*/
                parse(item);
            } else {
                DialogUtils.showToast(IntegratedQueryActivity.this, message);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                IntegratedQueryActivity.this.finish();
                break;
            case R.id.tv_search:
                if (!BeanUtils.isEmpty(queryList)) {
                    queryList.clear();
                }
                searchContent(et_query_content.getText().toString().trim());
                break;
            default:
                break;
        }
    }

}
