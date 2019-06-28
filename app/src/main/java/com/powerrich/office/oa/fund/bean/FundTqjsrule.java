package com.powerrich.office.oa.fund.bean;

/**
 * @author PC
 * @date 2019/04/20 16:54
 */
public class FundTqjsrule {

    /**
     * bpmid : 41
     * userid : 0
     */

    private String bpmid;
    private String userid = "0";

    public void setBpmid(String bpmid) {
        this.bpmid = bpmid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getBpmid() {
        return bpmid == null ? "" : bpmid;
    }

    public String getUserid() {
        return userid == null ? "" : userid;
    }
}
