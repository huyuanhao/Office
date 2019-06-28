package com.yt.simpleframe.http.bean;

import java.util.List;


public class LogisticsInfoBean extends BaseBean{

    /**
     * ADR_DATA : {"CONSIGNEE_ADDRESS":"鹰潭市经济大厦C637","CONSIGNEE_CITY":"鹰潭市","CONSIGNEE_NAME":"人社局",
     * "CONSIGNEE_PHONE":"07016441773","CONSIGNEE_PROV":"江西省","CONSIGNEE_YZBM":"335000","DATA_SOURCE":"1",
     * "EMIT_TIME":"2017-12-30 02:30:15","ORDER_NUM":"gt_ems_20171229143557363","ORDER_STATUS":"2",
     * "RECORD_ID":"201712291435575521931472255","SEND_ADDRESS":"深圳市福田区彩田南路海滨广场皇岗花园大厦A栋三楼","SEND_CITY":"深圳市","SEND_NAME":"陈志强",
     * "SEND_PHONE":"13006754102","SEND_PROV":"广东省","SEND_YZBM":"518000","SIGN_TIME":"","TRACKING_NUM":"1192602739310"}
     * EMS_DATA : [{"acceptAddress":"杭州市","acceptTime":"2017-07-08 16:43:44","code":"00","remark":"杭州市速递电商分公司已收件（揽投员姓名：电商02,
     * 联系电话:0571-86896153）"},{"acceptAddress":"杭州市","acceptTime":"2017-07-09 04:27:00","code":"40","remark":"离开杭州市 发往西安市"},
     * {"acceptAddress":"西安市","acceptTime":"2017-07-10 05:36:00","code":"41","remark":"到达西安邮区中心局邮件处理中心处理中心（经转）"},
     * {"acceptAddress":"西安市","acceptTime":"2017-07-10 10:53:00","code":"40","remark":"离开西安市 发往下一目的地（经转）"},{"acceptAddress":"
     * ","acceptTime":"2017-07-10 23:06:00","code":"41","remark":"到达银川中心局处理中心（经转）"},{"acceptAddress":"乌海市",
     * "acceptTime":"2017-07-11 11:09:16","code":"50","remark":"乌海市邮政速递物流分公司海北揽投部安排投递，预计23:59:00前投递（投递员姓名：熊帅;
     * 联系电话：18247355979）"},{"acceptAddress":"乌海市","acceptTime":"2017-07-11 17:49:15","code":"10","remark":"投递并签收，签收人：单位收发章
     * 单位收发章"}]
     * code : 0
     * message : 操作成功
     */

    private ADRDATABean ADR_DATA;
    private List<EMSDATABean> EMS_DATA;

    public ADRDATABean getADR_DATA() {
        return ADR_DATA;
    }

    public void setADR_DATA(ADRDATABean ADR_DATA) {
        this.ADR_DATA = ADR_DATA;
    }

    public List<EMSDATABean> getEMS_DATA() {
        return EMS_DATA;
    }

    public void setEMS_DATA(List<EMSDATABean> EMS_DATA) {
        this.EMS_DATA = EMS_DATA;
    }

    public static class ADRDATABean {
        /**
         * CONSIGNEE_ADDRESS : 鹰潭市经济大厦C637
         * CONSIGNEE_CITY : 鹰潭市
         * CONSIGNEE_NAME : 人社局
         * CONSIGNEE_PHONE : 07016441773
         * CONSIGNEE_PROV : 江西省
         * CONSIGNEE_YZBM : 335000
         * DATA_SOURCE : 1
         * EMIT_TIME : 2017-12-30 02:30:15
         * ORDER_NUM : gt_ems_20171229143557363
         * ORDER_STATUS : 2
         * RECORD_ID : 201712291435575521931472255
         * SEND_ADDRESS : 深圳市福田区彩田南路海滨广场皇岗花园大厦A栋三楼
         * SEND_CITY : 深圳市
         * SEND_NAME : 陈志强
         * SEND_PHONE : 13006754102
         * SEND_PROV : 广东省
         * SEND_YZBM : 518000
         * SIGN_TIME :
         * TRACKING_NUM : 1192602739310
         */

        private String CONSIGNEE_ADDRESS;
        private String CONSIGNEE_CITY;
        private String CONSIGNEE_NAME;
        private String CONSIGNEE_PHONE;
        private String CONSIGNEE_PROV;
        private String CONSIGNEE_YZBM;
        private String DATA_SOURCE;
        private String EMIT_TIME;
        private String ORDER_NUM;
        private String ORDER_STATUS;
        private String RECORD_ID;
        private String SEND_ADDRESS;
        private String SEND_CITY;
        private String SEND_NAME;
        private String SEND_PHONE;
        private String SEND_PROV;
        private String SEND_YZBM;
        private String SIGN_TIME;
        private String TRACKING_NUM;

        public String getCONSIGNEE_ADDRESS() {
            return CONSIGNEE_ADDRESS;
        }

