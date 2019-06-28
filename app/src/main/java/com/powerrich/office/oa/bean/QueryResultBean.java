package com.powerrich.office.oa.bean;

/**
 * 文 件 名：QueryResultBean
 * 描   述：办件查询进度实体类
 * 作   者：Wangzheng
 * 时   间：2018-7-10 16:13:09
 * 版   权：v1.0
 */
public class QueryResultBean {
    private String COMP_NAME;
    private String COMP_NO;
    private String DEAL_RESULT;
    private String FINISH_SITE_NAME;
    private String FINISH_STAFF_NAME;
    private String FINISH_TIME;
    private String LIMIT_TIME;
    private String SIGN_TIME;

    public String getCOMP_NAME() {
        return COMP_NAME;
    }

    public void setCOMP_NAME(String COMP_NAME) {
        this.COMP_NAME = COMP_NAME;
    }

    public String getCOMP_NO() {
        return COMP_NO;
    }

    public void setCOMP_NO(String COMP_NO) {
        this.COMP_NO = COMP_NO;
    }

    public String getDEAL_RESULT() {
        return DEAL_RESULT;
    }

    public void setDEAL_RESULT(String DEAL_RESULT) {
        this.DEAL_RESULT = DEAL_RESULT;
    }

    public String getFINISH_SITE_NAME() {
        return FINISH_SITE_NAME;
    }

    public void setFINISH_SITE_NAME(String FINISH_SITE_NAME) {
        this.FINISH_SITE_NAME = FINISH_SITE_NAME;
    }

    public String getFINISH_STAFF_NAME() {
        return FINISH_STAFF_NAME;
    }

    public void setFINISH_STAFF_NAME(String FINISH_STAFF_NAME) {
        this.FINISH_STAFF_NAME = FINISH_STAFF_NAME;
    }

    public String getFINISH_TIME() {
        return FINISH_TIME;
    }

    public void setFINISH_TIME(String FINISH_TIME) {
        this.FINISH_TIME = FINISH_TIME;
    }

    public String getLIMIT_TIME() {
        return LIMIT_TIME;
    }

    public void setLIMIT_TIME(String LIMIT_TIME) {
        this.LIMIT_TIME = LIMIT_TIME;
    }

    public String getSIGN_TIME() {
        return SIGN_TIME;
    }

    public void setSIGN_TIME(String SIGN_TIME) {
        this.SIGN_TIME = SIGN_TIME;
    }
}
