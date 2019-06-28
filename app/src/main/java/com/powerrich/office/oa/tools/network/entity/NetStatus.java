package com.powerrich.office.oa.tools.network.entity;

import com.google.gson.Gson;

/**
 * @author Administrator
 * @date 2018/10/31 10:41
 */
public class NetStatus {

    /**
     * success : true
     * msg : 保存成功
     * data :
     * code : 200
     */

    private boolean success;
    private String msg;
    private Object data;
    private String code;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
