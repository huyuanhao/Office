package com.yt.simpleframe.http.bean;


import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/5/24 0024
 * 版权：
 */

public class LoginBean extends BaseBean {
    private Data DATA;


    public Data getDATA() {
        return DATA;
    }

    public void setDATA(Data DATA) {
        this.DATA = DATA;
    }

    public static class Data implements Serializable{
        private String AUTHTOKEN;
        private String USERID;
        private String USERNAME;
        private String USERTYPE;
        private String SFSMRZ;

        public String getSFSMRZ() {
            return SFSMRZ;
        }

        public void setSFSMRZ(String SFSMRZ) {
            this.SFSMRZ = SFSMRZ;
        }

        public String getAUTHTOKEN() {
            return AUTHTOKEN;
        }

        public void setAUTHTOKEN(String AUTHTOKEN) {
            this.AUTHTOKEN = AUTHTOKEN;
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

        public String getUSERTYPE() {
            return USERTYPE;
        }

        public void setUSERTYPE(String USERTYPE) {
            this.USERTYPE = USERTYPE;
        }
    }
}


