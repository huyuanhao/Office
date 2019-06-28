package com.yt.simpleframe.http.bean.entity;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/12/012
 * 版权：
 */
public class ReservationInfo implements Serializable{
    private String A_ID;
    private String DEPTNAME;
    private String ITEMNAME;
    private String ORDER_DATE;
    private String ORDER_NO;
    private String REAL_NAME;

    @Override
    public String toString() {
        return "ReservationInfo{" +
                "A_ID='" + A_ID + '\'' +
                ", DEPTNAME='" + DEPTNAME + '\'' +
                ", ITEMNAME='" + ITEMNAME + '\'' +
                ", ORDER_DATE='" + ORDER_DATE + '\'' +
                ", ORDER_NO='" + ORDER_NO + '\'' +
                ", REAL_NAME='" + REAL_NAME + '\'' +
                '}';
    }

    public String getA_ID() {
        return A_ID;
    }

    public void setA_ID(String a_ID) {
        A_ID = a_ID;
    }

    public String getDEPTNAME() {
        return DEPTNAME;
    }

    public void setDEPTNAME(String DEPTNAME) {
        this.DEPTNAME = DEPTNAME;
    }

    public String getITEMNAME() {
        return ITEMNAME;
    }

    public void setITEMNAME(String ITEMNAME) {
        this.ITEMNAME = ITEMNAME;
    }

    public String getORDER_DATE() {
        return ORDER_DATE;
    }

    public void setORDER_DATE(String ORDER_DATE) {
        this.ORDER_DATE = ORDER_DATE;
    }

    public String getORDER_NO() {
        return ORDER_NO;
    }

    public void setORDER_NO(String ORDER_NO) {
        this.ORDER_NO = ORDER_NO;
    }

    public String getREAL_NAME() {
        return REAL_NAME;
    }

    public void setREAL_NAME(String REAL_NAME) {
        this.REAL_NAME = REAL_NAME;
    }
}
