package com.yt.simpleframe.http.bean.entity;

/**
 * 借款人关系查询
 * @author AlienChao
 * @date 2019/04/26 17:08
 */
public class ReFundJkrgxInfo {
    private String jkrgx;
    private String xm;
    private String zjhm;
    private String lxdh;
    private String grzhye;


    public String getJkrgx() {
        return jkrgx == null ? "" : jkrgx;
    }

    public String getXm() {
        return xm == null ? "" : xm;
    }

    public String getZjhm() {
        return zjhm == null ? "" : zjhm;
    }

    public String getLxdh() {
        return lxdh == null ? "" : lxdh;
    }

    public String getGrzhye() {
        return grzhye == null ? "" : grzhye;
    }

    public void setJkrgx(String jkrgx) {
        this.jkrgx = jkrgx;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public void setGrzhye(String grzhye) {
        this.grzhye = grzhye;
    }
}
