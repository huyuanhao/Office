package com.powerrich.office.oa.base;

import android.os.Message;

import com.powerrich.office.oa.network.base.IRequestItem;
import com.powerrich.office.oa.network.base.IResponseItem;
import com.powerrich.office.oa.network.base.ProtocolType;


/**
 * FileName    : InvokeThread.java
 * Description : 数据请求调用的线稄1�7
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2011-12-14 下午21:50:38
 **/
public class InvokeThread extends Thread{

	private IRequestItem request = null;
	private BaseHandler handler = null;
	private Runnable runable = null;
	private int what = 0;
	
	public InvokeThread(IRequestItem request,BaseHandler hadHandler){
		this.request = request;
		this.handler = hadHandler;
	}
	
	public InvokeThread(IRequestItem request,BaseHandler hadHandler,int what){
		this.request = request;
		this.handler = hadHandler;
		this.what = what;
	}
	
	public InvokeThread(Runnable runable,BaseHandler baseHandler){
		this.runable = runable;
		this.handler = baseHandler;
	}
	
	public InvokeThread(Runnable runable,BaseHandler baseHandler,int what){
		this.runable = runable;
		this.handler = baseHandler;
		this.what = what;
	}
	
	@Override
	public void run() {
		Message msg = new Message();
		try{
			msg.what = what;
			handler.setWhat(what);
			if(request!=null){
				//进行数据请求
				
				request.setCallBack(new com.powerrich.office.oa.network.base.IRequestCallBack() {
					
					@Override
					public void onResponseEvent(ProtocolType.ResponseEvent event, IResponseItem response) {
						if(ProtocolType.ResponseEvent.isFinish(event)){
							handler.sendMessage(handler.obtainMessage(what,response));
						}
					}
				});
				request.send();
			}
			if(runable!=null){
				runable.run();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(handler!=null&&runable!=null){
				handler.sendMessage(msg);
			}
		}
	}
}
