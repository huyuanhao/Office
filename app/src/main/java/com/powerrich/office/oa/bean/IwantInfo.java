package com.powerrich.office.oa.bean;

import java.util.ArrayList;
import java.util.List;

public class IwantInfo {

    /**
     * type : (1：咨询  2：投诉 3：建议 4：领导信箱)
     * dept : 单位名称
     * deptid : 单位名称id
     * title : 标题
     * content : 内容
     * isopen : 是否公开0不公开，1公开
     * name : 姓名
     * idcard : 身份证号码
     * phone : 手机号码
     * isnote : 是否短信通知(0:否，1:是)
     * address : 联系地址
     * emailtype : 领导信箱信件类型，领导信箱专用，其它类型请传空值。传值：1、建议；2、咨询；3、投诉；4、举报；5、求决；6、其它
     * filelist : [{"downpath":"/xzsp/20180621/2018062114330016221289561","filename":"152032452334_图片3.png"},{"downpath":"/xzsp/20180621/2018062114359011520289781","filename":"152089100283_图片4.png"},{"downpath":"/xzsp/20180621/2018062114370216221208527","filename":"152030985615_图片5.png"}]
     */

    private String type;
    private String dept;
    private String deptid;
    private String title;
    private String content;
    private String isopen;
    private String name;
    private String idcard;
    private String phone;
    private String isnote;
    private String address;
    private String emailtype;
    private List<FilelistBean> filelist = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsopen() {
        return isopen;
    }

    public void setIsopen(String isopen) {
        this.isopen = isopen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsnote() {
        return isnote;
    }

    public void setIsnote(String isnote) {
        this.isnote = isnote;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailtype() {
        return emailtype;
    }

    public void setEmailtype(String emailtype) {
        this.emailtype = emailtype;
    }

    public List<FilelistBean> getFilelist() {
        return filelist;
    }

    public void setFilelist(List<FilelistBean> filelist) {
        this.filelist = filelist;
    }

    public static class FilelistBean {
        /**
         * downpath : /xzsp/20180621/2018062114330016221289561
         * filename : 152032452334_图片3.png
         */

        private String downpath;
        private String filename;

        public String getDownpath() {
            return downpath;
        }

        public void setDownpath(String downpath) {
            this.downpath = downpath;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }
    }
}
