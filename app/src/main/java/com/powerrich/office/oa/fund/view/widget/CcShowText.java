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
import com.powerrich.office.oa.fund.view.WidgetType;
import com.powerrich.office.oa.fund.view.entity.Prepayment;

/**
 * 只做显示的View
 * @author AlienChao
 * @date 2019/04/19 10:51
 */
public class CcShowText extends BaseView {





    public CcShowText(Context context,int type) {

        this(context, null,type);

    }

    public CcShowText(Context context, @Nullable AttributeSet attrs,int type) {
        this(context, attrs, 0,type);
    }

    public CcShowText(Context context, @Nullable AttributeSet attrs, int defStyleAttr,int type) {
        super(context, attrs, defStyleAttr);
        showType = type;
        initView();
    }

    private void initView() {
        setType(showType);
        tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);
//        tvContent = (TextView) mRootView.findViewById(R.id.tv_content);
    }


    public void setInit(Prepayment prepayment) {
        tvTitle.setText(prepayment.getTitle());
    }

    /**
     * 根据类型显示
     */
    private void setType(int type){

        switch (type){
            case WidgetType.CcSmallTitleView:
                mRootView = LayoutInflater.from(mContext).inflate(R.layout.early_small_title_item, this);
                break;
        }

    }



}
