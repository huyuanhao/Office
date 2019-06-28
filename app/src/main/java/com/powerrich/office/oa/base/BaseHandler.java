package com.powerrich.office.oa.base;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.StringUtils;


/**
 * FileName    : BaseHandler.java
 * Description : 基本的message handler处理
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2011-12-14 下午21:50:38
 **/
public class BaseHandler extends Handler{
	
	/**绑定的数据回调方法对豄1�7*/
	private IRequestCallBack callBack = null;
	
	/**绑定的dialog对象*/
	private Object dialogObj = null;
	
	/**绑定当前的上下问环境*/
	private Context context = null;
	
	/**绑定的处理类垄1�7*/
	private int what = 0;
	
	public BaseHandler(Context context,IRequestCallBack callBack,int what){
		this(context, null, callBack);
		this.what = what;
	}
	
	public BaseHandler(Context context,Object dialog,IRequestCallBack callBack){
		this.context = context;
		this.dialogObj = dialog;
		this.callBack = callBack;
	}
	
	public void setWhat(int what){
		this.what = what;
	}
	
	public Object getDialogObj(){ 
		return dialogObj;
	}
	
	public int getWhat() {
		return what;
	}
	
	
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		try{
			Object messageObj = msg.obj;
			if(callBack!=null){
				//绑定Context
				callBack.bindContext(this.context);
				//绑定Handler
				callBack.bindHandler(this,this.what);
				if(messageObj instanceof HttpResponse){
					//处理response
					processResponse((HttpResponse)messageObj,this.what);
				}else{
					//处理我们自定义的消息内容
					callBack.handleMessage(msg,this.what);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(callBack!=null){
				//callBack的结束回谄1�7
				callBack.finish(dialogObj,this.what);
			}
		}
	}
	
	/**
	 * 处理response对象
	 * @param response
	 */
	protected void processResponse(HttpResponse response,int what){
		//请求成功是否的处理
		if(response.isSuccess()){
			//返回正确数据的处理
			if(StringUtils.interceptResponse(context, response)) {
				return;
			}
			callBack.process(response,what);
		}else{
		    //判断返回的数据头为空或者 返回的不是200，则进行提醒网络异常
		    if(response.getHeaderData() == null || !(response.getHeaderData().get("X-ANDROID-RESPONSE-SOURCE")+"").contains("200")){
		        //网络错误时的统一处理
		        callBack.onNetError(what);
		    }else{
		        callBack.onReturnError(response, response.getErrorItem(), what);
		    }

		}
	}

	public IRequestCallBack getCallBack() {
		return callBack;
	}
}
