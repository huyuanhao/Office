package com.powerrich.office.oa.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;

/**
 * @author AlienChao
 * @date 2018/12/19 17:13
 */
public class LoanView extends LinearLayout {
    private Context mContext;
    private View rootView;
    private TextView tvBljdName;
    private TextView tvBljdValue;
    private TextView tvBlsjName;
    private TextView tvBlsjValue;
    private TextView tvBlresultName;
    private TextView tvBlresultValue;
    private TextView tv_cllr;
    private TextView tv_cllr_time;

    /**
     * 三种样式 0 有内容白底  1 有内容蓝底  2 无内容白底 3 完结  4 申请受理
     */
    private int state = -1;
    private LayoutParams mDotParams;
    private int mDotColor;
    private TextView mTvSlsqName;

    public void setState(int state) {
        this.state = state;
        initView();
    }


    public void setTv_cllr(String  name) {
        this.tv_cllr .setText(name);
    }

    public void setTv_cllr_time(String  name) {
        this.tv_cllr_time.setText(name);
    }

    public void setTvBlsjValue(String  name) {
        this.tvBlsjValue.setText(name);
    }

    public void setTvBljdValue(String  name) {
        this.tvBljdValue.setText(name);
    }



    public void setTvBlresultValue(String  name) {
        this.tvBlresultValue.setText(name);
    }

    public LoanView(Context context) {
        this(context, null);
    }

    public LoanView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("jsc", "LoanView-LoanView:state:"+state);
        mContext = context;

    }


    public void setTvSlsjName(String name){
        mTvSlsqName.setText(name);
    }

    private  void initView() {

        Log.e("jsc", "LoanView-initView-state:"+state);
        if(state==-1){
            return;
        }

        //是否是完结
        if(state==3){

            rootView = LayoutInflater.from(mContext).inflate(R.layout.loan_end, null);

            this.addView(rootView);
            return;
        }
        //申请受理  tv_slsj_name
        if(state==4){
            rootView = LayoutInflater.from(mContext).inflate(R.layout.loan_sqsl, null);
            mTvSlsqName = rootView.findViewById(R.id.tv_slsq_name);
            this.addView(rootView);
            return;
        }

        rootView = LayoutInflater.from(mContext).inflate(R.layout.loan_item, null);
        initView(rootView);
        //圆柱体
        ImageView ivColumar = rootView.findViewById(R.id.iv_columnar);
        //圆点
        View view_dot = rootView.findViewById(R.id.view_dot);
        //背景
        LinearLayout ll_content = rootView.findViewById(R.id.ll_content);




        if(state==0){
            tv_cllr_time.setVisibility(View.VISIBLE);
            tv_cllr.setVisibility(View.GONE);

            mDotParams = new LayoutParams(36, 36);
            mDotColor = R.drawable.shapcycle;
            ivColumar.setBackgroundResource(R.drawable.loan_cyclinder);

            ll_content.setBackgroundResource(R.drawable.loban_bg_white);
            textViewState(true);



        }else if(state==1){
            tv_cllr_time.setVisibility(View.VISIBLE);
            tv_cllr.setVisibility(View.VISIBLE);
            tv_cllr.setTextColor(mContext.getResources().getColor(R.color.loan_text_dark));

            mDotParams = new LayoutParams(58, 58);
            mDotColor = R.drawable.shapcycle2;
            ivColumar.setBackgroundResource(R.drawable.loan_cyclinder);

            ll_content.setBackgroundResource(R.drawable.loban_bg);
            textViewState(false);

        }else if(state==2){
            tv_cllr_time.setVisibility(View.GONE);
            tv_cllr.setVisibility(View.VISIBLE);
            tv_cllr.setTextColor(mContext.getResources().getColor(R.color.loan_text_cllr_gray));

            mDotParams = new LayoutParams(30, 30);
            mDotColor = R.drawable.shapcycle3;
            ivColumar.setBackgroundResource(R.drawable.circleline);

            ll_content.setVisibility(View.INVISIBLE);

        }

        view_dot.setLayoutParams(mDotParams);
        view_dot.setBackgroundResource(mDotColor);

        this.addView(rootView);
    }


    private void initView(View view) {
        tvBljdName = view.findViewById(R.id.tv_bljd_name);
        tvBljdValue = view.findViewById(R.id.tv_bljd_value);
        tvBlsjName = view.findViewById(R.id.tv_blsj_name);
        tvBlsjValue = view.findViewById(R.id.tv_blsj_value);
        tvBlresultName = view.findViewById(R.id.tv_blresult_name);
        tvBlresultValue = view.findViewById(R.id.tv_blresult_value);
        tv_cllr = view.findViewById(R.id.tv_cllr);
        tv_cllr_time = view.findViewById(R.id.tv_cllr_time);
    }

    private void textViewState(boolean dark) {
        if (dark) {
            tvBljdName.setTextColor(mContext.getResources().getColor(R.color.loan_text_dark));
            tvBljdValue.setTextColor(mContext.getResources().getColor(R.color.loan_text_dark));
            tvBlsjName.setTextColor(mContext.getResources().getColor(R.color.loan_text_dark));
            tvBlsjValue.setTextColor(mContext.getResources().getColor(R.color.loan_text_dark));
            tvBlresultName.setTextColor(mContext.getResources().getColor(R.color.loan_text_dark));
            tvBlresultValue.setTextColor(mContext.getResources().getColor(R.color.loan_text_dark));
        } else {
            tvBljdName.setTextColor(mContext.getResources().getColor(R.color.white));
            tvBljdValue.setTextColor(mContext.getResources().getColor(R.color.white));
            tvBlsjName.setTextColor(mContext.getResources().getColor(R.color.white));
            tvBlsjValue.setTextColor(mContext.getResources().getColor(R.color.white));
            tvBlresultName.setTextColor(mContext.getResources().getColor(R.color.white));
            tvBlresultValue.setTextColor(mContext.getResources().getColor(R.color.white));
        }

    }


}
