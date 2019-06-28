package com.powerrich.office.oa.fund.bean;

/**
 * @author PC
 * @date 2019/05/08 11:40
 */
public class FundCodeBean {

    /**
     * DATA : {"ret":"0","msg":"短信生成成功"}
     * code : 0
     * message : 操作成功！
     */

    private String DATA;
    private String code;
    private String message;

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
