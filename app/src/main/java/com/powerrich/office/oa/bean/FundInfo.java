package com.powerrich.office.oa.bean;

import java.util.List;

/**
 * @author PC
 * @date 2019/03/01 13:51
 */
public class FundInfo {

    /**
     * CURRENTROWS : 1
     * DATA : [{"dwjcbl":"0.12","dwmc":"余江县第四中学","dwyjce":"591","dwzh":"030123","email":"","grckzhhm":"","grckzhkhyhmc":"","grjcbl":"0.12","grjcjs":"4925","gryjce":"591","grzh":"000008481092","grzhye":"5558.52","grzhzt":"正常","hyzk":"已婚","jzny":"201812","khrq":"2016-09-01","qjny":"190001","sjhm":"15970283426","xingming":"陈梦帆","zhsfdj":"0","zjhm":"360622198806250041","zjjce":"591","zjjcrq":"2018-12-29","zjtqe":"878.12","zjtqrq":"2019-02-11"}]
     */

    private int CURRENTROWS;
    private List<DATABean> DATA;

    public int getCURRENTROWS() {
        return CURRENTROWS;
    }

    public void setCURRENTROWS(int CURRENTROWS) {
        this.CURRENTROWS = CURRENTROWS;
    }

    public List<DATABean> getDATA() {
        return DATA;
    }

    public void setDATA(List<DATABean> DATA) {
        this.DATA = DATA;
    }

    public static class DATABean {
        /**
         * dwjcbl : 0.12
         * dwmc : 余江县第四中学
         * dwyjce : 591
         * dwzh : 030123
         * email :
         * grckzhhm :
         * grckzhkhyhmc :
         * grjcbl : 0.12
         * grjcjs : 4925
         * gryjce : 591
         * grzh : 000008481092
         * grzhye : 5558.52
         * grzhzt : 正常
         * hyzk : 已婚
         * jzny : 201812
         * khrq : 2016-09-01
         * qjny : 190001
         * sjhm : 15970283426
         * xingming : 陈梦帆
         * zhsfdj : 0
         * zjhm : 360622198806250041
         * zjjce : 591
         * zjjcrq : 2018-12-29
         * zjtqe : 878.12
         * zjtqrq : 2019-02-11
         */

        private String dwjcbl;
        private String dwmc;
        private String dwyjce;
        private String dwzh;
        private String email;
        private String grckzhhm;
        private String grckzhkhyhmc;
        private String grjcbl;
        private String grjcjs;
        private String gryjce;
        private String grzh;
        private String grzhye;
        private String grzhzt;
        private String hyzk;
        private String jzny;
        private String khrq;
        private String qjny;
        private String sjhm;
        private String xingming;
        private String zhsfdj;
        private String zjhm;
        private String zjjce;
        private String zjjcrq;
        private String zjtqe;
        private String zjtqrq;

        public String getDwjcbl() {
            return dwjcbl;
        }

        public void setDwjcbl(String dwjcbl) {
            this.dwjcbl = dwjcbl;
        }

        public String getDwmc() {
            return dwmc;
        }

        public void setDwmc(String dwmc) {
            this.dwmc = dwmc;
        }

        public String getDwyjce() {
            return dwyjce;
        }

        public void setDwyjce(String dwyjce) {
            this.dwyjce = dwyjce;
        }

        public String getDwzh() {
            return dwzh;
        }

        public void setDwzh(String dwzh) {
            this.dwzh = dwzh;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGrckzhhm() {
            return grckzhhm;
        }

        public void setGrckzhhm(String grckzhhm) {
            this.grckzhhm = grckzhhm;
        }

        public String getGrckzhkhyhmc() {
            return grckzhkhyhmc;
        }

        public void setGrckzhkhyhmc(String grckzhkhyhmc) {
            this.grckzhkhyhmc = grckzhkhyhmc;
        }

        public String getGrjcbl() {
            return grjcbl;
        }

        public void setGrjcbl(String grjcbl) {
            this.grjcbl = grjcbl;
        }

        public String getGrjcjs() {
            return grjcjs;
        }

        public void setGrjcjs(String grjcjs) {
            this.grjcjs = grjcjs;
        }

        public String getGryjce() {
            return gryjce;
        }

        public void setGryjce(String gryjce) {
            this.gryjce = gryjce;
        }

        public String getGrzh() {
            return grzh;
        }

        public void setGrzh(String grzh) {
            this.grzh = grzh;
        }

        public String getGrzhye() {
            return grzhye;
        }

        public void setGrzhye(String grzhye) {
            this.grzhye = grzhye;
        }

        public String getGrzhzt() {
            return grzhzt;
        }

        public void setGrzhzt(String grzhzt) {
            this.grzhzt = grzhzt;
        }

        public String getHyzk() {
            return hyzk;
        }

        public void setHyzk(String hyzk) {
            this.hyzk = hyzk;
        }

        public String getJzny() {
            return jzny;
        }

        public void setJzny(String jzny) {
            this.jzny = jzny;
        }

        public String getKhrq() {
            return khrq;
        }

        public void setKhrq(String khrq) {
            this.khrq = khrq;
        }

        public String getQjny() {
            return qjny;
        }

        public void setQjny(String qjny) {
            this.qjny = qjny;
        }

        public String getSjhm() {
            return sjhm;
        }

        public void setSjhm(String sjhm) {
            this.sjhm = sjhm;
        }

        public String getXingming() {
            return xingming;
        }

        public void setXingming(String xingming) {
            this.xingming = xingming;
        }

        public String getZhsfdj() {
            return zhsfdj;
        }

        public void setZhsfdj(String zhsfdj) {
            this.zhsfdj = zhsfdj;
        }

        public String getZjhm() {
            return zjhm;
        }

        public void setZjhm(String zjhm) {
            this.zjhm = zjhm;
        }

        public String getZjjce() {
            return zjjce;
        }

        public void setZjjce(String zjjce) {
            this.zjjce = zjjce;
        }

        public String getZjjcrq() {
            return zjjcrq;
        }

        public void setZjjcrq(String zjjcrq) {
            this.zjjcrq = zjjcrq;
        }

        public String getZjtqe() {
            return zjtqe;
        }

        public void setZjtqe(String zjtqe) {
            this.zjtqe = zjtqe;
        }

        public String getZjtqrq() {
            return zjtqrq;
        }

        public void setZjtqrq(String zjtqrq) {
            this.zjtqrq = zjtqrq;
        }
    }
}
