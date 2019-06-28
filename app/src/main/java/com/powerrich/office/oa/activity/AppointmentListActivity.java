package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.AppointmentListBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.view.pull.PullToRefreshBase;
import com.powerrich.office.oa.view.pull.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名：AppointmentListActivity
 * 描述：预约列表
 * 作者：白煜
 * 时间：2018/1/4 0004
 * 版权：
 */

public class AppointmentListActivity extends BaseActivity implements View.OnClickListener{

    private PullToRefreshListView pull_lv;
    private TextView tv_no_data;

    private List<AppointmentListBean.DATABeanX.DATABean> mList = new ArrayList<>();

    private int currentPage = 1;
    private static final int CODE_GET_DATA = 886;
    private ListView lv;
    private CommonAdapter<AppointmentListBean.DATABeanX.DATABean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoginUtils.getInstance().isLoginSuccess()) {
            loadData(currentPage + "");
        }
    }

    private void loadData(String currentPage) {
        invoke.invokeWidthDialog(OAInterface.getMyAppointmentList("",currentPage), callBack, CODE_GET_DATA);
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
                    AppointmentListBean appointmentListBean = gson.fromJson(jsonStr, AppointmentListBean.class);
                    if (currentPage == 1 && !BeanUtils.isEmpty(mList)) {
                        mList.clear();
                    }
                    if(!BeanUtils.isEmpty(appointmentListBean.getDATA().getDATA())) {
                        mList.addAll(appointmentListBean.getDATA().getDATA());
                    }
                    showData(mList);
                    //判断是否已经全部加载完成：如果完成了就关闭“下拉加载更多”功能，否则，继续打开“下拉加载更多”功能
                    if (BeanUtils.isEmpty(appointmentListBean.getDATA().getDATA()) || appointmentListBean.getDATA().getDATA().size
                            () < Constants.COMMON_PAGE) {
                        // 已经全部加载完成，关闭UI组件的下拉加载更多功能
                        pull_lv.onPullDownRefreshComplete();
                        pull_lv.onPullUpRefreshComplete();
                        pull_lv.setHasMoreData(false);
                    } else {
                        // 还有更多数据，继续打开“下拉加载更多”功能
                        pull_lv.onPullDownRefreshComplete();
                        pull_lv.onPullUpRefreshComplete();
                        pull_lv.setHasMoreData(true);
                    }
                }
            } else {
                DialogUtils.showToast(AppointmentListActivity.this, message);
            }
        }
    };

    private void showData(List<AppointmentListBean.DATABeanX.DATABean> list) {
        if (BeanUtils.isEmpty(list)) {
            tv_no_data.setVisibility(View.VISIBLE);
            return;
        }
        if (adapter == null) {
            if (!BeanUtils.isEmpty(list)) {
                adapter = new CommonAdapter<AppointmentListBean.DATABeanX.DATABean>(AppointmentListActivity.this, mList, R.layout
                        .item_appointment_list) {
                    @Override
                    public void convert(ViewHolder holder, AppointmentListBean.DATABeanX.DATABean item) {
                        holder.setTextView(R.id.tv_name, item.getITEMNAME());
                        holder.setTextView(R.id.tv_unit, item.getDEPTNAME());
                        holder.setTextView(R.id.tv_booking_man, item.getREAL_NAME());
//                        holder.setTextView(R.id.tv_time, item.getTRACKING_NUM());
                    }
                };
                lv.setAdapter(adapter);
            } else {
                tv_no_data.setVisibility(View.VISIBLE);
            }

        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void initData() {
        lv = BeanUtils.setProperty(pull_lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AppointmentListActivity.this, AppointmentDetailActivity.class);
                intent.putExtra("a_id", mList.get(position).getA_ID());
                startActivity(intent);
            }
        });
        pull_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                loadData(currentPage + "");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                loadData(currentPage + "");
            }
        });
    }

    private void initView() {
        initTitleBar(R.string.my_booking, this, null);
        pull_lv = (PullToRefreshListView) findViewById(R.id.pull_lv);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                AppointmentListActivity.this.finish();
                break;
        }
    }
}
