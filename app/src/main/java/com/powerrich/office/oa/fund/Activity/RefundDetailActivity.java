package com.powerrich.office.oa.fund.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.adapter.CommonRvAdapter;
import com.powerrich.office.oa.adapter.ViewHolderRv;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.fund.bean.FundBean;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.ReFundHKJHInfoBean;
import com.yt.simpleframe.http.bean.ReFundInfoBean;

import com.yt.simpleframe.http.bean.ReFundYQHKInfoBean;
import com.yt.simpleframe.http.bean.entity.ReFundHKJHInfo;
import com.yt.simpleframe.http.bean.entity.ReFundInfo;
import com.yt.simpleframe.http.bean.entity.ReFundYQCXInfo;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.T;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 还款明细、还款计划、 贷款逾期查询
 */
public class RefundDetailActivity extends BaseActivity implements View.OnClickListener, OnRefreshLoadmoreListener {


    private RelativeLayout titleLayout;
    private ImageView systemBack;
    private TextView tvTopTitle;
    private ImageView btnTopRight;
    private TextView tvTopRight;
    private ImageView btnCustomRight;
    private ImageView btnCustomRightCc;
    private LinearLayout topSearch;
    private TextView queryType;
    private TextView startDate;
    private TextView endDate;
    private TextView lastYear;
    private TextView lastHalfYear;
    private TextView lastMonth;
    private Button search;


    private RecyclerView rvContent;
    private CommonRvAdapter mReFundBeanCommonRvAdapter;
    private PopupWindow pop;
    private String[] allString = new String[]{"全部", "已缴费", "未缴费"};

    private TimePickerView pvTime;
    String currentDate;
    String aac002, aae140, beginAae003, endAae003;
    private SmartRefreshLayout refreshLayout;

    // 0还款明细  1还款计划 2贷款逾期查询
    private int intentType = 0;


    @Override
    protected int provideContentViewId() {

        return R.layout.activity_refund_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        intentType = getIntent().getIntExtra("type", 0);
        initView();
        if (intentType == 0) {
            initTitleBar("还款明细", this, null);
        } else if (intentType == 1) {
            initTitleBar("还款计划", this, null);
        } else {
            initTitleBar("贷款逾期查询", this, null);
            topSearch.setVisibility(View.GONE);
        }


        initData();

        initEvent();

        getNetworkData();

    }

    private void getNetworkData() {
        if (intentType == 0) {
            getInfo();
        } else if (intentType == 1) {
            getHKJHInfo();
        } else {
            getYQWHKInfo();
        }
    }

    private void initEvent() {
        refreshLayout.setOnRefreshLoadmoreListener(this);
        mReFundBeanCommonRvAdapter.setOnItemClickListener(new CommonRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // ToastUtils.showMessage(RefundDetailActivity.this, "" + position);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.query_type:
                showDepPop(queryType);
                break;

            case R.id.start_date:

                pvTime.setDate(DateUtils.dataToCalendar(DateUtils.parseDate(startDate.getText().toString().trim(), "yyyy/MM/dd")));
                pvTime.show(startDate);
                break;
            case R.id.end_date:
                pvTime.setDate(DateUtils.dataToCalendar(DateUtils.parseDate(endDate.getText().toString().trim(), "yyyy/MM/dd")));
                pvTime.show(endDate);
                break;
            case R.id.last_year:

                beginAae003 = DateUtils.getMonthAgo(new Date(), -12, "yyyy-MM-dd");
                endAae003 = currentDate;

                startDate.setText(DateUtils.getMonthAgo(new Date(), -12, "yyyy/MM/dd"));
                endDate.setText(DateUtils.getDateStr("yyyy/MM/dd"));
                lastYear.setBackgroundResource(R.drawable.gray_corners_bg2);
                lastHalfYear.setBackgroundResource(R.drawable.gray_corners_bg3);
                lastMonth.setBackgroundResource(R.drawable.gray_corners_bg3);

                break;
            case R.id.last_half_year:
                startDate.setText(DateUtils.getMonthAgo(new Date(), -6, "yyyy/MM/dd"));
                beginAae003 = DateUtils.getMonthAgo(new Date(), -6, "yyyy-MM-dd");
                endAae003 = currentDate;
                endDate.setText(DateUtils.getDateStr("yyyy/MM/dd"));
                lastHalfYear.setBackgroundResource(R.drawable.gray_corners_bg2);
                lastYear.setBackgroundResource(R.drawable.gray_corners_bg3);
                lastMonth.setBackgroundResource(R.drawable.gray_corners_bg3);
                break;
            case R.id.last_month:
                startDate.setText(DateUtils.getMonthAgo(new Date(), -1, "yyyy/MM/dd"));
                beginAae003 = DateUtils.getMonthAgo(new Date(), -1, "yyyy-MM-dd");
                endAae003 = currentDate;
                endDate.setText(DateUtils.getDateStr("yyyy/MM/dd"));
                lastMonth.setBackgroundResource(R.drawable.gray_corners_bg2);
                lastYear.setBackgroundResource(R.drawable.gray_corners_bg3);
                lastHalfYear.setBackgroundResource(R.drawable.gray_corners_bg3);
                break;

            case R.id.search:

                beginAae003 = DateUtils.getDateStr(startDate.getText().toString(), "yyyy/MM/dd", "yyyy-MM-dd");
                endAae003 = DateUtils.getDateStr(endDate.getText().toString(), "yyyy/MM/dd", "yyyy-MM-dd");

                getNetworkData();
                break;
        }

    }

