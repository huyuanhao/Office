package com.powerrich.office.oa.fund.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PC
 * @date 2019/04/24 11:10
 */
public class FundTqyhxx {

    /**
     * success : false
     * msg : null
     * totalcount : 0
     * results : null
     * erros : null
     * vdMapList : null
     * data : [{"id":null,"grzh":null,"zhhm":"陈梦帆","yhzh":"6215581506002157213","ssyh":"0101","khyh":"工行四海支行","yhhh":"","sszh":"0001","count":0,"page":0,"size":0,"userid":0,"blqd":null,"zxbm":null,"jgbm":null,"khbh":null,"zhbh":null,"ywfl":null,"ywlb":null,"ffbm":null,"cxlx":null,"ret":0,"msg":null,"lmkbz":0}]
     */


    private String success;
    private String msg;
    private String totalcount;
    private String results;
    private String erros;
    private String vdMapList;
    private List<DataBean> data;

    public String getSuccess() {
        return success == null ? "" : success;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public String getTotalcount() {
        return totalcount == null ? "" : totalcount;
    }

    public String getResults() {
        return results == null ? "" : results;
    }

    public String getErros() {
        return erros == null ? "" : erros;
    }

    public String getVdMapList() {
        return vdMapList == null ? "" : vdMapList;
    }

    public List<DataBean> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public void setErros(String erros) {
        this.erros = erros;
    }

    public void setVdMapList(String vdMapList) {
        this.vdMapList = vdMapList;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : null
         * grzh : null
         * zhhm : 陈梦帆
         * yhzh : 6215581506002157213
         * ssyh : 0101
         * khyh : 工行四海支行
         * yhhh :
         * sszh : 0001
         * count : 0
         * page : 0
         * size : 0
         * userid : 0
         * blqd : null
         * zxbm : null
         * jgbm : null
         * khbh : null
         * zhbh : null
         * ywfl : null
         * ywlb : null
         * ffbm : null
         * cxlx : null
         * ret : 0
         * msg : null
         * lmkbz : 0
         */

        private String id;
        private String grzh;
        private String zhhm;
        private String yhzh;
        private String ssyh;
        private String khyh;
        private String yhhh;
        private String sszh;
        private String count;
        private String page;
        private String size;
        private String userid;
        private String blqd;
        private String zxbm;
        private String jgbm;
        private String khbh;
        private String zhbh;
        private String ywfl;
        private String ywlb;
        private String ffbm;
        private String cxlx;
        private String ret;
        private String msg;
        private String lmkbz;

        public String getId() {
            return id == null ? "" : id;
        }

        public String getGrzh() {
            return grzh == null ? "" : grzh;
        }

        public String getZhhm() {
            return zhhm == null ? "" : zhhm;
        }

        public String getYhzh() {
            return yhzh == null ? "" : yhzh;
        }

        public String getSsyh() {
            return ssyh == null ? "" : ssyh;
        }

        public String getKhyh() {
            return khyh == null ? "" : khyh;
        }

        public String getYhhh() {
            return yhhh == null ? "" : yhhh;
        }

        public String getSszh() {
            return sszh == null ? "" : sszh;
        }

        public String getCount() {
            return count == null ? "" : count;
        }

        public String getPage() {
            return page == null ? "" : page;
        }

        public String getSize() {
            return size == null ? "" : size;
        }

        public String getUserid() {
            return userid == null ? "" : userid;
        }

        public String getBlqd() {
            return blqd == null ? "" : blqd;
        }

        public String getZxbm() {
            return zxbm == null ? "" : zxbm;
        }

        public String getJgbm() {
            return jgbm == null ? "" : jgbm;
        }

        public String getKhbh() {
            return khbh == null ? "" : khbh;
        }

        public String getZhbh() {
            return zhbh == null ? "" : zhbh;
        }

        public String getYwfl() {
            return ywfl == null ? "" : ywfl;
        }

        public String getYwlb() {
            return ywlb == null ? "" : ywlb;
        }

        public String getFfbm() {
            return ffbm == null ? "" : ffbm;
        }

        public String getCxlx() {
            return cxlx == null ? "" : cxlx;
        }

        public String getRet() {
            return ret == null ? "" : ret;
        }

        public String getMsg() {
            return msg == null ? "" : msg;
        }

        public String getLmkbz() {
            return lmkbz == null ? "" : lmkbz;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setGrzh(String grzh) {
            this.grzh = grzh;
        }

        public void setZhhm(String zhhm) {
            this.zhhm = zhhm;
        }

        public void setYhzh(String yhzh) {
            this.yhzh = yhzh;
        }

        public void setSsyh(String ssyh) {
            this.ssyh = ssyh;
        }

        public void setKhyh(String khyh) {
            this.khyh = khyh;
        }

        public void setYhhh(String yhhh) {
            this.yhhh = yhhh;
        }

        public void setSszh(String sszh) {
            this.sszh = sszh;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public void setBlqd(String blqd) {
            this.blqd = blqd;
        }

        public void setZxbm(String zxbm) {
            this.zxbm = zxbm;
        }

        public void setJgbm(String jgbm) {
            this.jgbm = jgbm;
        }

        public void setKhbh(String khbh) {
            this.khbh = khbh;
        }

        public void setZhbh(String zhbh) {
            this.zhbh = zhbh;
        }

        public void setYwfl(String ywfl) {
            this.ywfl = ywfl;
        }

        public void setYwlb(String ywlb) {
            this.ywlb = ywlb;
        }

        public void setFfbm(String ffbm) {
            this.ffbm = ffbm;
        }

        public void setCxlx(String cxlx) {
            this.cxlx = cxlx;
        }

        public void setRet(String ret) {
            this.ret = ret;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void setLmkbz(String lmkbz) {
            this.lmkbz = lmkbz;
        }
    }
}
