package com.powerrich.office.oa.bean;

/**
 * 文 件 名：PolicyStatuteInfo
 * 描   述：阳光政务政策法规实体类
 * 作   者：Wangzheng
 * 时   间：2017/12/7
 * 版   权：v1.0
 */
public class PolicyStatuteInfo {
    private String createTime;
    private String title;
    private String type;
    private String policyStatuteId;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPolicyStatuteId() {
        return policyStatuteId;
    }

    public void setPolicyStatuteId(String policyStatuteId) {
        this.policyStatuteId = policyStatuteId;
    }
}
