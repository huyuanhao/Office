package com.powerrich.office.oa.activity.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.adapter.ExpandableSocialAdapter;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.tools.AutoUtils;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.SocialInfo;
import com.yt.simpleframe.http.bean.SocialInfoBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InsuranceInquiriesActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;

    @BindView(R.id.expand_list)
    ExpandableListView expandList;

    TextView message;

    TextView name;
    TextView type;
    TextView personalAll;
    TextView companyAll;
    TextView all;
    TextView queryType;
    TextView startDate;
    TextView endDate;
    TextView lastYear;
    TextView lastHalfYear;
    TextView lastMonth;
    Button search;

    private TimePickerView pvTime;
    private PopupWindow pop;
    private String[] allString = new String[]{"全部", "已缴费", "未缴费"};
    ExpandableSocialAdapter adapter;
    List<SocialInfo> list = new ArrayList<>();
    String aac002, aae140, beginAae003, endAae003;
    String currentDate;
    String company = "";

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_insurance_inquiries;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        int type = getIntent().getIntExtra("type", 0);
        currentDate = DateUtils.getDateStr("yyyy-MM");
        beginAae003 = DateUtils.getMonthAgo(new Date(), -120, "yyyy-MM");
        endAae003 = currentDate;
        switch (type) {
            case 0:
                barTitle.setText("基本养老保险");
                aae140 = "11";
                break;
            case 1:
                barTitle.setText("医疗保险");
                aae140 = "31";
                break;
            case 2:
                barTitle.setText("失业保险");
                aae140 = "21";
                break;
            case 3:
                barTitle.setText("工伤保险");
                aae140 = "41";
                break;
            case 4:
                barTitle.setText("生育保险");
                aae140 = "51";
                break;
        }
        aac002 = LoginUtils.getInstance().getUserInfo().getDATA().getIDCARD();
