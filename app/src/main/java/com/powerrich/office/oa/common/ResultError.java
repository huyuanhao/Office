package com.powerrich.office.oa.common;

import com.powerrich.office.oa.tools.Constants;




/**
 * FileName    : ResultError.java
 * Description : 返回的错误代码
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2011-8-8 下午03:58:21
 **/
public class ResultError extends ResultItem{
	
	public ResultError(String errorCode,String errorMessage) {
		super();
		this.put(Constants.XML_ERROR_CODE_KEY, errorCode);
		this.put(Constants.XML_ERROR_MESSAGE_KEY, errorMessage);
	}

	/**
	 * 获取错误CODE
	 * @return
	 */
	public String getErrorCode(){
		return getString(Constants.XML_ERROR_CODE_KEY);
	}
	
	/**
	 * 获取错误信息
	 * @return
	 */
	public String getErrorMessage(){
		return getString(Constants.XML_ERROR_MESSAGE_KEY);
	}

	
	
}