    /**
     * 还款明细
     */
    List< ReFundInfo > reFundBeans = new ArrayList<>();
    /**
     * 还款计划
     */
    List< ReFundHKJHInfo > mReFundHKJHInfos = new ArrayList<>();
    /**
     * //逾期未还款
     */
    List< ReFundYQCXInfo > mReFundYQCXInfos = new ArrayList<>();

    private void initView() {
        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        rvContent.setLayoutManager(new LinearLayoutManager(this));


        if (intentType == 0) {
            /**
             * 还款明细
             */
            mReFundBeanCommonRvAdapter = new CommonRvAdapter< ReFundInfo >(reFundBeans, R.layout.refund_item) {
                @Override
                public void convert(ViewHolderRv holder, ReFundInfo item, int position) {


                    holder.setText(R.id.tv_time, item.getHkrq());

                    holder.setText(R.id.tv_line1_name1, "业务摘要:");
                    holder.setText(R.id.tv_line1_name2, "本金余额:");
                    holder.setText(R.id.tv_line1_value1, item.getYwzy());
                    holder.setText(R.id.tv_line1_value2, item.getBjye());


                    holder.setText(R.id.tv_line2_name1, "偿还本金:");
                    holder.setText(R.id.tv_line2_name2, "已还本金:");
                    holder.setText(R.id.tv_line2_value1, item.getChbj());
                    holder.setText(R.id.tv_line2_value2, item.getYhbjhj());

                    holder.setText(R.id.tv_line3_name1, "偿还利息:");
                    holder.setText(R.id.tv_line3_name2, "已还利息:");
                    holder.setText(R.id.tv_line3_value1, item.getChlx());
                    holder.setText(R.id.tv_line3_value2, item.getYhlxhj());


                    holder.setText(R.id.tv_line4_name1, "偿还罚息:");
                    holder.setText(R.id.tv_line4_name2, "已还罚息:");
                    holder.setText(R.id.tv_line4_value1, item.getChfx());
                    holder.setText(R.id.tv_line4_value2, item.getYhfxhj());
                }
            };
        } else if (intentType == 1) {
            /**
             * 还款计划
             */
            mReFundBeanCommonRvAdapter = new CommonRvAdapter< ReFundHKJHInfo >(mReFundHKJHInfos, R.layout.refund_item) {
                @Override
                public void convert(ViewHolderRv holder, ReFundHKJHInfo item, int position) {


                    holder.setText(R.id.tv_time, item.getYhrq());


                    holder.setText(R.id.tv_line1_name1, "应还本金:");
                    holder.setText(R.id.tv_line1_name2, "应还本金合计:");
                    holder.setText(R.id.tv_line1_value1, item.getYhbj());
                    holder.setText(R.id.tv_line1_value2, item.getYhbjhj());


                    holder.setText(R.id.tv_line2_name1, "应还利息:");
                    holder.setText(R.id.tv_line2_name2, "应还利息合计:");
                    holder.setText(R.id.tv_line2_value1, item.getYhlx());
                    holder.setText(R.id.tv_line2_value2, item.getYhlxhj());

                    holder.setText(R.id.tv_line3_name1, "应还本息:");
                    holder.setText(R.id.tv_line3_name2, "应还本息合计:");
                    holder.setText(R.id.tv_line3_value1, item.getYhbx());
                    holder.setText(R.id.tv_line3_value2, item.getYhbxhj());


                    holder.setText(R.id.tv_line4_name1, "应还期数:");
                    holder.setText(R.id.tv_line4_name2, "应还日期:");
                    holder.setText(R.id.tv_line4_value1, item.getYhqs());
                    holder.setText(R.id.tv_line4_value2, item.getYhrq());
                }
            };
        } else {
            /**
             *   //逾期未还款
             */
            mReFundBeanCommonRvAdapter = new CommonRvAdapter< ReFundYQCXInfo >(mReFundYQCXInfos, R.layout.refund_item) {
                @Override
                public void convert(ViewHolderRv holder, ReFundYQCXInfo item, int position) {
                    holder.itemView.findViewById(R.id.ll_line4).setVisibility(View.GONE);

                    holder.setText(R.id.tv_time, item.getYhny());

                    holder.setText(R.id.tv_line1_name1, "逾期本金:");
                    holder.setText(R.id.tv_line1_name2, "逾期本金合计:");
                    holder.setText(R.id.tv_line1_value1, item.getYqbj());
                    holder.setText(R.id.tv_line1_value2, item.getYqbjhj());


                    holder.setText(R.id.tv_line2_name1, "逾期利息:");
                    holder.setText(R.id.tv_line2_name2, "逾期利息合计:");
                    holder.setText(R.id.tv_line2_value1, item.getYqlx());
                    holder.setText(R.id.tv_line2_value2, item.getYqlxhj());

                    holder.setText(R.id.tv_line3_name1, "逾期罚息:");
                    holder.setText(R.id.tv_line3_name2, "逾期罚息合计:");
                    holder.setText(R.id.tv_line3_value1, item.getYqfx());
                    holder.setText(R.id.tv_line3_value2, item.getYqfxhj());


                }
            };
        }


        rvContent.setAdapter(mReFundBeanCommonRvAdapter);
        titleLayout = (RelativeLayout) findViewById(R.id.title_layout);
        systemBack = (ImageView) findViewById(R.id.system_back);
        tvTopTitle = (TextView) findViewById(R.id.tv_top_title);
        btnTopRight = (ImageView) findViewById(R.id.btn_top_right);
        tvTopRight = (TextView) findViewById(R.id.tv_top_right);
        btnCustomRight = (ImageView) findViewById(R.id.btn_custom_right);
        btnCustomRightCc = (ImageView) findViewById(R.id.btn_custom_right_cc);
        topSearch = (LinearLayout) findViewById(R.id.top_search);
        queryType = (TextView) findViewById(R.id.query_type);
        queryType.setVisibility(View.GONE);
        startDate = (TextView) findViewById(R.id.start_date);
        endDate = (TextView) findViewById(R.id.end_date);
        lastYear = (TextView) findViewById(R.id.last_year);
        lastHalfYear = (TextView) findViewById(R.id.last_half_year);
        lastMonth = (TextView) findViewById(R.id.last_month);
        search = (Button) findViewById(R.id.search);
        initTimePicker();
        queryType.setOnClickListener(this);
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        lastYear.setOnClickListener(this);
        lastHalfYear.setOnClickListener(this);
        lastMonth.setOnClickListener(this);
        search.setOnClickListener(this);


    }

