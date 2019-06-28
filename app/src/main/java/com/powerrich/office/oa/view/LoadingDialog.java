package com.powerrich.office.oa.view;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.powerrich.office.oa.R;


/**
 * 类描述：<br> 
 * 加载进度对话框 
 * @author  Fitz
 * @date    2015年5月18日
 * @version v1.0
 */
public class LoadingDialog extends Dialog{

	public Context context;
	private TextView tvMeesage;
	
	private static LoadingDialog customProgressDialog = null;
	
	public LoadingDialog(Context context) {
		super(context, R.style.TipsDialog);
		this.context = context;
		setContentView(R.layout.dialog_loading);
		bindView();
	}

	private void bindView() {
		this.tvMeesage = (TextView)findViewById(R.id.tv_message);
	}
	
	public LoadingDialog setMessage(String message) {
		this.tvMeesage.setText(message);
		return customProgressDialog;
	}

}

