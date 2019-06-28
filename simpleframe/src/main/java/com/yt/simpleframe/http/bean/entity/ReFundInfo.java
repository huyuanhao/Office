package com.yt.simpleframe.http.bean.entity;

/**
 * 还款明细、还款计划、 贷款逾期查询
 * @author AlienChao
 * @date 2019/04/20 14:28
 */
public class ReFundInfo  {


    /**
     * bjye : 600000
     * chbj : 0
     * chfx : 0
     * chlx : 0
     * hkny :
     * hkqs : 0
     * hkrq : 2018-08-13
     * yhbjhj : 0
     * yhfxhj : 0
     * yhlxhj : 0
     * ywzy : 贷款发放
     */

    private String bjye;
    private String chbj;
    private String chfx;
    private String chlx;
    private String hkny;
    private String hkqs;
    private String hkrq;
    private String yhbjhj;
    private String yhfxhj;
    private String yhlxhj;
    private String ywzy;

    public String getBjye() {
        return bjye == null ? "" : bjye;
    }

    public String getChbj() {
        return chbj == null ? "" : chbj;
    }

    public String getChfx() {
        return chfx == null ? "" : chfx;
    }

    public String getChlx() {
        return chlx == null ? "" : chlx;
    }

    public String getHkny() {
        return hkny == null ? "" : hkny;
    }

    public String getHkqs() {
        return hkqs == null ? "" : hkqs;
    }

    public String getHkrq() {
        return hkrq == null ? "" : hkrq;
    }

    public String getYhbjhj() {
        return yhbjhj == null ? "" : yhbjhj;
    }

    public String getYhfxhj() {
        return yhfxhj == null ? "" : yhfxhj;
    }

    public String getYhlxhj() {
        return yhlxhj == null ? "" : yhlxhj;
    }

    public String getYwzy() {
        return ywzy == null ? "" : ywzy;
    }

    public void setBjye(String bjye) {
        this.bjye = bjye;
    }

    public void setChbj(String chbj) {
        this.chbj = chbj;
    }

    public void setChfx(String chfx) {
        this.chfx = chfx;
    }

    public void setChlx(String chlx) {
        this.chlx = chlx;
    }

    public void setHkny(String hkny) {
        this.hkny = hkny;
    }

    public void setHkqs(String hkqs) {
        this.hkqs = hkqs;
    }

    public void setHkrq(String hkrq) {
        this.hkrq = hkrq;
    }

    public void setYhbjhj(String yhbjhj) {
        this.yhbjhj = yhbjhj;
    }

    public void setYhfxhj(String yhfxhj) {
        this.yhfxhj = yhfxhj;
    }

    public void setYhlxhj(String yhlxhj) {
        this.yhlxhj = yhlxhj;
    }

    public void setYwzy(String ywzy) {
        this.ywzy = ywzy;
    }
}
