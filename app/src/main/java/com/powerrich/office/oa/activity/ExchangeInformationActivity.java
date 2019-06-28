package com.powerrich.office.oa.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;

import java.util.LinkedHashMap;
import java.util.List;


/**
 * 文 件 名：ExchangeInformationActivity
 * 描   述：交换信息
 * 作   者：Wangzheng
 * 时   间：2018-7-23 11:45:37
 * 版   权：v1.0
 */
public class ExchangeInformationActivity extends BaseActivity implements View.OnClickListener {

    private static final int SYNC_USER_INFO_CODE = 0;
    private TableLayout table;
    private TextView tv_no_data;
    private String materialId;
    private String materialName;
    private String asyncJhpt;
    private String proKeyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        materialId = getIntent().getStringExtra("materialId");
        materialName = getIntent().getStringExtra("materialName");
        asyncJhpt = getIntent().getStringExtra("asyncJhpt");
        proKeyId = getIntent().getStringExtra("proKeyId");
        initView();
        syncUserInfo();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_exchange_information;
    }

    private void initView() {
        initTitleBar(R.string.exchange_information, this, null);
        table = (TableLayout) findViewById(R.id.table_layout);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
    }

    /**
     * 同步事项人口库信息请求
     */
    private void syncUserInfo() {
        ApiRequest request = OAInterface.syncUserInfo(materialId, materialName, asyncJhpt, proKeyId);
        invoke.invokeWidthDialog(request, callBack, SYNC_USER_INFO_CODE);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == SYNC_USER_INFO_CODE) {
                    List<ResultItem> items = item.getItems("DATA");
                    parseSyncUserInfo(items);
                }
            } else {
                DialogUtils.showToast(ExchangeInformationActivity.this, message);
            }
        }
    };

    private void parseSyncUserInfo(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            tv_no_data.setVisibility(View.VISIBLE);
            return;
        }
        if (table != null) {
            table.removeAllViews();
        }
        for (ResultItem item : items) {
            LinkedHashMap<String, Object> map = item.getItems();
            for (String key : map.keySet()) {
                addWidget(item, key);
            }
        }
    }

    /**
     * 动态加载表格数据
     *
     * @param item
     * @param key
     */
    private void addWidget(ResultItem item, String key) {
        String content = item.getString(key);
        TableRow tablerow = new TableRow(this);
        tablerow.setBackgroundColor(Color.rgb(222, 220, 210));
        final TextView tv_content = new TextView(this);
        tv_content.setText(content);
        tv_content.setTextColor(Color.BLACK);
        tv_content.setBackgroundResource(R.drawable.form_bg);
        final TextView tv_name = new TextView(this);
        tv_name.setText(key);
        tv_name.setTextColor(Color.BLACK);
        tv_name.setBackgroundResource(R.drawable.form_bg);
        //监听TextView的行数
        ViewTreeObserver observer = tv_content.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = tv_content.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                tv_name.setLines(tv_content.getLineCount());
            }
        });
        tablerow.addView(tv_name);
        tablerow.addView(tv_content);
        table.setBackgroundResource(R.drawable.form_table_bg);
        table.addView(tablerow, new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                ExchangeInformationActivity.this.finish();
                break;
        }
    }
}
