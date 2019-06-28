package com.powerrich.office.oa.fund.bean;

import java.util.List;

/**
 * @author PC
 * @date 2019/06/24 16:29
 */
public class FundLhhBean {

    /**
     * success : false
     * msg : null
     * totalcount : 1
     * results : null
     * erros : null
     * vdMapList : null
     * data : [{"page":0,"size":0,"bpmid":null,"dwzh":null,"grzh":null,"xingming":null,"zjlx":null,"zjhm":null,"grjcjs":0,"gryjce":0,"dwyjce":0,"jcblmc":null,"xingbie":null,"csny":null,"gddhhm":null,"sjhm":null,"yzbm":null,"hyzk":null,"zhiye":null,"zhichen":null,"zhiwu":null,"xueli":null,"jtzz":null,"jtysr":0,"grckzhkhyhmc":"中国建设银行股份有限公司鹰潭市分行","grckzhhm":null,"grckzhkhyhdm":"105427000000","msg":null,"grlbbm":null,"ywqd":null,"jcblbm":null,"userid":0,"mret":null,"yjce":0,"yjce1":null,"end":0,"only":null,"ret":null,"grbh":null,"cnt":0,"dwmc":null,"grzhzt":null,"ycje":0,"blqd":null,"xm":null,"sftjspzt":null,"dwbh":null,"grjcl":null,"dwjcl":null,"bpmid1":null,"xmmc":null,"oldvalue":null,"newvalue":null,"ywlsh":null,"gryjce1":null,"dwyjce1":null,"ret1":0,"count":0,"spzt":0,"processunid":null,"processinstanceid":null,"zxbm":null,"zjbzxbm":null,"jgbm":null,"khbh":null,"zhbh":null,"ywfl":null,"ywlb":null,"ffbm":null,"bgrq":null,"jbrxm":null,"jbrsjhm":null,"lrczy":null,"lrczyid":null,"czfl":null,"ywzy":null,"ywczbs":null,"ywczje":0,"grjcjsHj":0,"gryjceHj":0,"dwyjceHj":0,"jtysrHj":0,"zfbzh":null,"zzjglx":null,"cxlx":null,"yhbm":null,"bgyd":null,"jcehjHj":0,"zmxyfs":0}]
     */

    private boolean success;
    private String msg;
    private int totalcount;
    private String results;
    private String erros;
    private String vdMapList;
    private List< DataBean > data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getErros() {
        return erros;
    }

    public void setErros(String erros) {
        this.erros = erros;
    }

    public String getVdMapList() {
        return vdMapList;
    }

    public void setVdMapList(String vdMapList) {
        this.vdMapList = vdMapList;
    }

    public List< DataBean > getData() {
        return data;
    }

