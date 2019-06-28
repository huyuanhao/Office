package com.powerrich.office.oa.bean;

public class PharmacyInfo {
	
	/** 部门编号*/
	String deptId;
	/** 主办单位*/
	String pkdeptName;
	/** 子流程名称*/
	String pkName;
	/** 事项基本信息主键编号*/
	String pxId;
	/** 事项编号*/
	String sxId;
	public PharmacyInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PharmacyInfo(String deptId, String pkdeptName, String pkName,
			String pxId, String sxId) {
		super();
		this.deptId = deptId;
		this.pkdeptName = pkdeptName;
		this.pkName = pkName;
		this.pxId = pxId;
		this.sxId = sxId;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getPkdeptName() {
		return pkdeptName;
	}
	public void setPkdeptName(String pkdeptName) {
		this.pkdeptName = pkdeptName;
	}
	public String getPkName() {
		return pkName;
	}
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}
	public String getPxId() {
		return pxId;
	}
	public void setPxId(String pxId) {
		this.pxId = pxId;
	}
	public String getSxId() {
		return sxId;
	}
	public void setSxId(String sxId) {
		this.sxId = sxId;
	}
	
	
}
