package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonRvAdapter;
import com.powerrich.office.oa.adapter.ViewHolderRv;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;

import java.util.List;


/**
 * 文 件 名：EnterpriseInformationActivity
 * 描   述：企业信息列表
 * 作   者：Wangzheng
 * 时   间：2018-8-17 10:03:49
 * 版   权：v1.0
 */
public class EnterpriseInformationActivity extends BaseActivity implements View.OnClickListener {
    private static final int GET_ENTERPRISE_INFO_CODE = 0;
    private RecyclerView recycler_view;
    private CommonRvAdapter<UserInfo.CompanyInfo> adapter;
    private String type;
    //1 是在线办事 2 是预约办事
    private String intentType;
    private String itemId;
    private String mPosition;
    private List<UserInfo.CompanyInfo> companyInfoList;

    private String normacceptdepartStr;
    private String itemnameStr;
    private String sxflidStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        itemId = getIntent().getStringExtra("item_id");
        intentType = getIntent().getStringExtra("intentType");
        mPosition = getIntent().getStringExtra("position");

        normacceptdepartStr = getIntent().getStringExtra("NORMACCEPTDEPARTSTR");
        itemnameStr = getIntent().getStringExtra("ITEMNAMESTR");
        sxflidStr = getIntent().getStringExtra("SXFLIDSTR");


        companyInfoList = LoginUtils.getInstance().getUserInfo().getDATA().getCOMPANYLIST();
       // i(this+"-企业数量："+companyInfoList.size());
        initView();
        initData();
        parseData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_enterprise_information_list;
    }

    private void initView() {
        initTitleBar(getString(R.string.select_enterprise_information), this, null);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private void initData() {
        recycler_view.setLayoutManager(new LinearLayoutManager(EnterpriseInformationActivity.this));
    }

    /**
     * 获取办理服务网点请求
     */
    private void getEnterpriseInfo() {
        ApiRequest request = OAInterface.getUserByIdInfo(LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME());
        invoke.invokeWidthDialog(request, callBack, GET_ENTERPRISE_INFO_CODE);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == GET_ENTERPRISE_INFO_CODE) {

                }
            } else {
                DialogUtils.showToast(EnterpriseInformationActivity.this, message);
            }
        }
    };

    private void parseData() {
        if (null == adapter) {
            adapter = new CommonRvAdapter<UserInfo.CompanyInfo>(companyInfoList, R.layout.enterprise_information_item) {
                @Override
                public void convert(ViewHolderRv holder, UserInfo.CompanyInfo item, int position) {
                    holder.setText(R.id.tv_enterprise_name, item.getCOMPANYNAME());
                    holder.setText(R.id.tv_enterprise_address, item.getQYTXDZ());
                }
            };
            recycler_view.setAdapter(adapter);
        } else {
            adapter.setData(companyInfoList);
        }
        adapter.setOnItemClickListener(new CommonRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                /**
                 * 在线预约
                 */
                if(intentType.equals("1")){
                    Intent intent = new Intent(EnterpriseInformationActivity.this, DeclareNoticeActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("item_id", itemId);
                    intent.putExtra("position", mPosition);
                    intent.putExtra("companyId", companyInfoList.get(position).getID());
                    intent.putExtra("companyAdress", companyInfoList.get(position).getQYTXDZ());
                    startActivity(intent);
                }else{
                    /**
                     * 跳转预约办事
                     */
                    Intent intent2 = new Intent(EnterpriseInformationActivity.this, OnlineBookingHallActivity.class);
                    intent2.putExtra("NORMACCEPTDEPART", normacceptdepartStr);
                    intent2.putExtra("ITEMNAME", itemnameStr);
                    intent2.putExtra("ITEMID", itemId);
                    intent2.putExtra("SITENO", sxflidStr);
                    intent2.putExtra("type", type);
                    intent2.putExtra("COMPANYNAME", companyInfoList.get(position).getCOMPANYNAME());
                    intent2.putExtra("BUSINESSLICENCE", companyInfoList.get(position).getBUSINESSLICENCE());
                    startActivity(intent2);
                }






            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                EnterpriseInformationActivity.this.finish();
                break;
        }
    }
}
