package com.powerrich.office.oa.activity.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.ExpandableMedicalRecordsAdapter;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.tools.AutoUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.SocialInfo;
import com.yt.simpleframe.http.bean.SocialInfoBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MedicalRecordsActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.expand_list)
    ExpandableListView expandList;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    TextView back;
    ExpandableMedicalRecordsAdapter adapter;
    List<SocialInfo> list = new ArrayList<>();
    String aac002 = "";
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_medical_records;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    public void initView(){
        barTitle.setText("异地就医备案查询");
        aac002 = LoginUtils.getInstance().getUserInfo().getDATA().getIDCARD();
//        aac002 = "36252619540501031X";

        setAdapter();
        getInfo();
    }

    public void setAdapter(){
        View bottom =  LayoutInflater.from(this).inflate(R.layout.activity_medical_bottom,null);
        AutoUtils.auto(bottom);
        back = (TextView) bottom.findViewById(R.id.back);
        back.setOnClickListener(this);

        adapter = new ExpandableMedicalRecordsAdapter(this,list);
        expandList.setAdapter(adapter);
        expandList.addFooterView(bottom);

    }

    public void getInfo() {
        ApiManager.getApi().SocialInfo(RequestBodyUtils.QueryMedicalrecords(aac002))
                .compose(RxSchedulers.<SocialInfoBean>io_main())
                .compose(this.<SocialInfoBean>loadingDialog())
                .subscribe(new BaseSubscriber<SocialInfoBean>(false) {
                    @Override
                    public void result(SocialInfoBean baseBean) {
                        list.clear();
                        if(baseBean.getDATA().getSocialInfoList() != null && baseBean.getDATA().getSocialInfoList().size() > 0) {
                            emptyView.setVisibility(View.GONE);
                            list.addAll(baseBean.getDATA().getSocialInfoList());
                            Collections.reverse(list);
                        }else {
                            emptyView.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onNext(SocialInfoBean socialInfoBean) {
                        super.onNext(socialInfoBean);
                        if(!socialInfoBean.getCode().equals("0")){
                            emptyView.setVisibility(View.VISIBLE);
                            expandList.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
    @OnClick({R.id.bar_back,R.id.empty_back})
    public void onClicks(View v){
        switch (v.getId()){
            case R.id.bar_back:
                finish();
                break;
            case R.id.empty_back:
                finish();
                break;
        }
    }
}
