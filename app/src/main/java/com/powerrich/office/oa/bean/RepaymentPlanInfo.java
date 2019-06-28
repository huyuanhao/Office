package com.powerrich.office.oa.bean;

import java.util.List;

/**
 * @author PC
 * @date 2019/03/01 15:37
 */
public class RepaymentPlanInfo {

    /**
     * CURRENTROWS : 10
     * DATA : [{"yhbj":"1487.96","yhbjhj":"1487.96","yhbx":"2779.26","yhbxhj":"2779.26","yhlx":"1291.3","yhlxhj":"1291.3","yhny":"201903","yhqs":"10","yhrq":"2019-03-11"},{"yhbj":"1491.99","yhbjhj":"2979.95","yhbx":"2779.26","yhbxhj":"5558.52","yhlx":"1287.27","yhlxhj":"2578.57","yhny":"201904","yhqs":"11","yhrq":"2019-04-11"},{"yhbj":"1496.03","yhbjhj":"4475.98","yhbx":"2779.26","yhbxhj":"8337.78","yhlx":"1283.23","yhlxhj":"3861.8","yhny":"201905","yhqs":"12","yhrq":"2019-05-11"},{"yhbj":"1500.08","yhbjhj":"5976.06","yhbx":"2779.26","yhbxhj":"11117.04","yhlx":"1279.18","yhlxhj":"5140.98","yhny":"201906","yhqs":"13","yhrq":"2019-06-11"},{"yhbj":"1504.14","yhbjhj":"7480.2","yhbx":"2779.26","yhbxhj":"13896.3","yhlx":"1275.12","yhlxhj":"6416.1","yhny":"201907","yhqs":"14","yhrq":"2019-07-11"},{"yhbj":"1508.22","yhbjhj":"8988.42","yhbx":"2779.26","yhbxhj":"16675.56","yhlx":"1271.04","yhlxhj":"7687.14","yhny":"201908","yhqs":"15","yhrq":"2019-08-11"},{"yhbj":"1512.3","yhbjhj":"10500.72","yhbx":"2779.26","yhbxhj":"19454.82","yhlx":"1266.96","yhlxhj":"8954.1","yhny":"201909","yhqs":"16","yhrq":"2019-09-11"},{"yhbj":"1516.4","yhbjhj":"12017.12","yhbx":"2779.26","yhbxhj":"22234.08","yhlx":"1262.86","yhlxhj":"10216.96","yhny":"201910","yhqs":"17","yhrq":"2019-10-11"},{"yhbj":"1520.51","yhbjhj":"13537.63","yhbx":"2779.26","yhbxhj":"25013.34","yhlx":"1258.75","yhlxhj":"11475.71","yhny":"201911","yhqs":"18","yhrq":"2019-11-11"},{"yhbj":"1524.62","yhbjhj":"15062.25","yhbx":"2779.26","yhbxhj":"27792.6","yhlx":"1254.64","yhlxhj":"12730.35","yhny":"201912","yhqs":"19","yhrq":"2019-12-11"}]
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
         * yhbj : 1487.96
         * yhbjhj : 1487.96
         * yhbx : 2779.26
         * yhbxhj : 2779.26
         * yhlx : 1291.3
         * yhlxhj : 1291.3
         * yhny : 201903
         * yhqs : 10
         * yhrq : 2019-03-11
         */

        private String yhbj;
        private String yhbjhj;
        private String yhbx;
        private String yhbxhj;
        private String yhlx;
        private String yhlxhj;
        private String yhny;
        private String yhqs;
        private String yhrq;

        public String getYhbj() {
            return yhbj;
        }

        public void setYhbj(String yhbj) {
            this.yhbj = yhbj;
        }

        public String getYhbjhj() {
            return yhbjhj;
        }

        public void setYhbjhj(String yhbjhj) {
            this.yhbjhj = yhbjhj;
        }

        public String getYhbx() {
            return yhbx;
        }

        public void setYhbx(String yhbx) {
            this.yhbx = yhbx;
        }

        public String getYhbxhj() {
            return yhbxhj;
        }

        public void setYhbxhj(String yhbxhj) {
            this.yhbxhj = yhbxhj;
        }

        public String getYhlx() {
            return yhlx;
        }

        public void setYhlx(String yhlx) {
            this.yhlx = yhlx;
        }

        public String getYhlxhj() {
            return yhlxhj;
        }

        public void setYhlxhj(String yhlxhj) {
            this.yhlxhj = yhlxhj;
        }

        public String getYhny() {
            return yhny;
        }

        public void setYhny(String yhny) {
            this.yhny = yhny;
        }

        public String getYhqs() {
            return yhqs;
        }

        public void setYhqs(String yhqs) {
            this.yhqs = yhqs;
        }

        public String getYhrq() {
            return yhrq;
        }

        public void setYhrq(String yhrq) {
            this.yhrq = yhrq;
        }
    }
}
