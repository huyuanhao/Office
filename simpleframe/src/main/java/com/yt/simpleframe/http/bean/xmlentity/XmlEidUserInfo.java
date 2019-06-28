package com.yt.simpleframe.http.bean.xmlentity;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/8
 * 版权：
 */

/**
 XmlCompanyInfo
 */



public class XmlEidUserInfo extends BaseXmlInfo{
    private static final long serialVersionUID = 1L;

    /**
     *
     *
     buffer.append("<auditRegInfo>");
     buffer.append("<name>" + userName + "</name>");
     buffer.append("<userduty>" + userDuty + "</userduty>");
     buffer.append("<phone>" + phoneNum + "</phone>");
     buffer.append("<realname>" + realName + "</realname>");
     buffer.append("<idcard>" + idCard + "</idcard>");
     buffer.append("<sex>" + sex + "</sex>");
     buffer.append("<ethnic>" + nation + "</ethnic>");
     buffer.append("<appeidcode>" + appEidCode + "</appeidcode>");
     buffer.append("</auditRegInfo>");

     */

    private String name;
    private String userduty;
    private String phone;
    private String realname;
    private String idcard;
    private String sex;
    private String ethnic;
    private String appeidcode;

    public XmlEidUserInfo(String userduty, String name, String phone, String realname, String idcard, String sex, String ethnic,
                          String appeidcode) {
        this.userduty = userduty;
        this.name = name;
        this.phone = phone;
        this.realname = realname;
        this.idcard = idcard;
        this.sex = sex;
        this.ethnic = ethnic;
        this.appeidcode = appeidcode;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserduty() {
        return userduty;
    }

    public void setUserduty(String userduty) {
        this.userduty = userduty;
    }

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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    public String getAppeidcode() {
        return appeidcode;
    }

    public void setAppeidcode(String appeidcode) {
        this.appeidcode = appeidcode;
    }

    @Override
    public String getType() {
        return "auditRegInfo";
    }


}