    private void initData() {

        startDate.setText(DateUtils.getMonthAgo(new Date(), -120, "yyyy/MM/dd"));
        endDate.setText(DateUtils.getDateStr("yyyy/MM/dd"));
        currentDate = DateUtils.getDateStr("yyyy-MM");


        beginAae003 = DateUtils.getDateStr(startDate.getText().toString(), "yyyy/MM/dd", "yyyy-MM-dd");
        endAae003 = DateUtils.getDateStr(endDate.getText().toString(), "yyyy/MM/dd", "yyyy-MM-dd");
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {

        pageIndex++;
        getNetworkData();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageIndex = 1;
        getNetworkData();
    }

    private void initTimePicker() {
        //时间选择器
        pvTime = new TimePickerBuilder(RefundDetailActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                ((TextView) view).setText(DateUtils.getCustomDateStr(date));
            }
        }).build();
    }


    private int pageIndex = 1;

    //<editor-fold desc="还款明细">
    public void getInfo() {
        Log.e("jsc", "RefundDetailActivity-getInfo:" + pageIndex);
        ApiManager.getApi().queryDkhkmxcx(RequestBodyUtils.queryDkhkmxcx(FundBean.zjbzxbm, "20180502030002", beginAae003, endAae003, String.valueOf(pageIndex), "10"))
                .compose(RxSchedulers.< ReFundInfoBean >io_main())
                .compose(this.< ReFundInfoBean >loadingDialog())
                .subscribe(new BaseSubscriber< ReFundInfoBean >(refreshLayout) {
                    @Override
                    public void result(ReFundInfoBean baseBean) {
                        Log.e("jsc", "RefundDetailActivity-result:" + baseBean.getDATA().getDATA().size());
                        if (!BeanUtils.isEmpty(baseBean.getDATA().getDATA())) {
                            if (pageIndex == 1) {
                                reFundBeans.clear();
                                reFundBeans.addAll(baseBean.getDATA().getDATA());
                            } else {
                                reFundBeans.addAll(baseBean.getDATA().getDATA());
                            }
                            mReFundBeanCommonRvAdapter.notifyDataSetChanged();
                        }

                    }


                });
    }
    //</editor-fold>

    //<editor-fold desc="还款计划">
    public void getHKJHInfo() {
        Log.e("jsc", "RefundDetailActivity-getInfo:" + pageIndex);
        ApiManager.getApi().queryHKJH(RequestBodyUtils.queryHkjhcx(FundBean.zjbzxbm, "20180502030002", beginAae003, endAae003, String.valueOf(pageIndex), "10"))
                .compose(RxSchedulers.< ReFundHKJHInfoBean >io_main())
                .compose(this.< ReFundHKJHInfoBean >loadingDialog())
                .subscribe(new BaseSubscriber< ReFundHKJHInfoBean >(refreshLayout) {
                    @Override
                    public void result(ReFundHKJHInfoBean baseBean) {
                        // Log.e("jsc", "RefundDetailActivity-result:" + baseBean.getDATA().getDATA().size());

                        if (baseBean != null) {
                            if (!BeanUtils.isEmpty(baseBean.getDATA().getDATA())) {
                                if (pageIndex == 1) {
                                    mReFundHKJHInfos.clear();
                                    mReFundHKJHInfos.addAll(baseBean.getDATA().getDATA());
                                } else {
                                    mReFundHKJHInfos.addAll(baseBean.getDATA().getDATA());
                                }
                                mReFundBeanCommonRvAdapter.notifyDataSetChanged();
                            }
                        }
                    }


                });
    }
    //</editor-fold>

    //<editor-fold desc="逾期未还款">
    public void getYQWHKInfo() {
        Log.e("jsc", "RefundDetailActivity-getInfo:" + pageIndex);
        ApiManager.getApi().queryYQWHK(RequestBodyUtils.queryYqwhk(FundBean.zjbzxbm2, FundBean.jkhtbh2))
                .compose(RxSchedulers.< ReFundYQHKInfoBean >io_main())
                .compose(this.< ReFundYQHKInfoBean >loadingDialog())
                .subscribe(new BaseSubscriber< ReFundYQHKInfoBean >(refreshLayout) {
                    @Override
                    public void result(ReFundYQHKInfoBean baseBean) {
                        Log.e("jsc", "RefundDetailActivity-result:" + baseBean.getDATA().getDATA().size());
                        if (!BeanUtils.isEmpty(baseBean.getDATA().getDATA())) {
                            if (pageIndex == 1) {
                                mReFundYQCXInfos.clear();
                                mReFundYQCXInfos.addAll(baseBean.getDATA().getDATA());
                            } else {
                                mReFundYQCXInfos.addAll(baseBean.getDATA().getDATA());
                            }
                            mReFundBeanCommonRvAdapter.notifyDataSetChanged();
                        }

                    }


                });
    }

    //</editor-fold>

    private void showDepPop(View v) {
        if (null == pop) {
            View view = this.getLayoutInflater().inflate(R.layout.dropdownlist_popupwindow, null);
            pop = DialogUtils.createPopWindow(RefundDetailActivity.this, view, v, v.getWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            ListView listView = (ListView) view.findViewById(R.id.listView);
            CommonAdapter depAdapter = new CommonAdapter< String >(RefundDetailActivity.this, new ArrayList<>(Arrays.asList(allString)), R.layout.dropdown_list_item) {
                @Override
                public void convert(ViewHolder holder, String item) {
                    holder.setTextView(R.id.tv, item);
                }
            };
            listView.setAdapter(depAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView< ? > parent, View view, int position, long id) {
                    String item = (String) parent.getAdapter().getItem(position);
                    queryType.setText(item);
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


}
