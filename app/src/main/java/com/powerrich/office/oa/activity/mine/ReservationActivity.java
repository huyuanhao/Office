package com.powerrich.office.oa.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.OnlineBookingDetailActivity;
import com.powerrich.office.oa.activity.mine.base.BaseRefreshActivity;
import com.powerrich.office.oa.enums.SearchFragmentType;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.viewholder.ProcessViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.ReservationListBean;
import com.yt.simpleframe.http.bean.entity.ReservationInfo;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.KeyboardUtils;
import com.yt.simpleframe.view.StatusView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 文件名：
 * 描述：我的预约
 * 作者：梁帆
 * 时间：2018/6/6/006
 * 版权：
 */
public class ReservationActivity extends BaseRefreshActivity<ReservationInfo> {

    Unbinder unbinder;
    @BindView(R.id.et_search_txt)
    EditText mEtSearchTxt;
    @BindView(R.id.bt_search)
    Button mBtSearch;
    @BindView(R.id.tv_mine)
    TextView mTvMine;
    @BindView(R.id.cb_select_all)
    CheckBox mCbSelectAll;
    @BindView(R.id.tv_select)
    TextView mTvSelect;
    @BindView(R.id.tv_select_count)
    TextView mTvSelectCount;
    @BindView(R.id.bt_del)
    Button mBtDel;
    @BindView(R.id.lt_frame)
    LinearLayout mLtFrame;


    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_collection);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        statusView = (StatusView) view.findViewById(R.id.multiplestatusview);
        ButterKnife.bind(this, view);
        mLtFrame.setVisibility(View.GONE);
        mTvMine.setText("预约事项");
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的预约");
        showBack();
//        refreshLayout.autoRefresh(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void loadListData() {
        getDate("");
    }

    public void getDate(final String searchName) {
        ApiManager.getApi().getMyAppointmentList(RequestBodyUtils.getMyAppointmentList(
                LoginUtils.getInstance().getUserInfo().getAuthtoken(), searchName, kPage, kPageSize))
                .compose(RxSchedulers.<ReservationListBean>io_main())
                .subscribe(new BaseSubscriber<ReservationListBean>(refreshLayout) {
                    @Override
                    public void result(ReservationListBean listBean) {
                        ArrayList<ReservationInfo> data = listBean.getDATA().getDATA();
                        if (!TextUtils.isEmpty(searchName)) {
                            _adapter.clearDatas();
                        }
                        if(listBean != null && listBean.getDATA() != null){
                            setListData(listBean.getDATA().getDATA());
                        }else{
                            setListData(null);
                        }
                    }
                });
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(int viewType) {
        View view = inflateContentView(R.layout.item_process_fragment);
        ProcessViewHolder holder = new ProcessViewHolder(unbinder, view, SearchFragmentType.预约);
        holder.mCbProcess.setVisibility(View.GONE);
        return holder;
    }

    boolean longboo = false;

    @Override
    public void BindViewHolder(RecyclerView.ViewHolder viewHolder, final int position, int viewType, final Object data) {
        final ProcessViewHolder holder = (ProcessViewHolder) viewHolder;
        holder.bindView(data);
        holder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReservationInfo reservationInfo = (ReservationInfo) data;
                Intent intent = new Intent(ReservationActivity.this, OnlineBookingDetailActivity.class);
                intent.putExtra("a_id", reservationInfo.getA_ID());
                startActivity(intent);
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }

    @OnClick({R.id.bt_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_search:
                String txt = mEtSearchTxt.getText().toString();
                if (!TextUtils.isEmpty(txt)) {
                    getDate(txt);
                } else {
//                    refreshLayout.autoRefresh(0);
                    ToastUtils.showMessage(this,"请输入搜索内容");
                }
                KeyboardUtils.hideSoftInput(this);
                break;
        }
    }


}
