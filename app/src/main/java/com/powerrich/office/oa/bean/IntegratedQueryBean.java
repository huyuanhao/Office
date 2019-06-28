package com.powerrich.office.oa.bean;

/**
 * 文 件 名：IntegratedQueryBean
 * 描   述：综合查询实体类
 * 作   者：Wangzheng
 * 时   间：2018/2/9
 * 版   权：v1.0
 */
public class IntegratedQueryBean {
    private String itemId;
    private String itemName;
    private String transactScope;
    private String transactOrganization;
    private String transactLimit;
    private String isCharge;
    private String transactCount;
    private String transactWindowAddress;
    private String consultPhone;
    private String complainPhone;
    private String shortName;
    private String siteNo;
    /** "0":事项  "1":事项主题  "2":行政权力清单  "3":投诉、咨询、建议*/
    private String type;
    private String iwantType;
    private String tagType;
    private String sxlx;

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getIwantType() {
        return iwantType;
    }

    public void setIwantType(String iwantType) {
        this.iwantType = iwantType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getTransactScope() {
        return transactScope;
    }

    public void setTransactScope(String transactScope) {
        this.transactScope = transactScope;
    }

    public String getTransactOrganization() {
        return transactOrganization;
    }

    public void setTransactOrganization(String transactOrganization) {
        this.transactOrganization = transactOrganization;
    }

    public String getTransactLimit() {
        return transactLimit;
    }

    public void setTransactLimit(String transactLimit) {
        this.transactLimit = transactLimit;
    }

    public String getIsCharge() {
        return isCharge;
    }

    public void setIsCharge(String isCharge) {
        this.isCharge = isCharge;
    }

    public String getTransactCount() {
        return transactCount;
    }

    public void setTransactCount(String transactCount) {
        this.transactCount = transactCount;
    }

    public String getTransactWindowAddress() {
        return transactWindowAddress;
    }

    public void setTransactWindowAddress(String transactWindowAddress) {
        this.transactWindowAddress = transactWindowAddress;
    }

    public String getConsultPhone() {
        return consultPhone;
    }

    public void setConsultPhone(String consultPhone) {
        this.consultPhone = consultPhone;
    }

    public String getComplainPhone() {
        return complainPhone;
    }

    public void setComplainPhone(String complainPhone) {
        this.complainPhone = complainPhone;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSiteNo() {
        return siteNo;
    }

    public void setSiteNo(String siteNo) {
        this.siteNo = siteNo;
    }

    public String getSxlx() {
        return sxlx;
    }

    public void setSxlx(String sxlx) {
        this.sxlx = sxlx;
    }
}
