package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.SIMeID.SIMeIDApplication;
import com.powerrich.office.oa.adapter.CommonRvAdapter;
import com.powerrich.office.oa.adapter.ViewHolderRv;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.MyCertificateBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.AutoUtils;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;


/**
 * w我的证件
 */
public class MyCertificateActivity extends BaseActivity {
    private RelativeLayout titleLayout, empty_view;
    private ImageView systemBack;
    private TextView tvTopTitle;
    private ImageView btnTopRight;
    private TextView tvTopRight;
    private ImageView btnCustomRight;
    private ImageView btnCustomRightCc;
    private RecyclerView rvContent;

    private CommonRvAdapter<MyCertificateBean.DATABean> adapter;
    private List<MyCertificateBean.DATABean> dataBeanList;
    private int currentpage = 1;

    private final int GET_DATA_REQ = 1;
    private SmartRefreshLayout refreshLayout;
    private RelativeLayout mTitleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isAutoFlag = false;
        super.onCreate(savedInstanceState);
        mTitleLayout = findViewById(R.id.title_layout);
        AutoUtils.auto(titleLayout);
        initView();
        initData();
        initEvent();
        refreshLayout.autoRefresh();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_my_certificate;
    }


    private void initEvent() {
        systemBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter.setOnItemClickListener(new CommonRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //   Toast.makeText(MyCertificateActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                currentpage++;
                getData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentpage = 1;
                getData();
            }
        });

    }

    private void initData() {
        dataBeanList = new ArrayList<>();
        adapter = new CommonRvAdapter<MyCertificateBean.DATABean>(dataBeanList, R.layout.my_space_item) {
            @Override
            public void convert(ViewHolderRv holder, MyCertificateBean.DATABean dataBean, int position) {
                holder.setText(R.id.tv_zjmc_value, dataBean.getNAME());
                holder.setText(R.id.tv_zjhm_value, dataBean.getID_CODE());
                holder.setText(R.id.tv_jyzz_value, dataBean.getHOLDER_NAME());
                holder.setText(R.id.tv_fzjg_value, dataBean.getISSUE_ORG_NAME());
                holder.setText(R.id.tv_qfrq_value, dataBean.getISSUE_DATE());
            }
        };
        rvContent.setAdapter(adapter);
    }

    private void initView() {
        titleLayout = (RelativeLayout) findViewById(R.id.title_layout);
        empty_view = (RelativeLayout) findViewById(R.id.empty_view);
        empty_view.setVisibility(View.GONE);
        systemBack = (ImageView) findViewById(R.id.system_back);
        tvTopTitle = (TextView) findViewById(R.id.tv_top_title);
        btnTopRight = (ImageView) findViewById(R.id.btn_top_right);
        tvTopRight = (TextView) findViewById(R.id.tv_top_right);
        btnCustomRight = (ImageView) findViewById(R.id.btn_custom_right);
        btnCustomRightCc = (ImageView) findViewById(R.id.btn_custom_right_cc);
        rvContent = (RecyclerView) findViewById(R.id.rv_content);

        tvTopTitle = (TextView) findViewById(R.id.tv_top_title);
        tvTopTitle.setText("我的证照");

        systemBack = (ImageView) findViewById(R.id.system_back);
        systemBack.setVisibility(View.VISIBLE);

        rvContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvContent.setItemAnimator(new DefaultItemAnimator());
        // rvContent.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);

    }


    private void getData() {

        ApiRequest request = OAInterface.getCertificateListNew(LoginUtils.getInstance().getUserInfo().getDATA().getUSERID(), "", currentpage + "", +10 + "");
        invoke.invoke(request, callBack, GET_DATA_REQ);


//        ApiManager.getApi().getCertificateNewList(RequestBodyUtils.getCertificateListNew(LoginUtils.getInstance().getUserInfo().getDATA().getUSERID(), "", 1 + "", +10 + ""))
//                .compose(RxSchedulers.<CertificateListNewBean>io_main())
//                .subscribe(new BaseSubscriber<CertificateInfoNew>(refreshLayout) {
//                    @Override
//                    public void result(CertificateInfoNew baseBean) {
//                        if (baseBean.getDATA() != null) {
//                            Log.i("jsc", "result个数: "+baseBean.getDATA().getDATA().size());
//                           // setListData(baseBean.getDATA().getDATA());
//                        } else {
//                          //  setListData(null);
//                        }
//                    }
//                });
    }


    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.finishRefresh();
            } else {
                refreshLayout.finishLoadmore();
            }


            if (!BeanUtils.isEmpty(response)) {
                ResultItem item = response.getResultItem(ResultItem.class);
                String code = item.getString("code");
                String message = item.getString("message");
                if (Constants.SUCCESS_CODE.equals(code)) {
                    if (what == GET_DATA_REQ) {
                        String dataStr = item.getDataStr();
                        MyCertificateBean myCertificateBean = SIMeIDApplication.getmGson().fromJson(dataStr, MyCertificateBean.class);
                        if (currentpage == 1) {
                            dataBeanList = myCertificateBean.getDATA();
                        } else {
                            dataBeanList.addAll(myCertificateBean.getDATA());
                        }
                        //没数据时显示空 布局
                        if (dataBeanList == null || dataBeanList.size() == 0) {
                            empty_view.setVisibility(View.VISIBLE);
                            refreshLayout.setVisibility(View.GONE);
                        } else {
                            empty_view.setVisibility(View.GONE);
                            refreshLayout.setVisibility(View.VISIBLE);
                        }

                        adapter.setData(dataBeanList);


                    } else {
                        DialogUtils.showToast(MyCertificateActivity.this, message);
                    }
                }
            }

        }

        @Override
        public void onReturnError(HttpResponse response, ResultItem error, int what) {
            super.onReturnError(response, error, what);
//            if (what == GET_COLLECT_ITEM_REQ || what == CANCEL_COLLECT_ITEM_REQ) {
//                // isCollecting = false;
//            }
        }

        @Override
        public void onNetError(int what) {
            super.onNetError(what);
//            if (what == GET_COLLECT_ITEM_REQ || what == CANCEL_COLLECT_ITEM_REQ) {
//                //isCollecting = false;
//            }
        }
    };

}
