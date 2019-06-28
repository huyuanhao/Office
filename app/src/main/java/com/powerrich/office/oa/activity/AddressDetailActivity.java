package com.powerrich.office.oa.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.AddressListBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.view.LoadingDialog;
import com.powerrich.office.oa.view.MyDialog;

/**
 * 文件名：AddressDetailActivity
 * 描述：地址详情，修改
 * 作者：白煜
 * 时间：2017/12/4 0004
 * 版权：
 */

public class AddressDetailActivity extends BaseActivity implements View.OnClickListener {

    public static final String TYPE_ADD = "add_address";
    public static final String TYPE_EDIT = "edit_address";
    public static final int DELETE_ADDRESS = 666;
    public static final int ADD_OR_EDIT_ADDRESS = 667;

    private String type;
    private AddressListBean.DATABean addressData;

    private EditText et_consignee;
    private EditText et_consulting_phone;
    private EditText et_postalcode;
    private EditText et_company_name;
    private EditText et_address;
    private EditText et_sheng_name;
    private EditText et_shi_name;
    private LinearLayout ll_delete_address;
    private TextView tv_top_right;
    private RadioGroup rg_default;
    private RadioButton rb_yes;
    private RadioButton rb_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        initView();
        if (type.equals(TYPE_EDIT)) {
            addressData = (AddressListBean.DATABean) getIntent().getSerializableExtra("address_data");
            showData();
        } else if (type.equals(TYPE_ADD)) {
            addressData = new AddressListBean.DATABean();
            addressData.setADDRESSID("");
        }
    }




    @Override
    protected int provideContentViewId() {
        return R.layout.activity_address_detail;
    }

    private void initView() {
        initTitleBar(R.string.address_manage, this, null);
        tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        et_consignee = (EditText) findViewById(R.id.et_consignee);
        et_consulting_phone = (EditText) findViewById(R.id.et_consulting_phone);
        et_postalcode = (EditText) findViewById(R.id.et_postalcode);
        et_company_name = (EditText) findViewById(R.id.et_company_name);
        et_address = (EditText) findViewById(R.id.et_address);
        et_sheng_name = (EditText) findViewById(R.id.et_sheng_name);
        et_shi_name = (EditText) findViewById(R.id.et_shi_name);
        ll_delete_address = (LinearLayout) findViewById(R.id.ll_delete_address);
        rg_default = (RadioGroup) findViewById(R.id.rg_default);
        rb_yes = (RadioButton) findViewById(R.id.rb_yes);
        rb_no = (RadioButton) findViewById(R.id.rb_no);

        ll_delete_address.setOnClickListener(this);

        tv_top_right.setVisibility(View.VISIBLE);
        tv_top_right.setText(R.string.save);
        tv_top_right.setOnClickListener(this);
    }

    private void showData() {
        et_consignee.setText(addressData.getSJRXM());
        et_consulting_phone.setText(addressData.getHANDSET());
        et_postalcode.setText(addressData.getYZBM());
        et_company_name.setText(addressData.getCOMPANY_NAME());
        et_address.setText(addressData.getADDRESS());
        et_sheng_name.setText(addressData.getPROV());
        et_shi_name.setText(addressData.getCITY());
        ll_delete_address.setVisibility(View.VISIBLE);

        if ("1".equals(addressData.getISDEFAULT())) {
            rb_yes.setChecked(true);
        } else {
            rb_no.setChecked(true);
        }

    }

    private void deleteAddress() {
        invoke.invokeWidthDialog(OAInterface.deleteAddress(addressData.getADDRESSID()), callBack, DELETE_ADDRESS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.ll_delete_address:
                MyDialog builder = new MyDialog(AddressDetailActivity.this).builder();
                builder.setCancelable(true);
                builder.setTitle("提示")
                        .setMessage("是否要删除？")
                        .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                deleteAddress();
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
                break;
            case R.id.tv_top_right:

                if (checkInputData()) {
                    addressData.setADDRESS(et_address.getText().toString().trim());
                    addressData.setCOMPANY_NAME(et_company_name.getText().toString().trim());
                    addressData.setHANDSET(et_consulting_phone.getText().toString().trim());
                    addressData.setYZBM(et_postalcode.getText().toString().trim());
                    addressData.setSJRXM(et_consignee.getText().toString().trim());
                    addressData.setPROV(et_sheng_name.getText().toString().trim());
                    addressData.setCITY(et_shi_name.getText().toString().trim());
                    addressData.setTEL_NO("");
                    if (rg_default.getCheckedRadioButtonId() == R.id.rb_yes) {
                        addressData.setISDEFAULT("1");
                    } else if (rg_default.getCheckedRadioButtonId() == R.id.rb_no) {
                        addressData.setISDEFAULT("0");
                    }
                    invoke.invokeWidthDialog(OAInterface.modifyAddress(addressData), callBack, ADD_OR_EDIT_ADDRESS);
                }

                break;
        }
    }

    private boolean checkInputData() {
        if ("".equals(et_consignee.getText().toString().trim())) {
            DialogUtils.showToast(AddressDetailActivity.this, "收件人不能为空");
            return false;
        } else if (!BeanUtils.isPostCode(et_postalcode.getText().toString().trim())) {
            DialogUtils.showToast(AddressDetailActivity.this, "邮政编码格式不正确");
            return false;
        } else if ("".equals(et_consulting_phone.getText().toString().trim())) {
            DialogUtils.showToast(AddressDetailActivity.this, "联系电话不能为空");
            return false;
        } else if ("".equals(et_sheng_name.getText().toString().trim())) {
            DialogUtils.showToast(AddressDetailActivity.this, "省名称不能为空");
            return false;
        } else if ("".equals(et_shi_name.getText().toString().trim())) {
            DialogUtils.showToast(AddressDetailActivity.this, "市名称不能为空");
            return false;
        } else if ("".equals(et_address.getText().toString().trim())) {
            DialogUtils.showToast(AddressDetailActivity.this, "收货地址不能为空");
            return false;
        } else if (!BeanUtils.isMobileNO(et_consulting_phone.getText().toString().trim())) {
            DialogUtils.showToast(AddressDetailActivity.this, "联系电话格式不正确");
            return false;
        }
        return true;
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String msg = item.getString("message");

            if (what == DELETE_ADDRESS) {
                if (Constants.SUCCESS_CODE.equals(item.getString("code"))) {
                    DialogUtils.showToast(AddressDetailActivity.this, msg);
                    AddressDetailActivity.this.finish();
                } else {
                    DialogUtils.showToast(AddressDetailActivity.this, msg);
                }
            } else if (what == ADD_OR_EDIT_ADDRESS) {
                if (Constants.SUCCESS_CODE.equals(item.getString("code"))) {
                    DialogUtils.showToast(AddressDetailActivity.this, msg);
                    AddressDetailActivity.this.finish();
                } else {
                    DialogUtils.showToast(AddressDetailActivity.this, msg);
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
