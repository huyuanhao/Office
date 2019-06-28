package com.powerrich.office.oa.network.base;

import java.util.Collection;

/**
 * FileName    : IRequestProcesser.java
 * Description : 各自协议的请求管理
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-8 上午10:30:12
 **/
public interface IRequestProcesser {

	/***
	 * 执行的网络请求
	 * @param task
	 * @param callBack
	 */
    void addRequest(IRequestItem request);

	/**
	 * 取消网络请求
	 * @param task
	 */
    void cancelRequest(String requestId);
	
	/**
	 * 获取所有管理的任务
	 * @return
	 */
    Collection<IRequestTask> list();
	
	/**
	 * 请求是否，应用退出时触发
	 */
    void clear();
}
