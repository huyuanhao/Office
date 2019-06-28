package com.powerrich.office.oa.bean;

/**
 * 文件名：
 * 描述：
 * 作者：白煜
 * 时间：2018/1/5 0005
 * 版权：
 */

public class AppointmentDetailBean {

    /**
     * DATA : {"A_ID":"201712070900143568129143018","DEPTID":"ۍ{ם:מ5㍺띻｀","DEPTNAME":"市公安局","IDCARD":"441423199307252019",
     * "ITEMID":"","ITEMNAME":"港澳通行证申请","ORDER_DATE":"2017-12-09 9:00-10:30","ORDER_NO":"00143562609",
     * "ORDER_PHONE":"13312960198","ORDER_STATE":"1","REAL_NAME":"张楠","USERID":"2017060115434771793843065"}
     * code : 0
     * message : 操作成功
     */

    private DATABean DATA;
    private String code;
    private String message;

    public DATABean getDATA() {
        return DATA;
    }

    public void setDATA(DATABean DATA) {
        this.DATA = DATA;
    }

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

    public static class DATABean {
        /**
         * A_ID : 201712070900143568129143018
         * DEPTID : ۍ{ם:מ5㍺띻｀
         * DEPTNAME : 市公安局
         * IDCARD : 441423199307252019
         * ITEMID :
         * ITEMNAME : 港澳通行证申请
         * ORDER_DATE : 2017-12-09 9:00-10:30
         * ORDER_NO : 00143562609
         * ORDER_PHONE : 13312960198
         * ORDER_STATE : 1
         * REAL_NAME : 张楠
         * USERID : 2017060115434771793843065
         */

        private String A_ID;
        private String DEPTID;
        private String DEPTNAME;
        private String IDCARD;
        private String ITEMID;
        private String ITEMNAME;
        private String ORDER_DATE;
        private String ORDER_NO;
        private String ORDER_PHONE;
        private String ORDER_STATE;
        private String REAL_NAME;
        private String USERID;

        public String getA_ID() {
            return A_ID;
        }

        public void setA_ID(String A_ID) {
            this.A_ID = A_ID;
        }

        public String getDEPTID() {
            return DEPTID;
        }

        public void setDEPTID(String DEPTID) {
            this.DEPTID = DEPTID;
        }

        public String getDEPTNAME() {
            return DEPTNAME;
        }

        public void setDEPTNAME(String DEPTNAME) {
            this.DEPTNAME = DEPTNAME;
        }

        public String getIDCARD() {
            return IDCARD;
        }

        public void setIDCARD(String IDCARD) {
            this.IDCARD = IDCARD;
        }

        public String getITEMID() {
            return ITEMID;
        }

        public void setITEMID(String ITEMID) {
            this.ITEMID = ITEMID;
        }

        public String getITEMNAME() {
            return ITEMNAME;
        }

        public void setITEMNAME(String ITEMNAME) {
            this.ITEMNAME = ITEMNAME;
        }

        public String getORDER_DATE() {
            return ORDER_DATE;
        }

        public void setORDER_DATE(String ORDER_DATE) {
            this.ORDER_DATE = ORDER_DATE;
        }

        public String getORDER_NO() {
            return ORDER_NO;
        }

        public void setORDER_NO(String ORDER_NO) {
            this.ORDER_NO = ORDER_NO;
        }

        public String getORDER_PHONE() {
            return ORDER_PHONE;
        }

        public void setORDER_PHONE(String ORDER_PHONE) {
            this.ORDER_PHONE = ORDER_PHONE;
        }

        public String getORDER_STATE() {
            return ORDER_STATE;
        }

        public void setORDER_STATE(String ORDER_STATE) {
            this.ORDER_STATE = ORDER_STATE;
        }

        public String getREAL_NAME() {
            return REAL_NAME;
        }

        public void setREAL_NAME(String REAL_NAME) {
            this.REAL_NAME = REAL_NAME;
        }

        public String getUSERID() {
            return USERID;
        }

        public void setUSERID(String USERID) {
            this.USERID = USERID;
        }
    }
}
