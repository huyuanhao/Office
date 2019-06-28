package com.powerrich.office.oa.activity.home.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.ExpandableSocialPayMentAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.bean.BasicInfoSbjfcxBean;
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
 * 文件名：社保缴费信息
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/1
 * 版权：
 */

public class SocialSecurityInquryActivity extends BaseActivity implements View.OnClickListener{

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


    private TimePickerView pvTime;
    private PopupWindow pop;
    ExpandableSocialPayMentAdapter adapter;
    List<BasicInfoSbjfcxBean.DATABeanX.DATABean> list = new ArrayList<BasicInfoSbjfcxBean.DATABeanX.DATABean>();
    String aac002,  beginAae003, endAae003;
    String currentDate;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_insurance_inquiries;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getInfo();

    }

    private void initView() {
        int type = getIntent().getIntExtra("type", 0);
        currentDate = DateUtils.getDateStr("yyyy-MM");
        beginAae003 = DateUtils.getMonthAgo(new Date(), -120, "yyyy-MM");
        endAae003 = currentDate;
        barTitle.setText("社保缴费信息");
        aac002 = LoginUtils.getInstance().getUserInfo().getDATA().getIDCARD();
        initData();
    }

    private void initData() {
        setAdapter();
        initTimePicker();
    }

    public void setAdapter() {
        adapter = new ExpandableSocialPayMentAdapter(this, list);
        expandList.setVisibility(View.VISIBLE);
        expandList.setAdapter(adapter);
        View bottom = LayoutInflater.from(this).inflate(R.layout.activity_medical_bottom, null);
        AutoUtils.auto(bottom);

        back = (TextView) bottom.findViewById(R.id.back);


        back.setOnClickListener(this);

        expandList.addFooterView(bottom);
    }

    private void initTimePicker() {
        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                ((TextView) view).setText(DateUtils.getCustomDateStr(date));
            }
        }).build();
    }


    //缴费查询
    public void getInfo() {
        UserInfo.DATABean data = LoginUtils.getInstance().getUserInfo().getDATA();
        String name = data.getREALNAME();
        String idCard = data.getIDCARD();

        ApiRequest api = OAInterface.coBasicInfoSbjfcx(name,idCard);
        invoke.invokeWidthDialog(api, new BaseRequestCallBack() {
            @Override
            public void process(HttpResponse response, int what) {
                ResultItem item = response.getResultItem(ResultItem.class);
                String dataStr = item.getJsonStr();
                BasicInfoSbjfcxBean jfcxBean = GsonUtil.GsonToBean(dataStr, BasicInfoSbjfcxBean.class);
                if(jfcxBean != null && jfcxBean.getDATA() != null && jfcxBean.getDATA().getDATA() != null && jfcxBean.getDATA().getDATA().size() != 0){
                    list = jfcxBean.getDATA().getDATA();
                    initView();

                }else{
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
