package com.yt.simpleframe.http.bean;

import com.yt.simpleframe.http.bean.entity.ProcessInfo;

import java.util.ArrayList;

/**
 * Created by fanliang on 18/6/9.
 */

public class ProcessMaterialsDepotBeanListBean extends BaseBean{

    private int CURRENTROWS;
    private String ROWS;
    private ArrayList<MaterialsDepotBean> DATA;

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

    public ArrayList<MaterialsDepotBean> getDATA() {
        return DATA;
    }

    public void setDATA(ArrayList<MaterialsDepotBean> DATA) {
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
