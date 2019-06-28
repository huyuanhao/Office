package com.powerrich.office.oa.network;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.powerrich.office.oa.network.base.IRequestItem;
import com.powerrich.office.oa.network.base.IRequestProcesser;
import com.powerrich.office.oa.network.base.ProtocolType;
import com.powerrich.office.oa.network.http.HttpRequestProcesser;
import com.powerrich.office.oa.tools.Logger;



/**
 * FileName    : RequestDispatcher.java
 * Description : 所有请求的调度者，调度请求给各个协议管理处理
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市鸿宇时代信息技术有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-8 上午10:25:26
 **/
public class RequestDispatcher {
	
	private static final String TAG = "RequestDispatcher";
	/**单例*/
	private static RequestDispatcher MANAGER = new RequestDispatcher();

	/**协议对应的处理器*/
	private Map<ProtocolType, IRequestProcesser> helpers = new HashMap<ProtocolType, IRequestProcesser>();
	
	/**所有请求的总的线程数*/
	private ExecutorService executors = Executors.newFixedThreadPool(20);

	private RequestDispatcher() {
		//注册通讯协议处理对象
		registerRequestManager(ProtocolType.HTTP, new HttpRequestProcesser());
	}

	public static RequestDispatcher getInstance() {
		if (MANAGER == null) {
			MANAGER = new RequestDispatcher();
		}
		return MANAGER;
	}

	/**
	 * 注册协议的处理类
	 * @param type
	 * @param helper
	 */
	public void registerRequestManager(ProtocolType type, IRequestProcesser helper) {
		helpers.put(type, helper);
	}
	
	/**
	 * 获取协议的处理类
	 * @param type
	 * @return
	 */
	public IRequestProcesser getHelperByProtocal(ProtocolType type){
		return helpers.get(type);
	}

	/***
	 * 添加新的请求
	 * @param items
	 */
	public void addRequest(final IRequestItem items) {
		executors.execute(new Runnable() {

			@Override
			public void run() {
				Logger.i(TAG, "dispather " + items.getRequestId());
				try {
					//获取执行的对象
					IRequestProcesser helper = helpers.get(items.getProtocal());
					helper.addRequest(items);
				} catch (Exception e) {
					e.printStackTrace();
					Logger.d(TAG, "dispather error requestId " + e.getMessage());
				}
			}
		});
	}

	/**
	 * 取消请求
	 * @param requestId
	 */
	public void cancelRequest(final IRequestItem items) {
		executors.execute(new Runnable() {

			@Override
			public void run() {
				Logger.i(TAG, "cancelRequest " + items.getRequestId());
				try {
					//获取执行的对象
					IRequestProcesser helper = helpers.get(items.getProtocal());
					helper.cancelRequest(items.getRequestId());
				} catch (Exception e) {
					e.printStackTrace();
					Logger.d(TAG, "cancelRequest error requestId " + e.getMessage());
				}
			}
		});
	}
}
