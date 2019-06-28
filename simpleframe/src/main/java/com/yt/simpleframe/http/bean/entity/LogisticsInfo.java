package com.yt.simpleframe.http.bean.entity;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/15
 * 版权：
 */

public class LogisticsInfo implements Serializable{
    private String CONSIGNEE_ADDRESS;
    private String CONSIGNEE_NAME;
    private String DATA_SOURCE;
    private String EMIT_TIME;
    private String ITEM_NAME;
    private String ORDER_NUM;
    private String ORDER_STATUS;
    private String PROKEYID;
    private String RECORD_ID;
    private String SEND_NAME;
    private String TRACKING_NUM;
    private String USER_ID;

    @Override
    public String toString() {
        return "LogisticsInfo{" +
                "CONSIGNEE_ADDRESS='" + CONSIGNEE_ADDRESS + '\'' +
                ", CONSIGNEE_NAME='" + CONSIGNEE_NAME + '\'' +
                ", DATA_SOURCE='" + DATA_SOURCE + '\'' +
                ", EMIT_TIME='" + EMIT_TIME + '\'' +
                ", ITEM_NAME='" + ITEM_NAME + '\'' +
                ", ORDER_NUM='" + ORDER_NUM + '\'' +
                ", ORDER_STATUS='" + ORDER_STATUS + '\'' +
                ", PROKEYID='" + PROKEYID + '\'' +
                ", RECORD_ID='" + RECORD_ID + '\'' +
                ", SEND_NAME='" + SEND_NAME + '\'' +
                ", TRACKING_NUM='" + TRACKING_NUM + '\'' +
                ", USER_ID='" + USER_ID + '\'' +
                '}';
    }

    public String getCONSIGNEE_ADDRESS() {
        return CONSIGNEE_ADDRESS;
    }

    public void setCONSIGNEE_ADDRESS(String CONSIGNEE_ADDRESS) {
        this.CONSIGNEE_ADDRESS = CONSIGNEE_ADDRESS;
    }

    public String getCONSIGNEE_NAME() {
        return CONSIGNEE_NAME;
    }

    public void setCONSIGNEE_NAME(String CONSIGNEE_NAME) {
        this.CONSIGNEE_NAME = CONSIGNEE_NAME;
    }

    public String getDATA_SOURCE() {
        return DATA_SOURCE;
    }

    public void setDATA_SOURCE(String DATA_SOURCE) {
        this.DATA_SOURCE = DATA_SOURCE;
    }

    public String getEMIT_TIME() {
        return EMIT_TIME;
    }

    public void setEMIT_TIME(String EMIT_TIME) {
        this.EMIT_TIME = EMIT_TIME;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public String getORDER_NUM() {
        return ORDER_NUM;
    }

    public void setORDER_NUM(String ORDER_NUM) {
        this.ORDER_NUM = ORDER_NUM;
    }

    public String getORDER_STATUS() {
        return ORDER_STATUS;
    }

    public void setORDER_STATUS(String ORDER_STATUS) {
        this.ORDER_STATUS = ORDER_STATUS;
    }

    public String getPROKEYID() {
        return PROKEYID;
    }

    public void setPROKEYID(String PROKEYID) {
        this.PROKEYID = PROKEYID;
    }

    public String getRECORD_ID() {
        return RECORD_ID;
    }

    public void setRECORD_ID(String RECORD_ID) {
        this.RECORD_ID = RECORD_ID;
    }

    public String getSEND_NAME() {
        return SEND_NAME;
    }

    public void setSEND_NAME(String SEND_NAME) {
        this.SEND_NAME = SEND_NAME;
    }

    public String getTRACKING_NUM() {
        return TRACKING_NUM;
    }

    public void setTRACKING_NUM(String TRACKING_NUM) {
        this.TRACKING_NUM = TRACKING_NUM;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }
}
