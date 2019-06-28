package com.powerrich.office.oa.bean;

public class ImgTxInfo {
    int img;
    String title;

    public ImgTxInfo(String title,int img){
        this.img = img;
        this.title = title;
    }
    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
