package com.yt.simpleframe.http.bean;

import java.util.List;

/**
 * Created by fanliang on 18/6/9.
 */

public class VolunteerImgsBean extends BaseBean {

    private List<ImageInfo> DATA;

    public List<ImageInfo> getDATA() {
        return DATA;
    }

    public void setDATA(List<ImageInfo> DATA) {
        this.DATA = DATA;
    }

    public static class ImageInfo {
        /**
         * IMG : platform/news_img/201807281443553790947349094_2.png
         * NEW_ID : 201807281443553790947349094
         * TITLE : 红十字基金会“守护安全•中小学生安全防护书包捐赠活动”
         */

        private String IMG;
        private String NEW_ID;
        private String TITLE;

        public String getIMG() {
            return IMG;
        }

        public void setIMG(String IMG) {
            this.IMG = IMG;
        }

        public String getNEW_ID() {
            return NEW_ID;
        }

        public void setNEW_ID(String NEW_ID) {
            this.NEW_ID = NEW_ID;
        }

        public String getTITLE() {
            return TITLE;
        }

        public void setTITLE(String TITLE) {
            this.TITLE = TITLE;
        }
    }
}
