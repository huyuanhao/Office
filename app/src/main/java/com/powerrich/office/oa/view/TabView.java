package com.powerrich.office.oa.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.tools.AutoUtils;


/**
 * @author Administrator
 */
public class TabView extends LinearLayout implements OnClickListener {

    private OnTabChangeListener mOnTabChangedListener;

    private int mState = -1;


    private ImageView mStatusImageView1;
    private ImageView mStatusImageView2;
    private ImageView mStatusImageView3;
    private ImageView mStatusImageView4;

    private TextView mStatusTextView1;
    private TextView mStatusTextView2;
    private TextView mStatusTextView3;
    private TextView mStatusTextView4;

    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    @SuppressLint("NewApi")
    public TabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.view_tab, this);
        findViewById(R.id.ll_state1).setOnClickListener(this);
        findViewById(R.id.ll_state2).setOnClickListener(this);
        findViewById(R.id.ll_state3).setOnClickListener(this);
        findViewById(R.id.ll_state4).setOnClickListener(this);

        mStatusImageView1 = ((ImageView) findViewById(R.id.iv_state1));
        mStatusImageView2 = ((ImageView) findViewById(R.id.iv_state2));
        mStatusImageView3 = ((ImageView) findViewById(R.id.iv_state3));
        mStatusImageView4 = ((ImageView) findViewById(R.id.iv_state4));

        mStatusTextView1 = ((TextView) findViewById(R.id.tv_state1));
        mStatusTextView2 = ((TextView) findViewById(R.id.tv_state2));
        mStatusTextView3 = ((TextView) findViewById(R.id.tv_state3));
        mStatusTextView4 = ((TextView) findViewById(R.id.tv_state4));

    }

    public void setOnTabChangeListener(OnTabChangeListener listener) {
        mOnTabChangedListener = listener;
    }

    public void setCurrentTab(int index) {
        switchState(index);
    }

    private void switchState(int state) {
        if (mState == state) {
            return;
        } // else continue

        mState = state;
        mStatusImageView1.setSelected(false);
        mStatusTextView1.setSelected(false);

        mStatusImageView2.setSelected(false);
        mStatusTextView2.setSelected(false);

        mStatusImageView3.setSelected(false);
        mStatusTextView3.setSelected(false);

        mStatusImageView4.setSelected(false);
        mStatusTextView4.setSelected(false);

        Object tag = null;

        switch (mState) {
            case 0:
                mStatusImageView2.setSelected(true);
                mStatusTextView2.setSelected(true);
                tag = mStatusTextView2.getTag();
                break;

            case 1:
                mStatusImageView1.setSelected(true);
                mStatusTextView1.setSelected(true);
                tag = mStatusTextView1.getTag();
                break;

            case 2:
                mStatusImageView3.setSelected(true);
                mStatusTextView3.setSelected(true);
                tag = mStatusTextView3.getTag();
                break;

            case 3:
                mStatusImageView4.setSelected(true);
                mStatusTextView4.setSelected(true);
                tag = mStatusTextView4.getTag();
                break;

            default:
                break;
        }

        if (mOnTabChangedListener != null) {
            if (tag != null && mOnTabChangedListener != null) {
                mOnTabChangedListener.onTabChange(tag.toString());
            } else {
                mOnTabChangedListener.onTabChange(null);
            }
        } // else ignored
    }


    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_state1:
                switchState(1);
                break;

            case R.id.ll_state2:
                switchState(0);
                break;

            case R.id.ll_state3:
                switchState(2);
                break;

            case R.id.ll_state4:
                switchState(3);
                break;
            default:
                break;
        }
    }

    public interface OnTabChangeListener {
        void onTabChange(String tag);
    }
}
