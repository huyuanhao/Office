package com.powerrich.office.oa.view;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.enums.EvaluationType;
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


public class EvaluationDialog extends Dialog {

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
    private String id;

    private Context context;

    private ICallback callback;

    public interface ICallback {
        void success();
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
                dismiss();
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
                        dismiss();
                        callback.success();
                    }
                });
    }

    public EvaluationDialog(Context context,String id,EvaluationType type, ICallback callback) {
        super(context, R.style.dialog);
        this.context = context;
        this.id = id;
        this.mEvaluationType = type;
        this.callback = callback;
        init();
        mTvType.setText(starStr);
        selectBt(3);
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_evaluation, null);
        ButterKnife.bind(this,view);
        setContentView(view);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


}
