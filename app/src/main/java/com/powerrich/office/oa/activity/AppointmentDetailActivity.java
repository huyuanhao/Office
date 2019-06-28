package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.AppointmentDetailBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;

/**
 * 文件名：AppointmentDetailActivity
 * 描述：预约详情
 * 作者：白煜
 * 时间：2018/1/4 0004
 * 版权：
 */

public class AppointmentDetailActivity extends BaseActivity implements View.OnClickListener{
    String a_id;
    public static final int CODE_GET_DETAIL = 6666;

    private EditText et_booking_event;
    private EditText et_unit;
    private EditText et_booking_time;
    private EditText et_booking_man;
    private EditText et_booking_man_idcard;
    private EditText et_booking_man_phone;
    private EditText et_booking_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a_id = getIntent().getStringExtra("a_id");
        initView();
        loadData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_appointment_detail;
    }

    private void loadData() {
        ApiRequest request = OAInterface.getMyAppointmentById(a_id);
        invoke.invokeWidthDialog(request, callBack, CODE_GET_DETAIL);
    }

    private void initView() {
        initTitleBar(R.string.booking_detail, this, null);
        et_booking_event = (EditText)findViewById(R.id.et_booking_event);
        et_unit = (EditText)findViewById(R.id.et_unit);
        et_booking_time = (EditText)findViewById(R.id.et_booking_time);
        et_booking_man = (EditText)findViewById(R.id.et_booking_man);
        et_booking_man_idcard = (EditText)findViewById(R.id.et_booking_man_idcard);
        et_booking_man_phone = (EditText)findViewById(R.id.et_booking_man_phone);
        et_booking_status = (EditText)findViewById(R.id.et_booking_status);
        setEnabled();
    }

    private void setEnabled() {
        et_booking_event.setEnabled(false);
        et_booking_event.setFocusable(false);
        et_unit.setEnabled(false);
        et_unit.setFocusable(false);
        et_booking_time.setEnabled(false);
        et_booking_time.setFocusable(false);
        et_booking_man.setEnabled(false);
        et_booking_man.setFocusable(false);
        et_booking_man_idcard.setEnabled(false);
        et_booking_man_idcard.setFocusable(false);
        et_booking_man_phone.setEnabled(false);
        et_booking_man_phone.setFocusable(false);
        et_booking_status.setEnabled(false);
        et_booking_status.setFocusable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                AppointmentDetailActivity.this.finish();
                break;
        }
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (CODE_GET_DETAIL == what) {
                    String jsonStr = item.getJsonStr();
                    Gson gson = new Gson();
                    AppointmentDetailBean appointmentDetailBean = gson.fromJson(jsonStr, AppointmentDetailBean.class);
                    showData(appointmentDetailBean);
                }
            } else {
                DialogUtils.showToast(AppointmentDetailActivity.this, message);
            }
        }
    };

    private void showData(AppointmentDetailBean appointmentDetailBean) {
        et_booking_event.setText(appointmentDetailBean.getDATA().getITEMNAME());
        et_unit.setText(appointmentDetailBean.getDATA().getDEPTNAME());
        et_booking_time.setText(appointmentDetailBean.getDATA().getORDER_DATE());
        et_booking_man.setText(appointmentDetailBean.getDATA().getREAL_NAME());
        et_booking_man_idcard.setText(appointmentDetailBean.getDATA().getIDCARD());
        et_booking_man_phone.setText(appointmentDetailBean.getDATA().getORDER_PHONE());
        if("1".equals(appointmentDetailBean.getDATA().getORDER_STATE())){
            et_booking_status.setText(getString(R.string.booking_status_1));
        }else if("2".equals(appointmentDetailBean.getDATA().getORDER_STATE())){
            et_booking_status.setText(getString(R.string.booking_status_2));
        }else if("3".equals(appointmentDetailBean.getDATA().getORDER_STATE())){
            et_booking_status.setText(getString(R.string.booking_status_3));
        }

    }
}
