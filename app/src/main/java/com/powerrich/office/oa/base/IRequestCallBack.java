package com.powerrich.office.oa.base;

import android.content.Context;
import android.os.Message;

import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;


/**
 * FileName    : IRequestCallBack
 * Description : 
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2011-12-14 下午21:50:38
 **/
public interface IRequestCallBack {
	
	/**
	 * 数据正确请求
	 * @param response
	 */
    void process(HttpResponse response, int what);
	
	/**
	 * @param response
	 * @param error
	 */
    void onReturnError(HttpResponse response, ResultItem error, int what);
	
	void onNetError(int what);
	
	/**
	 * @ param messageType
	 * @ param messageObj
	 * @ param 原始的message
	 */
    void handleMessage(Message message, int what);
	
	/**
	 * @param dialogObj
	 */
    void finish(Object dialogObj, int what);
	
	/**
	 * 绑定整个过程的Context
	 * @param context
	 */
    void bindContext(Context context);
	
	/**
	 * 绑定当前的消息机刄1�7
	 * @param handler
	 */
    void bindHandler(BaseHandler handler, int what);
	
	/**
	 * 获取对应的消息机刄1�7
	 * @ param handler
	 */
    BaseHandler getHandler(int what);
	
	/**
	 * 获取handler对应的what
	 * @param handler
	 * @return
	 */
    int getHandlerWhat(BaseHandler handler);
}
