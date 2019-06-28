package com.yt.simpleframe.http.bean.entity;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/18
 * 版权：
 */

public class CertificateInfo implements Serializable{
    /**
     *  "CARD_FILE": "/xzsp/20180716/201807161652002842361426349",
     "CARD_NAME": "营业执照（F类个体户）",
     "COMP_FILE_NAME": "",
     "ENDTIME": "2022-08-31 00:00:00",
     "FILENAME": "3606002018000001DT.pdf",
     "HDFSFILENAME": "3606002018000001DT.pdf",
     "ID": "201807161652003697682514308",
     "STARTTIME": "",
     "TRACKINGNUMBER": "",
     "ZZDJ": "B",
     "ZZLYS": "电子证照库"
     */


    private String CARD_FILE;
    private String CARD_NAME;
    private String COMP_FILE_NAME;
    private String ENDTIME;
    private String FILENAME;
    private String HDFSFILENAME;
    private String ID;
    private String STARTTIME;
    private String TRACKINGNUMBER;
    private String ZZDJ;
    private String ZZLYS;
    private String CARD_NO;

    private String CREATETIME="";
    private String NAME="";
    private String PATH="";

    public String getPATH() {
        return PATH;
    }

    public void setPATH(String PATH) {
        this.PATH = PATH;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(String CREATETIME) {
        this.CREATETIME = CREATETIME;
    }

    public String getCARD_NO() {
        return CARD_NO;
    }

    public void setCARD_NO(String CARD_NO) {
        this.CARD_NO = CARD_NO;
    }

    public boolean checkBoo = false;

    public String getCARD_FILE() {
        return CARD_FILE;
    }

    public void setCARD_FILE(String CARD_FILE) {
        this.CARD_FILE = CARD_FILE;
    }

    public String getCARD_NAME() {
        return CARD_NAME;
    }

    public void setCARD_NAME(String CARD_NAME) {
        this.CARD_NAME = CARD_NAME;
    }

    public String getCOMP_FILE_NAME() {
        return COMP_FILE_NAME;
    }

    public void setCOMP_FILE_NAME(String COMP_FILE_NAME) {
        this.COMP_FILE_NAME = COMP_FILE_NAME;
    }

    public String getENDTIME() {
        return ENDTIME;
    }

    public void setENDTIME(String ENDTIME) {
        this.ENDTIME = ENDTIME;
    }

    public String getFILENAME() {
        return FILENAME;
    }

    public void setFILENAME(String FILENAME) {
        this.FILENAME = FILENAME;
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

    public String getSTARTTIME() {
        return STARTTIME;
    }

    public void setSTARTTIME(String STARTTIME) {
        this.STARTTIME = STARTTIME;
    }

    public String getTRACKINGNUMBER() {
        return TRACKINGNUMBER;
    }

    public void setTRACKINGNUMBER(String TRACKINGNUMBER) {
        this.TRACKINGNUMBER = TRACKINGNUMBER;
    }

    public String getZZDJ() {
        return ZZDJ;
    }

    public void setZZDJ(String ZZDJ) {
        this.ZZDJ = ZZDJ;
    }

    public String getZZLYS() {
        return ZZLYS;
    }

    public void setZZLYS(String ZZLYS) {
        this.ZZLYS = ZZLYS;
    }
}
