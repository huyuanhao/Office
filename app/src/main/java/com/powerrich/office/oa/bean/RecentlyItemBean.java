package com.powerrich.office.oa.bean;

/**
 * 文 件 名：RecentlyItemBean
 * 描   述：最近使用事项实体类
 * 作   者：Wangzheng
 * 时   间：2018-7-10 09:07:01
 * 版   权：v1.0
 */
public class RecentlyItemBean {
    private String browseTime;
    private String id;
    private String itemId;
    private String itemName;
    private String userId;
    private String BSDX;


    public String getBSDX() {
        return BSDX;
    }

    public void setBSDX(String BSDX) {
        this.BSDX = BSDX;
    }

    public String getBrowseTime() {
        return browseTime;
    }

    public void setBrowseTime(String browseTime) {
        this.browseTime = browseTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
