package com.powerrich.office.oa.bean;

import java.io.Serializable;

/**
 * 文 件 名：ServiceBranchBean
 * 描   述：服务网点
 * 作   者：Wangzheng
 * 时   间：2018-7-25 10:30:22
 * 版   权：v1.0
 */
public class ServiceBranchBean implements Serializable{
    private String CREAT_TIME;
    private String ID;
    private String NAME;
    private String SXWINDOWS_ID;
    private String SXWINDOW_NAME;
    private boolean isSelected;

    public String getCREAT_TIME() {
        return CREAT_TIME;
    }

    public void setCREAT_TIME(String CREAT_TIME) {
        this.CREAT_TIME = CREAT_TIME;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getSXWINDOWS_ID() {
        return SXWINDOWS_ID;
    }

    public void setSXWINDOWS_ID(String SXWINDOWS_ID) {
        this.SXWINDOWS_ID = SXWINDOWS_ID;
    }

    public String getSXWINDOW_NAME() {
        return SXWINDOW_NAME;
    }

    public void setSXWINDOW_NAME(String SXWINDOW_NAME) {
        this.SXWINDOW_NAME = SXWINDOW_NAME;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
