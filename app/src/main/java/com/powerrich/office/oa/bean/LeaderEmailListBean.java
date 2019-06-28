package com.powerrich.office.oa.bean;

import java.util.List;

/**
 * 文件名：LeaderEmailListBean
 * 描述：
 * 作者：白煜
 * 时间：2018/3/20 0020
 * 版权：
 */

public class LeaderEmailListBean {

    /**
     * DATA : {"CURRENTROWS":1,"DATA":[{"IS_REVERT":"0","MID":"201803201539128306759584702","SEND_TIME":"2018-03-20 15:39:12",
     * "TITLE":"领导好，我买的手机为什么这么卡"}],"ROWS":"1"}
     * code : 0
     * message : 操作成功！
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
         * DATA : [{"IS_REVERT":"0","MID":"201803201539128306759584702","SEND_TIME":"2018-03-20 15:39:12",
         * "TITLE":"领导好，我买的手机为什么这么卡"}]
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
             * IS_REVERT : 0
             * MID : 201803201539128306759584702
             * SEND_TIME : 2018-03-20 15:39:12
             * TITLE : 领导好，我买的手机为什么这么卡
             */

            private String IS_REVERT;
            private String MID;
            private String SEND_TIME;
            private String TITLE;

            public String getIS_REVERT() {
                return IS_REVERT;
            }

            public void setIS_REVERT(String IS_REVERT) {
                this.IS_REVERT = IS_REVERT;
            }

            public String getMID() {
                return MID;
            }

            public void setMID(String MID) {
                this.MID = MID;
            }

            public String getSEND_TIME() {
                return SEND_TIME;
            }

            public void setSEND_TIME(String SEND_TIME) {
                this.SEND_TIME = SEND_TIME;
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
