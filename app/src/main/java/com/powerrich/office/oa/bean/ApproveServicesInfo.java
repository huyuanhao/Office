package com.powerrich.office.oa.bean;

/**
 * 文 件 名：ApproveServicesInfo
 * 描   述：阳光政务行政审批中介服务清单实体类
 * 作   者：Wangzheng
 * 时   间：2017/12/8
 * 版   权：v1.0
 */
public class ApproveServicesInfo {
    private String createTime;
    private String siteName;
    private String siteNo;
    private String servicesName;
    private String servicesId;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteNo() {
        return siteNo;
    }

    public void setSiteNo(String siteNo) {
        this.siteNo = siteNo;
    }

    public String getServicesName() {
        return servicesName;
    }

    public void setServicesName(String servicesName) {
        this.servicesName = servicesName;
    }

    public String getServicesId() {
        return servicesId;
    }

    public void setServicesId(String servicesId) {
        this.servicesId = servicesId;
    }
}