        public void setCONSIGNEE_ADDRESS(String CONSIGNEE_ADDRESS) {
            this.CONSIGNEE_ADDRESS = CONSIGNEE_ADDRESS;
        }

        public String getCONSIGNEE_CITY() {
            return CONSIGNEE_CITY;
        }

        public void setCONSIGNEE_CITY(String CONSIGNEE_CITY) {
            this.CONSIGNEE_CITY = CONSIGNEE_CITY;
        }

        public String getCONSIGNEE_NAME() {
            return CONSIGNEE_NAME;
        }

        public void setCONSIGNEE_NAME(String CONSIGNEE_NAME) {
            this.CONSIGNEE_NAME = CONSIGNEE_NAME;
        }

        public String getCONSIGNEE_PHONE() {
            return CONSIGNEE_PHONE;
        }

        public void setCONSIGNEE_PHONE(String CONSIGNEE_PHONE) {
            this.CONSIGNEE_PHONE = CONSIGNEE_PHONE;
        }

        public String getCONSIGNEE_PROV() {
            return CONSIGNEE_PROV;
        }

        public void setCONSIGNEE_PROV(String CONSIGNEE_PROV) {
            this.CONSIGNEE_PROV = CONSIGNEE_PROV;
        }

        public String getCONSIGNEE_YZBM() {
            return CONSIGNEE_YZBM;
        }

        public void setCONSIGNEE_YZBM(String CONSIGNEE_YZBM) {
            this.CONSIGNEE_YZBM = CONSIGNEE_YZBM;
        }

        public String getDATA_SOURCE() {
            return DATA_SOURCE;
        }

        public void setDATA_SOURCE(String DATA_SOURCE) {
            this.DATA_SOURCE = DATA_SOURCE;
        }

        public String getEMIT_TIME() {
            return EMIT_TIME;
        }

        public void setEMIT_TIME(String EMIT_TIME) {
            this.EMIT_TIME = EMIT_TIME;
        }

        public String getORDER_NUM() {
            return ORDER_NUM;
        }

        public void setORDER_NUM(String ORDER_NUM) {
            this.ORDER_NUM = ORDER_NUM;
        }

        public String getORDER_STATUS() {
            return ORDER_STATUS;
        }

        public void setORDER_STATUS(String ORDER_STATUS) {
            this.ORDER_STATUS = ORDER_STATUS;
        }

        public String getRECORD_ID() {
            return RECORD_ID;
        }

        public void setRECORD_ID(String RECORD_ID) {
            this.RECORD_ID = RECORD_ID;
        }

        public String getSEND_ADDRESS() {
            return SEND_ADDRESS;
        }

        public void setSEND_ADDRESS(String SEND_ADDRESS) {
            this.SEND_ADDRESS = SEND_ADDRESS;
        }

        public String getSEND_CITY() {
            return SEND_CITY;
        }

        public void setSEND_CITY(String SEND_CITY) {
            this.SEND_CITY = SEND_CITY;
        }

        public String getSEND_NAME() {
            return SEND_NAME;
        }

        public void setSEND_NAME(String SEND_NAME) {
            this.SEND_NAME = SEND_NAME;
        }

        public String getSEND_PHONE() {
            return SEND_PHONE;
        }

        public void setSEND_PHONE(String SEND_PHONE) {
            this.SEND_PHONE = SEND_PHONE;
        }

        public String getSEND_PROV() {
            return SEND_PROV;
        }

        public void setSEND_PROV(String SEND_PROV) {
            this.SEND_PROV = SEND_PROV;
        }

        public String getSEND_YZBM() {
            return SEND_YZBM;
        }

        public void setSEND_YZBM(String SEND_YZBM) {
            this.SEND_YZBM = SEND_YZBM;
        }

        public String getSIGN_TIME() {
            return SIGN_TIME;
        }

        public void setSIGN_TIME(String SIGN_TIME) {
            this.SIGN_TIME = SIGN_TIME;
        }

        public String getTRACKING_NUM() {
            return TRACKING_NUM;
        }

        public void setTRACKING_NUM(String TRACKING_NUM) {
            this.TRACKING_NUM = TRACKING_NUM;
        }
    }

    public static class EMSDATABean {
        /**
         * acceptAddress : 杭州市
         * acceptTime : 2017-07-08 16:43:44
         * code : 00
         * remark : 杭州市速递电商分公司已收件（揽投员姓名：电商02,联系电话:0571-86896153）
         */

        private String acceptAddress;
        private String acceptTime;
        private String code;
        private String remark;

        public String getAcceptAddress() {
            return acceptAddress;
        }

        public void setAcceptAddress(String acceptAddress) {
            this.acceptAddress = acceptAddress;
        }

        public String getAcceptTime() {
            return acceptTime;
        }

        public void setAcceptTime(String acceptTime) {
            this.acceptTime = acceptTime;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
