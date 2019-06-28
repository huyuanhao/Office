package com.yt.simpleframe.http.bean.xmlentity;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/8
 * 版权：
 */

/**
 buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
 buffer.append("<auditRegInfo>");
 buffer.append("<name>" + userName + "</name>");
 buffer.append("<userduty>" + userDuty + "</userduty>");
 buffer.append("<businesslicence>" + socialCreditCode + "</businesslicence>");
 buffer.append("<idcard>" + idCard + "</idcard>");
 buffer.append("<enterprisePerson>" + legalName + "</enterprisePerson>");
 buffer.append("<phone>" + phone + "</phone>");
 buffer.append("<address>" + address + "</address>");
 buffer.append("</auditRegInfo>");
 request.addParam("xmlInfo", buffer.toString());
 */



public class XmlCompanyInfo extends BaseXmlInfo{
    private static final long serialVersionUID = 1L;

    private String userduty;
    private String enterpriseName;
    private String enterpriseCode;
    private String enterpriseAddress;
    private String enterprisePerson;
    private String idcard;


    public XmlCompanyInfo(String userduty, String enterpriseName, String enterpriseCode, String enterpriseAddress, String
            enterprisePerson, String idcard) {
        this.userduty = userduty;
        this.enterpriseName = enterpriseName;
        this.enterpriseCode = enterpriseCode;
        this.enterpriseAddress = enterpriseAddress;
        this.enterprisePerson = enterprisePerson;
        this.idcard = idcard;
    }



    public String getUserduty() {
        return userduty;
    }

    public void setUserduty(String userduty) {
        this.userduty = userduty;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }

    public String getEnterpriseAddress() {
        return enterpriseAddress;
    }

    public void setEnterpriseAddress(String enterpriseAddress) {
        this.enterpriseAddress = enterpriseAddress;
    }

    public String getEnterprisePerson() {
        return enterprisePerson;
    }

    public void setEnterprisePerson(String enterprisePerson) {
        this.enterprisePerson = enterprisePerson;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    @Override
    public String getType() {
        return "auditRegInfo";
    }



}
