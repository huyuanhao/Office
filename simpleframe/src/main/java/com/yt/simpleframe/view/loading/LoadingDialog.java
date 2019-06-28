package com.yt.simpleframe.view.loading;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.yt.simpleframe.R;


public class LoadingDialog extends Dialog {
	private Context mContext;
	private LayoutInflater inflater;
	private LayoutParams lp;
	private TextView loadingText;

	public static LoadingDialog mDialog;

	public static LoadingDialog getInstance(Context context){
		if(mDialog == null){
			mDialog = new LoadingDialog(context,"正在加载中...");
		}
		return mDialog;
	}


	public LoadingDialog(Context context, String content) {
		super(context, R.style.dialog);

		this.mContext = context;
		// 设置点击对话框之外能消失
		setCanceledOnTouchOutside(false);
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.view_load_progress, null);
		loadingText = (TextView) layout.findViewById(R.id.progress_msg);
		if (null != content) {
			loadingText.setText(content);
			loadingText.setVisibility(View.GONE);
			}else{
			loadingText.setVisibility(View.GONE);
		}
		setContentView(layout);

		// 设置window属性
		lp = getWindow().getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.dimAmount = 0; // 去背景遮盖
		lp.alpha = 1.0f;
		getWindow().setAttributes(lp);
	}
	
	public void setMessage(String message){
		this.loadingText.setText(message);
	}

	public LoadingDialog(Context context, String content, boolean iancleable) {
		super(context, R.style.dialog);

		this.mContext = context;
		// 设置点击对话框之外能消失
		setCanceledOnTouchOutside(iancleable);

		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.view_load_progress, null);
		loadingText = (TextView) layout.findViewById(R.id.progress_msg);
		if (null != content) {
			loadingText.setText(content);
		}
		setContentView(layout);

		// 设置window属性
		lp = getWindow().getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.dimAmount = 0; // 去背景遮盖
		lp.alpha = 1.0f;
		getWindow().setAttributes(lp);
	}
}