//        aac002 = "36062219570814003X";
        getInfo();

        initData();
    }

    private void initData() {
        setAdapter();
        startDate.setText(DateUtils.getMonthAgo(new Date(), -120, "yyyy/MM/dd"));
        endDate.setText(DateUtils.getDateStr("yyyy/MM/dd"));
        initTimePicker();
    }

    public void setAdapter() {
//        list.addAll(getList());
        View head = LayoutInflater.from(this).inflate(R.layout.activity_insurance_inquiries_head, null);
        View bottom = LayoutInflater.from(this).inflate(R.layout.empty_text, null);
        AutoUtils.auto(head);
        AutoUtils.auto(bottom);
        name = (TextView) head.findViewById(R.id.name);
        type = (TextView) head.findViewById(R.id.type);
        personalAll = (TextView) head.findViewById(R.id.personal_all);
        companyAll = (TextView) head.findViewById(R.id.company_all);
        all = (TextView) head.findViewById(R.id.all);
        queryType = (TextView) head.findViewById(R.id.query_type);
        queryType.setVisibility(View.GONE);
        startDate = (TextView) head.findViewById(R.id.start_date);
        endDate = (TextView) head.findViewById(R.id.end_date);
        lastYear = (TextView) head.findViewById(R.id.last_year);
        lastHalfYear = (TextView) head.findViewById(R.id.last_half_year);
        lastMonth = (TextView) head.findViewById(R.id.last_month);
        search = (Button) head.findViewById(R.id.search);

        message = (TextView) bottom.findViewById(R.id.message);

        queryType.setOnClickListener(this);
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        lastYear.setOnClickListener(this);
        lastHalfYear.setOnClickListener(this);
        lastMonth.setOnClickListener(this);
        search.setOnClickListener(this);

        expandList.addHeaderView(head);
        expandList.addFooterView(bottom);
        adapter = new ExpandableSocialAdapter(this, list);
        expandList.setAdapter(adapter);
    }

    private void initTimePicker() {
        //时间选择器
        pvTime = new TimePickerBuilder(InsuranceInquiriesActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                ((TextView) view).setText(DateUtils.getCustomDateStr(date));
            }
        }).build();
    }

    private void showDepPop(View v) {
        if (null == pop) {
            View view = this.getLayoutInflater().inflate(R.layout.dropdownlist_popupwindow, null);
            pop = DialogUtils.createPopWindow(InsuranceInquiriesActivity.this, view, v, v.getWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            ListView listView = (ListView) view.findViewById(R.id.listView);
            CommonAdapter depAdapter = new CommonAdapter<String>(InsuranceInquiriesActivity.this, new ArrayList<>(Arrays.asList(allString)), R.layout.dropdown_list_item) {
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

    public void getInfo() {
        ApiManager.getApi().SocialInfo(RequestBodyUtils.QuerySocialInfo(aac002, aae140, beginAae003, endAae003))
                .compose(RxSchedulers.<SocialInfoBean>io_main())
                .compose(this.<SocialInfoBean>loadingDialog())
                .subscribe(new BaseSubscriber<SocialInfoBean>(false) {
                    @Override
                    public void result(SocialInfoBean baseBean) {
                        list.clear();
                        if (baseBean.getDATA().getSocialInfoList() != null && baseBean.getDATA().getSocialInfoList().size() > 0) {
                            message.setVisibility(View.GONE);
                            expandList.setVisibility(View.VISIBLE);
                            list.addAll(baseBean.getDATA().getSocialInfoList());
                            setViewssss(baseBean.getDATA());
                            Collections.reverse(list);
                        }else {
                            message.setVisibility(View.VISIBLE);
                            expandList.setVisibility(View.VISIBLE);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNext(SocialInfoBean socialInfoBean) {
                        super.onNext(socialInfoBean);
                        if(!socialInfoBean.getCode().equals("0")){
                            message.setVisibility(View.VISIBLE);
                            expandList.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    public void getInfo2() {
        ApiManager.getApi().SocialInfo(RequestBodyUtils.SocialPayInfo(aac002))
                .compose(RxSchedulers.<SocialInfoBean>io_main())
                .subscribe(new BaseSubscriber<SocialInfoBean>(false) {
                    @Override
                    public void result(SocialInfoBean baseBean) {
                        setView2(baseBean.getDATA().getSocialInfoList().get(0));
                    }
                });
    }

    public List<SocialInfo> getList() {
        List<SocialInfo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SocialInfo socialInfo = new SocialInfo();
            socialInfo.setAac003("名字");
            socialInfo.setAae140("险种类型");
            socialInfo.setAae080grhj("10000");
            socialInfo.setAae080dwhj("100000");
            socialInfo.setAae080hjze("110000");

            socialInfo.setAae003("201808");
            socialInfo.setAae080dw("100");
            socialInfo.setAae080gr("100");
            socialInfo.setAae080ss("200");
            socialInfo.setAae180("50");
            list.add(socialInfo);
        }
        return list;
    }

    public void setViewssss(SocialInfoBean.DATABeanX beanX) {
        getInfo2();
        if (list.size() > 0) {
            SocialInfo socialInfo = list.get(0);
            name.setText(socialInfo.getAac003());
            type.setText(socialInfo.getAae140());
            personalAll.setText(beanX.getAAE020GRHJ() + "元");
            companyAll.setText(beanX.getAAE020DWHJ() + "元");
            all.setText(beanX.getAAE020SSHJ() + "元");
        }
    }

    public void setView2(SocialInfo socialInfo) {
        company = socialInfo.getAab004();

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setAab004(company);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
                startDate.setText(DateUtils.getMonthAgo(new Date(), -12, "yyyy/MM/dd"));
                beginAae003 = DateUtils.getMonthAgo(new Date(), -12, "yyyy-MM");
                endAae003 = currentDate;
                endDate.setText(DateUtils.getDateStr("yyyy/MM/dd"));
                lastYear.setBackgroundResource(R.drawable.gray_corners_bg2);
                lastHalfYear.setBackgroundResource(R.drawable.gray_corners_bg3);
                lastMonth.setBackgroundResource(R.drawable.gray_corners_bg3);

                getInfo();
                break;
            case R.id.last_half_year:
                startDate.setText(DateUtils.getMonthAgo(new Date(), -6, "yyyy/MM/dd"));
                beginAae003 = DateUtils.getMonthAgo(new Date(), -6, "yyyy-MM");
                endAae003 = currentDate;
                endDate.setText(DateUtils.getDateStr("yyyy/MM/dd"));
                lastHalfYear.setBackgroundResource(R.drawable.gray_corners_bg2);
                lastYear.setBackgroundResource(R.drawable.gray_corners_bg3);
                lastMonth.setBackgroundResource(R.drawable.gray_corners_bg3);

                getInfo();
                break;
            case R.id.last_month:
                startDate.setText(DateUtils.getMonthAgo(new Date(), -1, "yyyy/MM/dd"));
                beginAae003 = DateUtils.getMonthAgo(new Date(), -1, "yyyy-MM");
                endAae003 = currentDate;
                endDate.setText(DateUtils.getDateStr("yyyy/MM/dd"));
                lastMonth.setBackgroundResource(R.drawable.gray_corners_bg2);
                lastYear.setBackgroundResource(R.drawable.gray_corners_bg3);
                lastHalfYear.setBackgroundResource(R.drawable.gray_corners_bg3);

                getInfo();
                break;
            case R.id.search:
                beginAae003 = DateUtils.getDateStr(startDate.getText().toString(), "yyyy/MM/dd", "yyyy-MM");
                endAae003 = DateUtils.getDateStr(endDate.getText().toString(), "yyyy/MM/dd", "yyyy-MM");

                getInfo();
                break;
        }
    }

    @OnClick({R.id.bar_back})
    public void onClicks(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
        }
    }
}
