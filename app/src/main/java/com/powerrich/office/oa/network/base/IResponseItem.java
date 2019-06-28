package com.powerrich.office.oa.network.base;

import java.util.List;

import com.powerrich.office.oa.common.ResultError;
import com.powerrich.office.oa.common.ResultItem;


/**
 * FileName    : IResponseItem.java
 * Description : 请求返回的接口
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-8 下午5:00:06
 **/
public interface IResponseItem {

	/**
	 * 获取指定的结果接
	 * @param T
	 * @return
	 */
    <T> T getResultItem(Class<T> T);

	/***
	 * 获取指定的结果集
	 * @param T
	 * @return
	 */
    <T> List<T> getResultArray(Class<T> T);

	/***
	 * 设置数据
	 * @param obj
	 */
    void setResultData(Object obj);

	/**
	 * 响应的中长度
	 * @param size
	 */
    void setContentSize(long size);

	/***
	 * 获取响应的长度
	 * @return
	 */
    long getContentSize();

	/**
	 * 计算进度的总长度
	 * @param size
	 */
    void setTotalSize(long size);

	/***
	 * 获取进度的总长度
	 * @return
	 */
    long getTotalSize();

	/**
	 * 计算进度的完成大小
	 * @param size
	 */
    void setCompleteSize(long size);

	/***
	 * 获取进度的完成大小
	 * @return
	 */
    long getCompleteSize();

	/**
	 * 返回错误对象
	 * @return
	 */
    ResultError getErrorItem();

	/**
	 * 设置code信息
	 * @param code
	 * @param error
	 */
    void setError(String code, String error);

	/**
	 * 设置头域数据
	 * @param item
	 */
    void setHeaderData(ResultItem item);

	/**
	 * 获取响应头信息
	 * @return
	 */
    ResultItem getHeaderData();
	
	
	boolean isSuccess();

}
