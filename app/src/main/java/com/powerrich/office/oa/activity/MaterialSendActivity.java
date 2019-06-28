package com.powerrich.office.oa.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.DoThingBean;
import com.powerrich.office.oa.bean.MaterialSendInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.view.MyDialog;

import java.util.Calendar;
import java.util.Date;

/**
 * 文 件 名：MaterialSendActivity
 * 描   述：办理材料寄送界面
 * 作   者：Wangzheng
 * 时   间：2018/1/17
 * 版   权：v1.0
 */
public class MaterialSendActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_EXPRESS_ADDRESS_REQ = 0;
    private static final int MATERIAL_SEND_REQ = 1;
    private TextView tv_project_name, tv_collect_materials_time;
    private EditText et_name, et_phone, et_postal_code, et_receive_address;
    private TextView et_province_name, et_city_name;
    private TextView tv_send_address;
    private Button btn_order;
    private String itemName;
    private String proKeyId;
    private DoThingBean.DATABean.DELIVERYBean addressBean;
    private MaterialSendInfo sendInfo;
    private TimePickerView pvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemName = getIntent().getStringExtra("itemName");
        proKeyId = getIntent().getStringExtra("proKeyId");
        addressBean = (DoThingBean.DATABean.DELIVERYBean) getIntent().getSerializableExtra("addressBean");
        initView();
        initData();
        getExpressAddress();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_material_send;
    }

    private void initView() {
        initTitleBar(R.string.express_send_info, this, null);
        tv_project_name = findViewById(R.id.tv_project_name);
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_postal_code = findViewById(R.id.et_postal_code);
        tv_collect_materials_time = findViewById(R.id.tv_collect_materials_time);
        et_province_name = findViewById(R.id.et_province_name);
        et_city_name = findViewById(R.id.et_city_name);
        et_receive_address = findViewById(R.id.et_receive_address);
        tv_send_address = findViewById(R.id.tv_send_address);
        btn_order = findViewById(R.id.btn_order);

    }

    private void initData() {
        tv_collect_materials_time.setOnClickListener(this);
        btn_order.setOnClickListener(this);
        tv_project_name.setText(itemName);
        String sendAddress = "寄送地址：" + addressBean.getZXCKDZ() + "    " + addressBean.getZXMKZXDH() + "    " + addressBean.getZXCKMC() + "(收)";
        tv_send_address.setText(sendAddress);
        initTimePicker();
    }

    private void initTimePicker() {
        //时间选择器
        pvTime = new TimePickerBuilder(MaterialSendActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                tv_collect_materials_time.setText(DateUtils.getDateStr(date, DateUtils.DATE_TIME_FORMAT));
            }
        }).setType(new boolean[]{true, true, true, true, true, true}).build();
    }

    private void getExpressAddress() {
        ApiRequest request = OAInterface.getExpressAddress(proKeyId);
        invoke.invokeWidthDialog(request, callBack, GET_EXPRESS_ADDRESS_REQ);
    }

    /**
     * 办理材料寄送（线上快递下单，线下快递员上门取件）请求
     *
     * @param materialSendInfo
     */
    private void materialSend(MaterialSendInfo materialSendInfo) {
        ApiRequest request = OAInterface.materialSendEms(materialSendInfo);
        invoke.invokeWidthDialog(request, callBack, MATERIAL_SEND_REQ);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == GET_EXPRESS_ADDRESS_REQ) {
                    ResultItem result = (ResultItem) item.get("DATA");
                    parseData(result);
                } else if (what == MATERIAL_SEND_REQ) {
                    showSendDialog(message);
                }
            } else {
                DialogUtils.showToast(MaterialSendActivity.this, message);
            }
        }
    };

    private void parseData(ResultItem result) {
        if (result == null) {
            return;
        }
        String name = result.getString("SJRXM");
        String phone = result.getString("HANDSET");
        String postal_code = result.getString("YZBM");
        String province_name = result.getString("PROV");
        String city_name = result.getString("CITY");
        String receive_address = result.getString("ADDRESS");
        et_name.setText(name);
        et_phone.setText(phone);
        et_postal_code.setText(postal_code);
        et_province_name.setText(province_name);
        et_city_name.setText(city_name);
        et_receive_address.setText(receive_address);
    }

    /**
     * 材料寄送提交成功后提示
     */
    private void showSendDialog(String message) {
        MyDialog builder = new MyDialog(MaterialSendActivity.this).builder();
        builder.setCancelable(false)
                .setMessage(message)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        toMain();
                    }
                })
                .show();
    }

    /**
     * 转跳到主界面
     */
    private void toMain() {
        Intent intent = new Intent(MaterialSendActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * 校验信息
     */
    private boolean validate() {
        Date currentDate = DateUtils.parseDate(DateUtils.getDateStr(DateUtils.DATE_TIME_FORMAT), DateUtils.DATE_TIME_FORMAT);
        Date endDate = DateUtils.parseDate(sendInfo.getTime(), DateUtils.DATE_TIME_FORMAT);
        if (BeanUtils.isEmptyStr(sendInfo.getName())) {
            DialogUtils.showToast(MaterialSendActivity.this, "姓名不能为空");
            return false;
        }
        if (BeanUtils.isEmptyStr(sendInfo.getPhone())) {
            DialogUtils.showToast(MaterialSendActivity.this, "电话不能为空");
            return false;
        } else if (!BeanUtils.isMobileNO(sendInfo.getPhone())) {
            DialogUtils.showToast(MaterialSendActivity.this, "手机号格式不正确");
            return false;
        }
        if (BeanUtils.isEmptyStr(sendInfo.getPostcode())) {
            DialogUtils.showToast(MaterialSendActivity.this, "邮政编码不能为空");
            return false;
        } else if (!BeanUtils.isPostCode(sendInfo.getPostcode())) {
            DialogUtils.showToast(MaterialSendActivity.this, "邮政编码格式不正确");
            return false;
        }
        if (BeanUtils.isEmptyStr(sendInfo.getTime())) {
            DialogUtils.showToast(MaterialSendActivity.this, "揽件时间不能为空");
            return false;
        } else if (endDate.before(currentDate)) {
            DialogUtils.showToast(MaterialSendActivity.this, "您选择揽件时间必须大于等于当前时间");
            return false;
        }
        if (BeanUtils.isEmptyStr(sendInfo.getProvince())) {
            DialogUtils.showToast(MaterialSendActivity.this, "省名称不能为空");
            return false;
        }
        if (BeanUtils.isEmptyStr(sendInfo.getCity())) {
            DialogUtils.showToast(MaterialSendActivity.this, "市名称不能为空");
            return false;
        }
        if (BeanUtils.isEmptyStr(sendInfo.getAddress())) {
            DialogUtils.showToast(MaterialSendActivity.this, "揽收地址不能为空");
            return false;
        }
        return true;
    }

    private void setMaterialSend() {
        if (null == sendInfo) {
            sendInfo = new MaterialSendInfo();
        }
        sendInfo.setRegister(tv_project_name.getText().toString());
        sendInfo.setName(et_name.getText().toString().trim());
        sendInfo.setPhone(et_phone.getText().toString().trim());
        sendInfo.setPostcode(et_postal_code.getText().toString().trim());
        sendInfo.setTime(tv_collect_materials_time.getText().toString());
        sendInfo.setProvince(et_province_name.getText().toString().trim());
        sendInfo.setCity(et_city_name.getText().toString().trim());
        sendInfo.setAddress(et_receive_address.getText().toString().trim());
        sendInfo.setProKeyId(proKeyId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                MaterialSendActivity.this.finish();
                break;
            case R.id.tv_collect_materials_time:
                String time = tv_collect_materials_time.getText().toString().trim();
                if (BeanUtils.isEmptyStr(time)) {
                    pvTime.setDate(Calendar.getInstance());
                } else {
                    pvTime.setDate(DateUtils.dataToCalendar(DateUtils.parseDate(time, DateUtils.DATE_TIME_FORMAT)));
                }
                pvTime.show();
                break;
            case R.id.btn_order:
                setMaterialSend();
                if (!validate()) {
                    return;
                }
                materialSend(sendInfo);
                break;
            default:
                break;
        }
    }
}
