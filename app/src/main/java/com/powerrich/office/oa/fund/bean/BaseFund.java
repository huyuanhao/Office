package com.powerrich.office.oa.fund.bean;

import java.util.List;

/**
 * @author PC
 * @date 2019/04/12 16:35
 */
public class BaseFund<T> {

    /**
     * CURRENTROWS : 1
     * DATA : [{"dkffe":"490000","dkffrq":"2018-05-11","dkhkfs":"等额本息","dkjqrq":"","dkll":"3.25","dklx":"公积金贷款","dkqs":"240","dksqrq":"2018-05-03 00:00:00.0","dkye":"475299.97","dkzt":"正常还款","dqyqbj":"0","dqyqcs":"0","dqyqlx":"0","fwjzmj":"158.03","fwzj":"703200","fwzl":"中川磨仂洲E16幢1单元5层501号","fxze":"0","gtjkrdwmc":"余江县春涛镇春涛中学","gtjkrgjjzh":"000004481958","gtjkrxm":"徐卫平","gtjkrzjhm":"360622198007121913","hkzh":"6215581506002157213","hsbjze":"14700.03","hslxze":"13092.57","htdkje":"490000","jkhtbh":"20180502030002","jkrdwmc":"余江县第四中学","jkrgjjzh":"000008481092","jkrxm":"陈梦帆","jkrzjh":"360622198806250041","sfdc":"是","shqs":"10","ydhkr":"11","yjqrq":"2038-05-11","zhkhyhmc":""}]
     */

    private int CURRENTROWS;
    private List<T> DATA;

    public int getCURRENTROWS() {
        return CURRENTROWS;
    }

    public void setCURRENTROWS(int CURRENTROWS) {
        this.CURRENTROWS = CURRENTROWS;
    }

    public List<T> getDATA() {
        return DATA;
    }

    public void setDATA(List<T> DATA) {
        this.DATA = DATA;
    }

    public static class DATABean {
        /**
         * dkffe : 490000
         * dkffrq : 2018-05-11
         * dkhkfs : 等额本息
         * dkjqrq :
         * dkll : 3.25
         * dklx : 公积金贷款
         * dkqs : 240
         * dksqrq : 2018-05-03 00:00:00.0
         * dkye : 475299.97
         * dkzt : 正常还款
         * dqyqbj : 0
         * dqyqcs : 0
         * dqyqlx : 0
         * fwjzmj : 158.03
         * fwzj : 703200
         * fwzl : 中川磨仂洲E16幢1单元5层501号
         * fxze : 0
         * gtjkrdwmc : 余江县春涛镇春涛中学
         * gtjkrgjjzh : 000004481958
         * gtjkrxm : 徐卫平
         * gtjkrzjhm : 360622198007121913
         * hkzh : 6215581506002157213
         * hsbjze : 14700.03
         * hslxze : 13092.57
         * htdkje : 490000
         * jkhtbh : 20180502030002
         * jkrdwmc : 余江县第四中学
         * jkrgjjzh : 000008481092
         * jkrxm : 陈梦帆
         * jkrzjh : 360622198806250041
         * sfdc : 是
         * shqs : 10
         * ydhkr : 11
         * yjqrq : 2038-05-11
         * zhkhyhmc :
         */

        private String dkffe;
        private String dkffrq;
        private String dkhkfs;
        private String dkjqrq;
        private String dkll;
        private String dklx;
        private String dkqs;
        private String dksqrq;
        private String dkye;
        private String dkzt;
        private String dqyqbj;
        private String dqyqcs;
        private String dqyqlx;
        private String fwjzmj;
        private String fwzj;
        private String fwzl;
        private String fxze;
        private String gtjkrdwmc;
        private String gtjkrgjjzh;
        private String gtjkrxm;
        private String gtjkrzjhm;
        private String hkzh;
        private String hsbjze;
        private String hslxze;
        private String htdkje;
        private String jkhtbh;
        private String jkrdwmc;
        private String jkrgjjzh;
        private String jkrxm;
        private String jkrzjh;
        private String sfdc;
        private String shqs;
        private String ydhkr;
        private String yjqrq;
        private String zhkhyhmc;

        public String getDkffe() {
            return dkffe;
        }

        public void setDkffe(String dkffe) {
            this.dkffe = dkffe;
        }

        public String getDkffrq() {
            return dkffrq;
        }

        public void setDkffrq(String dkffrq) {
            this.dkffrq = dkffrq;
        }

        public String getDkhkfs() {
            return dkhkfs;
        }

        public void setDkhkfs(String dkhkfs) {
            this.dkhkfs = dkhkfs;
        }

        public String getDkjqrq() {
            return dkjqrq;
        }

        public void setDkjqrq(String dkjqrq) {
            this.dkjqrq = dkjqrq;
        }

        public String getDkll() {
            return dkll;
        }

        public void setDkll(String dkll) {
            this.dkll = dkll;
        }

