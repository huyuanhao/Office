package com.yt.simpleframe.http.bean.entity;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/12
 * 版权：
 */

public class AdvisoryInfo implements Serializable{
    private String BIZCODE;
    private String JL_ID;
    private String TITLE;
    private String MYNAME;

    private String CRETTIME;
    private String ISREVERT; // 1：回复0：未回复
    private String RESERTTIME;

    private String MOBILE;
    private String QUESTION;
    private String QUSTIONTYPE;
    private String REVERTCONTENT;
    private String REVERTER;
    private String SITE_NAME;

    public String getSITE_NAME() {
        return SITE_NAME;
    }

    public void setSITE_NAME(String SITE_NAME) {
        this.SITE_NAME = SITE_NAME;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getQUESTION() {
        return QUESTION;
    }

    public void setQUESTION(String QUESTION) {
        this.QUESTION = QUESTION;
    }

    public String getQUSTIONTYPE() {
        return QUSTIONTYPE;
    }

    public void setQUSTIONTYPE(String QUSTIONTYPE) {
        this.QUSTIONTYPE = QUSTIONTYPE;
    }

    public String getREVERTCONTENT() {
        return REVERTCONTENT;
    }

    public void setREVERTCONTENT(String REVERTCONTENT) {
        this.REVERTCONTENT = REVERTCONTENT;
    }

    public String getREVERTER() {
        return REVERTER;
    }

    public void setREVERTER(String REVERTER) {
        this.REVERTER = REVERTER;
    }

    /**
     * BIZCODE	string	受理编号
     {
     "ADDRESS": "",
     "BIZCODE": "ZX20180428135730897",
     "CRETTIME": "2018-04-28 13:57:30",
     "JL_ID": "201804281357308976658490163",
     "MOBILE": "15874568777",
     "MYNAME": "5ttttttttttttioghjfgjrtvhikrjirfjysbkhggjgfjhfjjhgdfhj",
     "QUESTION": "ghjh%xyvgrztvojohibuvnt9jtuukattatmtapdtapmtdtmwmtatdtdtdtdtdtdtdtdtdtdtd",
     "QUSTIONTYPE": "市司法局",
     "RESERTTIME": "",
     "REVERTCONTENT": "",
     "REVERTER": "",
     "TITLE": "fgyrdhidbjfvjykyfjvkgjdjwvjdhifkugkifjifjlfhfjgk"
     }
     * @return
     */




    public String getBIZCODE() {
        return BIZCODE;
    }

    public void setBIZCODE(String BIZCODE) {
        this.BIZCODE = BIZCODE;
    }

    public String getCRETTIME() {
        return CRETTIME;
    }

    public void setCRETTIME(String CRETTIME) {
        this.CRETTIME = CRETTIME;
    }

    public String getISREVERT() {
        return ISREVERT;
    }

    public void setISREVERT(String ISREVERT) {
        this.ISREVERT = ISREVERT;
    }

    public String getJL_ID() {
        return JL_ID;
    }

    public void setJL_ID(String JL_ID) {
        this.JL_ID = JL_ID;
    }

    public String getMYNAME() {
        return MYNAME;
    }

    public void setMYNAME(String MYNAME) {
        this.MYNAME = MYNAME;
    }

    public String getRESERTTIME() {
        return RESERTTIME;
    }

    public void setRESERTTIME(String RESERTTIME) {
        this.RESERTTIME = RESERTTIME;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }
}
