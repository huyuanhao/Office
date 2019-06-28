package com.powerrich.office.oa.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Message;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.view.LoadingDialog;

import java.util.HashMap;
import java.util.Map;


/**
 * FileName    : BaseRequestCallBack.java
 * Description : 基本的数请求回调 籄1�7
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2011-12-14 下午21:50:38
 **/
public abstract class BaseRequestCallBack implements IRequestCallBack{

	protected Context context;
	/**Handler的关系映射1�7*/
	private Map<Integer,BaseHandler> whatsMapping = new HashMap<Integer,BaseHandler>();
	/**Handler的关系映射的另一种表玄1�7*/
	private Map<BaseHandler,Integer> handlersMapping = new HashMap<BaseHandler,Integer>();
	
	@SuppressWarnings("unused")
	private Object dialogObject;
	
	@Override
	public void onReturnError(HttpResponse response, ResultItem error,int what) {
		showErrorMessage(context.getString(R.string.system_data_error_message));
	}

	@Override
	public void onNetError(int what) {
		showErrorMessage(context.getString(R.string.system_net_error_message));
	}
	
	protected void showErrorMessage(String message){
		try{
			Activity activity = (Activity) context;
			if(activity instanceof BaseActivity){
				((BaseActivity)activity).showErrorMessage(message);
			} else{
				Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			}
		}catch(Exception e){
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void handleMessage(Message message,int what) {
		
	}

	@Override
	public void finish(Object dialogObj,int what) {
		dialogObject = dialogObj;
		if(dialogObj !=null){
			if(dialogObj instanceof ProgressDialog ){
				((ProgressDialog)dialogObj).dismiss();
				
			}else if(dialogObj instanceof LoadingDialog){
				//这里有时会报View not attached to window manager
				Activity activity = (Activity) context;
                if (null != context) {
                    if (((LoadingDialog)dialogObj).isShowing() && activity != null && !activity.isFinishing())
				        ((LoadingDialog)dialogObj).dismiss();
                }
			}
		}
	}
	
	@Override
	public void bindContext(Context context) {
		this.context = context;
	}

	@Override
	public void bindHandler(BaseHandler handler,int what) {
		whatsMapping.put(what, handler);
		handlersMapping.put(handler, what);
	}

	@Override
	public BaseHandler getHandler(int what) {
		return whatsMapping.get(what);
	}

	@Override
	public int getHandlerWhat(BaseHandler handler) {
		return handlersMapping.get(handler);
	}
}
