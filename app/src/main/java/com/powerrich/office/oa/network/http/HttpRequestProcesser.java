package com.powerrich.office.oa.network.http;

import com.powerrich.office.oa.network.base.BaseRequestProcesser;
import com.powerrich.office.oa.network.base.IRequestTask;



/**
 * FileName    : HttpRequestHelper.java
 * Description : 
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-8 下午6:06:36
 **/
public class HttpRequestProcesser extends BaseRequestProcesser {

	@Override
	public IRequestTask buildTask() {
		return new HttpRequestTask();
	}

}
