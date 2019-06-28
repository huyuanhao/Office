package com.powerrich.office.oa.bean;

public class WorkInfo {
	//个人、企业
	String tag_id;
	String tag_level;
	String tag_level_about;
	String tag_name;
	String tag_order;
	String tag_type;
	String tag_visit_count;
	//部门
	String short_name;
	String site_name;
	String site_no;
	String site_type;
	String up_site_no;
	
	
	public WorkInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WorkInfo(String tag_id, String tag_level, String tag_level_about,
			String tag_name, String tag_order, String tag_type,
			String tag_visit_count) {
		super();
		this.tag_id = tag_id;
		this.tag_level = tag_level;
		this.tag_level_about = tag_level_about;
		this.tag_name = tag_name;
		this.tag_order = tag_order;
		this.tag_type = tag_type;
		this.tag_visit_count = tag_visit_count;
	}
	
	public WorkInfo(String short_name, String site_name, String site_no,
			String site_type, String up_site_no) {
		super();
		this.short_name = short_name;
		this.site_name = site_name;
		this.site_no = site_no;
		this.site_type = site_type;
		this.up_site_no = up_site_no;
	}
	
	public String getShort_name() {
		return short_name;
	}
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getSite_no() {
		return site_no;
	}
	public void setSite_no(String site_no) {
		this.site_no = site_no;
	}
	public String getSite_type() {
		return site_type;
	}
	public void setSite_type(String site_type) {
		this.site_type = site_type;
	}
	public String getUp_site_no() {
		return up_site_no;
	}
	public void setUp_site_no(String up_site_no) {
		this.up_site_no = up_site_no;
	}
	public String getTag_id() {
		return tag_id;
	}
	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}
	public String getTag_level() {
		return tag_level;
	}
	public void setTag_level(String tag_level) {
		this.tag_level = tag_level;
	}
	public String getTag_level_about() {
		return tag_level_about;
	}
	public void setTag_level_about(String tag_level_about) {
		this.tag_level_about = tag_level_about;
	}
	public String getTag_name() {
		return tag_name;
	}
	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}
	public String getTag_order() {
		return tag_order;
	}
	public void setTag_order(String tag_order) {
		this.tag_order = tag_order;
	}
	public String getTag_type() {
		return tag_type;
	}
	public void setTag_type(String tag_type) {
		this.tag_type = tag_type;
	}
	public String getTag_visit_count() {
		return tag_visit_count;
	}
	public void setTag_visit_count(String tag_visit_count) {
		this.tag_visit_count = tag_visit_count;
	}
	
	
}
