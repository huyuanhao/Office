package com.powerrich.office.oa.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.AddressRvAdapter;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.AddressListBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.view.LoadingDialog;
import com.powerrich.office.oa.view.MyDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名：AddressListActivity
 * 描述：地址列表
 * 作者：白煜
 * 时间：2017/11/30 0030
 * 版权：
 */

public class AddressListActivity extends BaseActivity implements View.OnClickListener, AddressRvAdapter.onItemDeleteListener {
    private RecyclerView rv_address;
    private TextView tv_add;
    private TextView tv_please_add;
    private static final int CODE_GET_ADDRESS_LIST = 555;
    private static final int CODE_DELETE_ADDRESS = 556;
    private List<AddressListBean.DATABean> mList = new ArrayList<>();
    private AddressRvAdapter mAddressRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.avtivity_address_list;
    }

    private void initView() {
        initTitleBar(R.string.address_manage, this, null);
        rv_address = (RecyclerView) findViewById(R.id.rv_address);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_please_add = (TextView) findViewById(R.id.tv_please_add);
        rv_address.setLayoutManager(new LinearLayoutManager(this));
        mAddressRvAdapter = new AddressRvAdapter(mList, this);
        mAddressRvAdapter.setOnItemDeleteListener(this);
        rv_address.setAdapter(mAddressRvAdapter);

        tv_add.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoginUtils.getInstance().isLoginSuccess()) {
            loadData();
        }
    }

    private void loadData() {
        invoke.invokeWidthDialog(OAInterface.getAddressManager(), callBack, CODE_GET_ADDRESS_LIST);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String msg = item.getString("message");

            if (what == CODE_GET_ADDRESS_LIST) {
                if (Constants.SUCCESS_CODE.equals(item.getString("code"))) {
                    String jsonStr = item.getJsonStr();
                    Gson gson = new Gson();
                    AddressListBean addressListBean = gson.fromJson(jsonStr, AddressListBean.class);
                    mList.clear();
                    mList.addAll(addressListBean.getDATA());
                    if (mList.size() == 0) {
                        tv_please_add.setVisibility(View.VISIBLE);
                        rv_address.setVisibility(View.INVISIBLE);
                    } else {
                        tv_please_add.setVisibility(View.INVISIBLE);
                        rv_address.setVisibility(View.VISIBLE);
                    }
                    mAddressRvAdapter.notifyDataSetChanged();

                } else {
                    DialogUtils.showToast(AddressListActivity.this, msg);
                }
            } else if (what == CODE_DELETE_ADDRESS) {
                if (Constants.SUCCESS_CODE.equals(item.getString("code"))) {

                    DialogUtils.showToast(AddressListActivity.this, msg);
                    loadData();
                } else {
                    DialogUtils.showToast(AddressListActivity.this, msg);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.tv_add:
                Intent intent = new Intent(this, AddressDetailActivity.class);
                intent.putExtra("type", AddressDetailActivity.TYPE_ADD);
                startActivity(intent);
            default:
                break;
        }
    }

    @Override
    public void onItemDeleteListener(final AddressListBean.DATABean deleteData) {
        MyDialog builder = new MyDialog(AddressListActivity.this).builder();
        builder.setCancelable(true);
        builder.setTitle("提示")
                .setMessage("是否要删除？")
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteAddress(deleteData);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    private void deleteAddress(AddressListBean.DATABean addressData) {
        invoke.invokeWidthDialog(OAInterface.deleteAddress(addressData.getADDRESSID()), callBack, CODE_DELETE_ADDRESS);
    }
}
