package com.powerrich.office.oa.bean;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：白煜
 * 时间：2018/1/5 0005
 * 版权：
 */

public class AppointmentListBean {

    /**
     * DATA : {"CURRENTROWS":1,"DATA":[{"A_ID":"201712070900143568129143018","DEPTNAME":"市公安局","ITEMNAME":"港澳通行证申请",
     * "ORDER_NO":"00143562609","REAL_NAME":"张楠"}],"ROWS":"1"}
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
         * DATA : [{"A_ID":"201712070900143568129143018","DEPTNAME":"市公安局","ITEMNAME":"港澳通行证申请","ORDER_NO":"00143562609",
         * "REAL_NAME":"张楠"}]
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
             * A_ID : 201712070900143568129143018
             * DEPTNAME : 市公安局
             * ITEMNAME : 港澳通行证申请
             * ORDER_NO : 00143562609
             * REAL_NAME : 张楠
             */

            private String A_ID;
            private String DEPTNAME;
            private String ITEMNAME;
            private String ORDER_NO;
            private String REAL_NAME;

            public String getA_ID() {
                return A_ID;
            }

            public void setA_ID(String A_ID) {
                this.A_ID = A_ID;
            }

            public String getDEPTNAME() {
                return DEPTNAME;
            }

            public void setDEPTNAME(String DEPTNAME) {
                this.DEPTNAME = DEPTNAME;
            }

            public String getITEMNAME() {
                return ITEMNAME;
            }

            public void setITEMNAME(String ITEMNAME) {
                this.ITEMNAME = ITEMNAME;
            }

            public String getORDER_NO() {
                return ORDER_NO;
            }

            public void setORDER_NO(String ORDER_NO) {
                this.ORDER_NO = ORDER_NO;
            }

            public String getREAL_NAME() {
                return REAL_NAME;
            }

            public void setREAL_NAME(String REAL_NAME) {
                this.REAL_NAME = REAL_NAME;
            }
        }
    }
}
