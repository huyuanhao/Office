package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.AddAddressActivity;
import com.powerrich.office.oa.adapter.AddressAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.enums.AddressType;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.view.MyDialog;
import com.powerrich.office.oa.view.NoScrollListView;
import com.yt.simpleframe.http.bean.entity.AddressInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * 文 件 名：ExpressServiceActivity
 * 描   述：个人、企业办事快递服务
 * 作   者：Wangzheng
 * 时   间：2018-6-13 11:09:00
 * 版   权：v1.0
 */
public class ExpressServiceActivity extends BaseActivity implements View.OnClickListener, AddressAdapter.IModifyListener {

    private static final int GET_ADDRESS_CODE = 0;
    private static final int GET_EXPRESS_ADDRESS_CODE = 1;
    private TextView tv_item_name, tv_item_code;
    private RadioGroup rg_way;
    private RadioButton rb_get, rb_mail;
    private RadioGroup rg_send;
    private RadioButton rb_no, rb_yes;
    private TextView tv_is_send;
    private NoScrollListView lv_address;
    private TextView tv_add_address;
    private TextView tv_no_data;
    private Button btn_submit;
    private String itemName, itemCode;
    private String proKeyId;
    private String trackId;
    private AddressAdapter adapter;
    private List<AddressInfo> addressInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemName = getIntent().getStringExtra("itemName");
        itemCode = getIntent().getStringExtra("itemCode");
        proKeyId = getIntent().getStringExtra("proKeyId");
        trackId = getIntent().getStringExtra("trackId");
        initView();
        initData();
//        getExpressAddress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!BeanUtils.isEmpty(addressInfoList)) {
            addressInfoList.clear();
        }
        getAddress();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_express_service;
    }

    private void initView() {
        initTitleBar(R.string.express_service, this, null);
        tv_item_name = (TextView) findViewById(R.id.tv_item_name);
        tv_item_code = (TextView) findViewById(R.id.tv_item_code);
        rg_way = (RadioGroup) findViewById(R.id.rg_way);
        rb_get = (RadioButton) findViewById(R.id.rb_get);
        rb_mail = (RadioButton) findViewById(R.id.rb_mail);
        rg_way.setTag(rb_get.getTag().toString());
        rg_send = (RadioGroup) findViewById(R.id.rg_send);
        rb_no = (RadioButton) findViewById(R.id.rb_no);
        rb_yes = (RadioButton) findViewById(R.id.rb_yes);
        rg_send.setTag(rb_no.getTag().toString());
        tv_is_send = (TextView) findViewById(R.id.tv_is_send);
        lv_address = (NoScrollListView) findViewById(R.id.lv_address);
        tv_add_address = (TextView) findViewById(R.id.tv_add_address);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        btn_submit = (Button) findViewById(R.id.btn_submit);
    }

    private void initData() {
        tv_item_name.setText(itemName);
        tv_item_code.setText(itemCode);
        tv_add_address.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        rg_way.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == rb_get.getId()) {
                    rg_way.setTag(rb_get.getTag().toString());
                    tv_add_address.setVisibility(View.GONE);
                    lv_address.setVisibility(View.GONE);
                    tv_no_data.setVisibility(View.GONE);
                } else if (checkedId == rb_mail.getId()) {
                    rg_way.setTag(rb_mail.getTag().toString());
                    tv_add_address.setVisibility(View.VISIBLE);
                    lv_address.setVisibility(View.VISIBLE);
                    if (BeanUtils.isEmpty(addressInfoList)) {
                        tv_no_data.setVisibility(View.VISIBLE);
                    } else {
                        tv_no_data.setVisibility(View.GONE);
                    }
                }
            }
        });
        rg_send.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == rb_no.getId()) {
                    rg_send.setTag(rb_no.getTag().toString());
                    tv_is_send.setText("自行寄送到相关办理单位");
                } else if (checkedId == rb_yes.getId()) {
                    rg_send.setTag(rb_yes.getTag().toString());
                    tv_is_send.setText("快递员上门揽收并寄送到相关办理单位");
                }
            }
        });
    }

    /**
     * 获取事项申报寄送办理材料时查询用户揽件地址请求
     */
    private void getExpressAddress() {
        ApiRequest request = OAInterface.getExpressAddress(proKeyId);
        invoke.invokeWidthDialog(request, callBack, GET_EXPRESS_ADDRESS_CODE);
    }

    /**
     * 获取地址信息请求
     */
    private void getAddress() {
        ApiRequest request = OAInterface.getAddressManager();
        invoke.invokeWidthDialog(request, callBack, GET_ADDRESS_CODE);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == GET_ADDRESS_CODE) {
                    List<ResultItem> items = item.getItems("DATA");
                    showAddressData(items);
                } else if (what == GET_EXPRESS_ADDRESS_CODE) {
                    /*ResultItem result = (ResultItem) item.get("DATA");
                    parseData(result);*/
                }
            } else {
                DialogUtils.showToast(ExpressServiceActivity.this, message);
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showAddressData(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            if ("0".equals(rg_way.getTag().toString())) {
                tv_no_data.setVisibility(View.GONE);
            } else {
                tv_no_data.setVisibility(View.VISIBLE);
            }
            lv_address.setVisibility(View.GONE);
            return;
        }
        tv_no_data.setVisibility(View.GONE);
        for (ResultItem resultItem : items) {
            AddressInfo addressInfo = new AddressInfo();
            addressInfo.setADDRESS(resultItem.getString("ADDRESS"));
            addressInfo.setADDRESSID(resultItem.getString("ADDRESSID"));
            addressInfo.setCITY(resultItem.getString("CITY"));
            addressInfo.setCOMPANY_NAME(resultItem.getString("COMPANY_NAME"));
            addressInfo.setHANDSET(resultItem.getString("HANDSET"));
            addressInfo.setISDEFAULT(resultItem.getString("ISDEFAULT"));
            addressInfo.setDefault(resultItem.getString("ISDEFAULT").equals("1"));
            addressInfo.setPROV(resultItem.getString("PROV"));
            addressInfo.setSJRXM(resultItem.getString("SJRXM"));
            addressInfo.setTEL_NO(resultItem.getString("TEL_NO"));
            addressInfo.setUSERID(resultItem.getString("USERID"));
            addressInfo.setYZBM(resultItem.getString("YZBM"));
            addressInfoList.add(addressInfo);
        }
        if (adapter == null) {
            adapter = new AddressAdapter(this, addressInfoList);
            lv_address.setAdapter(adapter);
            adapter.setOnClickListener(this);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, AddAddressActivity.class);
        intent.putExtra("type", AddressType.修改);
        intent.putExtra("data", addressInfoList.get(position));
        startActivity(intent);
    }

    /**
     * 转跳到主界面
     */
    private void toMain() {
        Intent intent = new Intent(ExpressServiceActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void isExit() {
        MyDialog.showDialog(this, "确定要退出吗", "退出后，可在我的--我的办件--暂存中查看和继续办理", new MyDialog.InterfaceClick() {
            @Override
            public void click() {
                toMain();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            isExit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.system_back:
                isExit();
                break;
            case R.id.tv_add_address:
                intent = new Intent(ExpressServiceActivity.this, AddAddressActivity.class);
                intent.putExtra("type", AddressType.添加);
                startActivity(intent);
                break;
            case R.id.btn_submit:
                intent = new Intent(ExpressServiceActivity.this, DeclareNotifyActivity.class);
                intent.putExtra("itemName", itemName);
                intent.putExtra("itemCode", itemCode);
                intent.putExtra("proKeyId", proKeyId);
                intent.putExtra("trackId", trackId);
                startActivity(intent);
                break;
        }
    }

}
