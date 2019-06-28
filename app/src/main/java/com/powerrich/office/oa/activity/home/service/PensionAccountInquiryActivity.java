package com.powerrich.office.oa.activity.home.service;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.bean.BasicInfoBean;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.GsonUtil;
import com.powerrich.office.oa.tools.LoginUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/1
 * 版权：
 */

public class PensionAccountInquiryActivity extends BaseActivity {


    @BindView(R.id.tv_1)
    TextView mTv1;
    @BindView(R.id.tv_2)
    TextView mTv2;
    @BindView(R.id.tv_3)
    TextView mTv3;
    @BindView(R.id.tv_4)
    TextView mTv4;
    @BindView(R.id.tv_5)
    TextView mTv5;
    @BindView(R.id.tv_6)
    TextView mTv6;
    @BindView(R.id.back)
    TextView mBack;
    @BindView(R.id.llt_content)
    RelativeLayout mLltContent;
    @BindView(R.id.img)
    ImageView mImg;
    @BindView(R.id.message)
    TextView mMessage;
    @BindView(R.id.empty_back)
    TextView mEmptyBack;
    @BindView(R.id.bottom_text)
    TextView mBottomText;
    @BindView(R.id.empty_view)
    RelativeLayout mEmptyView;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pension_account_inquiry;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTitleBar("养老账户查询", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, null);

        coBasicInfo();

    }
    private void initView( BasicInfoBean.DATABeanX.DATABean dataBean) {
        mTv1.setText(dataBean.getAaa119());
        mTv2.setText(dataBean.getAae240());
        mTv3.setText(dataBean.getAae238());
        mTv4.setText(dataBean.getAae239());
        mTv5.setText(dataBean.getAae206());
        mTv6.setText(dataBean.getAaa027());
    }

    private void coBasicInfo() {

        UserInfo.DATABean data = LoginUtils.getInstance().getUserInfo().getDATA();
        String name = data.getREALNAME();
        String idCard = data.getIDCARD();
        ApiRequest api = OAInterface.coBasicInfo("?",name,idCard);
        invoke.invokeWidthDialog(api, new BaseRequestCallBack() {
            @Override
            public void process(HttpResponse response, int what) {
                ResultItem item = response.getResultItem(ResultItem.class);
                String dataStr = item.getJsonStr();
                BasicInfoBean infoBean = GsonUtil.GsonToBean(dataStr, BasicInfoBean.class);
                if(infoBean != null && infoBean.getDATA() != null && infoBean.getDATA().getDATA() != null){

                    List<BasicInfoBean.DATABeanX.DATABean> data = infoBean.getDATA().getDATA();
                    if(data != null && data.size() > 0){
                        BasicInfoBean.DATABeanX.DATABean dataBean = data.get(0);
                        initView(dataBean);
                    }else{
                        mLltContent.setVisibility(View.GONE);
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                }else{
                    //没有数据显示
                    mLltContent.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNetError(int what) {
                super.onNetError(what);
                mLltContent.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick({R.id.back, R.id.empty_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
            case R.id.empty_back:
                finish();
                break;
        }
    }
}
