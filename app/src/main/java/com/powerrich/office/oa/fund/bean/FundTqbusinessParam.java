package com.powerrich.office.oa.fund.bean;

/**
 * @author PC
 * @date 2019/04/20 15:28
 */
public class FundTqbusinessParam {

    /**
     * bczywlsh : 9
     * beizhu : 10
     * blqd : 35
     * bpmid : 41
     * czbz : 01
     * dbrsjhm : 13
     * dlrlx : 14
     * dwmc : 15
     * dwzh : 16
     * fy : 17
     * grzh : 18
     * grzhye : 19
     * htqdrq : 20
     * jgbm : 36
     * jsfs : df
     * jsid : 22
     * jspt : 01
     * khjgbm : 23
     * lcbz : 0
     * sfjs : 1
     * skyh : 24
     * tqclbh : 25
     * tqfs : 02
     * tqjehj : 26
     * tqlx : 0
     * tqlxbm : 27
     * tqyhzh : 28
     * tqyy : 0201
     * userid : 0
     * xingming : 29
     * yhbm :
     * ywfl : 02
     * ywfsrq : 30
     * ywlb : 99
     * ywlsh : 30
     * zjhm : 32
     * zrzxqc : 33
     * zxbm : 40
     */

    private String bczywlsh = "";
    private String beizhu = "";
    private String blqd = "app";
    private String bpmid;//流程id
    private String czbz = "01";
    private String dbrsjhm = " ";
    private String dlrlx = " ";
    private String dwmc = FundBean.dwmc;
    private String dwzh = FundBean.dwzh;
    private String fy;//业务描述 姓名+提取+金额
    private String grzh = FundBean.grzh;
    private String grzhye = FundBean.grzhye;
    private String htqdrq = " ";
    private String jgbm = "0101";
    private String jsfs = "df";
    private String jsid = "";
    private String jspt = "01";
    private String khjgbm = "0101";
    private String lcbz = "0";
    private String sfjs = "1";
    private String skyh = FundBean.khyh;
    private String tqclbh = "";
    private String tqfs = "02";
    private String tqjehj = (Double.parseDouble(FundBean.grzhye) +Double.parseDouble(FundBean.zjlx))+"" ;
    private String tqlx = FundBean.zjlx;
    private String tqlxbm = " ";
    private String tqyhzh = FundBean.yhzh;
    private String tqyy = "0201";
    private String userid = "0";
    private String xingming = FundBean.xingming;
    private String yhbm = " ";
    private String ywfl = "02";
    private String ywfsrq;//业务发生日期
    private String ywlb = "12";
    private String ywlsh;//业务流水号
    private String zjhm = FundBean.zjhm;
    private String zrzxqc = FundBean.xingming;
    private String zxbm = "0101";

