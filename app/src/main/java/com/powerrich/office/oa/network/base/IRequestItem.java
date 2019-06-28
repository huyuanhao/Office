package com.powerrich.office.oa.network.base;

/**
 * FileName    : IRequestTask.java
 * Description : 
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-8 上午10:29:39
 **/
public interface IRequestItem {
	/***
	 * 请求是否有效
	 * @return
	 */
    boolean isValid();

	/***
	 * 获取网络协议
	 * @return
	 */
    ProtocolType getProtocal();

	/***
	 * 获取请求ID
	 * @return
	 */
    String getRequestId();

	/**
	 * 获取请求的状态
	 * @return
	 */
    RequestStatus getStatus();

	/***
	 * 设置请求的状态
	 * @param status
	 */
    void setRequestStatus(RequestStatus status);

	/**
	 * 获取对应callback
	 * @return
	 */
    IRequestCallBack getCallback();
	
	
	void setCallBack(IRequestCallBack callBack);

	/**请求发送*/
    void send();
	
	/**取消请求*/
    void cancel();

	/**请求任务的机制状态*/
    enum RequestStatus {
		/**未执行*/
		NONE,
		/**执行中*/
		RUNNING,
		/**执行失败*/
		ERROR,
		/**请求取消*/
		CANCEL,
		/**请求成功*/
		SUCCESS
    }
}
