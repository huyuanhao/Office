package com.yt.simpleframe.http.bean.entity;

/**
 * Created by Administrator on 2018/6/8.
 */

public class XmlInfo {
    private String loginTime;
    private String userName;
    private String phoneNumber;
    private String phone;
    private String email;
    private String postNumber;
    private boolean isNote;
    private boolean isMail;
    private String address;
    private String dept;
    private String title;
    private String content;
    private String local;
    private String isOpen;

    public XmlInfo() {
    }

    public XmlInfo(String loginTime, String userName, String phone, String phoneNumber, String email, String postNumber, boolean isNote, boolean isMail, String address, String dept, String title, String content, String local, String isOpen) {
        this.loginTime = loginTime;
        this.userName = userName;
        this.phone = phone;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.postNumber = postNumber;
        this.isNote = isNote;
        this.isMail = isMail;
        this.address = address;
        this.dept = dept;
        this.title = title;
        this.content = content;
        this.local = local;
        this.isOpen = isOpen;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }

    public boolean isNote() {
        return isNote;
    }

    public void setNote(boolean note) {
        isNote = note;
    }

    public boolean isMail() {
        return isMail;
    }

    public void setMail(boolean mail) {
        isMail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }
}
