package com.powerrich.office.oa.bean;

public class AttachmentInfo {
	String biz_id;
	String comp_file_name;
	String comp_file_no;
	String comp_no;
	String filename;
	String filepath;
	String filesize;
	String filety;
	String file_state;
	public AttachmentInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AttachmentInfo(String biz_id, String comp_file_name,
			String comp_file_no, String comp_no, String filename,
			String filepath, String filesize, String filety, String file_state) {
		super();
		this.biz_id = biz_id;
		this.comp_file_name = comp_file_name;
		this.comp_file_no = comp_file_no;
		this.comp_no = comp_no;
		this.filename = filename;
		this.filepath = filepath;
		this.filesize = filesize;
		this.filety = filety;
		this.file_state = file_state;
	}
	public String getBiz_id() {
		return biz_id;
	}
	public void setBiz_id(String biz_id) {
		this.biz_id = biz_id;
	}
	public String getComp_file_name() {
		return comp_file_name;
	}
	public void setComp_file_name(String comp_file_name) {
		this.comp_file_name = comp_file_name;
	}
	public String getComp_file_no() {
		return comp_file_no;
	}
	public void setComp_file_no(String comp_file_no) {
		this.comp_file_no = comp_file_no;
	}
	public String getComp_no() {
		return comp_no;
	}
	public void setComp_no(String comp_no) {
		this.comp_no = comp_no;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public String getFilety() {
		return filety;
	}
	public void setFilety(String filety) {
		this.filety = filety;
	}
	public String getFile_state() {
		return file_state;
	}
	public void setFile_state(String file_state) {
		this.file_state = file_state;
	}
	
	
}