        public String getDklx() {
            return dklx;
        }

        public void setDklx(String dklx) {
            this.dklx = dklx;
        }

        public String getDkqs() {
            return dkqs;
        }

        public void setDkqs(String dkqs) {
            this.dkqs = dkqs;
        }

        public String getDksqrq() {
            return dksqrq;
        }

        public void setDksqrq(String dksqrq) {
            this.dksqrq = dksqrq;
        }

        public String getDkye() {
            return dkye;
        }

        public void setDkye(String dkye) {
            this.dkye = dkye;
        }

        public String getDkzt() {
            return dkzt;
        }

        public void setDkzt(String dkzt) {
            this.dkzt = dkzt;
        }

        public String getDqyqbj() {
            return dqyqbj;
        }

        public void setDqyqbj(String dqyqbj) {
            this.dqyqbj = dqyqbj;
        }

        public String getDqyqcs() {
            return dqyqcs;
        }

        public void setDqyqcs(String dqyqcs) {
            this.dqyqcs = dqyqcs;
        }

        public String getDqyqlx() {
            return dqyqlx;
        }

        public void setDqyqlx(String dqyqlx) {
            this.dqyqlx = dqyqlx;
        }

        public String getFwjzmj() {
            return fwjzmj;
        }

        public void setFwjzmj(String fwjzmj) {
            this.fwjzmj = fwjzmj;
        }

        public String getFwzj() {
            return fwzj;
        }

        public void setFwzj(String fwzj) {
            this.fwzj = fwzj;
        }

        public String getFwzl() {
            return fwzl;
        }

        public void setFwzl(String fwzl) {
            this.fwzl = fwzl;
        }

        public String getFxze() {
            return fxze;
        }

        public void setFxze(String fxze) {
            this.fxze = fxze;
        }

        public String getGtjkrdwmc() {
            return gtjkrdwmc;
        }

        public void setGtjkrdwmc(String gtjkrdwmc) {
            this.gtjkrdwmc = gtjkrdwmc;
        }

        public String getGtjkrgjjzh() {
            return gtjkrgjjzh;
        }

        public void setGtjkrgjjzh(String gtjkrgjjzh) {
            this.gtjkrgjjzh = gtjkrgjjzh;
        }

        public String getGtjkrxm() {
            return gtjkrxm;
        }

        public void setGtjkrxm(String gtjkrxm) {
            this.gtjkrxm = gtjkrxm;
        }

        public String getGtjkrzjhm() {
            return gtjkrzjhm;
        }

        public void setGtjkrzjhm(String gtjkrzjhm) {
            this.gtjkrzjhm = gtjkrzjhm;
        }

        public String getHkzh() {
            return hkzh;
        }

        public void setHkzh(String hkzh) {
            this.hkzh = hkzh;
        }

        public String getHsbjze() {
            return hsbjze;
        }

        public void setHsbjze(String hsbjze) {
            this.hsbjze = hsbjze;
        }

        public String getHslxze() {
            return hslxze;
        }

        public void setHslxze(String hslxze) {
            this.hslxze = hslxze;
        }

        public String getHtdkje() {
            return htdkje;
        }

        public void setHtdkje(String htdkje) {
            this.htdkje = htdkje;
        }

        public String getJkhtbh() {
            return jkhtbh;
        }

        public void setJkhtbh(String jkhtbh) {
            this.jkhtbh = jkhtbh;
        }

        public String getJkrdwmc() {
            return jkrdwmc;
        }

        public void setJkrdwmc(String jkrdwmc) {
            this.jkrdwmc = jkrdwmc;
        }

        public String getJkrgjjzh() {
            return jkrgjjzh;
        }

        public void setJkrgjjzh(String jkrgjjzh) {
            this.jkrgjjzh = jkrgjjzh;
        }

        public String getJkrxm() {
            return jkrxm;
        }

        public void setJkrxm(String jkrxm) {
            this.jkrxm = jkrxm;
        }

        public String getJkrzjh() {
            return jkrzjh;
        }

        public void setJkrzjh(String jkrzjh) {
            this.jkrzjh = jkrzjh;
        }

        public String getSfdc() {
            return sfdc;
        }

        public void setSfdc(String sfdc) {
            this.sfdc = sfdc;
        }

        public String getShqs() {
            return shqs;
        }

        public void setShqs(String shqs) {
            this.shqs = shqs;
        }

        public String getYdhkr() {
            return ydhkr;
        }

        public void setYdhkr(String ydhkr) {
            this.ydhkr = ydhkr;
        }

        public String getYjqrq() {
            return yjqrq;
        }

        public void setYjqrq(String yjqrq) {
            this.yjqrq = yjqrq;
        }

        public String getZhkhyhmc() {
            return zhkhyhmc;
        }

        public void setZhkhyhmc(String zhkhyhmc) {
            this.zhkhyhmc = zhkhyhmc;
        }
    }
}
