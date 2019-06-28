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
import com.powerrich.office.oa.bean.BasicInfoGrcbxxBean;
import com.powerrich.office.oa.bean.BasicInfoJbxxBean;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.GsonUtil;
import com.powerrich.office.oa.tools.LoginUtils;
import com.yt.simpleframe.utils.StringUtil;

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

public class PersonalInformatioActivity extends BaseActivity {


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
    @BindView(R.id.tv_7)
    TextView mTv7;
    @BindView(R.id.tv_8)
    TextView mTv8;
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
        return R.layout.activity_personal_information;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTitleBar("个人基本信息", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, null);

        //开始请求数据
        coBasicInfoJbxx();
    }

    private void initView(BasicInfoJbxxBean.DATABeanX.DATABean dataBean) {
        mTv1.setText(dataBean.getAac003());
        mTv2.setText(dataBean.getAac004());
        //身份证中间10位 *
        mTv3.setText(StringUtil.replaceIdCard(dataBean.getAac147()));

        mTv4.setText(dataBean.getAac010());
    }

    private void initView2(BasicInfoGrcbxxBean.DATABeanX.DATABean dataBean) {
        mTv5.setText(dataBean.getAac049());
        mTv6.setText(dataBean.getAac008());
        mTv7.setText(dataBean.getAac031());
        mTv8.setText(dataBean.getAaa027());
    }


    //查询个人参保基本信息
    private void coBasicInfoJbxx() {
        UserInfo.DATABean data = LoginUtils.getInstance().getUserInfo().getDATA();
        String name = data.getREALNAME();
        String idCard = data.getIDCARD();
        ApiRequest api = OAInterface.coBasicInfoJbxx("?", name, idCard);
        invoke.invokeWidthDialog(api, new BaseRequestCallBack() {
            @Override
            public void process(HttpResponse response, int what) {
                ResultItem item = response.getResultItem(ResultItem.class);
                String dataStr = item.getJsonStr();
                BasicInfoJbxxBean jbBean = GsonUtil.GsonToBean(dataStr, BasicInfoJbxxBean.class);
                if (jbBean != null && jbBean.getDATA() != null && jbBean.getDATA().getDATA() != null) {
                    List<BasicInfoJbxxBean.DATABeanX.DATABean> data1 = jbBean.getDATA().getDATA();
                    if (data1 != null && data1.size() > 0) {
                        coBasicInfoGrcbxx();
                        BasicInfoJbxxBean.DATABeanX.DATABean dataBean = data1.get(0);
                        initView(dataBean);
                    } else {
                        mLltContent.setVisibility(View.GONE);
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                } else {
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


    //城乡居民养老个人参保信息查询
    private void coBasicInfoGrcbxx() {
        UserInfo.DATABean data = LoginUtils.getInstance().getUserInfo().getDATA();
        String name = data.getREALNAME();
        String idCard = data.getIDCARD();
        ApiRequest api = OAInterface.coBasicInfoGrcbxx("?", name, idCard);
        invoke.invokeWidthDialog(api, new BaseRequestCallBack() {
            @Override
            public void process(HttpResponse response, int what) {
                ResultItem item = response.getResultItem(ResultItem.class);
                String dataStr = item.getJsonStr();
                BasicInfoGrcbxxBean cbBean = GsonUtil.GsonToBean(dataStr, BasicInfoGrcbxxBean.class);
                if (cbBean != null && cbBean.getDATA() != null && cbBean.getDATA().getDATA() != null) {
                    List<BasicInfoGrcbxxBean.DATABeanX.DATABean> data1 = cbBean.getDATA().getDATA();
                    if (data1 != null) {
                        BasicInfoGrcbxxBean.DATABeanX.DATABean dataBean = data1.get(0);
                        initView2(dataBean);
                    }
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
