package com.powerrich.office.oa.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.QueryResultBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.view.NoScrollListView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yt.simpleframe.utils.StringUtil;
import com.zxinglibrary.android.CaptureActivity;
import com.zxinglibrary.bean.ZxingConfig;
import com.zxinglibrary.common.Constant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 文 件 名：DoThingQueryActivity
 * 描   述：办件查询
 * 作   者：Wangzheng
 * 时   间：2018-6-11 15:56:28
 * 版   权：v1.0
 */
public class DoThingQueryActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_ITEM_STATISTICS_NUMBER_CODE = 0;
    private static final int GET_ITEM_SCHEDULE_CODE = 1;
    private EditText et_query_content;
    private TextView tv_search;
    private LinearLayout scan;
    private TextView tv_current_month_accept, tv_current_month_conclude;
    private TextView tv_total_accept, tv_total_conclude;
    private NoScrollListView horizontal_lv;
    private TextView tv_no_data;
    private LinearLayout layout_statistics;
    private View layout_result;
    private int REQUEST_CODE_SCAN = 111;



    private List<QueryResultBean> resultBeans;
    private CommonAdapter<QueryResultBean> adapter;

    private LinearLayout ll_head;

    private LinearLayout mLtSearch;
    private String processCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            processCode = getIntent().getExtras().getString("processCode");
        }catch (Exception e){

        }
        initView();

        if(StringUtil.isEmpty(processCode)){
            mLtSearch.setVisibility(View.VISIBLE);
            layout_statistics.setVisibility(View.VISIBLE);
            initData();
            getItemStatisticsNumber();
        }else{
            mLtSearch.setVisibility(View.GONE);
            layout_statistics.setVisibility(View.GONE);
            layout_result.setVisibility(View.VISIBLE);
            getItemSchedule(processCode);
        }

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_do_thing_query;
    }

    private void initView() {
        initTitleBar(R.string.do_thing_query, this, null);
        et_query_content = (EditText) findViewById(R.id.et_query_content);
        tv_search = (TextView) findViewById(R.id.tv_search);
        scan = (LinearLayout) findViewById(R.id.scan);
        tv_current_month_accept = (TextView) findViewById(R.id.tv_current_month_accept);
        tv_current_month_conclude = (TextView) findViewById(R.id.tv_current_month_conclude);
        tv_total_accept = (TextView) findViewById(R.id.tv_total_accept);
        tv_total_conclude = (TextView) findViewById(R.id.tv_total_conclude);
        horizontal_lv = (NoScrollListView) findViewById(R.id.horizontal_lv);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        layout_statistics = (LinearLayout) findViewById(R.id.layout_statistics);
        layout_result = findViewById(R.id.layout_result);
        ll_head = (LinearLayout) findViewById(R.id.ll_head);
        mLtSearch = (LinearLayout) findViewById(R.id.lt_search);
    }

    private void initData() {
        scan.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        et_query_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_query_content.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(DoThingQueryActivity.this.getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 获取查询系统办件数量请求
     */
    private void getItemStatisticsNumber() {
        ApiRequest request = OAInterface.getItemStatisticsNumber();
        invoke.invokeWidthDialog(request, callBack, GET_ITEM_STATISTICS_NUMBER_CODE);
    }

    /**
     * 获取根据受理编号查询办事进度请求
     */
    private void getItemSchedule(String trackNumber) {
        ApiRequest request = OAInterface.getItemSchedule(trackNumber);
        invoke.invokeWidthDialog(request, callBack, GET_ITEM_SCHEDULE_CODE);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {

                if (what == GET_ITEM_STATISTICS_NUMBER_CODE) {
                    ResultItem result = (ResultItem) item.get("DATA");
                    parseData(result);
                } else if (what == GET_ITEM_SCHEDULE_CODE) {
                    List<ResultItem> items = item.getItems("DATA");
                    parse(items);
                }
            } else {
                DialogUtils.showToast(DoThingQueryActivity.this, message);
            }
        }

    };

    private void parse(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            ll_head.setVisibility(View.GONE);
            horizontal_lv.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.VISIBLE);
            return;
        }
        tv_no_data.setVisibility(View.GONE);
        ll_head.setVisibility(View.VISIBLE);
        horizontal_lv.setVisibility(View.VISIBLE);
        resultBeans = new ArrayList<>();
        for (ResultItem item : items) {
            QueryResultBean bean = new QueryResultBean();
            bean.setCOMP_NAME(item.getString("COMP_NAME"));
            bean.setCOMP_NO(item.getString("COMP_NO"));
            bean.setDEAL_RESULT(item.getString("DEALRESULT"));
            bean.setFINISH_SITE_NAME(item.getString("FINISH_SITE_NAME"));
            bean.setFINISH_STAFF_NAME(item.getString("FINISH_STAFF_NAME"));
            bean.setFINISH_TIME(item.getString("FINISH_TIME"));
            bean.setLIMIT_TIME(item.getString("LIMIT_TIME"));
            bean.setSIGN_TIME(item.getString("SIGN_TIME"));
            resultBeans.add(bean);
        }
        if (adapter == null) {
            adapter = new CommonAdapter<QueryResultBean>(DoThingQueryActivity.this, resultBeans, R.layout.horizontal_list_item) {
                @Override
                public void convert(ViewHolder holder, QueryResultBean item) {
                    holder.setTextView(R.id.tv_table01, item.getCOMP_NAME());
                    holder.setTextView(R.id.tv_table02, item.getFINISH_STAFF_NAME());
                    holder.setTextView(R.id.tv_table03, item.getFINISH_SITE_NAME());
                    holder.setTextView(R.id.tv_table04, item.getDEAL_RESULT());
                    holder.setTextView(R.id.tv_table05, item.getSIGN_TIME());
                    holder.setTextView(R.id.tv_table06, item.getFINISH_TIME());
                }
            };
            horizontal_lv.setAdapter(adapter);
        } else {
            adapter.setData(resultBeans);
        }
    }

    /**
     * 解析查询系统办件数量
     */
    private void parseData(ResultItem result) {
        if (result == null) {
            return;
        }
        String currentMonthAccept = result.getString("BYSL");
        String currentMonthConclude = result.getString("BYBJ");
        String totalAccept = result.getString("LJSL");
        String totalConclude = result.getString("LJBJ");
        tv_current_month_accept.setText(currentMonthAccept);
        tv_current_month_conclude.setText(currentMonthConclude);
        tv_total_accept.setText(totalAccept);
        tv_total_conclude.setText(totalConclude);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                DoThingQueryActivity.this.finish();
                break;
            case R.id.tv_search:
                search();
                break;
            case R.id.scan:
                RxPermissions rxPermissions = new RxPermissions(DoThingQueryActivity.this);
                rxPermissions
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    Intent intent = new Intent(DoThingQueryActivity.this, CaptureActivity.class);
                                    /*ZxingConfig是配置类
                                     *可以设置是否显示底部布局，闪光灯，相册，
                                     * 是否播放提示音  震动
                                     * 设置扫描框颜色等
                                     * 也可以不传这个参数
                                     * */
                                    ZxingConfig config = new ZxingConfig();
                                    config.setPlayBeep(true);//是否播放扫描声音 默认为true
                                    config.setShake(true);//是否震动  默认为true
                                    config.setDecodeBarCode(false);//是否扫描条形码 默认为true
                                    config.setReactColor(R.color.white);//设置扫描框四个角的颜色 默认为淡蓝色
                                    config.setFrameLineColor(R.color.white);//设置扫描框边框颜色 默认无色
                                    config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                                    intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                                    startActivityForResult(intent, REQUEST_CODE_SCAN);
                                } else {
                                    showTipDialog("没有相机或存储权限,请前往设置中打开权限！");
                                }
                            }
                        });
                break;
        }
    }

    private void search() {
        String content = et_query_content.getText().toString().trim();
        if (BeanUtils.isEmptyStr(content)) {
            DialogUtils.showToast(DoThingQueryActivity.this, "请输入搜索内容");
            return;
        }
        layout_statistics.setVisibility(View.GONE);
        layout_result.setVisibility(View.VISIBLE);
//                getItemSchedule("360600-0012018052202");
        getItemSchedule(content);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                et_query_content.setText(content);
                search();
            }
        }
    }
}
