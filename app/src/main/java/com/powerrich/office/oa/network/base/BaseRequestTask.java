package com.powerrich.office.oa.network.base;

import com.powerrich.office.oa.network.base.IRequestItem.RequestStatus;
import com.powerrich.office.oa.network.base.ProtocolType.ResponseEvent;
import com.powerrich.office.oa.tools.Logger;



/**
 * FileName    : BaseRequestTask.java
 * Description : IRequestTask的基类
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-25 下午6:33:14
 **/
public abstract class BaseRequestTask implements IRequestTask {

	protected String TAG = this.getClass().getSimpleName();

	protected RequestStatus status = RequestStatus.NONE;
	/**请求对象*/
	protected IRequestItem request;
	/**响应*/
	protected IResponseItem response;

	/**
	 * 回调统一回调
	 * @param event
	 */
	protected void doCallBack(ResponseEvent event) {
		if(status == RequestStatus.CANCEL || status == RequestStatus.ERROR || status == RequestStatus.SUCCESS){
			Logger.w(TAG, "can't doCallback requestId " + request.getRequestId() + " event:" + ResponseEvent.ERROR);
			return;
		}
		if(request.getCallback() == null){
			Logger.w(TAG, "can't doCallback callback not seting");
			return;
		}
		
		if (ResponseEvent.SUCCESS == event) {
			status = RequestStatus.SUCCESS;
			Logger.d(TAG, "requestId " + request.getRequestId() + " event:" + ResponseEvent.SUCCESS);
			request.getCallback().onResponseEvent(ResponseEvent.SUCCESS, response);

		} else if (ResponseEvent.PROGRESS == event) {
			Logger.d(
					TAG,
					"requestId " + request.getRequestId() + " event:" + ResponseEvent.PROGRESS + " completeSize:" + response.getCompleteSize() + " totalSize:"
							+ response.getTotalSize());
			request.getCallback().onResponseEvent(ResponseEvent.PROGRESS, response);

		} else if (ResponseEvent.ERROR == event) {
			status = RequestStatus.ERROR;
			Logger.w(TAG, "requestId " + request.getRequestId() + " event:" + ResponseEvent.ERROR);
			request.getCallback().onResponseEvent(ResponseEvent.ERROR, response);
			
		} else if (ResponseEvent.START == event) {
			status = RequestStatus.RUNNING;
			Logger.d(TAG, "requestId " + request.getRequestId() + " event:" + ResponseEvent.START);
			request.getCallback().onResponseEvent(ResponseEvent.START, response);
			
		} else if (ResponseEvent.CANCEL == event) {
			status = RequestStatus.CANCEL;
			Logger.d(TAG, "requestId " + request.getRequestId() + " event:" + ResponseEvent.CANCEL);
			request.getCallback().onResponseEvent(ResponseEvent.CANCEL, response);
		}
	}
}
