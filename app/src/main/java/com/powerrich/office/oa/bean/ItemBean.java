package com.powerrich.office.oa.bean;

import java.io.Serializable;

/**
 * @author MingPeng
 * @date 2017/11/4
 */

public class ItemBean implements Serializable {
    private int imageRes;
    private String title;
    private String content;

    public ItemBean(int imageRes, String title, String content) {
        this.imageRes = imageRes;
        this.title = title;
        this.content = content;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
