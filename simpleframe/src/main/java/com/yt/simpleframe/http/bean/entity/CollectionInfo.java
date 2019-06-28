package com.yt.simpleframe.http.bean.entity;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/12
 * 版权：
 */

public class CollectionInfo implements Serializable{
    private String ITEMID;
    private String ITEMNAME;
    private String NORMACCEPTDEPART; // 1：回复0：未回复
    private String NORMACCEPTDEPARTID;
    private String SCID;
    private String SCTIME;
    private String SXBM;
    private String SXCODE;
    private String USERID;
    public boolean checkBoo = false;


    @Override
    public String toString() {
        return "CollectionInfo{" +
                "ITEMID='" + ITEMID + '\'' +
                ", ITEMNAME='" + ITEMNAME + '\'' +
                ", NORMACCEPTDEPART='" + NORMACCEPTDEPART + '\'' +
                ", NORMACCEPTDEPARTID='" + NORMACCEPTDEPARTID + '\'' +
                ", SCID='" + SCID + '\'' +
                ", SCTIME='" + SCTIME + '\'' +
                ", SXBM='" + SXBM + '\'' +
                ", SXCODE='" + SXCODE + '\'' +
                ", USERID='" + USERID + '\'' +
                '}';
    }

    public String getITEMID() {
        return ITEMID;
    }

    public void setITEMID(String ITEMID) {
        this.ITEMID = ITEMID;
    }

    public String getITEMNAME() {
        return ITEMNAME;
    }

    public void setITEMNAME(String ITEMNAME) {
        this.ITEMNAME = ITEMNAME;
    }

    public String getNORMACCEPTDEPART() {
        return NORMACCEPTDEPART;
    }

    public void setNORMACCEPTDEPART(String NORMACCEPTDEPART) {
        this.NORMACCEPTDEPART = NORMACCEPTDEPART;
    }

    public String getNORMACCEPTDEPARTID() {
        return NORMACCEPTDEPARTID;
    }

    public void setNORMACCEPTDEPARTID(String NORMACCEPTDEPARTID) {
        this.NORMACCEPTDEPARTID = NORMACCEPTDEPARTID;
    }

    public String getSCID() {
        return SCID;
    }

    public void setSCID(String SCID) {
        this.SCID = SCID;
    }

    public String getSCTIME() {
        return SCTIME;
    }

    public void setSCTIME(String SCTIME) {
        this.SCTIME = SCTIME;
    }

    public String getSXBM() {
        return SXBM;
    }

    public void setSXBM(String SXBM) {
        this.SXBM = SXBM;
    }

    public String getSXCODE() {
        return SXCODE;
    }

    public void setSXCODE(String SXCODE) {
        this.SXCODE = SXCODE;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }
}
