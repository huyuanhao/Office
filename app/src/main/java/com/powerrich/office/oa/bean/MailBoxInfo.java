package com.powerrich.office.oa.bean;

public class MailBoxInfo {
	String name;
	String post;
	String responsibility;
	int photo;
	public MailBoxInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MailBoxInfo(String name, String post, String responsibility,
			int photo) {
		super();
		this.name = name;
		this.post = post;
		this.responsibility = responsibility;
		this.photo = photo;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResponsibility() {
		return responsibility;
	}
	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}
	public int getPhoto() {
		return photo;
	}
	public void setPhoto(int photo) {
		this.photo = photo;
	}
	
}
