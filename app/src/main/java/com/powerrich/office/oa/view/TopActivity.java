package com.powerrich.office.oa.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.tools.BeanUtils;


@SuppressLint("NewApi")
public class TopActivity extends LinearLayout {

	private Context context;
	private boolean isBindViews;
	private TextView tvTitle;// 中间的标题文字
	private Button btnBack;// 左边的按钮图标
	private Button btnRight;// 右边的按钮图标

	public TopActivity(Context context) {
		super(context);
		this.context = context;
	}

	public TopActivity(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (!this.isBindViews) {
			bindViews();
		}
	}

	public void setBtnBackOnClickListener(OnClickListener listener) {
		if (listener != null) {
			this.btnBack.setOnClickListener(listener);
		}
	}

	public void setBtnBackVisibility(int visibility) {
		this.btnBack.setVisibility(visibility);
	}

	public void setBtnBackText(String text) {
		this.btnBack.setText(text);
	}

	public void setRightBtnOnClickListener(OnClickListener listener) {
		if (listener != null) {
			this.btnRight.setOnClickListener(listener);
		}
	}

	public void setRightBtnVisibility(int visibility) {
		this.btnRight.setVisibility(visibility);
	}

/*	public void setRightBtnStyle(String btnText, int backBg, boolean backBGisNull) {
		this.btnRight.setText(btnText);
		if (backBGisNull) {
			this.btnRight.setBackground(null);
		} else {
			this.btnRight.setPadding(10, 0, 10, 0);
			this.btnRight.setBackgroundResource(backBg);
		}
	}
*/	
	/**
	 * 方法说明：<br>
	 * 如果按钮文本为空，则设置图片点击背景<br>
	 * 两者二选一
	 * @param btnText
	 * @param backBg
	 */
	public void setRightBtnStyle(String btnText) {
		if(!BeanUtils.isEmpty(btnText)){
			this.btnRight.setText(btnText);
		}
	}
	
	public void setRightBtnStyle(int bgResource){
		setBtnStyle(this.context, this.btnRight, bgResource);
		/*this.btnRight.setText("");
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(10, 10, 10, 10); 
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		this.btnRight.setLayoutParams(lp);
		this.btnRight.setPadding(0, 0, 0, 0);
		this.btnRight.setBackgroundResource(bgResource);*/
	}

	/**
	 * 设置右上角按钮
	 * 
	 * @param btnRight
	 * @param bgResource
	 */
	public void setBtnStyle(Context context, Button btnRight, int bgResource) {
		btnRight.setText("");
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		btnRight.setLayoutParams(lp);
		btnRight.setPadding(20, 19, 20, 19);
		
		Drawable drawable = context.getResources().getDrawable(bgResource);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		btnRight.setCompoundDrawables(drawable, null, null, null);
	}
	public void setTopTitle(String title) {
		this.tvTitle.setText(title);
	}

	private void bindViews() {
		this.isBindViews = true;

		this.btnBack = (Button) findViewById(R.id.system_back);
		this.btnRight = (Button) findViewById(R.id.btn_top_right);
		this.tvTitle = (TextView) findViewById(R.id.tv_top_title);
	}

	public TextView getTvTitle() {
		return tvTitle;
	}

	public void setTvTitle(TextView tvTitle) {
		this.tvTitle = tvTitle;
	}

	public Button getBtnBack() {
		return btnBack;
	}

	public void setBtnBack(Button btnBack) {
		this.btnBack = btnBack;
	}

	public Button getBtnRight() {
		return btnRight;
	}

	public void setBtnRight(Button btnRight) {
		this.btnRight = btnRight;
	}

}
