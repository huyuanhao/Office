package com.powerrich.office.oa.bean;

import java.io.Serializable;

public class PolicyStatute implements Serializable {

	private static final long serialVersionUID = -7942573624727739292L;
	private String content;
	private String title;
	private String createTime;
	private String id;
	private String idPr;
	private String idPrAll;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdPr() {
		return idPr;
	}

	public void setIdPr(String idPr) {
		this.idPr = idPr;
	}

	public String getIdPrAll() {
		return idPrAll;
	}

	public void setIdPrAll(String idPrAll) {
		this.idPrAll = idPrAll;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
