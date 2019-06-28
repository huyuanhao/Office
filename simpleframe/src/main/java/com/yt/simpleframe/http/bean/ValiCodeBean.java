package com.yt.simpleframe.http.bean;

/**
 * Created by fanliang on 18/1/3.
 */

public class ValiCodeBean extends BaseBean {

    private String vali_code;

    public String getVali_code() {
        return vali_code;
    }

    public void setVali_code(String vali_code) {
        this.vali_code = vali_code;
    }

    @Override
    public String toString() {
        return "ValiCodeBean{" +
                "vali_code='" + vali_code + '\'' +
                '}';
    }
}
