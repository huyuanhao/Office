package com.powerrich.office.oa.bean;

import java.util.List;

/**
 * 文件名：IWantListBean
 * 描述：
 * 作者：白煜
 * 时间：2017/11/22 0022
 * 版权：
 */

public class IWantListBean {

    /**
     * DATA : {"CURRENTROWS":1,"DATA":[{"BIZCODE":"TS2017111310352131","ISREVERT":"2","JL_ID":"20171113103521316536026957",
     * "MYNAME":"powerrich","TITLE":"powerrich的投诉"}],"ROWS":"1"}
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
         * DATA : [{"BIZCODE":"TS2017111310352131","ISREVERT":"2","JL_ID":"20171113103521316536026957","MYNAME":"powerrich",
         * "TITLE":"powerrich的投诉"}]
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
             * BIZCODE : TS2017111310352131
             * ISREVERT : 2
             * JL_ID : 20171113103521316536026957
             * MYNAME : powerrich
             * TITLE : powerrich的投诉
             */

            private String BIZCODE;
            private String ISREVERT;
            private String JL_ID;
            private String MYNAME;
            private String TITLE;
            private String CRETTIME;
            private String RESERTTIME;

            public void setRESERTTIME(String RESERTTIME) {
                this.RESERTTIME = RESERTTIME;
            }

            public String getRESERTTIME() {
                return RESERTTIME;
            }

            public void setCRETTIME(String CRETTIME) {
                this.CRETTIME = CRETTIME;
            }

            public String getCRETTIME() {
                return CRETTIME;
            }

            public String getBIZCODE() {
                return BIZCODE;
            }

            public void setBIZCODE(String BIZCODE) {
                this.BIZCODE = BIZCODE;
            }

            public String getISREVERT() {
                return ISREVERT;
            }

            public void setISREVERT(String ISREVERT) {
                this.ISREVERT = ISREVERT;
            }

            public String getJL_ID() {
                return JL_ID;
            }

            public void setJL_ID(String JL_ID) {
                this.JL_ID = JL_ID;
            }

            public String getMYNAME() {
                return MYNAME;
            }

            public void setMYNAME(String MYNAME) {
                this.MYNAME = MYNAME;
            }

            public String getTITLE() {
                return TITLE;
            }

            public void setTITLE(String TITLE) {
                this.TITLE = TITLE;
            }
        }
    }
}
