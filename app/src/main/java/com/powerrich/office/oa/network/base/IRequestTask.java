package com.powerrich.office.oa.network.base;



/**
 * FileName    : IRequestTask.java
 * Description : 真正做请求的任务
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-25 下午5:54:05
 **/
public interface IRequestTask {
	
	/**
	 * 执行请求
	 * @param request
	 */
    void init(IRequestItem request);
	
	/**
	 * 执行
	 */
    void exec();
	
	/**
	 * 取消
	 */
    void cancel();
}
