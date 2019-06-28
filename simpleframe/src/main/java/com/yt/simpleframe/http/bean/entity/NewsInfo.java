package com.yt.simpleframe.http.bean.entity;

/**
 * Created by Administrator on 2018/6/8.
 */

public class NewsInfo {
    private String CREATE_DATE;
    private String NEWS_ID;
    private String TITLE;
    private String NEWS_CONTENT;//内容
    private String IMG;//图片
    private boolean HOTSPOT;//true为热点新闻
    private String SOURCE;

    public boolean isHOTSPOT() {
        return HOTSPOT;
    }

    public void setHOTSPOT(boolean HOTSPOT) {
        this.HOTSPOT = HOTSPOT;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getNEWS_ID() {
        return NEWS_ID;
    }

    public void setNEWS_ID(String NEWS_ID) {
        this.NEWS_ID = NEWS_ID;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getNEWS_CONTENT() {
        return NEWS_CONTENT;
    }

    public void setNEWS_CONTENT(String NEWS_CONTENT) {
        this.NEWS_CONTENT = NEWS_CONTENT;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public String getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(String SOURCE) {
        this.SOURCE = SOURCE;
    }
}
