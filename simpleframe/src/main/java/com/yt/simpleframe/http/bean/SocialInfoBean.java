package com.yt.simpleframe.http.bean;

import java.util.List;

public class SocialInfoBean extends BaseBean{

    /**
     * DATA : {"CURRENTROWS":1,"DATA":[{"aac002":"360622198111145827","aac003":"祝风华","aac004":"女","aac005":"01","aac006":"1981-11-13","aac007":"2016-12-31","aac010":"春涛乡罗坪村赵家083号","aac058":"01","aac147":"360622198111145827","aae473":"1","bab304":"春涛乡罗坪村赵家083号"}]}
     * code : 0
     * message : 操作成功！
     */

    private DATABeanX DATA;

    public DATABeanX getDATA() {
        return DATA;
    }

    public void setDATA(DATABeanX DATA) {
        this.DATA = DATA;
    }

    public static class DATABeanX {
        /**
         * CURRENTROWS : 1
         * DATA : [{"aac002":"360622198111145827","aac003":"祝风华","aac004":"女","aac005":"01","aac006":"1981-11-13","aac007":"2016-12-31","aac010":"春涛乡罗坪村赵家083号","aac058":"01","aac147":"360622198111145827","aae473":"1","bab304":"春涛乡罗坪村赵家083号"}]
         */

        private String AAE020DWHJ;
        private String AAE020GRHJ;
        private String AAE020QTHJ;
        private String AAE020SSHJ;

        private int CURRENTROWS;
        private List<SocialInfo> DATA;

        public String getAAE020DWHJ() {
            return AAE020DWHJ;
        }

        public void setAAE020DWHJ(String AAE020DWHJ) {
            this.AAE020DWHJ = AAE020DWHJ;
        }

        public String getAAE020GRHJ() {
            return AAE020GRHJ;
        }

        public void setAAE020GRHJ(String AAE020GRHJ) {
            this.AAE020GRHJ = AAE020GRHJ;
        }

        public String getAAE020QTHJ() {
            return AAE020QTHJ;
        }

        public void setAAE020QTHJ(String AAE020QTHJ) {
            this.AAE020QTHJ = AAE020QTHJ;
        }

        public String getAAE020SSHJ() {
            return AAE020SSHJ;
        }

        public void setAAE020SSHJ(String AAE020SSHJ) {
            this.AAE020SSHJ = AAE020SSHJ;
        }

        public int getCURRENTROWS() {
            return CURRENTROWS;
        }

        public void setCURRENTROWS(int CURRENTROWS) {
            this.CURRENTROWS = CURRENTROWS;
        }

        public List<SocialInfo> getSocialInfoList() {
            return DATA;
        }

        public void setSocialInfoList(List<SocialInfo> socialInfoList) {
            this.DATA = socialInfoList;
        }

    }
}
