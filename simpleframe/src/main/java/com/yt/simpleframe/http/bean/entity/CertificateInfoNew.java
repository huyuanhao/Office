package com.yt.simpleframe.http.bean.entity;

import java.io.Serializable;

/**
 * 新的电子证件类
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/18
 * 版权：
 */

public class CertificateInfoNew implements Serializable{
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


    private String ID_CODE;
    private String AUTH_CODE;
    private String NAME;
    private String HOLDER_NAME;
    private String ISSUE_ORG_NAME;
    private String ISSUE_DATE;
    private String BEGIN_DATE;
    private String EXPIRY_DATE="";
    private String ISASYNC;

    public boolean checkBoo = false;

    public String getID_CODE() {
        return ID_CODE;
    }

    public void setID_CODE(String ID_CODE) {
        this.ID_CODE = ID_CODE;
    }

    public String getAUTH_CODE() {
        return AUTH_CODE;
    }

    public void setAUTH_CODE(String AUTH_CODE) {
        this.AUTH_CODE = AUTH_CODE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getHOLDER_NAME() {
        return HOLDER_NAME;
    }

    public void setHOLDER_NAME(String HOLDER_NAME) {
        this.HOLDER_NAME = HOLDER_NAME;
    }

    public String getISSUE_ORG_NAME() {
        return ISSUE_ORG_NAME;
    }

    public void setISSUE_ORG_NAME(String ISSUE_ORG_NAME) {
        this.ISSUE_ORG_NAME = ISSUE_ORG_NAME;
    }

    public String getISSUE_DATE() {
        return ISSUE_DATE;
    }

    public void setISSUE_DATE(String ISSUE_DATE) {
        this.ISSUE_DATE = ISSUE_DATE;
    }

    public String getBEGIN_DATE() {
        return BEGIN_DATE;
    }

    public void setBEGIN_DATE(String BEGIN_DATE) {
        this.BEGIN_DATE = BEGIN_DATE;
    }

    public String getEXPIRY_DATE() {
        return EXPIRY_DATE;
    }

    public void setEXPIRY_DATE(String EXPIRY_DATE) {
        this.EXPIRY_DATE = EXPIRY_DATE;
    }

    public String getISASYNC() {
        return ISASYNC;
    }

    public void setISASYNC(String ISASYNC) {
        this.ISASYNC = ISASYNC;
    }
}
