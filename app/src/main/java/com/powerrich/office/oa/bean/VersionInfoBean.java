package com.powerrich.office.oa.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * @author MingPeng
 * @date 2018/1/25
 */

public class VersionInfoBean implements Serializable {

    /**
     * APKSIZE : 10M
     * CONTENT : 基础版本
     * DATATIME : 2018-01-25 11:37:29
     * DOWNPATH : /xzsp/20180125/201801251137281882880760306
     * FILENAME : iyingtan.apk
     * FILETYPE : apk
     * HDFSFILENAME : 1516851448138_iyingtan.apk
     * ISFORCE : 0 是否强制更新 0是 1否
     * PROJECT_ID : 201801251137294480965450146
     * SYSNO : 1  app类型 0为ios 1为android
     * URL : /xzsp/20180125/201801251137281882880760306
     * VERCODE : 0.1.0
     * VERNAME : iyingtan
     */

    private String APKSIZE;
    private String CONTENT;
    private String DATATIME;
    private String DOWNPATH;
    private String FILENAME;
    private String FILETYPE;
    private String HDFSFILENAME;
    /**
     * 是否强制更新 0是 1否
     */
    private String ISFORCE;
    private String PROJECT_ID;
    /**
     * app类型 0为ios 1为android
     */
    private String SYSNO;
    private String URL;
    private String VERCODE;
    private String VERNAME;

    public String getAPKSIZE() {
        return APKSIZE;
    }

    public long getApkByteSize() {
        if (!TextUtils.isEmpty(APKSIZE)) {
            if (APKSIZE.endsWith("M") || APKSIZE.endsWith("MB")) {
                return Long.parseLong(APKSIZE.split("M")[0]) * 1024 * 1024;
            }
            if (APKSIZE.endsWith("K") || APKSIZE.endsWith("KB")) {
                return Long.parseLong(APKSIZE.split("K")[0]) * 1024;
            }
        }
        return Long.parseLong(APKSIZE);
    }

    public void setAPKSIZE(String APKSIZE) {
        this.APKSIZE = APKSIZE.toUpperCase();
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getDATATIME() {
        return DATATIME;
    }

    public void setDATATIME(String DATATIME) {
        this.DATATIME = DATATIME;
    }

    public String getDOWNPATH() {
        return DOWNPATH;
    }

    public void setDOWNPATH(String DOWNPATH) {
        this.DOWNPATH = DOWNPATH;
    }

    public String getFILENAME() {
        return FILENAME;
    }

    public void setFILENAME(String FILENAME) {
        this.FILENAME = FILENAME;
    }

    public String getFILETYPE() {
        return FILETYPE;
    }

    public void setFILETYPE(String FILETYPE) {
        this.FILETYPE = FILETYPE;
    }

    public String getHDFSFILENAME() {
        return HDFSFILENAME;
    }

    public void setHDFSFILENAME(String HDFSFILENAME) {
        this.HDFSFILENAME = HDFSFILENAME;
    }

    public boolean getISFORCE() {
        return "0".equals(ISFORCE);
    }

    public void setISFORCE(String ISFORCE) {
        this.ISFORCE = ISFORCE;
    }

    public String getPROJECT_ID() {
        return PROJECT_ID;
    }

    public void setPROJECT_ID(String PROJECT_ID) {
        this.PROJECT_ID = PROJECT_ID;
    }

    public String getSYSNO() {
        return SYSNO;
    }

    public void setSYSNO(String SYSNO) {
        this.SYSNO = SYSNO;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getVERCODE() {
        return VERCODE;
    }

    public void setVERCODE(String VERCODE) {
        this.VERCODE = VERCODE;
    }

    public String getVERNAME() {
        return VERNAME;
    }

    public void setVERNAME(String VERNAME) {
        this.VERNAME = VERNAME;
    }
}
