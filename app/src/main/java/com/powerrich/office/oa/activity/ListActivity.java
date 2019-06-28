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
import com.powerrich.office.oa.bean.Items;
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

public class ListActivity extends BaseActivity implements View.OnClickListener {
    private PullToRefreshListView pull_lv;
    private ListView listView;
    private TextView tv_no_data;
    private String tag_id;
    private int type;
    private List<Items> itemList = new ArrayList<>();
    private CommonAdapter<Items> adapter;
    private int currentPage = 1;
    private String sitName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag_id = getIntent().getStringExtra("tag_id");
        type = getIntent().getIntExtra("type", 0);
        sitName = getIntent().getStringExtra("tag_name");
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_list;
    }

    private void initView() {
        if (type == 0) {
            initTitleBar(R.string.personal_items, this, null);
        } else if (type == 1) {
            initTitleBar(R.string.company_items, this, null);
        } else {
            initTitleBar(R.string.department_items, this, null);
        }
        pull_lv = (PullToRefreshListView) findViewById(R.id.pull_lv);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
    }

    private void initData() {
        getItem();
        listView = BeanUtils.setProperty(pull_lv);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // fixme 保存预约信息
                //保存预约所需信息
//                OnlineBookingInfo.siteName = itemList.get(position).getNORMACCEPTDEPART();
//                OnlineBookingInfo.siteid = itemList.get(position).getSITENO();
//                OnlineBookingInfo.itemName = itemList.get(position).getITEMNAME();
//                OnlineBookingInfo.itemId = itemList.get(position).getITEMID();
                Intent intent = new Intent(ListActivity.this, WorkGuideNewActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("item_id", itemList.get(position).getITEMID());
                intent.putExtra("item_name", itemList.get(position).getITEMNAME());
                intent.putExtra("item_code", itemList.get(position).getSXBM());
                intent.putExtra("sxlx", itemList.get(position).getSXLX());
                startActivity(intent);
            }
        });
        pull_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                getItem();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                getItem();
            }
        });
    }

    private void getItem() {
        if (type == 0) {
            getItem(Constants.PERSONAL_WORK_TYPE, tag_id, "");
        } else if (type == 1) {
            getItem(Constants.COMPANY_WORK_TYPE, tag_id, "");
        } else {
//            getItem(Constants.DEPARTMENT_WORK_TYPE, tag_id, "");
            getSiteAndItemList();
        }
    }

    private void getSiteAndItemList() {
        ApiRequest request = OAInterface.getSiteListBySort(sitName);
        invoke.invokeWidthDialog(request, callBack);
    }

    /**
     * 获取事项信息请求
     */
    private void getItem(String type, String tagId, String itemName) {
        ApiRequest request = OAInterface.getItemByTagId(type, tagId, itemName, String.valueOf(currentPage));
        invoke.invokeWidthDialog(request, callBack);
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (0 == type || 1 == type) {
                    item = (ResultItem) item.get("DATA");
                }
                List<ResultItem> data = null;
                if (null != item) {
                        data = item.getItems("DATA");
                        if (!BeanUtils.isEmpty(data) && !(0 == type || 1 == type)) {
                            data = data.get(0).getItems("SITE_ITEM");
                        }
                        if (currentPage == 1 && !BeanUtils.isEmpty(itemList)) {
                            itemList.clear();
                        }
                        parseData(data);
                        if (0 == type || 1 == type) {
                            //判断是否已经全部加载完成：如果完成了就关闭“下拉加载更多”功能，否则，继续打开“下拉加载更多”功能
                            if (BeanUtils.isEmpty(data) || data.size() < Constants.COMMON_PAGE) {
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
                            // 已经全部加载完成，关闭UI组件的下拉加载更多功能
                            pull_lv.onPullDownRefreshComplete();
                            pull_lv.onPullUpRefreshComplete();
                            pull_lv.setHasMoreData(false);
                        }
                }
            } else {
                DialogUtils.showToast(ListActivity.this, message);
            }
        }

    };

    private void parseData(List<ResultItem> data) {
        if (BeanUtils.isEmpty(data)) {
            tv_no_data.setVisibility(View.VISIBLE);
            return;
        }
        for (ResultItem resultItem : data) {
            String itemId = null;
            String departNo = null;
            String itemCode = null;
            String sxlx = "";
            if (0 == type || 1 == type) {
                itemId = resultItem.getString("HID");
                departNo = resultItem.getString("SITENO");
                itemCode = resultItem.getString("CODE");
            } else {
                itemId = resultItem.getString("ITEMID");
                departNo = tag_id;
                itemCode = resultItem.getString("SXBM");
                sxlx = resultItem.getString("SXLX");
            }
            String itemName = resultItem.getString("ITEMNAME");
            String itemDepart = resultItem.getString("NORMACCEPTDEPART");
            String itemType = resultItem.getString("SSTYPE");
            Items items = new Items();
            items.setITEMID(itemId);
            items.setITEMNAME(itemName);
            items.setNORMACCEPTDEPART(itemDepart);
            items.setSITENO(departNo);
            items.setSSTYPE(itemType);
            items.setSXBM(itemCode);
            items.setSXLX(sxlx);
            itemList.add(items);
        }
        if (adapter == null) {
            if (!BeanUtils.isEmpty(itemList)) {
                adapter = new CommonAdapter<Items>(this, itemList, R.layout.list_item) {
                    @Override
                    public void convert(ViewHolder holder, Items item) {
                        holder.setTextView(R.id.text, item.getITEMNAME());
                        holder.setTextView(R.id.depart_txt, item.getNORMACCEPTDEPART());
                    }
                };
                listView.setAdapter(adapter);
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
                ListActivity.this.finish();
                break;
            default:
                break;
        }
    }
}
