package com.powerrich.office.oa.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.view.chartview.MPChartHelper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 文 件 名：QueryStatisticsActivity
 * 描   述：阳光政务查询统计界面
 * 作   者：Wangzheng
 * 时   间：2017/12/8
 * 版   权：v1.0
 */
public class QueryStatisticsActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_item_statistics, tv_time_statistics, tv_unit_statistics, tv_result_statistics;
    private ListView lv;
    private TextView tv_no_data;
    private TableLayout table;
    private TextView tv_table01;
    private CommonAdapter<StatisticsInfo> adapter;
    private String type = "1";
    private CombinedChart combineChart;
    private PieChart pieChart;
    private List<String> xAxisValues;
    private List<Float> lineValues;
    private List<Float> barValues;
    private Map<String, Float> pieValues;
    private List<StatisticsInfo> statisticsInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_query_statistics;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!BeanUtils.isEmpty(statisticsInfoList)) {
            statisticsInfoList.clear();
        }
        getQueryStatistics(type, "");
    }

    private void initView() {
        initTitleBar(R.string.query_statistics, this, null);
        tv_item_statistics = (TextView) findViewById(R.id.tv_item_statistics);
        tv_time_statistics = (TextView) findViewById(R.id.tv_time_statistics);
        tv_unit_statistics = (TextView) findViewById(R.id.tv_unit_statistics);
        tv_result_statistics = (TextView) findViewById(R.id.tv_result_statistics);
        tv_item_statistics.setOnClickListener(this);
        tv_time_statistics.setOnClickListener(this);
        tv_unit_statistics.setOnClickListener(this);
        tv_result_statistics.setOnClickListener(this);
        tv_time_statistics.setTextColor(Color.GRAY);
        tv_unit_statistics.setTextColor(Color.GRAY);
        tv_result_statistics.setTextColor(Color.GRAY);
        lv = (ListView) findViewById(R.id.listView);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        table = (TableLayout) findViewById(R.id.table);
        tv_table01 = (TextView) findViewById(R.id.tv_table01);
        combineChart = (CombinedChart) findViewById(R.id.combineChart);
        pieChart = (PieChart) findViewById(R.id.pieChart);
    }

    private void initData() {

    }


    /**
     * 查询阳光政务查询统计请求
     *
     * @param type
     * @param year
     */
    private void getQueryStatistics(String type, String year) {
        ApiRequest request = OAInterface.getQueryStatistics(type, year);
        invoke.invokeWidthDialog(request, callBack);
    }

    /**
     * 获取阳光政务查询统计数据解析
     *
     * @param items
     */
    private void parseData(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            tv_no_data.setVisibility(View.VISIBLE);
            return;
        }
        xAxisValues = new ArrayList<>();
        lineValues = new ArrayList<>();
        barValues = new ArrayList<>();
        pieValues = new LinkedHashMap<>();
        if (type.equals("1")) {
            for (ResultItem item : items) {
                StatisticsInfo statisticsInfo = new StatisticsInfo();
                statisticsInfo.setName(item.getString("ITEMNAME"));
                statisticsInfo.setQuantity(item.getString("COUNT"));
                statisticsInfoList.add(statisticsInfo);
            }
            tv_table01.setText(R.string.item_name);
            table.setVisibility(View.VISIBLE);
            combineChart.setVisibility(View.GONE);
            pieChart.setVisibility(View.GONE);
        } else if (type.equals("2")) {
            for (ResultItem item : items) {
                xAxisValues.add(item.getString("NAME"));
                lineValues.add(Float.parseFloat(item.getString("VALUE")));
                barValues.add(Float.parseFloat(item.getString("VALUE")));
            }
            MPChartHelper.setCombineChart(combineChart, xAxisValues, lineValues, barValues, "折线", "柱子");
            table.setVisibility(View.GONE);
            combineChart.setVisibility(View.VISIBLE);
            pieChart.setVisibility(View.GONE);
        } else if (type.equals("3")) {
            for (ResultItem item : items) {
                pieValues.put(item.getString("name"), Float.parseFloat(item.getString("value")));
            }
            MPChartHelper.setPieChart(pieChart, pieValues, "", true);
            table.setVisibility(View.GONE);
            combineChart.setVisibility(View.GONE);
            pieChart.setVisibility(View.VISIBLE);
        } else {
            for (ResultItem item : items) {
                StatisticsInfo statisticsInfo = new StatisticsInfo();
                statisticsInfo.setName(item.getString("NAME"));
                statisticsInfo.setQuantity(item.getString("VALUE"));
                statisticsInfoList.add(statisticsInfo);
            }
            tv_table01.setText(R.string.do_thing_state);
            table.setVisibility(View.VISIBLE);
            combineChart.setVisibility(View.GONE);
            pieChart.setVisibility(View.GONE);
        }


        if (adapter == null) {
            if (!BeanUtils.isEmpty(statisticsInfoList)) {
                adapter = new CommonAdapter<StatisticsInfo>(QueryStatisticsActivity.this, statisticsInfoList, R.layout.query_statistics_form_item) {
                    @Override
                    public void convert(ViewHolder holder, StatisticsInfo item) {
                        holder.setTextView(R.id.tv_table01, item.getName());
                        holder.setTextView(R.id.tv_table02, item.getQuantity());
                    }
                };
                lv.setAdapter(adapter);
            } else {
                tv_no_data.setVisibility(View.VISIBLE);
            }
        } else {
            adapter.setData(statisticsInfoList);
        }
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                List<ResultItem> items = item.getItems("DATA");
                parseData(items);
            } else {
                DialogUtils.showToast(QueryStatisticsActivity.this, message);
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (!BeanUtils.isEmpty(statisticsInfoList)) {
            statisticsInfoList.clear();
        }
        switch (v.getId()) {
            case R.id.system_back:
                QueryStatisticsActivity.this.finish();
                break;
            case R.id.tv_item_statistics:
                tv_item_statistics.setBackgroundResource(R.drawable.select_tab_checked_left);
                tv_item_statistics.setTextColor(Color.WHITE);
                tv_time_statistics.setBackground(null);
                tv_time_statistics.setTextColor(Color.GRAY);
                tv_unit_statistics.setBackground(null);
                tv_unit_statistics.setTextColor(Color.GRAY);
                tv_result_statistics.setBackground(null);
                tv_result_statistics.setTextColor(Color.GRAY);
                type = "1";
                getQueryStatistics(type, "");
                break;
            case R.id.tv_time_statistics:
                tv_item_statistics.setBackground(null);
                tv_item_statistics.setTextColor(Color.GRAY);
                tv_time_statistics.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_main));
                tv_time_statistics.setTextColor(Color.WHITE);
                tv_unit_statistics.setBackground(null);
                tv_unit_statistics.setTextColor(Color.GRAY);
                tv_result_statistics.setBackground(null);
                tv_result_statistics.setTextColor(Color.GRAY);
                type = "2";
                getQueryStatistics(type, "");
                break;
            case R.id.tv_unit_statistics:
                tv_item_statistics.setBackground(null);
                tv_item_statistics.setTextColor(Color.GRAY);
                tv_time_statistics.setBackground(null);
                tv_time_statistics.setTextColor(Color.GRAY);
                tv_unit_statistics.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_main));
                tv_unit_statistics.setTextColor(Color.WHITE);
                tv_result_statistics.setBackground(null);
                tv_result_statistics.setTextColor(Color.GRAY);
                type = "3";
                getQueryStatistics(type, "");
                break;
            case R.id.tv_result_statistics:
                tv_item_statistics.setBackground(null);
                tv_item_statistics.setTextColor(Color.GRAY);
                tv_time_statistics.setBackground(null);
                tv_time_statistics.setTextColor(Color.GRAY);
                tv_unit_statistics.setBackground(null);
                tv_unit_statistics.setTextColor(Color.GRAY);
                tv_result_statistics.setBackgroundResource(R.drawable.select_tab_checked_right);
                tv_result_statistics.setTextColor(Color.WHITE);
                type = "4";
                getQueryStatistics(type, "");
                break;
            default:
                break;
        }
    }

    public static class StatisticsInfo {
        private String name;
        private String quantity;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }

}
