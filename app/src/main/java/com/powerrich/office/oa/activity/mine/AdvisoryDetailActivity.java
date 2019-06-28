package com.powerrich.office.oa.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.tools.LoginUtils;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.AdvisoryInfoBean;
import com.yt.simpleframe.http.bean.entity.AdvisoryInfo;
import com.yt.simpleframe.http.requst.RequestBodyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/6/006
 * 版权：
 */
public class AdvisoryDetailActivity extends YTBaseActivity {


    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_ask)
    TextView mTvAsk;
    @BindView(R.id.tv_ask_name)
    TextView mTvAskName;
    @BindView(R.id.tv_ask_time)
    TextView mTvAskTime;
    @BindView(R.id.tv_answer)
    TextView mTvAnswer;
    @BindView(R.id.tv_answer_name)
    TextView mTvAnswerName;
    @BindView(R.id.tv_answer_time)
    TextView mTvAnswerTime;


    private String id;
    private String type;

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_advisory_detail);
        ButterKnife.bind(this, view);
        id = getIntent().getExtras().getString("id");
        type = getIntent().getExtras().getString("type");
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showBack();
        getExchangeInfo();
//        咨询(1),投诉(2),建议(3);
        if("1".equals(type)){
            setTitle("咨询详情");
        }else if("2".equals(type)){
            setTitle("投诉详情");
        }else if("3".equals(type)){
            setTitle("建议详情");
        }


    }


    public void getExchangeInfo(){
        String token = LoginUtils.getInstance().getUserInfo().getAuthtoken();
        ApiManager.getApi().getExchangeInfo(RequestBodyUtils.getExchangeInfo(token,type,id))
                .compose(RxSchedulers.<AdvisoryInfoBean>io_main())
                .compose(this.<AdvisoryInfoBean>loadingDialog())
                .subscribe(new BaseSubscriber<AdvisoryInfoBean>() {
                    @Override
                    public void result(AdvisoryInfoBean baseBean) {
                        setView(baseBean.getDATA());
                    }
                });
    }

    public void setView(AdvisoryInfo info){
        mTvTitle.setText("主题 ：" + info.getTITLE());
        mTvAsk.setText(info.getQUESTION());
        mTvAskName.setText(info.getMYNAME());
        mTvAskTime.setText(info.getCRETTIME());
        mTvAnswer.setText(info.getREVERTCONTENT());
        mTvAnswerName.setText(info.getREVERTER());
        mTvAnswerTime.setText(info.getRESERTTIME());
    }
}
