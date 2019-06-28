package com.yt.simpleframe.http.bean.entity;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/7/19
 * 版权：
 */

public class EvaluationInfo implements Serializable {
    /**
     * {
     "APPRAISER": "",
     "CREATTIME": "2018-07-19 13:54:39",
     "EVALUATE_CONTENT": "挺好的",
     "ID": "201807191354392753655466708",
     "ISANONYMITY": "",
     "ITEM_CODE": "36060000wj-xk-0002-12018071202",
     "ITEM_NAME": "公共场所卫生首次许可",
     "MODIFY_TIME": "2018-07-19 13:54:39",
     "PROKEYID": "a790ef59d4944349bdfb94fbe0dc0009",
     "SATISFACTION_DEGREE": "4",
     "SXID": "201807110853199812702425514"
     }
     */
    private String APPRAISER;
    private String CREATTIME;
    private String EVALUATE_CONTENT;
    private String ID;
    private String ISANONYMITY;
    private String ITEM_CODE;
    private String ITEM_NAME;
    private String MODIFY_TIME;
    private String PROKEYID;
    private String SATISFACTION_DEGREE;
    private String SXID;

    public String getAPPRAISER() {
        return APPRAISER;
    }

    public void setAPPRAISER(String APPRAISER) {
        this.APPRAISER = APPRAISER;
    }

    public String getCREATTIME() {
        return CREATTIME;
    }

    public void setCREATTIME(String CREATTIME) {
        this.CREATTIME = CREATTIME;
    }

    public String getEVALUATE_CONTENT() {
        return EVALUATE_CONTENT;
    }

    public void setEVALUATE_CONTENT(String EVALUATE_CONTENT) {
        this.EVALUATE_CONTENT = EVALUATE_CONTENT;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getISANONYMITY() {
        return ISANONYMITY;
    }

    public void setISANONYMITY(String ISANONYMITY) {
        this.ISANONYMITY = ISANONYMITY;
    }

    public String getITEM_CODE() {
        return ITEM_CODE;
    }

    public void setITEM_CODE(String ITEM_CODE) {
        this.ITEM_CODE = ITEM_CODE;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public String getMODIFY_TIME() {
        return MODIFY_TIME;
    }

    public void setMODIFY_TIME(String MODIFY_TIME) {
        this.MODIFY_TIME = MODIFY_TIME;
    }

    public String getPROKEYID() {
        return PROKEYID;
    }

    public void setPROKEYID(String PROKEYID) {
        this.PROKEYID = PROKEYID;
    }

    public String getSATISFACTION_DEGREE() {
        return SATISFACTION_DEGREE;
    }

    public void setSATISFACTION_DEGREE(String SATISFACTION_DEGREE) {
        this.SATISFACTION_DEGREE = SATISFACTION_DEGREE;
    }

    public String getSXID() {
        return SXID;
    }

    public void setSXID(String SXID) {
        this.SXID = SXID;
    }
}
