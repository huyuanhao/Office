package com.powerrich.office.oa.bean;

/**
 * 文 件 名：PublicityInfo
 * 描   述：阳光政务负面公示实体类
 * 作   者：Wangzheng
 * 时   间：2017/12/4
 * 版   权：v1.0
 */
public class PublicityInfo {
    private String enterpriseName;
    private String applyName;
    private String approveEnterprise;
    private String desDate;
    private String failDescribe;
    private String publicityId;
    private String link;
    private String showMessage;

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getApproveEnterprise() {
        return approveEnterprise;
    }

    public void setApproveEnterprise(String approveEnterprise) {
        this.approveEnterprise = approveEnterprise;
    }

    public String getDesDate() {
        return desDate;
    }

    public void setDesDate(String desDate) {
        this.desDate = desDate;
    }

    public String getFailDescribe() {
        return failDescribe;
    }

    public void setFailDescribe(String failDescribe) {
        this.failDescribe = failDescribe;
    }

    public String getPublicityId() {
        return publicityId;
    }

    public void setPublicityId(String publicityId) {
        this.publicityId = publicityId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getShowMessage() {
        return showMessage;
    }

    public void setShowMessage(String showMessage) {
        this.showMessage = showMessage;
    }
}
