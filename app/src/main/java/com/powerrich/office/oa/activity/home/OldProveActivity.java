package com.powerrich.office.oa.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.SocialInfo;
import com.yt.simpleframe.http.bean.SocialInfoBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OldProveActivity extends BaseActivity {
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.id)
    TextView id;
    @BindView(R.id.id_code)
    TextView idCode;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.company_code)
    TextView companyCode;
    @BindView(R.id.money_get)
    TextView moneyGet;
    @BindView(R.id.time_get)
    TextView timeGet;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    String aac002;
    String aac004 = "男";

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_old_prove;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        scrollView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        barTitle.setText("养老金收入证明");
        aac002 = LoginUtils.getInstance().getUserInfo().getDATA().getIDCARD();
//        aac002 = "360681196004162622";
        getInfo();
//        getServiceOrItems("360622198111145827");
    }

    public void getInfo() {
        ApiManager.getApi().SocialInfo(RequestBodyUtils.Pensionincome(aac002))
                .compose(RxSchedulers.<SocialInfoBean>io_main())
                .compose(this.<SocialInfoBean>loadingDialog())
                .subscribe(new BaseSubscriber<SocialInfoBean>(false) {
                    @Override
                    public void result(SocialInfoBean baseBean) {
                        if(baseBean.getDATA().getSocialInfoList() != null && baseBean.getDATA().getSocialInfoList().size() > 0) {
                            emptyView.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                            setView(baseBean.getDATA().getSocialInfoList().get(0));
                        }else {
                            emptyView.setVisibility(View.VISIBLE);
                            scrollView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNext(SocialInfoBean socialInfoBean) {
                        super.onNext(socialInfoBean);
                        if(!socialInfoBean.getCode().equals("0")){
                            emptyView.setVisibility(View.VISIBLE);
                            scrollView.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @OnClick({R.id.bar_back,R.id.back,R.id.empty_back})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.bar_back:
                finish();
                break;
            case R.id.back:
                finish();
//                ToastUtils.showMessage(this, getString(R.string.developing));
                break;
            case R.id.empty_back:
                finish();
                break;
        }
    }



    public void setView(SocialInfo socialInfo) {
        name.setText(socialInfo.getAac003());
//        sex.setText(aac004);
        id.setText(socialInfo.getAac147());
        idCode.setText(socialInfo.getAac999());
        company.setText(socialInfo.getAab004());
        companyCode.setText(socialInfo.getAab999());
        moneyGet.setText(socialInfo.getAic263());
        timeGet.setText(socialInfo.getAae002());
    }
}
