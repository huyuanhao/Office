package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.LogisticsDetailBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.view.NoScrollListView;

import java.util.Collections;
import java.util.List;

/**
 * 文件名：LogisticsDetailActivity
 * 描述：物流详情
 * 作者：白煜
 * 时间：2017/12/13 0013
 * 版权：
 */

public class LogisticsDetailActivity extends BaseActivity implements View.OnClickListener {

    private String expressNum;
    private static final int CODE_GET_DATA = 663;
    private TextView tv_express_num;
    private TextView tv_take_status;
    private TextView tv_send_address;
    private TextView tv_send_name;
    private TextView tv_send_phone;
    private TextView tv_take_address;
    private TextView tv_take_name;
    private TextView tv_take_phone;
    private NoScrollListView lv_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expressNum = getIntent().getStringExtra("expressNum");
        initView();

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_logistics_detail;
    }

    private void initView() {
        initTitleBar(R.string.express_info, this, null);
        tv_express_num = (TextView) findViewById(R.id.tv_express_num);
        tv_take_status = (TextView) findViewById(R.id.tv_take_status);
        tv_send_address = (TextView) findViewById(R.id.tv_send_address);
        tv_send_name = (TextView) findViewById(R.id.tv_send_name);
        tv_send_phone = (TextView) findViewById(R.id.tv_send_phone);
        tv_take_address = (TextView) findViewById(R.id.tv_take_address);
        tv_take_name = (TextView) findViewById(R.id.tv_take_name);
        tv_take_phone = (TextView) findViewById(R.id.tv_take_phone);
        lv_info = (NoScrollListView) findViewById(R.id.lv_info);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        invoke.invokeWidthDialog(OAInterface.getExpressFollowInfo(expressNum), callBack, CODE_GET_DATA);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == CODE_GET_DATA) {
                    String jsonStr = item.getJsonStr();
                    Gson gson = new Gson();
                    LogisticsDetailBean logisticsDetailBean = gson.fromJson(jsonStr, LogisticsDetailBean.class);
                    showData(logisticsDetailBean);
                }
            } else {
                DialogUtils.showToast(LogisticsDetailActivity.this, message);
            }
        }
    };

    private void showData(LogisticsDetailBean logisticsDetailBean) {

        final List<LogisticsDetailBean.EMSDATABean> traces = logisticsDetailBean.getEMS_DATA();
        LogisticsDetailBean.ADRDATABean addressInfo = logisticsDetailBean.getADR_DATA();
        tv_express_num.setText(logisticsDetailBean.getADR_DATA().getTRACKING_NUM());
        // 0已下单，1物流中，2已签收
        if("0".equals(addressInfo.getORDER_STATUS())){
            tv_take_status.setText(R.string.order_status1);
        }else if("1".equals(addressInfo.getORDER_STATUS())){
            tv_take_status.setText(R.string.order_status2);
        }else if("2".equals(addressInfo.getORDER_STATUS())){
            tv_take_status.setText(R.string.express_is_take1);
        }
        tv_take_address.setText(addressInfo.getCONSIGNEE_ADDRESS());
        tv_take_name.setText(addressInfo.getCONSIGNEE_NAME());
        tv_take_phone.setText(addressInfo.getCONSIGNEE_PHONE());

        tv_send_address.setText(addressInfo.getSEND_ADDRESS());
        tv_send_name.setText(addressInfo.getSEND_NAME());
        tv_send_phone.setText(addressInfo.getSEND_PHONE());

        Collections.reverse(traces);
        CommonAdapter<LogisticsDetailBean.EMSDATABean> commonAdapter = new CommonAdapter<LogisticsDetailBean.EMSDATABean>(this,
                traces, R.layout.item_logistics_item_info) {

            @Override
            public void convert(ViewHolder holder, LogisticsDetailBean.EMSDATABean item) {
                View v_top = holder.getItemView(R.id.v_top);
                View v_bottom = holder.getItemView(R.id.v_bottom);
                if (traces.indexOf(item) == 0) {
                    v_top.setVisibility(View.INVISIBLE);
                }else{
                    v_top.setVisibility(View.VISIBLE);
                }
                if (traces.indexOf(item) == traces.size() - 1) {
                    v_bottom.setVisibility(View.INVISIBLE);
                }else{
                    v_bottom.setVisibility(View.VISIBLE);
                }
                TextView tv_time_h = holder.getItemView(R.id.tv_time_h);
                TextView tv_time_d = holder.getItemView(R.id.tv_time_d);
                TextView tv_info = holder.getItemView(R.id.tv_info);

                String[] time = item.getAcceptTime().split(" ");
                String[] time_h = time[1].split(":");
                tv_time_h.setText(time_h[0]+":"+time_h[1]);
                tv_time_d.setText(time[0]);

                tv_info.setText(item.getRemark());
            }
        };

        lv_info.setAdapter(commonAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                LogisticsDetailActivity.this.finish();
                break;
        }
    }
}