    public void setBczywlsh(String bczywlsh) {
        this.bczywlsh = bczywlsh;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public void setBlqd(String blqd) {
        this.blqd = blqd;
    }

    public void setBpmid(String bpmid) {
        this.bpmid = bpmid;
    }

    public void setCzbz(String czbz) {
        this.czbz = czbz;
    }

    public void setDbrsjhm(String dbrsjhm) {
        this.dbrsjhm = dbrsjhm;
    }

    public void setDlrlx(String dlrlx) {
        this.dlrlx = dlrlx;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public void setDwzh(String dwzh) {
        this.dwzh = dwzh;
    }

    public void setFy(String fy) {
        this.fy = fy;
    }

    public void setGrzh(String grzh) {
        this.grzh = grzh;
    }

    public void setGrzhye(String grzhye) {
        this.grzhye = grzhye;
    }

    public void setHtqdrq(String htqdrq) {
        this.htqdrq = htqdrq;
    }

    public void setJgbm(String jgbm) {
        this.jgbm = jgbm;
    }

    public void setJsfs(String jsfs) {
        this.jsfs = jsfs;
    }

    public void setJsid(String jsid) {
        this.jsid = jsid;
    }

    public void setJspt(String jspt) {
        this.jspt = jspt;
    }

    public void setKhjgbm(String khjgbm) {
        this.khjgbm = khjgbm;
    }

    public void setLcbz(String lcbz) {
        this.lcbz = lcbz;
    }

    public void setSfjs(String sfjs) {
        this.sfjs = sfjs;
    }

    public void setSkyh(String skyh) {
        this.skyh = skyh;
    }

    public void setTqclbh(String tqclbh) {
        this.tqclbh = tqclbh;
    }

    public void setTqfs(String tqfs) {
        this.tqfs = tqfs;
    }

    public void setTqjehj(String tqjehj) {
        this.tqjehj = tqjehj;
    }

    public void setTqlx(String tqlx) {
        this.tqlx = tqlx;
    }

    public void setTqlxbm(String tqlxbm) {
        this.tqlxbm = tqlxbm;
    }

    public void setTqyhzh(String tqyhzh) {
        this.tqyhzh = tqyhzh;
    }

    public void setTqyy(String tqyy) {
        this.tqyy = tqyy;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setXingming(String xingming) {
        this.xingming = xingming;
    }

    public void setYhbm(String yhbm) {
        this.yhbm = yhbm;
    }

    public void setYwfl(String ywfl) {
        this.ywfl = ywfl;
    }

    public void setYwfsrq(String ywfsrq) {
        this.ywfsrq = ywfsrq;
    }

    public void setYwlb(String ywlb) {
        this.ywlb = ywlb;
    }

    public void setYwlsh(String ywlsh) {
        this.ywlsh = ywlsh;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public void setZrzxqc(String zrzxqc) {
        this.zrzxqc = zrzxqc;
    }

    public void setZxbm(String zxbm) {
        this.zxbm = zxbm;
    }

    public String getBczywlsh() {
        return bczywlsh == null ? "" : bczywlsh;
    }

    public String getBeizhu() {
        return beizhu == null ? "" : beizhu;
    }

    public String getBlqd() {
        return blqd == null ? "" : blqd;
    }

    public String getBpmid() {
        return bpmid == null ? "" : bpmid;
    }

    public String getCzbz() {
        return czbz == null ? "" : czbz;
    }

    public String getDbrsjhm() {
        return dbrsjhm == null ? "" : dbrsjhm;
    }

    public String getDlrlx() {
        return dlrlx == null ? "" : dlrlx;
    }

    public String getDwmc() {
        return dwmc == null ? "" : dwmc;
    }

    public String getDwzh() {
        return dwzh == null ? "" : dwzh;
    }

    public String getFy() {
        return fy == null ? "" : fy;
    }

    public String getGrzh() {
        return grzh == null ? "" : grzh;
    }

    public String getGrzhye() {
        return grzhye == null ? "" : grzhye;
    }

    public String getHtqdrq() {
        return htqdrq == null ? "" : htqdrq;
    }

    public String getJgbm() {
        return jgbm == null ? "" : jgbm;
    }

    public String getJsfs() {
        return jsfs == null ? "" : jsfs;
    }

    public String getJsid() {
        return jsid == null ? "" : jsid;
    }

    public String getJspt() {
        return jspt == null ? "" : jspt;
    }

    public String getKhjgbm() {
        return khjgbm == null ? "" : khjgbm;
    }

    public String getLcbz() {
        return lcbz == null ? "" : lcbz;
    }

    public String getSfjs() {
        return sfjs == null ? "" : sfjs;
    }

    public String getSkyh() {
        return skyh == null ? "" : skyh;
    }

    public String getTqclbh() {
        return tqclbh == null ? "" : tqclbh;
    }

    public String getTqfs() {
        return tqfs == null ? "" : tqfs;
    }

    public String getTqjehj() {
        return tqjehj == null ? "" : tqjehj;
    }

    public String getTqlx() {
        return tqlx;
    }

    public String getTqlxbm() {
        return tqlxbm == null ? "" : tqlxbm;
    }

    public String getTqyhzh() {
        return tqyhzh == null ? "" : tqyhzh;
    }

    public String getTqyy() {
        return tqyy == null ? "" : tqyy;
    }

    public String getUserid() {
        return userid;
    }

    public String getXingming() {
        return xingming == null ? "" : xingming;
    }

    public String getYhbm() {
        return yhbm == null ? "" : yhbm;
    }

    public String getYwfl() {
        return ywfl == null ? "" : ywfl;
    }

    public String getYwfsrq() {
        return ywfsrq == null ? "" : ywfsrq;
    }

    public String getYwlb() {
        return ywlb == null ? "" : ywlb;
    }

    public String getYwlsh() {
        return ywlsh == null ? "" : ywlsh;
    }

    public String getZjhm() {
        return zjhm == null ? "" : zjhm;
    }

    public String getZrzxqc() {
        return zrzxqc == null ? "" : zrzxqc;
    }

    public String getZxbm() {
        return zxbm == null ? "" : zxbm;
    }
}
