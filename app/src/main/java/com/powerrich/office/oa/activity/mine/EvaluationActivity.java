package com.powerrich.office.oa.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.enums.EvaluationType;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.bean.EvaluationBean;
import com.yt.simpleframe.http.bean.entity.EvaluationInfo;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/7/10/006
 * 版权：
 */
public class EvaluationActivity extends YTBaseActivity {


    @BindView(R.id.tv_star1)
    TextView mTvStar1;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_star2)
    TextView mTvStar2;
    @BindView(R.id.tv_star3)
    TextView mTvStar3;
    @BindView(R.id.tv_star4)
    TextView mTvStar4;
    @BindView(R.id.tv_star5)
    TextView mTvStar5;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.cb_evaluation)
    CheckBox mCbEvaluation;
    @BindView(R.id.et_comment)
    EditText mEtComment;
    @BindView(R.id.lt_bottom)
    LinearLayout mLtBottom;


    String starStr = "基本满意";
    private EvaluationType mEvaluationType;

    private String type = "3";//默认评价三颗星
    private String id = "";

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_evaluation);
        ButterKnife.bind(this, view);
        setNoneTitle();
        id = getIntent().getExtras().getString("id");
        mEvaluationType = (EvaluationType) getIntent().getExtras().get("type");
        mTvType.setText(starStr);
        selectBt(3);
        initView();
        return view;
    }


    public void initView() {
        if (mEvaluationType == EvaluationType.查看评价) {
            getdata();
            mLtBottom.setVisibility(View.GONE);
            mTvStar1.setEnabled(false);
            mTvStar2.setEnabled(false);
            mTvStar3.setEnabled(false);
            mTvStar4.setEnabled(false);
            mTvStar5.setEnabled(false);
            mEtComment.setEnabled(false);
            mTvTitle.setText("查看评价");

        }
    }

    private void getdata() {
        ApiManager.getApi().getEvaluateInfo(RequestBodyUtils.getEvaluateInfo(
                LoginUtils.getInstance().getUserInfo().getAuthtoken(), id))
                .compose(RxSchedulers.<EvaluationBean>io_main())
                .subscribe(new BaseSubscriber<EvaluationBean>() {
                    @Override
                    public void result(EvaluationBean bean) {
                        EvaluationInfo data = bean.getDATA();
                        if (data != null) {
                            if (StringUtil.isEmpty(data.getEVALUATE_CONTENT())) {
                                mEtComment.setText("当前暂无评价内容");
                            }else {
                                mEtComment.setText(data.getEVALUATE_CONTENT());
                            }
                            selectBt(Integer.valueOf(data.getSATISFACTION_DEGREE()));
                            mTvType.setText(starStr);

                        }
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.tv_star1, R.id.tv_star2, R.id.tv_star3, R.id.tv_star4, R.id.tv_star5, R.id.tv_commit, R.id.tv_cancel, R.id
            .cb_evaluation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_star1:
                selectBt(1);

                break;
            case R.id.tv_star2:
                selectBt(2);

                break;
            case R.id.tv_star3:
                selectBt(3);

                break;
            case R.id.tv_star4:
                selectBt(4);

                break;
            case R.id.tv_star5:
                selectBt(5);

                break;
            case R.id.tv_commit:
                String comment = mEtComment.getText().toString();
                evaluation(id, type, comment);
                break;
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.cb_evaluation:
                break;
        }
        mTvType.setText(starStr);
    }

    public void selectBt(int key) {
        type = key + "";
        switch (key) {
            case 1:
                mTvStar1.setBackgroundResource(R.drawable.star_orange);
                mTvStar2.setBackgroundResource(R.drawable.star_gray);
                mTvStar3.setBackgroundResource(R.drawable.star_gray);
                mTvStar4.setBackgroundResource(R.drawable.star_gray);
                mTvStar5.setBackgroundResource(R.drawable.star_gray);
                starStr = "很不满意";
                break;
            case 2:
                mTvStar1.setBackgroundResource(R.drawable.star_orange);
                mTvStar2.setBackgroundResource(R.drawable.star_orange);
                mTvStar3.setBackgroundResource(R.drawable.star_gray);
                mTvStar4.setBackgroundResource(R.drawable.star_gray);
                mTvStar5.setBackgroundResource(R.drawable.star_gray);
                starStr = "不满意";
                break;
            case 3:
                mTvStar1.setBackgroundResource(R.drawable.star_orange);
                mTvStar2.setBackgroundResource(R.drawable.star_orange);
                mTvStar3.setBackgroundResource(R.drawable.star_orange);
                mTvStar4.setBackgroundResource(R.drawable.star_gray);
                mTvStar5.setBackgroundResource(R.drawable.star_gray);
                starStr = "基本满意";
                break;
            case 4:
                mTvStar1.setBackgroundResource(R.drawable.star_orange);
                mTvStar2.setBackgroundResource(R.drawable.star_orange);
                mTvStar3.setBackgroundResource(R.drawable.star_orange);
                mTvStar4.setBackgroundResource(R.drawable.star_orange);
                mTvStar5.setBackgroundResource(R.drawable.star_gray);
                starStr = "很不错";
                break;
            case 5:
                mTvStar1.setBackgroundResource(R.drawable.star_orange);
                mTvStar2.setBackgroundResource(R.drawable.star_orange);
                mTvStar3.setBackgroundResource(R.drawable.star_orange);
                mTvStar4.setBackgroundResource(R.drawable.star_orange);
                mTvStar5.setBackgroundResource(R.drawable.star_orange);
                starStr = "非常满意，无可挑剔";
                break;
        }

    }


    private void evaluation(String prokeyid, String satisfaction, String remark) {
        ApiManager.getApi().exeNormal(RequestBodyUtils.evaluate(prokeyid, satisfaction, remark))
                .compose(RxSchedulers.<BaseBean>io_main())
                .subscribe(new BaseSubscriber<BaseBean>() {
                    @Override
                    public void result(BaseBean baseBean) {
                        DialogUtils.showToast(EvaluationActivity.this, "评价成功！");
                        finish();
                    }
                });
    }
}
