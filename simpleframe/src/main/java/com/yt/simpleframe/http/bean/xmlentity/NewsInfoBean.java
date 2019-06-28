package com.yt.simpleframe.http.bean.xmlentity;

import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.bean.entity.NewsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/8.
 */

public class NewsInfoBean extends BaseBean{

    /**
     * DATA : {"CURRENTROWS":10,"DATA":[{"CREATE_DATE":"2018-03-28","NEWS_ID":"201803261113475816982621683","TITLE":"全市村（社区）\u201c两委\u201d换届选举工作推进会召开"},{"CREATE_DATE":"2018-03-28","NEWS_ID":"201803261115477111363245335","TITLE":"鹰潭市2018年\u201c3·15\u201d 国际消费者权益日 宣传咨询服务活动举行"},{"CREATE_DATE":"2018-03-28","NEWS_ID":"201803261114335195718327943","TITLE":"市领导调研全市招商引资工作和重大项目推进情况"},{"CREATE_DATE":"2018-03-26 11:12","NEWS_ID":"201803261112424726417132034","TITLE":"关于依法管理机动车违法停放占用集镇人行道的有关通告"},{"CREATE_DATE":"2018-03-26","NEWS_ID":"201803261117332132360317983","TITLE":"行人闯红灯视频抓拍系统启用"},{"CREATE_DATE":"2018-03-26","NEWS_ID":"201803261118562447372159115","TITLE":"一架波音737大飞机从马来西亚运抵江西师范高等专科学校校园"},{"CREATE_DATE":"2018-02-08 15:34","NEWS_ID":"201802081534527791830523785","TITLE":"互联网企业布局线下 商业地产或将重新振兴"},{"CREATE_DATE":"2018-02-08","NEWS_ID":"201802081547309775976139201","TITLE":"大家一起努力把江西建设得更美好"},{"CREATE_DATE":"2018-02-06","NEWS_ID":"201802061141338722073111002","TITLE":"习近平心系\u201c三农\u201d 领航新时代乡村振兴"},{"CREATE_DATE":"2018-02-02","NEWS_ID":"201802021402274693898064970","TITLE":"人民日报连续4年提及最多的企业家：马云"}],"ROWS":"16"}
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
         * CURRENTROWS : 10
         * DATA : [{"CREATE_DATE":"2018-03-28","NEWS_ID":"201803261113475816982621683","TITLE":"全市村（社区）\u201c两委\u201d换届选举工作推进会召开"},{"CREATE_DATE":"2018-03-28","NEWS_ID":"201803261115477111363245335","TITLE":"鹰潭市2018年\u201c3·15\u201d 国际消费者权益日 宣传咨询服务活动举行"},{"CREATE_DATE":"2018-03-28","NEWS_ID":"201803261114335195718327943","TITLE":"市领导调研全市招商引资工作和重大项目推进情况"},{"CREATE_DATE":"2018-03-26 11:12","NEWS_ID":"201803261112424726417132034","TITLE":"关于依法管理机动车违法停放占用集镇人行道的有关通告"},{"CREATE_DATE":"2018-03-26","NEWS_ID":"201803261117332132360317983","TITLE":"行人闯红灯视频抓拍系统启用"},{"CREATE_DATE":"2018-03-26","NEWS_ID":"201803261118562447372159115","TITLE":"一架波音737大飞机从马来西亚运抵江西师范高等专科学校校园"},{"CREATE_DATE":"2018-02-08 15:34","NEWS_ID":"201802081534527791830523785","TITLE":"互联网企业布局线下 商业地产或将重新振兴"},{"CREATE_DATE":"2018-02-08","NEWS_ID":"201802081547309775976139201","TITLE":"大家一起努力把江西建设得更美好"},{"CREATE_DATE":"2018-02-06","NEWS_ID":"201802061141338722073111002","TITLE":"习近平心系\u201c三农\u201d 领航新时代乡村振兴"},{"CREATE_DATE":"2018-02-02","NEWS_ID":"201802021402274693898064970","TITLE":"人民日报连续4年提及最多的企业家：马云"}]
         * ROWS : 16
         */

        private int CURRENTROWS;
        private int ROWS;
        private List<NewsInfo> DATA = new ArrayList<>();

        public int getCURRENTROWS() {
            return CURRENTROWS;
        }

        public void setCURRENTROWS(int CURRENTROWS) {
            this.CURRENTROWS = CURRENTROWS;
        }

        public int getROWS() {
            return ROWS;
        }

        public void setROWS(int ROWS) {
            this.ROWS = ROWS;
        }

        public List<NewsInfo> getDATA() {
            return DATA;
        }

        public void setDATA(List<NewsInfo> DATA) {
            this.DATA = DATA;
        }
    }
}
