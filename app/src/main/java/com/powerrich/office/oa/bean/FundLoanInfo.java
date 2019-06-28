package com.powerrich.office.oa.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PC
 * @date 2019/03/01 15:04
 */
public class FundLoanInfo {
    private List<DkxxBean> dkxx;
    private List<GjjxxBean> gjjxx;

    public List<DkxxBean> getDkxx() {
        return dkxx == null ? new ArrayList<DkxxBean>() : dkxx;
    }

    public void setDkxx(List<DkxxBean> dkxx) {
        this.dkxx = dkxx;
    }

    public List<GjjxxBean> getGjjxx() {
        return gjjxx == null ? new ArrayList<GjjxxBean>() : gjjxx;
    }

    public void setGjjxx(List<GjjxxBean> gjjxx) {
        this.gjjxx = gjjxx;
    }

    public static class DkxxBean {
        /**
         * dkffe : 490000
         * dkye : 475299.97
         * dkzt : 正常还款
         * jkhtbh : 20180502030002
         * jkrgjjzh : 000008481092
         */

        private String dkffe;
        private String dkye;
        private String dkzt;
        private String jkhtbh;
        private String jkrgjjzh;

        public String getDkffe() {
            return dkffe;
        }

        public void setDkffe(String dkffe) {
            this.dkffe = dkffe;
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

        public String getJkhtbh() {
            return jkhtbh;
        }

        public void setJkhtbh(String jkhtbh) {
            this.jkhtbh = jkhtbh;
        }

        public String getJkrgjjzh() {
            return jkrgjjzh;
        }

        public void setJkrgjjzh(String jkrgjjzh) {
            this.jkrgjjzh = jkrgjjzh;
        }
    }

    public static class GjjxxBean {
        /**
         * dwmc : 余江县杨溪乡中心学校
         * dwzh : 030040
         * grzh : 000004482299
         * grzhye : 0
         * grzhzt : 提取销户
         * jgbm : 0103
         */

        private String dwmc;
        private String dwzh;
        private String grzh;
        private String grzhye;
        private String grzhzt;
        private String jgbm;

        public String getDwmc() {
            return dwmc;
        }

        public void setDwmc(String dwmc) {
            this.dwmc = dwmc;
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

        public String getJgbm() {
            return jgbm;
        }

        public void setJgbm(String jgbm) {
            this.jgbm = jgbm;
        }
    }
}