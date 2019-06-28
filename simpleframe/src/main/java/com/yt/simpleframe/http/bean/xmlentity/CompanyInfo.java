package com.yt.simpleframe.http.bean.xmlentity;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/8
 * 版权：
 */

/**
 *  buffer.append("<auditRegInfo>");
 buffer.append("<name>" + userName + "</name>");
 buffer.append("<userduty>" + userDuty + "</userduty>");
 buffer.append("<businesslicence>" + socialCreditCode + "</businesslicence>");
 buffer.append("<idcard>" + idCard + "</idcard>");
 buffer.append("<enterprisePerson>" + legalName + "</enterprisePerson>");
 buffer.append("<phone>" + phone + "</phone>");
 buffer.append("<address>" + address + "</address>");
 buffer.append("</auditRegInfo>");
 */



public class CompanyInfo extends BaseXmlInfo{
    private static final long serialVersionUID = 1L;
    private String name;
    private String userduty;
    private String businesslicence;
    private String idcard;
    private String enterprisePerson;
    private String phone;
    private String address;


    public CompanyInfo() {
    }

    public CompanyInfo(String name, String userduty, String businesslicence, String idcard, String enterprisePerson, String
            phone, String address) {
        this.name = name;
        this.userduty = userduty;
        this.businesslicence = businesslicence;
        this.idcard = idcard;
        this.enterprisePerson = enterprisePerson;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public String getType() {
        return "auditRegInfo";
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserduty() {
        return userduty;
    }

    public void setUserduty(String userduty) {
        this.userduty = userduty;
    }

    public String getBusinesslicence() {
        return businesslicence;
    }

    public void setBusinesslicence(String businesslicence) {
        this.businesslicence = businesslicence;
    }

    public String getEnterprisePerson() {
        return enterprisePerson;
    }

    public void setEnterprisePerson(String enterprisePerson) {
        this.enterprisePerson = enterprisePerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CompanyInfo{" +
                "name='" + name + '\'' +
                ", userduty='" + userduty + '\'' +
                ", businesslicence='" + businesslicence + '\'' +
                ", enterprisePerson='" + enterprisePerson + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }


}
