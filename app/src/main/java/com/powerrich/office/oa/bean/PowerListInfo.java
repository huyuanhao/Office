package com.powerrich.office.oa.bean;

/**
 * 文 件 名：PowerListInfo
 * 描   述：阳光政务行政权力清单列表实体类
 * 作   者：Wangzheng
 * 时   间：2018/1/23
 * 版   权：v1.0
 */
public class PowerListInfo {
    private String itemName;
    private String department;
    private String departmentId;
    private String pid;
    private String powerTypeName;
    private String updateTime;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPowerTypeName() {
        return powerTypeName;
    }

    public void setPowerTypeName(String powerTypeName) {
        this.powerTypeName = powerTypeName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