    public void setData(List< DataBean > data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * page : 0
         * size : 0
         * bpmid : null
         * dwzh : null
         * grzh : null
         * xingming : null
         * zjlx : null
         * zjhm : null
         * grjcjs : 0.0
         * gryjce : 0.0
         * dwyjce : 0.0
         * jcblmc : null
         * xingbie : null
         * csny : null
         * gddhhm : null
         * sjhm : null
         * yzbm : null
         * hyzk : null
         * zhiye : null
         * zhichen : null
         * zhiwu : null
         * xueli : null
         * jtzz : null
         * jtysr : 0.0
         * grckzhkhyhmc : 中国建设银行股份有限公司鹰潭市分行
         * grckzhhm : null
         * grckzhkhyhdm : 105427000000
         * msg : null
         * grlbbm : null
         * ywqd : null
         * jcblbm : null
         * userid : 0
         * mret : null
         * yjce : 0.0
         * yjce1 : null
         * end : 0
         * only : null
         * ret : null
         * grbh : null
         * cnt : 0
         * dwmc : null
         * grzhzt : null
         * ycje : 0.0
         * blqd : null
         * xm : null
         * sftjspzt : null
         * dwbh : null
         * grjcl : null
         * dwjcl : null
         * bpmid1 : null
         * xmmc : null
         * oldvalue : null
         * newvalue : null
         * ywlsh : null
         * gryjce1 : null
         * dwyjce1 : null
         * ret1 : 0
         * count : 0
         * spzt : 0
         * processunid : null
         * processinstanceid : null
         * zxbm : null
         * zjbzxbm : null
         * jgbm : null
         * khbh : null
         * zhbh : null
         * ywfl : null
         * ywlb : null
         * ffbm : null
         * bgrq : null
         * jbrxm : null
         * jbrsjhm : null
         * lrczy : null
         * lrczyid : null
         * czfl : null
         * ywzy : null
         * ywczbs : null
         * ywczje : 0.0
         * grjcjsHj : 0.0
         * gryjceHj : 0.0
         * dwyjceHj : 0.0
         * jtysrHj : 0.0
         * zfbzh : null
         * zzjglx : null
         * cxlx : null
         * yhbm : null
         * bgyd : null
         * jcehjHj : 0.0
         * zmxyfs : 0
         */

        private int page;
        private int size;
        private String bpmid;
        private String dwzh;
        private String grzh;
        private String xingming;
        private String zjlx;
        private String zjhm;
        private double grjcjs;
        private double gryjce;
        private double dwyjce;
        private String jcblmc;
        private String xingbie;
        private String csny;
        private String gddhhm;
        private String sjhm;
        private String yzbm;
        private String hyzk;
        private String zhiye;
        private String zhichen;
        private String zhiwu;
        private String xueli;
        private String jtzz;
        private double jtysr;
        private String grckzhkhyhmc;
        private String grckzhhm;
        private String grckzhkhyhdm;
        private String msg;
        private String grlbbm;
        private String ywqd;
        private String jcblbm;
        private int userid;
        private String mret;
        private double yjce;
        private String yjce1;
        private int end;
        private String only;
        private String ret;
        private String grbh;
        private int cnt;
        private String dwmc;
        private String grzhzt;
        private double ycje;
        private String blqd;
        private String xm;
        private String sftjspzt;
        private String dwbh;
        private String grjcl;
        private String dwjcl;
        private String bpmid1;
        private String xmmc;
        private String oldvalue;
        private String newvalue;
        private String ywlsh;
        private String gryjce1;
        private String dwyjce1;
        private int ret1;
        private int count;
        private int spzt;
        private String processunid;
        private String processinstanceid;
        private String zxbm;
        private String zjbzxbm;
        private String jgbm;
        private String khbh;
        private String zhbh;
        private String ywfl;
        private String ywlb;
        private String ffbm;
        private String bgrq;
        private String jbrxm;
        private String jbrsjhm;
        private String lrczy;
        private String lrczyid;
        private String czfl;
        private String ywzy;
        private String ywczbs;
        private double ywczje;
        private double grjcjsHj;
        private double gryjceHj;
        private double dwyjceHj;
        private double jtysrHj;
        private String zfbzh;
        private String zzjglx;
        private String cxlx;
        private String yhbm;
        private String bgyd;
        private double jcehjHj;
        private int zmxyfs;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getBpmid() {
            return bpmid;
        }

        public void setBpmid(String bpmid) {
            this.bpmid = bpmid;
        }

        public String getDwzh() {
            return dwzh;
        }

        public void setDwzh(String dwzh) {
            this.dwzh = dwzh;
        }

        public String getGrzh() {
            return grzh;
        }

        public void setGrzh(String grzh) {
            this.grzh = grzh;
        }

        public String getXingming() {
            return xingming;
        }

        public void setXingming(String xingming) {
            this.xingming = xingming;
        }

        public String getZjlx() {
            return zjlx;
        }

        public void setZjlx(String zjlx) {
            this.zjlx = zjlx;
        }

        public String getZjhm() {
            return zjhm;
        }

        public void setZjhm(String zjhm) {
            this.zjhm = zjhm;
        }

        public double getGrjcjs() {
            return grjcjs;
        }

        public void setGrjcjs(double grjcjs) {
            this.grjcjs = grjcjs;
        }

        public double getGryjce() {
            return gryjce;
        }

        public void setGryjce(double gryjce) {
            this.gryjce = gryjce;
        }

        public double getDwyjce() {
            return dwyjce;
        }

        public void setDwyjce(double dwyjce) {
            this.dwyjce = dwyjce;
        }

        public String getJcblmc() {
            return jcblmc;
        }

        public void setJcblmc(String jcblmc) {
            this.jcblmc = jcblmc;
        }

        public String getXingbie() {
            return xingbie;
        }

        public void setXingbie(String xingbie) {
            this.xingbie = xingbie;
        }

        public String getCsny() {
            return csny;
        }

        public void setCsny(String csny) {
            this.csny = csny;
        }

        public String getGddhhm() {
            return gddhhm;
        }

        public void setGddhhm(String gddhhm) {
            this.gddhhm = gddhhm;
        }

        public String getSjhm() {
            return sjhm;
        }

        public void setSjhm(String sjhm) {
            this.sjhm = sjhm;
        }

        public String getYzbm() {
            return yzbm;
        }

        public void setYzbm(String yzbm) {
            this.yzbm = yzbm;
        }

        public String getHyzk() {
            return hyzk;
        }

        public void setHyzk(String hyzk) {
            this.hyzk = hyzk;
        }

        public String getZhiye() {
            return zhiye;
        }

        public void setZhiye(String zhiye) {
            this.zhiye = zhiye;
        }

        public String getZhichen() {
            return zhichen;
        }

        public void setZhichen(String zhichen) {
            this.zhichen = zhichen;
        }

        public String getZhiwu() {
            return zhiwu;
        }

        public void setZhiwu(String zhiwu) {
            this.zhiwu = zhiwu;
        }

        public String getXueli() {
            return xueli;
        }

        public void setXueli(String xueli) {
            this.xueli = xueli;
        }

        public String getJtzz() {
            return jtzz;
        }

        public void setJtzz(String jtzz) {
            this.jtzz = jtzz;
        }

        public double getJtysr() {
            return jtysr;
        }

        public void setJtysr(double jtysr) {
            this.jtysr = jtysr;
        }

        public String getGrckzhkhyhmc() {
            return grckzhkhyhmc;
        }

        public void setGrckzhkhyhmc(String grckzhkhyhmc) {
            this.grckzhkhyhmc = grckzhkhyhmc;
        }

        public String getGrckzhhm() {
            return grckzhhm;
        }

        public void setGrckzhhm(String grckzhhm) {
            this.grckzhhm = grckzhhm;
        }

        public String getGrckzhkhyhdm() {
            return grckzhkhyhdm;
        }

        public void setGrckzhkhyhdm(String grckzhkhyhdm) {
            this.grckzhkhyhdm = grckzhkhyhdm;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getGrlbbm() {
            return grlbbm;
        }

        public void setGrlbbm(String grlbbm) {
            this.grlbbm = grlbbm;
        }

        public String getYwqd() {
            return ywqd;
        }

        public void setYwqd(String ywqd) {
            this.ywqd = ywqd;
        }

        public String getJcblbm() {
            return jcblbm;
        }

        public void setJcblbm(String jcblbm) {
            this.jcblbm = jcblbm;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getMret() {
            return mret;
        }

        public void setMret(String mret) {
            this.mret = mret;
        }

        public double getYjce() {
            return yjce;
        }

        public void setYjce(double yjce) {
            this.yjce = yjce;
        }

        public String getYjce1() {
            return yjce1;
        }

        public void setYjce1(String yjce1) {
            this.yjce1 = yjce1;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public String getOnly() {
            return only;
        }

        public void setOnly(String only) {
            this.only = only;
        }

        public String getRet() {
            return ret;
        }

        public void setRet(String ret) {
            this.ret = ret;
        }

        public String getGrbh() {
            return grbh;
        }

        public void setGrbh(String grbh) {
            this.grbh = grbh;
        }

        public int getCnt() {
            return cnt;
        }

        public void setCnt(int cnt) {
            this.cnt = cnt;
        }

        public String getDwmc() {
            return dwmc;
        }

        public void setDwmc(String dwmc) {
            this.dwmc = dwmc;
        }

        public String getGrzhzt() {
            return grzhzt;
        }

        public void setGrzhzt(String grzhzt) {
            this.grzhzt = grzhzt;
        }

        public double getYcje() {
            return ycje;
        }

        public void setYcje(double ycje) {
            this.ycje = ycje;
        }

        public String getBlqd() {
            return blqd;
        }

        public void setBlqd(String blqd) {
            this.blqd = blqd;
        }

        public String getXm() {
            return xm;
        }

        public void setXm(String xm) {
            this.xm = xm;
        }

        public String getSftjspzt() {
            return sftjspzt;
        }

        public void setSftjspzt(String sftjspzt) {
            this.sftjspzt = sftjspzt;
        }

        public String getDwbh() {
            return dwbh;
        }

        public void setDwbh(String dwbh) {
            this.dwbh = dwbh;
        }

        public String getGrjcl() {
            return grjcl;
        }

        public void setGrjcl(String grjcl) {
            this.grjcl = grjcl;
        }

        public String getDwjcl() {
            return dwjcl;
        }

        public void setDwjcl(String dwjcl) {
            this.dwjcl = dwjcl;
        }

        public String getBpmid1() {
            return bpmid1;
        }

        public void setBpmid1(String bpmid1) {
            this.bpmid1 = bpmid1;
        }

        public String getXmmc() {
            return xmmc;
        }

        public void setXmmc(String xmmc) {
            this.xmmc = xmmc;
        }

        public String getOldvalue() {
            return oldvalue;
        }

        public void setOldvalue(String oldvalue) {
            this.oldvalue = oldvalue;
        }

        public String getNewvalue() {
            return newvalue;
        }

        public void setNewvalue(String newvalue) {
            this.newvalue = newvalue;
        }

        public String getYwlsh() {
            return ywlsh;
        }

        public void setYwlsh(String ywlsh) {
            this.ywlsh = ywlsh;
        }

        public String getGryjce1() {
            return gryjce1;
        }

        public void setGryjce1(String gryjce1) {
            this.gryjce1 = gryjce1;
        }

        public String getDwyjce1() {
            return dwyjce1;
        }

        public void setDwyjce1(String dwyjce1) {
            this.dwyjce1 = dwyjce1;
        }

        public int getRet1() {
            return ret1;
        }

        public void setRet1(int ret1) {
            this.ret1 = ret1;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getSpzt() {
            return spzt;
        }

        public void setSpzt(int spzt) {
            this.spzt = spzt;
        }

        public String getProcessunid() {
            return processunid;
        }

        public void setProcessunid(String processunid) {
            this.processunid = processunid;
        }

        public String getProcessinstanceid() {
            return processinstanceid;
        }

        public void setProcessinstanceid(String processinstanceid) {
            this.processinstanceid = processinstanceid;
        }

        public String getZxbm() {
            return zxbm;
        }

        public void setZxbm(String zxbm) {
            this.zxbm = zxbm;
        }

        public String getZjbzxbm() {
            return zjbzxbm;
        }

        public void setZjbzxbm(String zjbzxbm) {
            this.zjbzxbm = zjbzxbm;
        }

        public String getJgbm() {
            return jgbm;
        }

        public void setJgbm(String jgbm) {
            this.jgbm = jgbm;
        }

        public String getKhbh() {
            return khbh;
        }

        public void setKhbh(String khbh) {
            this.khbh = khbh;
        }

        public String getZhbh() {
            return zhbh;
        }

        public void setZhbh(String zhbh) {
            this.zhbh = zhbh;
        }

        public String getYwfl() {
            return ywfl;
        }

        public void setYwfl(String ywfl) {
            this.ywfl = ywfl;
        }

        public String getYwlb() {
            return ywlb;
        }

        public void setYwlb(String ywlb) {
            this.ywlb = ywlb;
        }

        public String getFfbm() {
            return ffbm;
        }

        public void setFfbm(String ffbm) {
            this.ffbm = ffbm;
        }

        public String getBgrq() {
            return bgrq;
        }

        public void setBgrq(String bgrq) {
            this.bgrq = bgrq;
        }

        public String getJbrxm() {
            return jbrxm;
        }

        public void setJbrxm(String jbrxm) {
            this.jbrxm = jbrxm;
        }

        public String getJbrsjhm() {
            return jbrsjhm;
        }

        public void setJbrsjhm(String jbrsjhm) {
            this.jbrsjhm = jbrsjhm;
        }

        public String getLrczy() {
            return lrczy;
        }

        public void setLrczy(String lrczy) {
            this.lrczy = lrczy;
        }

        public String getLrczyid() {
            return lrczyid;
        }

        public void setLrczyid(String lrczyid) {
            this.lrczyid = lrczyid;
        }

        public String getCzfl() {
            return czfl;
        }

        public void setCzfl(String czfl) {
            this.czfl = czfl;
        }

        public String getYwzy() {
            return ywzy;
        }

        public void setYwzy(String ywzy) {
            this.ywzy = ywzy;
        }

        public String getYwczbs() {
            return ywczbs;
        }

        public void setYwczbs(String ywczbs) {
            this.ywczbs = ywczbs;
        }

        public double getYwczje() {
            return ywczje;
        }

        public void setYwczje(double ywczje) {
            this.ywczje = ywczje;
        }

        public double getGrjcjsHj() {
            return grjcjsHj;
        }

        public void setGrjcjsHj(double grjcjsHj) {
            this.grjcjsHj = grjcjsHj;
        }

        public double getGryjceHj() {
            return gryjceHj;
        }

        public void setGryjceHj(double gryjceHj) {
            this.gryjceHj = gryjceHj;
        }

        public double getDwyjceHj() {
            return dwyjceHj;
        }

        public void setDwyjceHj(double dwyjceHj) {
            this.dwyjceHj = dwyjceHj;
        }

        public double getJtysrHj() {
            return jtysrHj;
        }

        public void setJtysrHj(double jtysrHj) {
            this.jtysrHj = jtysrHj;
        }

        public String getZfbzh() {
            return zfbzh;
        }

        public void setZfbzh(String zfbzh) {
            this.zfbzh = zfbzh;
        }

        public String getZzjglx() {
            return zzjglx;
        }

        public void setZzjglx(String zzjglx) {
            this.zzjglx = zzjglx;
        }

        public String getCxlx() {
            return cxlx;
        }

        public void setCxlx(String cxlx) {
            this.cxlx = cxlx;
        }

        public String getYhbm() {
            return yhbm;
        }

        public void setYhbm(String yhbm) {
            this.yhbm = yhbm;
        }

        public String getBgyd() {
            return bgyd;
        }

        public void setBgyd(String bgyd) {
            this.bgyd = bgyd;
        }

        public double getJcehjHj() {
            return jcehjHj;
        }

        public void setJcehjHj(double jcehjHj) {
            this.jcehjHj = jcehjHj;
        }

        public int getZmxyfs() {
            return zmxyfs;
        }

        public void setZmxyfs(int zmxyfs) {
            this.zmxyfs = zmxyfs;
        }
    }
}
