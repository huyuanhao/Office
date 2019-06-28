package com.powerrich.office.oa.fund.view.CInterface;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author AlienChao
 * @date 2019/04/19 14:40
 */
public class BaseView extends LinearLayout implements ValueUI {
    protected Context mContext;
    // tvTitle、 tvContent 是一定要实例的控件
    protected TextView tvTitle;
    protected TextView tvContent;
    protected View mRootView = null;
    //显示控件的类型
    protected int showType;

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext= context;


    }

    @Override
    public void getText() {

    }

    @Override
    public void setTitle() {

    }

    @Override
    public void setText() {

    }

    @Override
    public void isNoClick() {

    }
}
