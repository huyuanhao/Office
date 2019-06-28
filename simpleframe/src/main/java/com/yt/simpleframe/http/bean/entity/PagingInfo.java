package com.yt.simpleframe.http.bean.entity;

/**
 * Created by fanliang on 18/6/9.
 */

import java.util.ArrayList;

/**
 * 分页
 */
public class PagingInfo<T> {
    private int CURRENTROWS;
    private String ROWS;
    private ArrayList<T> DATA;

    public int getCURRENTROWS() {
        return CURRENTROWS;
    }

    public void setCURRENTROWS(int CURRENTROWS) {
        this.CURRENTROWS = CURRENTROWS;
    }

    public String getROWS() {
        return ROWS;
    }

    public void setROWS(String ROWS) {
        this.ROWS = ROWS;
    }

    public ArrayList<T> getDATA() {
        return DATA;
    }

    public void setDATA(ArrayList<T> DATA) {
        this.DATA = DATA;
    }
}
