package com.powerrich.office.oa.bean;

/**
 * 文 件 名：AddressInfo
 * 描   述：邮寄地址信息类
 * 作   者：Wangzheng
 * 时   间：2018/1/8
 * 版   权：v1.0
 */
public class AddressInfo {
    private String address;
    private String companyName;
    private String handSet;
    private String id;
    private String isDefault;
    private String addressee;
    private String telNo;
    private String userId;
    private String postalCode;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getHandSet() {
        return handSet;
    }

    public void setHandSet(String handSet) {
        this.handSet = handSet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
