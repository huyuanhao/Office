package com.yt.simpleframe.http.bean;

import java.io.Serializable;

/**
 * Created by fanliang on 18/1/3.
 */

public class BaseBean implements Serializable {
    /*版本说明*/
    private String code;
    /*回馈消息*/
    private String message;

    private String COUNT;

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

}
