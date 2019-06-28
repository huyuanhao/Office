package com.powerrich.office.oa.bean;

/**
 * @author PC
 * @date 2019/03/01 11:52
 */
public class BaBean<T> {
    /*版本说明*/
    private String code;
    /*回馈消息*/
    private String message;

    private String COUNT;

    private T DATA;

    public String getCOUNT() {
        return COUNT;
    }

    public void setCOUNT(String COUNT) {
        this.COUNT = COUNT;
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

    public T getDATA() {
        return DATA;
    }

    public void setDATA(T DATA) {
        this.DATA = DATA;
    }
}
