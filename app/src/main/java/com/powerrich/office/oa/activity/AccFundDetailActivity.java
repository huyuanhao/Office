package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.adapter.CommonRvAdapter;
import com.powerrich.office.oa.adapter.ViewHolderRv;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.AccFundDetailBean;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.DialogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AccFundDetailActivity extends BaseActivity implements View.OnClickListener, OnRefreshLoadmoreListener {

    private SmartRefreshLayout refresh_layout;
    private RecyclerView recyclerView;
    private Button back;
    private Button search;
    private TextView all;
    private TextView start_date;
    private TextView end_date;
    private TextView last_year;
    private TextView last_half_year;
    private TextView last_month;
    private TextView tv_no_data;
    private TimePickerView pvTime;
    private PopupWindow pop;
    private String[] allString =  new String[]{"全部", "已缴费", "未缴费"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_acc_fund_detail;
    }

    private void initView() {
        initTitleBar("公积金明细", this, null);
        refresh_layout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        back = (Button) findViewById(R.id.back);
        search = (Button) findViewById(R.id.search);
        all = (TextView) findViewById(R.id.all);
        start_date = (TextView) findViewById(R.id.start_date);
        end_date = (TextView) findViewById(R.id.end_date);
        last_year = (TextView) findViewById(R.id.last_year);
        last_half_year = (TextView) findViewById(R.id.last_half_year);
        last_month = (TextView) findViewById(R.id.last_month);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);

        all.setOnClickListener(this);
        start_date.setOnClickListener(this);
        end_date.setOnClickListener(this);
        last_year.setOnClickListener(this);
        last_half_year.setOnClickListener(this);
        last_month.setOnClickListener(this);
        back.setOnClickListener(this);
        search.setOnClickListener(this);

        refresh_layout.setEnableRefresh(false);
        refresh_layout.setOnRefreshLoadmoreListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initData();
    }

    private void initData() {
        start_date.setText(DateUtils.getCustomDateStr(new Date()));
        end_date.setText(DateUtils.getCustomDateStr(new Date()));
        initTimePicker();
        loadData();
    }

    private void loadData() {
        ArrayList<AccFundDetailBean> accFundDetailBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            AccFundDetailBean accFundDetailBean = new AccFundDetailBean();
            accFundDetailBean.setDate("2018-05-18");
            accFundDetailBean.setBalance("5588.0");
            accFundDetailBean.setHj("转入");
            accFundDetailBean.setJf("0.00");
            accFundDetailBean.setDf("0.00");
            accFundDetailBeans.add(accFundDetailBean);
        }
        CommonRvAdapter<AccFundDetailBean> adapter = new CommonRvAdapter<AccFundDetailBean>(accFundDetailBeans, R.layout.acc_fund_detail_item) {
            @Override
            public void convert(ViewHolderRv holder, AccFundDetailBean item, int position) {
                holder.setText(R.id.date, item.getDate());
                holder.setText(R.id.hj, "汇缴：" + item.getHj());
                holder.setText(R.id.balance, "余额：" + item.getBalance());
                holder.setText(R.id.jf, "借方发生额：" + item.getJf());
                holder.setText(R.id.df, "贷方发生额：" + item.getDf());
            }
        };
        recyclerView.setAdapter(adapter);

    }

    private void initTimePicker() {
        //时间选择器
        pvTime = new TimePickerBuilder(AccFundDetailActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                ((TextView) view).setText(DateUtils.getCustomDateStr(date));
            }
        }).build();
    }

    private void showDepPop(View v) {
        if (null == pop) {
            View view = this.getLayoutInflater().inflate(R.layout.dropdownlist_popupwindow, null);
            pop = DialogUtils.createPopWindow(AccFundDetailActivity.this, view, v, v.getWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            ListView listView = (ListView) view.findViewById(R.id.listView);
            CommonAdapter depAdapter = new CommonAdapter<String>(AccFundDetailActivity.this, new ArrayList<>(Arrays.asList(allString)), R.layout.dropdown_list_item) {
                @Override
                public void convert(ViewHolder holder, String item) {
                    holder.setTextView(R.id.tv, item);
                }
            };
            listView.setAdapter(depAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = (String) parent.getAdapter().getItem(position);
                    all.setText(item);
                    if (pop.isShowing()) {
                        pop.dismiss();
                    }
                }
            });
        } else {
            if (pop.isShowing()) {
                pop.dismiss();
            } else {
                pop.showAsDropDown(v);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
            case R.id.back://返回
                finish();
                break;
            case R.id.search://查询
                break;
            case R.id.all://选择全部 、已缴费 、未缴费
                showDepPop(all);
                break;
            case R.id.start_date://开始日期
                pvTime.setDate(DateUtils.dataToCalendar(DateUtils.parseDate(start_date.getText().toString().trim(), DateUtils.DATE_FORMAT_CUSTOM)));
                pvTime.show(start_date);
                break;
            case R.id.end_date://结束日期
                pvTime.setDate(DateUtils.dataToCalendar(DateUtils.parseDate(end_date.getText().toString().trim(), DateUtils.DATE_FORMAT_CUSTOM)));
                pvTime.show(end_date);
                break;
            case R.id.last_year://最近一年
                last_year.setBackgroundResource(R.drawable.gray_corners_bg2);
                last_half_year.setBackgroundResource(R.drawable.gray_corners_bg3);
                last_month.setBackgroundResource(R.drawable.gray_corners_bg3);
                start_date.setText(DateUtils.getLastDateFormat(DateUtils.YEAR));
                end_date.setText(DateUtils.getLastDateFormat(""));
                break;
            case R.id.last_half_year://最近半年
                last_half_year.setBackgroundResource(R.drawable.gray_corners_bg2);
                last_year.setBackgroundResource(R.drawable.gray_corners_bg3);
                last_month.setBackgroundResource(R.drawable.gray_corners_bg3);
                start_date.setText(DateUtils.getLastDateFormat(DateUtils.HALF_YEAR));
                end_date.setText(DateUtils.getLastDateFormat(""));
                break;
            case R.id.last_month://最近一个月
                last_month.setBackgroundResource(R.drawable.gray_corners_bg2);
                last_year.setBackgroundResource(R.drawable.gray_corners_bg3);
                last_half_year.setBackgroundResource(R.drawable.gray_corners_bg3);
                start_date.setText(DateUtils.getLastDateFormat(DateUtils.MONTH));
                end_date.setText(DateUtils.getLastDateFormat(""));
                break;
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        refresh_layout.finishLoadmore(2000);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
    }
}
