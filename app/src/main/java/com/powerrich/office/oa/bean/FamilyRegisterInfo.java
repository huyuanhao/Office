package com.powerrich.office.oa.bean;

import java.io.Serializable;

/**
 * @title 待办件类
 * @author dir_wang
 * @date 2016-8-17下午4:56:56
 */
public class FamilyRegisterInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5260988631330065985L;
	private String itemId;
	private String title;
	private String time;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getTitle() {
		return title;
	}

	public String getTime() {
		return time;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
