package com.powerrich.office.oa.activity.home.service;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.bean.BasicInfoJdcxBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.GsonUtil;
import com.powerrich.office.oa.tools.LoginUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/1
 * 版权：
 */

public class SocialProcessActivity extends BaseActivity {


    String idCard = "";
    @BindView(R.id.tv1)
    TextView mTv1;
    @BindView(R.id.tv2)
    TextView mTv2;
    @BindView(R.id.tv3)
    TextView mTv3;
    @BindView(R.id.tv4)
    TextView mTv4;
    @BindView(R.id.tv5)
    TextView mTv5;
    @BindView(R.id.back)
    TextView mBack;
    @BindView(R.id.llt_content)
    LinearLayout mLltContent;
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
        return R.layout.activity_social_process;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTitleBar("个人卡进度查询", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, null);
        idCard = LoginUtils.getInstance().getUserInfo().getDATA().getIDCARD();
        ciBasicInfoJdcx();
    }


    private void ciBasicInfoJdcx() {
        ApiRequest request = OAInterface.ciBasicInfoJdcx(idCard);
//        ApiRequest request = OAInterface.ciBasicInfoJdcx("340123198209242591");
        invoke.invokeWidthDialog(request, new BaseRequestCallBack() {
            @Override
            public void process(HttpResponse response, int what) {
                ResultItem item = response.getResultItem(ResultItem.class);

                String dataStr = item.getJsonStr();
                BasicInfoJdcxBean jdcxBean = GsonUtil.GsonToBean(dataStr, BasicInfoJdcxBean.class);

                if(jdcxBean != null && jdcxBean.getDATA() != null && jdcxBean.getDATA().getDATA() != null && jdcxBean.getDATA().getDATA().size() != 0){
                    BasicInfoJdcxBean.DATABeanX.DATABean data = jdcxBean.getDATA().getDATA().get(0);
                    mTv1.setText(data.getName());
                    mTv2.setText(data.getIdNumber());
                    mTv3.setText(data.getBankName());
                    mTv4.setText(data.getApplyDate());
                    mTv5.setText(data.getCardBatchMessage());
                }else{
                    mLltContent.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                }


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
