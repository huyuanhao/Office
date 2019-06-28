package com.powerrich.office.oa.bean;


import java.io.File;

/**
 * Created by ch on 2016/5/4.
 */
public class RollItem {
    /**
     * 轮播图文字
     */
    public String title;
    /**
     * 默认图片
     */
    public int defaultImage;
    /**
     * 网络地址
     */
    public String url;

    public String id;

    public File file;

    public RollItem() {

    }

    public RollItem(String title, int defaultImage) {
        this.title = title;
        this.defaultImage = defaultImage;
    }

    public RollItem(String title, int defaultImage, String url) {
        this.title = title;
        this.defaultImage = defaultImage;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(int defaultImage) {
        this.defaultImage = defaultImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
