package com.powerrich.office.oa.bean;

/**
 * 文 件 名：MobileBusinessLeftBean
 * 描    述：
 * 作    者：chenhao
 * 时    间：2018/7/23
 * 版    权：v1.0
 */
public class MobileBusinessLeftBean {

    private String id;
    private String title;
    private boolean isSelected;

    public MobileBusinessLeftBean() {
    }

    public MobileBusinessLeftBean(String id, String title, boolean isSelected) {
        this.title = title;
        this.isSelected = isSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
