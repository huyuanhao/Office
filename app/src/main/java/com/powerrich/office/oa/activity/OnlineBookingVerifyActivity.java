package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.BookingDetailBean;
import com.powerrich.office.oa.bean.BookingTimeInfo;
import com.powerrich.office.oa.bean.OnlineBookingInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;

import java.io.Serializable;
import java.util.List;

public class OnlineBookingVerifyActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_item_name;
    private TextView tv_booking_date;
    private TextView tv_booking_time;
    private TextView tv_name;
    private TextView tv_id_card;
    private LinearLayout ll_org_name;
    private LinearLayout ll_org_num;
    private TextView tv_org_name;
    private TextView tv_org_num;
    private TextView tv_phone_number;
    private TextView tv_email;
    private OnlineBookingInfo bookingInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_online_booking_verify_activity);
        bookingInfo = (OnlineBookingInfo) getIntent().getSerializableExtra("bookingInfo");
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_online_booking_verify_activity;
    }

    private void initView() {
        initTitleBar("确认信息", this, null);
        tv_item_name = (TextView) findViewById(R.id.tv_item_name);
        tv_booking_date = (TextView) findViewById(R.id.tv_booking_date);
        tv_booking_time = (TextView) findViewById(R.id.tv_booking_time);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_id_card = (TextView) findViewById(R.id.tv_id_card);
        ll_org_name = (LinearLayout) findViewById(R.id.ll_org_name);
        ll_org_num = (LinearLayout) findViewById(R.id.ll_org_num);
        tv_org_name = (TextView) findViewById(R.id.tv_org_name);
        tv_org_num = (TextView) findViewById(R.id.tv_org_num);
        tv_phone_number = (TextView) findViewById(R.id.tv_phone_number);
        tv_email = (TextView) findViewById(R.id.tv_email);
        findViewById(R.id.btn_last_step).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        initData();
    }

    private void initData() {
        if (!BeanUtils.isEmpty(bookingInfo)) {
            tv_item_name.setText(bookingInfo.getItemName());
            tv_booking_date.setText(bookingInfo.getOrderDate());
            tv_booking_time.setText(bookingInfo.getOrderTime());
            tv_name.setText(bookingInfo.getName());
            tv_id_card.setText(bookingInfo.getIdcard());
            if (!BeanUtils.isEmpty(LoginUtils.getInstance().getUserInfo())) {
                if ("1".equals(LoginUtils.getInstance().getUserInfo().getUserType())) {//"0"为个人   "1"为企业
                    ll_org_name.setVisibility(View.VISIBLE);
                    ll_org_num.setVisibility(View.VISIBLE);
                    tv_org_name.setText(bookingInfo.getOrgName());
                    tv_org_num.setText(bookingInfo.getOrgNum());
                }
            }
            tv_phone_number.setText(bookingInfo.getPhone());
//            tv_email.setText(bookingInfo.getEmail());
        }
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                BookingDetailBean bookingDetailBean = new BookingDetailBean();
                bookingDetailBean.setOrder_no(item.getString("ORDER_NO"));
                bookingDetailBean.setOrder_date(item.getString("ORDER_DATE"));
                bookingDetailBean.setOrder_name(item.getString("NAME"));
                bookingDetailBean.setOrder_item(item.getString("ORDER_ITEM"));
                bookingDetailBean.setBlwindow(item.getString("BLWINDOW"));
                bookingDetailBean.setZxmkzxdh(item.getString("ZXMKZXDH"));
                Intent intent = new Intent(OnlineBookingVerifyActivity.this, OnlineBookingDetailActivity.class);
                intent.putExtra("itemId", bookingInfo == null ? "" : bookingInfo.getItemId());
                intent.putExtra("BookingDetailBean", bookingDetailBean);
                startActivity(intent);
                OnlineBookingHallActivity.instance.finish();
                OnlineBookingVerifyActivity.this.finish();
            } else {
                DialogUtils.showToast(OnlineBookingVerifyActivity.this, message);
            }
        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.btn_last_step:
                finish();
                break;
            case R.id.btn_confirm:
                //调用保存预约接口
                invoke.invokeWidthDialog(OAInterface.saveAppointmentInfo(bookingInfo), callBack);
                break;
            default:
                break;

        }
    }
}
