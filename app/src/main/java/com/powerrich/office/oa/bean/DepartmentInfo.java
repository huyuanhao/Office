package com.powerrich.office.oa.bean;

/**
 * 文 件 名：DepartmentInfo
 * 描   述：部门列表实体类
 * 作   者：Wangzheng
 * 时   间：2017/11/28
 * 版   权：v1.0
 */
public class DepartmentInfo {
    private String deptImage;
    private String shortName;
    private String siteName;
    private String siteNo;
    private String siteType;
    private String upSiteNo;
    private boolean isSelected;

    public DepartmentInfo() {
        super();
    }

    public DepartmentInfo(String siteName, String siteNo) {
        super();
        this.siteName = siteName;
        this.siteNo = siteNo;
    }

    public String getDeptImage() {
        return deptImage;
    }

    public void setDeptImage(String deptImage) {
        this.deptImage = deptImage;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getUpSiteNo() {
        return upSiteNo;
    }

    public void setUpSiteNo(String upSiteNo) {
        this.upSiteNo = upSiteNo;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
