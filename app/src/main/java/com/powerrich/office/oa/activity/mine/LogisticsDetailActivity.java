package com.powerrich.office.oa.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.LogisticsInfoBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/11/011
 * 版权：
 */
public class LogisticsDetailActivity extends YTBaseActivity {
    @BindView(R.id.tv_send1)
    TextView mTvSend1;
    @BindView(R.id.tv_receive5)
    TextView mTvReceive5;
    @BindView(R.id.iv_send2)
    ImageView mIvSend2;
    @BindView(R.id.iv_car3)
    ImageView mIvCar3;
    @BindView(R.id.iv_receive4)
    ImageView mIvReceive4;
    @BindView(R.id.civ_middler6)
    CircleImageView mCivMiddler6;
    @BindView(R.id.tv_left7)
    TextView mTvLeft7;
    @BindView(R.id.tv_car8)
    TextView mTvCar8;
    @BindView(R.id.tv_right9)
    TextView mTvRight9;
    @BindView(R.id.tv_ems)
    TextView mTvEms;
    @BindView(R.id.rlt_middle)
    RelativeLayout mRltMiddle;
    @BindView(R.id.llt_logistics)
    LinearLayout mLltLogistics;


    TextView mTvTime;
    ImageView mIvTop;
    ImageView mIvMiddle;
    ImageView mIvBottom;
    LinearLayout mLltMiddle;
    TextView mTvStatus;
    TextView mTvContent;

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("物流详情");
        id = getIntent().getExtras().getString("id");
        getData();
    }


    public void getData() {
        String token = LoginUtils.getInstance().getUserInfo().getAuthtoken();
        ApiManager.getApi().getLogisticsInfo(RequestBodyUtils.getLoginsticsInfo(token, id))
                .compose(this.<LogisticsInfoBean>loadingDialog())
                .compose(RxSchedulers.<LogisticsInfoBean>io_main())
                .subscribe(new BaseSubscriber<LogisticsInfoBean>() {
                    @Override
                    public void result(LogisticsInfoBean baseBean) {
                        initView(baseBean);
                    }
                });
    }

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_logistics2_detail);
        ButterKnife.bind(this, view);
        return view;
    }

    LogisticsInfoBean.ADRDATABean emsData;

    void initView(LogisticsInfoBean baseBean) {
        emsData = baseBean.getADR_DATA();
        if (emsData == null) {
            return;
        }
        String sendStr = emsData.getSEND_NAME();
        String receiveStr = emsData.getCONSIGNEE_CITY();
        String status = emsData.getORDER_STATUS();//0-已下单，1-物流中，2-已签收
        String orderNumber = emsData.getORDER_NUM();
        mTvCar8.setText("运输中");
        mTvLeft7.setText(DateUtils.DateToMdStr(emsData.getEMIT_TIME()));
        mTvRight9.setText(DateUtils.DateToMdStr(emsData.getSIGN_TIME()));
        mTvEms.setText("EMS: " + orderNumber);
        mTvSend1.setText(sendStr);
        mTvReceive5.setText(receiveStr);
        if ("0".equals(status)) {
            mIvSend2.setImageResource(R.drawable.send_things_blue);
            mTvLeft7.setTextColor(getResources().getColor(R.color.login_tv_bt));
            mIvCar3.setImageResource(R.drawable.logistics_car);
            mTvCar8.setTextColor(getResources().getColor(R.color.color_999999));
            mIvReceive4.setImageResource(R.drawable.collect_things_gray);
            mTvRight9.setTextColor(getResources().getColor(R.color.color_999999));
        } else if ("1".equals(status)) {
            mIvSend2.setImageResource(R.drawable.send_things_gray);
            mTvLeft7.setTextColor(getResources().getColor(R.color.color_999999));
            mIvCar3.setImageResource(R.drawable.logistics_car);
            mTvCar8.setTextColor(getResources().getColor(R.color.login_tv_bt));
            mIvReceive4.setImageResource(R.drawable.icon_collect_things);
            mTvRight9.setTextColor(getResources().getColor(R.color.color_999999));
        } else if ("2".equals(status)) {
            mIvSend2.setImageResource(R.drawable.send_things_gray);
            mTvLeft7.setTextColor(getResources().getColor(R.color.color_999999));
            mIvCar3.setImageResource(R.drawable.logistics_car);
            mTvCar8.setTextColor(getResources().getColor(R.color.color_999999));
            mIvReceive4.setImageResource(R.drawable.icon_collect_things_blue);
            mTvRight9.setTextColor(getResources().getColor(R.color.login_tv_bt));
        }

        List<LogisticsInfoBean.EMSDATABean> list = baseBean.getEMS_DATA();
        if (list != null && list.size() > 0) {
            addView(list);
        }
    }

    //动态将数据填充到LineayLayout中
    void addView(List<LogisticsInfoBean.EMSDATABean> list) {
        View mView = inflateContentView(R.layout.item_logistics_info);
        ButterKnife.bind(this, mView);
        mTvTime = (TextView) mView.findViewById(R.id.tv_time);
        mIvTop= (ImageView) mView.findViewById(R.id.iv_top);
        mIvMiddle= (ImageView) mView.findViewById(R.id.iv_middle);
        mIvBottom= (ImageView) mView.findViewById(R.id.iv_bottom);
        mTvStatus = (TextView) mView.findViewById(R.id.tv_status);
        mTvContent = (TextView) mView.findViewById(R.id.tv_content);

        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                mIvTop.setVisibility(View.GONE);
                mIvMiddle.setImageResource(R.drawable.arr_blue);
                mIvBottom.setVisibility(View.VISIBLE);
                mTvTime.setTextColor(getResources().getColor(R.color.login_tv_bt));
                mTvStatus.setTextColor(getResources().getColor(R.color.login_tv_bt));
                mTvContent.setTextColor(getResources().getColor(R.color.login_tv_bt));
            } else {
                mIvTop.setVisibility(View.VISIBLE);
                mIvMiddle.setImageResource(R.drawable.arr_gray);
                mIvBottom.setVisibility(View.VISIBLE);
                mTvTime.setTextColor(getResources().getColor(R.color.color_999999));
                mTvStatus.setTextColor(getResources().getColor(R.color.color_999999));
                mTvContent.setTextColor(getResources().getColor(R.color.color_999999));
            }
            mTvTime.setText(list.get(i).getAcceptTime());
            String code = list.get(i).getCode();
            if ("00".equals(code)) {
                code = "揽件";
            } else if ("10".equals(code)) {
                code = "妥投";
            } else if ("20".equals(code)) {
                code = "未妥投";
            } else if ("30".equals(code)) {
                code = "经转过程中";
            } else if ("40".equals(code)) {
                code = "离开处理中心";
            } else if ("41".equals(code)) {
                code = "到达处理中心";
            } else if ("50".equals(code)) {
                code = "安排投递";
            } else if ("51".equals(code)) {
                code = "正在投递";
            }
            mTvStatus.setText(code);
            mTvContent.setText(list.get(i).getRemark());
            mLltLogistics.addView(mView);
        }
    }
}
