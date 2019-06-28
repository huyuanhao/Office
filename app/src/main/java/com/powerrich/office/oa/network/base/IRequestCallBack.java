package com.powerrich.office.oa.network.base;

import com.powerrich.office.oa.network.base.ProtocolType.ResponseEvent;




/**
 * FileName    : IRequestCallBack.java
 * Description : 对外统一的callback回调
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-8 下午4:56:38
 **/
public interface IRequestCallBack {

	/**
	 * 事件回调
	 * @param event
	 * @param response
	 */
    void onResponseEvent(ResponseEvent event, IResponseItem response);
}
