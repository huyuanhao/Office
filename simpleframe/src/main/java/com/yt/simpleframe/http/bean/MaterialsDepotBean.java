package com.yt.simpleframe.http.bean;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 文 件 名：MaterialsDepotBean
 * 描   述：材料库实体类
 * 作   者：Wangzheng
 * 时   间：2018-7-24 10:11:07
 * 版   权：v1.0
 */
public class MaterialsDepotBean implements Serializable {
    private String COMP_FILE_NAME;
    private String CREATEPERSON;
    private String CREATETIME;
    private String FILESIZE;
    private String HDFSFILENAME;
    private String ID;
    private String NAME;
    private String PATH;
    private String TASK_FILE_ID;
    private boolean isSelected;


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getCOMP_FILE_NAME() {
        return COMP_FILE_NAME;
    }

    public void setCOMP_FILE_NAME(String COMP_FILE_NAME) {
        this.COMP_FILE_NAME = COMP_FILE_NAME;
    }

    public String getCREATEPERSON() {
        return CREATEPERSON;
    }

    public void setCREATEPERSON(String CREATEPERSON) {
        this.CREATEPERSON = CREATEPERSON;
    }

    public String getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(String CREATETIME) {
        this.CREATETIME = CREATETIME;
    }

    public String getFILESIZE() {
        return FILESIZE;
    }

    public void setFILESIZE(String FILESIZE) {
        this.FILESIZE = FILESIZE;
    }

    public String getHDFSFILENAME() {
        return HDFSFILENAME;
    }

    public void setHDFSFILENAME(String HDFSFILENAME) {
        this.HDFSFILENAME = HDFSFILENAME;
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

    public String getPATH() {
        return PATH;
    }

    public void setPATH(String PATH) {
        this.PATH = PATH;
    }

    public String getTASK_FILE_ID() {
        return TASK_FILE_ID;
    }

    public void setTASK_FILE_ID(String TASK_FILE_ID) {
        this.TASK_FILE_ID = TASK_FILE_ID;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
