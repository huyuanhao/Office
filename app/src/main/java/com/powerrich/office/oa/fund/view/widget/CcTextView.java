package com.powerrich.office.oa.fund.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.fund.view.CInterface.BaseView;
import com.powerrich.office.oa.fund.view.CInterface.ValueUI;
import com.powerrich.office.oa.fund.view.entity.Prepayment;

/**
 * @author AlienChao
 * @date 2019/04/19 10:51
 */
public class CcTextView extends BaseView {





    private ImageView ivRightArrow;

    public CcTextView(Context context) {
        this(context, null);
    }

    public CcTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CcTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.early_textview_item, this);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvContent = (TextView) findViewById(R.id.tv_content);

        ivRightArrow = (ImageView) findViewById(R.id.iv_right_arrow);
    }


    public void setInit(Prepayment prepayment) {
        tvTitle.setText(prepayment.getTitle());
        tvContent.setText(prepayment.getValue());
        if(prepayment.isClick()){
            ivRightArrow.setVisibility(View.VISIBLE);
        }else {
            ivRightArrow.setVisibility(View.GONE);
        }
    }




}
