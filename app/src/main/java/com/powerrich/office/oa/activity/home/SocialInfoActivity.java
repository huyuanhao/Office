package com.powerrich.office.oa.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.EthnicBean;
import com.powerrich.office.oa.bean.EthnicInfo;
import com.powerrich.office.oa.tools.PickUtils;
import com.yt.simpleframe.http.bean.SocialInfo;
import com.yt.simpleframe.http.bean.SocialInfoBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.requst.RequestBodyUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 养老个人基本信息
 */
public class SocialInfoActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.social_code)
    TextView socialCode;
    @BindView(R.id.id)
    TextView id;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.nation)
    TextView nation;
    @BindView(R.id.date_birth)
    TextView dateBirth;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.date_work)
    TextView dateWork;
    @BindView(R.id.state)
    TextView state;
    @BindView(R.id.number_months)
    TextView numberMonths;
    @BindView(R.id.base)
    TextView base;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;

    String aac002 = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_social_info;
    }

    private void initView() {
        llLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        barTitle.setText("个人基本信息");
        aac002 = LoginUtils.getInstance().getUserInfo().getDATA().getIDCARD();
//        aac002 = "360622198111145827";
        getInfo1();
//        getServiceOrItems("360622198111145827");
    }

    /**
     * 获取部门列表
     */
    private void getServiceOrItems(String aac147) {
        if (null != invoke)
            invoke.invokeWidthDialog(OAInterface.getSocialInfo(aac147), callBack);
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {

            } else {
                DialogUtils.showToast(SocialInfoActivity.this, message);
            }
        }

    };

    public void getInfo1() {
        ApiManager.getApi().SocialInfo(RequestBodyUtils.SocialInfo(aac002))
                .compose(RxSchedulers.<SocialInfoBean>io_main())
                .compose(this.<SocialInfoBean>loadingDialog())
                .subscribe(new BaseSubscriber<SocialInfoBean>(false) {
                    @Override
                    public void result(SocialInfoBean baseBean) {
                        if(baseBean.getDATA().getSocialInfoList() != null && baseBean.getDATA().getSocialInfoList().size() > 0) {
                            emptyView.setVisibility(View.GONE);
                            llLayout.setVisibility(View.VISIBLE);
                            setView1(baseBean.getDATA().getSocialInfoList().get(0));
                        }else {
                            emptyView.setVisibility(View.VISIBLE);
                            llLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNext(SocialInfoBean socialInfoBean) {
                        super.onNext(socialInfoBean);
                        if(!socialInfoBean.getCode().equals("0")){
                            emptyView.setVisibility(View.VISIBLE);
                            llLayout.setVisibility(View.GONE);
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


    public void setView1(SocialInfo socialInfo) {
        getInfo2();
//        getInfo2(socialInfo.getAac002());
//        getInfo2("360621196802167317");
        name.setText(socialInfo.getAac003());
        socialCode.setText(socialInfo.getAac002());
        id.setText(socialInfo.getAac147());
        type.setText(socialInfo.getAae473().equals("1") ? "一般账户":"临时缴费账户" );
        sex.setText(socialInfo.getAac004());
        nation.setText(PickUtils.getNation(this,socialInfo.getAac005()));
        dateBirth.setText(socialInfo.getAac006());
        dateWork.setText(socialInfo.getAac007());
    }

    public void setView2(SocialInfo socialInfo) {
        company.setText(socialInfo.getAab004());
        state.setText(socialInfo.getAac008());
        numberMonths.setText(socialInfo.getAic142());
        base.setText(socialInfo.getAae180());
    }

    @OnClick({R.id.bar_back, R.id.back,R.id.empty_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.empty_back:
                finish();
                break;
        }
    }
}
