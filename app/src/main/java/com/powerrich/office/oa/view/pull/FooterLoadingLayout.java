package com.powerrich.office.oa.view.pull;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.powerrich.office.oa.R;


/**
 * 类描述：<br>
 * 这个类封装了下拉刷新的布局
 * @author  Fitz
 * @date    2014-12-3
 */
public class FooterLoadingLayout extends LoadingLayout {
	
    /**进度条*/
    private ProgressBar mProgressBar;
    /** 显示的文本 */
    private TextView mHintView;
    
    
    /**
     * 构造方法
     * 
     * @param context context
     */
    public FooterLoadingLayout(Context context) {
        super(context);
        init(context);
    }

    /**
     * 构造方法
     * 
     * @param context context
     * @param attrs attrs
     */
    public FooterLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化
     * 
     * @param context context
     */
    private void init(Context context) {
        mProgressBar = (ProgressBar) findViewById(R.id.pull_to_load_footer_progressbar);
        mHintView = (TextView) findViewById(R.id.pull_to_load_footer_hint_textview);
        
        setState(State.RESET);
    }
    
    @Override
    protected View createLoadingView(Context context, AttributeSet attrs) {
        return LayoutInflater.from(context).inflate(R.layout.pull_to_load_footer, null);
    }

    public void setLastUpdatedLabel(CharSequence label) {
    	
    }

    @Override
    public int getContentSize() {
        View view = findViewById(R.id.pull_to_load_footer_content);
        if (null != view) {
            return view.getHeight();
        }
        
        return (int) (getResources().getDisplayMetrics().density * 40);
    }
    
    @Override
    protected void onStateChanged(State curState, State oldState) {
        mProgressBar.setVisibility(View.GONE);
        mHintView.setVisibility(View.INVISIBLE);
        
        super.onStateChanged(curState, oldState);
    }
    
    public void onText(String text) {
        mHintView.setText(Html.fromHtml(text));
    }
    
    @Override
    protected void onReset() {
        mHintView.setText(R.string.pull_to_refresh_header_hint_loading);
    }

    @Override
    protected void onPullToRefresh() {
        mHintView.setVisibility(View.VISIBLE);
        mHintView.setText(R.string.pull_to_refresh_header_hint_normal2);
    }

    @Override
    protected void onReleaseToRefresh() {
        mHintView.setVisibility(View.VISIBLE);
        mHintView.setText(R.string.pull_to_refresh_header_hint_ready);
    }

    @Override
    protected void onRefreshing() {
        mProgressBar.setVisibility(View.VISIBLE);
        mHintView.setVisibility(View.VISIBLE);
        mHintView.setText(R.string.pull_to_refresh_header_hint_loading);
    }
    
    @Override
    protected void onNoMoreData() {
        mHintView.setVisibility(View.VISIBLE);
        mHintView.setText(R.string.pull_no_more_msg);
    }

	public ProgressBar getmProgressBar() {
		return mProgressBar;
	}

	public void setmProgressBar(ProgressBar mProgressBar) {
		this.mProgressBar = mProgressBar;
	}

	public TextView getmHintView() {
		return mHintView;
	}

	public void setmHintView(TextView mHintView) {
		this.mHintView = mHintView;
	}
    
}
