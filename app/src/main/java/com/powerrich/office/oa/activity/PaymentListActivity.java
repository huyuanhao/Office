package com.powerrich.office.oa.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.PaymentRvAdapter;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.PaymentListBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名：PaymentListActivity
 * 描述：支付列表
 * 作者：白煜
 * 时间：2017/12/6 0006
 * 版权：
 */

public class PaymentListActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView rv_payment;
    private static final int CODE_GET_PAYMENT_LIST = 556;

    private List<PaymentListBean.DATABean> mList = new ArrayList<>();
    private PaymentRvAdapter mPaymentRvAdapter;

    private TextView tv_tab_payment_no;
    private TextView tv_tab_payment_yes;
    private TextView tv_no_data;

    private EditText et_query_content;
    private TextView tv_query;

    private int select_tab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_payment_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoginUtils.getInstance().isLoginSuccess()) {
            loadData(true, "0", "");
        }
    }

    private void loadData(boolean isDiaLog, String status, String itemName) {
        if (isDiaLog) {
            invoke.invokeWidthDialog(OAInterface.getItemPays(status, "", itemName), callBack, CODE_GET_PAYMENT_LIST);
        } else {
            invoke.invoke(OAInterface.getItemPays(status, "", itemName), callBack, CODE_GET_PAYMENT_LIST);
        }
    }

    private void initView() {
        initTitleBar(R.string.my_payment, this, null);
        rv_payment = (RecyclerView) findViewById(R.id.rv_payment);
        tv_tab_payment_no = (TextView) findViewById(R.id.tv_tab_payment_no);
        tv_tab_payment_yes = (TextView) findViewById(R.id.tv_tab_payment_yes);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        rv_payment = (RecyclerView) findViewById(R.id.rv_payment);
        rv_payment.setLayoutManager(new LinearLayoutManager(this));
        mPaymentRvAdapter = new PaymentRvAdapter(this, mList);
        rv_payment.setAdapter(mPaymentRvAdapter);

        et_query_content = (EditText) findViewById(R.id.et_query_content);
        tv_query = (TextView) findViewById(R.id.tv_search);
        tv_query.setOnClickListener(this);

        tv_tab_payment_no.setOnClickListener(this);
        tv_tab_payment_yes.setOnClickListener(this);
        tv_tab_payment_yes.setTextColor(Color.GRAY);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                PaymentListActivity.this.finish();
                break;
            case R.id.tv_tab_payment_no:
                tv_tab_payment_no.setBackgroundResource(R.drawable.select_tab_checked_left);
                tv_tab_payment_no.setTextColor(getResources().getColor(R.color.white));
                tv_tab_payment_yes.setBackground(null);
                tv_tab_payment_yes.setTextColor(Color.GRAY);

                loadData(false, "0", "");
                select_tab = 0;
                break;
            case R.id.tv_tab_payment_yes:
                tv_tab_payment_yes.setBackgroundResource(R.drawable.select_tab_checked_right);
                tv_tab_payment_yes.setTextColor(getResources().getColor(R.color.white));
                tv_tab_payment_no.setBackground(null);
                tv_tab_payment_no.setTextColor(Color.GRAY);

                loadData(false, "1", "");
                select_tab = 1;
                break;
            case R.id.tv_search:
                if (et_query_content.getText().toString().trim().isEmpty()) {
                    DialogUtils.showToast(PaymentListActivity.this, "请输入搜索内容");
                } else {
                    loadData(true, select_tab + "", et_query_content.getText().toString().trim());
                }
                break;
            default:
                break;
        }
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String msg = item.getString("message");

            if (what == CODE_GET_PAYMENT_LIST) {
                if (Constants.SUCCESS_CODE.equals(item.getString("code"))) {
                    String jsonStr = item.getJsonStr();
                    Gson gson = new Gson();
                    PaymentListBean paymentListBean = gson.fromJson(jsonStr, PaymentListBean.class);
                    mList.clear();
                    mList.addAll(paymentListBean.getDATA());
                    if (mList.size() == 0) {
                        rv_payment.setVisibility(View.GONE);
                        tv_no_data.setVisibility(View.VISIBLE);
                    } else {
                        rv_payment.setVisibility(View.VISIBLE);
                        tv_no_data.setVisibility(View.GONE);
                    }
                    mPaymentRvAdapter.notifyDataSetChanged();

                } else {
                    DialogUtils.showToast(PaymentListActivity.this, msg);
                }
            }

        }

        @Override
        public void finish(Object dialogObj, int what) {
            super.finish(dialogObj, what);
            if (dialogObj != null) {
                if (dialogObj instanceof LoadingDialog) {
                    ((LoadingDialog) dialogObj).dismiss();
                }
            }
        }
    };
}
