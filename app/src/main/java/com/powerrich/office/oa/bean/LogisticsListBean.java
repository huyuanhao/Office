package com.powerrich.office.oa.bean;

import java.util.List;

/**
 * 文件名：LogisticsListBean
 * 描述：
 * 作者：白煜
 * 时间：2017/12/25 0025
 * 版权：
 */

public class LogisticsListBean {

    /**
     * DATA : {"CURRENTROWS":1,"DATA":[{"CONSIGNEE_ADDRESS":"深圳市福田区彩田南路海滨广场皇岗花园大厦A栋三楼","CONSIGNEE_NAME":"陈志强",
     * "DATA_SOURCE":"3","EMIT_TIME":"2017-12-29 11:21:52","ITEM_NAME":"公共场所卫生许可申请","ORDER_NUM":"gt_ems_20171229112152127",
     * "ORDER_STATUS":"0","PROKEYID":"20c855a6998d4e8bb069511778285f96","RECORD_ID":"201712291121522937138301490",
     * "SEND_NAME":"市卫计委","TRACKING_NUM":"","USER_ID":"20171229090750376239661372"}],"ROWS":"1"}
     * code : 0
     * message : 操作成功
     */

    private DATABeanX DATA;
    private String code;
    private String message;

    public DATABeanX getDATA() {
        return DATA;
    }

    public void setDATA(DATABeanX DATA) {
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

    public static class DATABeanX {
        /**
         * CURRENTROWS : 1
         * DATA : [{"CONSIGNEE_ADDRESS":"深圳市福田区彩田南路海滨广场皇岗花园大厦A栋三楼","CONSIGNEE_NAME":"陈志强","DATA_SOURCE":"3",
         * "EMIT_TIME":"2017-12-29 11:21:52","ITEM_NAME":"公共场所卫生许可申请","ORDER_NUM":"gt_ems_20171229112152127",
         * "ORDER_STATUS":"0","PROKEYID":"20c855a6998d4e8bb069511778285f96","RECORD_ID":"201712291121522937138301490",
         * "SEND_NAME":"市卫计委","TRACKING_NUM":"","USER_ID":"20171229090750376239661372"}]
         * ROWS : 1
         */

        private int CURRENTROWS;
        private String ROWS;
        private List<DATABean> DATA;

        public int getCURRENTROWS() {
            return CURRENTROWS;
        }

        public void setCURRENTROWS(int CURRENTROWS) {
            this.CURRENTROWS = CURRENTROWS;
        }

        public String getROWS() {
            return ROWS;
        }

        public void setROWS(String ROWS) {
            this.ROWS = ROWS;
        }

        public List<DATABean> getDATA() {
            return DATA;
        }

        public void setDATA(List<DATABean> DATA) {
            this.DATA = DATA;
        }

        public static class DATABean {
            /**
             * CONSIGNEE_ADDRESS : 深圳市福田区彩田南路海滨广场皇岗花园大厦A栋三楼
             * CONSIGNEE_NAME : 陈志强
             * DATA_SOURCE : 3
             * EMIT_TIME : 2017-12-29 11:21:52
             * ITEM_NAME : 公共场所卫生许可申请
             * ORDER_NUM : gt_ems_20171229112152127
             * ORDER_STATUS : 0
             * PROKEYID : 20c855a6998d4e8bb069511778285f96
             * RECORD_ID : 201712291121522937138301490
             * SEND_NAME : 市卫计委
             * TRACKING_NUM :
             * USER_ID : 20171229090750376239661372
             */

            private String CONSIGNEE_ADDRESS;
            private String CONSIGNEE_NAME;
            private String DATA_SOURCE;
            private String EMIT_TIME;
            private String ITEM_NAME;
            private String ORDER_NUM;
            private String ORDER_STATUS;
            private String PROKEYID;
            private String RECORD_ID;
            private String SEND_NAME;
            private String TRACKING_NUM;
            private String USER_ID;

            public String getCONSIGNEE_ADDRESS() {
                return CONSIGNEE_ADDRESS;
            }

            public void setCONSIGNEE_ADDRESS(String CONSIGNEE_ADDRESS) {
                this.CONSIGNEE_ADDRESS = CONSIGNEE_ADDRESS;
            }

            public String getCONSIGNEE_NAME() {
                return CONSIGNEE_NAME;
            }

            public void setCONSIGNEE_NAME(String CONSIGNEE_NAME) {
                this.CONSIGNEE_NAME = CONSIGNEE_NAME;
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

            public String getITEM_NAME() {
                return ITEM_NAME;
            }

            public void setITEM_NAME(String ITEM_NAME) {
                this.ITEM_NAME = ITEM_NAME;
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

            public String getPROKEYID() {
                return PROKEYID;
            }

            public void setPROKEYID(String PROKEYID) {
                this.PROKEYID = PROKEYID;
            }

            public String getRECORD_ID() {
                return RECORD_ID;
            }

            public void setRECORD_ID(String RECORD_ID) {
                this.RECORD_ID = RECORD_ID;
            }

            public String getSEND_NAME() {
                return SEND_NAME;
            }

            public void setSEND_NAME(String SEND_NAME) {
                this.SEND_NAME = SEND_NAME;
            }

            public String getTRACKING_NUM() {
                return TRACKING_NUM;
            }

            public void setTRACKING_NUM(String TRACKING_NUM) {
                this.TRACKING_NUM = TRACKING_NUM;
            }

            public String getUSER_ID() {
                return USER_ID;
            }

            public void setUSER_ID(String USER_ID) {
                this.USER_ID = USER_ID;
            }
        }
    }
}
