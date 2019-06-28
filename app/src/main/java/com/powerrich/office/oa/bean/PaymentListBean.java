package com.powerrich.office.oa.bean;

import java.util.List;

/**
 * 文件名：PaymentListBean
 * 描述：
 * 作者：白煜
 * 时间：2017/12/6 0006
 * 版权：
 */

public class PaymentListBean {


    /**
     * DATA : [{"BIZID":"92cf13634f21477ba0933cb90c9798ff","ITEMPAYID":"201711221425493626757881825","KPZT":"0","ORDERID":"",
     * "SQXMNAME":"中华人民共和国普通护照申请","SSZJE":"160","STATUS":"0","SXID":"201711211515498612972479072","SXSQNO":"pthz2017112201",
     * "USERID":"201711201757024574595625227","USERNAME":"方鸿渐","YMYY":"","YMZJE":"0","YSZJE":"160","ZFFS":""},
     * {"BIZID":"de6ed322d2f54071851e057ed8475584","ITEMPAYID":"201711222018486188216798221","KPZT":"0","ORDERID":"",
     * "SQXMNAME":"中华人民共和国前往港澳通行证申请","SSZJE":"40","STATUS":"0","SXID":"201711211516128553754705035","SXSQNO":"GATXZ2017112204",
     * "USERID":"201711201757024574595625227","USERNAME":"方鸿渐","YMYY":"","YMZJE":"0","YSZJE":"40","ZFFS":""},
     * {"BIZID":"ccf6ba9b129b49c9bf06443ebe443969","ITEMPAYID":"201711222041238700494993657","KPZT":"0","ORDERID":"",
     * "SQXMNAME":"中华人民共和国前往港澳通行证申请","SSZJE":"40","STATUS":"0","SXID":"201711211516128553754705035","SXSQNO":"GATXZ2017112205",
     * "USERID":"201711201757024574595625227","USERNAME":"方鸿渐","YMYY":"","YMZJE":"0","YSZJE":"40","ZFFS":""},
     * {"BIZID":"decc04fc61d34c49b677e695a30ff74c","ITEMPAYID":"201711301536516164080661755","KPZT":"0","ORDERID":"",
     * "SQXMNAME":"中华人民共和国普通护照申请","SSZJE":"160","STATUS":"0","SXID":"201711222102264686388404091","SXSQNO":"pthz2017113001",
     * "USERID":"201711201757024574595625227","USERNAME":"方鸿渐","YMYY":"","YMZJE":"0","YSZJE":"160","ZFFS":""}]
     * code : 0
     * message : 操作成功
     */

    private String code;
    private String message;
    private List<DATABean> DATA;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DATABean> getDATA() {
        return DATA;
    }

    public void setDATA(List<DATABean> DATA) {
        this.DATA = DATA;
    }

    public static class DATABean {
        /**
         * BIZID : 92cf13634f21477ba0933cb90c9798ff
         * ITEMPAYID : 201711221425493626757881825
         * KPZT : 0
         * ORDERID :
         * SQXMNAME : 中华人民共和国普通护照申请
         * SSZJE : 160
         * STATUS : 0
         * SXID : 201711211515498612972479072
         * SXSQNO : pthz2017112201
         * USERID : 201711201757024574595625227
         * USERNAME : 方鸿渐
         * YMYY :
         * YMZJE : 0
         * YSZJE : 160
         * ZFFS :
         */

        private String BIZID;
        private String ITEMPAYID;
        private String KPZT;
        private String ORDERID;
        private String SQXMNAME;
        private String SSZJE;
        private String STATUS;
        private String SXID;
        private String SXSQNO;
        private String USERID;
        private String USERNAME;
        private String YMYY;
        private String YMZJE;
        private String YSZJE;
        private String ZFFS;

        public String getBIZID() {
            return BIZID;
        }

        public void setBIZID(String BIZID) {
            this.BIZID = BIZID;
        }

        public String getITEMPAYID() {
            return ITEMPAYID;
        }

        public void setITEMPAYID(String ITEMPAYID) {
            this.ITEMPAYID = ITEMPAYID;
        }

        public String getKPZT() {
            return KPZT;
        }

        public void setKPZT(String KPZT) {
            this.KPZT = KPZT;
        }

        public String getORDERID() {
            return ORDERID;
        }

        public void setORDERID(String ORDERID) {
            this.ORDERID = ORDERID;
        }

        public String getSQXMNAME() {
            return SQXMNAME;
        }

        public void setSQXMNAME(String SQXMNAME) {
            this.SQXMNAME = SQXMNAME;
        }

        public String getSSZJE() {
            return SSZJE;
        }

        public void setSSZJE(String SSZJE) {
            this.SSZJE = SSZJE;
        }

        public String getSTATUS() {
            return STATUS;
        }

        public void setSTATUS(String STATUS) {
            this.STATUS = STATUS;
        }

        public String getSXID() {
            return SXID;
        }

        public void setSXID(String SXID) {
            this.SXID = SXID;
        }

        public String getSXSQNO() {
            return SXSQNO;
        }

        public void setSXSQNO(String SXSQNO) {
            this.SXSQNO = SXSQNO;
        }

        public String getUSERID() {
            return USERID;
        }

        public void setUSERID(String USERID) {
            this.USERID = USERID;
        }

        public String getUSERNAME() {
            return USERNAME;
        }

        public void setUSERNAME(String USERNAME) {
            this.USERNAME = USERNAME;
        }

        public String getYMYY() {
            return YMYY;
        }

        public void setYMYY(String YMYY) {
            this.YMYY = YMYY;
        }

        public String getYMZJE() {
            return YMZJE;
        }

        public void setYMZJE(String YMZJE) {
            this.YMZJE = YMZJE;
        }

        public String getYSZJE() {
            return YSZJE;
        }

        public void setYSZJE(String YSZJE) {
            this.YSZJE = YSZJE;
        }

        public String getZFFS() {
            return ZFFS;
        }

        public void setZFFS(String ZFFS) {
            this.ZFFS = ZFFS;
        }
    }
}
