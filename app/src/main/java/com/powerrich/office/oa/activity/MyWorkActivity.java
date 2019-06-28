package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.ItemsAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.ItemsInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名：MyWorkActivity
 * 描   述：获取事项界面
 * 作   者：Wangzheng
 * 时   间：2017/11/17
 * 版   权：v1.0
 */
public class MyWorkActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_SERVICE_ITEMS_REQ = 0;
    private static final int GET_SITE_ITEMS_REQ = 1;
    private GridView gridView;
    private TextView tv_no_data;
    private int type;
    private List<ItemsInfo> itemsInfoList = new ArrayList<>();
    private ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", 0);
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_my_work;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        if (type == 0) {
            initTitleBar(R.string.personal_work, this, null);
            getServiceItems(Constants.PERSONAL_WORK_TYPE, "");
        } else if (type == 1) {
            initTitleBar(R.string.enterprise_work, this, null);
            getServiceItems(Constants.COMPANY_WORK_TYPE, "");
        } else {
            initTitleBar(R.string.department_work, this, null);
            getSiteAndItemList();
        }
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        gridView = (GridView) findViewById(R.id.gridView);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyWorkActivity.this, ListActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("tag_id", itemsInfoList.get(position).getTAG_ID());
                if (!(type == 0 || type == 1)) {
                    intent.putExtra("tag_name", itemsInfoList.get(position).getTAG_NAME());
                }
                startActivity(intent);
            }
        });
    }

    /**
     * 获取事项请求接口
     */
    private void getServiceItems(String type, String tagName) {
        ApiRequest request = OAInterface.getTagsListBySort(type, tagName);
        invoke.invokeWidthDialog(request, callBack, GET_SERVICE_ITEMS_REQ);
    }

    private void getSiteAndItemList() {
        ApiRequest request = OAInterface.getSiteListBySort("");
        invoke.invokeWidthDialog(request, callBack, GET_SITE_ITEMS_REQ);
    }

    /**
     * 解析数据
     *
     * @param items
     */
    private void parseData(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            tv_no_data.setVisibility(View.VISIBLE);
            return;
        }
        if (type == 0 || type == 1) {
            for (ResultItem item : items) {
                String tag_id = item.getString("TAG_ID");
                String tag_name = item.getString("TAG_NAME");
                String tag_type = item.getString("TAG_TYPE");
                ItemsInfo itemsInfo = new ItemsInfo();
                itemsInfo.setTAG_ID(tag_id);
                itemsInfo.setTAG_NAME(tag_name);
                itemsInfo.setTAG_TYPE(tag_type);
                itemsInfoList.add(itemsInfo);
            }
        } else {
            for (ResultItem item : items) {
                String tag_id = item.getString("SITE_NO");
                String tag_name = item.getString("SITE_NAME");
                ItemsInfo itemsInfo = new ItemsInfo();
                itemsInfo.setTAG_ID(tag_id);
                itemsInfo.setTAG_NAME(tag_name);
                itemsInfoList.add(itemsInfo);
            }
        }
        if (adapter == null) {
            if (!BeanUtils.isEmpty(itemsInfoList)) {
                adapter = new ItemsAdapter(this, itemsInfoList);
                gridView.setAdapter(adapter);
            } else {
                tv_no_data.setVisibility(View.VISIBLE);
            }
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == GET_SERVICE_ITEMS_REQ) {
                    List<ResultItem> data = item.getItems("DATA");
                    parseData(data);
                } else if (what == GET_SITE_ITEMS_REQ) {
                    List<ResultItem> data = item.getItems("DATA");
                    parseData(data);
                }

            } else {
                DialogUtils.showToast(MyWorkActivity.this, message);
            }
        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                MyWorkActivity.this.finish();
                break;
            default:
                break;
        }
    }
}
