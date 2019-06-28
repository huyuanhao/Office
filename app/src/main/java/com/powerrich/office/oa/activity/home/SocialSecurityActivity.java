package com.powerrich.office.oa.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.SocialAdapter;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.ImgTxInfo;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.tools.LoginUtils;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.SocialInfoBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 社保查询主页
 * yuanhaohu
 */
public class SocialSecurityActivity extends BaseActivity {
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    SocialAdapter adapter;
    int [] tvs = new int[]{R.string.social_security1,R.string.social_security2,R.string.social_security3
    ,R.string.social_security4,R.string.social_security5,R.string.social_security6,
            R.string.social_security7,R.string.social_security8,R.string.social_security9};

    String aac002 = "";

    int[] imgs = new int[]{R.drawable.social1,R.drawable.social2,R.drawable.social3,R.drawable.social4,R.drawable.social5,
            R.drawable.social6, R.drawable.social7,R.drawable.social8,R.drawable.social9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_socialsecurity;
    }

    public void initView(){
        aac002 = LoginUtils.getInstance().getUserInfo().getDATA().getIDCARD();
//        getInfo1("360622198111145827");
//        getInfo1();
        barTitle.setText("城镇职工社保查询");
        List<ImgTxInfo> list = new ArrayList<>();
        for (int i = 0; i < tvs.length; i++) {
            list.add(new ImgTxInfo(getResources().getString(tvs[i]),imgs[i]));
        }
        adapter = new SocialAdapter(R.layout.item_social,list);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
//        recyclerview.addItemDecoration(new SpaceItemDecoration(1,this,R.color.bg_app,true));
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(SocialSecurityActivity.this, SocialInfoActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(SocialSecurityActivity.this, InsuranceInquiriesActivity.class)
                        .putExtra("type",0));
                        break;
                    case 2:
                        startActivity(new Intent(SocialSecurityActivity.this, InsuranceInquiriesActivity.class)
                                .putExtra("type",1));
                        break;
                    case 3:
                        startActivity(new Intent(SocialSecurityActivity.this, InsuranceInquiriesActivity.class)
                                .putExtra("type",2));
                        break;
                    case 4:
                        startActivity(new Intent(SocialSecurityActivity.this, InsuranceInquiriesActivity.class)
                                .putExtra("type",3));
                        break;
                    case 5:
                        startActivity(new Intent(SocialSecurityActivity.this, InsuranceInquiriesActivity.class)
                                .putExtra("type",4));
                        break;
                    case 6://异地就医备案查询
                        startActivity(new Intent(SocialSecurityActivity.this, MedicalRecordsActivity.class));
                        break;
                    case 7://参保缴费证明
                        startActivity(new Intent(SocialSecurityActivity.this, SocialProveActivity.class));
                        break;
                    case 8://养老金收入证明
                        startActivity(new Intent(SocialSecurityActivity.this, OldProveActivity.class));
                        break;
                }
            }
        });
    }

    String Aac002 = "";
    String aac004 = "男";
    public void getInfo1() {
        ApiManager.getApi().SocialInfo(RequestBodyUtils.SocialInfo(aac002))
                .compose(RxSchedulers.<SocialInfoBean>io_main())
                .subscribe(new BaseSubscriber<SocialInfoBean>() {
                    @Override
                    public void result(SocialInfoBean baseBean) {
                        if(baseBean.getDATA()!=null && baseBean.getDATA().getSocialInfoList().size()>0) {
                            Aac002 = baseBean.getDATA().getSocialInfoList().get(0).getAac002();
                            aac004 = baseBean.getDATA().getSocialInfoList().get(0).getAac004();
                        }
                    }
                });
    }

    @OnClick({R.id.bar_back})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.bar_back:
                finish();
                break;
        }
    }
}
