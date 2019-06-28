package com.yt.simpleframe.http.bean.entity;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/5/25 0025
 * 版权：
 */

public class NewImageInfo implements Serializable{
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
