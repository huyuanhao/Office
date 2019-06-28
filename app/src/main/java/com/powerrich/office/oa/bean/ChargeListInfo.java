package com.powerrich.office.oa.bean;

/**
 * 文 件 名：ChargeListInfo
 * 描   述：阳光政务行政事业性收费清单实体类
 * 作   者：Wangzheng
 * 时   间：2017/12/4
 * 版   权：v1.0
 */
public class ChargeListInfo {
    private String createTime;
    private String listId;
    private String projectApproval;
    private String chargeProject;
    private String siteName;
    private String siteNo;
    private String policyId;
    private String policyGist;
    private String fundsManagement;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getProjectApproval() {
        return projectApproval;
    }

    public void setProjectApproval(String projectApproval) {
        this.projectApproval = projectApproval;
    }

    public String getChargeProject() {
        return chargeProject;
    }

    public void setChargeProject(String chargeProject) {
        this.chargeProject = chargeProject;
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

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getPolicyGist() {
        return policyGist;
    }

    public void setPolicyGist(String policyGist) {
        this.policyGist = policyGist;
    }

    public String getFundsManagement() {
        return fundsManagement;
    }

    public void setFundsManagement(String fundsManagement) {
        this.fundsManagement = fundsManagement;
    }
}
