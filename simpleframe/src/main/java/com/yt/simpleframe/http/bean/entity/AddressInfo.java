package com.yt.simpleframe.http.bean.entity;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/12
 * 版权：
 */

public class AddressInfo implements Serializable {

//    {"ADDRESS":"江西省赣州市龙南县105国道39号","ADDRESSID":"201803271430475878029986036","CITY":"",
//            "COMPANY_NAME":"日升新能源有限公司","HANDSET":"15078552369","ISDEFAULT":"1","PROV":"",
//            "SJRXM":"陈生","TEL_NO":"","USERID":"201801261631497478967845420","YZBM":"315000"}

    private String ADDRESS;
    private String ADDRESSID;
    private String COMPANY_NAME;
    private String HANDSET;
    private String ISDEFAULT;
    private String SJRXM;
    private String TEL_NO;
    private String USERID;
    private String YZBM;
    private String PROV;
    private String CITY;

    private boolean isDefault = false;

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getADDRESSID() {
        return ADDRESSID;
    }

    public void setADDRESSID(String ADDRESSID) {
        this.ADDRESSID = ADDRESSID;
    }

    public String getCOMPANY_NAME() {
        return COMPANY_NAME;
    }

    public void setCOMPANY_NAME(String COMPANY_NAME) {
        this.COMPANY_NAME = COMPANY_NAME;
    }

    public String getHANDSET() {
        return HANDSET;
    }

    public void setHANDSET(String HANDSET) {
        this.HANDSET = HANDSET;
    }

    public String getISDEFAULT() {
        return ISDEFAULT;
    }

    public void setISDEFAULT(String ISDEFAULT) {
        this.ISDEFAULT = ISDEFAULT;
    }

    public String getSJRXM() {
        return SJRXM;
    }

    public void setSJRXM(String SJRXM) {
        this.SJRXM = SJRXM;
    }

    public String getTEL_NO() {
        return TEL_NO;
    }

    public void setTEL_NO(String TEL_NO) {
        this.TEL_NO = TEL_NO;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getYZBM() {
        return YZBM;
    }

    public void setYZBM(String YZBM) {
        this.YZBM = YZBM;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getPROV() {
        return PROV;
    }

    public void setPROV(String PROV) {
        this.PROV = PROV;
    }
}
