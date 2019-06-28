package com.powerrich.office.oa.bean;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索的实体类
 */
public class SearchItem {
    //三种类型   ///标题 1    //标题内容2  //服务内容3//更多4  5 线
    private int type;
    //名称
    private String name ;
    // 对应接口数据
    private SearchBean searchBean;
    //对应服务的集合 （图标+文字）
    private List<SearchBean> arrayList;
     //跳转
    private int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setArrayList(List<SearchBean> arrayList) {
        this.arrayList = arrayList;
    }

    public SearchItem(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public SearchBean getItem() {
        return searchBean;
    }

    public void setItem(SearchBean item) {
        this.searchBean = item;
    }

    public List<SearchBean> getArrayList() {
        return arrayList;
    }

    public void List(ArrayList<SearchBean> arrayList) {
        this.arrayList = arrayList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SearchBean getSearchBean() {
        return searchBean;
    }

    public void setSearchBean(SearchBean searchBean) {
        this.searchBean = searchBean;
    }
}
