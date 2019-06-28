package com.yt.simpleframe.http.bean;

import com.yt.simpleframe.http.bean.entity.ProcessInfo;

import java.util.ArrayList;

/**
 * Created by fanliang on 18/6/9.
 */

public class ProcessListCommonBean<T> extends BaseBean{

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


    //    private PagingInfo<ProcessInfo> DATA;
//
//    public PagingInfo<ProcessInfo> getDATA() {
//        return DATA;
//    }
//
//    public void setDATA(PagingInfo<ProcessInfo> DATA) {
//        this.DATA = DATA;
//    }
}
