package com.powerrich.office.oa.bean;

import java.io.Serializable;

public class OnlineBookingInfo implements Serializable{
	String status;
	String time;
	String count;
	public OnlineBookingInfo() {
		super();
	}
	public OnlineBookingInfo(String status, String time, String count) {
		super();
		this.status = status;
		this.time = time.substring(time.lastIndexOf(" ") + 1, time.length());
		this.count = count;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String name;				//预约人姓名
	private String phone;				//预约人电话
	private String idcard;			//预约人身份证号码
	private String email;			//预约人身份证号码
	private String orderDate;			//预约日期
	private String orderTime;			//预约时间
	private String itemId;			//预约事项id
	private String siteid;			//部门id
	private String siteName;			//部门名称
	private String itemName;			//事项名称
	private String orgName;			//组织机构名称
	private String orgNum;			//组织机构代码

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgNum() {
		return orgNum;
	}

	public void setOrgNum(String orgNum) {
		this.orgNum = orgNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
