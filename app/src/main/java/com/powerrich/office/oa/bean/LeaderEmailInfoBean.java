package com.powerrich.office.oa.bean;

/**
 * 文件名：
 * 描述：
 * 作者：白煜
 * 时间：2018/3/21 0021
 * 版权：
 */

public class LeaderEmailInfoBean
{

    /**
     * DATA : {"CONTENT":"sdf","IS_REVERT":"0","LEADER_NAME":"","LEADER_NO":"","MID":"201803201539128306759584702",
     * "REVERT_CONTENT":"","REVERT_TIME":"","SEND_TIME":"2018-03-20 15:39:12","SEND_USER":"chenzhiqiang",
     * "SEND_USER_ID":"201801261631497478967845420","SITE_NAME":"市科技局","SITE_NO":"201711151545456564685680018",
     * "TITLE":"领导好，我买的手机为什么这么卡","USER_TYPE":"1"}
     * code : 0
     * message : 操作成功！
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
         * CONTENT : sdf
         * IS_REVERT : 0
         * LEADER_NAME :
         * LEADER_NO :
         * MID : 201803201539128306759584702
         * REVERT_CONTENT :
         * REVERT_TIME :
         * SEND_TIME : 2018-03-20 15:39:12
         * SEND_USER : chenzhiqiang
         * SEND_USER_ID : 201801261631497478967845420
         * SITE_NAME : 市科技局
         * SITE_NO : 201711151545456564685680018
         * TITLE : 领导好，我买的手机为什么这么卡
         * USER_TYPE : 1
         */

        private String CONTENT;
        private String IS_REVERT;
        private String LEADER_NAME;
        private String LEADER_NO;
        private String MID;
        private String REVERT_CONTENT;
        private String REVERT_TIME;
        private String SEND_TIME;
        private String SEND_USER;
        private String SEND_USER_ID;
        private String SITE_NAME;
        private String SITE_NO;
        private String TITLE;
        private String USER_TYPE;

        public String getCONTENT() {
            return CONTENT;
        }

        public void setCONTENT(String CONTENT) {
            this.CONTENT = CONTENT;
        }

        public String getIS_REVERT() {
            return IS_REVERT;
        }

        public void setIS_REVERT(String IS_REVERT) {
            this.IS_REVERT = IS_REVERT;
        }

        public String getLEADER_NAME() {
            return LEADER_NAME;
        }

        public void setLEADER_NAME(String LEADER_NAME) {
            this.LEADER_NAME = LEADER_NAME;
        }

        public String getLEADER_NO() {
            return LEADER_NO;
        }

        public void setLEADER_NO(String LEADER_NO) {
            this.LEADER_NO = LEADER_NO;
        }

        public String getMID() {
            return MID;
        }

        public void setMID(String MID) {
            this.MID = MID;
        }

        public String getREVERT_CONTENT() {
            return REVERT_CONTENT;
        }

        public void setREVERT_CONTENT(String REVERT_CONTENT) {
            this.REVERT_CONTENT = REVERT_CONTENT;
        }

        public String getREVERT_TIME() {
            return REVERT_TIME;
        }

        public void setREVERT_TIME(String REVERT_TIME) {
            this.REVERT_TIME = REVERT_TIME;
        }

        public String getSEND_TIME() {
            return SEND_TIME;
        }

        public void setSEND_TIME(String SEND_TIME) {
            this.SEND_TIME = SEND_TIME;
        }

        public String getSEND_USER() {
            return SEND_USER;
        }

        public void setSEND_USER(String SEND_USER) {
            this.SEND_USER = SEND_USER;
        }

        public String getSEND_USER_ID() {
            return SEND_USER_ID;
        }

        public void setSEND_USER_ID(String SEND_USER_ID) {
            this.SEND_USER_ID = SEND_USER_ID;
        }

        public String getSITE_NAME() {
            return SITE_NAME;
        }

        public void setSITE_NAME(String SITE_NAME) {
            this.SITE_NAME = SITE_NAME;
        }

        public String getSITE_NO() {
            return SITE_NO;
        }

        public void setSITE_NO(String SITE_NO) {
            this.SITE_NO = SITE_NO;
        }

        public String getTITLE() {
            return TITLE;
        }

        public void setTITLE(String TITLE) {
            this.TITLE = TITLE;
        }

        public String getUSER_TYPE() {
            return USER_TYPE;
        }

        public void setUSER_TYPE(String USER_TYPE) {
            this.USER_TYPE = USER_TYPE;
        }
    }
}
