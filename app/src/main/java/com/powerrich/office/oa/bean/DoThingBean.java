package com.powerrich.office.oa.bean;

import java.io.Serializable;

/**
 * 文件名：DoThingBean
 * 描述：
 * 作者：白煜
 * 时间：2017/12/8 0008
 * 版权：
 */

public class DoThingBean {


    /**
     * DATA : {"NAME":"深圳市广通软件有限公司","PERSON_ADDRESS":"广东省深圳市福田区彩田南路皇岗花园大厦","PERSON_ID":"441423199307252019",
     * "PERSON_PHONE":"13312960198","PERSON_TYPE":"0","PROKEYID":"3451570a476247f49387ac776e5c0d07",
     * "REGISTER_REMARK":"港澳通行证申请申请","SQSJ":"2017-11-22 20:39:58","SXBM":"GATXZ01","SXMC":"港澳通行证申请",
     * "TRACKINGNUMBER":"GATXZ2017112206"}
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
         * NAME : 深圳市广通软件有限公司
         * PERSON_ADDRESS : 广东省深圳市福田区彩田南路皇岗花园大厦
         * PERSON_ID : 441423199307252019
         * PERSON_PHONE : 13312960198
         * PERSON_TYPE : 0
         * PROKEYID : 3451570a476247f49387ac776e5c0d07
         * REGISTER_REMARK : 港澳通行证申请申请
         * SQSJ : 2017-11-22 20:39:58
         * SXBM : GATXZ01
         * SXMC : 港澳通行证申请
         * TRACKINGNUMBER : GATXZ2017112206
         */

        private String NAME;
        private String PERSON_ADDRESS;
        private String PERSON_ID;
        private String PERSON_PHONE;
        private String PERSON_TYPE;
        private String PERSON_NAME;
        private String PROKEYID;
        private String REGISTER_REMARK;
        private String SQSJ;
        private String SXBM;
        private String SXMC;
        private String TRACKINGNUMBER;
        private String DYNAMICFORM;
        private String FILEDATA;
        private String ISEMS;
        private String ORDER_NUM;
        private String SXID;
        private String POSITION;
        private String PROCESS_STATE;
        private String ZJHM;
        private String USER_TYPE;

        private DELIVERYBean DELIVERY_ADDRESS;


        public String getZJHM() {
            return ZJHM;
        }

        public void setZJHM(String ZJHM) {
            this.ZJHM = ZJHM;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getPERSON_ADDRESS() {
            return PERSON_ADDRESS;
        }

        public void setPERSON_ADDRESS(String PERSON_ADDRESS) {
            this.PERSON_ADDRESS = PERSON_ADDRESS;
        }

        public String getPERSON_ID() {
            return PERSON_ID;
        }

        public void setPERSON_ID(String PERSON_ID) {
            this.PERSON_ID = PERSON_ID;
        }

        public String getPERSON_PHONE() {
            return PERSON_PHONE;
        }

        public void setPERSON_PHONE(String PERSON_PHONE) {
            this.PERSON_PHONE = PERSON_PHONE;
        }

        public String getPERSON_TYPE() {
            return PERSON_TYPE;
        }

        public void setPERSON_TYPE(String PERSON_TYPE) {
            this.PERSON_TYPE = PERSON_TYPE;
        }

        public String getPERSON_NAME() {
            return PERSON_NAME;
        }

        public void setPERSON_NAME(String PERSON_NAME) {
            this.PERSON_NAME = PERSON_NAME;
        }

        public String getPROKEYID() {
            return PROKEYID;
        }

        public void setPROKEYID(String PROKEYID) {
            this.PROKEYID = PROKEYID;
        }

        public String getREGISTER_REMARK() {
            return REGISTER_REMARK;
        }

        public void setREGISTER_REMARK(String REGISTER_REMARK) {
            this.REGISTER_REMARK = REGISTER_REMARK;
        }

        public String getSQSJ() {
            return SQSJ;
        }

        public void setSQSJ(String SQSJ) {
            this.SQSJ = SQSJ;
        }

        public String getSXBM() {
            return SXBM;
        }

        public void setSXBM(String SXBM) {
            this.SXBM = SXBM;
        }

        public String getSXMC() {
            return SXMC;
        }

        public void setSXMC(String SXMC) {
            this.SXMC = SXMC;
        }

        public String getTRACKINGNUMBER() {
            return TRACKINGNUMBER;
        }

        public void setTRACKINGNUMBER(String TRACKINGNUMBER) {
            this.TRACKINGNUMBER = TRACKINGNUMBER;
        }

        public String getDYNAMICFORM() {
            return DYNAMICFORM;
        }

        public void setDYNAMICFORM(String DYNAMICFORM) {
            this.DYNAMICFORM = DYNAMICFORM;
        }

        public String getFILEDATA() {
            return FILEDATA;
        }

        public void setFILEDATA(String FILEDATA) {
            this.FILEDATA = FILEDATA;
        }

        public String getISEMS() {
            return ISEMS;
        }

        public void setISEMS(String ISEMS) {
            this.ISEMS = ISEMS;
        }

        public String getORDER_NUM() {
            return ORDER_NUM;
        }

        public void setORDER_NUM(String ORDER_NUM) {
            this.ORDER_NUM = ORDER_NUM;
        }

        public String getSXID() {
            return SXID;
        }

        public void setSXID(String SXID) {
            this.SXID = SXID;
        }

        public String getPOSITION() {
            return POSITION;
        }

        public void setPOSITION(String POSITION) {
            this.POSITION = POSITION;
        }

        public String getPROCESS_STATE() {
            return PROCESS_STATE;
        }

        public void setPROCESS_STATE(String PROCESS_STATE) {
            this.PROCESS_STATE = PROCESS_STATE;
        }

        public String getUSER_TYPE() {
            return USER_TYPE;
        }

        public void setUSER_TYPE(String USER_TYPE) {
            this.USER_TYPE = USER_TYPE;
        }

        public DELIVERYBean getDELIVERY_ADDRESS() {
            return DELIVERY_ADDRESS;
        }

        public void setDELIVERY_ADDRESS(DELIVERYBean DELIVERY_ADDRESS) {
            this.DELIVERY_ADDRESS = DELIVERY_ADDRESS;
        }

        public static class DELIVERYBean implements Serializable {
            private String ZXCKDZ;
            private String ZXCKMC;
            private String ZXMKZXDH;

            public String getZXCKDZ() {
                return ZXCKDZ;
            }

            public void setZXCKDZ(String ZXCKDZ) {
                this.ZXCKDZ = ZXCKDZ;
            }

            public String getZXCKMC() {
                return ZXCKMC;
            }

            public void setZXCKMC(String ZXCKMC) {
                this.ZXCKMC = ZXCKMC;
            }

            public String getZXMKZXDH() {
                return ZXMKZXDH;
            }

            public void setZXMKZXDH(String ZXMKZXDH) {
                this.ZXMKZXDH = ZXMKZXDH;
            }
        }
    }
}
