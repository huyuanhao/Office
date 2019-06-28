package com.powerrich.office.oa.activity.home.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.ExpandableSocialPutAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.bean.BasicInfoFfcxBean;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.AutoUtils;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.GsonUtil;
import com.powerrich.office.oa.tools.LoginUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：养老金发放
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/1
 * 版权：
 */

public class PensionPutActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;

    @BindView(R.id.expand_list)
    ExpandableListView expandList;
    @BindView(R.id.empty_back)
    TextView emptyBack;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    TextView back;


    TextView lastYear;
    TextView lastHalfYear;
    TextView lastMonth;

    TextView startDate;
    TextView endDate;
    Button search;
    TextView queryType;

    private TimePickerView pvTime;
    ExpandableSocialPutAdapter adapter;
    List<BasicInfoFfcxBean.DATABeanX.DATABean> list = new ArrayList<BasicInfoFfcxBean.DATABeanX.DATABean>();
    String aac002,  beginAae003, endAae003;
    String currentDate;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_insurance_put_inquiries;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getInfo("195001","205001");

    }

    private void initView() {
        int type = getIntent().getIntExtra("type", 0);
        currentDate = DateUtils.getDateStr("yyyy-MM");
        beginAae003 = DateUtils.getMonthAgo(new Date(), -120, "yyyy-MM");
        endAae003 = currentDate;
        barTitle.setText("养老发放查询");
        aac002 = LoginUtils.getInstance().getUserInfo().getDATA().getIDCARD();
        initData();
    }

    private void initData() {
        setAdapter();
        initTimePicker();
    }

    public void setAdapter() {
        if(adapter != null){
            adapter.setData(list);
            adapter.notifyDataSetChanged();
        }else{
            adapter = new ExpandableSocialPutAdapter(this, list);
            expandList.setVisibility(View.VISIBLE);
            expandList.setAdapter(adapter);
//            View head = LayoutInflater.from(this).inflate(R.layout.activity_insurance_inquiries_head, null);
            View bottom = LayoutInflater.from(this).inflate(R.layout.activity_medical_bottom, null);
//            AutoUtils.auto(head);
            AutoUtils.auto(bottom);

            queryType = (TextView) findViewById(R.id.query_type);
            queryType.setVisibility(View.GONE);

            lastYear = (TextView) findViewById(R.id.last_year);
            lastHalfYear = (TextView) findViewById(R.id.last_half_year);
            lastMonth = (TextView) findViewById(R.id.last_month);


            startDate = (TextView) findViewById(R.id.start_date);
            endDate = (TextView) findViewById(R.id.end_date);
            search = (Button) findViewById(R.id.search);
            back = (TextView) bottom.findViewById(R.id.back);
            startDate.setOnClickListener(this);
            endDate.setOnClickListener(this);
            search.setOnClickListener(this);

            lastYear.setOnClickListener(this);
            lastHalfYear.setOnClickListener(this);
            lastMonth.setOnClickListener(this);


            back.setOnClickListener(this);
//            expandList.addHeaderView(head);
            expandList.addFooterView(bottom);

            startDate.setText(DateUtils.getMonthAgo(new Date(), -120, "yyyy/MM"));
            endDate.setText(DateUtils.getDateStr("yyyy/MM"));
        }

    }

    private void initTimePicker() {
        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                ((TextView) view).setText(DateUtils.getCustomDateYM(date));
            }
        }).setType(new boolean[]{true,true,false,false,false,false}).build();
    }


    //缴费查询
    public void getInfo(String startTime,String endTime) {

        UserInfo.DATABean data = LoginUtils.getInstance().getUserInfo().getDATA();
        String name = data.getREALNAME();
        String idCard = data.getIDCARD();
        ApiRequest api = OAInterface.coBasicInfoFfcx(name,idCard,startTime,endTime);
        invoke.invokeWidthDialog(api, new BaseRequestCallBack() {
            @Override
            public void process(HttpResponse response, int what) {
                ResultItem item = response.getResultItem(ResultItem.class);
                String dataStr = item.getJsonStr();
                BasicInfoFfcxBean jfcxBean = GsonUtil.GsonToBean(dataStr, BasicInfoFfcxBean.class);
                if(jfcxBean != null && jfcxBean.getDATA() != null && jfcxBean.getDATA().getDATA() != null && jfcxBean.getDATA().getDATA().size() != 0){
                    expandList.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    list = jfcxBean.getDATA().getDATA();
                    initView();

                }else{
                    initView();
                    //没有数据显示
                    expandList.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNetError(int what) {
                super.onNetError(what);
                expandList.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }
        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_date:
                pvTime.setDate(DateUtils.dataToCalendar(DateUtils.parseDate(startDate.getText().toString().trim(), "yyyy/MM")));
                pvTime.show(startDate);
                break;
            case R.id.end_date:
                pvTime.setDate(DateUtils.dataToCalendar(DateUtils.parseDate(endDate.getText().toString().trim(), "yyyy/MM")));
                pvTime.show(endDate);
                break;
            case R.id.search:
                beginAae003 = startDate.getText().toString().trim().replaceAll("/","");
                endAae003 = endDate.getText().toString().trim().replaceAll("/","");
                getInfo(beginAae003,endAae003);
                break;

            case R.id.last_year:
                startDate.setText(DateUtils.getMonthAgo(new Date(), -12, "yyyy/MM"));
                beginAae003 = DateUtils.getMonthAgo(new Date(), -12, "yyyyMM");
                endAae003 = currentDate;
                lastYear.setBackgroundResource(R.drawable.gray_corners_bg2);
                lastHalfYear.setBackgroundResource(R.drawable.gray_corners_bg3);
                lastMonth.setBackgroundResource(R.drawable.gray_corners_bg3);
                getInfo(beginAae003,endAae003);
                break;
            case R.id.last_half_year:
                startDate.setText(DateUtils.getMonthAgo(new Date(), -6, "yyyy/MM"));
                beginAae003 = DateUtils.getMonthAgo(new Date(), -6, "yyyyMM");
                endAae003 = currentDate;
                lastHalfYear.setBackgroundResource(R.drawable.gray_corners_bg2);
                lastYear.setBackgroundResource(R.drawable.gray_corners_bg3);
                lastMonth.setBackgroundResource(R.drawable.gray_corners_bg3);
                getInfo(beginAae003,endAae003);
                break;
            case R.id.last_month:
                startDate.setText(DateUtils.getMonthAgo(new Date(), -1, "yyyy/MM"));
                beginAae003 = DateUtils.getMonthAgo(new Date(), -1, "yyyyMM");
                endAae003 = currentDate;
                lastMonth.setBackgroundResource(R.drawable.gray_corners_bg2);
                lastYear.setBackgroundResource(R.drawable.gray_corners_bg3);
                lastHalfYear.setBackgroundResource(R.drawable.gray_corners_bg3);
                getInfo(beginAae003,endAae003);
                break;


            case R.id.back:
                finish();
                break;
        }
    }

    @OnClick({R.id.bar_back,R.id.empty_back})
    public void onClicks(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
            case R.id.empty_back:
                finish();
                break;
        }
    }
}
