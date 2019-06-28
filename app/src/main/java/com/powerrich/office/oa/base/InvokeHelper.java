package com.powerrich.office.oa.base;

import android.content.Context;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.view.LoadingDialog;


/**
 * FileName    : InvokeHelper.java
 * Description : 
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2011-12-14 下午21:50:38
 **/
public class InvokeHelper {
	
	private Context context;
	
	public InvokeHelper(Context context){
		this.context = context;
	}
	
	public void invokeWidthDialog(ApiRequest request,IRequestCallBack callBack){
//		ProgressDialog dialog = buildDialog(request.getRequestMessage());
		LoadingDialog dialog = buildDialog(request.getRequestMessage());
		invoke(request, callBack, dialog, 0);		
	}
	
	public void invokeWidthDialog(ApiRequest request,IRequestCallBack callBack,int what){
//		ProgressDialog dialog = buildDialog(request.getRequestMessage());
		LoadingDialog dialog = buildDialog(request.getRequestMessage());
		invoke(request, callBack, dialog, what);		
	}
	
	public void invoke(ApiRequest request,IRequestCallBack callBack){
		invoke(request, callBack, null, 0);
	}
	
	public void invoke(ApiRequest request,IRequestCallBack callBack,int what){
		invoke(request, callBack, null, what);
	}
	
	public void invoke(ApiRequest request,IRequestCallBack callBack,LoadingDialog dialog,int what){
		if(dialog!=null){
			dialog.setCancelable(true);
			dialog.show();
		}
		BaseHandler handler = new BaseHandler(this.context, dialog, callBack);
		new InvokeThread(request,handler,what).start();
	}
	
	/*public ProgressDialog buildDialog(String message){
		ProgressDialog daDialog = new ProgressDialog(context);
		daDialog.setMessage(BeanUtils.isEmpty(message) ? context.getString(R.string.system_load_message) : message);
		return daDialog;
	}*/
	
	public LoadingDialog buildDialog(String message){
		LoadingDialog daDialog = new LoadingDialog(context);
		daDialog.setMessage(BeanUtils.isEmpty(message) ? context.getString(R.string.system_load_message) : message);
		return daDialog;
	}
}
